package de.tu_bs.cs.isf.e4cf.core.file_structure.tree.sync;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tu_bs.cs.isf.e4cf.core.file_structure.tree.sync.FileTreeEvent.FileEvent;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileEventLog;

public class FileTreeWatcher implements Runnable {

	/**
	 * The time (in milliseconds) the watcher will wait until it interrupts the wait for a finished file operation.
	 */
	public final long MAX_WAIT_TIME;
	
	private WatchService _watchService = null;
	private Map<WatchKey, Path> _watchedPaths = new HashMap<>();
	private List<TreeListener> listeners = new ArrayList<>();
	private Path _root = null;
	
	private Thread _thread = null;
	private boolean _isRunning = true;
	
	private FileEventLog _logger = FileEventLog.getInstance();
	
	public FileTreeWatcher(Path root, long maxWaitTime) {
		_root = root;
		MAX_WAIT_TIME = maxWaitTime;
	}
	
	public void watchFileTree() throws IOException {
		_watchService = FileSystems.getDefault().newWatchService();
		_watchedPaths = new HashMap<>();
		
		registerDirectory(_root, true);
		
		_thread = new Thread(this, this.getClass().getSimpleName()+"@"+hashCode());
		_thread.setDaemon(true);
		_thread.start();
	}
	
	public void stopWatchJob() {
		_isRunning = false;
		try {
			_thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void registerDirectory(Path dir, boolean recursive) {
		try {
			FileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attribs) throws IOException {
					WatchKey key = dir.register(_watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
					Path p = _watchedPaths.put(key, dir);
					if (p == null) _logger.insertEvent("Register Directory", dir);
					return FileVisitResult.CONTINUE;
				}
			};
			Files.walkFileTree(_root,Collections.<FileVisitOption>emptySet(),recursive?Integer.MAX_VALUE:0, visitor);
		
		} catch (IOException e) {
			_logger.insertEvent("Registering Directory Failed", dir);
			e.printStackTrace();
		}
	}
	
	public void deregisterDirectory(Path dir) {
		if (_watchedPaths.entrySet().removeIf(entry -> entry.getValue().equals(dir))) {
			_logger.insertEvent("Deregister Directory", dir);				
		}
	}
	
	@Override
	public void run() {
		
		while (_isRunning) {
			WatchKey key = null;
			try {
				key = _watchService.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Path watchedDirectoryPath = _watchedPaths.get(key);
			if (watchedDirectoryPath != null) { // directory registered in our map
				for (WatchEvent<?> watchEvent : key.pollEvents()) {
					if (watchEvent.context() instanceof Path) {
						Path fullPath = watchedDirectoryPath.resolve((Path) watchEvent.context());
						logWatchEvent(watchEvent.kind(), fullPath);
						FileTreeEvent event = new FileTreeEvent(fullPath, watchEvent);
						handleWatchEvent(fullPath, event);
					}
				}					
			} else {
				_logger.insertEvent("Directory is not registered", null);
			}
			key.reset();
		}
		cleanUp();
	}

	private void cleanUp() {
		try {
			_watchService.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	protected void handleWatchEvent(Path p, FileTreeEvent event) {
		
		waitFileOperation(p, event);
		
		// (de-)register directories
		if (event.getType() == FileEvent.CREATED) {
			if (Files.exists(p) && Files.isDirectory(p)) {
				registerDirectory(p, true);
			}
		} else if (event.getType() == FileEvent.DELETED) {
			deregisterDirectory(p);
		}
		
		// process events
		listeners.forEach(l -> l.treeEvent(event));
	}
	
	protected void waitFileOperation(Path p, FileTreeEvent event) {
		boolean eventCondition = true;
		long endTime = System.currentTimeMillis() + MAX_WAIT_TIME;
		if (event.getType() == FileEvent.CREATED) {
			eventCondition = Files.exists(p);
		} else if (event.getType() == FileEvent.DELETED) {
			eventCondition = !Files.exists(p);
		} else if (event.getType() == FileEvent.MODIFIED) {
			// TODO: look for wait times induced my modifying operations
		}
		
		while (!eventCondition && endTime >= System.currentTimeMillis()) {}
	}

	protected void logWatchEvent(WatchEvent.Kind<?> type, Path p) {
		if (type == StandardWatchEventKinds.ENTRY_CREATE) {
			_logger.insertEvent("Create Event Fired", p);
		} else if (type == StandardWatchEventKinds.ENTRY_DELETE) {
			_logger.insertEvent("Delete Event Fired", p);
		} else if (type == StandardWatchEventKinds.ENTRY_MODIFY) {
			_logger.insertEvent("Modify Event Fired", p);
		}
	}	
	
	public void addListener(TreeListener l) {
		if (!listeners.contains(l)) listeners.add(l);
	}
	
	public boolean removeListener(TreeListener l) {
		return listeners.remove(l);
	}
	
	public void close() {
		_isRunning = false;
	}
}

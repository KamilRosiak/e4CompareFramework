package de.tu_bs.cs.isf.e4cf.core.file_structure.tree.sync;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.NoSuchElementException;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.WorkspaceFileSystem;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations.CreateFile;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations.CreateSubdirectory;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations.DeleteDirectory;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations.DeleteFile;
import de.tu_bs.cs.isf.e4cf.core.file_structure.tree.sync.FileTreeEvent.FileEvent;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileEventLog;

public class TreeSynchronization {
	
	private static final long FILE_SYSTEM_MAX_WAIT_TIME = 5000L;
	
	private WorkspaceFileSystem _fileSystem = null;
	private FileTreeWatcher _watcher = null;
	private TreeListener _treeListener = null;
	private FileEventLog _logger = FileEventLog.getInstance();
		
	public TreeSynchronization(WorkspaceFileSystem instance) {
		try {
			_fileSystem = instance;
			startSyncronization();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void startSyncronization() throws IOException {
		if (_watcher == null) {
			_watcher = new FileTreeWatcher(_fileSystem.getWorkspaceDirectory().getFile(),FILE_SYSTEM_MAX_WAIT_TIME);
		}
		_watcher.watchFileTree();
		_watcher.addListener(constructTreeListener());
	}

	private TreeListener constructTreeListener() {
		_treeListener = 
			event -> {
				try {
					if (event.getType() == FileEvent.CREATED) {
						fileCreated(event.getPath());
					} else if (event.getType() == FileEvent.DELETED) {
						fileDeleted(event.getPath());
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					_fileSystem.refresh();
				}
			};
		return _treeListener;
	}
	
	private void fileCreated(Path path) throws IOException {
		FileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attribs) throws IOException {
				createTreeElementFrom(path);
				return FileVisitResult.CONTINUE;
			}
			@Override
			public FileVisitResult visitFile(Path path, BasicFileAttributes attribs) throws IOException {
				createTreeElementFrom(path);
				return FileVisitResult.CONTINUE;
			}
			@Override
			public FileVisitResult visitFileFailed(Path path, IOException e) throws IOException {
				_logger.insertEvent("File Visit Failed", path);
				return FileVisitResult.CONTINUE;
			}
		};
		Files.walkFileTree(path, visitor);
	}
	
	private FileTreeElement createTreeElementFrom(Path path) {
		FileTreeElement element = null;
		try {
			if (!pathExists(path) && !isHiddenDirectory(path)) {
				FileTreeElement parentElement = _fileSystem.search(path.getParent().toString());
				element = appendNewElement(path, parentElement);
			} else {
				_logger.insertEvent("Create Node Failed", path);
			}
		} catch (NoSuchElementException e) {
			_logger.insertEvent("Parent Element Not Found", path.getParent());	
		}
		return element;
	}

	private boolean pathExists(Path path) {
		try {
			_fileSystem.search(path.toString());
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	private FileTreeElement appendNewElement(Path path, FileTreeElement parentElement) {
		FileTreeElement element = null;
		try {
			if (Files.isDirectory(path)) {
				element = createSubDirectory(parentElement, path.getFileName().toString());
			} else if (Files.isRegularFile(path)) {
				element = createFile(parentElement, path.getFileName().toString());
			} else {
				throw new RuntimeException("Path \""+path.toString()+"\" is neither a regular file nor a directory.");
			}
			if (element != null) {
				_logger.insertEvent("Create Node", path);
			} else {
				_logger.insertEvent("Create Node Failed", path);
			}
		} catch (Exception e) {
			e.printStackTrace();
			_logger.insertEvent("Create Node Failed", path);
		}
		return element;
	}
	
	private void fileDeleted(Path path) {
		deleteTreeElementFrom(path);
	}
	
	private FileTreeElement deleteTreeElementFrom(Path path) {
		FileTreeElement element = null;
		try {
			if (!isHiddenDirectory(path)) {
				element = _fileSystem.search(path.toString());
				deleteElement(element);
				_logger.insertEvent("Delete Node", path);
			} else {
				_logger.insertEvent("Delete Node Failed", path);
			}
		} catch (NoSuchElementException e) {
			_logger.insertEvent("Delete Node Failed", path);
			element = null;
		} catch (IOException e) {
			_logger.insertEvent("Delete Node Failed (file already deleted)", path);
			element = null;
		}
		return element;
	}
	
	private boolean isHiddenDirectory(Path path) {
		String filename = path.getFileName().toString();
		if (filename.startsWith(".")) {
			return true;
		}
		return false;
	}
	
	private FileTreeElement createSubDirectory(FileTreeElement parentDirectory, String subdirectoryName) throws IOException {
		return parentDirectory.create(new CreateSubdirectory(subdirectoryName));
	}

	private FileTreeElement createFile(FileTreeElement parentDirectory, String filename) throws IOException {
		return parentDirectory.create(new CreateFile(filename));
	}

	private FileTreeElement deleteElement(FileTreeElement element) throws IOException{
		if (_fileSystem.isWorkspace(element)) {
			return null;
		}
		
		if (element.isDirectory()) {
			return element.delete(new DeleteDirectory());				
		} else {
			return element.delete(new DeleteFile());				
		}
	}
	
	public void stopSynchronization() {
		_watcher.removeListener(_treeListener);
	}
	
	public void continueSynchronization() {
		_watcher.addListener(_treeListener);			
	}
	
	public void dispose() {
		_watcher.close();
	}
}

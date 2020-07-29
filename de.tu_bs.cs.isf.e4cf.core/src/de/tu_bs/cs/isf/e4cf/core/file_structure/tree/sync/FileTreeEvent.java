package de.tu_bs.cs.isf.e4cf.core.file_structure.tree.sync;

import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;

public class FileTreeEvent {

	public static enum FileEvent {
		CREATED,
		DELETED,
		MODIFIED
	}

	Path _path;
	FileEvent _type;
	
	public FileTreeEvent(Path p, FileEvent event) {
		_path = p;
		_type = event;
	}

	public FileTreeEvent(Path p, WatchEvent<?> watchEvent) {
		_path = p;
		if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
			_type = FileEvent.CREATED;
		}
		if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
			_type = FileEvent.DELETED;
		}
		if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
			_type = FileEvent.MODIFIED;
		}
	}

	public Path getPath() {
		return _path;
	}

	public FileEvent getType() {
		return _type;
	}

}

package de.tu_bs.cs.isf.e4cf.core.file_structure.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileEventLog {

	protected static FileEventLog _instance = null;
	protected List<Pair<String, Path>> _eventLog = new ArrayList<>();
	protected Path _root;
	protected boolean _debug = true;
	
	protected FileEventLog() {
	}

	public static FileEventLog getInstance() {
		if (_instance == null) _instance = new FileEventLog();
		return _instance;
	}
	
	public void insertEvent(String action, Path affectedPath) {
		if (_debug) {
			_eventLog.add(new Pair<String, Path>(action, affectedPath));			
		}
	}
	
	public List<Pair<String,Path>> getLog() {
		return _eventLog;
	}
	
	public void writeEventLogTo(Path p) throws IOException {
		if (_debug) {
			List<String> events = new ArrayList<>();
			_eventLog.forEach(pair -> {
				Path relPath = null;
				if (pair.second != null) {
					if (p.equals(getRoot())) {
						relPath = getRoot();
					} else if (pair.second.isAbsolute()) {
						relPath = pair.second != null ? getRoot().relativize(pair.second) : null;					
					} else {
						relPath = pair.second;	
					}
				}
				events.add(String.format("%-30s", pair.first)+" : "+(relPath != null ? relPath.toString() : ""));
			});
			Files.write(p, events, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);	
		}
	}

	public Path getRoot() {
		return _root;
	}

	public void setRoot(Path root) {
		_root = root;
	}

	public boolean isDebug() {
		return _debug;
	}

	public void setDebug(boolean debug) {
		this._debug = debug;
	}
}

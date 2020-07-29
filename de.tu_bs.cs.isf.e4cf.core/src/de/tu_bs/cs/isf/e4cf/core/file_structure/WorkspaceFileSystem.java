package de.tu_bs.cs.isf.e4cf.core.file_structure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.services.events.IEventBroker;

import de.tu_bs.cs.isf.e4cf.core.file_structure.components.Directory;
import de.tu_bs.cs.isf.e4cf.core.file_structure.tree.DefaultTreeBuilder;
import de.tu_bs.cs.isf.e4cf.core.file_structure.tree.TreeBuilder;
import de.tu_bs.cs.isf.e4cf.core.file_structure.tree.util.DepthFirstTreeIterator;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileEventLog;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CEventTable;

@Creatable
@Singleton
public class WorkspaceFileSystem implements Iterable<FileTreeElement>{
	protected FileTreeElement _root = null;
	protected TreeBuilder _builder;	
	@Inject protected IEventBroker _eventBroker;
	
	// TODO: Create ExtensionManager that is extendible by plugins
	// --> holds a map for load/save operations
	// --> holds a map for icons association

	public WorkspaceFileSystem() {
		_builder = new DefaultTreeBuilder(3);
	}
	
	public WorkspaceFileSystem(TreeBuilder builder) {	
		_builder = builder;
	}
	
	public void initializeFileTree(String path) {
		_root = new Directory(path);
		FileEventLog.getInstance().setRoot(FileHandlingUtility.getPath(_root));
		FileEventLog.getInstance().setDebug(false);
		try {
			_builder.buildTree(_root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void copy(Path source, Path target) throws IOException {
		Files.copy(source, target.resolve(source.getFileName()));
	}
	
	public FileTreeElement search(String absPath) throws NoSuchElementException {
		Iterator<FileTreeElement> it = iterator();
		while (it.hasNext()) {
			FileTreeElement element = it.next();
			if (element.getAbsolutePath().equals(absPath)) return element;
		}
		throw new NoSuchElementException("The path "+absPath+" hasn't been found in the workspace.");
	}
	
	public FileTreeElement search(Path path) throws NoSuchElementException {
		return search(path.toAbsolutePath().toString());
	}
	
	public boolean isWorkspace(FileTreeElement element) {
		return (element.equals(_root) && element.getParent() == null && element.isDirectory());
	}
	
	public boolean isProject(FileTreeElement element) {
		return (element.getParent().equals(_root) && element.isDirectory());
	}
	
	public List<Directory> getProjectDirectories() {
		List<Directory> projects = new ArrayList<>();
		_root.getChildren().forEach(element -> {
			if (element.isDirectory()) projects.add((Directory) element);	
		});
		return projects;
	}
	
	public Directory getWorkspaceDirectory() {
		if (!(_root instanceof Directory)) throw new RuntimeException("File system root is not a directory.");
		return (Directory) _root;
	}
	
	@Override
	public Iterator<FileTreeElement> iterator() {
		return new DepthFirstTreeIterator(_root);
	}
	
	public void refresh() {
		_eventBroker.send(E4CEventTable.EVENT_REFRESH_PROJECT_VIEWER, null);
	}
}

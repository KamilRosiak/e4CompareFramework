package de.tu_bs.cs.isf.e4cf.core.file_structure.components;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations.CopyOperation;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations.SaveOperation;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;

public abstract class AbstractFileTreeElement implements FileTreeElement {

	protected Path _file;
	protected FileTreeElement _parent;
	protected List<FileTreeElement> _childElements = new ArrayList<>();
	
	protected AbstractFileTreeElement(String path, FileTreeElement parent, List<FileTreeElement> childElements) {
		try {
			_file = Paths.get(path);
			_parent = parent;
			if (parent != null) {
				_parent.getChildren().add(this);
			}
			if (childElements != null) {
				_childElements.addAll(childElements);
				_childElements.forEach(c -> c.setParent(this));
			}
		} catch(InvalidPathException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void copy(CopyOperation copyOp) {
		copyOp.execute();
	}

	@Override
	public boolean equals(FileTreeElement ftElement) {
		return this.getAbsolutePath().equals(ftElement.getAbsolutePath());
	}
	
	public boolean exists() {
		return Files.exists(_file, LinkOption.NOFOLLOW_LINKS);
	}

	
	@Override
	public String getAbsolutePath() {
		return _file.normalize().toAbsolutePath().toString();
	}

	@Override
	public String getRelativePath() {
		return Paths.get(RCPContentProvider.getCurrentWorkspacePath()).relativize(_file).toString();
	}

	@Override
	public long getSize() {
		long fileSize = 0;
		try {
			fileSize = Files.size(_file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileSize;
	}

	@Override
	public FileTreeElement getParent() {
		return _parent;
	}

	@Override
	public void setParent(FileTreeElement parent) {
		_parent = parent;
	}
	
	@Override
	public List<FileTreeElement> getChildren() {
		return _childElements;
	}

	@Override
	public void save(SaveOperation saveOp) {
		saveOp.execute();
	}
	
	public Path getFile() {
		return _file;
	}

	public void setFile(Path file) {
		_file = file;
	}
	
	@Override
	public String toString() {
		return this.getFile().getFileName().toString();
	}
}

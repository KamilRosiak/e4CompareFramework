package de.tu_bs.cs.isf.e4cf.core.file_structure.components;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations.CreateOperation;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations.DeleteOperation;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations.LoadOperation;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations.MoveOperation;
import de.tu_bs.cs.isf.e4cf.core.file_structure.tree.util.TreeVisitor;

public class Directory extends AbstractFileTreeElement {
	
	public Directory(String path) {
		super(path, null, new ArrayList<FileTreeElement>());
		checkDirectory(Paths.get(path));
	}
	

	public Directory(String path, FileTreeElement parent) {
		super(path, parent, new ArrayList<FileTreeElement>());
		checkDirectory(Paths.get(path));
	}
	
	public Directory(String path, FileTreeElement parent, List<FileTreeElement> childElements) {
		super(path, parent, childElements);
		checkDirectory(Paths.get(path));
	}

	@Override
	public FileTreeElement create(CreateOperation createOp) throws IOException {
		return createOp.execute(this);
	}
	
	@Override
	public FileTreeElement delete(DeleteOperation deleteOp) throws IOException {
		return deleteOp.execute(this);
	}
	
	@Override
	public void move(MoveOperation moveOp) throws IOException {
		moveOp.execute(this);
	}
	
	@Override
	public boolean isDirectory() {
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String getContent(LoadOperation loadOp) {
		// TODO: may even be the list of files inside (recursive)
		throw new  UnsupportedOperationException("A directory does not provide a content.");
	}

	@Override
	public String getExtension() {
		throw new UnsupportedOperationException("A directory does not provide an extension.");
	}

	private void checkDirectory(Path path) {
		if (!isDirectory()) {
			throw new RuntimeException("Underlying file system object is no directory: "+path);
		}
	}


	@Override
	public void accept(TreeVisitor visitor) {
		visitor.visit(this);
	}
}

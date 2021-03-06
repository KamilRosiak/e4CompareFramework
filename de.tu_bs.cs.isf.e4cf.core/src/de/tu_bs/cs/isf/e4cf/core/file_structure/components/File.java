package de.tu_bs.cs.isf.e4cf.core.file_structure.components;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations.CreateOperation;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations.DeleteOperation;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations.LoadOperation;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations.MoveOperation;
import de.tu_bs.cs.isf.e4cf.core.file_structure.tree.util.TreeVisitor;

public class File extends AbstractFileTreeElement {

	public File(String path) {
		super(path, null, new ArrayList<>());
		checkFile(Paths.get(path));
	}

	public File(String path, FileTreeElement parent) {
		super(path, parent, new ArrayList<>());
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
		return false;
	}

	/**
	 * This method returns the file extension of the file. The file extension begins
	 * at the last occurence of a dot (<code>.</code>). If no dot is present, then
	 * an empty string is returned. These are some examples:
	 * <ul>
	 * <li><code>.htaccess</code> returns <code>htaccess</code>;
	 * <li><code>exampleFile</code> returns an empty string;
	 * <li><code>file.ext</code> returns <code>ext</code>;
	 * <li><code>archive.tar.gz</code> returns <code>gz</code>.
	 * </ul>
	 * 
	 * @return File Extension
	 */
	@Override
	public String getExtension() {
		String filename = _file.getFileName().toString();
		String extension = "";
		int extensionBeginIndex = filename.lastIndexOf(".");
		if (extensionBeginIndex >= 0) {
			extension = filename.substring(extensionBeginIndex + 1);
		}
		return extension;
	}

	private void checkFile(Path path) {
		if (!Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS)) {
			throw new RuntimeException("Underlying file system object is no regular file: " + path);
		}
	}

	@Override
	public void accept(TreeVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public <T> T getContent(LoadOperation<T> loadOp) {
		return loadOp.execute(this);
	}

}

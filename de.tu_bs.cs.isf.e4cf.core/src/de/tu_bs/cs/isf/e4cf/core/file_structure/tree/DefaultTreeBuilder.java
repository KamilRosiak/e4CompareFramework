package de.tu_bs.cs.isf.e4cf.core.file_structure.tree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.stream.Stream;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.Directory;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.File;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;

public class DefaultTreeBuilder implements TreeBuilder {

	private int _recoveryAttempts = 0;

	public DefaultTreeBuilder(int recoverAttempts) {
		_recoveryAttempts = recoverAttempts;
	}

	@Override
	public void buildTree(FileTreeElement root) throws IOException {
		IOException e = null;
		boolean treeBuilt = false;
		do {
			try {
				buildTreeHelper(root);
			} catch (IOException ioException) {
				e = ioException;
			}
			treeBuilt = true;
		} while (!treeBuilt && _recoveryAttempts > 0);
		if (e != null)
			throw e;
	}

	public void buildTreeHelper(FileTreeElement root) throws IOException {
		Path rootPath = FileHandlingUtility.getPath(root);
		Stream<Path> fileStream = Files.list(rootPath);

		Iterator<Path> pathIt = fileStream.iterator();
		while (pathIt.hasNext()) {
			Path p = pathIt.next();
			if (FileHandlingUtility.isDirectory(p)) {
				if (isHiddenDirectory(p)) {
					
				} else {
					FileTreeElement dir = new Directory(FileHandlingUtility.getAbsolutePath(p), root);
					buildTree(dir);					
				}
			} else if (FileHandlingUtility.isFile(p)) {
				new File(FileHandlingUtility.getAbsolutePath(p), root);
			} else {
				fileStream.close();
				throw new RuntimeException("Unsupported file: " + FileHandlingUtility.getAbsolutePath(p));
			}
		}
		fileStream.close();
	}

	private boolean isHiddenDirectory(Path p) {
		String filename = p.getFileName().toString();
		if (filename.startsWith(".")) {
			return true;
		}
		return false;
	}
}

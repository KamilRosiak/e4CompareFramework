package de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.AbstractFileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;

public class RenameFile implements MoveOperation {

	String _newName; 
	
	public RenameFile(String newName) {
		_newName = newName;
	}
	
	@Override
	public void execute(FileTreeElement element) throws IOException {
		Path source = FileHandlingUtility.getPath(element);
		Path target = source.getParent().resolve(_newName);
		
		if (!Files.exists(target, LinkOption.NOFOLLOW_LINKS)) {
			((AbstractFileTreeElement) element).setFile(target);
		} else {
			RCPMessageProvider.errorMessage("Rename Operation", "File \""+target+"\" already exists.");
		}
		
	}

}

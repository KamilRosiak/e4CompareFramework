package de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations;

import java.nio.file.Files;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;

public class DeleteFile implements DeleteOperation {

	@Override
	public FileTreeElement execute(FileTreeElement element) {
		if (!Files.exists(FileHandlingUtility.getPath(element))) {
			element.getParent().getChildren().remove(element);
			element.setParent(null);			
		} else {
			element = null;
		}
		
		return element;
	}
}

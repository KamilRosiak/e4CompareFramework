package de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations;

import java.nio.file.Files;
import java.util.Iterator;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.tree.util.LeafFirstTreeIterator;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;

public class DeleteDirectory implements DeleteOperation {
	
	@Override
	public FileTreeElement execute(FileTreeElement element) {
		// needs to give leaves first
		Iterator<FileTreeElement> it = new LeafFirstTreeIterator(element);
		
		// delete leaves first and work the way up
		while (it.hasNext()) {
			FileTreeElement curElement = it.next();	
			
			if (!Files.exists(FileHandlingUtility.getPath(curElement))) {
				curElement.getParent().getChildren().remove(curElement);
				curElement.setParent(null);			
			} else {
				element = null;
			}
		}
		return element;
	}

}

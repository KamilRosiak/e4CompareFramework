package de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

public interface DeleteOperation {
	public FileTreeElement execute(FileTreeElement element);
}
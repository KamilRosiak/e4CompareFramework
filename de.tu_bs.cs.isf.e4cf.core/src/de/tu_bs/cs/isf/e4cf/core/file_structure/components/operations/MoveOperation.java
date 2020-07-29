package de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations;

import java.io.IOException;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

public interface MoveOperation {
	public void execute(FileTreeElement element) throws IOException;
}

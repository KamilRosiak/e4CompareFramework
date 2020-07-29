package de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

public interface LoadOperation<T> {
	public T execute(FileTreeElement element);
}

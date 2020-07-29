package de.tu_bs.cs.isf.e4cf.core.file_structure.tree;

import java.io.IOException;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

public interface TreeBuilder {

	void buildTree(FileTreeElement root) throws IOException;

}

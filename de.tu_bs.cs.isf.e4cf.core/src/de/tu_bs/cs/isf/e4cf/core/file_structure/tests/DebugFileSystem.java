package de.tu_bs.cs.isf.e4cf.core.file_structure.tests;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.WorkspaceFileSystem;
import de.tu_bs.cs.isf.e4cf.core.file_structure.tree.TreeBuilder;

public class DebugFileSystem extends WorkspaceFileSystem {

	public static boolean TestCheck = false;
	
	public DebugFileSystem(TreeBuilder builder) {
		super(builder);
	}
	
	public FileTreeElement getRoot() {
		return _root;
	}

}

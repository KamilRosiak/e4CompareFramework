package de.tu_bs.cs.isf.e4cf.core.file_structure.tree.util;

import de.tu_bs.cs.isf.e4cf.core.file_structure.components.Directory;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.File;

public interface TreeVisitor {
	
	public void visit(File file);
	
	public void visit(Directory directory); 
}

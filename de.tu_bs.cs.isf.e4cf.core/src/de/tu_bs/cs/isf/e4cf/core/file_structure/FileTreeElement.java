package de.tu_bs.cs.isf.e4cf.core.file_structure;

import java.io.IOException;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations.CopyOperation;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations.CreateOperation;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations.DeleteOperation;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations.LoadOperation;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations.MoveOperation;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations.SaveOperation;
import de.tu_bs.cs.isf.e4cf.core.file_structure.tree.util.TreeVisitor;

public interface FileTreeElement {
	
	FileTreeElement delete(DeleteOperation deleteOp) throws IOException;
	FileTreeElement create(CreateOperation createOp) throws IOException;
	void copy(CopyOperation copyOp);
	void move(MoveOperation moveOp) throws IOException;
	void save(SaveOperation saveOp);
	<T> T getContent(LoadOperation<T> loadOp);
	
	public boolean equals(FileTreeElement element);
	public boolean isDirectory();
	public boolean exists();
	
	public String getExtension();
	public String getAbsolutePath();
	public String getRelativePath();
	public long getSize();
	
	FileTreeElement getParent();
	void setParent(FileTreeElement parent);
	List<FileTreeElement> getChildren();
	void accept(TreeVisitor visitor);
	
}

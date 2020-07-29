package de.tu_bs.cs.isf.e4cf.core.file_structure.extensions;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

public interface FileExtension {

	public String getName();
	
	public <T> T loadFile(FileTreeElement element);
	
	public void saveFile(FileTreeElement element);

}

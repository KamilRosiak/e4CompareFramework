package de.tu_bs.cs.isf.e4cf.core.io.interfaces;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

/**
 * Interface for the text to model transformation
 * @author Kamil Rosiak
 */
public interface ArtifactReader {
    	public Tree readArtifact(FileTreeElement element);
    	public String[] getSupportedFiles();

	/**
	 * This method checks if the current fileEnding is supported
	 */
	public default boolean isFileSupported(FileTreeElement element) {
	    return element.isDirectory()? false : isFileSupported(element.getExtension());
	}
	
	public default boolean isFileSupported(String fileExtension) {
		for(String fileEnding : getSupportedFiles()) {
			if(fileEnding.equals(fileExtension)) {
				return true;
			}
		}
		return false;
	}
	
}

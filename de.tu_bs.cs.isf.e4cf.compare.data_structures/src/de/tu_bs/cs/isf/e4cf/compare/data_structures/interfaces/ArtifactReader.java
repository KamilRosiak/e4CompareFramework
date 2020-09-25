package de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces;

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
		for(String fileEnding : getSupportedFiles()) {
			if(fileEnding.equals(element.getExtension())) {
				return true;
			}
		}
		return false;
	}
}

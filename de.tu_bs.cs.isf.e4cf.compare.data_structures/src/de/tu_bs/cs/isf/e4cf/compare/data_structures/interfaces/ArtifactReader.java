package de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

/**
 * Interface for the text to model transformation
 * @author Kamil Rosiak
 */
public interface ArtifactReader {
	
	/**
	 * This method reads the given artifact into the {@link Tree} data structure.
	 * @param element An artifact to read
	 * @return Parsed artifact as a Tree instance
	 */
	public Tree readArtifact(FileTreeElement element);
	
	/**
	 * This method returns all file endings that are supported
	 * @return String array of file endings e.g "txt", "xml", "java", ...
	 */
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

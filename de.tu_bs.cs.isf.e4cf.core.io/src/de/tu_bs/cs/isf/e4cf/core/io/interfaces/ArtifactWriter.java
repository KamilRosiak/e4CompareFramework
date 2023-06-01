package de.tu_bs.cs.isf.e4cf.core.io.interfaces;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

/**
 * The ArtifactWritter servers as transformation back to the originated format.
 * 
 * @author Kamil Rosiak
 *
 */
public interface ArtifactWriter {
    /**
     * This method returns the supported type for the root node , e.g., TEXT,
     * JAVA,CPP
     */
    public String getSuppotedNodeType();

    /**
     * Returns the file ending of the to returned format.
     */
    public String getFileEnding();

    /**
     * This method write the artifact back into his originated format.
     */
    public void writeArtifact(Tree tree, String path);

    /**
     * returns true if file can be written.
     */
    default public boolean isFileSupported(String fileExtension) {
	return getFileEnding().equals(fileExtension) ? true : false;
    };
}

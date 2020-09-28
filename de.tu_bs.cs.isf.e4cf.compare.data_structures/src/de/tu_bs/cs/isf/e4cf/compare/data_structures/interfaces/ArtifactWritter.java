package de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces;


/**
 * The ArtifactWritter servers as transformation back to the originated format. 
 * @author Kamil Rosiak
 *
 */
public interface ArtifactWritter {
	/**
	 * Returns the file ending of the to returned format.
	 */
	public String getFileEnding();
	/**
	 * This method write the artifact back into his originated format.
	 */
	public void writeArtifact(Tree tree);
}

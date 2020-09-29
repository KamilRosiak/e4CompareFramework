package de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces;

import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPDialogService;

/**
 * Abstract class for the creation of an artifact writer. The constructor of the implementation have to be parameterless or injectable.
 *
 */
public abstract class AbstractArtifactWriter implements ArtifactWriter {
	private static final String DIALOG_MSG = "Select Path";
	private String fileEnding;
	public RCPDialogService dialog;
	
	public AbstractArtifactWriter(String fileEnding) {
		setFileEnding(fileEnding);
	}
	
	@Override
	public String getFileEnding() {
		return this.fileEnding;
	}

	public void setFileEnding(String fileEnding) {
		this.fileEnding = fileEnding;
	}
	
	/**
	 * Opens a dialog to choose a location for the storage of the given tree.
	 * @param tree
	 */
	public void witeArtifact(Tree tree) {
		writeArtifact(tree, RCPMessageProvider.getFilePathDialog(DIALOG_MSG, ""));
	}
	
}

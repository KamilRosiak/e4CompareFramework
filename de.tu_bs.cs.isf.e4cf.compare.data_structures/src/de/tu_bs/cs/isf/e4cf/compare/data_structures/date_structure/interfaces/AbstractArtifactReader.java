package de.tu_bs.cs.isf.e4cf.compare.data_structures.date_structure.interfaces;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

public abstract class AbstractArtifactReader implements ArtifactReader {
	private String[] supportedFiles;
	private FileTreeElement element;
	
	public AbstractArtifactReader(String[] supportedFiles , FileTreeElement element) {
		setSupportedFiles(supportedFiles);
		setElement(element);
	}

	@Override
	public String[] getSupportedFiles() {
		return supportedFiles;
	}

	public void setSupportedFiles(String[] supportedFiles) {
		this.supportedFiles = supportedFiles;
	}

	public FileTreeElement getElement() {
		return element;
	}

	public void setElement(FileTreeElement element) {
		this.element = element;
	}

	
}

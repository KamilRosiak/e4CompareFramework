package de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces;

public abstract class AbstractArtifactReader implements ArtifactReader {
	private String[] supportedFiles;

	public AbstractArtifactReader(String[] supportedFiles ) {
		setSupportedFiles(supportedFiles);
	}

	@Override
	public String[] getSupportedFiles() {
		return supportedFiles;
	}

	public void setSupportedFiles(String[] supportedFiles) {
		this.supportedFiles = supportedFiles;
	}


	
}

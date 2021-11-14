package de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;

/**
 * @author developer-olan
 *
 */
public class ArtifactFileDetails {
	private FileTreeElement artifactFileTreeElement;
	private String artifactName;
	private String artifactID;
	private String artifactPath;
	private int artifactLinesOfCode;
	private int numberOfSourceCodeCharactersInArtifact;
	private boolean isDirectory;
	private long totalByteSize = 0;
	private int allSubFiles = 0;
	private int allSubFolders = 0;

	public ArtifactFileDetails(FileTreeElement _artifactFileTreeElement) {
		this.isDirectory = _artifactFileTreeElement.isDirectory();
		this.artifactFileTreeElement = _artifactFileTreeElement;
		this.artifactName = Paths.get(_artifactFileTreeElement.getAbsolutePath()).getFileName().toString();
		this.artifactPath = Paths.get(_artifactFileTreeElement.getAbsolutePath()).toString();
		// Computed Class attributes
		this.artifactLinesOfCode = this.countLinesOfCode(Paths.get(_artifactFileTreeElement.getAbsolutePath()));
		this.numberOfSourceCodeCharactersInArtifact = this
				.countNumberOfCharacters(Paths.get(_artifactFileTreeElement.getAbsolutePath()));
		computeSizeInBytes(this.artifactFileTreeElement);
		computeSubContents(this.artifactFileTreeElement);
	}

	/**
	 * Calculates the lines of Code of a specified Variant or Artifact
	 * 
	 * @param aParsedFile
	 * @return
	 */
	private int countLinesOfCode(Path aParsedFile) {
		int linesOfCode = 0;

		try {
			if (!isDirectory) {
				List<String> codeInLines = Files.readAllLines(Paths.get(aParsedFile.toUri()), StandardCharsets.UTF_8);
				linesOfCode = codeInLines.size();
			} else if(isDirectory) {
				linesOfCode = getNumberOfChildren();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return linesOfCode;
	}

	/**
	 * Calculates and returns the number of characters given a specified Variant or
	 * Artifact
	 * 
	 * @param aParsedFile
	 * @return
	 */
	private int countNumberOfCharacters(Path aParsedFile) {
		int numberOfCharacters = 0;
		try {
			if (!isDirectory) {
				List<String> codeInLines = Files.readAllLines(Paths.get(aParsedFile.toUri()), StandardCharsets.UTF_8);
				codeInLines.forEach(line -> line.replaceAll("\\s+",""));
				numberOfCharacters = FileStreamUtil.readLineByLine(Paths.get(aParsedFile.toUri())).replaceAll("\\s+","").length();
			} else if(isDirectory) {
				numberOfCharacters = getNumberOfChildren();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return numberOfCharacters;
	}
	
	/**
	 * Returns ID of artifact
	 * @return <String> artifactID
	 */
	public String getArtifactID() {
		return this.artifactID;
	}

	/**
	 * Gets the file name of the specified variant
	 * @return
	 */
	public String getArtifactName() {
		return artifactName;
	}

	/**
	 * Returns the path of the specified Variant or Artifact
	 * 
	 * @return
	 */
	public String getArtifactPath() {
		return artifactPath;
	}
	
	/**
	 * 
	 * @return
	 */
	public FileTreeElement getFileTree() {
		return this.artifactFileTreeElement;
	}
	
	/**
	 * Returns the number of lines of code contained in a specific variant
	 * 
	 * @return
	 */
	public int getArtifactLinesOfCode() {
		return artifactLinesOfCode;
	}

	/**
	 * 
	 * Returns the number of characters in a Variant
	 * 
	 * @return
	 */
	public int getVariantSize() {
		if (artifactFileTreeElement.isDirectory()) {
//			return (int)totalByteSize;
			return (int)totalByteSize;

		} else {
			return numberOfSourceCodeCharactersInArtifact;
		}
	}

	/**

	 * Returns the number of children if artifact is a directory
	 * @return
	 */
	public int getNumberOfChildren() {
		int numberOfChildren = 0;
		if (isDirectory) {
			numberOfChildren = (int)artifactFileTreeElement.getSize();
		}
		return numberOfChildren;
	}
	
	
	public int getNumberOfSubFolders() {
		computeSubContents(this.artifactFileTreeElement);
		return this.allSubFolders;
	}
	
	public int getNumberOfSubFiles() {
		getNumberOfSubFolders();
		return this.allSubFiles;
	}
	
	public int getNumberOfCharactersInArtifact() {
		int numberOfCharacters = 0;
		if (!isDirectory) {
			numberOfCharacters = numberOfSourceCodeCharactersInArtifact;
		}
		return numberOfCharacters;
	}
	
	
	/**
	 * Returns the number of children if artifact is a directory
	 * @return
	 */
	private void computeSizeInBytes(FileTreeElement tree) {
		if (tree.isDirectory()) {
			tree.getChildren().stream().forEach(e -> computeSizeInBytes(e));
		} else {
			this.totalByteSize += tree.getSize();
		}
	}
	
	public void computeSubContents(FileTreeElement tree) {
		if (tree.isDirectory()) {
			this.allSubFolders++;
			tree.getChildren().stream().forEach(e -> computeSubContents(e));
		} else {
			this.allSubFiles++;
		}
	}
	
	
	public void setArtifactID(String newID) {
		this.artifactID = newID;
	}

}

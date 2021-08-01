package de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

/**
 * @author developer-olan
 *
 */
public class ArtifactFileDetails {
	private FileTreeElement artifactFileTreeElement;
	private String artifactName;
	private String artifactPath;
	private int artifactLinesOfCode;
	private int numberOfCharactersInArtifact;
	private boolean isDirectory;

	public ArtifactFileDetails(FileTreeElement _artifactFileTreeElement) {
		artifactFileTreeElement = _artifactFileTreeElement;
		artifactName = Paths.get(_artifactFileTreeElement.getAbsolutePath()).getFileName().toString();
		artifactPath = Paths.get(_artifactFileTreeElement.getAbsolutePath()).toString();
		artifactLinesOfCode = this.countLinesOfCode(Paths.get(_artifactFileTreeElement.getAbsolutePath()));
		numberOfCharactersInArtifact = this
				.countNumberOfCharacters(Paths.get(_artifactFileTreeElement.getAbsolutePath()));
		isDirectory = _artifactFileTreeElement.isDirectory();
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
			}
			else {
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
				numberOfCharacters = codeInLines.size();
				for (String aLine : codeInLines) {
					numberOfCharacters += aLine.length();
				}
			} else {
				numberOfCharacters = getNumberOfChildren();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return numberOfCharacters;
	}

	/**
	 * Gets the file name of the specified variant
	 * 
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
	 * Returns the number of lines of code contained in a specific variant
	 * 
	 * @return
	 */
	public int getArtifactLinesOfCode() {
		return artifactLinesOfCode;
	}

	/**
	 * Returns the number of characters in a Variant
	 * 
	 * @return
	 */
	public int getNumberOfCharacters() {
		return numberOfCharactersInArtifact;
	}

	/**
	 * Returns the number of children if artifact is a directory
	 * @return
	 */
	public int getNumberOfChildren() {
		int numberOfChildren = 0;
		if (isDirectory) {
			numberOfChildren = artifactFileTreeElement.getChildren().size();
		}
		return numberOfChildren;
	}

}

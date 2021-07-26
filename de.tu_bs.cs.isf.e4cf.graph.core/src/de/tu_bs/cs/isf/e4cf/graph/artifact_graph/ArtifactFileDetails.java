package de.tu_bs.cs.isf.e4cf.graph.artifact_graph;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author developer-olan
 *
 */
public class ArtifactFileDetails {
	private String artifactName;
	private String artifactPath;
	private int artifactLinesOfCode;
	private int numberOfCharactersInArtifact;

	public ArtifactFileDetails(Path _artifactPath) {
		artifactName = _artifactPath.getFileName().toString();
		artifactPath = _artifactPath.toString();
		artifactLinesOfCode = this.countLinesOfCode(_artifactPath);
		numberOfCharactersInArtifact = this.countNumberOfCharacters(_artifactPath);
	}

	/**
	 * Calculates the lines of Code of a specified Variant or Artifact
	 * @param aParsedFile
	 * @return
	 */
	private int countLinesOfCode(Path aParsedFile) {
		int linesOfCode = 0;

		try {
			List<String> codeInLines = Files.readAllLines(Paths.get(aParsedFile.toUri()), StandardCharsets.UTF_8);
			linesOfCode = codeInLines.size();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return linesOfCode;
	}

	/**
	 * Calculates and returns the number of characters given a specified Variant or Artifact
	 * @param aParsedFile
	 * @return
	 */
	private int countNumberOfCharacters(Path aParsedFile) {
		int numberOfCharacters = 0;

		try {
			List<String> codeInLines = Files.readAllLines(Paths.get(aParsedFile.toUri()), StandardCharsets.UTF_8);
			numberOfCharacters = codeInLines.size();
			for (String aLine: codeInLines) {
				numberOfCharacters += aLine.length();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return numberOfCharacters;
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
	 * @return
	 */
	public String getArtifactPath() {
		return artifactPath;
	}
	
	/**
	 * Returns the number of lines of code contained in a specific variant
	 * @return
	 */
	public int getArtifactLinesOfCode() {
		return artifactLinesOfCode;
	}
	
	/**
	 * Returns the number of characters in a Variant
	 * @return
	 */
	public int getNumberOfCharacters() {
		return numberOfCharactersInArtifact;
	}
}

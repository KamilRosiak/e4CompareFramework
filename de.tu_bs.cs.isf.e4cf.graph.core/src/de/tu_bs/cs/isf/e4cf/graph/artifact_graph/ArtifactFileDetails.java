/**
 * 
 */
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
		artifactLinesOfCode = this.getLinesOfCode(_artifactPath);
		numberOfCharactersInArtifact = this.getNumberOfCharacters(_artifactPath);
	}

	private int getLinesOfCode(Path aParsedFile) {
		int linesOfCode = 0;

		try {
			List<String> codeInLines = Files.readAllLines(Paths.get(aParsedFile.toUri()), StandardCharsets.UTF_8);
			linesOfCode = codeInLines.size();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return linesOfCode;
	}

	private int getNumberOfCharacters(Path aParsedFile) {
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

	public String getArtifactName() {
		return artifactName;
	}
	
	public String getArtifactPath() {
		return artifactPath;
	}
	
	public int getArtifactLinesOfCode() {
		return artifactLinesOfCode;
	}
	
	public int getNumberOfCharacters() {
		return numberOfCharactersInArtifact;
	}
}

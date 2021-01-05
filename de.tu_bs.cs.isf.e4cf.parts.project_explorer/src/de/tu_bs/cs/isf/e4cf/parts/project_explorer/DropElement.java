package de.tu_bs.cs.isf.e4cf.parts.project_explorer;

import java.nio.file.Path;

/**
 * An element that contains the target directory path and the source paths from
 * a drag and drop operation. A DropElement can contain multiple sources.
 */
public class DropElement {
	private Path[] sources;
	private Path target;

	/**
	 * Creates an drop element for a drag-and-drop operation.
	 * 
	 * @param target  the path from the target directory. This represents the 'root'
	 *                directory relative to the sources.
	 * @param sources an array of source paths, that will be copied to the target
	 *                directory.
	 */
	public DropElement(Path target, Path... sources) {
		this.sources = sources;
		this.target = target;
	}

	public Path[] getSources() {
		return sources;
	}

	public Path getTarget() {
		return target;
	}
}

package de.tu_bs.cs.isf.e4cf.parts.project_explorer;

import java.nio.file.Path;

/**
 * An element that contains the target directory path and the source paths from
 * a drag and drop operation. A DropElement can contain multiple sources.
 */
public class DropElement {
	private Path[] sources;
	private Path target;
	private DropMode dropMode;

	/**
	 * Creates an drop element for a drag-and-drop operation.
	 * 
	 * @param dropMode the dropMode for the drop-Operation (how the content is transfered to the target)
	 * @param target  the path from the target directory. This represents the 'root'
	 *                directory relative to the sources.
	 * @param sources an array of source paths, that will be copied to the target
	 *                directory.
	 */
	public DropElement(DropMode dropMode, Path target, Path... sources) {
		this.sources = sources;
		this.target = target;
		this.dropMode = dropMode;
	}

	public Path[] getSources() {
		return sources;
	}

	public Path getTarget() {
		return target;
	}

	public DropMode getDropMode() {
		return dropMode;
	}

	/**
	 * Indicates how the files should be transformed (by copying or moving)
	 */
	public enum DropMode {
		COPY, MOVE;
	}
}

package de.tu_bs.cs.isf.e4cf.text_editor.highlighter;

/**
 * 
 * 
 * Provides a constructor that will be called upon highlighting (currently used
 * for JavaHighlighting and serves as container of a css-class in a specified
 * range).
 *
 * @author Erwin Wijaya, Lukas Cronauer
 *
 */
public class HighlightBlock {
	private final String css;
	private final int startIndex;
	private final int endIndex;

	/**
	 * Constructs a new HighlightingBlock object.
	 * 
	 * @param css        css-class that should be applied to this block
	 * @param startIndex start of the block
	 * @param endIndex   end of the block
	 */
	public HighlightBlock(String css, int startIndex, int endIndex) {
		this.css = css;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	/**
	 * Gets the css-class.
	 * 
	 * @return css-class of this block
	 */
	public String getCss() {
		return css;
	}

	/**
	 * Gets the length of this block.
	 * 
	 * @return length of this block
	 */
	public int getLength() {
		return endIndex - startIndex;
	}

	/**
	 * Gets the start index of the block.
	 * 
	 * @return start index of this block
	 */
	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * Gets the end index of this block.
	 * 
	 * @return end index of this block
	 */
	public int getEndIndex() {
		return endIndex;
	}
}

package de.tu_bs.cs.isf.e4cf.text_editor;

/**
 * Util class to hold a function to count the number of words and lines inside a
 * string
 * 
 * @author Erwin Wijaya, Lukas Cronauer
 */
public class WordCountUtils {

	/**
	 * Counts the Words and Rows in the Textfield when a key is pressed. If the
	 * Textfield is empty there are 0 Words and 0 Rows.
	 *
	 * @return int[] Number of words at index 0, number of rows at index 1
	 * 
	 * @author Soeren Christmann, Cedric Kapalla, Lukas Cronauer
	 */
	public static int[] count(String text) {
		StringBuffer bufferText = new StringBuffer(text);
		int newLineCounter = 1;
		if (text.length() == 0) {
			return new int[] { 0, 0 };
		}
		// check whether there is any text to begin with
		char first = bufferText.charAt(0);
		if (first == ' ' || first == '\t') {
			bufferText.replace(0, 1, "");
		}
		if (first == '\n') {
			bufferText.replace(0, 1, "");
			newLineCounter++;
		}

		// Trims the Tabs.
		for (int i = 0; i < bufferText.length(); i++) {
			if (bufferText.charAt(i) == '\t') {
				bufferText.replace(i, i + 1, " ");
			}
		}
		// trims the Newlines out of the Text and Counts them
		for (int i = 0; i < bufferText.length(); i++) {
			if (bufferText.charAt(i) == '\n') {
				// Number of New Lines = Number of Rows
				newLineCounter++;
				bufferText.replace(i, i + 1, " ");
			}
		}
		String tmp = bufferText.toString();
		tmp = tmp.trim();
		bufferText = new StringBuffer(tmp);
		// trims the additional Spaces
		// Every Space is a new Word
		for (int i = 0; i < bufferText.length(); i++) {
			if (bufferText.charAt(i) == ' ') {
				if (bufferText.charAt(i + 1) == ' ') {
					// Placeholder so only one Space is counted for a new word
					bufferText.replace(i, i + 1, "a");
				}
			}
		}
		// Counts Spaces
		// Number of Spaces = Number of Word
		long countWord = (bufferText.chars().filter(ch -> ch == ' ').count() + 1);
		return new int[] { (int) countWord, newLineCounter };
	}

}

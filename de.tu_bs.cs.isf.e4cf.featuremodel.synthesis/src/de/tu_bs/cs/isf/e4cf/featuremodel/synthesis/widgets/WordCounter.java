package de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.widgets;

public class WordCounter {
	public String word;
	public int count = 1;

	public WordCounter(String word) {
		this.word = word;
	}
	
	public WordCounter(String word, int count) {
		this(word);
		this.count = count;
	}
}

package de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.widgets;

public class WordCounter {
	public String word;
	public double count = 1.0f;

	public WordCounter(String word) {
		this.word = word;
	}
	
	public WordCounter(String word, int count) {
		this(word);
		this.count = count;
	}
}

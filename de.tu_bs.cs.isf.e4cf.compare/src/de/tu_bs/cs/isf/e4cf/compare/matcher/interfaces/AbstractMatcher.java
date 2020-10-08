package de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces;

/**
 * Template class for the implementation of an matching approach. The concrete implementation must have a parameterless constructor and is created by the framework.
 * @author Kamil Rosiak
 *
 */
public abstract class AbstractMatcher implements Matcher {
	private String matcherName;
	private String matcherDescription;
	
	public AbstractMatcher(String matcherName, String matcherDescription) {
		setMatcherName(matcherName);
		setMatcherDescription(matcherDescription);
	}

	@Override
	public String getMatcherName() {
		return this.matcherName;
	}

	@Override
	public String getMatcherDescription() {
		return this.matcherDescription;
	}

	public void setMatcherName(String matcherName) {
		this.matcherName = matcherName;
	}

	public void setMatcherDescription(String matcherDescription) {
		this.matcherDescription = matcherDescription;
	}
	
	@Override
	public String toString() {
	    return matcherName;
	}

}

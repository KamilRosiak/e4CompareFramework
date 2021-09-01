/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.core.compare.parts.taxonomy_control_view.data_structures;

/**
 * @author developer-olan
 *
 */
public class TaxonomyControlSettings {
	private boolean sourceLevelComparison;
	private boolean levenshteinMode;
	private boolean languageJava;
	private boolean languageCplusplus;
	private boolean dirNameMetric;
	private boolean dirSizeMetric;
	private boolean dirNumNonSourceMetric;
	private boolean dirNumSourceMetric;
	private boolean classConstructMode;
	private boolean methodConstructMode;
	private boolean otherConstructMode;

	
	public TaxonomyControlSettings() {
		this.sourceLevelComparison = true;
		this.levenshteinMode = false;
		this.languageJava = true;
	}
	
	// Setters
	public void setSourceLevelComparison(boolean value) {
		this.sourceLevelComparison = value;
	}
	
	public void setLevenshteinMode(boolean value) {
		this.levenshteinMode = value;
	}
	
	public void setLanguageJava(boolean value) {
		this.languageJava = value;
	}
	
	public void setLanguageCplusplus(boolean value) {
		this.languageCplusplus = value;
	}
	
	public void setDirNameMetric(boolean value) {
		this.dirNameMetric = value;
	}
	
	public void setDirSizeMetric(boolean value) {
		this.dirSizeMetric = value;
	}
	
	public void setDirNumNonSourceMetric(boolean value) {
		this.dirNumNonSourceMetric = value;
	}
	
	public void setDirNumSourceMetric(boolean value) {
		this.dirNumSourceMetric = value;
	}
	
	public void setClassConstructMode(boolean value) {
		this.classConstructMode = value;
	}
	
	public void setMethodConstructMode(boolean value) {
		this.methodConstructMode = value;
	}
	
	public void setOtherConstructMode(boolean value) {
		this.otherConstructMode = value;
	}
	
	// Getters
	public boolean getSourceLevelComparison() {
		return this.sourceLevelComparison;
	}
	
	public boolean getLevenshteinMode() {
		return this.levenshteinMode;
	}
	
	public boolean getLanguageJava() {
		return this.languageJava;
	}
	
	public boolean getLanguageCplusplus() {
		return this.languageCplusplus;
	}
	
	public boolean getDirNameMetric() {
		return this.dirNameMetric;
	}
	
	public boolean getDirSizeMetric() {
		return this.dirSizeMetric;
	}
	
	public boolean getDirNumNonSourceMetric() {
		return this.dirNumNonSourceMetric;
	}
	
	public boolean getDirNumSourceMetric() {
		return this.dirNumSourceMetric;
	}
	
	public boolean getClassConstructMode() {
		return this.classConstructMode;
	}
	
	public boolean getMethodConstructMode() {
		return this.methodConstructMode;
	}
	
	public boolean getOtherConstructMode() {
		return this.otherConstructMode;
	}
	
	
}

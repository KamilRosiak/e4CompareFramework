/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.core.compare.parts.taxonomy_control_view.data_structures;

/**
 * @author developer-olan
 *
 */
public class TaxonomySettings {
	private boolean sourceLevelComparison;
	private boolean isTaxonomyATree;
	private boolean levenshteinMode;
	private boolean languageJava;
	private boolean languageCplusplus;
	private boolean dirNameMetric;
	private boolean dirSizeMetric;
	private boolean dirSourceDiffMetric;
	private boolean dirNonSourceDiffMetric;
	private boolean classConstructMode;
	private boolean methodConstructMode;
	private boolean otherConstructMode;

	
	public TaxonomySettings() {
		this.sourceLevelComparison = false;
		this.levenshteinMode = true;
		this.languageJava = true;
		this.dirNameMetric = true;
	}
	
	// Setters
	public void setSourceLevelComparison(boolean value) {
		this.sourceLevelComparison = value;
	}
	
	public void setTaxonomyTypeAsTree(boolean value) {
		this.isTaxonomyATree = value;
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
		this.dirNonSourceDiffMetric = value;
	}
	
	public void setDirNumSourceMetric(boolean value) {
		this.dirSourceDiffMetric = value;
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
	
	public boolean getTaxonomyTypeAsTree() {
		return this.isTaxonomyATree;
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
		return this.dirNonSourceDiffMetric;
	}
	
	public boolean getDirNumSourceMetric() {
		return this.dirSourceDiffMetric;
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

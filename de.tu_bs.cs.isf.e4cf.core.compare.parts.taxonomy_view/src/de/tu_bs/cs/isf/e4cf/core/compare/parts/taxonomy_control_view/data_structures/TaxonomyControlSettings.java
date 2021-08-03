/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.core.compare.parts.taxonomy_control_view.data_structures;

/**
 * @author developer-olan
 *
 */
public class TaxonomyControlSettings {
	private boolean asymetricMode;
	
	public TaxonomyControlSettings() {
		this.asymetricMode = true;
	}
	
	public boolean getAsymmetryMode() {
		return asymetricMode;
	}
	
	public void setAsymmetryMode(boolean value) {
		this.asymetricMode = value;
	}
}

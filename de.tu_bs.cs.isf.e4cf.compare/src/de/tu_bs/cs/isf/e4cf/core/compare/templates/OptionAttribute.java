package de.tu_bs.cs.isf.e4cf.core.compare.templates;

import java.io.Serializable;

import de.tu_bs.cs.isf.e4cf.core.compare.interfaces.IWeighted;

/**
 * This represents the option weight attribute to weight the options.
 * @author {Kamil Rosiak}
 *
 */
public class OptionAttribute implements IWeighted , Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4642422614189786244L;
	private float _weigth = 0.0f;
		
	@Override
	public float getWeight() {
		return _weigth;
	}

	@Override
	public void setWeight(float weight) {
		_weigth = weight;
	}
}

package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.components;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.ArtefactFilter;

/**
 *  Filter that accepts all objects.
 *  
 * @author Oliver Urbaniak
 *
 */
public class DefaultArtefactFilter implements ArtefactFilter {

	@Override
	public Boolean apply(Object t) {
		return true;
	}

}

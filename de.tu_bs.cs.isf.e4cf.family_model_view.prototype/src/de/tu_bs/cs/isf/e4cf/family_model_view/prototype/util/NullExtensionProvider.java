package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.util;

import org.eclipse.emf.ecore.EClass;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.ExtensionProvider;

public class NullExtensionProvider implements ExtensionProvider {

	@Override
	public String getExtension(EClass eclass) {
		return null;
	}

}

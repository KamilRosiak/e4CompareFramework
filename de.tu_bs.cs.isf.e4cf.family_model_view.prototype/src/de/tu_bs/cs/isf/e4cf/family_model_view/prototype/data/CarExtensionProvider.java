package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.data;

import org.eclipse.emf.ecore.EClass;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.ExtensionProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewStrings;

public class CarExtensionProvider implements ExtensionProvider {

	@Override
	public String getExtension(EClass eclass) {
		return FamilyModelViewStrings.TEST_CAR_FILE_EXTENSION;
	}

}

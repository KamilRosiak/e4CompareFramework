package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.components;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.ExtensionProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewStrings;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelPackage;

public class DefaultFamilyModelExtensionProvider implements ExtensionProvider {

	@Override
	public String getExtension(EClass eclass) {
		if (EcoreUtil.equals(eclass, FamilyModelPackage.eINSTANCE.getFamilyModel())) {
			return FamilyModelViewStrings.FM_DEFAULT_FILE_EXT;
		}
		
		return null;
	}
	
}

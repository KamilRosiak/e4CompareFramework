package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.dialog;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

public class SaveFamilyModelDialog extends AbstractResourceRowDialog {

	public SaveFamilyModelDialog(String title, double width, double rowHeight) {
		super(title, width, rowHeight);
	}

	@Override
	public void finish(Map<String, String> resourceMap) {
		// nothing to do here
	}
	
	public String getResourcePath(EObject eobject) {
		Resource res = eobject.eResource();
		if (res != null) {
			return res.getURI().toFileString();
		} else {
			return "";
		}
	}

}

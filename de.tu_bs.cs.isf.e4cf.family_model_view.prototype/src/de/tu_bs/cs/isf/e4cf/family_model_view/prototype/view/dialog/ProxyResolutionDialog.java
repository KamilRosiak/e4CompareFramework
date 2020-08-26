package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.dialog;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;

public class ProxyResolutionDialog extends AbstractResourceRowDialog {

	public ProxyResolutionDialog(String title, double width, double rowHeight) {
		super(title, width, rowHeight);
	}

	@Override
	public void finish(Map<String, String> resourceMap) {
		boolean validResources = true;
		for (Entry<String, String> resEntry : resourceMap.entrySet()) {
			if (resEntry.getKey() != null && !resEntry.getKey().isEmpty()) {
				File resourceFile = new File(resEntry.getValue());
				validResources &= resourceFile.exists();
			} else {
				validResources = false;
			}
		}
		
		if (!validResources) {
			RCPMessageProvider.errorMessage("Proxy Resolution", "There were problems while selecting a resource");
		}
	}
	
}

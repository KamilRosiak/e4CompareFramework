package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.persistence;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;

import de.tu_bs.cs.isf.e4cf.core.file_structure.util.Pair;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.dialog.ProxyResolutionDialog;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.dialog.AbstractResourceRowDialog.ResourceEntry;
import javafx.stage.FileChooser;

/**
 * Delegates proxy resolution to the user by opening a dialog. 
 * May change the segment of the proxy URI. The fragment part stays the same. 
 * 
 * @author Oliver Urbaniak
 */
public class ManualResolver implements ProxyResolver {
	
	/**
	 * Stores the mappings of proxy URIs to their moved locations.
	 * The cache stores fragment-less URIs as we only need the locations of resources; 
	 */
	private Map<URI, URI> uriCache = new HashMap<>();

	private URI candidateURI;
	
	/**
	 * Resolves proxy URIs by prompting the user to select the mapping for a proxy URI.
	 * Every manual resource mapping is cached. The user only gets prompted if the path of the URI hasn't been encountered before.
	 * 
	 * @param proxyUri An file URI 
	 * @return
	 */
	@Override
	public URI resolve(URI proxyUri) {
		// lookup in cache
		URI targetUri = uriCache.get(proxyUri.trimFragment());
		if (targetUri != null) {
			return targetUri.appendFragment(proxyUri.fragment());
		}

		// create the user dialog
		ProxyResolutionDialog dialog = new ProxyResolutionDialog("Proxy Resolution", 1024.0, 30.0);
		
		// create a dialog resource row description
		String proxyResourceId = "proxy";
		ResourceEntry resEntry = new ResourceEntry();
		resEntry.setId(proxyResourceId);
		resEntry.setLabel("Select the resource");
		if (candidateURI != null && !candidateURI.segmentsList().isEmpty()) {
			resEntry.setResource(candidateURI.toFileString());			
		} else {
			resEntry.setResource("");
		}
		resEntry.setButtonLabel("Choose file ...");
		resEntry.setResourceSetter(oldEntry -> {
			FileChooser fc = new FileChooser();
			
			File dir = new File(RCPContentProvider.getCurrentWorkspacePath());
			fc.setInitialDirectory(dir);
			fc.setTitle("Choose the correct resource");
			File selectedFile = fc.showOpenDialog(dialog.getStage());
			if (selectedFile != null) {
				return new Pair<>(resEntry.getId(), selectedFile.toString());
			} 
			
			RCPMessageProvider.errorMessage("Proxy Resolution", "The selected file was null.");
			return null;
		});
		
		
		dialog.buildDialog()
			.buildInfoText("The family model referenced a missing resource path. \n"
				+ "- "+proxyUri.toFileString()+"\n"
				+ "It was either moved, renamed or deleted. "
				+ "The path below is a candidate for the missing resource. "
				+ "Please inspect and if necessary correct the path to an existing file path.")
			.buildSeparator()
			.buildResourceEntry(resEntry)
			.open();
		
		String fileString = dialog.getResources().get(proxyResourceId);		
		if (fileString != null && !fileString.isEmpty()) {
			File file = new File(fileString);
			if (file.exists()) {
				targetUri = URI.createFileURI(fileString);
				uriCache.put(proxyUri.trimFragment(), targetUri.trimFragment());
				
				return targetUri.appendFragment(proxyUri.fragment());				
			}
		}
		
//		RCPMessageProvider.errorMessage("Proxy Resolution", "Invalid resource selected for resolution.");
		
		return null;
	}

	public URI getCandidateURI() {
		return candidateURI;
	}

	public void setCandidateURI(URI candidateURI) {
		this.candidateURI = candidateURI;
	}
	
	
}

package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.persistence;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.ExtensionProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant;

public class EMFResourceSetManager implements IResourceManager {

	protected static final String DEFAULT_FM_DIR = RCPContentProvider.getCurrentWorkspacePath();
	protected static final String DEFAULT_VARIANT_DIR = "";
	
	protected final boolean DEMAND_LOAD = false;
	
	protected ResourceSet resourceSet;
	
	private ExtensionProvider artifactExtProvider;
	private ExtensionProvider fmExtProvider;
	
	public EMFResourceSetManager(ExtensionProvider artifactExtProvider, ExtensionProvider fmExtProvider) {
		this.artifactExtProvider = artifactExtProvider;
		this.fmExtProvider = fmExtProvider;
	}
	
	private Resource addResource(URI uri) {
		return resourceSet.createResource(uri);
	}
	
	private Resource removeResources(URI uri) {
		for (Iterator<Resource> iterator = resourceSet.getResources().iterator(); iterator.hasNext();) {
			Resource res = (Resource) iterator.next();
			if (res.getURI().toString().equals(uri.toString())) {
				iterator.remove();
				return res;
			}
		}
		return null;
	}

	private Resource getResource(URI uri) {
		return resourceSet.getResource(uri, DEMAND_LOAD);
	}

	public void initializeResourceSet(Set<String> extensionSet) {
		resourceSet = new ResourceSetImpl();
		
		Map<String, Object> extensionToFactoryMap = new HashMap<>();
		for (String extension : extensionSet) {
			extensionToFactoryMap.put(extension, new XMIResourceFactoryImpl());
		}
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().putAll(extensionToFactoryMap);
	}

	@Override
	public URI toUri(Object obj) {
		if (obj instanceof String) {
			return URI.createFileURI((String) obj);			
		}
		return null;
	}

	/**
	 * Saves the family model under the designated URI. The family model is saved together with the referenced variants.
	 * The URI parameter represents a directory under which the contents are stored.<br>
	 * Variants are stored regardless of whether they are already contained in a resource or not.
	 * 
	 * @param familyModel The family model which will be saved
	 * @param uri The URI of the directory which will contain the serialized family model and referenced variants
	 */
	@Override
	public void save(FamilyModel familyModel, URI uri) throws IOException {
		// collect the necessary extensions
		Set<String> extensions = new HashSet<>();
		
		String fmExtension = fmExtProvider.getExtension(familyModel.eClass());
		extensions.add(fmExtension);
		
		for (Variant variant : familyModel.getVariants()) {
			String artifactExtension = artifactExtProvider.getExtension(variant.getInstance().eClass());			
			extensions.add(artifactExtension);
		}
		
		// create the resource set holding all referenced objects
		initializeResourceSet(extensions);

		// add referenced objects as resources
		URI fmUri = uri.appendSegment(familyModel.getName());
		fmUri = fmUri.appendFileExtension(fmExtension);
		addResource(fmUri).getContents().add(familyModel);
		
		URI baseVariantUri = uri.appendSegment(familyModel.getName()+"_variants");
		for (Variant variant : familyModel.getVariants()) {
			String artifactExtension = artifactExtProvider.getExtension(variant.getInstance().eClass());
			
			URI variantUri = baseVariantUri.appendSegment(variant.getIdentifier());
			variantUri = variantUri.appendFileExtension(artifactExtension);
			
			addResource(variantUri).getContents().add(variant.getInstance());
		}
		
//		--> TODO: check if the code is necessary. Handles the case in which the variant is already contained in a resource and does not store it twice.
//		for (Variant variant : familyModel.getVariants()) {
//			// 
//			Resource variantResource = variant.getInstance().eResource();
//			if (variantResource != null) {
//				resourceManager.addResource(variantResource);
//			} else {
//				// check the map for an URI
//				URI uri = variantMapping.get(variant);
//				Resource res = resourceManager.addResource(uri);
//				res.getContents().add(variant.getInstance());
//			}
//		}
		
		// save resource set under designated URIs
		for (Resource res : resourceSet.getResources()) {
			res.save(Collections.emptyMap());
		}
	}

	@Override
	public FamilyModel load(URI uri) throws IOException {
		if (!uri.isFile()) {
			return null;
		}
		
		Set<String> extensions = new HashSet<>();
		extensions.add(uri.fileExtension());
		initializeResourceSet(extensions);
		
		
		Resource res = resourceSet.createResource(uri);
		res.load(Collections.emptyMap());
		
		EObject eobject = res.getContents().get(0);
		
		return (FamilyModel) eobject;
	}
}

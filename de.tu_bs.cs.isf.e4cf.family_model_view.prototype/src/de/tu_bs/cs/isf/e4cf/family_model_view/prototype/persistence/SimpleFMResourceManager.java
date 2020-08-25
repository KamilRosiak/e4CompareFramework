package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.UnresolvedProxyCrossReferencer;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.ExtensionProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant;

public class SimpleFMResourceManager implements IResourceManager {

	protected static final String DEFAULT_FM_DIR = RCPContentProvider.getCurrentWorkspacePath();
	protected static final String DEFAULT_VARIANT_DIR = "";
	
	protected final boolean DEMAND_LOAD = false;
	
	protected ResourceSet resourceSet;
	
	protected ExtensionProvider artefactExtProvider;
	protected ExtensionProvider fmExtProvider;
	
	public SimpleFMResourceManager(ExtensionProvider artefactExtProvider, ExtensionProvider fmExtProvider) {
		this.artefactExtProvider = artefactExtProvider;
		this.fmExtProvider = fmExtProvider;
	}
	
	protected Resource removeResources(URI uri) {
		for (Iterator<Resource> iterator = resourceSet.getResources().iterator(); iterator.hasNext();) {
			Resource res = (Resource) iterator.next();
			if (res.getURI().toString().equals(uri.toString())) {
				iterator.remove();
				return res;
			}
		}
		return null;
	}

	protected Resource getResource(URI uri) {
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
		} else if (obj instanceof File) {
			return URI.createFileURI(((File) obj).getAbsolutePath());
		}
		return null;
	}

	/**
	 * Saves the family model under the designated URI. The family model is saved together with the referenced variants 
	 * according to the URI mapping provided.
	 * Only EObjects with an URI are stored. They are either mapped by <tt>URIMapping</tt> or are already contained in a resource.
	 */
	@Override
	public boolean save(FamilyModel familyModel, Map<EObject, URI> URIMapping) throws IOException {
		boolean isSaved = true;
		
		// collect the necessary extensions
		Set<String> extensions = new HashSet<>();
		
		String fmExtension = fmExtProvider.getExtension(familyModel.eClass());
		extensions.add(fmExtension);
		
		for (Variant variant : familyModel.getVariants()) {
			String artifactExtension = artefactExtProvider.getExtension(variant.getInstance().eClass());			
			extensions.add(artifactExtension);
		}
		
		// create the resource set holding all referenced objects
		initializeResourceSet(extensions);

		// add referenced objects as resources
		URI fmURI = URIMapping.get(familyModel);
		if (fmURI == null) {
			isSaved = false;
		}
		resourceSet.createResource(fmURI).getContents().add(familyModel);
		
		for (Variant variant : familyModel.getVariants()) {
			URI variantURI = URIMapping.get(variant);
			if (variantURI != null) {
				resourceSet.createResource(variantURI).getContents().add(variant.getInstance());
			} else {				
				// try to reuse its resource if there is one
				if (variant.getInstance().eResource() != null) {
					resourceSet.getResources().add(variant.eResource());
				} else {
					isSaved = false;
				}
			}
		}
				
		// save resource set under designated URIs
		for (Resource res : resourceSet.getResources()) {
			res.save(Collections.emptyMap());
		}	
		
		return isSaved;
	}

	@Override
	public FamilyModel load(URI fmURI, URI... lookupUris) throws IOException {
		if (!fmURI.isFile()) {
			return null;
		}
		
		Set<String> extensions = new HashSet<>();
		extensions.add(fmURI.fileExtension());
		initializeResourceSet(extensions);
		
		Resource res = resourceSet.createResource(fmURI);
		res.load(Collections.emptyMap());
		
		EObject eobject = res.getContents().get(0);
		
		// TODO: check if all references are resolved
		
		FamilyModel fm = (FamilyModel) eobject;
		
		// Repair unresolved proxies
		ProxyResolver movedResResolver = new MovedResourceResolver(lookupUris);
		for (Entry<EObject, Collection<Setting>> proxyEntry : UnresolvedProxyCrossReferencer.find(fm).entrySet()) {
			EObject proxy = proxyEntry.getKey();
			URI proxyUri = EcoreUtil.getURI(proxy);
			
			// 1. Try to track the unresolved URIs by using the lookup URIs
			// - assumption: resource was moved to a different location
			URI targetUri = movedResResolver.resolve(proxyUri);
			if (targetUri != null) {
				((InternalEObject) proxy).eSetProxyURI(targetUri);				
			}
			
			
			// 2. Ask the user to specify a resource path for the unresolved URIs
			// - assumption: user renamed (and moved) the files
			
		}
		
		return (FamilyModel) eobject;	
	}
}

package de.tu_bs.cs.isf.e4cf.core.util.emf;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

public class EMFResourceSetManager implements IResourceManager {

	protected final boolean DEMAND_LOAD = false;
	
	protected ResourceSet resourceSet;

	protected List<String> extensions;
	
	public EMFResourceSetManager(List<String> extensions) { 
		initialize(extensions);
		this.extensions = extensions;
	}
	
	@Override
	public Resource addResource(URI uri) {
		return resourceSet.createResource(uri);
	}

	@Override
	public Resource addResource(Resource resource) {
		resourceSet.getResources().add(resource);
		
		return resource;
	}
	
	@Override
	public Resource removeResource(URI uri) {
		for (Iterator<Resource> iterator = resourceSet.getResources().iterator(); iterator.hasNext();) {
			Resource res = (Resource) iterator.next();
			if (res.getURI().toString().equals(uri.toString())) {
				iterator.remove();
				return res;
			}
		}
		return null;
	}
	
	@Override
	public void removeAllResources() {
		resourceSet.getResources().clear();
	}

	@Override
	public Resource getResource(URI uri) {
		return resourceSet.getResource(uri, DEMAND_LOAD);
	}

	@Override
	public void initialize(List<String> extensions) {
		resourceSet = new ResourceSetImpl();
		
		Map<String, Object> extensionToFactoryMap = new HashMap<>();
		for (String extension : extensions) {
			extensionToFactoryMap.put(extension, new XMIResourceFactoryImpl());
		}
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().putAll(extensionToFactoryMap);
		
		resourceSet.getResourceFactoryRegistry();
	}

	@Override
	public void save() throws IOException {
		for (Resource res : resourceSet.getResources()) {
			res.save(Collections.emptyMap());
		}
	}

	@Override
	public void load() throws IOException {
		for (Resource res : resourceSet.getResources()) {
			res.load(Collections.emptyMap());
		}
	}

	@Override
	public List<String> getExtensions() {
		return extensions;
	}
}

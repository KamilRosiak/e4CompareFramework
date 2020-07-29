package de.tu_bs.cs.isf.e4cf.core.util.emf;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

public interface IResourceManager {
	
	public Resource addResource(URI uri);
	
	public Resource addResource(Resource resource);
	
	public Resource removeResource(URI uri);
	
	void removeAllResources();

	public Resource getResource(URI uri);
	
	void initialize(List<String> extensions);
	
	public void save() throws IOException;
	
	public void load() throws IOException;

	public List<String> getExtensions();
}

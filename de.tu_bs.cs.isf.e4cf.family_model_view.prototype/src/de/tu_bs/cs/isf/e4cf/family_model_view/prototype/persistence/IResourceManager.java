package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.persistence;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

public interface IResourceManager {
	
	public Resource addResource(URI uri);
	
	public Resource addResource(Resource resource);
	
	public Resource removeResources(URI uri);
	
	public Resource getResource(URI uri);
	
	void initialize(List<String> extensions);
	
	public void save() throws IOException;
	
	public void load() throws IOException;

	public List<String> getExtensions();
	
}

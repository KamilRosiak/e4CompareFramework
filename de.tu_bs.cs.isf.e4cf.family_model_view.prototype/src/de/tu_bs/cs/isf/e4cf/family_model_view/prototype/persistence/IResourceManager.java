package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.persistence;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel;

/**
 * Provides save and load functionality together with uri conversion.
 * All family model (de-)serialization should be done through this interface. 
 * 
 * @author Oliver Urbaniak
 *
 */
public interface IResourceManager {
	
	/**
	 * Convenience method for translating objects to URIs so that they can be processeds.
	 * 
	 * @param obj
	 * @return
	 */
	public URI toUri(Object obj);
	
	/**
	 * 
	 * 
	 * @param familyModel The family model which is saved
	 * @param uri The URI denoting the container for the family model and referenced objects
	 * @throws IOException
	 */
	public void save(FamilyModel familyModel, URI uri) throws IOException;
	
	/**
	 * 
	 * 
	 * @param uri The URI of the family model file
	 * @return The family model ecore instance
	 * @throws IOException
	 */
	public FamilyModel load(URI uri) throws IOException;
	
}

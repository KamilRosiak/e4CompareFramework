package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.persistence;

import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

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
	 * Saves the family model and its variants.
	 * The mapping specifies a desired save location for the family model and the containing variants.
	 * However, the specific save location is ultimately choosen by the implementation.  
	 * 
	 * @param familyModel The family model which is saved
	 * @param uriMapping The URI denoting the container for the family model and referenced objects
	 * @return <tt>true</tt> if the family model and all of its variants are contained and stored in a resource
	 * @throws IOException
	 */
	public boolean save(FamilyModel familyModel, Map<EObject, URI> uriMapping) throws IOException;
	
	/**
	 * Loads the family model. The variant references in the family model are resolved. 
	 * 
	 * @param fmURI The URI of the family model file
	 * @param lookupURIs The URIs used for lookup if the variant references are invalid 
	 * @return The family model ecore instance
	 * @throws IOException
	 */
	public FamilyModel load(URI fmURI, URI... lookupURIs) throws IOException;
	
}

package de.tu_bs.cs.isf.e4cf.family_model_view.prototype;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import de.tu_bs.cs.isf.e4cf.core.util.emf.IResourceManager;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant;

/**
 * Wrapper for {@link FamilyModel} that provides easier model access and manipulation.
 * 
 * @author Oliver Urbaniak
 *
 */
public class GenericFamilyModel {
	
	protected final static URI NO_URI = null;
	
	protected FamilyModel familyModel;
	
	protected IResourceManager resourceManager;
		
	protected Map<Variant, URI> variantMapping;
	
	public GenericFamilyModel(FamilyModel familyModel, IResourceManager resourceManager) {
		this(resourceManager);
		setInternalFamilyModel(familyModel);
	}
	
	public GenericFamilyModel(IResourceManager resourceManager) {
		this.resourceManager = resourceManager;
		this.variantMapping = new HashMap<Variant, URI>();
	}
	
	public void addVariantMapping(Variant variant, URI uri) {
		variantMapping.put(variant, uri);
	}
	
	public URI removeVariantMapping(Variant variant) {
		return variantMapping.remove(variant);
	}
	
	public void clearVariantMapping() {
		variantMapping.clear();
	}
	
	/**
	 * Assumes variants of the family model to be independent from each other meaning there's no cross-references between them.
	 * In order to save a family model including its references, we consider two cases: 
	 * <ul>
	 * 		<li> The variants are attached to a valid resource.
	 * 		<li> The variants are not attached to a resource.
	 * </ul>
	 * 
	 * @param path the file system path of the family model
	 * @throws IOException 
	 */
	public void save(String path) throws IOException {
		for (Variant variant : familyModel.getVariants()) {
			// 
			Resource variantResource = variant.getInstance().eResource();
			if (variantResource != null) {
				resourceManager.addResource(variantResource);
			} else {
				// check the map for an URI
				URI uri = variantMapping.get(variant);
				Resource res = resourceManager.addResource(uri);
				res.getContents().add(variant.getInstance());
			}
		}
		
		URI fmUri = URI.createFileURI(path);
		Resource fmResource = resourceManager.addResource(fmUri);
		fmResource.getContents().add(familyModel);
		
		resourceManager.save();
	}
	
	public void load(String path) throws IOException {
		URI fmUri = URI.createFileURI(path);
		Resource fmResource = resourceManager.addResource(fmUri);
		
		resourceManager.load();
		
		EObject loadedObject = fmResource.getContents().get(0);
		if (!(loadedObject instanceof FamilyModel)) {
			throw new RuntimeException("The path contained no valid ecore description for the family model");
		}
		
		familyModel = (FamilyModel) loadedObject;
	}
	
	/**
	 * Defines the root path that is combined with all the variants URI upon saving the model.
	 * 
	 * @param rootPath the prefixed path for all the variants paths
	 */
	public void setVariantRootPath(String rootPath) {
		throw new RuntimeException("Not implemented yet.");
	}
	
	/**
	 * Returns the variants involved in the family model.
	 * 
	 * @return list of variants
	 * 
	 * @see FamilyModel#getVariants()
	 */
	public List<Variant> getVariants() {
		return familyModel != null ? familyModel.getVariants() : Collections.emptyList();
	}
	
	public void setExtensions(List<String> extensions) {
		resourceManager.initialize(extensions);
	}
	
	public List<String> getExtensions() {
		return resourceManager.getExtensions();
	}

	public void setInternalFamilyModel(FamilyModel familyModel) {
		if (this.familyModel != familyModel) {
			this.familyModel = familyModel;
			variantMapping.clear();
		}
		
	}
	
	public FamilyModel getInternalFamilyModel() {
		return familyModel;
	}
}

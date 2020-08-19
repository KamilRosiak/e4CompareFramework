package de.tu_bs.cs.isf.e4cf.family_model_view.prototype;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.persistence.IResourceManager;

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
			
	public GenericFamilyModel(FamilyModel familyModel, IResourceManager resourceManager) {
		this(resourceManager);
		setInternalFamilyModel(familyModel);
	}
	
	public GenericFamilyModel(IResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}
	
	/**
	 * Assumes variants of the family model to be independent from each other meaning there's no cross-references between them.
	 * In order to save a family model including its references.
	 * 
	 * @param path The path of the directory which will contain the family model and referenced objects
	 * @throws IOException 
	 */
	public void save(String path) throws IOException {
		URI fmUri = resourceManager.toUri(path);
		resourceManager.save(familyModel, fmUri);
	}
	
	public void load(String path) throws IOException {
		URI fmUri = resourceManager.toUri(path);
		FamilyModel fm = resourceManager.load(fmUri);
		if (fm != null) {
			familyModel = fm;
		} else {
			throw new NullPointerException("Resource manager returned null object.");
		}
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

	public void setInternalFamilyModel(FamilyModel familyModel) {
		if (this.familyModel != familyModel) {
			this.familyModel = familyModel;
		}
	}
	
	public FamilyModel getInternalFamilyModel() {
		return familyModel;
	}
}

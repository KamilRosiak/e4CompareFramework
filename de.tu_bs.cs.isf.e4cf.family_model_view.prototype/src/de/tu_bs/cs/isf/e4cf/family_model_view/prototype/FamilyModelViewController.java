 
package de.tu_bs.cs.isf.e4cf.family_model_view.prototype;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelFactory;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelPackage;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariabilityCategory;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.persistence.EMFResourceSetManager;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.persistence.IResourceManager;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.plugin.FamilyModelViewPlugin;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewEvents;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewStrings;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.tests.CarExampleBuilder;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.util.EmptyIconProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.util.FamilyModelTransformation;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.util.NullExtensionProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.util.NullLabelProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.FXTreeBuilder;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.FamilyModelView;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.elements.FXVariantResourceDialog;

public class FamilyModelViewController {
		
	private ServiceContainer services;
	
	private GenericFamilyModel familyModelWrapper;
	private FamilyModelView familyModelView;
	
	private LabelProvider fmvLabelProvider;
	private ExtensionProvider fmvExtensionProvider;
	private IconProvider fmvIconProvider;
	
	private LabelProvider artefactLabelProvider;
	private ExtensionProvider artefactExtensionProvider;
	private IconProvider artefactIconProvider;
	
	private List<FamilyModelTransformation> fmTransformations;

	private MPart part;
	
	@Inject
	public FamilyModelViewController(ServiceContainer services) {
		setServices(services);
		this.part = services.partService.getPart(FamilyModelViewStrings.PART_NAME);
	
		// initialize all providers from given extensions
		collectProviderExtensions();
		
		// initialize the family model dummy
		FamilyModel fm = CarExampleBuilder.createCarFamilyModel();
		GenericFamilyModel genericFamilyModel = new GenericFamilyModel(
				new EMFResourceSetManager(
					(eclass) -> FamilyModelViewStrings.TEST_CAR_FILE_EXTENSION, 
					(eclass) -> FamilyModelViewStrings.FM_DEFAULT_FILE_EXT
				)
			);
		genericFamilyModel.setInternalFamilyModel(fm);
		
		setFamilyModelWrapper(genericFamilyModel);
		initializeFamilyModel(familyModelWrapper.getInternalFamilyModel());
	}
	
	private void collectProviderExtensions() {
		FamilyModelViewPlugin thisPlugin = new FamilyModelViewPlugin(FamilyModelViewStrings.FM_EXT_POINT_ID);
		
		// Warn developer of potentially unwanted behaviour 
		if (!thisPlugin.populate()) {
			System.out.println("[Family Model View] Warning: There is more than one extension for a provider. Only one of them will be in use.");
		}
		
		// Collect optional providers for the pure family model view 
		this.fmvLabelProvider = thisPlugin.getFamilyModelLabelProviderFromExtension();
		
		/* The default family model extension is fixed and known to the project explorer.
		 * For a custom family model extension, the double-click will not work on custom family model extensions.
		 * In order to make it work, the project explorer plugin must be extended. 
		 */ 
		this.fmvExtensionProvider = thisPlugin.getFamilyModelExtensionProviderFromExtension();
		
		this.fmvIconProvider = thisPlugin.getFamilyModelIconProviderFromExtension();
		
		// collect optional (though strongly recommended) artefact providers
		this.artefactLabelProvider = thisPlugin.getArtefactLabelProviderFromExtension();
		if (artefactLabelProvider == null ) {
			artefactLabelProvider = new NullLabelProvider();
		}
		
		this.artefactExtensionProvider = thisPlugin.getArtefactExtensionProviderFromExtension();
		if (artefactExtensionProvider == null) {
			artefactExtensionProvider = new NullExtensionProvider();
		}
		
		this.artefactIconProvider = thisPlugin.getArtefactIconProviderFromExtension();
		if (artefactIconProvider == null) {
			artefactIconProvider = new EmptyIconProvider();
		}
		
		this.fmTransformations = thisPlugin.getTransformationsFromExtension();
		if (fmTransformations == null) {
			fmTransformations = Collections.emptyList();
		}
	}
	
	private void initializeFamilyModel(FamilyModel familyModel) {
		String extension = fmvExtensionProvider.getExtension(FamilyModelPackage.eINSTANCE.getFamilyModel());
		IResourceManager resourceManager = new EMFResourceSetManager(artefactExtensionProvider, fmvExtensionProvider);
		GenericFamilyModel genericFamilyModel = new GenericFamilyModel(familyModel, resourceManager);
		setFamilyModelWrapper(genericFamilyModel);
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		this.part.setDirty(false);
		
		// create family model provider 
		FXTreeBuilder familyModelTreeBuilder = new FXTreeBuilder(fmvLabelProvider, fmvIconProvider);
		
		// Create car artefact tree builder
		FXTreeBuilder carArtefactTreeBuilder = new FXTreeBuilder(artefactLabelProvider, artefactIconProvider);
		
		// initialize the family model view
		familyModelView = new FamilyModelView(parent, familyModelTreeBuilder, carArtefactTreeBuilder, this);
		familyModelView.showFamilyModel(familyModelWrapper);
	}
	
	/**
	 * Test method that creates a family model
	 * @return
	 */
	public static GenericFamilyModel createDummyModel() {
		FamilyModel model = FamilyModelFactory.eINSTANCE.createFamilyModel();
		model.setName("Example Family Model");
		
		VariationPoint groupMandatory = FamilyModelFactory.eINSTANCE.createVariationPoint();
		groupMandatory.setVariabilityCategory(VariabilityCategory.MANDATORY);
		groupMandatory.setName("VP A");
		
		VariationPoint groupOption = FamilyModelFactory.eINSTANCE.createVariationPoint();
		groupOption.setVariabilityCategory(VariabilityCategory.OPTIONAL);
		groupOption.setName("VP B");
		
		VariationPoint groupAlternative = FamilyModelFactory.eINSTANCE.createVariationPoint();
		groupAlternative.setVariabilityCategory(VariabilityCategory.ALTERNATIVE);
		groupAlternative.setName("Collection of VPs");
		
		//sub groups
		VariationPoint subGroup1 = FamilyModelFactory.eINSTANCE.createVariationPoint();
		subGroup1.setVariabilityCategory(VariabilityCategory.ALTERNATIVE);
		subGroup1.setName("VP C");
		groupAlternative.getChildren().add(subGroup1);

		
		//sub groups
		VariationPoint subGroup2 = FamilyModelFactory.eINSTANCE.createVariationPoint();
		subGroup2.setVariabilityCategory(VariabilityCategory.ALTERNATIVE);
		subGroup2.setName("VP D");
		groupAlternative.getChildren().add(subGroup2);

	
		model.getVariationPoints().add(groupMandatory);
		model.getVariationPoints().add(groupOption);
		model.getVariationPoints().add(groupAlternative);

		
		GenericFamilyModel genericFamilyModel = new GenericFamilyModel(
			new EMFResourceSetManager(
				(eclass) -> FamilyModelViewStrings.TEST_CAR_FILE_EXTENSION, 
				(eclass) -> FamilyModelViewStrings.FM_DEFAULT_FILE_EXT
			)
		);
		genericFamilyModel.setInternalFamilyModel(model);
		
		return genericFamilyModel;
	}
	
	@PreDestroy
	public void preDestroy() {
		
	}
	
	@Focus
	public void onFocus() {
		
	}
	
	@Persist
	public void saveFamilyModel() {
		if (familyModelWrapper == null) {
			return;
		}
		
		// compose absolute path as suggestion for the save dialog
//		String filename = familyModelWrapper.getInternalFamilyModel().getName();
//		String fileExtension = fmvExtensionProvider.getExtension(familyModelWrapper.getInternalFamilyModel().eClass());
//		String relativePath = String.join("\\", "FamilyModels", filename+"."+fileExtension);
//		String workspace = RCPContentProvider.getCurrentWorkspacePath();
//		String absPath = String.join("\\", workspace, relativePath);
		
		// Query the user for the storage path for each object
		final int dialogWidth = 1024;
		final int rowHeight = 30;
		FamilyModel fm = familyModelWrapper.getInternalFamilyModel();
		FXVariantResourceDialog dialog = new FXVariantResourceDialog(fm, "Family Model Resources", dialogWidth, rowHeight);
		dialog.open();
		
		Map<EObject, String> resourceMap = dialog.getResourceMap();
		if (resourceMap.isEmpty()) {
			RCPMessageProvider.errorMessage("Save Family Model", "The resource path could not be initialized correctly. "
					+ "You haven't choosen a root directory.");
			return;
		}
		
		// save the family model along with the referenced variants
		try {
			String rootPath = resourceMap.get(fm);
			familyModelWrapper.save(rootPath);
			part.setDirty(false);
			System.out.println("Saved the family model under the directory \""+rootPath+"\"");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Optional
	@Inject
	public void loadFamilyModel(@UIEventTopic(FamilyModelViewEvents.EVENT_LOAD_FAMILY_MODEL) String path) {
		requestPartSave();
		
		// 
 		try {
			familyModelWrapper.load(path);
			familyModelView.showFamilyModel(familyModelWrapper);
		} catch (IOException e) {
			e.printStackTrace();
		}
 		
		part.setDirty(false);
	}
	
	@Optional
	@Inject
	public void showFamilyModel(@UIEventTopic(FamilyModelViewEvents.EVENT_SHOW_FAMILY_MODEL) FamilyModel fm) {
		// Ask to save the current family model, if it is dirty
		requestPartSave();
		
		// Initialize the new family model
		familyModelWrapper.setInternalFamilyModel(fm);
		
		// Initialize the view
		familyModelView.showFamilyModel(familyModelWrapper);
		
		// dirty part if the requested family model has a resource associated with it
		Resource fmResource = familyModelWrapper.getInternalFamilyModel().eResource();
		if (fmResource == null || !fmResource.isModified()) {
			part.setDirty(true);
		}
	}
	
	@Optional
	@Inject
	public void transformAndShowFamilyModel(@UIEventTopic(FamilyModelViewEvents.EVENT_TRANSFORM_AND_SHOW_FAMILY_MODEL) Object object) {
		// check if the object actually is transformable, pick the first transformation
		java.util.Optional<FamilyModelTransformation> fmTransformation = fmTransformations.stream()
				.filter(transformation -> transformation.canTransform(object)).findFirst();
		if (!fmTransformation.isPresent()) {
			RCPMessageProvider.errorMessage("Transform Family Model", "There is not suitable transformation for the provided model.");
		}
			
		// apply transformation
		FamilyModel fm = fmTransformation.get().apply(object);
		
		if (fm == null) {
			RCPMessageProvider.errorMessage("Transform Family Model", "Transformation to family model failed.");
		}
		
		showFamilyModel(fm);
	}

	/**
	 * Asks the user to save the part if dirty.
	 */
	private void requestPartSave() {
		EPartService ePartService = services.partService.partService;
		MPart thisPart = ePartService.findPart(FamilyModelViewStrings.PART_NAME);
		ePartService.savePart(thisPart, true);
	}
	
	public ServiceContainer getServices() {
		return services;
	}

	public void setServices(ServiceContainer services) {
		this.services = services;
	}
	
	public GenericFamilyModel getFamilyModelWrapper() {
		return familyModelWrapper;
	}

	public void setFamilyModelWrapper(GenericFamilyModel familyModelWrapper) {
		this.familyModelWrapper = familyModelWrapper;
		this.part.setDirty(this.familyModelWrapper != null);
	}

	public String getLabel(EObject eobject) {
		if (eobject == null) {
			return null;
		}
		
		String fmvLabel = fmvLabelProvider.getLabel(eobject);
		if (fmvLabel != null) {
			return fmvLabel;
		} 
		
		String artefactLabel = artefactLabelProvider.getLabel(eobject);
		if (artefactLabel != null) {
			return artefactLabel;
		}
		
		return null;
	}
	
	public String getExtension(EClass eclass) {
		if (eclass == null) {
			return null;
		}
		
		String fmvExtension = fmvExtensionProvider.getExtension(eclass);
		if (fmvExtension != null) {
			return fmvExtension;
		} 
		
		String artefactExtension = artefactExtensionProvider.getExtension(eclass);
		if (artefactExtension != null) {
			return artefactExtension;
		}
		
		return null;
	}
}
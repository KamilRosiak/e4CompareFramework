 
package de.tu_bs.cs.isf.e4cf.family_model_view.prototype;

import static de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewStrings.BUNDLE_NAME;
import static de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewStrings.PREF_ARTEFACT_SPECIALIZATION_KEY;
import static de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewStrings.PREF_FM_SPECIALIZATION_KEY;

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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.key_value.KeyValueNode;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.persistence.IResourceManager;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.persistence.SimpleFMResourceManager;
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
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.components.DefaultArtefactFilter;
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
	private ArtefactFilter artefactFilter;
	
	private List<FamilyModelTransformation> fmTransformations;

	private MPart part;

	private Composite parentComposite;
	
	@Inject
	public FamilyModelViewController(ServiceContainer services) {
		setServices(services);
		this.part = services.partService.getPart(FamilyModelViewStrings.PART_NAME);
//		this.part.setDirty(false);
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		this.parentComposite = parent;
		reload(null);
	}
	
	/**
	 * Reloads the family model view and resets the displayed view to the initial example.
	 * All the plugin extensions get reloaded from the preferences.
	 * 
	 * @param obj not used, can be null
	 */
	@Optional
	@Inject
	public void reload(@UIEventTopic(FamilyModelViewEvents.EVENT_RELOAD_VIEW) Object obj) {
		collectProviderExtensions();
		initializeCarExampleFamilyModel();
		resetFamilyModelView();
	}

	private void collectProviderExtensions() {
		FamilyModelViewPlugin thisPlugin = new FamilyModelViewPlugin(FamilyModelViewStrings.FM_EXT_POINT_ID);
		
		// Warn developer of potentially unwanted behaviour 
		if (!thisPlugin.populate()) {
			System.out.println("[Family Model View] Warning: One of the family model view specializations might be incorrect.");
		}
		
		// Collect providers for the pure family model view 
		KeyValueNode fmvPrefNode = PreferencesUtil.getValue(BUNDLE_NAME, PREF_FM_SPECIALIZATION_KEY);
		this.fmvLabelProvider = thisPlugin.getFamilyModelLabelProviderFromExtension(fmvPrefNode.getStringValue());
		
		/* The default family model extension is fixed and known to the project explorer.
		 * For a custom family model extension, the double-click will not work on custom family model extensions.
		 * In order to make it work, the project explorer plugin must be extended. 
		 */ 
		this.fmvExtensionProvider = thisPlugin.getFamilyModelExtensionProviderFromExtension(fmvPrefNode.getStringValue());
		
		this.fmvIconProvider = thisPlugin.getFamilyModelIconProviderFromExtension(fmvPrefNode.getStringValue());
		
		// collect optional (though strongly recommended) artefact providers
		KeyValueNode artefactPrefNode = PreferencesUtil.getValue(BUNDLE_NAME, PREF_ARTEFACT_SPECIALIZATION_KEY);
		this.artefactLabelProvider = thisPlugin.getArtefactLabelProviderFromExtension(artefactPrefNode.getStringValue());
		if (artefactLabelProvider == null ) {
			artefactLabelProvider = new NullLabelProvider();
		}
		
		this.artefactExtensionProvider = thisPlugin.getArtefactExtensionProviderFromExtension(artefactPrefNode.getStringValue());
		if (artefactExtensionProvider == null) {
			artefactExtensionProvider = new NullExtensionProvider();
		}
		
		this.artefactIconProvider = thisPlugin.getArtefactIconProviderFromExtension(artefactPrefNode.getStringValue());
		if (artefactIconProvider == null) {
			artefactIconProvider = new EmptyIconProvider();
		}
		
		this.fmTransformations = thisPlugin.getTransformationsFromExtension();
		if (fmTransformations == null) {
			fmTransformations = Collections.emptyList();
		}
		
		this.artefactFilter = thisPlugin.getArtefactFilterFromExtension(artefactPrefNode.getStringValue());
		if (artefactFilter == null) {
			artefactFilter = new DefaultArtefactFilter();
		}
	}
	
	private void initializeCarExampleFamilyModel() {
		IResourceManager resManager = new SimpleFMResourceManager(artefactExtensionProvider, fmvExtensionProvider);
		GenericFamilyModel genericFamilyModel = new GenericFamilyModel(resManager);
		FamilyModel fm = CarExampleBuilder.createCarFamilyModel();
		genericFamilyModel.setInternalFamilyModel(fm);
		
		// clear old family model and set the example family model
		if (familyModelWrapper != null && familyModelWrapper.getInternalFamilyModel() != null) {
			EcoreUtil.delete(familyModelWrapper.getInternalFamilyModel());
		}
		setGenericFamilyModel(genericFamilyModel);
	}
	
	public void resetFamilyModelView() {
		// reset parent
		for (int i = 0; i < parentComposite.getChildren().length; i++) {
			Control control = parentComposite.getChildren()[i];
			control.dispose();
		}
		
		// create family model provider 
		FXTreeBuilder familyModelTreeBuilder = new FXTreeBuilder(fmvLabelProvider, fmvIconProvider);
		
		// Create artefact tree builder
		FXTreeBuilder artefactTreeBuilder = new FXTreeBuilder(artefactLabelProvider, artefactIconProvider);
		
		// initialize the family model view
		familyModelView = new FamilyModelView(parentComposite, familyModelTreeBuilder, artefactTreeBuilder, artefactFilter, this);
		familyModelView.showFamilyModel(familyModelWrapper);
		
		parentComposite.layout();
	}
	
	/**
	 * Initialize the resource manager and the generic family model.
	 * 
	 * @param familyModel The model for this view.
	 */
	private void initializeFamilyModel(FamilyModel familyModel) {
		IResourceManager resourceManager = new SimpleFMResourceManager(artefactExtensionProvider, fmvExtensionProvider);
		GenericFamilyModel genericFamilyModel = new GenericFamilyModel(familyModel, resourceManager);
		setGenericFamilyModel(genericFamilyModel);
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
		
		// Query the user for the storage path for each object
		FamilyModel fm = familyModelWrapper.getInternalFamilyModel();
		final int dialogWidth = 1024;
		final int rowHeight = 30;
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
			familyModelWrapper.save(resourceMap);
//			part.setDirty(false);
			System.out.println("Saved the following family model resources:");
			for (Map.Entry<EObject, String> saveLocation : resourceMap.entrySet()) {
				if (saveLocation.getKey() instanceof FamilyModel) {
					System.out.println("\t > Family Model("+((FamilyModel) saveLocation.getKey()).getName()+") \""+saveLocation.getValue()+"\"");					
				} else if (saveLocation.getKey() instanceof Variant) {
					System.out.println("\t > Variant("+((Variant) saveLocation.getKey()).getIdentifier()+") \""+saveLocation.getValue()+"\"");
				}
			}
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
 		
//		part.setDirty(false);
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
//			part.setDirty(true);
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

	public void setGenericFamilyModel(GenericFamilyModel familyModelWrapper) {
		this.familyModelWrapper = familyModelWrapper;
//		this.part.setDirty(this.familyModelWrapper != null);
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
	
	/**
	 * Applies the filter provided through plugin extension.
	 * 
	 * @param eobject
	 * @return
	 */
	public boolean filter(EObject eobject) {
		return artefactFilter.apply(eobject);
	}
}
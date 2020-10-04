 
package de.tu_bs.cs.isf.e4cf.family_model_view.prototype;

import static de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewStrings.BUNDLE_NAME;
import static de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewStrings.PREF_ARTEFACT_SPECIALIZATION_KEY;
import static de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewStrings.PREF_FM_SPECIALIZATION_KEY;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import de.tu_bs.cs.isf.e4cf.core.compare.parts.detail_view.util.DetailViewStringTable;
import de.tu_bs.cs.isf.e4cf.core.compare.string_table.E4CompareEventTable;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.Pair;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.key_value.KeyValueNode;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelPackage;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.persistence.IResourceManager;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.persistence.SimpleFMResourceManager;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.plugin.FamilyModelViewPlugin;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewEvents;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewStrings;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.tests.CarExampleBuilder;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.transformation.FamilyModelTransformation;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.util.EmptyIconProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.util.NullExtensionProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.util.NullLabelProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.FXTreeBuilder;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.FamilyModelView;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.components.DefaultArtefactFilter;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.dialog.AbstractResourceRowDialog.ResourceEntry;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.dialog.LoadFamilyModelDialog;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.dialog.SaveFamilyModelDialog;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.elements.FXFamilyModelElement;
import javafx.stage.FileChooser;

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

	private IEclipseContext fmvContext;
	
	@Inject
	public FamilyModelViewController(@Active MPart part, @Active IEclipseContext ctx, ServiceContainer services) {
		setServices(services);
		this.part = part;
		this.part.setDirty(false);
		
		this.fmvContext = ctx;
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
		
		// clear the context from a previous content
		fmvContext.remove(FamilyModelViewStrings.ACTIVE_DATA_SOURCE);
		fmvContext.remove(FamilyModelViewStrings.ACTIVE_VAR_POINT_MAPPING);
	}

	@Inject
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
		
		// Create a save dialog and let the user select the correct paths for saving the family model + variants  
		FamilyModel fm = familyModelWrapper.getInternalFamilyModel();
		final int dialogWidth = 1024;
		final int rowHeight = 40;
		SaveFamilyModelDialog dialog = new SaveFamilyModelDialog("Family Model Resources", dialogWidth, rowHeight);
		
		// Family model resource entry
		ResourceEntry fmEntry = new ResourceEntry();
		fmEntry.setId(fm.getName());
		fmEntry.setLabel(fm.getName());
		fmEntry.setButtonLabel("Select Family Model Resource Path");
		fmEntry.setResource(dialog.getResourcePath(fm));
		fmEntry.setResourceSetter(oldEntry -> {
			FileChooser fc = new FileChooser();
			
			File dir = new File(RCPContentProvider.getCurrentWorkspacePath());
			fc.setInitialDirectory(dir);
			fc.setTitle("Choose a Family Model File");
			File selectedFile = fc.showSaveDialog(dialog.getStage());
			if (selectedFile != null) {	
				return new Pair<>(fm.getName(), selectedFile.toString());
			}
			return null;
		});
		
		// Variant resource entries
		List<ResourceEntry> variantEntries = new ArrayList<>();
		for (Variant variant : fm.getVariants()) {
			ResourceEntry variantEntry = new ResourceEntry();
			variantEntry.setId(variant.getIdentifier());
			variantEntry.setLabel(variant.getIdentifier());
			variantEntry.setButtonLabel("Select Variant Path");
			variantEntry.setResource(dialog.getResourcePath(variant.getInstance()));
			variantEntry.setResourceSetter(oldEntry -> {
				FileChooser fc = new FileChooser();
				
				File dir = new File(RCPContentProvider.getCurrentWorkspacePath());
				fc.setInitialDirectory(dir);
				fc.setTitle("Choose a Variant File");
				File selectedFile = fc.showSaveDialog(dialog.getStage());
				if (selectedFile != null) {	
					return new Pair<>(variant.getIdentifier(), selectedFile.toString());
				}
				return null;
			});
			
			variantEntries.add(variantEntry);
		}
		
		// Build and open dialog
		dialog.buildDialog()
			.buildResourceEntry(fmEntry)
			.buildSeparator()
			.buildResourceEntry(variantEntries.toArray(new ResourceEntry[0]))
			.open();
		
		// Obtain the resources from the dialog
		Map<String, String> resourceMap = dialog.getResources();
		if (resourceMap.isEmpty()) {
			RCPMessageProvider.errorMessage("Save Family Model", "The save operation has been aborted.");
			return;
		}
		
		// Map resources to actual eobjects
		Map<EObject, String> resourceObjectMap = new HashMap<>();
		resourceObjectMap.put(fm, resourceMap.get(fm.getName()));
		for (Variant variant : fm.getVariants()) {
			resourceObjectMap.put(variant, resourceMap.get(variant.getIdentifier()));
		}
		
		// save the family model along with the referenced variants
		try {
			boolean isSaved = familyModelWrapper.save(resourceObjectMap);
			if (isSaved) {
				part.setDirty(false);
				System.out.println("Saved the following family model resources:");
				for (Map.Entry<EObject, String> saveLocation : resourceObjectMap.entrySet()) {
					if (saveLocation.getKey() instanceof FamilyModel) {
						System.out.println("\t > Family Model("+((FamilyModel) saveLocation.getKey()).getName()+") \""+saveLocation.getValue()+"\"");					
					} else if (saveLocation.getKey() instanceof Variant) {
						System.out.println("\t > Variant("+((Variant) saveLocation.getKey()).getIdentifier()+") \""+saveLocation.getValue()+"\"");
					}
				}				
			} else {
				RCPMessageProvider.errorMessage("Save Family Model", "An error occurred while saving. The family model has not been saved.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadFamilyModel() {
		// Query the user for the storage path for each object
		final int dialogWidth = 1024;
		final int rowHeight = 40;
		LoadFamilyModelDialog dialog = new LoadFamilyModelDialog("Family Model Resources", dialogWidth, rowHeight);
		
		String resourceId = "fm";
		ResourceEntry fmEntry = new ResourceEntry();
		fmEntry.setId(resourceId);
		fmEntry.setLabel("Family Model Resource");
		fmEntry.setButtonLabel("Select Resource");
		fmEntry.setResource("");
		fmEntry.setResourceSetter(oldEntry -> {
			FileChooser fc = new FileChooser();
			
			File dir = new File(RCPContentProvider.getCurrentWorkspacePath());
			fc.setInitialDirectory(dir);
			fc.setTitle("Choose the Family Model");
			File selectedFile = fc.showOpenDialog(dialog.getStage());
			if (selectedFile != null && selectedFile.exists()) {
				return new Pair<String, String>(fmEntry.getId(), selectedFile.toString());
			}
			return null;
		});
		
		dialog.buildDialog().buildResourceEntry(fmEntry).open();
		
		// Retrieve the user selected resource
		String fmPathString = dialog.getResources().get(resourceId);
		String fmExtension = getExtension(FamilyModelPackage.eINSTANCE.getFamilyModel());
		if (!fmPathString.endsWith(fmExtension)) {
			RCPMessageProvider.errorMessage("Load Family Model", "The selected resource does not have a valid file extension.");
			return;
		}
		
		loadFamilyModel(fmPathString);
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
	
	/**
	 * Sets a data source for the family model.<br><br>
	 * 
	 * The data source is a single object that encapsulates the entire comparison represented in the family model view. 
	 * If there are parts or components interested in the comparison detail, they can listen for changes in the data source object.<br><br>
	 * 
	 * Sends Events:
	 * <ul>
	 * 	<li> {@link FamilyModelViewEvents#EVENT_DATA_SOURCE_UPDATED} 
	 * </ul> 
	 * 
	 * @param dataSource encapsulates entire comparison information
	 */
	@Optional
	@Inject
	public void setDataSource(@UIEventTopic(FamilyModelViewEvents.EVENT_SET_DATA_SOURCE) Object dataSource, IEventBroker eventBroker) {
		if (dataSource != null) {
			fmvContext.set(FamilyModelViewStrings.ACTIVE_DATA_SOURCE, dataSource);
			eventBroker.send(FamilyModelViewEvents.EVENT_DATA_SOURCE_UPDATED, dataSource);
		}
	}
	
	/**
	 * Sets a mapping from variation points to corresponding comparison data objects. 
	 * Internally this is used to provide the detail view with concrete information about every variation points.<br><br>
	 * 
	 * Sends Events:
	 * <ul>
	 * 	<li> {@link FamilyModelViewEvents#EVENT_VAR_POINT_MAPPING_UPDATED} 
	 * </ul> 
	 * 
	 * @param varPointMapping maps a variation points to an object
	 */
	@Optional
	@Inject
	public void setVariationPointMapping(@UIEventTopic(FamilyModelViewEvents.EVENT_SET_VAR_POINT_MAPPING) Map<VariationPoint, ?> varPointMapping, IEventBroker eventBroker) {
		if (varPointMapping != null) {
			fmvContext.set(FamilyModelViewStrings.ACTIVE_VAR_POINT_MAPPING, varPointMapping);			
			eventBroker.send(FamilyModelViewEvents.EVENT_VAR_POINT_MAPPING_UPDATED, varPointMapping);
		}
	}
	
	/**
	 * Shows family model details for a tree node in the family model tree. <br><br>
	 * 
	 * Sends Events:
	 * <ul>
	 * 	<li> {@link E4CompareEventTable#SHOW_DETAIL_EVENT} 
	 * </ul> 
	 * 
	 * @param fxFmElement
	 */
	@Optional
	@Inject
	public void showDetails(@UIEventTopic(FamilyModelViewEvents.EVENT_SHOW_DETAILS) FXFamilyModelElement fxFmElement) {
		// Check if there's a variation point with a mapping onto a comparison data object
		Object targetData = fxFmElement.get();
		
		// Query the local context for a mapping that contains the variation point
		Object activeVarPointMapping = fmvContext.get(FamilyModelViewStrings.ACTIVE_VAR_POINT_MAPPING);
		if (activeVarPointMapping instanceof Map) {
			Map<?,?> variationPointDataMapping = (Map<?, ?>) activeVarPointMapping;
			Object mappedData = variationPointDataMapping.get(targetData);
			if (mappedData != null) {
				targetData = mappedData;
			}
		}
		
		services.partService.showPart(DetailViewStringTable.FAMILYMODE_DETAIL_VIEW_ID);
		services.eventBroker.send(E4CompareEventTable.SHOW_DETAIL_EVENT, targetData);
	}

	/**
	 * Asks the user to save the part if dirty.
	 */
	private void requestPartSave() {
//		EPartService ePartService = services.partService.partService;
//		MPart thisPart = ePartService.findPart(FamilyModelViewStrings.PART_NAME);
//		ePartService.savePart(thisPart, true);
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
package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import de.tu_bs.cs.isf.e4cf.core.plugin.ExtensionPointContext;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.ArtefactFilter;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.ExtensionProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.IconProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.LabelProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewStrings;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.util.FamilyModelTransformation;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.components.DefaultArtefactFilter;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.components.DefaultFamilyModelExtensionProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.components.DefaultFamilyModelIconProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.components.DefaultFamilyModelLabelProvider;

/**
 * The family model plugin allows to manage the corresponding extensions.  
 * 
 * @author Oliver Urbaniak
 *
 */
public class FamilyModelViewPlugin {	
	private ExtensionPointContext extPoint;
	private List<IConfigurationElement> artefactSpecializationConfigs;
	private List<IConfigurationElement> familyModelSpecializationConfigs;
	private List<IConfigurationElement> transformationConfigs;
	
	public FamilyModelViewPlugin(String extensionPointId) {
		extPoint = new ExtensionPointContext(extensionPointId);
		
	}
	
	/**
	 * Collects all relevant extension entries for the family model properties extension point.
	 * This should be the first method to call to initialize the this class. 
	 * 
	 * @return <tt>true</tt>, if there is a valid number of registered extensions 
	 */
	public boolean populate() {
		boolean validPluginConfiguration = true;
		
		artefactSpecializationConfigs = extPoint.getConfigurationElements().stream()
				.filter(config -> config.getName().equals(FamilyModelViewStrings.FM_ARTEFACT_SPECIALIZATION_ELEM))
				.collect(Collectors.toList());
		
		familyModelSpecializationConfigs = extPoint.getConfigurationElements().stream()
				.filter(config -> config.getName().equals(FamilyModelViewStrings.FM_FAMILY_MODEL_SPECIALIZATION_ELEM))
				.collect(Collectors.toList());
		
		transformationConfigs = extPoint.getConfigurationElements().stream()
				.filter(config -> config.getName().equals(FamilyModelViewStrings.FM_TRANSFORMATION_ELEM))
				.collect(Collectors.toList());
		
		return validPluginConfiguration;
	}
	
	
	public List<String> getFamilyModelSpecializationIds() {
		return familyModelSpecializationConfigs
			.stream()
			.map((config) -> config.getAttribute(FamilyModelViewStrings.FM_ID_ATTR))
			.collect(Collectors.toList());
	}

	public List<String> getArtefactSpecializationIds() {
		return artefactSpecializationConfigs
			.stream()
			.map((config) -> config.getAttribute(FamilyModelViewStrings.FM_ID_ATTR))
			.collect(Collectors.toList());
	}
	
	public List<LabelProvider> getArtefactLabelProvidersFromExtension() {
		List<LabelProvider> artefactLabelProviders = createExecutableAttributes(artefactSpecializationConfigs, FamilyModelViewStrings.FM_LABEL_PROVIDER_ATTR);
		return artefactLabelProviders != null ? artefactLabelProviders : null;
	}
	
	public LabelProvider getArtefactLabelProviderFromExtension(String id) {
		return getExecutableExtensionById(id, artefactSpecializationConfigs, FamilyModelViewStrings.FM_LABEL_PROVIDER_ATTR);
	}
	
	public List<ExtensionProvider> getArtefactExtensionProvidersFromExtension() {
		List<ExtensionProvider> extensionProviders = createExecutableAttributes(artefactSpecializationConfigs, FamilyModelViewStrings.FM_EXTENSION_PROVIDER_ATTR);
		return extensionProviders != null ? extensionProviders : null;
	}
	
	public ExtensionProvider getArtefactExtensionProviderFromExtension(String id) {
		return getExecutableExtensionById(id, artefactSpecializationConfigs, FamilyModelViewStrings.FM_EXTENSION_PROVIDER_ATTR);
	}
	
	public List<IconProvider> getArtefactIconProvidersFromExtension() {
		List<IconProvider> iconProviders = createExecutableAttributes(artefactSpecializationConfigs, FamilyModelViewStrings.FM_ICON_PROVIDER_ATTR);
		return iconProviders != null ? iconProviders : null;
	}
	
	public IconProvider getArtefactIconProviderFromExtension(String id) {
		return getExecutableExtensionById(id, artefactSpecializationConfigs, FamilyModelViewStrings.FM_ICON_PROVIDER_ATTR);
	}
	
	public List<ArtefactFilter> getArtefactFiltersFromExtension() {
		List<ArtefactFilter> filters = createExecutableAttributes(artefactSpecializationConfigs, FamilyModelViewStrings.FM_ARTEFACT_FILTER_ATTR);
		return filters != null ? filters : Arrays.asList(new DefaultArtefactFilter());
	}
	
	public ArtefactFilter getArtefactFilterFromExtension(String id) {
		return getExecutableExtensionById(id, artefactSpecializationConfigs, FamilyModelViewStrings.FM_ARTEFACT_FILTER_ATTR);
	}
	
	public List<LabelProvider> getFamilyModelLabelProvidersFromExtension() {
		List<LabelProvider> labelProviders = createExecutableAttributes(familyModelSpecializationConfigs, FamilyModelViewStrings.FM_LABEL_PROVIDER_ATTR);
		return labelProviders != null ? labelProviders : Arrays.asList(new DefaultFamilyModelLabelProvider());
	}
	
	public LabelProvider getFamilyModelLabelProviderFromExtension(String id) {
		LabelProvider labelProvider = getExecutableExtensionById(id, familyModelSpecializationConfigs, FamilyModelViewStrings.FM_LABEL_PROVIDER_ATTR);
		return labelProvider != null ? labelProvider : new DefaultFamilyModelLabelProvider();
	}
	
	public List<ExtensionProvider> getFamilyModelExtensionProviderwFromExtension() {
		List<ExtensionProvider> extensionProviders = createExecutableAttributes(familyModelSpecializationConfigs, FamilyModelViewStrings.FM_EXTENSION_PROVIDER_ATTR);
		return extensionProviders != null ? extensionProviders : Arrays.asList(new DefaultFamilyModelExtensionProvider());
	}
	
	public ExtensionProvider getFamilyModelExtensionProviderFromExtension(String id) {
		ExtensionProvider extensionProvider = getExecutableExtensionById(id, familyModelSpecializationConfigs, FamilyModelViewStrings.FM_EXTENSION_PROVIDER_ATTR);
		return extensionProvider != null ? extensionProvider : new DefaultFamilyModelExtensionProvider();
	}
		
	public List<IconProvider> getFamilyModelIconProvidersFromExtension() {
		List<IconProvider> iconProviders = createExecutableAttributes(familyModelSpecializationConfigs, FamilyModelViewStrings.FM_ICON_PROVIDER_ATTR);
		return iconProviders != null ? iconProviders : Arrays.asList(new DefaultFamilyModelIconProvider());
	}
	
	public IconProvider getFamilyModelIconProviderFromExtension(String id) {
		IconProvider iconProvider = getExecutableExtensionById(id, familyModelSpecializationConfigs, FamilyModelViewStrings.FM_ICON_PROVIDER_ATTR);
		return iconProvider != null ? iconProvider : new DefaultFamilyModelIconProvider();
	}

	public List<FamilyModelTransformation> getTransformationsFromExtension() {
		return createExecutableAttributes(transformationConfigs, FamilyModelViewStrings.FM_TRANSFORMATION_ATTR);
	}
	
	public FamilyModelTransformation getTransformationFromExtension(String id) {
		return getExecutableExtensionById(id, transformationConfigs, FamilyModelViewStrings.FM_TRANSFORMATION_ATTR);
	}
	
	public List<String> getTransformationIds() {
		return transformationConfigs
			.stream()
			.map((config) -> config.getAttribute(FamilyModelViewStrings.FM_ID_ATTR))
			.collect(Collectors.toList());
	}
	
	@SuppressWarnings("unchecked")
	private <T> T getExecutableExtensionById(String id, List<IConfigurationElement> configs, String attribute) {
		for (IConfigurationElement config : configs) {
			if (id.equals(config.getAttribute(FamilyModelViewStrings.FM_ID_ATTR))) {
				try {
					return (T) config.createExecutableExtension(attribute);
				} catch (CoreException e) {
					// just return null
				}
			}
		}
		return null;
	}
	
	/**
	 * Instantiates a list of configuration elements and returns them.
	 * 
	 * @param configs
	 * @param executableAttr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> List<T> createExecutableAttributes(List<IConfigurationElement> configs, String executableAttr) {
		if (configs.isEmpty()) {
			return null;
		}
		
		try {
			List<T> typedExecutables = new ArrayList<>();
			for (IConfigurationElement config : configs) {
				Object obj = config.createExecutableExtension(executableAttr);
				typedExecutables.add((T) obj);
			}
			return typedExecutables;
		} catch (CoreException e) {
			return null;
		}
	}
}

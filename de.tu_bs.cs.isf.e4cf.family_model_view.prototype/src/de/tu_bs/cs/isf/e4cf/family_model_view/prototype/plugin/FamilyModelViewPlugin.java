package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.plugin;

import java.util.ArrayList;
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
	 * Ideally, there's a single extension for artefact specialization and at most one for family model specialization.
	 * If there's more than one registered extension for a provider, the first one will be used. 
	 * 
	 * @return <tt>true</tt>, if there is a valid number of registered extensions 
	 */
	public boolean populate() {
		boolean validPluginConfiguration = true;
		
		artefactSpecializationConfigs = extPoint.getConfigurationElements().stream()
				.filter(config -> config.getName().equals(FamilyModelViewStrings.FM_ARTEFACT_SPECIALIZATION_ELEM))
				.collect(Collectors.toList());
		
		// Not more than one artefact specialization allowed
		validPluginConfiguration &= artefactSpecializationConfigs.size() <= 1;
		
		familyModelSpecializationConfigs = extPoint.getConfigurationElements().stream()
				.filter(config -> config.getName().equals(FamilyModelViewStrings.FM_FAMILY_MODEL_SPECIALIZATION_ELEM))
				.collect(Collectors.toList());
		
		// Not more than one family model specialization allowed
		validPluginConfiguration &= familyModelSpecializationConfigs.size() <= 1;
		
		transformationConfigs = extPoint.getConfigurationElements().stream()
				.filter(config -> config.getName().equals(FamilyModelViewStrings.FM_TRANSFORMATION_ELEM))
				.collect(Collectors.toList());
		
		return validPluginConfiguration;
	}
	
	public LabelProvider getArtefactLabelProviderFromExtension() {
		List<LabelProvider> artefactLabelProviders = createExecutableAttributes(artefactSpecializationConfigs, FamilyModelViewStrings.FM_LABEL_PROVIDER_ATTR);
		return artefactLabelProviders != null ? artefactLabelProviders.get(0) : null;
	}
	
	public ExtensionProvider getArtefactExtensionProviderFromExtension() {
		List<ExtensionProvider> extensionProviders = createExecutableAttributes(artefactSpecializationConfigs, FamilyModelViewStrings.FM_EXTENSION_PROVIDER_ATTR);
		return extensionProviders != null ? extensionProviders.get(0) : null;
	}
	
	public IconProvider getArtefactIconProviderFromExtension() {
		List<IconProvider> iconProviders = createExecutableAttributes(artefactSpecializationConfigs, FamilyModelViewStrings.FM_ICON_PROVIDER_ATTR);
		return iconProviders != null ? iconProviders.get(0) : null;
	}
	
	public ArtefactFilter getArtefactFilterFromExtension() {
		List<ArtefactFilter> filters = createExecutableAttributes(artefactSpecializationConfigs, FamilyModelViewStrings.FM_ARTEFACT_FILTER_ATTR);
		return filters != null ? filters.get(0) : new DefaultArtefactFilter();
	}
	
	public LabelProvider getFamilyModelLabelProviderFromExtension() {
		List<LabelProvider> labelProviders = createExecutableAttributes(familyModelSpecializationConfigs, FamilyModelViewStrings.FM_LABEL_PROVIDER_ATTR);
		return labelProviders != null ? labelProviders.get(0) : new DefaultFamilyModelLabelProvider();
	}
	
	public ExtensionProvider getFamilyModelExtensionProviderFromExtension() {
		List<ExtensionProvider> extensionProviders = createExecutableAttributes(familyModelSpecializationConfigs, FamilyModelViewStrings.FM_EXTENSION_PROVIDER_ATTR);
		return extensionProviders != null ? extensionProviders.get(0) : new DefaultFamilyModelExtensionProvider();
	}
	
	public List<FamilyModelTransformation> getTransformationsFromExtension() {
		return createExecutableAttributes(transformationConfigs, FamilyModelViewStrings.FM_TRANSFORMATION_ATTR);
	}
	
	public IconProvider getFamilyModelIconProviderFromExtension() {
		List<IconProvider> iconProviders = createExecutableAttributes(familyModelSpecializationConfigs, FamilyModelViewStrings.FM_EXTENSION_PROVIDER_ATTR);
		return iconProviders != null ? iconProviders.get(0) : new DefaultFamilyModelIconProvider();
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

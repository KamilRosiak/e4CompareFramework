package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import de.tu_bs.cs.isf.e4cf.core.plugin.ExtensionPointContext;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.ExtensionProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.IconProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.LabelProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewStrings;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.util.FamilyModelTransformation;
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
		Object obj = createExecutableAttribute(artefactSpecializationConfigs, FamilyModelViewStrings.FM_LABEL_PROVIDER_ATTR);
		return obj != null ? (LabelProvider) obj : null;
	}
	
	public ExtensionProvider getArtefactExtensionProviderFromExtension() {
		Object obj = createExecutableAttribute(artefactSpecializationConfigs, FamilyModelViewStrings.FM_EXTENSION_PROVIDER_ATTR);
		return obj != null ? (ExtensionProvider) obj : null;
	}
	
	public IconProvider getArtefactIconProviderFromExtension() {
		Object obj = createExecutableAttribute(artefactSpecializationConfigs, FamilyModelViewStrings.FM_ICON_PROVIDER_ATTR);
		return obj != null ? (IconProvider) obj : null;
	}
	
	public LabelProvider getFamilyModelLabelProviderFromExtension() {
		Object obj = createExecutableAttribute(familyModelSpecializationConfigs, FamilyModelViewStrings.FM_LABEL_PROVIDER_ATTR);
		if (obj != null) {
			return (LabelProvider) obj;
		} else {
			return new DefaultFamilyModelLabelProvider();
		}
	}
	
	public ExtensionProvider getFamilyModelExtensionProviderFromExtension() {
		Object obj = createExecutableAttribute(familyModelSpecializationConfigs, FamilyModelViewStrings.FM_EXTENSION_PROVIDER_ATTR);
		if (obj != null) {
			return (ExtensionProvider) obj;
		} else {
			return new DefaultFamilyModelExtensionProvider();
		}
	}
	
	public List<FamilyModelTransformation> getTransformationsFromExtension() {
		try {
			List<FamilyModelTransformation> fmTransformations = new ArrayList<>();
			for (IConfigurationElement transformationConfig : transformationConfigs) {
				Object o = transformationConfig.createExecutableExtension(FamilyModelViewStrings.FM_TRANSFORMATION_ATTR);
				fmTransformations.add((FamilyModelTransformation) o);
			}
			return fmTransformations;
		} catch (CoreException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}			
	}
	
	public IconProvider getFamilyModelIconProviderFromExtension() {
		Object obj = createExecutableAttribute(familyModelSpecializationConfigs, FamilyModelViewStrings.FM_EXTENSION_PROVIDER_ATTR);
		if (obj != null) {
			return (IconProvider) obj;
		} else {
			return new DefaultFamilyModelIconProvider();
		}
	}
	
	private Object createExecutableAttribute(List<IConfigurationElement> configs, String executableAttr) {
		if (configs.isEmpty()) {
			return null;
		}
		
		try {
			return configs.get(0).createExecutableExtension(executableAttr);
		} catch (CoreException e) {
			e.printStackTrace();
			return null;
		}
	}
}

package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.contribution;

import static de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewStrings.*;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import de.tu_bs.cs.isf.e4cf.core.preferences.interfaces.IPreferencePage;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.key_value.KeyValueNode;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.plugin.FamilyModelViewPlugin;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewEvents;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewStrings;

public class FamilyModelPreferencePage implements IPreferencePage {

	private KeyValueNode fmSpecializationNode;
	private String initialFmSpecialization;
	
	private KeyValueNode artefactSpecializationNode;
	private String initialArtefactSpecialization;
	
	private KeyValueNode fmTransformationNode;
	private String initialFmTransformation;
	
	private ServiceContainer services;
	

	@Override
	public void createPage(CTabFolder parent, ServiceContainer services) {
		this.services = services;
		
		CTabItem tab = new CTabItem(parent, SWT.NONE);
		tab.setText("Family Model View [Prototype]");
		//tab.setImage(services.imageService.getImage(SimulinkStringTable.BUNDLE_NAME, MatlabCommandFileTable.MATLAB_ICON_24));
		
		Composite page = new Composite(parent, SWT.NONE);
		page.setLayout(new GridLayout(3, false));
		tab.setControl(page);
		
		// create the input rows for setting the specializations 
		FamilyModelViewPlugin fmvPlugin = new FamilyModelViewPlugin(FM_EXT_POINT_ID);
		if (!fmvPlugin.populate()) {
			throw new RuntimeException("Family Model Preferences: could not initialize Family Model Prototype plugin.");
		}
			
		CLabel info = new CLabel(page, SWT.CENTER);
		info.setImage(services.imageService.getImage(BUNDLE_NAME, "icons/optional_16.png"));
		info.setText("Selecting new specialization extensions for either field causes the family model view to reload.");
		info.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		info.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 3, 1));
		
		fmSpecializationNode = loadNode(PREF_FM_SPECIALIZATION_KEY, PREF_DEFAULT_SPECIALIZATION); 
		initialFmSpecialization = fmSpecializationNode.getStringValue();
		List<String> fmSpecIds = fmvPlugin.getFamilyModelSpecializationIds();
		createNodeRow(page, "FM Specialization", fmSpecializationNode, fmSpecIds.toArray(new String[0]));

		artefactSpecializationNode = loadNode(PREF_ARTEFACT_SPECIALIZATION_KEY, PREF_DEFAULT_SPECIALIZATION);
		initialArtefactSpecialization = artefactSpecializationNode.getStringValue();
		List<String> artefactSpecIds = fmvPlugin.getArtefactSpecializationIds();
		createNodeRow(page, "Artefact Specialization", artefactSpecializationNode, artefactSpecIds.toArray(new String[0]));
		
		fmTransformationNode = loadNode(PREF_FM_TRANSFORMATION_KEY, PREF_DEFAULT_TRANSFORMATION);
		initialFmTransformation = fmTransformationNode.getStringValue();
		List<String> fmTransformationIds = fmvPlugin.getTransformationIds();
		createNodeRow(page, "FM Transformation", fmTransformationNode, fmTransformationIds.toArray(new String[0]));
	}

	protected void createNodeRow(Composite page, String label, KeyValueNode keyValueNode, String... items) {
		Label rowLabel = new Label(page, SWT.NONE);
		rowLabel.setText(label);
		rowLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1));
		
		Combo rowCombo = new Combo(page, SWT.READ_ONLY | SWT.DROP_DOWN);
		rowCombo.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		rowCombo.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		
		rowCombo.setItems(items);
		if (items.length > 0) {
			rowCombo.select(0);
		}
		
		for (int i = 0; i < items.length; i++) {
			String item = items[i];
			if (item.equals(keyValueNode.getStringValue())) {
				rowCombo.select(i);
			}
		}
		
		rowCombo.addModifyListener(event -> {
			Combo combo = (Combo) event.widget;
			keyValueNode.setValue(combo.getText());
		});
	}
	
	@Override
	public void store() {
		System.out.println("Selected \""+fmSpecializationNode.getStringValue()+"\" as family model specialization");
		System.out.println("Selected \""+artefactSpecializationNode.getStringValue()+"\" as artefact specialization");
		PreferencesUtil.storeKeyValueNode(fmSpecializationNode);
		PreferencesUtil.storeKeyValueNode(artefactSpecializationNode);
		
		// for changed preferences, reload family model view
		if (!fmSpecializationNode.getStringValue().equals(initialFmSpecialization) || 
				!artefactSpecializationNode.getStringValue().equals(initialArtefactSpecialization)) {
			services.eventBroker.send(FamilyModelViewEvents.EVENT_RELOAD_VIEW, null);
		} 
	}

	protected KeyValueNode loadNode(String key, String defaultValue) {
		return PreferencesUtil.getValueWithDefault(FamilyModelViewStrings.BUNDLE_NAME, key, defaultValue);
	}
}

package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.templates;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class KeyValuePage extends WizardPage {
	Map<Label, Text> labelTextMap;
	Map<String, String> _keyValues;

	protected KeyValuePage(String pageName, String pageTitle, ImageDescriptor imageDescriptor,
			Map<String, String> keyValues) {
		super("pageName", pageName, imageDescriptor);
		_keyValues = keyValues;
		labelTextMap = new HashMap<Label, Text>();
	}

	@Override
	public void createControl(Composite parent) {
		Composite mainComposite = buildMainComposite(parent);
		intializeKeyValueComponents(mainComposite);
		setControl(mainComposite);
	}

	private Composite buildMainComposite(Composite parent) {
		Composite mainComposite = new Composite(parent, SWT.NONE);
		mainComposite.setLayout(new GridLayout(4, true));
		mainComposite.setLayoutData(new GridData());
		return mainComposite;
	}

	private void intializeKeyValueComponents(Composite parent) {
		for (Entry<String, String> e : _keyValues.entrySet()) {
			// create label
			Label keyLabel = new Label(parent, SWT.NONE);
			keyLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, true));
			keyLabel.setText(e.getKey());
			// create text
			Text valueText = new Text(parent, SWT.LEFT | SWT.SINGLE | SWT.H_SCROLL | SWT.BORDER);
			valueText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 3, 1));
			valueText.setText(e.getValue());
			valueText.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					checkFinishedStatus();
					updateWizard();
				}
			});
			labelTextMap.put(keyLabel, valueText);
		}
	}

	public void updateWizard() {
		getWizard().getContainer().updateButtons();
	}

	public void checkFinishedStatus() {
		boolean areAllValuesSet = true;
		for (Entry<Label, Text> e : labelTextMap.entrySet()) {
			_keyValues.put(e.getKey().getText(), e.getValue().getText());
			if (e.getValue().getText() == "") {
				areAllValuesSet = false;
			}
		}

		setPageComplete(areAllValuesSet);
	}
}

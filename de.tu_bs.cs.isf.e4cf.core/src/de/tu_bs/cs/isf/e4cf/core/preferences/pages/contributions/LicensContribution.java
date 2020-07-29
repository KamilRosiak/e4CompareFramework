package de.tu_bs.cs.isf.e4cf.core.preferences.pages.contributions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.gui.LabeledDecsion;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.key_value.KeyValueNode;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;

public class LicensContribution{
	private KeyValueNode licensValue;
	
	public LicensContribution(Composite parent) {
		createControl(parent);
	}
	
	private void createControl(Composite parent) {
		Group group = new Group(parent, SWT.None);
		group.setText("License");
		group.setLayout(new GridLayout(1,true));
		licensValue =  PreferencesUtil.getValue(E4CStringTable.DEFAULT_BUNDLE_NAME, E4CStringTable.LICENSE_AGREEMENT_KEY);
		new LabeledDecsion(group, SWT.None, E4CStringTable.LICENSE_AGREEMENT, licensValue);
	}
	
	public KeyValueNode getKeyValueNode() {
		return licensValue;
	}
}

package de.tu_bs.cs.isf.e4cf.compare.extensions.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import de.tu_bs.cs.isf.e4cf.compare.stringtable.CompareST;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.gui.LabeledSliderGroup;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.key_value.KeyValueNode;

public class OptionalThresholdPref {
    public static final String OPTIONAL_THRESHOLD ="OPTIONAL_THRESHOLD";
    public static final String OPTIONAL_THRESHOLD_LABEL = "Optional Threshold";
    public static final int DEFAULT_THRESHOLD = 40;
    private KeyValueNode optionThresholdValue;

    
    public OptionalThresholdPref(Composite page) {
	createControl(page);
    }
    
    private void createControl(Composite parent) {
	Group group = new Group(parent, SWT.None);
	group.setText("Optional Threshold");
	group.setLayout(new GridLayout(1,true));
	
	optionThresholdValue =  PreferencesUtil.getValueWithDefault(CompareST.BUNDLE_NAME,OPTIONAL_THRESHOLD, String.valueOf(DEFAULT_THRESHOLD));
	if(optionThresholdValue == null) {
	    optionThresholdValue = new KeyValueNode(CompareST.BUNDLE_NAME, OPTIONAL_THRESHOLD, DEFAULT_THRESHOLD);
	    PreferencesUtil.storeKeyValueNode(optionThresholdValue);
	}
	new LabeledSliderGroup(parent, SWT.None, OPTIONAL_THRESHOLD_LABEL, optionThresholdValue);
}

public KeyValueNode getKeyValueNode() {
	return optionThresholdValue;
}
}

package de.tu_bs.cs.isf.e4cf.compare.extensions.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.preferences.interfaces.IPreferencePage;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

public class ComparePrefPage implements IPreferencePage {
    private OptionalThresholdPref optionThreshold;
    @Override
    public void createPage(CTabFolder parent, ServiceContainer services) {
	CTabItem tab = new CTabItem(parent, SWT.None);
	tab.setText("Comparison");
	Composite page = new Composite(parent,SWT.None);
	page.setLayout(new GridLayout());
	optionThreshold = new OptionalThresholdPref(page);
	
	tab.setControl(page);
    }

    @Override
    public void store() {
	PreferencesUtil.storeKeyValueNode(optionThreshold.getKeyValueNode());
    }

}

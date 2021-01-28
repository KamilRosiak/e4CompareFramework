package de.tu_bs.e4cf.core.compare.remote.contribution.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.preferences.interfaces.IPreferencePage;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.e4cf.core.compare.remote.contribution.preferences.contribution.RemoteComparisonConfigurationContribution;

public class RemoteComparisonPreferencePage implements IPreferencePage {
	RemoteComparisonConfigurationContribution contribution;

	@Override
	public void createPage(CTabFolder parent, ServiceContainer services) {
		CTabItem tab = new CTabItem(parent, SWT.NONE);
		tab.setText("Remote Comparison");

		Composite page = new Composite(parent, SWT.None);
		page.setLayout(new FillLayout(SWT.VERTICAL));

		contribution = new RemoteComparisonConfigurationContribution(page);

		tab.setControl(page);
	}

	@Override
	public void store() {
		PreferencesUtil.storeKeyValueNode(contribution.getIpValue());
		PreferencesUtil.storeKeyValueNode(contribution.getPortValue());
	}

}

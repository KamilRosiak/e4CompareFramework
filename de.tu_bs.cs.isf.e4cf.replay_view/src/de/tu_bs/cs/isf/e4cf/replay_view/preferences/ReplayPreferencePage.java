package de.tu_bs.cs.isf.e4cf.replay_view.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.tu_bs.cs.isf.e4cf.core.preferences.interfaces.IPreferencePage;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.key_value.KeyValueNode;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.replay_view.util.ReplayViewStringTable;

public class ReplayPreferencePage implements IPreferencePage {
	
	private static final String DEFAUL_REPLAY_PERIOD = "1000";
	private KeyValueNode replayPeriodNode;
	private Text replayPeriodText;

	@Override
	public void createPage(CTabFolder parent, ServiceContainer services) {
		CTabItem tab = new CTabItem(parent, SWT.NONE);
		tab.setText("Replay View");
		//tab.setImage(services.imageService.getImage(SimulinkStringTable.BUNDLE_NAME, MatlabCommandFileTable.MATLAB_ICON_24));
		
		Composite page = new Composite(parent, SWT.NONE);
		page.setLayout(new GridLayout(3, false));
		tab.setControl(page);
		
		replayPeriodNode = PreferencesUtil.getValueWithDefault(ReplayViewStringTable.BUNDLE_NAME, ReplayViewStringTable.REPLAY_PERIOD_KEY, DEFAUL_REPLAY_PERIOD);
		
		createLicensePathPart(services, page);
	}

	private void createLicensePathPart(ServiceContainer services, Composite page) {
		Label replayPeriodLabel = new Label(page, SWT.NONE);
		replayPeriodLabel.setText("Replay period between commands (ms)");
		replayPeriodLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		replayPeriodText = new Text(page, SWT.SINGLE | SWT.BORDER);
		replayPeriodText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		replayPeriodText.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		replayPeriodText.setText(replayPeriodNode.getStringValue());
	}

	@Override
	public void store() {
		// store the value
		replayPeriodNode.setValue(replayPeriodText.getText());
		PreferencesUtil.storeKeyValueNode(replayPeriodNode);
	}

}

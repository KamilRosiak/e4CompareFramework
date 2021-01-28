package de.tu_bs.e4cf.core.compare.remote.contribution.preferences.contribution;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.tu_bs.cs.isf.e4cf.core.compare.remote.config.RemoteComparisonStringTable;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.key_value.KeyValueNode;

public class RemoteComparisonConfigurationContribution {
	private KeyValueNode ipConfiguration;
	private KeyValueNode portConfiguration;
	private Text textFieldIp;
	private Text textFieldPort;

	public RemoteComparisonConfigurationContribution(Composite parent) {
		createControl(parent);
	}

	private void createControl(Composite parent) {
		Group group = new Group(parent, SWT.None);
		group.setText("Connection");
		group.setLayout(new GridLayout(3, false));

		ipConfiguration = PreferencesUtil.getValueWithDefault(RemoteComparisonStringTable.BUNDLE_NAME,
				RemoteComparisonStringTable.IP_KEY, RemoteComparisonStringTable.DEFAULT_IP);
		portConfiguration = PreferencesUtil.getValueWithDefault(RemoteComparisonStringTable.BUNDLE_NAME,
				RemoteComparisonStringTable.PORT_KEY, RemoteComparisonStringTable.DEFAULT_PORT);

		textFieldIp = createTextField(group, "Address", ipConfiguration);
		textFieldPort = createTextField(group, "Port", portConfiguration);
	}

	private Text createTextField(Group group, String label, KeyValueNode keyValueNode) {

		Label uriLabel = new Label(group, SWT.NONE);
		uriLabel.setText(label);
		uriLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		Text textField = new Text(group, SWT.SINGLE | SWT.BORDER);
		textField.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		textField.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		textField.setText(keyValueNode.getStringValue());
		return textField;
	}

	public KeyValueNode getIpValue() {
		ipConfiguration.setValue(textFieldIp.getText());
		return ipConfiguration;
	}

	public KeyValueNode getPortValue() {
		portConfiguration.setValue(textFieldPort.getText());
		return portConfiguration;
	}
}


package de.tu_bs.cs.isf.e4cf.core.parts.license_view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import de.tu_bs.cs.isf.e4cf.core.license.E4License;
import de.tu_bs.cs.isf.e4cf.core.parts.perspective_switch.commands.SwitchPerspectiveCommand;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.gui.LabeledDecsion;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.key_value.KeyValueNode;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CFileTable;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

public class LicenseView {
	@Inject private ServiceContainer services;
	@Inject private MApplication app;
	@Inject private EPartService partService;
	@Inject private EModelService modelService;

	private static String licenseForm ="";

	@PostConstruct
	public void postConstruct(Composite parent) {
		KeyValueNode prefKeyValue = new KeyValueNode(E4CStringTable.LICENSE_AGREEMENT_KEY, E4CStringTable.DEFAULT_BUNDLE_NAME);
		
		if(prefKeyValue.getBoolValue()) {
			parent.setLayout(new GridLayout(1,true));
			validateLicense();
			
			GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
			
			Text txt = new Text(parent, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
			txt.setEditable(false);
			txt.setBackground(new Color(Display.getCurrent(),255,255,255));
			txt.setText(licenseForm);
			
			txt.setLayoutData(gridData);
			gridData = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
			LabeledDecsion labDecsion = new LabeledDecsion(parent, SWT.None,"Show message again?", prefKeyValue);
			labDecsion.setLayoutData(gridData);
			
			Composite buttonArea = new Composite(parent, SWT.NONE);
			buttonArea.setLayout(new GridLayout(2,false));
			
			
			
			Button acceptBtn = new Button(buttonArea, SWT.PUSH);
			acceptBtn.setText("Accept");
			acceptBtn.addListener(SWT.Selection, e -> {
				PreferencesUtil.storeKeyValueNode(labDecsion.getValueNode());
				services.partService.setTrimToolBarVisibility(true);
				new SwitchPerspectiveCommand(app, partService, modelService, E4CStringTable.PERSPECTIVE_DEFAULT);
			});
			
			
			Button cancelBtn = new Button(buttonArea, SWT.PUSH);
			cancelBtn.setText("Cancel");
			cancelBtn.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
			cancelBtn.addListener(SWT.Selection,e-> {
				Display.getCurrent().getActiveShell().dispose();
			});
			
			services.partService.setTrimToolBarVisibility(false);
			parent.layout();	
		} else {
			new SwitchPerspectiveCommand(app, partService, modelService, E4CStringTable.PERSPECTIVE_DEFAULT);
		}
	}
	
	@Focus
	public void onFocus() {
		
	}

	public void validateLicense() {
		InputStream licenseStream = E4License.class.getResourceAsStream(E4CFileTable.LICENSE_AGREEMENT);
			if (licenseStream != null) {
				BufferedReader br = new BufferedReader(new InputStreamReader(licenseStream));
				String line = "";
				try {
					while ((line = br.readLine()) != null) {
						if (line.isEmpty()) {
							licenseForm += "\n\n";
						} else {
							licenseForm += line;						
						}
					}
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
	}	
	
}
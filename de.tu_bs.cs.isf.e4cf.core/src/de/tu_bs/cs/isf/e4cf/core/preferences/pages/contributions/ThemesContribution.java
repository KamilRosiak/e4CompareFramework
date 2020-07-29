package de.tu_bs.cs.isf.e4cf.core.preferences.pages.contributions;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.key_value.KeyValueNode;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

public class ThemesContribution  {
	private KeyValueNode themesValue;
	private ServiceContainer services;
	
	public ThemesContribution(Composite parent, int style, ServiceContainer services) {
		this.services = services;
		createControl(parent);
	}
	
	private void createControl(Composite parent) {
		Group group = new Group(parent, SWT.None);
		group.setText("Themes");
		group.setLayout(new GridLayout(1,true));

		themesValue =  PreferencesUtil.getValueWithDefault(E4CStringTable.DEFAULT_BUNDLE_NAME, E4CStringTable.THEME_KEY, E4CStringTable.DEFAULT_THEME);

		IExtensionRegistry reg = Platform.getExtensionRegistry();
	    IConfigurationElement[] configs;
	    configs = reg.getConfigurationElementsFor(E4CStringTable.THEME_EXTENSION_POINT);
	    
	    ComboViewer combo = new ComboViewer(group,SWT.READ_ONLY);
	    combo.getCombo().setLayoutData(new GridData(SWT.FILL,SWT.CENTER, true, false));

	    combo.setLabelProvider(new LabelProvider() {
	    	 public String getText(Object element) {
                 return ((IConfigurationElement) element).getAttribute("label");
           }
	    });
	    
	    combo.getCombo().addListener(SWT.Selection, e -> {
	    	Object selection = ((IStructuredSelection)combo.getSelection()).getFirstElement();
	    	String id = ((IConfigurationElement)selection).getAttribute("id");
	    	services.partService.switchTheme(id);
	    	themesValue.setValue(id);
	    });
	    
	    //Fill Combo with content from extensions
	    for(IConfigurationElement config : configs) {
	    	if(config.getAttribute("id") != null) {
	    		combo.add(config);
	    		if(config.getAttribute("id").equals(themesValue.getStringValue())) {
	    			combo.setSelection(new StructuredSelection(config));
	    		}
			}
	    } 
	}
	
	public KeyValueNode getKeyValueNode() {
		return themesValue;
	}
}

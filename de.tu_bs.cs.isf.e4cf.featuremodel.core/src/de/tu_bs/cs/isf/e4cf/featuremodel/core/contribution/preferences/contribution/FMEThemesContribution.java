package de.tu_bs.cs.isf.e4cf.featuremodel.core.contribution.preferences.contribution;

import java.util.List;

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
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.theme.interfaces.IFeatureEditorTheme;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.theme.themes.DefaultTheme;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * This is a contribution to the preference page and provides switching of themes.
 * @author {Kamil Rosiak}
 *
 */
public class FMEThemesContribution {
	private KeyValueNode themesValue;
	private ServiceContainer services;
	
	public FMEThemesContribution(Composite parent, int style, ServiceContainer services) {
		this.services = services;
		createControl(parent);
	}
	
	private void createControl(Composite parent) {
		Group group = new Group(parent, SWT.None);
		group.setText("Themes");
		group.setLayout(new GridLayout(1,true));

		themesValue =  PreferencesUtil.getValueWithDefault(FDStringTable.BUNDLE_NAME, FDStringTable.FME_THEME_KEY, DefaultTheme.DEFAULT_THEME);

	    ComboViewer combo = new ComboViewer(group,SWT.READ_ONLY);
	    combo.getCombo().setLayoutData(new GridData(SWT.FILL,SWT.CENTER, true, false));
	    
	    //show the name of the theme by calling the getThemeName method.
	    combo.setLabelProvider(new LabelProvider() {
	    	 public String getText(Object element) {
                 return ((IFeatureEditorTheme) element).getThemeName();
           }
	    });
	    
	    //Fill Combo with content from extensions
	    for(IFeatureEditorTheme theme : getExtensions()) {
	    	combo.add(theme);
	    	if(theme.getCSSLocation().equals(themesValue.getStringValue())) {
	    		combo.setSelection(new StructuredSelection(theme));
	    	}
	    } 
	    
	    //When switch the selection send an event to switch the theme.
	    combo.getCombo().addListener(SWT.Selection, e -> {
	    	Object selection = ((IStructuredSelection)combo.getSelection()).getFirstElement();
	    	String cssLocation = ((IFeatureEditorTheme)selection).getCSSLocation();
	    	services.eventBroker.send(FDEventTable.SET_FDE_THEME, cssLocation);
	    	themesValue.setValue(cssLocation);
	    });
	}
	
	/**
	 * This method returns a List with all registered themes.
	 */
	public List<IFeatureEditorTheme> getExtensions() {
		List<Object> attrExtensions = RCPContentProvider.getInstanceFromBundle(FDStringTable.FME_THEME_EXTENSION,FDStringTable.FME_THEME_ATTR);
		ObservableList<IFeatureEditorTheme> themeList = FXCollections.observableArrayList();

		for(Object theme : attrExtensions) {
			if(theme instanceof IFeatureEditorTheme) {
				IFeatureEditorTheme feTheme  = (IFeatureEditorTheme)theme;
				themeList.add(feTheme);
			}
		}
		return themeList;
	}
	
	public KeyValueNode getKeyValueNode() {
		return themesValue;
	}
}

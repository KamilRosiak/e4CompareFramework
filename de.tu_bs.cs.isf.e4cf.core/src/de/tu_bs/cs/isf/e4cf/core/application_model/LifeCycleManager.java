package de.tu_bs.cs.isf.e4cf.core.application_model;

import java.io.IOException;

import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessAdditions;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.key_value.KeyValueNode;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;

public class LifeCycleManager {

    @PostContextCreate
    public void postContextCreate(IThemeEngine engine,EPartService partService, MApplication app) throws IllegalStateException, IOException {
    	//Load Theme Settings
    	KeyValueNode themesValue =  PreferencesUtil.getValueWithDefault(E4CStringTable.DEFAULT_BUNDLE_NAME, E4CStringTable.THEME_KEY, E4CStringTable.DEFAULT_THEME);
    	engine.setTheme(themesValue.getStringValue(), true);
	
    }
    
    @ProcessAdditions
    public void processAdditions() {
    	
    }  
    
}

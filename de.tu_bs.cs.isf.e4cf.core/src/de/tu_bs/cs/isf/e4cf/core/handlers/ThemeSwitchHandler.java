package de.tu_bs.cs.isf.e4cf.core.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;

import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;

public class ThemeSwitchHandler {
	
	
	    @Execute
	    public void switchTheme(IThemeEngine engine) {
	    	if(engine.getActiveTheme() == null) {
	    		engine.setTheme(E4CStringTable.DEFAULT_THEME, true);
	    	}
	        if (!engine.getActiveTheme().getId().equals(E4CStringTable.DEFAULT_THEME)){
	            // second argument defines that change is
	            // persisted and restored on restart
	            engine.setTheme(E4CStringTable.DEFAULT_THEME, true);
	        } else {
	            engine.setTheme(E4CStringTable.DARK_THEME, true);
	        }
	    }
}

package de.tu_bs.cs.isf.e4cf.core.preferences.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.e4cf.core.gui.swt.util.SWTGuiBuilder;
import de.tu_bs.cs.isf.e4cf.core.preferences.PreferenceManagerController;
import de.tu_bs.cs.isf.e4cf.core.preferences.interfaces.IPreferencePage;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CFileTable;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

public class PreferenceManagerView {
	private final static int WINDOW_WIDTH = 600;
	private final static int WINDOW_HEIGHT = 400;

	private Shell preferenceWindow;
	private ServiceContainer services;
	private List<IPreferencePage> pages;

	
	public PreferenceManagerView(PreferenceManagerController controller, ServiceContainer services) {
		this.services = services;
		pages = new ArrayList<IPreferencePage>();
		createControl();
	}
	
	public void createControl() {
		preferenceWindow = new Shell(Display.getCurrent());
		preferenceWindow.setSize(new Point(WINDOW_WIDTH, WINDOW_HEIGHT));
		preferenceWindow.setLayout(new GridLayout(1, false));
		preferenceWindow.setImage(services.imageService.getImage(E4CStringTable.DEFAULT_BUNDLE_NAME, E4CFileTable.PREFERECES_16_ICON));
		preferenceWindow.setText("Preferences");
		CTabFolder tabFolder = new CTabFolder(preferenceWindow, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tabFolder.setLayout(new FillLayout());
		tabFolder.addListener(SWT.Selection, e -> {
			preferenceWindow.pack();
		});
		loadPages(tabFolder);
		createControlArea(preferenceWindow);
		preferenceWindow.open();
	}
	
	private void createControlArea(Shell parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 1));
		
		SWTGuiBuilder.createButton(composite, "Apply and Close", e->{
			storeValues();
			preferenceWindow.dispose();
		});
		
		SWTGuiBuilder.createButton(composite, "Cancel", e-> {
			preferenceWindow.dispose();
		});	
	}
	
	/**
	 * This method loads the pages from extension point
	 */
	private void loadPages(CTabFolder tabFolder) {
		List<Object> objects = RCPContentProvider.getInstanceFromBundle(E4CStringTable.PREFERENCE_PAGE_SYMBOLIC_NAME, E4CStringTable.PREFERENCE_PAGE_ATTR_EXTENSION);
		for(Object obj : objects) {
			if(obj instanceof IPreferencePage) {
				IPreferencePage prePage = (IPreferencePage)obj;
				pages.add(prePage);
				prePage.createPage(tabFolder,services);
			}	
		}
	}
	
	/**
	 * This method calls the store method for all pages.
	 */
	private void storeValues() {
		for(IPreferencePage page : pages) {
			page.store();
		}
	}
}

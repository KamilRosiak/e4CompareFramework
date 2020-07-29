package de.tu_bs.cs.isf.e4cf.core.preferences.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.preferences.interfaces.IPreferencePage;
import de.tu_bs.cs.isf.e4cf.core.preferences.pages.contributions.LicensContribution;
import de.tu_bs.cs.isf.e4cf.core.preferences.pages.contributions.ThemesContribution;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CFileTable;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

public class GeneralPreferencePage implements IPreferencePage {
	private LicensContribution licensContribution;
	private ThemesContribution themesContribution;
	
	@Override
	public void createPage(CTabFolder parent, ServiceContainer services) {
		CTabItem tab = new CTabItem(parent, SWT.NONE);
		tab.setText("General");
		tab.setImage(services.imageService.getImage(E4CStringTable.DEFAULT_BUNDLE_NAME,E4CFileTable.RED_SPLAT_16_ICON_PATH));
		
		Composite page = new Composite(parent,SWT.None);
		page.setLayout(new FillLayout(SWT.VERTICAL));
		

		licensContribution = new LicensContribution(page);
		//Themes 
		themesContribution = new ThemesContribution(page, SWT.NONE, services);
		
		tab.setControl(page);
	}

	@Override
	public void store() {
		PreferencesUtil.storeKeyValueNode(licensContribution.getKeyValueNode());
		PreferencesUtil.storeKeyValueNode(themesContribution.getKeyValueNode());
	}
	
}

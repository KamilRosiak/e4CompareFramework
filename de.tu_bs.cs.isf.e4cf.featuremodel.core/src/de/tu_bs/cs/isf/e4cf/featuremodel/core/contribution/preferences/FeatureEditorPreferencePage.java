package de.tu_bs.cs.isf.e4cf.featuremodel.core.contribution.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.preferences.interfaces.IPreferencePage;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.contribution.preferences.contribution.FMEThemesContribution;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDFileTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
/**
 * This is the main preferences page for the FeatureModelEditor.
 * @author {Kamil Rosiak}
 *
 */
public class FeatureEditorPreferencePage implements IPreferencePage {
	FMEThemesContribution themeContribution;
	
	
	@Override
	public void createPage(CTabFolder parent, ServiceContainer services) {
		CTabItem tab = new CTabItem(parent, SWT.NONE);
		tab.setText("Feature Diagram Editor");
		tab.setImage(services.imageService.getImage(FDStringTable.BUNDLE_NAME, FDFileTable.FME_ICON_16));
		
		Composite page = new Composite(parent,SWT.None);
		page.setLayout(new FillLayout(SWT.VERTICAL));
		
		themeContribution = new FMEThemesContribution(page, SWT.None, services);
		
		tab.setControl(page);
	}

	@Override
	public void store() {
		PreferencesUtil.storeKeyValueNode(themeContribution.getKeyValueNode());	
	}

}

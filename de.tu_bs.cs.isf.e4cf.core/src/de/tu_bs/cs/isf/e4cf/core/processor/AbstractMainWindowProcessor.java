package de.tu_bs.cs.isf.e4cf.core.processor;

import java.net.URL;

import javax.inject.Inject;

import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.osgi.framework.Bundle;

import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;

/**
 * This template helps to customize the application window with its text and an icon.
 * @author Kamil Rosiak
 *
 */
public class AbstractMainWindowProcessor {
	private String windowName;
	private String bundleName;
	private String iconName;
	@Inject MApplication application;
	@Inject EModelService modelService;
	@Inject RCPImageService imgService;
	
	public AbstractMainWindowProcessor(String windowName,String iconBundleName,String iconName) {
		setWindowName(windowName);
		setBundleName(iconBundleName);
		setIconName(iconName);
	}

	@Execute
	public void execute() {
		MWindow window = (MWindow)modelService.find(E4CStringTable.MAIN_WINDOW_ID, application);
		window.setLabel(windowName);
		Bundle bundle = Platform.getBundle(bundleName);
		URL url = bundle.getEntry(iconName);
		window.setIconURI(url.toString());
	}
	
	public String getWindowName() {
		return windowName;
	}

	public void setWindowName(String windowName) {
		this.windowName = windowName;
	}

	public String getBundleName() {
		return bundleName;
	}

	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}
}

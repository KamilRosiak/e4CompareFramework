
package de.tu_bs.cs.isf.e4cf.core.parts.scenario_view;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class ScenarioView {
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		parent.setLayout(new GridLayout(1, true));
		
		int videoWidth = 800, videoHeight = 400;
		
		Browser browser;
		try {
			browser = new Browser(parent, SWT.NONE);
			browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			return;
		}
		
		String embeddedVideo = "<iframe "
				+ "width=\""+videoWidth+"\" "
				+ "height=\""+videoHeight+"\" "
				+ "src=\"https://www.youtube.com/embed/WoHTpSVt5k4\" "
				+ "frameborder=\"0\" "
				+ "allow=\"autoplay; "
				+ "encrypted-media\" "
				+ "allowfullscreen>"
				+ "</iframe>";
		
		String html = "<!DOCTYPE html> <HTML><HEAD><TITLE>HTML Test</TITLE></HEAD><BODY>";
		html += embeddedVideo;
		html += "</BODY></HTML>";
		browser.setText(html);
	}

	@PreDestroy
	public void preDestroy() {
		
	}

	@Focus
	public void onFocus() {
		
	}

}
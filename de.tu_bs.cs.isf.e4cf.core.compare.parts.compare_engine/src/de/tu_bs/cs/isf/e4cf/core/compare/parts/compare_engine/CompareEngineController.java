package de.tu_bs.cs.isf.e4cf.core.compare.parts.compare_engine;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.compare.parts.compare_engine.view.CompareEngineView;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.embed.swt.FXCanvas;

public class CompareEngineController {

	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services) {
		new CompareEngineView(new FXCanvas(parent, SWT.NONE), services);
	}
}

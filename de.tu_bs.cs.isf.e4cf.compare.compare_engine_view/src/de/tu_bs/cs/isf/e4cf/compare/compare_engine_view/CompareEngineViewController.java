 
package de.tu_bs.cs.isf.e4cf.compare.compare_engine_view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.string_table.CompareST;
import de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.view.CompareEngineView;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.embed.swt.FXCanvas;

public class CompareEngineViewController {
	CompareEngineView view;

	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services) {
		view = new CompareEngineView(new FXCanvas(parent, SWT.None), services);
	}
	
	@Optional
	@Inject
	public void showArtifacts(@UIEventTopic(CompareST.LOAD_ARTIFACTS_EVENET) List<Tree> artifacts) {
		view.showArtifacts(artifacts);
	}
	
	
}
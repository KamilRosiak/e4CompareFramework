package de.tu_bs.cs.isf.e4cf.featuremodel.constraint_view;


import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import FeatureDiagram.FeatureDiagramm;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.constraint_view.view.ConstraintView;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.FeatureModelEditorController;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import javafx.embed.swt.FXCanvas;

public class ConstraintViewController {
	private ServiceContainer services;
	private ConstraintView view;
	private FeatureDiagramm currentModel;
	
	@PostConstruct
	public void createPartControl(Composite parent, ServiceContainer services) {
		this.setServices(services);
		view = new ConstraintView(this, new FXCanvas(parent, SWT.NONE));
	}

	public ServiceContainer getServices() {
		return services;
	}

	public void setServices(ServiceContainer services) {
		this.services = services;
	}
	
	@Focus
	public void updateView() {
		try {
			FeatureModelEditorController fmec = ContextInjectionFactory.make(FeatureModelEditorController.class, EclipseContextFactory.create());
			currentModel = fmec.getCurrentFeatureDiagram();
			view.showConstraints(fmec.getCurrentFeatureDiagram().getConstraints());
		} catch (Exception e) {
			RCPMessageProvider.errorMessage("Error", "No FeatureModel Loaded");
		}
		
	}
	
	@Optional
	@Inject 
	public void showConstraints(@UIEventTopic(FDEventTable.SHOW_CONSTRAINT_EVENT) String event) {
		updateView();
	}
	
}

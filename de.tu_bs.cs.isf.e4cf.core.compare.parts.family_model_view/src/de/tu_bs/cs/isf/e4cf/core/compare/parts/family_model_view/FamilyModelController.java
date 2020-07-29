 
package de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.view.FamilyModelView;
import de.tu_bs.cs.isf.e4cf.core.compare.string_table.E4CompareEventTable;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import familyModel.FamilyModel;
import javafx.embed.swt.FXCanvas;

public class FamilyModelController {
	private ServiceContainer services;
	private FamilyModelView view;
	
	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services) {
		this.setServices(services);
		FXCanvas canvas = new FXCanvas(parent, SWT.NONE);
		view = new FamilyModelView(canvas, services);
	}
	
	@Focus
	public void onFocus(ServiceContainer services) {
		services.rcpSelectionService.selectionService.setSelection(view.getTree().getSelectionModel().getSelectedItem());
	}

	public ServiceContainer getServices() {
		return services;
	}

	public void setServices(ServiceContainer services) {
		this.services = services;
	}
	
	@Inject
	@Optional
	public void showFamilyModel(@UIEventTopic(E4CompareEventTable.SHOW_FAMILY_MODEL_EVENT) FamilyModel model) {
		view.showFamilyModel(model);
	}
	
}
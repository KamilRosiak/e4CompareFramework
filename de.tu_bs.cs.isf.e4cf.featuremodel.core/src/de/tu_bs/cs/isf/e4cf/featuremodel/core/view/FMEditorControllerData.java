package de.tu_bs.cs.isf.e4cf.featuremodel.core.view;

import java.util.List;

import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.error.ErrorListener;
import javafx.scene.control.TabPane;

public class FMEditorControllerData {
	public TabPane tabPane;
	public ServiceContainer services;
	public List<ErrorListener> errorListeners;

	public FMEditorControllerData() {
	}
}
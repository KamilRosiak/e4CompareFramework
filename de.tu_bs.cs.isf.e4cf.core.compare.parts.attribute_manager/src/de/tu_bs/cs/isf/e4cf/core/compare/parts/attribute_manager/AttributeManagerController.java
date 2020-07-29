package de.tu_bs.cs.isf.e4cf.core.compare.parts.attribute_manager;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.compare.parts.attribute_manager.view.AttributeManagerView;
import de.tu_bs.cs.isf.e4cf.core.compare.string_table.E4CompareStringTable;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

public class AttributeManagerController {
	private AttributeManagerView view;

	@PostConstruct
	public void createPartControl(Composite parent , ServiceContainer serviceContainer, EMenuService menuService) {
		view = new AttributeManagerView(this, parent, serviceContainer);
		menuService.registerContextMenu(view.getTree(), E4CompareStringTable.ATTRIBUTE_MANAGER_POPUP_MENU_ID);
	}

	public AttributeManagerView getView() {
		return view;
	}

	public void setView(AttributeManagerView view) {
		this.view = view;
	}
	
	@Focus
	public void onFocus(ESelectionService selectionService) {
		selectionService.setSelection(view.getTree());
		view.getTree().setFocus();
	}
}

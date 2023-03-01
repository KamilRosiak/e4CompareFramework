package de.tu_bs.cs.isf.e4cf.featuremodel.core.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.error.ErrorListener;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.FeatureDiagram;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.FMEditorTab;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.TabView;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;

public class TabController {
	private ServiceContainer services;
	private List<ErrorListener> errorListeners;
	
	private TabView view;
	
	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services, EMenuService menuService) {
		this.services = services;
		this.errorListeners = new ArrayList<>();
		this.view = new TabView(
			parent, 
			ui -> menuService.registerContextMenu(ui, FDStringTable.FD_TAB_VIEW_CONTEXT_MENU_ID)
		);
		
		this.createTab(FDStringTable.FD_DEFAULT_NAME);
	}
	
	public FMEditorTab createTab(String tabTitle) {
		EventHandler<Event> onTabClose = event -> {
			this.currentEditor().askToSave();		
			// don't allow the tabPane to be empty
			if (this.view.tabCount() == 1) {
				createTab(FDStringTable.FD_DEFAULT_NAME);
			}
		};
		
		FMEditorTab tab = new FMEditorTab(tabTitle, services, onTabClose);
		this.view.addTab(tab);
		
		return tab;		
	}
	
	private EditorController currentEditor() {
		return this.view.currentTab().editor();
	}
	
	@Optional
	@Inject
	public void loadFeatureDiagram(@UIEventTopic(FDEventTable.LOAD_FEATURE_DIAGRAM) FeatureDiagram diagram) {
		FMEditorTab newTab = this.createTab(diagram.getName());
		newTab.editor().setFeatureDiagram(diagram);
	}
	
	

}

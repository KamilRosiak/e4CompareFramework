package de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.FMEditorTab;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.TabView;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.error.ErrorListener;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.FeatureDiagram;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.EventBroker;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

@Singleton
@Creatable
public class TabController {
	private ServiceContainer services;
	@SuppressWarnings("unused")
	private List<ErrorListener> errorListeners;

	private TabView view;

	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services, EMenuService menuService) {
		this.services = services;
		EventBroker.set(services);
		this.errorListeners = new ArrayList<>();
		Map<String, MenuItem> menuItems = getMenuItems();
		Consumer<Widget> contextMenuTarget = ui -> menuService.registerContextMenu(ui, FDStringTable.FD_TAB_VIEW_CONTEXT_MENU_ID);
		this.view = new TabView(parent, menuItems, contextMenuTarget);
		
		// create initial tab
		this.createTab(FDStringTable.FD_DEFAULT_FM_NAME);
			
	}
	
	
	
	
	private Map<String, MenuItem> getMenuItems() {
		Map<String, MenuItem> menuItems = new HashMap<>();
		MenuItem newTab = new MenuItem("New Tab");
		newTab.setOnAction(e -> this.createTab(FDStringTable.FD_DEFAULT_FM_NAME));
		MenuItem save = new MenuItem("Save");
		save.setOnAction(e -> this.saveCurrentTab());
		MenuItem load = new MenuItem("Load");
		load.setOnAction(e -> {
			
			FeatureDiagram newDiagram;
			try {
				newDiagram = FeatureDiagram.loadFromFile();
				this.loadFeatureDiagram(newDiagram);
			} catch (ClassNotFoundException | IOException e1) {
				//e1.printStackTrace();
			}			
		});
		menuItems.put("new", newTab);
		menuItems.put("save", save);
		menuItems.put("load", load);
		return menuItems;
	}
	
	private EditorController currentEditor() {
		return this.view.currentTab().editor();
	}

	public FMEditorTab createTab(String tabTitle) {
		EventHandler<Event> onTabClose = event -> {
			// this.currentEditor().askToSave();
			if (this.view.tabCount() == 1) {
				createTab(FDStringTable.FD_DEFAULT_FM_NAME);
			}
		};

		FMEditorTab tab = new FMEditorTab(tabTitle, services, onTabClose);
		this.view.addTab(tab);

		return tab;
	}

	public void saveCurrentTab() {
		currentEditor().saveDiagram();
	}

	@Optional
	@Inject
	public void loadFeatureDiagram(@UIEventTopic(FDEventTable.LOAD_FEATURE_DIAGRAM) FeatureDiagram diagram) {
		FMEditorTab newTab = this.createTab(diagram.getName());
		//services.partService.showPart(FDStringTable.CONSTRAINT_VIEW);
		services.eventBroker.send(FDEventTable.SHOW_CONSTRAINT_EVENT, diagram);
		newTab.editor().setFeatureDiagram(diagram);
	}
	
	public FeatureDiagram getCurrentFeatureDiagram() {
		return this.currentEditor().getFeatureDiagram();
	}

}

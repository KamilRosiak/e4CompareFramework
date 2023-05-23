package de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view;

import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.EventBroker;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class TabView {
	private Scene scene;
	private MenuBar menuBar = new MenuBar();
	private TabPane tabPane = new TabPane();

	public TabView(Composite parent, Map<String, MenuItem> menuItems, Consumer<Widget> contextMenuTarget) {
		BorderPane container = new BorderPane();
		container.setTop(menuBar);
		container.setCenter(tabPane);
		this.scene = new Scene(container);

		// set the constraint view to show the constraints of the current diagram
		tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
			EventBroker.getServices().eventBroker.post(FDEventTable.SHOW_CONSTRAINT_EVENT,
					this.currentTab().editor().getFeatureDiagram());
		});

		// create menu items in menu bar
		Menu menu = new Menu("File");
		for (Map.Entry<String, MenuItem> item : menuItems.entrySet()) {
			menu.getItems().add(item.getValue());
		}
		menuBar.getMenus().add(menu);

		FXCanvas canvas = new FXCanvas(parent, SWT.NONE);
		canvas.setScene(this.scene);
		contextMenuTarget.accept(canvas);
	}

	public void addTab(FMEditorTab tab) {
		this.tabPane.getTabs().add(tab);
		this.tabPane.getSelectionModel().select(tab);
	}

	public FMEditorTab currentTab() {
		return (FMEditorTab) this.tabPane.getSelectionModel().getSelectedItem();
	}

	public int tabCount() {
		return this.tabPane.getTabs().size();
	}

}

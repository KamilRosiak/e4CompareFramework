package de.tu_bs.cs.isf.e4cf.featuremodel.core.view;

import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;

public class TabView {
	private Scene scene;
	private TabPane tabPane = new TabPane();
	
	public TabView(Composite parent, Consumer<Widget> uiConsumer) {
		this.scene = new Scene(this.tabPane);
		FXCanvas canvas = new FXCanvas(parent, SWT.NONE);
		canvas.setScene(this.scene);
		uiConsumer.accept(canvas);
	}
	
	public void addTab(FMEditorTab tab) {
		this.tabPane.getTabs().add(tab);
	}
	
	public FMEditorTab currentTab() {
		return (FMEditorTab) this.tabPane.getSelectionModel().getSelectedItem();
	}
	
	public int tabCount() {
		return this.tabPane.getTabs().size();
	}

}

package de.tu_bs.cs.isf.e4cf.core.gui.java_fx.templates;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.swt.widgets.Display;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.JavaFXBuilder;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Abstract Dialog with two buttons and a BorderPane use the setCenter method to add content. 
 * The stage opens on the current mouse position.
 * @author Kamil Rosiak
 *
 */
@Creatable
public class AbstractDialog {
	private static final String LEFT_BUTTON_DEFAULT_LABEL = "Cancel";
	private static final String RIGHT_BUTTON_DEFAULT_LABEL = "OK";
	public ServiceContainer services;
	private Stage stage;
	private Scene scene;
	private BorderPane layout;
	private Button leftButton;
	private Button rightButton;
	public DoubleProperty windowWidthProp;

	public AbstractDialog() {
		services = ContextInjectionFactory.make(ServiceContainer.class, EclipseContextFactory.create());
	}
	
	public AbstractDialog(String title, double width, double height) {
		this();
		createControl(title, width, height);
	}
	
	public void createControl(String title, double width, double height) {
		createScene(title, width, height);
		createButtons();
	}
	
	/**
	 * This method creates the Window
	 */
	private void createScene(String title, double width, double height) {
		layout = new BorderPane();
		scene = new Scene(layout, width, height);
        stage = new Stage();

        stage.setTitle(title);
        //bind the width of the window to a property
        windowWidthProp = new SimpleDoubleProperty();
        windowWidthProp.bind(getScene().widthProperty());
        
        stage.setScene(scene);
	}
	
	/**
	 * Sets the icon of the window
	 */
	 public void setSceneIcon(String icon) {
	    stage.getIcons().add(new Image(icon));
	 }
	 
	/**
	 * This method initializes the buttons and sets default label.
	 */
	private void createButtons() {
		HBox pane = new HBox();
		leftButton = JavaFXBuilder.createButton(LEFT_BUTTON_DEFAULT_LABEL, e-> {
			stage.close();
		});
		rightButton = JavaFXBuilder.createButton(RIGHT_BUTTON_DEFAULT_LABEL);
		
		// Binding the width of the buttons on the HBox
		pane.widthProperty().addListener(e -> {
			rightButton.setPrefWidth(pane.getWidth()*2/3);
			leftButton.setPrefWidth(pane.getWidth()/3);
		});
		
		pane.getChildren().addAll(leftButton, rightButton);
		setBottom(pane);
	}
	
	/**
	 * This method sets the css style of the scene
	 */
	public void setCSS(String location) {
		scene.getStylesheets().add(location);
	}
	
	/**
	 * Opens the window on the current mouse position.
	 */
	public void showStage() {
		showStageOnPosition((double)Display.getCurrent().getCursorLocation().x,(double)Display.getCurrent().getCursorLocation().y);	
	}
	
	/**
	 * Opens the window on given positon.
	 */
	public void showStageOnPosition(double x, double y) {
		stage.setX(x);
		stage.setY(y);
		stage.show();
	}
	
	/**
	 * Set the content of the center.
	 */
	public void setCenter(Node node) {
		layout.setCenter(node);
	}
	
	/**
	 * Set the content of the Bottom.
	 */
	public void setBottom(Node node) {
		layout.setBottom(node);
	}

	public Button getRightButton() {
		return rightButton;
	}

	public void setRightButton(Button rightButton) {
		this.rightButton = rightButton;
	}

	public Button getLeftButton() {
		return leftButton;
	}

	public void setLeftButton(Button leftButton) {
		this.leftButton = leftButton;
	}
	
	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}

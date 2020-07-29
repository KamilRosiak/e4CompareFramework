package de.tu_bs.cs.isf.e4cf.core.gui.java_fx.templates;

import java.io.File;

import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;

public abstract class AbstractLoadResourceDialog extends AbstractDialog {
	private static final double WINDOW_HANDLE_HEIGHT = 50;
	private static final double BOTTOM_TOOLBAR_HEIGHT = 30;
	
	protected GridPane gridPane;
	
	protected String title;
	protected double width;
	protected double rowHeight;
	
	private String resourceString;

	public AbstractLoadResourceDialog(String title, double width, double rowHeight) {
		this.title = title;
		this.width = width;
		this.rowHeight = rowHeight;
	}
	
	public void open() {
		// Initialize the dialog window
		double height = WINDOW_HANDLE_HEIGHT + rowHeight + BOTTOM_TOOLBAR_HEIGHT;
		createControl(title, width, height);		
		
		// Create controls for each resource
		gridPane = new GridPane();
		
		addResourceRows();
		
		
		// if the user is done selecting resource paths, collect and organize the input
		getRightButton().setOnMousePressed(event -> {
			// check if the resource map is sufficient to store the family model
			File resourceFile = new File(resourceString);
			if (!resourceFile.exists()) {
				resourceString = null;
			}
			getStage().close();
		});
		
		getLeftButton().setOnMousePressed(event -> {
			resourceString = null;
			getStage().close();
		});
		
		setCenter(gridPane);
		
		getStage().showAndWait();
	}

	/**
	 * Add the resource rows
	 */
	abstract protected void addResourceRows();

	/**
	 * 
	 * 
	 * @param name the label name display on the left
	 * @param buttonName the button name on the right
	 */
	protected void addResourceRow(String name, String buttonName) {		
		Label label = new Label(name);
		label.prefHeight(rowHeight);
		label.setMaxWidth(Double.MAX_VALUE);
		
		TextField resourceTextField = new TextField();
		resourceTextField.prefHeight(rowHeight);
		resourceTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				Platform.runLater(() -> resourceTextField.end());
			}
		});
		resourceTextField.widthProperty().addListener((observable, oldValue, newValue) -> {
			Platform.runLater(() -> resourceTextField.end());
		});
		resourceTextField.heightProperty().addListener((observable, oldValue, newValue) -> {
			Platform.runLater(() -> resourceTextField.end());
		});
		resourceTextField.selectionProperty().addListener((observable, oldValue, newValue) -> {
			// move text field selection to the end of the text
			if (newValue.getStart() == 0 && newValue.getEnd() == 0 && !resourceTextField.isFocused()) {
				Platform.runLater(() -> resourceTextField.end());
			}
		});
		
		Button button = new Button(buttonName);
		button.prefHeight(rowHeight);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setOnMousePressed(event -> {
			FileChooser fc = new FileChooser();
			
			File dir = new File(RCPContentProvider.getCurrentWorkspacePath());
			fc.setInitialDirectory(dir);
			fc.setTitle("Choose the Family Model");
			File selectedFile = fc.showOpenDialog(this.getStage());
			if (selectedFile != null) {
				resourceTextField.setText(selectedFile.toString());
				resourceTextField.selectEnd();
				resourceString = selectedFile.toString();
			}
		});
		
		gridPane.addRow(0, label, resourceTextField, button);
		
		GridPane.setMargin(label, new Insets(5));
		GridPane.setHgrow(label, Priority.SOMETIMES);
		
		GridPane.setMargin(resourceTextField, new Insets(5));
		GridPane.setHgrow(resourceTextField, Priority.ALWAYS);
		
		GridPane.setMargin(button, new Insets(5));
		GridPane.setHgrow(button, Priority.SOMETIMES);
	}
	
	/**
	 * Returns the the resource path.
	 * 
	 * @param eobject
	 * @return A valid file path or null if the selection was unsuccessful
	 */
	public String getResourcePath() {
		return resourceString;
	}
}

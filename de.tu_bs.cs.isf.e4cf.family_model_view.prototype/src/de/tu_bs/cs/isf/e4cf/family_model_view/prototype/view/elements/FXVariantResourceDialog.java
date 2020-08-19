package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.elements;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.templates.AbstractDialog;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class FXVariantResourceDialog extends AbstractDialog {
	
	private static final double WINDOW_HANDLE_HEIGHT = 50;
	private static final double BOTTOM_TOOLBAR_HEIGHT = 30;
	
	protected FamilyModel fm;
	protected Map<EObject, String> resourceMap;

	protected GridPane gridPane;
	
	protected String title;
	protected double width;
	protected double rowHeight;

	public FXVariantResourceDialog(FamilyModel fm, String title, double width, double rowHeight) {
		this.fm = fm;
		this.title = title;
		this.width = width;
		this.rowHeight = rowHeight;
		this.resourceMap = new HashMap<>();
	}
	
	public void open() {
		// Initialize the dialog window
		double height = WINDOW_HANDLE_HEIGHT + rowHeight * (fm.getVariants().size() + 2) + BOTTOM_TOOLBAR_HEIGHT;
		createControl(title, width, height);
		
		// dialog should block until the user is done
		
		
		// Create controls for each resource
		gridPane = new GridPane();
		// family model resource
		String fmResourcePath = getResourceDirectory(fm);
		addResourceSelectionRow(0, fm.getName(), fmResourcePath, "Select Parent Directory Path", fm);
				
		// if the user is done selecting resource paths, collect and organize the input
		getRightButton().setOnMousePressed(event -> {
			// check if the resource map is sufficient to store the family model
			if (!isValidResourceMap()) {
				resourceMap.clear();
			}
			getStage().close();
		});
		
		getLeftButton().setOnMousePressed(event -> {
			resourceMap.clear();
			getStage().close();
		});
		
		
		setCenter(gridPane);
		
		getStage().showAndWait();
	}

	private String getResourceDirectory(EObject eobject) {
		Resource res = eobject.eResource();
		if (res != null) {
			return res.getURI().trimSegments(1).toFileString();
		} else {
			return "";
		}
	}
	
	/**
	 * 
	 * 
	 * @param row the row in the grid pane 
	 * @param name the label name display on the left
	 * @param resPath the initial resource text in the middle
	 * @param buttonName the button name on the right
	 * @param eobject the associated EObject 
	 */
	private void addResourceSelectionRow(int row, String name, String resPath, String buttonName, EObject eobject) {		
		Label label = new Label(name);
		label.prefHeight(rowHeight);
		label.setMaxWidth(Double.MAX_VALUE);
		
		TextField resourceTextField = new TextField();
		if (resPath != null) {
			resourceTextField.setText(resPath);
			resourceMap.put(eobject, resPath);
		}
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
			
			// With every text change, update the resource entry
			resourceMap.put(eobject, resourceTextField.getText());
		});
		
		Button button = new Button(buttonName);
		button.prefHeight(rowHeight);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setOnMousePressed(event -> {
			DirectoryChooser fc = new DirectoryChooser();
			
			File dir = new File(RCPContentProvider.getCurrentWorkspacePath());
			fc.setInitialDirectory(dir);
			fc.setTitle("Choose a file");
			File selectedFile = fc.showDialog(this.getStage());
			if (selectedFile != null) {
				resourceTextField.setText(selectedFile.toString());
				resourceTextField.selectEnd();
				resourceMap.put(eobject, resourceTextField.getText());
			}
		});
		
		gridPane.addRow(row, label, resourceTextField, button);
		
		GridPane.setMargin(label, new Insets(5));
		GridPane.setHgrow(label, Priority.SOMETIMES);
		
		GridPane.setMargin(resourceTextField, new Insets(5));
		GridPane.setHgrow(resourceTextField, Priority.ALWAYS);
		
		GridPane.setMargin(button, new Insets(5));
		GridPane.setHgrow(button, Priority.SOMETIMES);
	}

	/**
	 * Checks the validity of the user selected resources.
	 * The family model and every referenced variant instance must be assigned to a resource path.
	 * 
	 * @return
	 */
	private boolean isValidResourceMap() {
		if (!isResourceValid(fm)) {
			return false;
		}
		
		return true;
	}

	/**
	 * Validates the existence of a resource entry for the <i>eobject</i> and 
	 * checks if the entry is a valid path in the file system.
	 * 
	 * @param eobject
	 * @return
	 */
	private boolean isResourceValid(EObject eobject) {
		String resPathString = resourceMap.get(eobject);
		if (resPathString != null) {
			try {
				Path fmResPath = Paths.get(resPathString);				
				if (Files.exists(fmResPath.getParent())) {
					return true;
				}
			} catch (InvalidPathException | NullPointerException e) {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Returns the the resource map.
	 * 
	 * @param eobject
	 * @return A non-empty resource map with entries for the family model and the referenced variants 
	 * or an empty map if the dialog finished unsuccessfully 
	 */
	public Map<EObject, String> getResourceMap() {
		return resourceMap;
	}
}

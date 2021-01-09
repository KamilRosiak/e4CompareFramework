package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.dialog;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import de.tu_bs.cs.isf.e4cf.core.file_structure.util.Pair;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.templates.AbstractDialog;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * Resource dialog that allows to select resources as strings.
 * The dialog can be extended by resource selection rows for each resource. A resource is mapped by an id.
 * <br><br>
 * Clients can start by {@link #buildDialog()} to initialize the dialog, followed by 
 * calls to {@link #buildResourceEntry(ResourceEntry)} or {@link #buildSeparator()}
 * in order to add rows. Eventually, the dialog can be opened by {@link #open()}.
 * Clients have to implement {@link #finish(Map)} to post-process the resources.
 * When the dialog closes, clients can obtain resources selected before. 
 * 
 * @author Oliver Urbaniak
 *
 */
public abstract class AbstractResourceRowDialog extends AbstractDialog {
	
	public static class ResourceEntry {
		private String id;
		private String label;
		private String resource;
		private String buttonLabel;
		private Function<Pair<String, String>, Pair<String, String>> resourceSetter;
	
		public ResourceEntry() {
			
		}
		
		public ResourceEntry(String id, String label, String resource, String buttonLabel, 
				Function<Pair<String,String>, Pair<String,String>> resourceSetter) {
			this.id = id;
			this.label = label;
			this.resource = resource;
			this.buttonLabel = buttonLabel;
			this.resourceSetter = resourceSetter;
		}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
		public String getResource() {
			return resource;
		}
		public void setResource(String resource) {
			this.resource = resource;
		}
		public String getButtonLabel() {
			return buttonLabel;
		}
		public void setButtonLabel(String buttonLabel) {
			this.buttonLabel = buttonLabel;
		}
		public Function<Pair<String, String>, Pair<String, String>> getResourceSetter() {
			return resourceSetter;
		}
		public void setResourceSetter(Function<Pair<String, String>, Pair<String, String>> resourceSetter) {
			this.resourceSetter = resourceSetter;
		}
	}
	
	private static final double WINDOW_HANDLE_HEIGHT = 50;
	private static final double BOTTOM_TOOLBAR_HEIGHT = 50;
	
	protected GridPane gridPane;
	
	protected String title;
	protected double width;
	protected double rowHeight;

	private int rowIndex;
	private double height;
	
	private Map<String, String> resourceMap = new HashMap<>();

	public AbstractResourceRowDialog(String title, double width, double rowHeight) {
		this.title = title;
		this.width = width;
		this.rowHeight = rowHeight;
	}
	
	public AbstractResourceRowDialog buildDialog() {
		resourceMap.clear();
		
		// Create controls for each resource
		gridPane = new GridPane();
		
		return this;
	}
	
	public AbstractResourceRowDialog buildResourceEntry(ResourceEntry... entries) {
		for (ResourceEntry entry : entries) {
			buildResourceEntry(entry);
		}
		
		return this;
	}
	
	public AbstractResourceRowDialog buildResourceEntry(ResourceEntry resEntry) {
		int inset = 5;
		
		Label label = new Label(resEntry.getLabel());
		label.prefHeight(rowHeight);
		label.setMinHeight(Region.USE_PREF_SIZE);
		label.setMaxWidth(Double.MAX_VALUE);
		
		// Add label's height after the layout computed
		label.heightProperty().addListener((observable, oldValue, newValue) -> {
			height += rowHeight > newValue.doubleValue() + 2 * inset ? rowHeight : newValue.doubleValue() + 2 * inset;
		});
		
		TextField resourceTextField = new TextField(resEntry.getResource());
		resourceTextField.prefHeight(rowHeight);
		resourceTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				Platform.runLater(() -> resourceTextField.end());
				resourceMap.put(resEntry.getId(), resourceTextField.getText());
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
		
		Button button = new Button(resEntry.getButtonLabel());
		button.prefHeight(rowHeight);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setOnMousePressed(event -> {
			Pair<String, String> entry = new Pair<>(resEntry.getId(), resourceTextField.getText());
			Pair<String, String> resultEntry = resEntry.getResourceSetter().apply(entry);
			if (resultEntry != null) {
				resourceMap.put(resultEntry.first, resultEntry.second);				
				resourceTextField.setText(resultEntry.second);
			}
		});
		
		gridPane.addRow(rowIndex, label, resourceTextField, button);
		
		GridPane.setMargin(label, new Insets(inset));
		GridPane.setHgrow(label, Priority.SOMETIMES);
		GridPane.setMargin(resourceTextField, new Insets(inset));
		GridPane.setHgrow(resourceTextField, Priority.ALWAYS);
		
		GridPane.setMargin(button, new Insets(inset));
		GridPane.setHgrow(button, Priority.SOMETIMES);
		
		resourceMap.put(resEntry.getId(), resEntry.getResource());
		
		rowIndex++;
		
		return this;
	}
	
	public AbstractResourceRowDialog buildInfoText(String infoText) {
		return buildInfoText(infoText, null);
	}
	
	public AbstractResourceRowDialog buildInfoText(String infoText, ImageView image) {
		int vertInset = 10;
		int horInset = 5;
		
		Label infoLabel = new Label(infoText, image);
		infoLabel.setWrapText(true);
		infoLabel.prefHeight(rowHeight);
		infoLabel.setMinHeight(Region.USE_PREF_SIZE);
		infoLabel.setMaxWidth(Double.MAX_VALUE);
		
		// Add label's height after the layout computed
		infoLabel.heightProperty().addListener((observable, oldValue, newValue) -> {
			height += newValue.doubleValue() + 2 * vertInset;
		});
		
		gridPane.addRow(rowIndex, infoLabel);
		
		GridPane.setColumnSpan(infoLabel, 3);
		
		GridPane.setMargin(infoLabel, new Insets(vertInset, horInset, vertInset, horInset));
		GridPane.setHgrow(infoLabel, Priority.SOMETIMES);
		
		rowIndex++;
		
		return this;
	}
	
	public AbstractResourceRowDialog buildSeparator() {
		// Separate family model from variant resource selection
		Separator sep = new Separator(Orientation.HORIZONTAL);
		gridPane.addRow(1, sep);
		
		// Add label's height after the layout computed
		sep.heightProperty().addListener((observable, oldValue, newValue) -> {
			height += newValue.doubleValue() + rowHeight;
		});		

		GridPane.setColumnSpan(sep, 3);
		GridPane.setMargin(sep, new Insets(rowHeight / 2.0, 0, rowHeight / 2.0, 0));
		
		rowIndex++;
		
		return this;
	}

	
	public void open() {		
		createControl(title, width, height);
		
		// if the user is done selecting resource paths, collect and organize the input
		getRightButton().setOnMousePressed(event -> {
			finish(resourceMap);
			getStage().close();
		});
		
		getLeftButton().setOnMousePressed(event -> {
			resourceMap.clear();
			getStage().close();
		});
		
		getStage().setOnCloseRequest(winEvent -> {
			resourceMap.clear();
		});
		
		setCenter(gridPane);
		
		// let scene compute its size
		getScene().getRoot().applyCss();
		getScene().getRoot().layout();
		
		// recompute size
		double totalHeight = WINDOW_HANDLE_HEIGHT + this.height + BOTTOM_TOOLBAR_HEIGHT;
		getStage().setHeight(totalHeight);
		
		getStage().showAndWait();
	}
	
	public abstract void finish(Map<String, String> resourceMap);
	
	public Map<String, String> getResources() {
		return resourceMap;
	}
}

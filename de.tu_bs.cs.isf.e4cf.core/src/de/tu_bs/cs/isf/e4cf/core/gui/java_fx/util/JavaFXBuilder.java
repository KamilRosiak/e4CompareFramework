package de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util;

import java.io.File;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class JavaFXBuilder {
	/**
	 * This method creates and returns a button with a given name and adds an mouse event to it.
	 */
	public static Button createButton(String name, EventHandler<? super MouseEvent> action) {
		Button button = createButton(name);
		button.addEventHandler(MouseEvent.MOUSE_CLICKED, action);
		return button;
	}
	
	/**
	 * This method creates and returns a Button with a given name.
	 */
	public static Button createButton(String name) {
		return new Button(name);
	}
	
	
	public static CheckBox createCheckBox(String description) {
		CheckBox checkBox = new CheckBox(description);
		return checkBox;
	}
	
	
	/**
	 * This method creates a slider with given min, max and default value. 
	 */
	public static Slider createSlider(double min, double max, double defaulValue) {
		Slider slider = new Slider();
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMin(min);
        slider.setMax(max);
        slider.setValue(defaulValue);
		return slider;
	}
	/**
	 * This method opens a FileChooser and returns all selected files
	 * @param path initial path for the chooser
	 * @param title title of the dialog window
	 * @return
	 */
	public static List<File> createFileChooser(String path, String title) {
	    FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle(title);
	    fileChooser.setInitialDirectory(new File(RCPContentProvider.getCurrentWorkspacePath()));
	    List<File> selectedFiles = fileChooser.showOpenMultipleDialog(new Stage());
	    return selectedFiles;
	}
	
}

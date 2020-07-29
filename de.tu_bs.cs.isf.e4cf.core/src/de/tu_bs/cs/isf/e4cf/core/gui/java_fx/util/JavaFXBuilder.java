package de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

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
	
}

package de.tu_bs.cs.isf.e4cf.core.preferences.util.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Slider;

import de.tu_bs.cs.isf.e4cf.core.preferences.util.key_value.KeyValueNode;

public class LabeledSliderGroup {
	private KeyValueNode value;
	private String text;
	private Group group;
	private Slider slider;
	private Label label;
	
	public LabeledSliderGroup(Composite parent, int style, String text, KeyValueNode value) {
		this.value = value;
		createControl(parent, style, text);
	}
	
	private void createControl(Composite parent, int style,String text) {
		group = new Group(parent, style);
		group.setLayout(new GridLayout(3,true));
		group.setText(text);
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		slider = new Slider(group, SWT.HORIZONTAL);
		GridData gd_slider = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_slider.horizontalSpan = 2;

		slider.setLayoutData(gd_slider);
		slider.setMinimum(0);
		slider.setMaximum(110);
		slider.setIncrement(1);
		slider.setSelection((int) value.getFloatValue());
		
		label = new Label(group, SWT.None);
		label.setText(Float.toString((int)value.getFloatValue()));
		
		slider.addListener(SWT.Selection, e-> {
			label.setText(Integer.toString(slider.getSelection()));
			value.setValue(slider.getSelection());
			label.pack();
		});
	}
	
	public Group getGroup() {
		return this.group;
	}
	public void setLayoutData(Object layoutData) {
		group.setLayoutData(layoutData);
	}

	/**
	 * Returns the KeyValueNode
	 */
	public KeyValueNode getValueNode() {
		return value;
	}
	
	/**
	 * Returns the boolean value of the KeyValueNode.
	 */
	public float getValue() {
		return Float.parseFloat(value.getStringValue());
	}
	
	/**
	 * Set the value of the node.
	 * @param value
	 */
	public void setValue(float value) {
		this.value.setValue(String.valueOf(value));
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public void setEnabled(boolean enabled) {
		label.setEnabled(enabled);
		slider.setEnabled(enabled);
	}

}

package de.tu_bs.cs.isf.e4cf.core.preferences.util.gui;

import org.eclipse.nebula.widgets.opal.rangeslider.RangeSlider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import de.tu_bs.cs.isf.e4cf.core.preferences.util.key_value.KeyValueNode;

/**
 * This widget allows us to set a lower and upper value and adjust it by using a slider.
 * @author Kamil Rosiak
 *
 */
public class LabeledRangeSliderGroup {
	private KeyValueNode lowerValue;
	private KeyValueNode upperValue;
	private String text;
	private Group group;
	
	public LabeledRangeSliderGroup(Composite parent, int style, String text, KeyValueNode lowerValue, KeyValueNode upperValue) {
		this.lowerValue = lowerValue;
		this.upperValue = upperValue;
		createControl(parent, style, text);
	}
	
	private void createControl(Composite parent, int style,String text) {
		group = new Group(parent, style);
		group.setLayout(new GridLayout(4,true));
		group.setText(text);
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label lowerLabel = new Label(group, SWT.None);
		lowerLabel.setText(Float.toString(lowerValue.getFloatValue()));
		
		RangeSlider slider = new RangeSlider(group, SWT.HORIZONTAL);
		GridData gd_slider = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_slider.horizontalSpan = 2;

		slider.setLayoutData(gd_slider);
		slider.setMinimum(0);
		slider.setMaximum(100);
		slider.setIncrement(1);
		slider.setLowerValue((int)lowerValue.getFloatValue());
		slider.setUpperValue((int)upperValue.getFloatValue());

		Label upperlabel = new Label(group, SWT.None);
		upperlabel.setText(Float.toString(upperValue.getFloatValue()));
		
		slider.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				lowerLabel.setText(Float.toString(slider.getLowerValue()));
				lowerValue.setValue(Integer.toString(slider.getLowerValue()));
				lowerLabel.pack();
				upperlabel.setText(Float.toString(slider.getUpperValue()));
				upperValue.setValue(Integer.toString(slider.getUpperValue()));
				upperlabel.pack();
			}
		});
	}
	
	public Group getGroup() {
		return this.group;
	}
	public void setLayoutData(Object layoutData) {
		group.setLayoutData(layoutData);
	}

	/**
	 * Returns the lower KeyValueNode
	 */
	public KeyValueNode getLowerValueNode() {
		return lowerValue;
	}
	
	/**
	 * Returns the upper KeyValueNode
	 */
	public KeyValueNode getUpperValueNode() {
		return upperValue;
	}
	
	/**
	 * Returns the 
	 */
	public float getUpperValue() {
		return Float.parseFloat(upperValue.getStringValue());
	}
	
	/**
	 * Returns the 
	 */
	public float getLowerValue() {
		return Float.parseFloat(lowerValue.getStringValue());
	}
	
	/**
	 * Set the value of the upper value node.
	 * @param value
	 */
	public void setUpperValue(float value) {
		this.upperValue.setValue(String.valueOf(value));
	}
	
	/**
	 * Set the value of the lower value node.
	 * @param value
	 */
	public void setLowerValue(float value) {
		this.lowerValue.setValue(String.valueOf(value));
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}

package de.tu_bs.cs.isf.e4cf.core.preferences.util.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import de.tu_bs.cs.isf.e4cf.core.preferences.util.key_value.KeyValueNode;

/**
 * GUI Template for a Label with two radio buttons that can be used in a Preference page or something else to store values.
 * @author {Kamil Rosiak}
 */
public class LabeledDecsion {
	private KeyValueNode value;
	private String text;
	private Composite comp;
	private Button yesBtn;
	private Button noBtn;
	public LabeledDecsion(Composite parent, int style, String text, KeyValueNode value) {
		setText(text);
		this.value = value;
		createControl(parent, style);
	}
	
	private void createControl(Composite parent, int style) {
		comp = new Composite(parent, style);
		comp.setLayout(new GridLayout(3,false));
		comp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label label = new Label(comp, SWT.NONE);
		label.setText(text);
		
		yesBtn = new Button(comp, SWT.RADIO);
		yesBtn.setText("yes");
		
		noBtn = new Button(comp, SWT.RADIO);
		noBtn.setText("no");

		yesBtn.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
		noBtn.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
		
		if(value.getBoolValue()) {
			yesBtn.setSelection(true);
		} else {
			noBtn.setSelection(true);
		}	
		
		// LISTENERS
		yesBtn.addSelectionListener(new SelectionAdapter() {
			
			@Override
            public void widgetSelected(SelectionEvent e) {
                Button source = (Button) e.getSource();
                 
                if(source.getSelection())  {
                	setValue(true);
                }
                else
                	setValue(false);
            }
		});		
	}
	
	public Composite getComposite() {
		return this.comp;
	}
	public void setLayoutData(Object layoutData) {
		comp.setLayoutData(layoutData);
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
	public boolean getValue() {
		return Boolean.parseBoolean(value.getStringValue());
	}
	
	/**
	 * Set the value of the node.
	 * @param value
	 */
	public void setValue(boolean value) {
		this.value.setValue(value);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public Button getYesButton() {
		return yesBtn;
	}
	
	public Button getNoButton() {
		return noBtn;
	}
}

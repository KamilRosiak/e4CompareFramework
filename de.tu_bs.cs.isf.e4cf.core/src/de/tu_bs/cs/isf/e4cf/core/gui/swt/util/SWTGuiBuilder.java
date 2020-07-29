package de.tu_bs.cs.isf.e4cf.core.gui.swt.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;

public class SWTGuiBuilder {
	
	/**
	 * This method creates a button , sets the text and adds a selection listener.
	 */
	public static Button createButton(Composite parent, String text, Listener listener) {
		Button button = new Button(parent, SWT.None);
		button.setText(text);
		button.addListener(SWT.Selection, listener);
		return button;	
	}

	/**
	 * This method creates a button, sets the text adds a selection listener a background and the selection.
	 */
	public static Button createButton(Composite parent, String text, Listener listener, Color background, Boolean selection) {
		Button button = createButton(parent, text, listener);
		button.setBackground(background);
		button.setSelection(selection);
		return button;
	}
	
	
	/**
	 * This method creates a CLabel, sets the given text and shows the icon on the left side.
	 */
	public static CLabel createCLabel(Composite parent, String text, Image icon) {
		CLabel cLabel = new CLabel(parent, SWT.CENTER);
		cLabel.setText(text);
		cLabel.setImage(icon);
		return cLabel;
	}
	
	public static Group creatGroup(Composite parent, String label, Layout layout, Object layoutData) {
		Group group = new Group(parent, SWT.NONE);
		group.setLayout(layout);
		group.setText(label);
		group.setLayoutData(layoutData);
		return group;
	}
}

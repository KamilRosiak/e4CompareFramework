package de.tu_bs.cs.isf.e4cf.core.gui.swt.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

public abstract class TreeBuilder {
	
	/**
	 * This method creates a TreeItem with the given parameters.
	 * @return Decorated TreeItem
	 */
	public static TreeItem createTreeItem(TreeItem parent,String name, String type, Image icon, boolean isChecked, Color color) {
		TreeItem item = new TreeItem(parent, SWT.ICON | SWT.CHECK);
		item.setText(new String[] {name, type});
		item.setImage(icon);
		item.setChecked(isChecked);
		item.setBackground(color);
		return item;
	}
	
	/**
	 * This method creates a TreeItem with the given parameters.
	 * @return Decorated TreeItem
	 */
	public static TreeItem createTreeItem(TreeItem parent,String name, String type,String description, Image icon, boolean isChecked, Color color) {
		TreeItem item = createTreeItem(parent, name, type, icon, isChecked, color);
		item.setText(new String[] {name,type,description});
		return item;
	}
	
	/**
	 * This method creates a TreeItem with the given parameters.
	 * @return Decorated TreeItem
	 */
	public static TreeItem createTreeItem(TreeItem parent,String name, String type,String description, Image icon, boolean isChecked, Color color, Object data) {
		TreeItem item = createTreeItem(parent, name, type, description, icon, isChecked, color);
		item.setData(data);
		return item;
	}
	
	/**
	 * This method creates a TreeItem with the given parameters. 
	 * @return Decorated TreeItem
	 */
	public static TreeItem createTreeItem(TreeItem parent,String name, String type, Image icon, boolean isChecked, Color color, Object data) {
		TreeItem item = createTreeItem(parent, name, type, icon, isChecked, color);
		item.setData(data);
		return item;
	}
	
	public static TreeItem createTreeItem(TreeItem parent, String name, Image img, Object data) {
		TreeItem treeItem = new TreeItem(parent,SWT.ICON);
		treeItem.setData(data);
		treeItem.setText(name);
		treeItem.setImage(img);
		return treeItem;
	}
	
	/**
	 * This method creates a TreeColumn with the given parameters. 
	 * @return Decorated TreeItem
	 */
	public static TreeColumn createTreeColumn(Tree parent ,String name, int alignment, int width) {
		TreeColumn nameColumn = new TreeColumn(parent, alignment);
		nameColumn.setText(name);
		nameColumn.setWidth(width);
		return nameColumn;
	}
}

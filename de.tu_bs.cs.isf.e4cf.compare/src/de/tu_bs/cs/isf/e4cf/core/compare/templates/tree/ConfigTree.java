package de.tu_bs.cs.isf.e4cf.core.compare.templates.tree;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.core.compare.string_table.E4CompareStringTable;
import de.tu_bs.cs.isf.e4cf.core.gui.swt.util.TreeBuilder;

/**
 * This class represents a styled tree for the metric tree with three columns. 
 * @author {Kamil Rosiak}
 *
 */
public class ConfigTree {
	private static final int COLUMN_WIDTH = 200;
	private Tree tree;
	private List<TreeColumn> columns;
	
	public ConfigTree(Composite parent, int style) {
		columns = new ArrayList<TreeColumn>();
		createConfigurationTree(parent,style);
	}
	
	public void createConfigurationTree(Composite parent, int style) {
		tree = new Tree(parent, style);
		tree.setLayoutData(createGridDate());
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);
		columns.add(TreeBuilder.createTreeColumn(tree, E4CompareStringTable.MM_COLUMN_NAME, SWT.LEFT, COLUMN_WIDTH));
		columns.add(TreeBuilder.createTreeColumn(tree, E4CompareStringTable.MM_COLUMN_TYPE, SWT.CENTER, COLUMN_WIDTH));
		columns.add(TreeBuilder.createTreeColumn(tree, E4CompareStringTable.MM_COLUMN_DESC, SWT.RIGHT, COLUMN_WIDTH));
	}
	
	private GridData createGridDate() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		return gridData;
	}
	
	/**
	 * This method packs the tree recursively to fit the size of new items.
	 */
	public void packColumn() {
		Point size = tree.getSize();
		int newWidth = 0;
		tree.setRedraw(false);
		for(TreeColumn tc: columns) {
			tc.pack();
			newWidth = newWidth + tc.getWidth();
		}
		TreeColumn lastCol = columns.get(columns.size()-1);
		int newSize = size.x - (newWidth-lastCol.getWidth());
		lastCol.setWidth(newSize);
		tree.setRedraw(true);
		tree.redraw();
	}
	
	public void expandTree() {
		tree.setRedraw(false);
		for(TreeItem t :tree.getItems()) {
			t.setExpanded(true);
			expandRek(t);
		}
		tree.setRedraw(true);
		packColumn();
		tree.redraw();
	}
	
	public void expandRek(TreeItem t) {
		for(TreeItem child: t.getItems()) {
			child.setExpanded(true);
			expandRek(child);
		}
	}
	
	public void colapseTree() {
		tree.setRedraw(false);
		for(TreeItem t: tree.getItems()) {
			t.setExpanded(false);
			colapseRek(t);
		}
		tree.setRedraw(true);
		packColumn();
		tree.redraw();		
	}
	
	public void colapseRek(TreeItem t) {
		for(TreeItem child: t.getItems()) {
			child.setExpanded(false);
			colapseRek(child);
		}
	}
	
	public Tree getTree() {
		return tree;
	}
	
	
}

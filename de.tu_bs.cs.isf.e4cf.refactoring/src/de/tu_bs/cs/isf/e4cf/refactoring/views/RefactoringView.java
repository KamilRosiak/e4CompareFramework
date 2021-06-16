package de.tu_bs.cs.isf.e4cf.refactoring.views;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.refactoring.model.RefactoringLayer;
import de.tu_bs.cs.isf.e4cf.refactoring.util.LayerTreeBuilder;


public class RefactoringView extends View {

	private Label layerLabel;

	private Tree layerTree;

	public Tree getLayerTree() {
		return layerTree;
	}

	public void setLayerTree(Tree layerTree) {
		this.layerTree = layerTree;
	}

	private LayerTreeBuilder layerTreeBuilder;

	public RefactoringView() {

		super(2, "Refactoring layers");
		layerTreeBuilder = new LayerTreeBuilder();

	}

	public void showView(List<RefactoringLayer> refactoringLayers) {

		createLayerTree(refactoringLayers);
		showView();
	}

	public void createLayerTree(List<RefactoringLayer> refactoringLayers) {

		for (TreeItem item : layerTree.getItems()) {
			item.dispose();
		}

		layerTreeBuilder.buildLayerTree(refactoringLayers, layerTree);

	}

	@Override
	public void setWidgets() {
		layerLabel = new Label(shell, 0);
		layerLabel.setText("Layers");

		layerTree = new Tree(shell, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);

		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		layerTree.setLayoutData(gridData);

	}

}

package de.tu_bs.cs.isf.e4cf.refactoring.util;

import java.util.List;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.refactoring.model.RefactoringLayer;

public class LayerTreeBuilder {

	public void buildLayerTree(List<RefactoringLayer> refactoringLayers, Tree layerTree) {

		for (RefactoringLayer refactoringLayer : refactoringLayers) {
			TreeItem item = new TreeItem(layerTree, 0);
			item.setText(refactoringLayer.getLayer());
			item.setData(refactoringLayer);
			item.setChecked(refactoringLayer.refactor());
		}

	}
}

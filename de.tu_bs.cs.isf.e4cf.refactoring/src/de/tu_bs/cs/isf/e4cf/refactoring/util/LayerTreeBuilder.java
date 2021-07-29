package de.tu_bs.cs.isf.e4cf.refactoring.util;

import java.util.List;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentLayer;

public class LayerTreeBuilder {

	public void buildLayerTree(List<ComponentLayer> componentlayers, Tree layerTree) {

		for (ComponentLayer componentLayer : componentlayers) {
			TreeItem item = new TreeItem(layerTree, 0);
			item.setText(componentLayer.getLayer());
			item.setData(componentLayer);
			item.setChecked(componentLayer.refactor());
		}

	}
}

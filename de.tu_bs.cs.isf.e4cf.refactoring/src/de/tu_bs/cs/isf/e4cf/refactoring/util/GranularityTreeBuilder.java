package de.tu_bs.cs.isf.e4cf.refactoring.util;

import java.util.List;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.refactoring.model.Granularity;

public class GranularityTreeBuilder {

	public void buildGranularityTree(List<Granularity> granularities, Tree layerTree) {

		for (Granularity granularity : granularities) {
			TreeItem item = new TreeItem(layerTree, 0);
			item.setText(granularity.getLayer());
			item.setData(granularity);
			item.setChecked(granularity.refactor());
		}

	}
}

package de.tu_bs.cs.isf.e4cf.refactoring.views;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.refactoring.model.Granularity;
import de.tu_bs.cs.isf.e4cf.refactoring.util.GranularityTreeBuilder;

public class GranularityView extends View {

	private Label granularityLabel;

	private Tree granularityTree;

	public Tree getLayerTree() {
		return granularityTree;
	}

	public void setLayerTree(Tree granularityTree) {
		this.granularityTree = granularityTree;
	}

	private GranularityTreeBuilder granularityTreeBuilder;

	public GranularityView() {

		super(2, "Component granularities");
		granularityTreeBuilder = new GranularityTreeBuilder();

	}

	public void showView(List<Granularity> granularities) {

		createLayerTree(granularities);
		showView();
	}

	public void createLayerTree(List<Granularity> granularities) {

		for (TreeItem item : granularityTree.getItems()) {
			item.dispose();
		}

		granularityTreeBuilder.buildGranularityTree(granularities, granularityTree);

	}

	@Override
	public void setWidgets() {
		granularityLabel = new Label(shell, 0);
		granularityLabel.setText("Select granularities:");

		granularityTree = new Tree(shell, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);

		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		granularityTree.setLayoutData(gridData);

	}

}

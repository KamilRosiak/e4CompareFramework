package de.tu_bs.cs.isf.e4cf.refactoring.handler;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.refactoring.evaluation.GranularityCallback;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ClusterEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ComponentExtractor;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.GranularityManager;
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Granularity;

@Singleton
@Creatable
public class ExtractionPipeline {

	private GranularityManager granularityManager;

	private ComponentExtractor componentExtractor;

	private ClusterEngine clusterEngine;

	@Inject
	public ExtractionPipeline(GranularityManager granularityManager, ComponentExtractor componentExtractor,
			ClusterEngine clusterEngine) {
		this.granularityManager = granularityManager;
		this.componentExtractor = componentExtractor;
		this.clusterEngine = clusterEngine;
	}

	public CloneModel pipe(Tree tree) {
		return pipe(tree, null, 0.15f);
	}

	public CloneModel pipe(Tree tree, GranularityCallback granularityCallback, float threshold) {
		clusterEngine.setThreshold(threshold);

		Map<Granularity, List<Node>> layerToNodes = granularityManager.extractNodesOfCertainGranularities(tree,
				granularityCallback);
		if (layerToNodes != null) {

			CloneModel cloneModel = componentExtractor.extractComponents(clusterEngine.detectClusters(layerToNodes));
			cloneModel.setTree(tree);

			return cloneModel;
		}
		return null;
	}

}

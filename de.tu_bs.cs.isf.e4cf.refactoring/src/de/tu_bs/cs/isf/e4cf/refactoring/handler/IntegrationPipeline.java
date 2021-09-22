package de.tu_bs.cs.isf.e4cf.refactoring.handler;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.refactoring.controllers.GranularityViewController;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ClusterEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ComponentExtractor;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.GranularityManager;
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Granularity;
import de.tu_bs.cs.isf.e4cf.refactoring.util.ProcessUtil;
import de.tu_bs.cs.isf.e4cf.refactoring.util.SynchronizationUtil;

@Singleton
@Creatable
public class IntegrationPipeline {

	private GranularityManager granularityManager;

	private ComponentExtractor componentExtractor;

	private ClusterEngine clusterEngine;

	private GranularityViewController granularityViewController;

	@Inject
	public IntegrationPipeline(GranularityManager granularityManager, ComponentExtractor componentExtractor,
			ClusterEngine clusterEngine, GranularityViewController granularityViewController) {
		this.granularityManager = granularityManager;
		this.componentExtractor = componentExtractor;
		this.clusterEngine = clusterEngine;
		this.granularityViewController = granularityViewController;
	}

	public CloneModel pipe(Tree tree, ProcessUtil process) {

		Set<String> nodeTypes = new HashSet<String>();
		nodeTypes.addAll(tree.getRoot().getAllNodeTypes());
		Set<Granularity> granularities = SynchronizationUtil.getGranularities(nodeTypes);

		granularityViewController.showView(granularities);
		if (granularityViewController.isResult()) {

			return pipe(tree, granularities, process);

		}

		return null;

	}

	public CloneModel pipe(Tree tree, Set<Granularity> granularities, ProcessUtil process) {
		Map<Granularity, List<Node>> layerToNodes = granularityManager.extractNodesOfCertainGranularities(tree,
				granularities);
		CloneModel cloneModel = componentExtractor
				.extractComponents(clusterEngine.detectClusters(layerToNodes, process), tree);
		cloneModel.setTree(tree);

		return cloneModel;

	}

}

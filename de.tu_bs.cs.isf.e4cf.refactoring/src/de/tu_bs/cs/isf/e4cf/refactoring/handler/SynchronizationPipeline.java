package de.tu_bs.cs.isf.e4cf.refactoring.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.compare.metric.interfaces.Metric;
import de.tu_bs.cs.isf.e4cf.refactoring.evaluation.ActionCallback;
import de.tu_bs.cs.isf.e4cf.refactoring.evaluation.GranularityCallback;
import de.tu_bs.cs.isf.e4cf.refactoring.evaluation.StatsLogger;
import de.tu_bs.cs.isf.e4cf.refactoring.evaluation.SynchronizationCallback;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ActionManager;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ClusterEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ComponentExtractor;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.EditScriptGenerator;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.GranularityManager;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.RevisionComparator;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.SynchronizationManager;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Delete;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Granularity;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Insert;
import de.tu_bs.cs.isf.e4cf.refactoring.model.RevisionComparison;

@Creatable
public class SynchronizationPipeline {

	private GranularityManager granularityManager;

	private ClusterEngine clusterEngine;

	private ComponentExtractor componentExtractor;

	private ActionManager actionManager;

	private SynchronizationManager synchronizationManager;

	private RevisionComparator revisionComparator;

	private EditScriptGenerator editScriptGenerator;

	private CompareEngineHierarchical compareEngine;

	@Inject
	public SynchronizationPipeline(GranularityManager granularityManager, ClusterEngine clusterEngine,
			ComponentExtractor componentExtractor, ActionManager actionManager,
			SynchronizationManager synchronizationManager, RevisionComparator revisionComparator,
			EditScriptGenerator editScriptGenerator) {
		super();
		this.granularityManager = granularityManager;
		this.clusterEngine = clusterEngine;
		this.componentExtractor = componentExtractor;
		this.actionManager = actionManager;
		this.synchronizationManager = synchronizationManager;
		this.revisionComparator = revisionComparator;
		this.editScriptGenerator = editScriptGenerator;
		this.compareEngine = new CompareEngineHierarchical(new SortingMatcher(), new MetricImpl("test"));
	}

	public CloneModel pipe(Tree tree1, Tree tree2) {
		return pipe(tree1, tree2, null, null, null, 0.15f, null);
	}

	public CloneModel pipe(CloneModel cloneModel, Tree tree2) {
		return pipe(cloneModel, tree2, null, null, null, 0.15f, null);
	}
	

	public CloneModel pipe(CloneModel cloneModel, Tree tree2, GranularityCallback granularityCallback,
			ActionCallback actionCallback, SynchronizationCallback synchronizationCallback, float threshold, StatsLogger statsLogger) {

		RevisionComparison revisionComparison = revisionComparator.compare(cloneModel, tree2);
		List<ActionScope> actionScopes = editScriptGenerator.generateEditScript(revisionComparison);

		boolean actionManagerResult = false;
		if (actionCallback == null) {
			actionManagerResult = actionManager.showActionView(cloneModel, actionScopes);
		} else {
			actionCallback.handle(actionScopes);
			actionManagerResult = true;
		}

		if (actionManagerResult) {

			actionManager.update(actionScopes, cloneModel);

			Map<ActionScope, List<ActionScope>> synchronizationScopes = synchronizationManager
					.translateActionsToSynchronizations(actionScopes, cloneModel);

			boolean synchronizationManagerResult = false;
			if (synchronizationCallback == null) {
				synchronizationManagerResult = synchronizationManager.showSynchronizationView(cloneModel,
						synchronizationScopes);
			} else {
				synchronizationCallback.handle(synchronizationScopes);
				synchronizationManagerResult = true;
			}

			if (synchronizationManagerResult) {
				synchronizationManager.synchronize(synchronizationScopes, cloneModel);

				cloneModel.replaceMatchesInTree(revisionComparison);

				cloneModel.setTree(compareEngine.compare(cloneModel.getTree(), tree2));

				clusterEngine.analyzeCloneModel(cloneModel, statsLogger);				
				
				return cloneModel;
			}

		}
		return null;

	}

	public CloneModel pipe(Tree tree1, Tree tree2, GranularityCallback granularityCallback,
			ActionCallback actionCallback, SynchronizationCallback synchronizationCallback, float threshold, StatsLogger statsLogger) {

		clusterEngine.setThreshold(threshold);

		Map<Tree, Map<Granularity, List<Node>>> treeToLayers = granularityManager
				.extractNodesOfCertainGranularities(tree1, tree2, granularityCallback);

		if (treeToLayers != null) {

			CloneModel cloneModel = componentExtractor
					.extractComponents(clusterEngine.detectClusters(treeToLayers.get(tree1)));
			cloneModel.setTree(tree1);

			return pipe(cloneModel, tree2, granularityCallback, actionCallback, synchronizationCallback, threshold, statsLogger);

		}
		return null;
	}

}

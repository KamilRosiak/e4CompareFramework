package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Granularity;
import de.tu_bs.cs.isf.e4cf.refactoring.model.MultiSetNode;
import de.tu_bs.cs.isf.e4cf.refactoring.model.MultiSetReferenceTree;
import de.tu_bs.cs.isf.e4cf.refactoring.model.MultiSetTree;

@Singleton
@Creatable
public class ComponentExtractor {

	public CloneModel extractComponents(Map<Granularity, List<Set<Node>>> layerToClusters, Tree tree) {

		List<MultiSetTree> multiSetTrees = new ArrayList<MultiSetTree>();

		Map<String, List<Set<MultiSetNode>>> allClusters = new HashMap<String, List<Set<MultiSetNode>>>();

		for (Entry<Granularity, List<Set<Node>>> entry : layerToClusters.entrySet()) {
			List<MultiSetTree> multiSetTreeList = extractComponents(entry.getValue(), entry.getKey());
			List<Set<MultiSetNode>> list = new ArrayList<Set<MultiSetNode>>();

			for (MultiSetTree multiSetTree : multiSetTreeList) {
				Set<MultiSetNode> roots = new HashSet<MultiSetNode>();
				roots.addAll(multiSetTree.getRoots());
				list.add(roots);
			}

			multiSetTrees.addAll(multiSetTreeList);
			allClusters.put(entry.getKey().getLayer(), list);
		}

		MultiSetReferenceTree reference = MultiSetReferenceTree.buildReferenceTree(tree, allClusters);

		CloneModel cloneModel = new CloneModel(multiSetTrees, reference, tree.getTreeName(), layerToClusters.keySet());
		return cloneModel;

	}

	private List<MultiSetTree> extractComponents(List<Set<Node>> clusters, Granularity granularity) {

		List<MultiSetTree> multiSetTrees = new ArrayList<MultiSetTree>();

		for (Set<Node> cluster : clusters) {

			multiSetTrees.add(MultiSetTree.build(cluster, granularity));

		}
		return multiSetTrees;
	}

}

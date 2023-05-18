package de.tu_bs.cs.isf.e4cf.featuremodel.synthesis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.core.util.tree.Node;
import de.tu_bs.cs.isf.e4cf.core.util.tree.Tree;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.annotation_view.Cluster;

public class FeatureOrganizer {
//	private MPLPlatform mpl;
//	private List<Cluster> clusters;
	
	public static Tree<Cluster> createHierarchy(MPLPlatform mpl, List<Cluster> clusters) {		
		Map<Configuration, List<Cluster>> configToClusters = mapConfigsToClusters(mpl.configurations, clusters);
		return buildHierarchy(configToClusters, clusters);
	}
	
	private static Map<Configuration, List<Cluster>> mapConfigsToClusters(List<Configuration> configs, List<Cluster> clusters) {
		Map<Configuration, List<Cluster>> configToCluster = new HashMap<>();
		for (Configuration config : configs) {
			List<Cluster> presentClusters = new ArrayList<>();
			for (Cluster cluster : clusters) {
				if (config.getUUIDs().containsAll(cluster.getSyntaxGroup().getUuids())) {
					presentClusters.add(cluster);
				}
			}
			configToCluster.put(config, presentClusters);
		}
		
		return configToCluster;
	}
	
	private static Tree<Cluster> buildHierarchy(Map<Configuration, List<Cluster>> validCombos, List<Cluster> allClusters) {
		Tree<Cluster> hierarchy = new Tree<>(null);
		List<List<Cluster>> clusterSelectionBase = removeTreeFromCombos(hierarchy, validCombos);
		while (!clusterSelectionBase.isEmpty()) {
			Cluster mostFrequentCluster = mostFrequentCluster(clusterSelectionBase);
			insertCluster(hierarchy, mostFrequentCluster, validCombos.values());
			clusterSelectionBase = removeTreeFromCombos(hierarchy, validCombos);
		}
		return hierarchy;
	}
	
	private static Cluster mostFrequentCluster(Collection<List<Cluster>> combos) {
		return rankClustersByFrequency(combos).first().getKey();
	}
	
	private static SortedSet<Map.Entry<Cluster, Integer>> rankClustersByFrequency(Collection<List<Cluster>> combos) {
		Map<Cluster, Integer> clusterCount = new HashMap<>();
		for (List<Cluster> combo : combos) {
			for (Cluster cluster : combo) {
				clusterCount.put(cluster, clusterCount.getOrDefault(cluster, 0) + 1);
			}
		}
		SortedSet<Map.Entry<Cluster, Integer>> sortedClusterCount = sortByValueDecreasing(clusterCount);
		return sortedClusterCount;
	}

	private static SortedSet<Map.Entry<Cluster, Integer>> sortByValueDecreasing(Map<Cluster, Integer> clusterCount) {
		SortedSet<Map.Entry<Cluster, Integer>> sortedClusterCount = new TreeSet<>((c1, c2) ->  {
			int comparision = -Integer.compare(c1.getValue(), c2.getValue());
			if (comparision == 0) { // clusters with same frequency 
				// compare by name
				comparision = c1.getKey().getName().compareTo(c2.getKey().getName());
			}
			return comparision;
		});
		sortedClusterCount.addAll(clusterCount.entrySet());
		return sortedClusterCount;
	}
	
	private static void insertCluster(Tree<Cluster> tree, Cluster cluster, Collection<List<Cluster>> combos) {
		if (tree.getRoot() == null) {
			tree.setRoot(new Node<>(cluster));
		} else {
			Cluster parent = getParent(cluster, combos, tree);
			Node<Cluster> parentNode = tree.getNode(parent).orElseThrow(IllegalStateException::new);
			parentNode.addChild(new Node<>(cluster));
			parentNode.value().addChild(cluster);
		}
	}
	
	private static Cluster getParent(Cluster leaf, Collection<List<Cluster>> combos, Tree<Cluster> tree) {
		Set<Cluster> alreadyInTree = tree.getAllNodes().stream().map(Node::value).collect(Collectors.toSet());
		List<List<Cluster>> combosWithLeaf = cloneCombos(combos).stream()
				.filter(combo -> combo.contains(leaf))
				.collect(Collectors.toList());
		combosWithLeaf.forEach(combo -> combo.retainAll(alreadyInTree));
		SortedSet<Map.Entry<Cluster, Integer>> frequencyRanking = rankClustersByFrequency(combosWithLeaf);
		int minCount = combosWithLeaf.size();
		List<Cluster> parentCandidates = frequencyRanking.stream()
				.filter(clusterCount -> clusterCount.getValue() == minCount)
				.map(clusterCount -> clusterCount.getKey())
				.collect(Collectors.toList());
		Map<Cluster, Integer> candidateDepth = new HashMap<>();
		for (Cluster cluster : parentCandidates) {
			candidateDepth.put(cluster, tree.getDepth(cluster));
		}
		return sortByValueDecreasing(candidateDepth).first().getKey();
	}
	
	private static List<List<Cluster>> removeTreeFromCombos(Tree<Cluster> tree, Map<Configuration, List<Cluster>> combos) {
		List<Cluster> inTree = tree.getAllNodes().stream().map(Node::value).collect(Collectors.toList());
		Map<Configuration, List<Cluster>> comboCopy = cloneCombos(combos);
		for (Cluster treeCluster : inTree) {
			List<Configuration> toRemove = new ArrayList<>();
			for (Map.Entry<Configuration, List<Cluster>> combo : comboCopy.entrySet()) {
				combo.getValue().remove(treeCluster);
				if (combo.getValue().isEmpty()) {
					toRemove.add(combo.getKey());
				}
			}
			for (Configuration config : toRemove) {
				comboCopy.remove(config);
			}
		}
		return new ArrayList<>(comboCopy.values());
	}
	
	private static Map<Configuration, List<Cluster>> cloneCombos(Map<Configuration, List<Cluster>> combos) {
		Map<Configuration, List<Cluster>> clone = new HashMap<>();
		for (Configuration config : combos.keySet()) {
			clone.put(config, new ArrayList<>(combos.get(config)));
		}
		return clone;
	}
	
	private static Collection<List<Cluster>> cloneCombos(Collection<List<Cluster>> combos) {
		List<List<Cluster>> clone = new ArrayList<>();
		for (List<Cluster> combo : combos) {
			clone.add(new ArrayList<>(combo));
		}
		return clone;
	}

}

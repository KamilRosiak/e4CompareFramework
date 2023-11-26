package de.tu_bs.cs.isf.e4cf.featuremodel.synthesis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.Feature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.FeatureDiagram;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.GroupVariability;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.IFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.Variability;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.annotation_view.Cluster;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.util.ColorPickerUtil;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.util.FeatureUtil;
import javafx.scene.paint.Color;

public class FeatureLocator {
	private List<SyntaxGroup> groups;

	public List<SyntaxGroup> locateFeatures(ServiceContainer container, MPLPlatform mpl) {
		this.groups = calculateAtomicSets(mpl);
		return this.groups;
	}

	private List<SyntaxGroup> calculateAtomicSets(MPLPlatform platform) {
		Set<UUID> allUUIDs = platform.model.getAllUUIDS();

		// map uuid to variants that contain it
		Map<UUID, String> idToVariants = new HashMap<>();
		// use enough whitespace for number of variants
		String padding = "%" + ((int) Math.floor(Math.log10(platform.configurations.size())) + 2) + "s";
		for (UUID uuid : allUUIDs) {
			StringBuilder variantString = new StringBuilder();
			for (int i = 0; i < platform.configurations.size(); i++) {
				Configuration config = platform.configurations.get(i);
				if (config.getUUIDs().contains(uuid)) {
					variantString.append(String.format(padding, i + 1));
				}
			}
			// map each uuid to a string of variant names that contain the uuid
			idToVariants.put(uuid, variantString.toString());
		}
		// map combinations of variants to set of ids
		Map<String, Set<UUID>> variantComboToUUIDs = new HashMap<>();
		for (Entry<UUID, String> idToVariantEntry : idToVariants.entrySet()) {
			if (!variantComboToUUIDs.containsKey(idToVariantEntry.getValue())) {
				variantComboToUUIDs.put(idToVariantEntry.getValue(), new HashSet<>());
			}
			variantComboToUUIDs.get(idToVariantEntry.getValue()).add(idToVariantEntry.getKey());
		}

		// TODO:CHECK UUIDS
		variantComboToUUIDs.remove("");
		if (variantComboToUUIDs.containsKey("")) {
			throw new IllegalStateException("UUID with no corresponding configuration present.");
		}

		// sort variantCombos by length (decreasing)
		Set<String> variantCombos = variantComboToUUIDs.keySet();
		TreeSet<String> sortedVariantCombos = new TreeSet<>(new Comparator<String>() {
			@Override
			public int compare(String arg0, String arg1) {
				int diff = arg1.length() - arg0.length();
				if (diff == 0) {
					diff = arg0.compareTo(arg1);
				}
				return diff;
			}
		});
		sortedVariantCombos.addAll(variantCombos);

		// cluster uuids into atomic sets
		List<SyntaxGroup> atomicSets = new ArrayList<>();
		int k = 0;
		List<Color> palette = ColorPickerUtil.generatePalette(sortedVariantCombos.size());
		for (String combo : sortedVariantCombos) { // iterate by decreasing combo length == number of variants in
													// combination
			// get uuids of the combination of variants
			Set<UUID> uuidsOfVariantCombo = variantComboToUUIDs.remove(combo);
			Set<Configuration> variants = new HashSet<>();
			String[] variantsSplit = combo.split("\\s+"); // split on whitespace, used to separate variant names in
															// combo
			for (String variantOrdinal : variantsSplit) { // create list of variants in the combo
				if (variantOrdinal.isEmpty())
					continue;
				// configs are identified by number starting with 1
				int ordinal = Integer.parseInt(variantOrdinal.trim());
				variants.add(platform.configurations.get(ordinal - 1));
			}
			// create the cluster for the combo
			atomicSets.add(new SyntaxGroup(variants, uuidsOfVariantCombo, palette.get(k)));
			k += 1;

			// remove uuids of current cluster from remaining combos
			List<String> toRemove = new ArrayList<>();
			for (Entry<String, Set<UUID>> variantComboToUUIDsEntry : variantComboToUUIDs.entrySet()) {
				variantComboToUUIDsEntry.getValue().removeAll(uuidsOfVariantCombo);
				if (variantComboToUUIDsEntry.getValue().isEmpty()) { // no uuids remaining for the variant
					toRemove.add(variantComboToUUIDsEntry.getKey());
				}
			}

			// remove empty configs
			for (String emptyCombo : toRemove) {
				variantComboToUUIDs.remove(emptyCombo);
			}
		}
		return atomicSets;
	}

	public FeatureDiagram updateMPL(MPLPlatform currentMpl, MPLPlatform newMpl) {
		Configuration newConfig = null;
		try {
			for (Configuration config : newMpl.configurations) {
				if (!currentMpl.configurations.contains(config)) {
					newConfig = config;
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		this.groups = calculateAtomicSets(newMpl);
		List<IFeature> newFeatures = groups.stream().map(Cluster::new).map(FeatureUtil::toFeature)
				.collect(Collectors.toList());
		return updateFeatureModel(currentMpl.getFeatureModel().get(), newFeatures, newConfig);
	}

	private FeatureDiagram updateFeatureModel(FeatureDiagram oldDiagram, List<IFeature> newFeatures,
			Configuration newConfig) {
		String newVariantName = newConfig.getName();
		Map<IFeature, IFeature> featureMap = new HashMap<>();

		Stack<IFeature> featureMatchStack = new Stack<>();
		featureMatchStack.push(oldDiagram.getRoot());
		List<IFeature> remainingNewFeatures = new ArrayList<>(newFeatures);

		while (!featureMatchStack.isEmpty()) {
			IFeature matchTarget = featureMatchStack.pop();
			List<IFeature> newSubsetFeatures = getDependencySubsets(matchTarget, remainingNewFeatures);
			remainingNewFeatures.removeAll(newSubsetFeatures);
			List<IFeature> newOverlapFeatures = getDependencyOverlaps(matchTarget, remainingNewFeatures);
			remainingNewFeatures.removeAll(newOverlapFeatures);
			// System.out.println(matchTarget.getArtifactUUIDs().size());
			// System.out.println(newSubsetFeatures.size());
			// System.out.println(newOverlapFeatures.size());
			// newSubsetFeatures.forEach(f ->
			// System.out.println(f.getArtifactUUIDs().size()));

			if (!newSubsetFeatures.isEmpty()) {
				List<IFeature> mostGeneralSubsetFeatures = getMostGeneralSubsetFeatures(newSubsetFeatures);
				newSubsetFeatures.removeAll(mostGeneralSubsetFeatures);
				if (mostGeneralSubsetFeatures.size() == 1) {
					// use all attributes from oldFeature in newFeature
					// System.out.println("one");
					IFeature mostGeneralSubFeature = mostGeneralSubsetFeatures.get(0);
					String name = matchTarget.getName();
					if (mostGeneralSubFeature.getConfigurations().contains(newConfig)) {
						name += "-" + newVariantName;
					}
					mostGeneralSubFeature.setName(name);
					mostGeneralSubFeature.setVariability(matchTarget.getVariability());
					mostGeneralSubFeature.setGroupVariability(matchTarget.getGroupVariability());
					mostGeneralSubFeature.setColor(matchTarget.getColor().get());
					mostGeneralSubFeature.setIsRoot(matchTarget.isRoot());
					featureMap.put(matchTarget, mostGeneralSubFeature);
				} else if (mostGeneralSubsetFeatures.size() > 1) {
					System.out.println("two");
				} else {
					System.out.println("none?!");
				}
			}

			// insert other eventual subsetfeatures as optional children,
			// considering the group variability, may insert abstract features
			for (IFeature subsetFeature : newSubsetFeatures) {
				subsetFeature.setName(matchTarget.getName());
				IFeature newParent = featureMap.get(matchTarget);
				if (matchTarget.getChildren().size() > 0) {
					if (newParent.getGroupVariability().equals(GroupVariability.OR)) {
						newParent.addChild(subsetFeature);
					} else if (newParent.getGroupVariability().equals(GroupVariability.ALTERNATIVE)) {
						newParent.setGroupVariability(GroupVariability.OR);
						IFeature container = new Feature("Abstract");
						newParent.getChildren().forEach(container::addChild);
						newParent.getChildren().clear();
						subsetFeature.setVariability(Variability.DEFAULT);
						newParent.addChild(container);
						newParent.addChild(subsetFeature);

					} else {
						// groupVar == Default
						if (newParent.getChildren().size() > 0) {
							newParent.setGroupVariability(GroupVariability.OR);
							newParent.getChildren().forEach(f -> f.setVariability(Variability.DEFAULT));
							newParent.addChild(subsetFeature);
						} else {
							subsetFeature.setVariability(Variability.OPTIONAL);
							newParent.addChild(subsetFeature);
						}
					}
				} else {
					newParent.setGroupVariability(GroupVariability.DEFAULT);
					subsetFeature.setVariability(Variability.OPTIONAL);
					newParent.addChild(subsetFeature);
				}
			}

			featureMatchStack.addAll(matchTarget.getChildren());
		}

		// create hierarchy by inserting matched children of oldFeatures in new diagram
		// using feature map
		for (IFeature oldFeature : featureMap.keySet()) {
			IFeature newFeature = featureMap.get(oldFeature);
			for (IFeature oldChild : oldFeature.getChildren()) {
				IFeature newChild = featureMap.get(oldChild);
				if (newChild != null) {
					if (!newFeature.getGroupVariability().equals(GroupVariability.DEFAULT)) {
						newChild.setVariability(Variability.DEFAULT);
					} else {
						newChild.setVariability(Variability.OPTIONAL);
					}
					newFeature.addChild(newChild);
				} else {
					System.out.println("lost a child");
				}
			}
		}

		// insert remaining new features regularly using parent finder algo
		FeatureDiagram newDiagram = new FeatureDiagram(oldDiagram.getName(), featureMap.get(oldDiagram.getRoot()));
		for (IFeature newFeature : remainingNewFeatures) {
			IFeature parent = getParent(newFeature, newDiagram);
			if (parent.getChildren().size() + 1 > 1) {
				if (parent.getGroupVariability().equals(GroupVariability.DEFAULT)) {
					parent.setGroupVariability(GroupVariability.OR);
				}
			} else {
				newFeature.setVariability(Variability.OPTIONAL);
			}
			parent.addChild(newFeature);

			if (featuresNonOverlapping(parent.getChildren())) {
				parent.setGroupVariability(GroupVariability.ALTERNATIVE);
				parent.getChildren().forEach(f -> f.setVariability(Variability.DEFAULT));
			}
		}

		return newDiagram;
	}

	private boolean featuresNonOverlapping(List<IFeature> features) {
		Set<String> configNames = new HashSet<>();
		for (IFeature feature : features) {
			for (Configuration config : feature.getConfigurations()) {
				if (!configNames.add(config.getName())) {
					return false;
				}
			}
		}
		return true;
	}

	private List<IFeature> getMostGeneralSubsetFeatures(List<IFeature> features) {
		int mostConfigs = 0;
		for (IFeature feature : features) {
			if (feature.getConfigurations().size() > mostConfigs) {
				mostConfigs = feature.getConfigurations().size();
			}
		}
		final int configCount = mostConfigs;
		return features.stream().filter(f -> f.getConfigurations().size() == configCount).collect(Collectors.toList());
	}

	private IFeature getParent(IFeature leaf, FeatureDiagram diagram) {
		Collection<IFeature> alreadyInTree = diagram.getAllFeatures();
		Map<IFeature, Integer> parentCandidateDepth = new HashMap<>();
		for (IFeature feature : alreadyInTree) {
			if (feature.getConfigurations().containsAll(leaf.getConfigurations())) {
				parentCandidateDepth.put(feature, diagram.getDepth(feature));
			}
		}
		TreeSet<Map.Entry<IFeature, Integer>> sortedParentCandidates = new TreeSet<>((m1, m2) -> {
			int diff = m2.getValue().compareTo(m1.getValue());
			if (diff == 0) {
				diff = m2.getKey().getConfigurations().size() - m1.getKey().getConfigurations().size();
			}
			return diff;
		});
		sortedParentCandidates.addAll(parentCandidateDepth.entrySet());
		IFeature parent = sortedParentCandidates.first().getKey();
		return parent;
	}

	private List<IFeature> getDependencySubsets(IFeature targetFeature, List<IFeature> features) {
		return checkFeatureCondition(features, f -> targetFeature.getArtifactUUIDs().containsAll(f.getArtifactUUIDs()));
	}

	private List<IFeature> getDependencyOverlaps(IFeature targetFeature, List<IFeature> features) {
		return checkFeatureCondition(features,
				f -> !Collections.disjoint(targetFeature.getArtifactUUIDs(), f.getArtifactUUIDs()));
	}

	private List<IFeature> checkFeatureCondition(List<IFeature> features, Function<IFeature, Boolean> condition) {
		List<IFeature> validFeatures = new ArrayList<>();
		for (IFeature feature : features) {
			if (condition.apply(feature)) {
				validFeatures.add(feature);
			}
		}
		return validFeatures;
	}

	private void updateFeatures(Collection<IFeature> oldFeatures, Collection<IFeature> newFeatures,
			Configuration newConfig) {
		String newVariantName = newConfig.getName();
		// map old feature to corresponding new feature(s)
		Map<IFeature, List<IFeature>> featureMap = new HashMap<>();

		for (IFeature newC : newFeatures) {
			for (IFeature oldC : oldFeatures) {
				if (oldC.getArtifactUUIDs().containsAll(newC.getArtifactUUIDs())) {
					// new cluster is subset of old cluster

					if (oldC.getConfigurations().size() < newC.getConfigurations().size()) {
						// clusters are the same
						newC.setName(oldC.getName());
						newC.setIsRoot(oldC.isRoot());
						newC.setVariability(oldC.getVariability());
						newC.setGroupVariability(oldC.getGroupVariability());
						featureMap.get(oldC).add(newC);
					} else {
						// remaining uuids not in new variant
						newC.setName(oldC.getName() + "\\" + newVariantName);
					}
					break;
				} else if (!Collections.disjoint(oldC.getArtifactUUIDs(), newC.getArtifactUUIDs())) {
					// other uuids in new cluster
					String newName = combineNames(oldC, newC);
					newC.setName(newName);
					System.out.println(newName);
					break;
				} else {
					// overlap between features
					System.out.println("other case");
				}
			}
		}

		// add children to new clusters
		for (IFeature oldC : oldFeatures) {
			for (IFeature newC : featureMap.get(oldC)) {
				for (IFeature oldChild : oldC.getChildren()) {
					featureMap.get(oldChild).forEach(newC::addChild);
				}
			}
		}
	}

	private String combineNames(IFeature oldC, IFeature newC) {
		String oldName = oldC.getName();
		String clusters = newC.getName();
		for (Configuration config : oldC.getConfigurations()) {
			clusters = clusters.replaceAll("\\b" + config.getName() + "\\b", "");
		}
		clusters = clusters.trim();
		String combined = oldName;
		if (!clusters.isEmpty()) {
			combined += "\\" + clusters;
		}
		return combined;
	}
}

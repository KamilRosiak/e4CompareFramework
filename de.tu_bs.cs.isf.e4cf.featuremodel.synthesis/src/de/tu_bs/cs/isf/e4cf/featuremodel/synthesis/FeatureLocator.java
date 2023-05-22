package de.tu_bs.cs.isf.e4cf.featuremodel.synthesis;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.util.ColorPicker;
import javafx.scene.paint.Color;

public class FeatureLocator {
	//private MPLPlatform currentMpl;
	private List<SyntaxGroup> groups;
	
	public List<SyntaxGroup> locateFeatures(ServiceContainer container, MPLPlatform mpl) {
		//this.currentMpl = mpl;
		
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
		List<Color> palette = ColorPicker.generatePalette(sortedVariantCombos.size());
		for (String combo : sortedVariantCombos) { // iterate by decreasing combo length == number of variants in combination
			// get uuids of the combination of variants
			Set<UUID> uuidsOfVariantCombo = variantComboToUUIDs.remove(combo);
			Set<Configuration> variants = new HashSet<>();
			String[] variantsSplit = combo.split("\\s+"); // split on whitespace, used to separate variant names in combo
			for (String variantOrdinal : variantsSplit) { // create list of variants in the combo
				if (variantOrdinal.isEmpty()) continue;
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
	
	public List<SyntaxGroup> updateMPL(MPLPlatform newMpl) {
		this.groups = recalculateAtomicGroups(this.groups, newMpl);
		//this.currentMpl = newMpl;
		return this.groups;
	}
	
	private List<SyntaxGroup> recalculateAtomicGroups(List<SyntaxGroup> groups, MPLPlatform mpl) {
		return calculateAtomicSets(mpl);
	}
}


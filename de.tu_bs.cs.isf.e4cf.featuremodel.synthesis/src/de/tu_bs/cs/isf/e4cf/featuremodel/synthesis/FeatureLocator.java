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
import java.util.stream.Collectors;

import javax.inject.Inject;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;
import de.tu_bs.cs.isf.e4cf.extractive_mple_platform_view.IFeatureLocaterExtension;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.util.ColorPicker;
import javafx.scene.paint.Color;

public class FeatureLocator implements IFeatureLocaterExtension {
	
	@Inject
	private ServiceContainer services;

	@Override
	public void locateFeatures(ServiceContainer container, MPLPlatform platform) {
		this.services = container;
		List<SyntaxGroup> syntaxGroups = calculateAtomicSets(platform);
		List<Set<UUID>> atomicSets = syntaxGroups.stream()
				.map(group -> group.getUuids())
				.collect(Collectors.toList());
		//String workspace = services.workspaceFileSystem.getWorkspaceDirectory().getAbsolutePath();
		//FileStreamUtil.writeTextToFile(workspace + "/atomicSets.txt", atomicSets.toString());
		services.eventBroker.post("atomic_sets_found", atomicSets);

		services.eventBroker.post(EventTable.PUBLISH_SYNTAX_GROUPS, syntaxGroups);
	}
	
	private List<SyntaxGroup> calculateAtomicSets(MPLPlatform platform) {
		Set<UUID> uuids = platform.model.getAllUUIDS();
		
		
		// map uuid to configs that contain it
		Map<UUID, String> configsForID = new HashMap<>();
		for (UUID id : uuids) {
			StringBuilder configString = new StringBuilder();
			for (int i = 0; i < platform.configurations.size(); i++) {
				Configuration config = platform.configurations.get(i);
				if (config.getUUIDs().contains(id)) {
					configString.append(String.format("%2s", i + 1));
				}
			}
			configsForID.put(id, configString.toString());
		}
		
		// map combinations of configs to set of ids
		Map<String, Set<UUID>> idsForConfigs = new HashMap<>();
		for (Entry<UUID, String> id : configsForID.entrySet()) {
			if (!idsForConfigs.containsKey(id.getValue())) {
				idsForConfigs.put(id.getValue(), new HashSet<>());
			}
			idsForConfigs.get(id.getValue()).add(id.getKey());
		}
		
		// sort configCombos by length
		Set<String> configCombos = idsForConfigs.keySet();
		TreeSet<String> sortedConfigCombos = new TreeSet<>(new Comparator<String>() {
			@Override
			public int compare(String arg0, String arg1) {
				int diff = arg1.length() - arg0.length();
				if (diff == 0) {
					diff = arg0.compareTo(arg1);
				}
				return diff;
			}	
		});
		sortedConfigCombos.addAll(configCombos);
		
		List<SyntaxGroup> atomicSets = new ArrayList<>();
		int k = 0;
		List<Color> palette = ColorPicker.generatePalette(sortedConfigCombos.size());
		for (String combo : sortedConfigCombos) {
			Set<UUID> ids = idsForConfigs.remove(combo);
			Set<Configuration> configs = new HashSet<>();
			String[] indices = combo.split("\\s+"); // split on whitespace
			for (String token : indices) {
				if (token.isEmpty()) continue;
				int i = Integer.parseInt(token);
				configs.add(platform.configurations.get(i - 1));
			}
			atomicSets.add(new SyntaxGroup(configs, ids, palette.get(k)));
			k += 1;
			
			// remove ids from all remaining combos
			List<String> toRemove = new ArrayList<>();
			for (Entry<String, Set<UUID>> entry : idsForConfigs.entrySet()) {
				entry.getValue().removeAll(ids);
				if (entry.getValue().isEmpty()) {
					toRemove.add(entry.getKey());
				}
			}
			
			for (String emptyCombo : toRemove) {
				idsForConfigs.remove(emptyCombo);
			}
		}
		
		return atomicSets;
	}

}

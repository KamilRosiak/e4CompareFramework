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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;
import de.tu_bs.cs.isf.e4cf.extractive_mple.consts.MPLEEditorConsts;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;
import de.tu_bs.cs.isf.e4cf.extractive_mple_platform_view.IFeatureLocaterExtension;

public class FeatureLocator implements IFeatureLocaterExtension {
	
	private MPLPlatform mpl;
	
	@Inject
	private ServiceContainer services;

	@Override
	public void locateFeatures(ServiceContainer container, MPLPlatform platform) {
		this.services = container;
		mpl = platform;
		System.out.println("Received platform: " + platform.name);
		List<Set<UUID>> atomicSets = calculateAtomicSets(platform);
		String workspace = services.workspaceFileSystem.getWorkspaceDirectory().getAbsolutePath();
		//FileStreamUtil.writeTextToFile(workspace + "/atomicSets.txt", atomicSets.toString());
		services.eventBroker.send("atomic_sets_found", atomicSets);
	}
	
	private List<Set<UUID>> calculateAtomicSets(MPLPlatform platform) {
		HashSet<UUID> uuids = new HashSet<>();
		platform.model.getAllUUIDS(uuids);
		
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
		
		List<Set<UUID>> atomicSets = new ArrayList<>();
		for (String combo : sortedConfigCombos) {
			Set<UUID> ids = idsForConfigs.remove(combo);
			atomicSets.add(ids);
			
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
	
	@Optional
	@Inject
	public void receiveMpl(@UIEventTopic(MPLEEditorConsts.SHOW_MPL) MPLPlatform platform) {
		mpl = platform;
		System.out.println("Received platform");
	}
	
	@PostConstruct
	public void initialize(Composite parent, ServiceContainer services, IEclipseContext context) {
		System.out.println("post construct");
	}

}

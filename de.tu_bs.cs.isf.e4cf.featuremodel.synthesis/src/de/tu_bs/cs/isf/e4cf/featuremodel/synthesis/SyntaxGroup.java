package de.tu_bs.cs.isf.e4cf.featuremodel.synthesis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.Collection;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;
import javafx.scene.paint.Color;

public class SyntaxGroup {
	
	private final SortedSet<Configuration> configurations;
	
	private final Set<UUID> uuids;
	
	private final Color color;
	
	public SyntaxGroup(Set<Configuration> configurations) {
		this.configurations = new LexicalConfigTreeSet(configurations);
		
		this.uuids = new HashSet<>();
		Iterator<Configuration> configs = configurations.iterator();
		if (configurations.size() > 0) {
			this.uuids.addAll(configs.next().getUUIDs());
			while (configs.hasNext()) { // intersection of configs
				this.uuids.retainAll(configs.next().getUUIDs());
			}
		}
		
		this.color = Color.web("#fff");
	}
	
	public SyntaxGroup(Set<Configuration> configs, Set<UUID> uuids, double hue) {
		this.configurations = new LexicalConfigTreeSet(configs);
		this.uuids = new HashSet<>();
		this.uuids.addAll(uuids);
		this.color = Color.hsb(hue, 0.8, 0.5);
		
	}

	public void addConfiguration(Configuration config) {
		this.configurations.add(config);
		if (this.uuids.isEmpty()) {
			this.uuids.addAll(config.getUUIDs());
		} else {
			// calculate intersection of configs
			this.uuids.retainAll(config.getUUIDs());
		}
	}

	/**
	 * @return the uuids
	 */
	public Set<UUID> getUuids() {
		return uuids;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public String getNormalizedName() {
		return getName(this.configurations);
	}
	
	public static String getNormalizedName(Collection<Configuration> configs) {
		SortedSet<Configuration> sortedConfigs = new LexicalConfigTreeSet(configs);
		return getName(sortedConfigs);
	}
	
	private static String getName(SortedSet<Configuration> configs) {
		StringBuilder strBuilder = new StringBuilder();
		for (Configuration config : configs) {
			strBuilder.append(String.format("%s ", config.getName()));
		}
		return strBuilder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(configurations, uuids);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SyntaxGroup other = (SyntaxGroup) obj;
		return Objects.equals(configurations, other.configurations) && Objects.equals(uuids, other.uuids);
	}
	
	
	private static final class LexicalConfigTreeSet extends TreeSet<Configuration> {
		private static final long serialVersionUID = 8266204679820046088L;

		public LexicalConfigTreeSet() {
			super((c1, c2) -> c1.getName().compareTo(c2.getName()));
		}
		
		public LexicalConfigTreeSet(Collection<? extends Configuration> c) {
			this();
			this.addAll(c);
		}
		
	}
	

}

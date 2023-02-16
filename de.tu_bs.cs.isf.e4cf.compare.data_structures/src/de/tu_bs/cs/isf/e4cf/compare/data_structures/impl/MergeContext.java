package de.tu_bs.cs.isf.e4cf.compare.data_structures.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.CloneConfiguration;

public class MergeContext {
	public Map<UUID, UUID> changedUUIDs = new HashMap<UUID, UUID>();
}

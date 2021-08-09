package de.tu_bs.cs.isf.e4cf.refactoring.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentLayer;


public class SynchronizationUtil {

	public static List<ComponentLayer> getComponentLayers(Set<String> nodeTypes) {

		List<ComponentLayer> componentLayers = new ArrayList<ComponentLayer>();
		for (String nodeType : nodeTypes) {
			boolean refactor = nodeType.equals("MethodDeclaration");
			componentLayers.add(new ComponentLayer(nodeType, refactor));
		}

		return componentLayers;
	}

	public static List<ComponentLayer> getComponentLayers(List<String> nodeTypes1, List<String> nodeTypes2) {

		Set<String> types = new HashSet<String>();
		types.addAll(nodeTypes1);
		types.addAll(nodeTypes2);

		return getComponentLayers(types);
	}	

}

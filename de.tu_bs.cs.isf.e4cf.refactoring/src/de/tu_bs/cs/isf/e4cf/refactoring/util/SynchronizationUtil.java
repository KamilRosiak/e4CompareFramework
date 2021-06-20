package de.tu_bs.cs.isf.e4cf.refactoring.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.refactoring.model.RefactoringLayer;

public class SynchronizationUtil {

	public static List<RefactoringLayer> getRefactoringLayers(Set<String> nodeTypes) {
	
		
		List<RefactoringLayer> refactoringLayers = new ArrayList<RefactoringLayer>();
		for (String nodeType : nodeTypes) {
			boolean refactor = nodeType.equals("MethodDeclaration");
			refactoringLayers.add(new RefactoringLayer(nodeType, refactor));
		}

		return refactoringLayers;
	}

	public static List<RefactoringLayer> getRefactoringLayers(List<String> nodeTypes1, List<String> nodeTypes2) {

		Set<String> types = new HashSet<String>();
		types.addAll(nodeTypes1);
		types.addAll(nodeTypes2);

		return getRefactoringLayers(types);
	}

}

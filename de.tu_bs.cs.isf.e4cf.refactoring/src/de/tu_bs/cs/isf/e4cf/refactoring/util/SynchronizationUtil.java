package de.tu_bs.cs.isf.e4cf.refactoring.util;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.refactoring.model.RefactoringLayer;

public class SynchronizationUtil {

	public static List<RefactoringLayer> getRefactoringLayers() {

		List<RefactoringLayer> refactoringLayers = new ArrayList<RefactoringLayer>();
		refactoringLayers.add(new RefactoringLayer("MethodDeclaration", true));
		refactoringLayers.add(new RefactoringLayer("CompilationUnit", false));
		refactoringLayers.add(new RefactoringLayer("IfStmt", false));
		refactoringLayers.add(new RefactoringLayer("ThenStmt", false));
		refactoringLayers.add(new RefactoringLayer("ForStmt", false));

		return refactoringLayers;
	}

}

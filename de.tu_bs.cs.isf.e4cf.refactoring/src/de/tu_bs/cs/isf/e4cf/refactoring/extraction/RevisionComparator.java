package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Container;
import de.tu_bs.cs.isf.e4cf.refactoring.model.RevisionComparison;

@Singleton
@Creatable
public class RevisionComparator {

	private CompareEngineHierarchical compareEngine;

	public RevisionComparator() {
		compareEngine = new CompareEngineHierarchical(new SortingMatcher(), new MetricImpl("test"));
	}

	public RevisionComparison compare(CloneModel cloneModel, Tree tree2) {
		Tree tree1 = cloneModel.getTree();

		Comparison<Node> comparison = compareEngine.compare(tree1.getRoot(), tree2.getRoot());
		RevisionComparison revisionComparison = new RevisionComparison();
		match(comparison, revisionComparison, cloneModel);
		return revisionComparison;

	}

	private void match(Comparison<Node> comparison, RevisionComparison revisionComparison, CloneModel cloneModel) {

		Node leftArtifact = comparison.getLeftArtifact();
		Node rightArtifact = comparison.getRightArtifact();

		if (leftArtifact != null && cloneModel.isInsideComponent(leftArtifact)) {

			if (rightArtifact == null) {
				Component component = cloneModel.getComponent(leftArtifact);
				revisionComparison.getDeletedNodes().add(new Container(component, leftArtifact));
			} else {
				Component component = cloneModel.getComponent(leftArtifact);
				revisionComparison.getMatchedNodes().put(new Container(component, leftArtifact), rightArtifact);
			}
			
			return;
		}

		if (leftArtifact != null && rightArtifact != null) {
			for (Comparison<Node> childComparison : comparison.getChildComparisons()) {
				match(childComparison, revisionComparison, cloneModel);
			}
		}

	}

}

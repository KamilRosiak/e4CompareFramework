package de.tu_bs.cs.isf.e4cf.compare.comparator.impl.node;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;
import de.tu_bs.cs.isf.e4cf.compare.comparator.templates.AbstractResultElement;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
/**
 * A concrete class that stores the comparison information of a comparator.
 * @author Kamil Rosiak
 *
 */
public class NodeResultElement extends AbstractResultElement<Node>{

	public NodeResultElement(Comparator<Node> usedComparator, float similarity) {
		super(usedComparator, similarity);
	}

}

package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.python.adjust;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.cpp.adjust.AdjustRename;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.cpp.adjust.Const;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.cpp.adjust.TreeAdjuster;


public class PAdjustAll extends TreeAdjuster{

	
	public void adjustAll(Node rootNode) {
		
		removeAndRenameNodes(rootNode);
		
		TreeAdjuster importAdjuster = new PAdjustImports();
		importAdjuster.recursiveAdjust(rootNode);
		
		TreeAdjuster ifAdjuster = new PAdjustIf();
		ifAdjuster.recursiveAdjust(rootNode);
		
		TreeAdjuster argumentAdjuster = new PAdjustArgument();
		argumentAdjuster.recursiveAdjust(rootNode);
		
		TreeAdjuster assignmentAdjuster = new PAdjustAssignment();
		assignmentAdjuster.recursiveAdjust(rootNode);
		

	}
	
	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		RenamerPython.getInstance().renameNode(node);
		if (nodeType.equals(Const.BODY) && parent.getNodeType().equals(Const.C_UNIT)) {
			node.cutWithoutChildren();
		}
	}

	
	private void removeAndRenameNodes(Node rootNode) {
		TreeAdjuster nodeAdjuster = new PAdjustNodes();
		nodeAdjuster.recursiveAdjust(rootNode);
		
		TreeAdjuster renameAdjuster = new AdjustRename();
		renameAdjuster.recursiveAdjust(rootNode);
		
		recursiveAdjust(rootNode);
	}
}

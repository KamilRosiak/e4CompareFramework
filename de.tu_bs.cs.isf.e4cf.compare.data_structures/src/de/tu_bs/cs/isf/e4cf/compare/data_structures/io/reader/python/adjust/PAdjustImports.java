package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.python.adjust;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.cpp.adjust.Const;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.cpp.adjust.TreeAdjuster;

public class PAdjustImports extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (nodeType.equals(Const.C_UNIT)) {
			boolean hasImports = false;
			List<Node> oldImportNodes = new ArrayList<>();
			for (Node child: node.getChildren()) {
				if (child.getNodeType().equals("Import") || child.getNodeType().equals("ImportFrom")) {
					hasImports = true;
					oldImportNodes.add(child);
					
				}
			}
			if (hasImports) {
				Node imports = new NodeImpl("Imports");
				imports.setParent(node);
				node.addChild(imports, 0);
				for (Node oldImport: oldImportNodes) {
					oldImport.setNodeType("Import");
					String value = oldImport.getChildren().get(0).getChildren().get(0).getValueAt(0);
					oldImport.addAttribute(Const.NAME_BIG, value);
					oldImport.getChildren().clear();
					oldImport.updateParent(imports);
				}
			}
		}

	}

}

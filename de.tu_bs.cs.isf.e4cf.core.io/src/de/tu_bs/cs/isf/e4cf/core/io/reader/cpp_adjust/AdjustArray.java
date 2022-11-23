package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class AdjustArray extends TreeAdjuster{

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (nodeType.equals("index") && parent.getParent().getParent().getNodeType().equals("Initialization")) {
			if (!parent.getAttributes().isEmpty()) {
				String name = parent.getValueAt(0);
				String value = node.getChildren().get(0).getChildren().get(0).getAttributeForKey("Name").getAttributeValues().get(0).getValue().toString();

				//create correct nodes
				Node realParent = parent.getParent().getParent().getParent();
				Node arrExpr = new NodeImpl("ArrayAccessExpr", realParent);
				Node nameExpr = new NodeImpl("NameExpr", arrExpr);
				arrExpr.addAttribute(new AttributeImpl("Value", new StringValueImpl(value)));
				nameExpr.addAttribute(new AttributeImpl("Name", new StringValueImpl(name)));
				parent.getParent().getParent().cut();
				realParent.setNodeType("VariableDeclarator");
				realParent.getParent().setNodeType("VariableDeclarationExpr");
			}
			
		} else if (nodeType.equals("index")) {
			Attribute oldAttribute = null;
			for (Attribute a : parent.getParent().getAttributes()) {
				if (a.getAttributeKey().equals("Type")) {
					oldAttribute = a;
				}
			}
			if (oldAttribute != null) {
				String oldValue = oldAttribute.getAttributeValues().get(0).getValue().toString();
				oldAttribute.getAttributeValues().get(0).setValue(oldValue + "[]");
			}
			node.cut();
		}
		
		//TODO rename "Name: 0,1,2.." into "Value: 0,1,2.." 
		if (nodeType.equals("Body") && parent.getParent().getParent().getNodeType().equals("FieldDeclaration")) {
			node.setNodeType("ArrayInitializerExpr");
			int length = node.getChildren().size();
			for (int i = 0; i < length; i++) {
				node.getChildren().get(0).cutWithoutChildren();
			}
		}
		
	}

}

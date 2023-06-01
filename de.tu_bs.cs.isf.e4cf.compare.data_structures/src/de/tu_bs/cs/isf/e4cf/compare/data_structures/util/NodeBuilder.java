package de.tu_bs.cs.isf.e4cf.compare.data_structures.util;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.BoolValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.FloatValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.IntegerValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

public class NodeBuilder {

	/**
	 * creates a node with the given type
	 * 
	 */
	public static Node createNode(String nodeType) {
		Node node = new NodeImpl(nodeType);
		return node;
	}

	public static <T> Attribute createAttribute(String attrKey, T value) {
		Attribute attr = new AttributeImpl(attrKey);
		attr.addAttributeValue(createValue(value));
		return attr;
	}

	public static <T> Value<?> createValue(T value) {
		Value<?> v = null;
		if (value instanceof String) {
			v = new StringValueImpl((String) value);
		}

		if (value instanceof Integer) {
			v = new IntegerValueImpl((Integer) value);
		}

		if (value instanceof Float) {
			v = new FloatValueImpl((Float) value);
		}

		if (value instanceof Boolean) {
			v = new BoolValueImpl((Boolean) value);
		}

		if (v == null) {
			v = new StringValueImpl((String) value);
		}
		return v;
	}
}

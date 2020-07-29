package de.tu_bs.cs.isf.e4cf.graph.core.elements.model;

import java.util.List;

import com.google.common.collect.Lists;

import de.tu_bs.cs.isf.e4cf.graph.core.elements.templates.AbstractGraphItem;

public class SimpleGraph extends AbstractGraphItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3352006607674685826L;
	
	public static final String PROP_CHILD_ELEMENTS = "childElements";

    private List<AbstractGraphItem> childElements = Lists.newArrayList();

    public void addChildElement(AbstractGraphItem node) {
        childElements.add(node);
        propertyChgSup.firePropertyChange(PROP_CHILD_ELEMENTS, null, node);
    }

    public void addChildElement(AbstractGraphItem node, int idx) {
        childElements.add(idx, node);
        propertyChgSup.firePropertyChange(PROP_CHILD_ELEMENTS, null, node);
    }

    public List<AbstractGraphItem> getChildElements() {
        return childElements;
    }

    public void removeChildElement(AbstractGraphItem node) {
        childElements.remove(node);
        propertyChgSup.firePropertyChange(PROP_CHILD_ELEMENTS, node, null);
    }
}



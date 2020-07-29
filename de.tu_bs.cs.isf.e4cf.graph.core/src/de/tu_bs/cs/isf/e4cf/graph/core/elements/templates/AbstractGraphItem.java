package de.tu_bs.cs.isf.e4cf.graph.core.elements.templates;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 * This class provides the {@link PropertyChangeSupport} for the GraphItems.
 *
 */
public class AbstractGraphItem implements Serializable {

	 /**
	 * UUID
	 */
	private static final long serialVersionUID = -5188258369891062860L;
	protected PropertyChangeSupport propertyChgSup = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChgSup.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChgSup.removePropertyChangeListener(listener);
    }
}

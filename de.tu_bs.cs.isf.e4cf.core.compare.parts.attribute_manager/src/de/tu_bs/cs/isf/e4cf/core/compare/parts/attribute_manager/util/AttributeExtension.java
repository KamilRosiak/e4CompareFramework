package de.tu_bs.cs.isf.e4cf.core.compare.parts.attribute_manager.util;

import de.tu_bs.cs.isf.e4cf.core.compare.parts.attribute_manager.extension.IAttributeExtension;
import de.tu_bs.cs.isf.e4cf.core.compare.templates.AbstractAttribute;

public class AttributeExtension {
	private AbstractAttribute attr;
	private IAttributeExtension extension;
	
	public AttributeExtension(AbstractAttribute attr, IAttributeExtension extension) {
		setAttribute(attr);
		setExtension(extension);
	}

	public AbstractAttribute getAttribute() {
		return attr;
	}

	public void setAttribute(AbstractAttribute attr) {
		this.attr = attr;
	}

	public IAttributeExtension getExtension() {
		return extension;
	}

	public void setExtension(IAttributeExtension extension) {
		this.extension = extension;
	}
}

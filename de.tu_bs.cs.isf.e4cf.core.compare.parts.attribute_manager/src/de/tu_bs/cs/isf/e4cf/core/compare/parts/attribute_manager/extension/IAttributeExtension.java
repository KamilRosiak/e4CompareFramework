package de.tu_bs.cs.isf.e4cf.core.compare.parts.attribute_manager.extension;

import java.util.List;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import de.tu_bs.cs.isf.e4cf.core.compare.templates.AbstractAttribute;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;

/**
 * This interface can be implemented to extend the attribute manager.
 * @author NoLimit
 *
 */
public interface IAttributeExtension {
	/**
	 * This method returns the attributes type name like "xxxx Attributes"
	 * @return
	 */
	public String getAttributeTypeName();
	
	/**
	 * This method returns the attributes group color
	 */
	public Color getAttributeTypColor();
	
	/**
	 * This method returns the attribute color
	 */
	public Color getAttributeColor();
	
	/**
	 * return a list of abstract attributes
	 * @return
	 */
	public List<AbstractAttribute> getAttributes();
	
	/**
	 * This method returns the icon that will be shown in the view
	 */
	public Image getIcon(RCPImageService imageService);
	
	/**
	 * Implement a double click action for the given attribute like add to configuration manager.
	 */
	public void execute(ServiceContainer services, AbstractAttribute attribute);
}

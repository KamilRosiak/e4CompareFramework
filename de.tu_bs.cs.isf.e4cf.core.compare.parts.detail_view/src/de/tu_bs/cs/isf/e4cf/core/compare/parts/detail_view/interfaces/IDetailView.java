package de.tu_bs.cs.isf.e4cf.core.compare.parts.detail_view.interfaces;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import de.tu_bs.cs.isf.e4cf.core.compare.parts.detail_view.DetailViewController;

/**
 * An interface representing a single tab within a tab folder for the part <i>Detail View</i>.
 * 
 * @author Oliver Urbaniak, Kamil Rosiak
 *
 */
public interface IDetailView {
	/**
	 * This method is called by the controller itself after the instantiation of a extension.
	 * @param controller
	 */
	public void setController(DetailViewController controller);
	
	/**
	 * Creates the composite structure of the view. The <i>root</i> is <b>not</b> intended to be the parent of this view 
	 * as it is attached to a tab item which represents the parent.
	 * 
	 * @param root
	 * 
	 * @see TabFolder
	 * @see TabItem
	 */
	public void createControl(Composite root);
	
	/**
	 * Refreshes the view with a new container.
	 * Only invoked if the <i>element</i> is supported by this view
	 * 
	 * @param container
	 * @see #isSupportedContainerType(Class)
	 */
	public void showContainer(Object element);
	
	/**
	 * Checks for supported object types.
	 * 
	 * @param <T>
	 * @param elementClass
	 * @return
	 */
	public <T>boolean isSupportedContainerType(Class<T> elementClass);
	
	public String getName();
	
	/**
	 * 
	 */
	public void dispose();
}

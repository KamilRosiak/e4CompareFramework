package de.tu_bs.cs.isf.e4cf.core.compare.parts.attribute_manager.view;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.core.compare.parts.attribute_manager.AttributeManagerController;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.attribute_manager.extension.IAttributeExtension;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.attribute_manager.util.AMStringTable;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.attribute_manager.util.AttributeExtension;
import de.tu_bs.cs.isf.e4cf.core.compare.string_table.E4CompareStringTable;
import de.tu_bs.cs.isf.e4cf.core.compare.templates.AbstractAttribute;
import de.tu_bs.cs.isf.e4cf.core.compare.templates.tree.ConfigTree;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

/**
 * This class represents the view part of a MVC implementation. It process all extension points and shows the attributes. 
 * @author {Kamil Rosiak}
 *
 */
public class AttributeManagerView {
	private Tree tree;
	private ServiceContainer services;
	private TreeItem stContainer = null;
	private TreeItem stImplContainer = null;
	private TreeItem sfcContainer = null;
	private TreeItem sfcImplContainer = null;
	private TreeItem ldContainer = null;
	private TreeItem ldImplContainer = null;
	private TreeItem fbdContainer = null;
	private TreeItem fbdImplContainer = null;
	private TreeItem containerItem = null;
	
	public AttributeManagerView(AttributeManagerController controller, Composite parent, ServiceContainer serviceContainer) {
		services = serviceContainer;
		createControl(parent);
		getExtensions();
	}
	
	private void createControl(Composite parent) {
		parent.setLayout(new GridLayout(1,true));
		ConfigTree configTree = new ConfigTree(parent, SWT.MULTI);
		this.tree = configTree.getTree();
		this.tree.addListener(SWT.MouseDoubleClick, e -> {
			for(TreeItem selectedItem :  tree.getSelection()) {
				if(selectedItem.getData() != null && selectedItem.getData() instanceof AttributeExtension) {
					AttributeExtension attrExtension = (AttributeExtension)selectedItem.getData();
					attrExtension.getExtension().execute(services, attrExtension.getAttribute());
				}	
			}
	
		});
	}

	public void getExtensions() {
		List<Object> attrExtensions = RCPContentProvider.getInstanceFromBundle(E4CompareStringTable.ATTRIBUTE_MANAGER_EXTENSION,E4CompareStringTable.ATTRIBUTE_MANAGER_ATTR);

		for(Object attr : attrExtensions) {
			if(attr instanceof IAttributeExtension) {
				IAttributeExtension extension = (IAttributeExtension)attr;
				
				//IF IEC 61131-3 Language Attributes are loaded create a container for this types.
				if(extension.getAttributeTypeName().equals(AMStringTable.ST_LANGUAGE_ATTR_TYPE) || 
						extension.getAttributeTypeName().equals(AMStringTable.SFC_LANGUAGE_ATTR_TYPE) ||
						extension.getAttributeTypeName().equals(AMStringTable.LD_LANGUAGE_ATTR_TYPE) ||
						extension.getAttributeTypeName().equals(AMStringTable.ST_IMPLEMENTATION_ATTR_TYPE) ||
						extension.getAttributeTypeName().equals(AMStringTable.SFC_IMPLEMENTATION_ATTR_TYPE) ||
						extension.getAttributeTypeName().equals(AMStringTable.LD_IMPLEMENTATION_ATTR_TYPE) ||
						extension.getAttributeTypeName().equals(AMStringTable.FBD_IMPLEMENTATION_ATTR_TYPE) ||
						extension.getAttributeTypeName().equals(AMStringTable.FBD_LANGUAGE_ATTR_TYPE)
						) {
					if(containerItem == null) {
						containerItem = new TreeItem(tree, SWT.None);
						decorateTreeItem(containerItem, AMStringTable.LANGUAGE_ATTR_TYPE, "Container", "Holds all attribute of this type",
						extension.getIcon(services.imageService), extension.getAttributeTypColor());
					}
		
					/**
					 * Build Items for Structured Text
					 */
					if(extension.getAttributeTypeName().equals(AMStringTable.ST_IMPLEMENTATION_ATTR_TYPE) 
							|| extension.getAttributeTypeName().equals(AMStringTable.ST_LANGUAGE_ATTR_TYPE)) {
						//create container item for ST attributes 
						if(stContainer == null) {
							stContainer = new TreeItem(containerItem,SWT.None);
							decorateTreeItem(stContainer, AMStringTable.ST_CONTAINER_NAME, AMStringTable.IMPL_CONTAINER_NAME, AMStringTable.IMPL_CONTAINER_DESC, extension.getIcon(services.imageService), extension.getAttributeTypColor());
	
							stImplContainer = new TreeItem(stContainer,SWT.None);
							decorateTreeItem(stImplContainer, AMStringTable.IMPLEMENTATION_OPTION, AMStringTable.IMPL_CONTAINER_NAME, AMStringTable.IMPL_CONTAINER_DESC, extension.getIcon(services.imageService), extension.getAttributeTypColor());
						}
						
						//process all ST Implementation Attributes
						if(extension.getAttributeTypeName().equals(AMStringTable.ST_IMPLEMENTATION_ATTR_TYPE)) {
							processAttributes(stImplContainer, extension);
						}
						//process all ST Language Attributes
						if(extension.getAttributeTypeName().equals(AMStringTable.ST_LANGUAGE_ATTR_TYPE)) {
							processAttributes(stContainer, extension);
						}		
					} 
		
					/**
					 * Build Items for Sequential Function Chart
					 */
					if(extension.getAttributeTypeName().equals(AMStringTable.SFC_IMPLEMENTATION_ATTR_TYPE) 
							|| extension.getAttributeTypeName().equals(AMStringTable.SFC_LANGUAGE_ATTR_TYPE)) {
						//create container item for SFC attributes 
						if(sfcContainer == null) {
							sfcContainer = new TreeItem(containerItem,SWT.None);
							decorateTreeItem(sfcContainer, AMStringTable.SFC_CONTAINER_NAME, AMStringTable.IMPL_CONTAINER_NAME, AMStringTable.IMPL_CONTAINER_DESC, extension.getIcon(services.imageService), extension.getAttributeTypColor());
							sfcImplContainer = new TreeItem(sfcContainer,SWT.None);
							decorateTreeItem(sfcImplContainer, AMStringTable.IMPLEMENTATION_OPTION, AMStringTable.IMPL_CONTAINER_NAME, AMStringTable.IMPL_CONTAINER_DESC, extension.getIcon(services.imageService), extension.getAttributeTypColor());
						}
						//process all SFC Implementation Attributes
						if(extension.getAttributeTypeName().equals(AMStringTable.SFC_IMPLEMENTATION_ATTR_TYPE)) {
							processAttributes(sfcImplContainer, extension);
						}
						
						//process all SFC Language Attributes
						if(extension.getAttributeTypeName().equals(AMStringTable.SFC_LANGUAGE_ATTR_TYPE)) {
							processAttributes(sfcContainer, extension);
						}
	
					} 
					
					if(extension.getAttributeTypeName().equals(AMStringTable.LD_IMPLEMENTATION_ATTR_TYPE) 
							|| extension.getAttributeTypeName().equals(AMStringTable.LD_LANGUAGE_ATTR_TYPE)) {
						//create container item for LD attributes 
						if(ldContainer == null) {
							ldContainer = new TreeItem(containerItem,SWT.None);
							decorateTreeItem(ldContainer, AMStringTable.LD_CONTAINER_NAME, AMStringTable.IMPL_CONTAINER_NAME, AMStringTable.IMPL_CONTAINER_DESC, extension.getIcon(services.imageService), extension.getAttributeTypColor());
							
							ldImplContainer = new TreeItem(ldContainer,SWT.None);
							decorateTreeItem(ldImplContainer, AMStringTable.IMPLEMENTATION_OPTION, AMStringTable.IMPL_CONTAINER_NAME, AMStringTable.IMPL_CONTAINER_DESC, extension.getIcon(services.imageService), extension.getAttributeTypColor());
						}
						
						//process all LD Implementation Attributes
						if(extension.getAttributeTypeName().equals(AMStringTable.LD_IMPLEMENTATION_ATTR_TYPE)) {
							processAttributes(ldImplContainer, extension);
						}
						
						//process all LD Language Attributes
						if(extension.getAttributeTypeName().equals(AMStringTable.LD_LANGUAGE_ATTR_TYPE)) {
							processAttributes(ldContainer, extension);
						}
					} 
					
					if(extension.getAttributeTypeName().equals(AMStringTable.FBD_IMPLEMENTATION_ATTR_TYPE) 
							|| extension.getAttributeTypeName().equals(AMStringTable.FBD_LANGUAGE_ATTR_TYPE)) {
						//create container item for FBD attributes 
						if(fbdContainer == null) {
							fbdContainer = new TreeItem(containerItem,SWT.None);
							decorateTreeItem(fbdContainer, AMStringTable.FBD_CONTAINER_NAME, AMStringTable.IMPL_CONTAINER_NAME, AMStringTable.IMPL_CONTAINER_DESC, extension.getIcon(services.imageService), extension.getAttributeTypColor());
							
							fbdImplContainer = new TreeItem(fbdContainer,SWT.None);
							decorateTreeItem(fbdImplContainer, AMStringTable.IMPLEMENTATION_OPTION, AMStringTable.IMPL_CONTAINER_NAME, AMStringTable.IMPL_CONTAINER_DESC, extension.getIcon(services.imageService), extension.getAttributeTypColor());
						}
						
						//process all FBD Implementation Attributes
						if(extension.getAttributeTypeName().equals(AMStringTable.FBD_IMPLEMENTATION_ATTR_TYPE)) {
							processAttributes(fbdImplContainer, extension);
						}
						
						//process all FBD Language Attributes
						if(extension.getAttributeTypeName().equals(AMStringTable.FBD_LANGUAGE_ATTR_TYPE)) {
							processAttributes(fbdContainer, extension);
						}
					} 
					
					
				} else {
					TreeItem containerItem = new TreeItem(tree, SWT.None);
					decorateTreeItem(containerItem, extension.getAttributeTypeName(), "Container", "Holds all attribute of this type",
					extension.getIcon(services.imageService), extension.getAttributeTypColor());
					processAttributes(containerItem, extension);
				}

			}
		}
	}
	
	/**
	 * This method processes all attributes of an extension and creates a TreeItem for each.
	 */
	public void processAttributes(TreeItem parent,IAttributeExtension extension) {
		for(AbstractAttribute absAttr : extension.getAttributes()) {
			decorateTreeItem(new TreeItem(parent,SWT.None), absAttr.getAttributeName(), extension.getAttributeTypeName(), absAttr.getAttributDescription(), 
					extension.getIcon(services.imageService), extension.getAttributeColor(), new AttributeExtension(absAttr, extension));
		}
	}
	
	/**
	 * This method decorates a created TreeItem.
	 */
	public void decorateTreeItem(TreeItem item, String attrName, String attrTyp, String attrDesc, Image icon, Color background, Object attr ) {
		decorateTreeItem(item, attrName, attrTyp, attrDesc, icon, background);
		item.setData(attr);
	}
	
	/**
	 * This method decorates a created TreeItem.
	 */
	public void decorateTreeItem(TreeItem item , String attrName, String attrTyp, String attrDesc, Image icon, Color background) {
		item.setText(new String[] {attrName,attrTyp,attrDesc});
		item.setImage(icon);
		item.setBackground(background);
	}
	
	public Tree getTree() {
		return tree;
	}
}

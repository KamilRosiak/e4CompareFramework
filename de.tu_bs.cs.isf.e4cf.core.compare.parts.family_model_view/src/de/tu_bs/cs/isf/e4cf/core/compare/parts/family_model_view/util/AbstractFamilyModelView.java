package de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.util;

import org.eclipse.emf.ecore.EObject;

import de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.stringtable.FaMoFileTable;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.view.elements.FXFamilyModelElement;
import de.tu_bs.cs.isf.e4cf.core.compare.string_table.E4CompareEventTable;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import familyModel.VariabilityCategory;
import familyModel.VariabilityGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class AbstractFamilyModelView {
	public ServiceContainer services;
	public TreeView<FXFamilyModelElement> tree;
	
	
	public AbstractFamilyModelView(ServiceContainer services) {
		this.services = services;
	}
	
	/**
	 * This method creates a TreeItem based on the VariabilityCatagory of the VariabilityGroup.
	 * @param element
	 * @return
	 */
	protected TreeItem<FXFamilyModelElement> createFamilyModelTreeItem(VariabilityGroup element) {
		if(element.getVariability().equals(VariabilityCategory.MANDATORY)) {
			return createMandatoryItem(element);
		}
		if(element.getVariability().equals(VariabilityCategory.ALTERNATIVE)) {
			return createAlternativeItem(element);
		}
		if(element.getVariability().equals(VariabilityCategory.OPTIONAL)) {
			return createOptionalItem(element);
		}
		return null;
	}
	
	/**
	 * This Method creates a TreeItem in a optional variability category fashion.
	 */
	private TreeItem<FXFamilyModelElement> createAlternativeItem(VariabilityGroup group) {
		return createTreeItem(group, FaMoFileTable.FV_ALTERNATIVE_16);
	}
	
	/**
	 * This Method creates a TreeItem in a optional variability category fashion.
	 */
	private TreeItem<FXFamilyModelElement> createOptionalItem(VariabilityGroup group) {
		return createTreeItem(group, FaMoFileTable.FV_OPTIONAL_16);
	}
	
	/**
	 * This Method creates a TreeItem in a mandatory variability category fashion.
	 */
	private TreeItem<FXFamilyModelElement> createMandatoryItem(VariabilityGroup group) {
		return createTreeItem(group, FaMoFileTable.FV_MANDATORY_16);
	}
	
	/**
	 * This method creates a TreeItem with an icon on the left side.
	 */
	protected TreeItem<FXFamilyModelElement> createTreeItem(EObject data, String iconPath) {
		TreeItem<FXFamilyModelElement> item = new TreeItem<>(new FXFamilyModelElement(data));
		item.setGraphic(new ImageView(new Image(iconPath)));
		item.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
			services.eventBroker.send(E4CompareEventTable.SHOW_DETAIL_EVENT, item.getValue());
		});
		return item;	
	}
	
	public TreeView<FXFamilyModelElement> getTree() {
		return tree;
	}
}

package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.IconProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.LabelProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.elements.FXFamilyModelElement;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class FXTreeBuilder {

	public LabelProvider labelProvider;
	public IconProvider iconProvider;
	
	public FXTreeBuilder(LabelProvider labelProvider, IconProvider iconProvider) {
		this.labelProvider = labelProvider;
		this.iconProvider = iconProvider;
	}	
	
	public <T> TreeItem<FXFamilyModelElement> createTreeItem(T data) {
		FXFamilyModelElement element = new FXFamilyModelElement(data);
		TreeItem<FXFamilyModelElement> item = new TreeItem<>(element);
		
		// set label
		String label = labelProvider.getLabel(data);
		if (label != null) {
			element.setLabel(label);			
		} else {
			element.setLabel(data.toString());
		}
		
		// set icon
		List<Image> icons = iconProvider.getIcon(data);
		Node im = createGraphic(icons);
		if (im != null) {
			item.setGraphic(im);			
		}
		
		return item;
	}

	private Node createGraphic(List<Image> icons) {
		if (icons.isEmpty()) {
			return null;
		} else if (icons.size() == 1) {
			return createGraphic(icons.get(0));
		} else {
			HBox hbox = new HBox(); 
			hbox.setSpacing(5);
			for (Image icon : icons) {
				hbox.getChildren().add(createGraphic(icon)); 
			}
			return hbox;
		}
	}
	
	private Node createGraphic(Image icon) {
		return new ImageView(icon);
	}
}

package de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.view.menu;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.ImageData;

import de.tu_bs.cs.isf.e4cf.core.compare.parts.detail_view.util.DetailViewStringTable;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.stringtable.FaMoStringTable;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.view.FamilyModelView;
import de.tu_bs.cs.isf.e4cf.core.compare.string_table.E4CompareEventTable;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.embed.swt.SWTFXUtils;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

public class ShowDetailsItem extends MenuItem {

	private ServiceContainer serviceContainer;
	private Object selectedElement;

	public ShowDetailsItem(FamilyModelView view, ServiceContainer serviceContainer) {
		this.serviceContainer = serviceContainer;
		this.selectedElement = view.getFamilyModel();
		setText("Show Details");
		
		ImageDescriptor im = serviceContainer.imageService.getImageDescriptor(FaMoStringTable.BUNDLE_NAME, "icons/detail_16.png");
        ImageData imData = im.getImageData(100);
        
        Image image = SWTFXUtils.toFXImage(imData, new WritableImage(imData.width, imData.height));
        ImageView imView = new ImageView(image);
        setGraphic(imView);
        
        // always keep track on the current selection
        view.tree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	this.selectedElement = newValue.getValue().getElement();
        });
	}
	
	@Override
	public void fire() {
		serviceContainer.partService.showPart(DetailViewStringTable.FAMILYMODE_DETAIL_VIEW_ID);
		serviceContainer.eventBroker.send(E4CompareEventTable.SHOW_DETAIL_EVENT, selectedElement);
		
		super.fire();
	}

	
	
}

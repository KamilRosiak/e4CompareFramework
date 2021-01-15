package de.tu_bs.cs.isf.e4cf.parts.project_explorer;

import java.util.List;

import org.eclipse.e4.core.contexts.IEclipseContext;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller.TagListCellController;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.Tag;
import javafx.scene.control.ListCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TagListCell extends ListCell<Tag> {

	private IEclipseContext context;

	public TagListCell(IEclipseContext context, List<Tag> selectedTags) {
		this.context = context;
	}

	@Override
	protected void updateItem(Tag item, boolean empty) {
		// TODO Auto-generated method stub
		super.updateItem(item, empty);

		if (empty) {
			setGraphic(null);
		} else {
			FXMLLoader<TagListCellController> loader = new FXMLLoader<TagListCellController>(context,
					StringTable.BUNDLE_NAME, FileTable.TAG_LIST_CELL);

			loader.getController().tagName.setText(item.getName());
			loader.getController().root.getChildren().add(0, createTagIcon(item.getColor()));
			setGraphic(loader.getNode());
		}
	}

	/**
	 * Generates a small circle node with a specific color
	 * 
	 * @param color the color of the circle
	 * @return the circle
	 */
	private Circle createTagIcon(Color color) {

		// TODO styling and layout
		DropShadow dropShadow = new DropShadow();
		dropShadow.setOffsetX(1);
		dropShadow.setOffsetY(1);
		dropShadow.setRadius(2);
		dropShadow.setColor(Color.GRAY);

		Circle circle = new Circle(6, color);
		circle.setEffect(dropShadow);
		return circle;
	}
}

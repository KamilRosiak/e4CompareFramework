package de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

public class ProjectExplorerToolBarController {
	
	/* Search {Textfield} | Undo Redo | NewFile NewFolder Import Rename Delete ShowInExplorer | CompareSelection ParseFiles*/
	
	private ToolBar bar;
	
	public ProjectExplorerToolBarController(ServiceContainer services, ToolBar toolbar) {
		bar = toolbar;
		
		// Search Section
		TextField search = new TextField();
		search.setVisible(false);
		search.setOnAction(actionEvent -> {
			// TODO Perform Filter / send filter event
			System.out.println(search.getText());
		});
		
		ImageView searchImage = services.imageService.getFXImage(null, FileTable.SEARCH_PNG);
		Tooltip searchTooltip = new Tooltip("Search Files");
		ImageView xImage = services.imageService.getFXImage(null, FileTable.X_PNG);
		Tooltip closeSearchTooltip = new Tooltip("Exit Search");
		Button searchButton = new Button("", searchImage);
		searchButton.setTooltip(searchTooltip);
		searchButton.setOnAction(actionEvent -> {
			search.setVisible(!search.isVisible());
			// Removing and readding the search bar allows automatic resizing
			if (search.isVisible()) {
				searchButton.setGraphic(xImage);
				searchButton.setTooltip(closeSearchTooltip);
				bar.getItems().add(1, search);
			} else {
				searchButton.setGraphic(searchImage);
				searchButton.setTooltip(searchTooltip);
				search.setText("");
				bar.getItems().remove(search);
				// TODO Remove all filters from project view / send event
			}
			
		});
		bar.getItems().add(searchButton);
		
		bar.getItems().add(new Separator());
		
		// Command Actions TODO to enable these buttons command stack systems are required
		ImageView undoImage = services.imageService.getFXImage(null, FileTable.UNDO_PNG);
		Button undoButton = new Button("", undoImage);
		undoButton.setTooltip(new Tooltip("Undo"));
		undoButton.setDisable(true);
		undoButton.setOnAction(actionEvent -> {
			System.out.println("Undo");
		});
		bar.getItems().add(undoButton);
		
		ImageView redoImage = services.imageService.getFXImage(null, FileTable.REDO_PNG);
		Button redoButton = new Button("", redoImage);
		redoButton.setTooltip(new Tooltip("Redo"));
		redoButton.setDisable(true);
		redoButton.setOnAction(actionEvent -> {
			System.out.println("Redo");
		});
		bar.getItems().add(redoButton);
		
		bar.getItems().add(new Separator());
		
		// Context Menu Actions (Use Extensionpoint)
		// TODO add NewFile ActionHandler 
		
		
	}
	
	
	public void test() {
		System.out.println("Test");
		return;
	}

}

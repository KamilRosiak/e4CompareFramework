package de.tu_bs.cs.isf.e4cf.featuremodel.configuration.view;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import FeatureDiagram.Feature;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.templates.FXToolbar;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.JavaFXBuilder;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfigurationController;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.stringtable.FeatureModelConfigurationStrings;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.helper.FeatureDiagramIterator;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfiguration;
import javafx.embed.swt.FXCanvas;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.BorderPane;

public class FeatureConfigurationView {
	
	private FeatureConfigurationController controller;
	
	private Composite parent;
	private TreeView<FeatureSelectionElement> featureSelectionTree;
	
	private FeatureConfiguration featureConfiguration;

	public FeatureConfigurationView(Composite parent, FeatureConfigurationController controller) {
		this.parent = parent;
		this.controller = controller;
	}
	
	public void createControls() {
		
		FXCanvas canvas = new FXCanvas(parent, SWT.NONE);
		
		BorderPane layout = new BorderPane();
		
		// create a toolbar for controls
		Button saveButton = JavaFXBuilder.createButton("Save Configuration", event -> {
			String workspace = RCPContentProvider.getCurrentWorkspacePath();
			String filename = featureConfiguration.getName() + "." + FeatureModelConfigurationStrings.FC_FILE_EXTENSION;
			controller.saveConfiguration(workspace+filename);
		});
		Button loadButton = JavaFXBuilder.createButton("Load Configuration", event -> {
			controller.loadConfiguration(new Object());
		});
		Button checkButton = JavaFXBuilder.createButton("Check Configuration", event -> {
			controller.checkCurrentConfiguration();				
		});
		
		FXToolbar toolbar = new FXToolbar(30, 10);
		toolbar.addItems(saveButton, loadButton, checkButton);
		layout.setTop(toolbar.getHbox());
		BorderPane.setMargin(toolbar.getHbox(), new Insets(10));
		
		// feature configuration tree
		featureSelectionTree = new TreeView<FeatureSelectionElement>();
		layout.setCenter(featureSelectionTree);
		// selection event
		featureSelectionTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null) {
				return;
			}
            TreeItem<FeatureSelectionElement> selectedItem = (TreeItem<FeatureSelectionElement>) newValue;
            controller.getServices().eventBroker.send(FDEventTable.RESET_SELECTED_FEATURES, null);
            controller.getServices().eventBroker.send(FDEventTable.SET_SELECTED_FEATURE, selectedItem.getValue().getFeature());
            
		});
		
		Scene scene = new Scene(layout);
		canvas.setScene(scene);
	}
	
	public void refreshView(FeatureConfiguration fc) {
		this.featureConfiguration = fc;
		
		// iterates breadth-first over features
		Queue<CheckBoxTreeItem<FeatureSelectionElement>> parentQueue = new LinkedList<>();
		for (Iterator<Feature> it = new FeatureDiagramIterator(fc.getFeatureDiagram()); it.hasNext();) {
			Feature feature = it.next();
			
			// find the corresponding configuration entry and attach it to a tree item
			EMap<Feature, Boolean> featureSelection = fc.getFeatureSelection();
			int featureEntryIndex = featureSelection.indexOfKey(feature);
			Entry<Feature, Boolean> featureEntry = featureSelection.get(featureEntryIndex);
		
			FeatureSelectionElement element = new FeatureSelectionElement(featureSelection.get(featureEntryIndex));
			CheckBoxTreeItem<FeatureSelectionElement> item = createCheckedItem(element, true);

			// distinguish the root feature from others 
			if (feature.getParent() == null) {
				featureSelectionTree.setRoot(item);
				item.setSelected(true);
			} else if (!parentQueue.isEmpty()) {				
				// connect new item to a parent item according to the feature tree
				CheckBoxTreeItem<FeatureSelectionElement> parentItem = parentQueue.peek();
				FeatureSelectionElement parentEntry = parentItem.getValue();
				
				while (parentEntry.getFeature() != featureEntry.getKey().getParent()) { 
					parentQueue.poll();
					parentItem = parentQueue.peek();
					parentEntry = parentItem.getValue();
				}		
				
				// find an initial selection state from the variability upwards
				if (parentItem.isSelected()) {
					Feature parentFeature = parentItem.getValue().getFeature();
					if (parentFeature.isOr() || parentFeature.isAlternative()) {
						// if the feature is the only child OR the feature is mandatory, select it too
						item.setSelected(parentFeature.getChildren().size() <= 1 || feature.isMandatory());
					} else {
						// in case of the parent feature variability state being AND, select feature
						item.setSelected(true);
					}
				} 
				
				parentItem.getChildren().add(item);
			} else {
				// error
				throw new RuntimeException("The stack should never be empty.");
			}
			
			parentQueue.add(item);
		}
		
		// set the cell factory
		featureSelectionTree.setCellFactory(CheckBoxTreeCell.<FeatureSelectionElement>forTreeView());
		
		expandTree(featureSelectionTree.getRoot());
		
	}

	private <T> void expandTree(TreeItem<T> item) {
		item.setExpanded(true);
		for (TreeItem<T> child : item.getChildren()) {
			expandTree(child);
		}
	}

	public void setFeatureConfiguration(FeatureConfiguration fc) {
		this.featureConfiguration = fc;
		
		refreshView(featureConfiguration);
	}
	
	private CheckBoxTreeItem<FeatureSelectionElement> createCheckedItem(FeatureSelectionElement element, boolean independent) {
		CheckBoxTreeItem<FeatureSelectionElement> item = new CheckBoxTreeItem<FeatureSelectionElement>(element);
		item.setIndependent(independent);
		item.setSelected(element.get().getValue());
		item.addEventHandler(CheckBoxTreeItem.<FeatureSelectionElement>checkBoxSelectionChangedEvent(), (event) -> {
			// propagate checkbox selection to configuration element
			item.getValue().setSelected(event.getTreeItem().isSelected());
			
			event.consume();
		});
		
		return item;
	}
	
	public FeatureConfiguration getFeatureConfiguration() {
		return featureConfiguration;
	}
}

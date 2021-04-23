package de.tu_bs.cs.isf.e4cf.compare.metric_view.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.fx.ui.controls.tree.FilterableTreeItem;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.compare.metric.util.MetricUtil;
import de.tu_bs.cs.isf.e4cf.compare.metric_view.components.FXComparatorElement;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;

/**
 * This class represents the controller class of the ui/view/MetricView.fxml and
 * implements all functionality.
 * 
 * @author Kamil Rosiak
 *
 */
public class MetricView implements Initializable {
	//IgnoreTable
	@FXML
	private TableView<Map> metricTable; 
	@FXML
	private TableColumn<Map, String> nameColumn;				
//	@FXML
//	private TableColumn ignoreColumn;
	
	@FXML
	private TreeTableView<FXComparatorElement> treeTable;
	@FXML
	private TreeTableColumn<FXComparatorElement, String> comparatorColumn;
	@FXML
	private TreeTableColumn<FXComparatorElement, Float> weightColumn;
	//Buttons
	@FXML
	private Button storeMetricButton;
	@FXML
	private Button loadMetricButton;
	@FXML
	private Button newMetricButton;
	@FXML
	private Button ignoreTypeButton;

	private MetricImpl currentMetric;
	
	private ObservableList<Map<String, String>> comparatorTypes;
	
	
	
	@FXML
	private void addToIgnoreList(ActionEvent event) {
		event.consume();
		
	}

	private void removeComparator(ActionEvent event) {
		ObservableList<TreeItem<FXComparatorElement>> selection = treeTable.getSelectionModel().getSelectedItems();
		
		for (TreeItem<FXComparatorElement> elem : selection) {
			TreeItem<FXComparatorElement> parent = elem.getParent();
			//subrootnode case
			if (treeTable.getRoot().getChildren().contains(elem)) {
				comparatorTypes.remove(elem.getValue().getComparatorType());
				parent.getChildren().remove(elem);
			}
			//no subrootnode case
			if (!parent.equals(treeTable.getRoot())) {
				//only element in subrootnode
				if (parent.getChildren().size() == 1) {
					parent.getChildren().remove(elem);
					treeTable.getRoot().getChildren().remove(parent);
					comparatorTypes.remove(elem.getValue().getComparatorType());
				} else {
					parent.getChildren().remove(elem);
				}
			}			
		}
		event.consume();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initButtons();
		initMetricTable();
		initIgnoreTable();

	}

	private void initButtons() {
		initStoreMetricButtonAction();
		initLoadMetricButtonAction();
		initNewMetricButton();
	}

	private void initStoreMetricButtonAction() {
		storeMetricButton.setOnAction(e -> MetricUtil.serializesMetric(currentMetric));
	}

	private void initLoadMetricButtonAction() {
		loadMetricButton.setOnAction(e -> {
			MetricUtil.deSerializesMetric(RCPMessageProvider.getFilePathDialog("Select Metric File", ""));
		});
	}

	private void initNewMetricButton() {

		newMetricButton.setOnAction(e -> {

			if (currentMetric != null) {
				if (RCPMessageProvider.questionMessage("Current Metric",
						"Would you like to store your current metric?")) {
					MetricUtil.serializesMetric(currentMetric);
				}
			}
			String metricName = RCPMessageProvider.inputDialog("Metric Name Dialog", "Please enter a metric name:");
			currentMetric = new MetricImpl(metricName);

		});
	}
	
 	private void initMetricTable() {
 		
 		comparatorColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
 		weightColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("weight"));
 		treeTable.setRowFactory(new Callback<TreeTableView<FXComparatorElement>, TreeTableRow<FXComparatorElement>>() {

			@Override
			public TreeTableRow<FXComparatorElement> call(TreeTableView<FXComparatorElement> param) {
				final TreeTableRow<FXComparatorElement> row = new TreeTableRow<FXComparatorElement>();
				final ContextMenu contextMenu = new ContextMenu();
				final MenuItem removeMenuItem = new MenuItem("Remove");  
                removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {  
                    @Override  
                    public void handle(ActionEvent event) {
                    	removeComparator(event);
//                    	FXComparatorElement treeItem = row.getItem();
//                    	System.out.println("TreeItem: " + treeItem);
//                    	
//                    	row.getParent().getChildrenUnmodifiable().remove(treeItem);
                    	
                
						  
//                        treeTable.getRoot().getChildren().remove(row.getItem());                    
                    }  
                });  
                contextMenu.getItems().add(removeMenuItem);
                row.contextMenuProperty().bind(  
                        Bindings.when(row.emptyProperty())  
                        .then((ContextMenu)null)  
                        .otherwise(contextMenu)  
                ); 
                
				return row;
			}
 			
 		});
 		
 		treeTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
 	}
	
	private void initIgnoreTable() {
		comparatorTypes = FXCollections.<Map<String, String>>observableArrayList();
//		ignoreList.addAll(comparatorTypes);
//		metricTable.setItems();
		nameColumn.setCellValueFactory(new MapValueFactory<>("type"));
		metricTable.getItems().addAll(comparatorTypes);
		
		metricTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);	
		metricTable.getSelectionModel().selectedItemProperty().addListener((obvservable, oldValue, newValue) -> {
			if (newValue == oldValue || newValue == null) {
				metricTable.getSelectionModel().clearSelection();
				System.out.println("Nothing selected");
//				treeTable.setRoot(origRoot);
				System.out.println(treeTable.getRoot().getChildren() + "");
				
				//
//				treeTable.getSelectionModel().clearSelection();
//				System.out.println("Nothing selected");
//				treeTable.setRoot(origRoot);
//				System.out.println(origRoot.getChildren() + "");
				//
				
//			} else if (newValue.toString() == "{comparatorName=testString}") {
//				metricTree.setRoot(originalRoot);
//				System.out.println(originalRoot.getChildren() + " else if");
				
			} else {
				System.out.println("originalRoot: " + treeTable.getRoot().getChildren());
				applyIgnoreTypes(newValue);
				System.out.println("ListView Selection Changed (selected: " + newValue.toString() + ")");
			}
		});
		
	}
	
	private void applyIgnoreTypes(Object selection) {
		
//		TreeItem<Comparator> newRoot = new TreeItem("Comparators");
//		newRoot.getChildren().addAll(originalRoot.getChildren());
//		newRoot.getChildren().removeIf(elem -> (elem.toString().contains(selection.toString().substring(selection.toString().lastIndexOf("=") + 1, selection.toString().lastIndexOf("}")))));
		
		//
		TreeItem<FXComparatorElement> newTableRoot = new TreeItem<>(new FXComparatorElement("Comparators"));
		newTableRoot.getChildren().addAll(treeTable.getRoot().getChildren());
//		for (TreeItem<FXMetricViewElement> elem: newTableRoot.getChildren()) {
//			if (elem.getValue().getComparatorType().contains(selection.toString().substring(selection.toString().lastIndexOf("=") + 1, selection.toString().lastIndexOf("}")))) {
//				newTableRoot.getChildren().remove(elem);
//			}
//		}
		newTableRoot.getChildren().removeIf(elem -> (elem.toString().contains(selection.toString().substring(selection.toString().lastIndexOf("=") + 1, selection.toString().lastIndexOf("}")))));
		//

		
//		for(Object elem: newRoot.getChildren()) {
//			System.out.println(selection + ", selection");
//			System.out.println("debugAusgabe2 " + selection.toString().substring(selection.toString().lastIndexOf("=") + 1, selection.toString().lastIndexOf("}")) + ", " + elem);
//		}
		
		
//		System.out.println(root.getChildren() + ", " + selection + ", " + newRoot.getChildren());
//		System.out.println("newRoot: " + newRoot.getChildren() + ", originalRoot: " + originalRoot.getChildren());
//		metricTree.setRoot(newRoot);
		
		treeTable.setRoot(newTableRoot); //
//		
//		newRoot.setExpanded(true);
//		metricTree.setShowRoot(true);
//		
		newTableRoot.setExpanded(true); //
		treeTable.setShowRoot(true); //
		
		
//		return originalRoot;
	}
	
	
	
	
	@Optional 
	@Inject
	private void metricTreeComparatorList(@UIEventTopic("comparatorListEvent") List<FXComparatorElement> comparators) {
		if (treeTable.getRoot() == null) {
			treeTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			initData(comparators);			
		} else {
			comparators.forEach(elem -> {
				System.err.println("elemType: " + elem.getComparatorType());
				boolean knownType = false;
				for(Map<String, String> e : comparatorTypes) {
					if (e.containsValue(elem.getComparatorType())) {
						knownType = true;
					}
				}
				if(!knownType) {
					Map<String, String> item = new HashMap<>();
					item.put("type", elem.getComparatorType());
					comparatorTypes.add(item);
					TreeItem<FXComparatorElement> typeNode = new TreeItem<FXComparatorElement>(new FXComparatorElement(elem.getComparatorType()));
					treeTable.getRoot().getChildren().add(typeNode);
					typeNode.setExpanded(true);
				}
			});
			
			comparators.forEach(elem -> {
				treeTable.getRoot().getChildren().forEach(child -> {
					boolean newElem = true;
					if (child.getValue().getName().equals(elem.getComparatorType())) {
						for(TreeItem<FXComparatorElement> typeChild: child.getChildren()) {
							if((typeChild.getValue().equals(elem))) {
								newElem = false;
							}
						}
						if (newElem) {
							child.getChildren().add(new TreeItem<FXComparatorElement>(elem));
						}
					}
				}); 

			});
			treeTable.setShowRoot(false);
			treeTable.getRoot().setExpanded(true);
		}
	
	}
			
	
	private void initData(List<FXComparatorElement> comparators) {
		TreeItem<FXComparatorElement> origRoot = new TreeItem<FXComparatorElement>(new FXComparatorElement("Root"));
		
		comparators.forEach(elem -> {
			Map<String, String> item = new HashMap<>();
			item.put("type", elem.getComparatorType());
			comparatorTypes.add(item);
		});
		
		comparatorTypes.forEach(elem -> {
			TreeItem<FXComparatorElement> subRootNode = new TreeItem<>(new FXComparatorElement(elem.get("type")));
			origRoot.getChildren().add(subRootNode);
			subRootNode.setExpanded(true);
		});
		
		comparators.forEach(elem -> {
			origRoot.getChildren().forEach(child -> {				
				if (child.getValue().getComparatorType().equals(elem.getComparatorType())) {
					child.getChildren().add(new TreeItem<>(elem));
				}
			}); 

		});
		
		metricTable.getItems().clear();
		metricTable.getItems().addAll(comparatorTypes);
		
		
		treeTable.setRoot(origRoot);
		treeTable.setShowRoot(false);
		origRoot.setExpanded(true);
	}
	
	
	private void initIgnoreTypes() {
		
	}
}

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

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.compare.metric.util.MetricUtil;
import de.tu_bs.cs.isf.e4cf.compare.metric_view.components.FXComparatorElement;
import de.tu_bs.cs.isf.e4cf.compare.metric_view.components.MetricViewCell;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;

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
	private TableView<Map<String, String>> metricTable; 
	@FXML
	private TableColumn<Map, String> nameColumn;				
	@FXML
	private TableColumn<Map, Boolean> ignoreColumn;
	
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
			if (elem == null || elem.equals(treeTable.getRoot())) {
				continue;
			}
			TreeItem<FXComparatorElement> parent = elem.getParent();
			int typeIndex = -1;
			for (int i = 0; i < comparatorTypes.size(); i++) {
				if (comparatorTypes.get(i).get("type").equals(elem.getValue().getComparatorType())) {
					typeIndex = i;
				}
			}
			
			//subrootnode case
			if (treeTable.getRoot().getChildren().contains(elem)) {
				comparatorTypes.remove(typeIndex);
				
				// construct comparator list to call remove comparators for the current metric
				List<Comparator> children = new ArrayList<>();
				for (TreeItem<FXComparatorElement> child : elem.getChildren()) {
					children.add(child.getValue().getComparator());
				}
				Comparator[] comparators = new Comparator[children.size()];
				
				// remove the comparator element from the metric table
				removeComparatorElement(elem, parent);
			}
			
			//no subrootnode case
			if (!parent.equals(treeTable.getRoot())) {
				//only element in subrootnode
				if (parent.getChildren().size() == 1) {
					removeComparatorElement(elem, parent);
					treeTable.getRoot().getChildren().remove(parent);
					comparatorTypes.remove(typeIndex);
				} else {
					removeComparatorElement(elem, parent);
					treeTable.getSelectionModel().clearSelection();
				}
			}			
		}
		
		event.consume();
	}

	private void removeComparatorElement(TreeItem<FXComparatorElement> elem, TreeItem<FXComparatorElement> parent) {
		elem.getValue().setWeight(0f);
		parent.getChildren().remove(elem);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//initButtons();
		initMetricTable();
		initIgnoreTable();

	}

//	private void initButtons() {
//		storeMetric();
//		loadMetric();
//		initMetric();
//	}
	
	@FXML
	private void storeMetric(Event event) {
		event.consume();
		if (currentMetric == null) {
			initMetric(new ActionEvent());
		}
		updateMetric(currentMetric);
		MetricUtil.serializesMetric(currentMetric);
	}

	private void updateMetric(MetricImpl metric) {
		metric.getAllComparator().clear();
		treeTable.getRoot().getChildren().forEach(typeNode -> {
			typeNode.getChildren().forEach(comparatorNode -> {
				metric.addComparator(comparatorNode.getValue().getComparatorType(), comparatorNode.getValue().getComparator());
			});
		});

		Map<String, Boolean> ignoredTypes = new HashMap<>();
		comparatorTypes.forEach(type -> {
			ignoredTypes.put(type.get("type"), Boolean.parseBoolean(type.get("ignored")));
		});
		metric.setNodeIgnorList(ignoredTypes);
	}
	
	@FXML
	private void loadMetric(Event event) {
		currentMetric = MetricUtil.deSerializesMetric(RCPMessageProvider.getFilePathDialog("Select Metric File", ""));
		Map<String, List<Comparator>> comparators = currentMetric.getAllComparator();
		treeTable.setRoot(null);
		List<FXComparatorElement> elements = new ArrayList<>();
		for (String type : comparators.keySet()) {
			for (Comparator c : comparators.get(type)) {
				elements.add(new FXComparatorElement(c));
			}
		}
		initData(elements);

		Map<String, Boolean> ignoredTypes = currentMetric.getNodeIgnoreList();
		for (Map<String, String> entry : comparatorTypes) {
			if (ignoredTypes.get(entry.get("type"))) {
				entry.put("ignored", "true");
			}
		}
		event.consume();
	}
	
	@FXML
	private void initMetric(Event event) {
		if (currentMetric != null) {
			if (RCPMessageProvider.questionMessage("Current Metric",
					"Would you like to store your current metric?")) {
				MetricUtil.serializesMetric(currentMetric);
			}
		}
		String metricName = RCPMessageProvider.inputDialog("Metric Name Dialog", "Please enter a metric name:");
		currentMetric = new MetricImpl(metricName);
		treeTable.setRoot(null);
		comparatorTypes.clear();
		event.consume();
	}
	
 	private void initMetricTable() {
 		comparatorColumn.setCellFactory(new Callback<TreeTableColumn<FXComparatorElement, String>, TreeTableCell<FXComparatorElement, String>>() {
			@Override
			public TreeTableCell<FXComparatorElement, String> call(TreeTableColumn<FXComparatorElement, String> e) {
				return new MetricViewCell(comparatorTypes);
			}
		});
 		comparatorColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
 		weightColumn.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new StringToFloatConverter()));
 		weightColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("weight"));
 		weightColumn.setOnEditCommit(event -> {
 			event.getTreeTablePosition().getTreeItem().getValue().setWeight(event.getNewValue());
 		});
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
                    }  
                });  
                contextMenu.getItems().add(removeMenuItem);
                row.contextMenuProperty().bind(  
                        Bindings.when(row.emptyProperty())  
                        .then((ContextMenu)null)  
                        .otherwise(contextMenu)  
                ); 
                row.setDisclosureNode(null);
				return row;
			}
 			
 		});
 		
 		treeTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
 	}
 	
 	public class StringToFloatConverter extends StringConverter<Float> {

		@Override
		public String toString(Float object) {
			return String.valueOf(object);			
		}

		@Override
		public Float fromString(String string) {
			treeTable.getRoot().getChildren().forEach(child -> {
				child.getChildren().forEach(child2 -> {
					System.out.println(child2.getValue().getWeight());
				});
			});
			try {
				return Float.parseFloat(string);
				
			} catch(NumberFormatException e) {
				System.err.println("Float Format Error");
			}
			return null;
		}
		
	}
	
	private void initIgnoreTable() {
		comparatorTypes = FXCollections.<Map<String, String>>observableArrayList();
		nameColumn.setCellValueFactory(new MapValueFactory<String>("type"));
		ignoreColumn.setCellValueFactory(cell -> {
			final Map<String, String> element = cell.getValue();
			final BooleanProperty prop = new SimpleBooleanProperty(Boolean.parseBoolean(element.get("ignored")));
			prop.addListener((p, old, value) -> {
				element.put("ignored", value.toString());
			});
			return prop;
		});
		ignoreColumn.setCellFactory(CheckBoxTableCell.forTableColumn(ignoreColumn));
		metricTable.getItems().addAll(comparatorTypes);
		
		metricTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);	
		metricTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == oldValue || newValue == null) {
				metricTable.getSelectionModel().clearSelection();
//				System.out.println("Nothing selected");
//				System.out.println(treeTable.getRoot().getChildren() + "");
				
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
		TreeItem<FXComparatorElement> newTableRoot = new TreeItem<>(new FXComparatorElement("Root"));
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
		treeTable.setShowRoot(false); //
		
		
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
					if (e.get("type").equals(elem.getComparatorType())) {
						knownType = true;
					}
				}
				if(!knownType) {
					Map<String, String> item = new HashMap<>();
					item.put("type", elem.getComparatorType());
					item.put("ignored", new String("false"));
					comparatorTypes.add(item);

					TreeItem<FXComparatorElement> typeNode = new TreeItem<FXComparatorElement>(new FXComparatorElement(elem.getComparatorType()));
					treeTable.getRoot().getChildren().add(typeNode);
					typeNode.setExpanded(true);
				}
			});
			
			// checks if a comparator is not yet present in the table and inserts it
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
			treeTable.setShowRoot(true);
			treeTable.getRoot().setExpanded(true);
		}
	
	}
			
	
	private void initData(List<FXComparatorElement> comparators) {
		comparatorTypes.clear();
		TreeItem<FXComparatorElement> origRoot = new TreeItem<FXComparatorElement>(new FXComparatorElement("Root"));
		
		Set<String> availableTypes = new HashSet<>();
		comparators.forEach(elem -> {
			availableTypes.add(elem.getComparatorType());
		});
		
		availableTypes.forEach(type -> {
			Map<String, String> item = new HashMap<>();
			item.put("type", type);
			item.put("ignored", new String("false"));
			comparatorTypes.add(item);
		});
		
		comparatorTypes.forEach(elem -> {
			TreeItem<FXComparatorElement> subRootNode = new TreeItem<>(new FXComparatorElement(elem.get("type")));
			origRoot.getChildren().add(subRootNode);
			subRootNode.setExpanded(true);
		});
		
		// inserts the comparator element into the fitting subtree
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
		origRoot.setExpanded(true);
	}
	
	
	private void initIgnoreTypes() {
		
	}
}

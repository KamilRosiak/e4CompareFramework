package de.tu_bs.cs.isf.e4cf.compare.metric_view.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.compare.metric.util.MetricUtil;
import de.tu_bs.cs.isf.e4cf.compare.metric_view.components.FXMetricViewElement;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

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
	
	//MetricTree
	@FXML
	private TreeView<Comparator> metricTree;	//changed from Metric to Comparator
//	@FXML
//	private TreeView<Metric> weightTree;
	
	//MetricTreeTable
	@FXML
	private TreeTableView<FXMetricViewElement> treeTable;
	@FXML
	private TreeTableColumn<FXMetricViewElement, String> comparatorColumn;
	@FXML
	private TreeTableColumn<FXMetricViewElement, Float> weightColumn;
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
	
	
	@FXML
	private void addToIgnoreList(ActionEvent event) {
		event.consume();
		
	}
	
	@FXML
	private void removeComparator(ActionEvent event) {
		event.consume();
//		System.out.println("Sysout selection" + metricTree.getSelectionModel().getSelectedItems());
		
		ObservableList<Comparator> comparators = FXCollections.observableArrayList();
		for (TreeItem<Comparator> elem : metricTree.getSelectionModel().getSelectedItems()) {
			elem.getParent().getChildren().remove(elem);
		}
		
		//
		for (TreeItem<FXMetricViewElement> elem : treeTable.getSelectionModel().getSelectedItems()) {
			elem.getParent().getChildren().remove(elem);
		}
		//
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initButtons();
		initIgnoreTable();
		initMetricTable();

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
	
	
	
	
	
	// wie ist das besser zu lösen?
	private TreeItem<Comparator> originalRoot;
	
	private TreeItem<FXMetricViewElement> origRoot;
	
// 	private void initMetricTree() {
////		private TreeItem<Comparator> originalRoot = new TreeItem<>("Comparators");
//// 		metricTree.getSelectionModel().selectedItemProperty().addListener((obvservable, oldValue, newValue) -> {
////			if (newValue == oldValue || newValue == null) {
////				return null;
////			}
//	}
// 	
 	private void initMetricTable() {
 		treeTable.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
 		comparatorColumn.setCellValueFactory(new TreeItemPropertyValueFactory("comparator"));
 		weightColumn.setCellValueFactory(new TreeItemPropertyValueFactory("weight"));
 		
// 		weightColumn.setCellValueFactory(new Callback<CellDataFeatures<FXMetricViewElement, Float>, ObservableValue<Float>>() {
//			@Override
//			public ObservableValue<Float> call(CellDataFeatures<FXMetricViewElement, Float> weight) {
//				ObservableValue<Float> obsFloat = new ReadOnlyObjectWrapper<Float>(weight.getValue().getValue().getWeight());
//				return obsFloat;
//			}
//			
// 			
// 		}); 
 		
 		treeTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
// 		treeTable.setVisible(true);
 	}
	
	private void initIgnoreTable() {
		metricTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		//MapValueFactory for the ignore types
		nameColumn.setCellValueFactory(new MapValueFactory<>("comparatorName"));
//		metricTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);	
		metricTable.getSelectionModel().selectedItemProperty().addListener((obvservable, oldValue, newValue) -> {
			if (newValue == oldValue || newValue == null) {
				metricTable.getSelectionModel().clearSelection();
				System.out.println("Nothing selected");
				metricTree.setRoot(originalRoot);
				System.out.println(originalRoot.getChildren() + "");
				
				//
				treeTable.getSelectionModel().clearSelection();
				System.out.println("Nothing selected");
				treeTable.setRoot(origRoot);
				System.out.println(origRoot.getChildren() + "");
				//
				
//			} else if (newValue.toString() == "{comparatorName=testString}") {
//				metricTree.setRoot(originalRoot);
//				System.out.println(originalRoot.getChildren() + " else if");
				
			} else {
				System.out.println("originalRoot: " + originalRoot.getChildren());
				applyIgnoreTypes(newValue);
				System.out.println("ListView Selection Changed (selected: " + newValue.toString() + ")");
			}
		});
		
	}
	
	private void applyIgnoreTypes(Object selection) {
		
		TreeItem<Comparator> newRoot = new TreeItem("Comparators");
		newRoot.getChildren().addAll(originalRoot.getChildren());
		newRoot.getChildren().removeIf(elem -> (elem.toString().contains(selection.toString().substring(selection.toString().lastIndexOf("=") + 1, selection.toString().lastIndexOf("}")))));
		
		//
		TreeItem<FXMetricViewElement> newTableRoot = new TreeItem("Comparators");
		newTableRoot.getChildren().addAll(origRoot.getChildren());
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
		System.out.println("newRoot: " + newRoot.getChildren() + ", originalRoot: " + originalRoot.getChildren());
		metricTree.setRoot(newRoot);
		
		treeTable.setRoot(newTableRoot); //
		
		newRoot.setExpanded(true);
		metricTree.setShowRoot(true);
		
		newTableRoot.setExpanded(true); //
		treeTable.setShowRoot(true); //
		
		
//		return originalRoot;
	}
	
	
	
	
	@Optional 
	@Inject
	private void metricTreeComparatorList(@UIEventTopic("comparatorListEvent") List<Comparator> comparators) {
		if (originalRoot == null) {
			
			treeTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); //table
			
			metricTree.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			System.out.println("getComparatorlists, null case " + comparators);
			originalRoot = initDataFromComparatorView(comparators);
			
			origRoot = initDataFromComparatorViewTable(comparators); //table
			
			//table
//			for (TreeItem<Comparator> elem: originalRoot.getChildren()) {
//				FXMetricViewElement elem2 = new FXMetricViewElement(elem.getValue());
//				origRoot.getChildren().add(elem2);
//			} 
			
		} else {
			
			
			
			
			System.out.println("getComparatorlists, else case " + comparators);
			metricTree.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			metricTree.getSelectionModel().selectAll();
			ObservableList<TreeItem<Comparator>> items = metricTree.getSelectionModel().getSelectedItems();
			List<TreeItem<Comparator>> tempList = new ArrayList<>(items);

			List<Comparator> rootList = new ArrayList<Comparator>();
			for (TreeItem<Comparator> child: originalRoot.getChildren()) {
				rootList.add(child.getValue());
			}
			
			for (TreeItem<Comparator> elem: items) {
//				System.out.println("Class: " + elem.getValue());
//				tempList2.add(elem.getValue().toString());
//				tempList.add(elem.getValue());
			}
			
//			List<Comparator> tempList2 = new ArrayList<>();
//			tempList.forEach(elem -> tempList2.add(elem.getValue()));
			metricTree.getSelectionModel().clearSelection();
//			System.out.println("items: " + tempList2);
			
			for (Comparator elem: comparators) {
				if (!rootList.contains(elem)) {
					
//					tempList.add(elem);
					System.out.println("rootList: " + rootList);
					
					originalRoot.getChildren().add(new TreeItem<Comparator>(elem));
					origRoot.getChildren().add(new TreeItem<FXMetricViewElement>(new FXMetricViewElement(elem)));
					System.out.println(tempList);
				}
			}
		}
		
		metricTree.setRoot(originalRoot);
		
		treeTable.setRoot(origRoot);
		
		originalRoot.setExpanded(true);
		metricTree.setShowRoot(true);
		
		treeTable.setShowRoot(true);
	}
	
	

	
	
	// in comparatortypen gliedern (wie bei comparatorview)?
	private TreeItem<Comparator> initDataFromComparatorView(List<Comparator> comparators) {
		TreeItem<FXMetricViewElement> tableRoot = new TreeItem("Comparators"); //table
		TreeItem<Comparator> root = new TreeItem("Comparators");
		List<String> ignoreTypes = new ArrayList<>();
		ObservableList<Map<String, Object>> ignoreList = FXCollections.<Map<String, Object>>observableArrayList();
		
		for(Comparator elem: comparators) {
			if(!ignoreTypes.contains(elem.getClass().getSimpleName())) {
				ignoreTypes.add(elem.getClass().getSimpleName());
				
			}
			root.getChildren().add(new TreeItem<>(elem));
			tableRoot.getChildren().add(new TreeItem<>(new FXMetricViewElement(elem))); //table
		}
		
		for(String elem: ignoreTypes) {
			if (!ignoreList.contains(elem)) {
				Map<String, Object> item = new HashMap<>();
				System.out.println("elem: "+ elem);
				item.put("comparatorName", elem);
//				item.put("test", "TestValue");
				ignoreList.add(item);
			}
		}
		
		Map<String, Object> item2 = new HashMap<>();
		item2.put("comparatorName", "testString");
		ignoreList.add(item2);
		
		
		metricTable.getItems().clear();
		metricTable.getItems().addAll(ignoreList);
		
		
		return root;
	}
	
	
	
	
	private TreeItem<FXMetricViewElement> initDataFromComparatorViewTable(List<Comparator> comparators) {
		TreeItem<FXMetricViewElement> tableRoot = new TreeItem("Comparators"); //table
		TreeItem<Comparator> root = new TreeItem("Comparators");
		List<String> ignoreTypes = new ArrayList<>();
		ObservableList<Map<String, Object>> ignoreList = FXCollections.<Map<String, Object>>observableArrayList();
		
		for(Comparator elem: comparators) {
			if(!ignoreTypes.contains(elem.getClass().getSimpleName())) {
				ignoreTypes.add(elem.getClass().getSimpleName());
				
			}
			root.getChildren().add(new TreeItem<>(elem));
			tableRoot.getChildren().add(new TreeItem<>(new FXMetricViewElement(elem))); //table
		}
		
		for(String elem: ignoreTypes) {
			if (!ignoreList.contains(elem)) {
				Map<String, Object> item = new HashMap<>();
				System.out.println("elem: "+ elem);
				item.put("comparatorName", elem);
//				item.put("test", "TestValue");
				ignoreList.add(item);
			}
		}
		
		Map<String, Object> item2 = new HashMap<>();
		item2.put("comparatorName", "testString");
		ignoreList.add(item2);
		
		
		metricTable.getItems().clear();
		metricTable.getItems().addAll(ignoreList);
		
		
		return tableRoot;
	}
	
	private void initIgnoreTypes() {
		
	}
	
	
//	@Optional
//	@Inject
//	private void initMetricTree(@UIEventTopic("sendComparatorEvent") TreeView tree) {
//		metricTree = tree;
//	}
//	@Optional
//	@Inject
//	private void initMetricTree(@UIEventTopic("sendComparatorEvent") TreeView<Object> tree) {
//		metricTree = tree;
//	}
//	
//	@Optional
//	@Inject
//	private void initMetricTable(@UIEventTopic("sendComparatorTypesEvent") Set types) {
//		System.out.println("initMetricTable Method");
//		System.out.println("types: " + types);
//		//services.partService.showPart(MetricST.BUNDLE_NAME);
//		List typeList = new ArrayList<String>(types);
//		System.out.println(typeList + " List");
//		
//		ObservableList<String> type = FXCollections.observableArrayList(typeList);
//		System.out.println("after observablelist: "+ type);
//		TableColumn ignoreTypes = new TableColumn("Ignoretypes");
//		System.out.println("after ignoretypes");
//		ignoreTypes.setCellValueFactory(new PropertyValueFactory<>("Name"));
//		
//		System.out.println("after cellfactory");
//		metricTable.setItems(type);
//		System.out.println("after setitems");
//		metricTable.getColumns().addAll(ignoreTypes);
//		System.out.println("after getcolumns");
////		metricTable.setItems(type);
////		type.forEach(elem -> {
////			metricTable.setItems(elem);
////		});
//		
//	}
	
	/*
	 * direkt liste von Comparatoren senden -> baum neu aufbauen
	 */
//	@Optional
//	@Inject
//	private void metricTreeModel(@UIEventTopic("comparatorEvent") Map<String, List<FilterableTreeItem<Object>>> model) {
//		services.partService.showPart(MetricST.BUNDLE_NAME);
//		
//		
//		
//		System.out.println(model.get("StringComparatorChild").get(0).getValue().getClass() + " " + model.get("StringComparatorChild").get(0).getValue());
////		ObservableList<Comparator> ignoreList = FXCollections.observableArrayList(model.values());
//		
////		System.out.println("metricTreeModel Method");
////		System.out.println(model.keySet() + "KeySet");
////		TableColumn ignoreTypes = new TableColumn("Ignoretypes");
////		System.out.println("after ignoretypes");
////		ignoreTypes.setCellValueFactory(new PropertyValueFactory<>("Name"));
//		
//		
//		ObservableList<Map<String, Object>> ignoreList = FXCollections.<Map<String, Object>>observableArrayList();
//		
//		for(String elem: model.keySet()) {
//			if (!elem.equals("root") && !elem.contains("Child")) {
//				System.out.println(elem + " elem");
//				Map<String, Object> item = new HashMap<>();
//				item.put("comparatorName", elem);
//				ignoreList.add(item);
//
//				Map<String, Object> item2 = new HashMap<>();
//				item.put("comparatorName", "testString");
//				ignoreList.add(item2);
//				
//			}
//		}
//		System.out.println(ignoreList +" ignoreList");
//		metricTable.setItems(ignoreList);
//		FilterableTreeItem<Object> root = model.get("root").get(0);
//		metricTree.setRoot(root);
//		root.setExpanded(true);
//		metricTree.setShowRoot(false);
//	}
}

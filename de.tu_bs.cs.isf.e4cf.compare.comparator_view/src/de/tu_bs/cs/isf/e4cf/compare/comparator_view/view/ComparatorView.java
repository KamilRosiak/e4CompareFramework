package de.tu_bs.cs.isf.e4cf.compare.comparator_view.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.fx.ui.controls.tree.FilterableTreeItem;
import org.eclipse.fx.ui.controls.tree.TreeItemPredicate;
import org.eclipse.fx.ui.controls.tree.TreeTableViewUtil;

import de.tu_bs.cs.isf.e4cf.compare.comparator.impl.node.StringComparator;
import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;
import de.tu_bs.cs.isf.e4cf.compare.comparator.util.ComparisonUtil;
import de.tu_bs.cs.isf.e4cf.compare.comparator_view.ComparatorViewController;
import de.tu_bs.cs.isf.e4cf.compare.comparator_view.components.ComparatorCell;
import de.tu_bs.cs.isf.e4cf.compare.metric_view.components.FXComparatorElement;
import de.tu_bs.cs.isf.e4cf.compare.metric_view.stringtable.MetricST;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class ComparatorView implements Initializable {

	private ComparatorViewController cvc;
	
	@Inject
	private ServiceContainer serviceContainer;

	@FXML
	TreeView<Comparator> treeView;
	@FXML
	private TreeTableView<FXComparatorElement> treeTable;
	@FXML
	private TreeTableColumn<FXComparatorElement, String> comparatorColumn;
	@FXML
	private TreeTableColumn<FXComparatorElement, Float> weightColumn;
	
	@FXML
	private TextField filterField;

	/**
	 * this method loads on click the ComparatorTree
	 */
	@FXML
	private void loadComparators(ActionEvent event) {
		event.consume();
		sendComparators(null); // default value, sends every element in the list to metricview
	}

	@FXML
	private void addComparator(Event event) {
		event.consume();
		System.out.println("Sysout selection" + treeTable.getSelectionModel().getSelectedItem().getValue());
		ObservableList<FXComparatorElement> comparators = FXCollections.observableArrayList();
		for (TreeItem<FXComparatorElement> elem : treeTable.getSelectionModel().getSelectedItems()) {
			if (!treeTable.getRoot().getChildren().contains(elem)) {
				comparators.add(elem.getValue());
			}
		}
		sendComparators(comparators);
	}


	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initButtons();
		initTable();
	}

	public void initButtons() {

	}

	/**
	 * method to send selected comparators to the metricView
	 * 
	 * @param comparators list of comparators if null sends each comparator
	 */
	private void sendComparators(ObservableList<FXComparatorElement> comparators) {
		serviceContainer.partService.showPart(MetricST.BUNDLE_NAME);
		if (comparators == null) {
			ObservableList<FXComparatorElement> temp = FXCollections.observableArrayList();
			treeTable.getSelectionModel().selectAll();
			treeTable.getSelectionModel().clearSelection(0);
			for (TreeItem<FXComparatorElement> elem : treeTable.getSelectionModel().getSelectedItems()) {
				temp.add(elem.getValue());
			}
			treeTable.getSelectionModel().clearSelection();
			
			serviceContainer.eventBroker.send("comparatorListEvent", temp);
		} else {
			System.out.println("comparators: " + comparators);
			serviceContainer.eventBroker.send("comparatorListEvent", comparators);
		}

	}

	/**
	 * Method to get a list of all elements that should be added to the treeview
	 * possible that it will later not be needed
	 * 
	 * @return list of NodeComparators
	 */
	private List<FXComparatorElement> getFxComparatorElements() {
		ObservableList<Comparator> comparatorList = FXCollections
				.observableArrayList(ComparisonUtil.getComparator());
		StringComparator st1 = new StringComparator();
		StringComparator st2 = new StringComparator();
		StringComparator st3 = new StringComparator();
		comparatorList.addAll(st1, st2, st3);
		
		List<FXComparatorElement> fxComparatorElementsList = new ArrayList<>();
		comparatorList.stream().forEach(elem -> fxComparatorElementsList.add(new FXComparatorElement(elem)));
		return fxComparatorElementsList;
	}	
	
	private void initTable() {
		comparatorColumn.setCellFactory(new Callback<TreeTableColumn<FXComparatorElement, String>, TreeTableCell<FXComparatorElement, String>>() {
			@Override
			public TreeTableCell<FXComparatorElement, String> call(TreeTableColumn<FXComparatorElement, String> e) {
				return new ComparatorCell(serviceContainer);
			}
		});
		comparatorColumn.setCellValueFactory(new TreeItemPropertyValueFactory("name"));
		
		weightColumn.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new StringToFloatConverter()));
 		weightColumn.setCellValueFactory(new TreeItemPropertyValueFactory("weight"));
 		weightColumn.setOnEditCommit(event -> {
 			event.getTreeTablePosition().getTreeItem().getValue().setWeight(event.getNewValue());
 		});
 		
		treeTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		FilterableTreeItem<FXComparatorElement> root = new FilterableTreeItem<>(new FXComparatorElement("root"));
		
		Set<String> availableTypes = new HashSet<>();
		getFxComparatorElements().forEach(elem -> {
			availableTypes.add(elem.getComparatorType());
		});
		
		availableTypes.forEach(elem -> {
			FilterableTreeItem<FXComparatorElement> subRootNode = new FilterableTreeItem(new FXComparatorElement(elem));
			root.getInternalChildren().add(subRootNode);
			subRootNode.setExpanded(true);
		});
		
		
		getFxComparatorElements().forEach(elem -> {
			root.getInternalChildren().forEach(child -> {				
				if (child.getValue().getComparatorType().equals(elem.getComparatorType())) {
					((FilterableTreeItem) child).getInternalChildren().add(new FilterableTreeItem<>(elem));
				}
			}); 

		});
		
		root.predicateProperty().bind(Bindings.createObjectBinding(() -> {
			if (filterField.getText() == null || filterField.getText().isEmpty()) {
				return null;
			}
			return TreeItemPredicate.create(elem -> (elem.getName().contains(filterField.getText().toUpperCase())
					|| elem.getName().contains(filterField.getText().toLowerCase())
					|| elem.getName().contains(filterField.getText())));
		}, filterField.textProperty()));
		
		treeTable.setRoot(root);
		root.setExpanded(true);
		treeTable.setShowRoot(false);
		
	}
	
	private FXComparatorElement createElement(Comparator comparator) {
		return  new FXComparatorElement(comparator);
		
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

}



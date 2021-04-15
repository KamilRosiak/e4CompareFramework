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

import de.tu_bs.cs.isf.e4cf.compare.comparator.impl.node.StringComparator;
import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;
import de.tu_bs.cs.isf.e4cf.compare.comparator.util.ComparisonUtil;
import de.tu_bs.cs.isf.e4cf.compare.comparator_view.ComparatorViewController;
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
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

public class ComparatorView implements Initializable {

	private ComparatorViewController cvc;
	
	@Inject
	private ServiceContainer serviceContainer;

	@FXML
	TreeView<Comparator> treeView;

	@FXML
	TextField filterField;

	/**
	 * this method loads on click the ComparatorTree
	 */
	@FXML
	private void loadComparators(ActionEvent event) {
		event.consume();
//		System.out.println("Sortiert von kurz nach lang");
//		getTree().getRoot().getChildren().sort(Comparator.comparing(t->t.toString().length()));;
//		getAvailableComparatorTypes();
//		getTreeModel();
		// sendToMetricView();
//		sendTreeToMetricView();
//		sendMap();
		sendComparators(null); // default value, sends every element in the list to metricview
	}

	@FXML
	private void addComparator(Event event) {
		event.consume();
		System.out.println("Sysout selection" + treeView.getSelectionModel().getSelectedItem().getValue());
		ObservableList<Comparator> comparators = FXCollections.observableArrayList();
		for (TreeItem<Comparator> elem : treeView.getSelectionModel().getSelectedItems()) {
			comparators.add(elem.getValue());
		}
		sendComparators(comparators);
	}


	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initButtons();
		initTree();
	}

	public void initButtons() {

	}

	/**
	 * method to send selected comparators to the metricView
	 * 
	 * @param comparators list of comparators if null sends each comparator
	 */
	private void sendComparators(ObservableList<Comparator> comparators) {
		serviceContainer.partService.showPart(MetricST.BUNDLE_NAME);
		if (comparators == null) {
			ObservableList<Comparator> temp = FXCollections.observableArrayList();
			treeView.getSelectionModel().selectAll();
			treeView.getSelectionModel().clearSelection(0);
			for (TreeItem<Comparator> elem : treeView.getSelectionModel().getSelectedItems()) {
				temp.add(elem.getValue());
			}
			treeView.getSelectionModel().clearSelection();
			
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
	private List<Comparator> getNodeComparatorList() {
		ObservableList<Comparator> comparatorList = FXCollections
				.observableArrayList(ComparisonUtil.getComparator());
		StringComparator st1 = new StringComparator();
		StringComparator st2 = new StringComparator();
		StringComparator st3 = new StringComparator();
		comparatorList.addAll(st1, st2, st3);
		return comparatorList;
	}

	/**
	 * this method generates the tree
	 * 
	 * @return treeModel with Children as FilteralbleTreeItem
	 */
	private Map<String, List<FilterableTreeItem<Comparator>>> getTreeModel() {
		// init rootNode
		FilterableTreeItem<Comparator> root = new FilterableTreeItem<Comparator>(null);
		// init Map which will be used to store different comparators
		Map<String, List<FilterableTreeItem<Comparator>>> nodeMap = new HashMap<>();
		// init set with different comparatortypes
		Set<String> availableTypes = new HashSet<>();
		getNodeComparatorList().forEach(elem -> {
			availableTypes.add(splitByLastDot(elem));
		});

		// add subrootnodes for each different comparator type
		availableTypes.forEach(elem -> {
			// as list elements, then add internal children to that?
			List<FilterableTreeItem<Comparator>> subRootNodeList = new ArrayList<FilterableTreeItem<Comparator>>();
			
			FilterableTreeItem<Comparator> subRootNode = new FilterableTreeItem("StringComparator");
			subRootNodeList.add(subRootNode);
			root.getInternalChildren().add(subRootNode);
			subRootNode.setExpanded(true);
			nodeMap.put(elem, subRootNodeList);
		});

		// add comparators to the fitting subrootnode as children
		getNodeComparatorList().forEach(elem -> {
			if (nodeMap.containsKey(splitByLastDot(elem))) {
				// get(0) da zwangsläufig erstes element
				nodeMap.get(splitByLastDot(elem)).get(0).getInternalChildren().add(new FilterableTreeItem<>(elem));
				// init List for element of a specific comparator type
				List<FilterableTreeItem<Comparator>> subRootNodeChildList = new ArrayList<FilterableTreeItem<Comparator>>();
				subRootNodeChildList.add(new FilterableTreeItem<>(elem));
				nodeMap.put(splitByLastDot(elem) + "Child", subRootNodeChildList);

			} else {
				System.out.println("Else case");
				root.getInternalChildren().add(new FilterableTreeItem<>(elem));
			}

		});
		List<FilterableTreeItem<Comparator>> rootList = new ArrayList<FilterableTreeItem<Comparator>>();
		rootList.add(root);
		nodeMap.put("root", rootList);
		return nodeMap;

	}

	/**
	 * this method initialzes the TreeView and allows filtering filtering done by
	 * using predicate selectionMode is set to multiple
	 * 
	 */
	private void initTree() {		
		treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		FilterableTreeItem<Comparator> root = getTreeModel().get("root").get(0);
		root.predicateProperty().bind(Bindings.createObjectBinding(() -> {
			if (filterField.getText() == null || filterField.getText().isEmpty()) {
				return null;
			}
			return TreeItemPredicate.create(elem -> (elem.toString().contains(filterField.getText().toUpperCase())
					|| elem.toString().contains(filterField.getText().toLowerCase())));
		}, filterField.textProperty()));
		
		treeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			   @Override 	
			   public void handle(MouseEvent e) {
			      if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
			         System.out.println(treeView.getSelectionModel().getSelectedItem());  
			         System.out.println("listener test");
			      }
			   }
			});
		
		treeView.setRoot(root);
		root.setExpanded(true);
		treeView.setShowRoot(false);
	}
	
	

//	private Set<String> getAvailableComparatorTypes() {
//		Set comparatorTypes = new HashSet<String>();
//		getNodeComparatorList().forEach(elem -> {
//			comparatorTypes.add(splitByLastDot(elem));
//		});
////		System.out.println("Comparator Types: " + comparatorTypes);
//		return comparatorTypes;
//	}

	private String splitByLastDot(Object elem) {	
		return elem.getClass().toString().substring(elem.getClass().toString().lastIndexOf(".") + 1);
	}
	
	

}



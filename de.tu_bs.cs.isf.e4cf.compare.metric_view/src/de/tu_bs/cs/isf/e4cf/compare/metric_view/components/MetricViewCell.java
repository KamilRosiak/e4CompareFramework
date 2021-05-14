package de.tu_bs.cs.isf.e4cf.compare.metric_view.components;

import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableRow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MetricViewCell extends TreeTableCell<FXComparatorElement, String> {
	
    ObservableList<Map<String, String>> ignoredTypes = FXCollections.<Map<String, String>>observableArrayList();
//    Node disclosureNode;
//    boolean node = false;
	@Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setText(null);
        } else if (isIgnored(getType(item))) {
        	getTreeTableRow().setDisable(true);  
//        	if (!node) {
//        		node = true;
//        		disclosureNode = getTreeTableRow().getDisclosureNode();
//        	}
//        	getTreeTableRow().setDisclosureNode(null);
        	
        	//getTreeTableRow().setEventDispatcher(new CellEventDispatcher());
            setText(item);    
        } else {
        	getTreeTableRow().setDisable(false);
//        	getTreeTableRow().setDisclosureNode(disclosureNode);
            setText(item);
        }
    }

    class CellEventDispatcher implements EventDispatcher {

        private final EventDispatcher original = getTreeTableRow().getEventDispatcher();

        @Override
        public Event dispatchEvent(Event event, EventDispatchChain tail) {
            if(event instanceof KeyEvent && event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
                if((((KeyEvent)event).getCode().equals(KeyCode.LEFT) || 
                    ((KeyEvent)event).getCode().equals(KeyCode.RIGHT))){
                    event.consume();
                }
            }
            return original.dispatchEvent(event, tail);
        }
    }
	
	public MetricViewCell(ObservableList<Map<String, String>> comparatorTypes) {
		super();
        ignoredTypes = comparatorTypes;
	}
	
	private boolean isIgnored(String type) {
		for (Map<String, String> typeMap : ignoredTypes) {
			if (Boolean.parseBoolean(typeMap.get("ignored")) && typeMap.get("type").equals(type)) {
				return true;
			}
		}
		return false;
		
	}

    private String getType(String item) {
        for (TreeItem<FXComparatorElement> type : getTreeTableView().getRoot().getChildren()) {
            String currentType = type.getValue().getName();
            if (currentType.equals(item)) {        
                return currentType;
            }
        }
        return "";
    }
}

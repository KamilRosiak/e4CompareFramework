package de.tu_bs.cs.isf.e4cf.compare.comparator_view.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class ComparatorView implements Initializable {

    
    @FXML BorderPane root;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
	
	
    }
    
    
    public void setCss(String location) {
	root.getStylesheets().add(location);
    }
    
}

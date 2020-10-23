package de.tu_bs.cs.isf.e4cf.compare.metric_view.view;

import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.compare.metric.interfaces.Metric;
import de.tu_bs.cs.isf.e4cf.compare.metric.util.MetricUtil;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import javax.inject.Inject;

/**
 * This class represents the controller class of the ui/view/MetricView.fxml and implements all functionality.
 * @author Kamil Rosiak
 *
 */
public class MetricView implements Initializable {
    @FXML private TreeView<Metric> metricTree;
    @FXML private Button storeMetricButton;
    @FXML private Button loadMetricButton;
    @FXML private Button newMetricButton;
    @FXML private Button ignoreTypeButton;
    
    @Inject private ServiceContainer services;
    
    private MetricImpl currentMetric;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
	initButtons();
	
    }
    
    private void initButtons() {
	initStoreMetricButtonAction();
	initLoadMetricButtonAction();
	initNewMetricButton();
    }
    
    private void initStoreMetricButtonAction() {
	storeMetricButton.setOnAction(e-> MetricUtil.serializesMetric(currentMetric));
    }
    
    private void initLoadMetricButtonAction() {
	loadMetricButton.setOnAction(e -> {
	    MetricUtil.deSerializesMetric(RCPMessageProvider.getFilePathDialog("Select Metric File", ""));
	});
    }
    
    private void initNewMetricButton() {
	newMetricButton.setOnAction(e-> {
	    
	    if(currentMetric != null) {
		if(RCPMessageProvider.questionMessage("Current Metric", "Would you like to store your current metric?")) {
		    MetricUtil.serializesMetric(currentMetric);  
		}    
	    }
	    String metricName = RCPMessageProvider.inputDialog("Metric Name Dialog", "Pleas enter a metric name:");
	    currentMetric = new MetricImpl(metricName);
 
	});
    }
    
    
    
    
    
    
    
    private void initMetricTree() {
	
	
	
    }
    
    
    
    
    
}

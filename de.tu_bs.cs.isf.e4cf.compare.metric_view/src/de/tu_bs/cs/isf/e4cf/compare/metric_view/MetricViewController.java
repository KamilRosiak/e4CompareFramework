package de.tu_bs.cs.isf.e4cf.compare.metric_view;

import de.tu_bs.cs.isf.e4cf.compare.metric_view.stringtable.MetricST;
import de.tu_bs.cs.isf.e4cf.compare.metric_view.view.MetricView;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.templates.AbstractJavaFXController;

public class MetricViewController extends AbstractJavaFXController<MetricView> {
    private static final String METRIC_VIEW_FXML = "/ui/view/MetricView.fxml";
    public static final String METRIC_CSS_LOCATION = "css/metric_view.css";
    
    public MetricViewController() {
	super(MetricST.BUNDLE_NAME, METRIC_VIEW_FXML,METRIC_CSS_LOCATION);
    }
}

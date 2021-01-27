package de.tu_bs.cs.isf.e4cf.compare.comparator_view;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.compare.comparator_view.stringtable.ComparatorST;
import de.tu_bs.cs.isf.e4cf.compare.comparator_view.view.ComparatorView;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLUtil;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

public class ComparatorViewController {
    private static final String COMPARATOR_VIEW_FXML = "/ui/view/ComparatorView.fxml";
    public static final String COMPARATOR_VIEW_CSS_LOCATION = "css/comparator_view.css";

    private ComparatorView view;

    @PostConstruct
    public void postConstruct(Composite parent, ServiceContainer services, IEclipseContext context) throws IOException {
	view = FXMLUtil.<ComparatorView>loadFXML(parent, context, ComparatorST.BUNDLE_NAME, COMPARATOR_VIEW_FXML);
	view.setCss(COMPARATOR_VIEW_CSS_LOCATION);
    }
}

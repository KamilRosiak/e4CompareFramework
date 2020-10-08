 
package de.tu_bs.cs.isf.e4cf.compare.compare_engine_view;

import de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.string_table.CompareST;
import de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.view.CompareEngineView;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.templates.AbstractJavaFXController;

/**
 * This class initializes the fxml ui located at the location that is stored in COMPARE_ENGINE_FXML 
 * @author Kamil Rosiak
 *
 */
public class CompareEngineController extends AbstractJavaFXController<CompareEngineView> {
    private static final String COMPARE_ENGINE_FXML = "/ui/view/parts/CompareEngineView.fxml";
    public static final String COMPARE_ENGINE_CSS_LOCATION ="css/compare_engine.css";
    
    
    
    public CompareEngineController() {
	super(CompareST.BUNDLE_NAME, COMPARE_ENGINE_FXML,COMPARE_ENGINE_CSS_LOCATION);
    }


    

}
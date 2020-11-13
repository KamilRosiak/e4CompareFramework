package de.tu_bs.cs.isf.e4cf.text_editor;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.text_editor.view.TextEditor;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;

public class TextEditorViewController {
	private static final String TEXT_EDITOR_VIEW_FXML = "/ui/view/TextEditorView.fxml";
    public static final String TEXT_EDITOR_VIEW_CSS_LOCATION ="css/comparator_view.css";
    
    
    @PostConstruct
    public void postConstruct(Composite parent, ServiceContainer services, IEclipseContext context) throws IOException {
        FXCanvas canvans = new FXCanvas(parent, SWT.None);
        FXMLLoader<TextEditor> loader = new FXMLLoader<TextEditor>(context, "de.tu_bs.cs.isf.e4cf.text_editor",TEXT_EDITOR_VIEW_FXML);
        
        Scene scene = new Scene(loader.getNode());
        //scene.getStylesheets().add(TEXT_EDITOR_CSS_LOCATION);
        canvans.setScene(scene);	
        System.out.print("This is a push test");
    }

}

package de.tu_bs.cs.isf.e4cf.parts.project_explorer;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.view.ProjectExplorerView;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;

public class ProjectExplorerViewController {
    private static final String PROJECT_EXPLORER_VIEW_FXML = "/ui/view/ProjectExplorerView.fxml";
    
    
    @PostConstruct
    public void postConstruct(Composite parent, ServiceContainer services, IEclipseContext context) throws IOException {
        FXCanvas canvans = new FXCanvas(parent, SWT.None);
        FXMLLoader<ProjectExplorerView> loader = new FXMLLoader<ProjectExplorerView>(context, StringTable.BUNDLE_NAME, PROJECT_EXPLORER_VIEW_FXML);
        
        Scene scene = new Scene(loader.getNode());
        canvans.setScene(scene);	
    }
}

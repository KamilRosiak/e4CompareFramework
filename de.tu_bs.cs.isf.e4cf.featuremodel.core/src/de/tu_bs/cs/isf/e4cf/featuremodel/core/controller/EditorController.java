package de.tu_bs.cs.isf.e4cf.featuremodel.core.controller;

import java.nio.file.Path;
import java.util.function.Consumer;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.FeatureDiagram;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.FeatureDiagramSerialiazer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.FMEditorView;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.feature.FMEditorPaneController;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.toolbar.FMEditorToolbar;
import javafx.scene.Node;

public class EditorController {
	private final ServiceContainer services;
	private final FMEditorView view;
	private FeatureDiagram diagram;
	
	public EditorController(ServiceContainer services, Consumer<Node> uiConsumer) {
		this.services = services;
		FMEditorPaneController editorPane = new FMEditorPaneController(services);
		this.view = new FMEditorView(editorPane, uiConsumer);
	}
	
	public EditorController(ServiceContainer services, Consumer<Node> uiConsumer, FeatureDiagram diagram) {
		this(services, uiConsumer);
		this.diagram = diagram;
	}
	
	public void askToSave() {
		boolean shouldSave = RCPMessageProvider.questionMessage("Feature-Diagram Editor",
				"Would you like to save the current Feature Model?");
		if (shouldSave) {
			saveDiagram();
		}
	}
	
	@Optional
	@Inject
	public void loadFeatureDiagram(@UIEventTopic(FDEventTable.LOAD_FEATURE_DIAGRAM) FeatureDiagram diagram) {
		this.view.displayFeatureDiagram(diagram);
		System.out.println("Feature Diagram " + diagram.getRoot().getName() + " successfully loaded.");
	}
	
	public void saveDiagram() {
		FileTreeElement root = services.workspaceFileSystem.getWorkspaceDirectory();
		Path rootPath = FileHandlingUtility.getPath(root);
		Path projectPath = rootPath.resolve("");
		Path uriPath = projectPath.resolve(E4CStringTable.FEATURE_MODEL_DIRECTORY);
		String fileName = this.diagram.getRoot().getName() + FDStringTable.FD_FILE_ENDING;
		String absolutePath = uriPath.toUri() + "/" + fileName;

		FeatureDiagramSerialiazer.save(this.diagram, absolutePath);
	}

}

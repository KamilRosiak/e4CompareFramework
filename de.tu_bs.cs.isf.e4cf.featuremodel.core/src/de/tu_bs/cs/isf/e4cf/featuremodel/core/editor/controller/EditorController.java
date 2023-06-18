package de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.FMEditorView;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature.FMEditorPaneController;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.Feature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.FeatureDiagram;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.Variability;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import javafx.scene.Node;

public class EditorController {
	private final ServiceContainer services;
	private final FMEditorView view;
	private FeatureDiagram diagram;

	public EditorController(ServiceContainer services, Consumer<Node> uiConsumer) {
		this.services = services;
		FMEditorPaneController editorPane = new FMEditorPaneController(services);
		this.view = new FMEditorView(editorPane, uiConsumer);

		this.diagram = new FeatureDiagram(FDStringTable.FD_DEFAULT_FM_NAME, new Feature("Root"));
		this.diagram.getRoot().setVariability(Variability.MANDATORY);
		this.view.displayFeatureDiagram(this.diagram);
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

	public FeatureDiagram getFeatureDiagram() {
		return this.diagram;
	}

	@Optional
	@Inject
	public void setFeatureDiagram(FeatureDiagram diagram) {
		this.view.displayFeatureDiagram(diagram);
		this.diagram = diagram;
		System.out.println("Feature Diagram " + diagram.getName() + " successfully loaded.");
	}

	public void saveDiagram() {
		FileTreeElement root = services.workspaceFileSystem.getWorkspaceDirectory();
		Path rootPath = FileHandlingUtility.getPath(root);
		Path projectPath = rootPath.resolve("");
		Path uriPath = projectPath.resolve(E4CStringTable.FEATURE_MODEL_DIRECTORY);
		String absolutePath = uriPath.toString() + "/" + diagram.getName() + ".fd";

		try {
			this.diagram.writeToFile(new File(absolutePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

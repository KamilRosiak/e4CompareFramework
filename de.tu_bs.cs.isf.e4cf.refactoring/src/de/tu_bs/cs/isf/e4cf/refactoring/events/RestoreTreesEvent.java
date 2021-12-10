package de.tu_bs.cs.isf.e4cf.refactoring.events;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.osgi.service.event.Event;

import com.google.common.io.Files;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.gson.GsonExportService;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.Directory;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ClusterEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;

@Singleton
@Creatable
public class RestoreTreesEvent extends EventHandlerBase {

	private GsonExportService exportService;

	private ServiceContainer services;

	@Inject
	public RestoreTreesEvent(GsonExportService exportService, ServiceContainer services, ClusterEngine clusterEngine) {
		super(clusterEngine);
		this.exportService = exportService;
		this.services = services;
	}

	@Override
	public void handleEvent(Event event) {

		try {
			CloneModel cloneModel = (CloneModel) event.getProperty(DSEditorST.CLONE_MODEL);
			List<Tree> trees = cloneModel.restoreIntegratedTrees();

			Directory directory = services.workspaceFileSystem.getWorkspaceDirectory();
			boolean createNewDirectory = true;

			File baseDirectory = null;
			for (FileTreeElement subElement : directory.getChildren()) {

				if (subElement.getFileName().equals("Restored trees")) {
					createNewDirectory = false;
					baseDirectory = new File(subElement.getAbsolutePath());
					break;
				}
			}

			if (createNewDirectory) {
				baseDirectory = new File(directory.getAbsolutePath() + "/Restored trees");
				baseDirectory.mkdir();

			}

			int count = 0;
			for (File subElement : baseDirectory.listFiles()) {
				if (subElement.getName().equals("Result_" + count)) {
					count++;
				}

			}

			File resultDirectory = new File(baseDirectory.getAbsolutePath() + "/" + "Result_" + count);
			resultDirectory.mkdir();

			for (Tree tree : trees) {

				String json = exportService.exportTree((TreeImpl) tree);

				File concreteElement = new File(resultDirectory.getAbsolutePath() + "/" + tree.getTreeName() + ".tree");
				concreteElement.createNewFile();

				Files.write(json.getBytes(), new File(concreteElement.getAbsolutePath()));

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

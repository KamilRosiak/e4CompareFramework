package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.handler;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter.TreeWritter;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.Directory;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

public class StoreAsModelHandler {

	@Execute
	public void execute(ServiceContainer services, ReaderManager reader, IEclipseContext context) {

		services.rcpSelectionService.getCurrentSelectionsFromExplorer().forEach(e -> {
			Tree model = reader.readFile(e);

			TreeWritter treeWritter = new TreeWritter();
			ContextInjectionFactory.inject(treeWritter, context);
			String fileName = "";
			for (Directory directory : services.workspaceFileSystem.getProjectDirectories()) {
				if (directory.getFileName().contains("02 Tree")) {
					fileName = directory.getAbsolutePath() + "\\" + e.getFileName();
				}
			}

			treeWritter.writeArtifact(model, fileName);
		});

	}
}

package de.tu_bs.cs.isf.e4cf.compare.data_structures.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.TaxonomyArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.string_table.DataStructureST;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.File;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.JavaFXBuilder;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

public class FileToTreeHandler {

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager) {
		List<Tree> artifactList = new ArrayList<Tree>();
		List<FileTreeElement> parsedFiles = new ArrayList<FileTreeElement>();
		TaxonomyArtifactReader taxonomyArtifactReader = new TaxonomyArtifactReader();

		//transform every file element into an tree.
		services.rcpSelectionService.getCurrentSelectionsFromExplorer().stream().forEach(e -> artifactList.add(readerManager.readFile(e)));
		
		//transform every file element into a file tree Element
		services.rcpSelectionService.getCurrentSelectionsFromExplorer().stream().forEach(e -> parsedFiles.add(new File(e.getAbsolutePath())));
		//services.rcpSelectionService.getCurrentSelectionsFromExplorer().stream().forEach(e -> parsedFiles.add(new File(e.getAbsolutePath())));

		services.partService.showPart(DataStructureST.COMPARE_ENGINE_VIEW);
		services.eventBroker.send(DataStructureST.LOAD_ARTIFACTS_EVENET, artifactList);		// sends message of paths to parsed artifacts to listening subscribers
		services.eventBroker.send(DataStructureST.LOAD_ARTIFACTS_PATH_EVENET, parsedFiles); // sends message of paths to parsed artifacts to listening subscribers 
	}
}

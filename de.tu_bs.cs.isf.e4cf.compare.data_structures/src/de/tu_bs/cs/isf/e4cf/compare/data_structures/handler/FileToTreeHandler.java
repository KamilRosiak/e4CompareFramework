package de.tu_bs.cs.isf.e4cf.compare.data_structures.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.string_table.CompareST;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.string_table.DataStructureST;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.taxonomy_control_view.string_table.TaxonomyST;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

public class FileToTreeHandler {

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager) {
		List<Tree> artifactList = new ArrayList<Tree>();
		List<FileTreeElement> parsedFiles = new ArrayList<FileTreeElement>();

		//transform every file element into an tree.
		services.rcpSelectionService.getCurrentSelectionsFromExplorer().stream().forEach(e -> artifactList.add(readerManager.readFile(e)));
		
		//transform every file element into a file tree Element
		services.rcpSelectionService.getCurrentSelectionsFromExplorer().stream().forEach(e -> parsedFiles.add(e));
		
		services.partService.showPart(CompareST.BUNDLE_NAME); // Open View for Artifact comparison 
		services.partService.showPart(TaxonomyST.BUNDLE_NAME); // Open Taxonomy information window
		services.eventBroker.send(DataStructureST.LOAD_ARTIFACTS_EVENET, artifactList);		// sends message of paths to parsed artifacts list to listening subscribers
		services.eventBroker.send(CompareST.LOAD_ARTIFACTS_PATH_EVENT, parsedFiles); // sends message of paths to parsed artifacts to listening subscribers 
	}
}

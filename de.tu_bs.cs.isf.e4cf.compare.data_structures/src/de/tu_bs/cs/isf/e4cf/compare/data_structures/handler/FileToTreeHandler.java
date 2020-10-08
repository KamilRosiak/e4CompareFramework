package de.tu_bs.cs.isf.e4cf.compare.data_structures.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.ArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.string_table.DataStructureST;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.util.ArtifactIOUtil;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

public class FileToTreeHandler {
	
	@Execute
	public void execute(ServiceContainer services) {
		List<Tree> artifactList = new ArrayList<Tree> ();
		List<AbstractArtifactReader> reader = ArtifactIOUtil.getAllArtifactReader();
		
		for(FileTreeElement file : services.rcpSelectionService.getCurrentSelectionsFromExplorer()) {
			for(ArtifactReader read : reader) {
				if(read.isFileSupported(file)) {
					artifactList.add(read.readArtifact(file));
				}
			}
		}
		services.partService.showPart(DataStructureST.COMPARE_ENGINE_VIEW);
		services.eventBroker.send(DataStructureST.LOAD_ARTIFACTS_EVENET, artifactList);
	}
}

package de.tu_bs.cs.isf.e4cf.compare.data_structures.handler;

import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

public class FileToTreeHandler {
	
	@Execute
	public void execute(ServiceContainer services) {
		services.rcpSelectionService.getCurrentSelectionsFromExplorer();
	}
}

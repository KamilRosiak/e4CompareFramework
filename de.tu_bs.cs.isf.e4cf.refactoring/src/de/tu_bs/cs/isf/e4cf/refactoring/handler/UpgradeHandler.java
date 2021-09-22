package de.tu_bs.cs.isf.e4cf.refactoring.handler;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;

public class UpgradeHandler {

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager, IntegrationPipeline integrationPipeline,
			UpgradePipeline upgradePipeline) {

		Tree tree = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionFromExplorer());
		CloneModel cloneModel = integrationPipeline.pipe(tree, null);

		if (cloneModel != null) {
			for (int i = 1; i < services.rcpSelectionService.getCurrentSelectionsFromExplorer().size(); i++) {
				Tree tree2 = readerManager
						.readFile(services.rcpSelectionService.getCurrentSelectionsFromExplorer().get(i));
				upgradePipeline.pipe(cloneModel, tree2);
			}
			
			services.eventBroker.send(DSEditorST.INITIALIZE_TREE_EVENT, cloneModel.restoreIntegratedTrees().get(0));
		}

	}

	@CanExecute
	public boolean canExecute(ServiceContainer services, RCPSelectionService selectionService) {
		return services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() >= 1;
	}

}

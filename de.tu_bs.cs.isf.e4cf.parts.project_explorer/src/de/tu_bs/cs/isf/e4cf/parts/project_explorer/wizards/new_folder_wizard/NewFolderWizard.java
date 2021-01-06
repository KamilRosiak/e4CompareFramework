package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.new_folder_wizard;

import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.base_wizard.Wizard;
import javafx.stage.Stage;


/**
 * Wizard that presents a page for creating new directories,
 * no logic has to be used on the finish Button currently.
 *
 */
public class NewFolderWizard extends Wizard {
	Stage owner;

	public NewFolderWizard(Stage owner, RCPSelectionService rcpSelectionService) {
		super(new NewFolderPage(rcpSelectionService));
		this.owner = owner;
	}

	@Override
	public void finish() {
		owner.close();
	}

	@Override
	public void cancel() {
		owner.close();
	}

}
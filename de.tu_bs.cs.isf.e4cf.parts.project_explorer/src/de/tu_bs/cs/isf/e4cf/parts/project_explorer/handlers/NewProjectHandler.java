package de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.NewProjectWizard;

/**
 * This command opens a wizard that allows a user to creates a new project in
 * the current workspace.
 * 
 * @author {Kamil Rosiak}
 */
public class NewProjectHandler {
	@Inject
	@Named(IServiceConstants.ACTIVE_SHELL)
	private Shell _shell;
	@Inject
	IEventBroker _eventBroker;
	@Inject
	RCPImageService _imageService;

	@Execute
	public void execute() {
		NewProjectWizard wizard = new NewProjectWizard(_eventBroker, _imageService);
		WizardDialog dialog = new WizardDialog(_shell, wizard);
		Window.setDefaultImage(_imageService.getImage(null, "icons/Explorer_View/items/project24.png"));
		dialog.open();
	}

}
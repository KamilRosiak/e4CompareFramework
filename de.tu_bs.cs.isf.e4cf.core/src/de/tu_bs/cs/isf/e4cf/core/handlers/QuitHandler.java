package de.tu_bs.cs.isf.e4cf.core.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;


public class QuitHandler {
	@Execute
	public void execute(IWorkbench workbench, @Named(IServiceConstants.ACTIVE_SHELL)Shell shell){
		if (MessageDialog.openConfirm(shell, "Confirmation",
				"Do you want to exit?")) {
			workbench.close();
		}
	}
}

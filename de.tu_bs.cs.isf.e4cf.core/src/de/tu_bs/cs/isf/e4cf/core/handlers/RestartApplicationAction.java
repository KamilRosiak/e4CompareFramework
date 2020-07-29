package de.tu_bs.cs.isf.e4cf.core.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.e4cf.core.application_model.FrameworkE4Application;

public class RestartApplicationAction {
	
	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell, IWorkbench workbench) {
		FrameworkE4Application.getInstance().setRestartCode();
		workbench.close();	
		
	}
}

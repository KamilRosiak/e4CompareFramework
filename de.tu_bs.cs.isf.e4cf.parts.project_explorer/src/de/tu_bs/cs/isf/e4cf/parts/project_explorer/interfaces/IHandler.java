package de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;

public interface IHandler {

	void execute(IEclipseContext context, ServiceContainer services, Shell shell);

	boolean canExecute(RCPSelectionService selectionService);
}

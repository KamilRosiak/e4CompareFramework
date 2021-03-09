package de.tu_bs.cs.isf.e4cf.core.util;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.e4cf.core.file_structure.WorkspaceFileSystem;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPDialogService;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPPartService;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.core.util.tagging.TagService;

@Creatable
@Singleton
public class ServiceContainer {
	public RCPImageService imageService;
	public RCPSelectionService rcpSelectionService;
	public RCPPartService partService;
	public RCPDialogService dialogService;
	public TagService tagService;
	public IEventBroker eventBroker;
	public WorkspaceFileSystem workspaceFileSystem;
	public Shell shell;
	public IEclipseContext context;

	@Inject
	public ServiceContainer(RCPImageService is, RCPSelectionService ss, RCPPartService ps, RCPDialogService ds,
			IEventBroker eb, WorkspaceFileSystem wfs, Shell sh, IEclipseContext context) {
		this.imageService = is;
		this.rcpSelectionService = ss;
		this.partService = ps;
		this.dialogService = ds;
		this.eventBroker = eb;
		this.workspaceFileSystem = wfs;
		this.shell = sh;
		this.context = context;
	}

}

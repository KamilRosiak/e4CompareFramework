package de.tu_bs.cs.isf.e4cf.parts.project_explorer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.WorkspaceFileSystem;
import de.tu_bs.cs.isf.e4cf.core.file_structure.tree.sync.TreeSynchronization;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileEventLog;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CEventTable;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.extension_points.ExtensionAttrUtil;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.WorkspaceStructureTemplate;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.listeners.OpenFileListener;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.listeners.ProjectExplorerKeyListener;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;

/**
 * The project explorer shows a specifically defined content model. Its possible to replace it by your own content.
 * The content is shown in a jface treeviewer.
 * @author {Kamil Rosiak}
 */
public class ProjectExplorerView {
	public static final String PROJECT_EXPLORER_PART_ID = "de.tu_bs.cs.isf.e4cf.parts.project_explorer";
	@Inject private EMenuService _menuService;
	@Inject private ESelectionService _selectionService;
	@Inject private IEventBroker _eventBroker;
	@Inject RCPImageService _imageService;
	@Inject IEclipseContext _eclipseContext;

	private TreeViewer _treeViewer;
	private WorkspaceFileSystem _workspaceFileSystem;
	private TreeSynchronization _treeSync;
	private OpenFileListener _openFileListener;
	private Map<String,IProjectExplorerExtension> fileExtensions;
	
	protected class ProjectExplorerComparator extends ViewerComparator {
		
		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			if (e1 instanceof FileTreeElement && e2 instanceof FileTreeElement) {
				FileTreeElement element1 = (FileTreeElement) e1;
				FileTreeElement element2 = (FileTreeElement) e2;
				if (element1.isDirectory() == element2.isDirectory()) {
					return getFilename(element1).compareTo(getFilename(element2));				
				} else {
					return element1.isDirectory() ? -1 : 1;
				}				
			} else {
				return 0;
			}
		}
		
		private String getFilename(FileTreeElement element) {
			return Paths.get(element.getAbsolutePath()).getFileName().toString();
		}
	}
	
	@PostConstruct
	public void postConstruct(Composite parent, IEclipseContext eclipseContext, WorkspaceFileSystem fileSystem, ServiceContainer container) {
		//init items from extension points
		getContributions();
		//TODO: MAKE CLEAN
		_treeViewer = new TreeViewer(parent);
		_treeViewer.getTree().setBackgroundMode(SWT.INHERIT_FORCE);
		_treeViewer.setComparator(new ProjectExplorerComparator());
		_treeViewer.setContentProvider(new ProjectExplorerContentProvider());
		_treeViewer.setInput(initializeInput(fileSystem));
		_treeViewer.setLabelProvider(new ProjectExplorerLabelProvider(_imageService, _workspaceFileSystem,fileExtensions));
		_treeViewer.expandToLevel(1);
		//adding KeyListener
		_treeViewer.getTree().addKeyListener(new ProjectExplorerKeyListener(eclipseContext));

		addOpenFileContext(eclipseContext);
		setupSelectionService();		
		new ProjectExplorerDropTarget(_treeViewer.getControl(),fileSystem);
		 //register the context menu within the menuService
		_menuService.registerContextMenu(_treeViewer.getTree(), StringTable.PROJECT_EXPLORER_CONTEXT_MENU_ID);
		//checkWorkspace("");
		createWorkspaceFromExtension();
	}

	/**
	 * Add double-click listener to the project explorer
	 * 
	 * @param eclipseContext
	 */
	private void addOpenFileContext(IEclipseContext eclipseContext) {
		_openFileListener = new OpenFileListener(fileExtensions);
		ContextInjectionFactory.inject(_openFileListener, eclipseContext);
		_treeViewer.addDoubleClickListener(_openFileListener);
	}
	
	/**
	 * Gets the file extension and the attribute from the Project explorer extension point.
	 */
	private void getContributions() {
		IConfigurationElement[] configs = RCPContentProvider.getIConfigurationElements(StringTable.PROJECT_EXPLORER_FILE_EXTENSION_POINT);	
		fileExtensions = new HashMap<String,IProjectExplorerExtension>();
		for(IConfigurationElement config : configs) {
			try {
				IProjectExplorerExtension attr = (IProjectExplorerExtension) config.createExecutableExtension(StringTable.FILE_EXT_ATTR);
				String fileExtension = config.getAttribute(StringTable.FILE_EXT);
				fileExtensions.put(fileExtension, attr);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setupSelectionService() {
		//set a initial selection on the root object
		StructuredSelection structuredSelection = new StructuredSelection(_workspaceFileSystem.getWorkspaceDirectory());
		_selectionService.setSelection(structuredSelection);
		
		//add a SelectionListener to Treeviewer to propagate the selection that is done in the treeviewer
		_treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				_selectionService.setSelection(event.getSelection());
				_eventBroker.send(E4CEventTable.SELECTION_CHANGED_EVENT, structuredSelection);
			}
		});
	}
	
	/**
	 * Initialize the <b>Workspace</b> and builds the <b>file tree</b> recursively
	 * 
	 * @return 
	 */
	private Object initializeInput(WorkspaceFileSystem fileSystem) {
		_workspaceFileSystem = fileSystem;
		_workspaceFileSystem.initializeFileTree(RCPContentProvider.getCurrentWorkspacePath());
		_treeSync = new TreeSynchronization(_workspaceFileSystem);
		
		return _workspaceFileSystem.getWorkspaceDirectory();
	}
	
	@PreDestroy
	public void cleanUp() {
		try {
			FileEventLog.getInstance().writeEventLogTo(Paths.get(RCPContentProvider.getCurrentWorkspacePath()+"\\FileEventLog.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		_treeSync.stopSynchronization();
		_treeSync.dispose();
	}
	
	/**
	 * Change the selection to the tree root if there is currently no tree item selected.
	 * Otherwise, there still is a selection of tree items.
	 */
	@Focus
	public void getFocus() {
		Object selection = _selectionService.getSelection(PROJECT_EXPLORER_PART_ID);
		if (selection instanceof TreeSelection) {
			TreeSelection treeSelection = (TreeSelection) selection;
			if (treeSelection.getPaths().length == 0) {
				_selectionService.setSelection(_treeViewer);							
			}
		}
	}

	/**
	 * Rebuild the project explorer and update it's view.
	 * 
	 */
	@Inject
	@Optional
	public void refresh(@UIEventTopic(E4CEventTable.EVENT_REFRESH_PROJECT_VIEWER) Object o) {
		_treeViewer.refresh();
	}
	
	/**
	 * This method creates the workspace based on the PROJECT_EXPLORER_WORKSPACE_STRUCUTRE extension. If not available, no directories are added initially.
	 */
	private void createWorkspaceFromExtension() {
		List<WorkspaceStructureTemplate> workspaceExtension = ExtensionAttrUtil.getAttrsFromExtension(StringTable.PROJECT_EXPLORER_WORKSPACE_STRUCUTRE, StringTable.WORKSPACE_STR_ATTR);
		FileTreeElement root = _workspaceFileSystem.getWorkspaceDirectory();
		Path rootPath = FileHandlingUtility.getPath(root);
		Path projectPath = rootPath.resolve("");
		
		for(WorkspaceStructureTemplate extension : workspaceExtension) {
			for(String directory : extension.getDirectories()) {
				try {
					if(!Files.exists(projectPath.resolve(directory))) {
						FileEventLog.getInstance().insertEvent("Create Directory", projectPath.resolve(directory));
						Files.createDirectory(projectPath.resolve(directory));
					}
				} catch (IOException e) {
					e.printStackTrace();
					try {
						FileHandlingUtility.delete(projectPath);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		refresh(null);
	}
}
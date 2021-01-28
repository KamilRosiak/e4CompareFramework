package de.tu_bs.cs.isf.e4cf.text_editor.extensions;

import org.eclipse.swt.graphics.Image;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;
import de.tu_bs.cs.isf.e4cf.text_editor.stringtable.EditorST;

/**
 * Base class for specific file extensions to extend. Implements the execute
 * method of the IProjectExplorerExtension as it does not differ between file
 * types.
 * 
 * @author Lukas Cronauer
 *
 */
public interface ITextEditorProjectExplorerExtension extends IProjectExplorerExtension {

	public Image getIcon(RCPImageService imageService);

	@Override
	public default void execute(ServiceContainer container) {
		container.eventBroker.send(EditorST.FILE_OPENED,
				container.rcpSelectionService.getCurrentSelectionFromExplorer());
	}

}

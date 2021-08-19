package de.tu_bs.cs.isf.e4cf.text_editor.extensions;

import org.eclipse.swt.graphics.Image;

import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.text_editor.stringtable.EditorST;

/**
 * Provides an Icon for Java-Files.
 * 
 * @author Lukas Cronauer
 */
public class JavaFileExtension implements ITextEditorProjectExplorerExtension {
	@Override
	public Image getIcon(RCPImageService imageService) {
		return imageService.getImage(EditorST.BUNDLE_NAME, EditorST.JAVA_ICON);
	}

}

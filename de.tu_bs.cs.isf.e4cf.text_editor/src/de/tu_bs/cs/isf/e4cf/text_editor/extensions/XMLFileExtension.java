package de.tu_bs.cs.isf.e4cf.text_editor.extensions;

import org.eclipse.swt.graphics.Image;

import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.text_editor.stringtable.EditorST;

/**
 * Provides an Icon for xml-Files.
 * 
 * @author Lukas Cronauer
 */
public class XMLFileExtension implements ITextEditorProjectExplorerExtension {
	@Override
	public Image getIcon(RCPImageService imageService) {
		return imageService.getImage(EditorST.BUNDLE_NAME, EditorST.XML_ICON);
	}

}
package de.tu_bs.cs.isf.e4cf.text_editor.extensions;

import org.eclipse.swt.graphics.Image;

import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.text_editor.stringtable.EditorST;

/**
 * Implements the getIcon method for Xml files
 * 
 * @author Lukas Cronauer
 */
public class XMLFileExtension extends TextEditorProjectExplorerExtension {
	@Override
	public Image getIcon(RCPImageService imageService) {
		return imageService.getImage(EditorST.BUNDLE_NAME, "icons/xml-icon.png");
	}

}
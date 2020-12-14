package de.tu_bs.cs.isf.e4cf.text_editor.extensions;

import org.eclipse.swt.graphics.Image;

import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.text_editor.stringtable.EditorST;

public class TxtFileExtension extends TextEditorProjectExplorerFileExtension {
	@Override
	public Image getIcon(RCPImageService imageService) {
		return imageService.getImage(EditorST.BUNDLE_NAME, "icons/txt-icon.png");
	}

}
package de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations;

import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.swt.widgets.Display;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.File;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;

public class CreateFile implements CreateOperation {

	String _filename;
	
	public CreateFile(String filename) {
		_filename = filename;
	}

	@Override
	public FileTreeElement execute(FileTreeElement parentElement) {
		Path newFilePath = FileHandlingUtility.getPath(parentElement).resolve(_filename);
		if (Files.exists(newFilePath)) {
			return new File(newFilePath.toString(), parentElement);
		} else {
			Display.getDefault().syncExec(() -> {
				RCPMessageProvider.errorMessage("Create File", "The specified file does not exist.");		
			});
			return null;
		}
	}
}

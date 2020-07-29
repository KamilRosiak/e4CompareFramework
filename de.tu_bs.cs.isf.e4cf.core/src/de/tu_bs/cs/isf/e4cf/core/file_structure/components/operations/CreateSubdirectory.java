package de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.Directory;

public class CreateSubdirectory implements CreateOperation {

	String _subdirName;
	
	public CreateSubdirectory(String subdirName) {
		_subdirName = subdirName;
	}
	
	@Override
	public FileTreeElement execute(FileTreeElement element) {
		Path newDirPath = Paths.get(element.getAbsolutePath(), _subdirName);
		
		if (Files.exists(newDirPath)) {
			return new Directory(newDirPath.toString(), element);			
		} else {
			return null;
		}
	}

}

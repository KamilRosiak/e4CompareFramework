package de.tu_bs.cs.isf.e4cf.core.file_structure.extensions;

import java.util.HashMap;
import java.util.Map;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;


public class FileExtensionMapper {

	Map<String,FileExtension> _extensions = new HashMap<>();
	
	public void add(FileExtension extension) {
		_extensions.put(extension.getName(), extension);
	}
	
	public void addDefault(String extension) {
		add(new FileExtension() {
			@Override
			public String getName() {
				return extension;
			}
			@Override
			public <T> T loadFile(FileTreeElement element) {return null;} // do nothing

			@Override
			public void saveFile(FileTreeElement element) {} // do nothing
		});
	}
	
	public FileExtension get(String extension) {
		return _extensions.get(extension);
	}
}

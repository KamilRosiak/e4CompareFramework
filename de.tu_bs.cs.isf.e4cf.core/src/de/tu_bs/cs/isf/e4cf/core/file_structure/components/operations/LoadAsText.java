package de.tu_bs.cs.isf.e4cf.core.file_structure.components.operations;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

public class LoadAsText implements LoadOperation<String> {

	@Override
	public String execute(FileTreeElement element) {
		Path file = Paths.get(element.getAbsolutePath());
		try {
			String content = new String(Files.readAllBytes(file), "UTF-8");
			return content;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


}

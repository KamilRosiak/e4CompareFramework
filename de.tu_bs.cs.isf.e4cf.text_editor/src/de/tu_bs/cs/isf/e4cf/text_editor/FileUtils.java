package de.tu_bs.cs.isf.e4cf.text_editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;


public class FileUtils {
	private FileChooser fileChooser;
	private Window parent;
	private File file;
	private int lastSavedRevision;
	
	/**
	 * A Method to initialize fileChooser and the extension that can be choose
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	public FileUtils(Window parent) {
		this.parent = parent;

		fileChooser = new FileChooser();
		fileChooser.setTitle("Open a File");
		fileChooser.getExtensionFilters().addAll(
			new ExtensionFilter("Text Files", "*.txt"),
			new ExtensionFilter("Java Files", "*.java"),
			new ExtensionFilter("XML Files", "*.xml")
		);
		fileChooser.setInitialDirectory(new File("."));
	}

	/**
	 * A Method for opening a file
	 * 
	 * @author Lukas Cronauer
	 */
	public String openFile() {
		File f = fileChooser.showOpenDialog(parent);
		if (f == null) {
			return "";
		} else if (!f.exists()) {
			// show error dialog
			return "";
		}

		file = f;
		return readFile(file);	
	}
	
	/**
	 * A Method to read the file and return the string
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	public String readFile(File file) {
		FileReader reader;
		try {
			reader = new FileReader(file);
			int character;
			String text="";
			
			while((character = reader.read()) != -1) {
				text += (char) character;
			}
			reader.close();
			lastSavedRevision = text.hashCode();
			return text;
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}
		return "";
	}
	
	/**
	 * A Method to save a file that is currently in use
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	public boolean save(String content) {
		if (file == null) {
			saveAs(content);
		}

		return writeFile(content);
	}

	/**
	 * A Method to save a file in another directory or with another name
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	public boolean saveAs(String content) {
		File f = fileChooser.showSaveDialog(parent);
		if (f == null) {
			return false;
		} else {
			file = f;
		}

		return writeFile(content);
	}

	/**
	 * A Method to write the File and saving it
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	private boolean writeFile(String content) {
		fileChooser.setTitle("Save a File");
		FileWriter writer;
		try {
			writer = new FileWriter(file.getName(), false);
			writer.write(content);
			writer.close();
			lastSavedRevision = content.hashCode();
			return true;
		} catch(IOException io) {
			io.printStackTrace();
			return false;
		}
	}

	/**
	 * A Method to get latest Revision
	 * 
	 * @author Lukas Cronauer
	 */
	public int getLastRevision() {
		return lastSavedRevision;
	}
}
	


package de.tu_bs.cs.isf.e4cf.text_editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;

import java.io.FileReader;
import java.io.FileWriter;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;


public class FileUtils {
	private FileChooser fileChooser;
	private Window parent;
	private File file;
	private int lastSavedRevision;
	
	/**
	 *  Constructor used to initialize the fileChooser instance of this object.
	 *  Available file extensions are added to the fileChooser.
	 *  
	 *  @param parent Window the @TextEditor is part of. Needed to display open/save dialogs
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	public FileUtils(Window parent) {
		this.parent = parent;

		fileChooser = new FileChooser();
		
		//TODO: obtain wanted extensions from collection and add them here
		fileChooser.getExtensionFilters().addAll(
			new ExtensionFilter("Text File (.txt)", "*.txt"),
			new ExtensionFilter("Java File (.java)", "*.java"),
			new ExtensionFilter("XML File (.xml)", "*.xml")
		);
		fileChooser.setInitialDirectory(new File(RCPContentProvider.getCurrentWorkspacePath()));
	}

	/**
	 * Opens the file chosen by the open dialog.
	 * 
	 * @author Lukas Cronauer
	 */
	public String openFile() {
		fileChooser.setTitle("Open...");
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
	 * Reads the entire content the file and returns it as a string.
	 * 
	 * @param file The file to open
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
			// error message: file not found
			e.printStackTrace();
		} catch (IOException io) {
			// error message: error reading file
			io.printStackTrace();
		}
		return "";
	}
	
	/**
	 * Saves the parameter content into the file instance of this object.
	 * If file is not yet set, saveAs() is called.
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	public boolean save(String content) {
		if (file == null) {
			return saveAs(content);
		}

		boolean result = writeFile(content);
		if (!result) {
			RCPMessageProvider.errorMessage("Looks like there's and error, file can't be saved", "Error while saving file");
		}

		return result;
	}

	/**
	 * A Method to save a file in another directory or with another name.
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	public boolean saveAs(String content) {
		fileChooser.setTitle("Save...");
		File f = fileChooser.showSaveDialog(parent);
		if (f == null) {
			return false;
		} else {
			file = f;
		} 

		boolean result = writeFile(content);
		if (!result) {
			RCPMessageProvider.errorMessage("Looks like there's and error, file can't be saved", "Error while saving file");
		}

		return result;
	}

	/**
	 * Writes the parameter content into the File.
	 * @param content The String to save
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	private boolean writeFile(String content) {
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
	 * Returns the hashCode of the files last saved content as a string.
	 * 
	 * @author Lukas Cronauer
	 */
	public int getLastRevision() {
		return lastSavedRevision;
	}
}
	


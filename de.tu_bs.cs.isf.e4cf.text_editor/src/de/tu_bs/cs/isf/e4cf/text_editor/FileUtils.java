package de.tu_bs.cs.isf.e4cf.text_editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

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
	private HashMap<String, String> filePaths = new HashMap<>();
	
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

	public FileUtils() {
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
	 * @return String[2] with fileName at index 0 and file-content at index 2
	 * 
	 * @author Lukas Cronauer
	 */
	public String[] openFile() {
		fileChooser.setTitle("Open...");
		String[] returnValue = new String[2];
		File f = fileChooser.showOpenDialog(parent);
		if (f == null) {
			return returnValue;
		} else if (!f.exists()) {
			// show error dialog
			return returnValue;
		}

		filePaths.put(f.getName(), f.getAbsolutePath());
		returnValue[0] = f.getName();
		returnValue[1] = readFile(f);
		return returnValue;	
	}
	
	/**
	 * Reads the entire content the file and returns it as a string.
	 * 
	 * @param file The file to open
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	public  String readFile(File file) {
		if (file == null) {
			throw new NullPointerException("File is null");
		}

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
			filePaths.put(file.getName(), file.getAbsolutePath());
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
	public boolean save(String fileName, String content) {
		if (!filePaths.containsKey(fileName)) {
			return saveAs(content);
		}

		boolean result = writeFile(fileName, content);
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
			filePaths.put(f.getName(), f.getAbsolutePath());
		} 

		boolean result = writeFile(f.getName(), content);
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
	private boolean writeFile(String fileName, String content) {
		FileWriter writer;
		try {
			writer = new FileWriter(new File(filePaths.get(fileName)));
			writer.write(content);
			writer.close();
			lastSavedRevision = content.hashCode();
			return true;
		} catch(IOException io) {
			io.printStackTrace();
			RCPMessageProvider.errorMessage("Error while saving file", "Looks like there's and error, file can't be saved");
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
	


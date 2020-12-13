package de.tu_bs.cs.isf.e4cf.text_editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.inject.Inject;

import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.text_editor.stringtable.EditorST;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

/**
 * Contains methods for common file interactions such as opening and closing
 * 
 * @author Erwin Wijaya, Lukas Cronauer, Soeren Christmann, Cedric Kapalla
 *
 */
public class FileUtils {
	private FileChooser fileChooser;
	private Window parent;
	private int lastSavedRevision;

	/**
	 * Constructor used to initialize the fileChooser instance of this object.
	 * Available file extensions are added to the fileChooser.
	 * 
	 * @param parent Window the @TextEditor is part of. This will be needed to
	 *               display open/save dialogs
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	public FileUtils(Window parent) {
		this.parent = parent;

		fileChooser = new FileChooser();

		for (String extension : EditorST.FILE_FORMATS) {
			String displayName = extension.substring(0, 1).toUpperCase() + extension.substring(1) + " File (."
					+ extension + ")";
			String extensionFormat = "*." + extension;
			fileChooser.getExtensionFilters().add(new ExtensionFilter(displayName, extensionFormat));
		}

		fileChooser.setInitialDirectory(new File(RCPContentProvider.getCurrentWorkspacePath()));
	}

	/**
	 * Extracts the fileName from a string containing a file path.
	 * 
	 * @param path A filePath ending in a file
	 * @return The fileName with extension (e.g. name.extension)
	 * @author Lukas Cronauer
	 */
	public static String parseFileNameFromPath(String path) {
		String fileName = path;
		String[] splittedPath;
		if (!path.startsWith(EditorST.NEW_TAB_TITLE)) {
			if (System.getProperty("os.name").startsWith("Windows")) {
				splittedPath = path.split("\\\\");
			} else {
				splittedPath = path.split("/");
			}

			try {
				if (splittedPath[splittedPath.length - 1].matches(EditorST.FILE_REGEX)) {
					fileName = splittedPath[splittedPath.length - 1];
				} else {
					throw new ArrayIndexOutOfBoundsException("Invalid filename");
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println(e.getMessage());
				fileName = EditorST.NEW_TAB_TITLE;
			}
		}

		return fileName;
	}

	/**
	 * Opens the file, which will be chosen by the open dialog.
	 *
	 * @return String[] of length 2 with (absolute) filePath at index 0 and
	 *         file-content at index 1
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

		returnValue[0] = f.getAbsolutePath();
		returnValue[1] = readFile(f);
		return returnValue;
	}

	/**
	 * Reads the entire content of the file and returns it as a string.
	 * 
	 * @param file The file to open
	 * @return String the text from the file
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	public String readFile(File file) {
		if (file == null) {
			return "";
		}

		FileReader reader;
		try {
			reader = new FileReader(file);
			int character;
			String text = "";

			while ((character = reader.read()) != -1) {
				text += (char) character;
			}
			reader.close();
			lastSavedRevision = text.hashCode();
			return text;
		} catch (FileNotFoundException e) {
			// error message: file not found
			e.printStackTrace();
		} catch (IOException io) {
			// error message: error reading file
			io.printStackTrace();
		}
		return "";
	}

	/**
	 * Saves the parameter content into the file instance of this object. If file is
	 * not yet set, saveAs() is called instead.
	 * 
	 * @param filepath the Name of the file to write
	 * @param content  The String to save
	 * @return a method that will be called to write the file on the given path
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	public boolean save(String filepath, String content) {
		return writeFile(filepath, content);
	}

	/**
	 * A Method to save a file in another directory or with another name.
	 * 
	 * @param content The String to save
	 * @return (absolute) path of the file, if file has been saved. Otherwise an
	 *         empty String
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	public String saveAs(String content) {
		fileChooser.setTitle("Save...");
		File f = fileChooser.showSaveDialog(parent);
		if (f != null && writeFile(f.getAbsolutePath(), content)) {
			return f.getAbsolutePath();
		} else {
			// no file selected or error while saving
			return "";
		}
	}

	/**
	 * Writes the parameter content into the File.
	 * 
	 * @param filepath the Name of the file to write
	 * @param content  The String to save
	 * @return boolean to show if the file has been successfully saved
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	private boolean writeFile(String filepath, String content) {
		FileWriter writer;
		try {
			writer = new FileWriter(new File(filepath));
			writer.write(content);
			writer.close();
			lastSavedRevision = content.hashCode();
			return true;
		} catch (IOException io) {
			io.printStackTrace();
			RCPMessageProvider.errorMessage("Error while saving file", io.getMessage());
			return false;
		} catch (NullPointerException n) {
			n.printStackTrace();
			RCPMessageProvider.errorMessage("Error while saving file", n.getMessage());
			return false;
		}
	}

	/**
	 * A Method to get the latest revision of a file.
	 * 
	 * @return the hashCode of the files last saved content as a string.
	 * @author Lukas Cronauer
	 */
	public int getLastRevision() {
		return lastSavedRevision;
	}
}

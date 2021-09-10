package de.tu_bs.cs.isf.e4cf.core.util.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;

/**
 * This Utility class contains helper methods that allow to handle files and
 * their input.
 * 
 * @author Kamil
 *
 */
public class FileStreamUtil {
	/**
	 * Read file content into string with - Files.lines(Path path, Charset cs)
	 * 
	 * @param filePath is the path of the file to read
	 * @return returns a the content of the file as String
	 */
	public static String readLineByLine(Path filePath) {
		StringBuilder contentBuilder = new StringBuilder();

		try (Stream<String> stream = Files.lines(Paths.get(filePath.toUri()), StandardCharsets.UTF_8)) {
			stream.forEach(s -> {
				
				
				//System.out.println("VALUE: " + s);
				contentBuilder.append(s).append("\n");	
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		return contentBuilder.toString();
	}

	/**
	 * This method write a string into an text file.
	 */
	public static void writeTextToFile(String path, String text) {
		try {
			File file = new File(path);
			// Handle overwriting files with an message dialog
			int choice = 0;
			if (file.exists()) {
				choice = RCPMessageProvider.optionMessage("File Exists", "Would you like to overwrite the file ?",
						"yes", "no", "cancel");
			}
			// choice yes means to overwrite the file if exist
			if (choice == 0) {
				Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
				PrintWriter print = new PrintWriter(writer);
				print.append(text);
				print.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Makes a deep copy of any Java object that is passed.
	 */
	public static Object deepCopy(Object object) {
		try {
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream outputStrm = new ObjectOutputStream(outputStream);
			outputStrm.writeObject(object);
			ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
			ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
			return objInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

package de.tu_bs.cs.isf.e4cf.core.util.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;

/**
 * This Utility class contains helper methods that allow to handle files and their input.
 * @author Kamil
 *
 */
public class FileStreamUtil {
	/**
	 * Read file content into string with - Files.lines(Path path, Charset cs)
	 * @param filePath is the path of the file to read
	 * @return returns a the content of the file as String
	 */
	public static String readLineByLine(Path filePath) {
		StringBuilder contentBuilder = new StringBuilder();
		
        try (Stream<String> stream = Files.lines(Paths.get(filePath.toUri()), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e) {
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
			//Handle overwriting files with an message dialog
			int choice = 0;
			if(file.exists()) {
				choice = RCPMessageProvider.optionMessage("File Exists", "Would you like to overwrite the file ?", "yes", "no", "cancel");
			}
			//choice yes means to overwrite the file if exist
			if(choice == 0) {
				FileWriter writer = new FileWriter(path);
				PrintWriter print = new PrintWriter(writer);
				print.append(text);
				print.close();
			} 

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

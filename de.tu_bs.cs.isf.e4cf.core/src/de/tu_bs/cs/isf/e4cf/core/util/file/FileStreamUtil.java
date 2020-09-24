package de.tu_bs.cs.isf.e4cf.core.util.file;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

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
}

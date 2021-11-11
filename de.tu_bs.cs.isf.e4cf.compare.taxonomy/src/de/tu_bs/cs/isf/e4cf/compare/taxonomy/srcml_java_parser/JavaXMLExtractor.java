/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.srcml_java_parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Path;

/**
 * @author developer-olan
 *
 */
public class JavaXMLExtractor {

	/**
	 * 
	 */
	public JavaXMLExtractor() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static InputStream parseWithsrcML(String exePath, Path fileArgument, String xmLResultPath) throws IOException {
		
		InputStream inputStream = null;
		try 
		{		
			// Using Process Builder
			ProcessBuilder pb = new ProcessBuilder(exePath, fileArgument.toString());
			//pb.directory(new File("C:\\Program Files\\srcML\\"));
			pb.redirectErrorStream(true);
			
			if (xmLResultPath != null && (!xmLResultPath.equals(""))) {
				File log = new File(xmLResultPath);
				pb.redirectOutput(log); 
			}
			
			pb.redirectOutput(Redirect.PIPE);
			Process p = pb.start();
			inputStream = p.getInputStream();
			
		} catch (Exception ex) {
			System.out.println("Error Occurred: "+ex.getMessage());
		}
		
		return inputStream;
	}
	
	
	

}

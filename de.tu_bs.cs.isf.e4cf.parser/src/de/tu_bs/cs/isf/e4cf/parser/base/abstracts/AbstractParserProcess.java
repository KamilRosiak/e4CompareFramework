package de.tu_bs.cs.isf.e4cf.parser.base.abstracts;

import static de.tu_bs.cs.isf.e4cf.parser.plugin.ParserPluginStrings.DESCRIPTION_ATTRIBUTE;
import static de.tu_bs.cs.isf.e4cf.parser.plugin.ParserPluginStrings.ID_ATTRIBUTE;
import static de.tu_bs.cs.isf.e4cf.parser.plugin.ParserPluginStrings.IS_DEFAULT_ATTRIBUTE;
import static de.tu_bs.cs.isf.e4cf.parser.plugin.ParserPluginStrings.LABEL_ATTRIBUTE;
import static de.tu_bs.cs.isf.e4cf.parser.plugin.ParserPluginStrings.PARSER_PROCESS_EXTENSION_POINT;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.ecore.EObject;

import de.tu_bs.cs.isf.e4cf.core.plugin.ExtensionPointContext;
import de.tu_bs.cs.isf.e4cf.parser.base.IParserProcess;
import de.tu_bs.cs.isf.e4cf.parser.base.helper.IParserFactory;
import de.tu_bs.cs.isf.e4cf.parser.impl.DefaultParserFactory;
import de.tu_bs.cs.isf.e4cf.parser.util.ModelToXMIExporter;

/**
 * Default Parser Process implementation extracting the id and description from the extension and 
 * partially defining getters and setters. It also provides the process of parsing a list of elements 
 * and writing the resulting model to an output directory. 
 * Clients should implement {@link AbstractParserProcess#parse} and {@link AbstractParserProcess#getModelFilename}.
 *  
 * @author Oliver Urbaniak
 */
public abstract class AbstractParserProcess <T extends EObject> implements IParserProcess {

	protected IParserFactory parserFactory = new DefaultParserFactory();
	private String label;
	private String description;
	private boolean isDefault;
	
	public AbstractParserProcess(String extensionId) {
		ExtensionPointContext pluginContext = new ExtensionPointContext(PARSER_PROCESS_EXTENSION_POINT);
		IConfigurationElement config = pluginContext.getConfigElementWhere(ID_ATTRIBUTE, extensionId);
		label = config.getAttribute(LABEL_ATTRIBUTE);
		description = config.getAttribute(DESCRIPTION_ATTRIBUTE);
		isDefault = Boolean.parseBoolean(config.getAttribute(IS_DEFAULT_ATTRIBUTE));
	}

	abstract protected T parse(Path file) throws IOException;
	
	/**
	 * Returns the output file name deduced from the model instance.
	 * The null value is also valid causing the output file name to be deduced
	 * from the input file. 
	 * 
	 * @param modelInstance
	 * @return model file name or null, if the input file name is to be used.
	 */
	abstract protected String getModelFilename(T modelInstance);
	
	@Override
	public void start(List<Path> inputFiles, Path outputDirectory) {
		List<Path> outputFiles = new ArrayList<>();
		List<Path> existingFiles = new ArrayList<>();
		List<T> outputPrograms = new ArrayList<>();
		
		try {			
			for (Path pasFile : inputFiles) {
				
				// parse file
				T program = parse(pasFile);	
				
				if (program != null) {
					// compose output file, if there was no provided filename, use the input file name
					String filename = getModelFilename(program);
					if (filename == null) {
						filename = getFilename(pasFile);
					}
					String modelFileName = buildFile(filename, getOutputFileFormat());
					
					// store output files and and files that already exist
					Path outputPath = outputDirectory.resolve(modelFileName);
					if (Files.exists(outputPath)) {
						existingFiles.add(outputPath);						
					}
					outputPrograms.add(program);
					outputFiles.add(outputPath);
					System.out.println("Parser successfully compiled \""+pasFile.getFileName()+"\".");
				} else {
					System.out.println("Parser error on \""+pasFile.getFileName()+"\" occured.");
				}
			}
			saveOutputFiles(outputFiles, existingFiles, outputPrograms);
			printEmptyLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void printEmptyLine() {
		System.out.println("");
	}
	
	/**
	 * Gets the content of <b>pasFile</b> using the <b>charset</b>. 
	 * 
	 * @param pasFile the input file
	 * @param charset the text encoding 
	 * @return the String representing the content of the file
	 * @throws IOException when reading from the file fails
	 */
	public static String getFileContent(Path file, Charset charset) throws IOException {	
		FileInputStream fis = new FileInputStream(file.toAbsolutePath().toString());
		InputStreamReader isr = new InputStreamReader(fis, charset);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) sb.append(line+"\n");
		String fileString = sb.toString().trim();
		fis.close();
		isr.close();
		br.close();
		
		if (charset.equals(StandardCharsets.UTF_8) || charset.equals(StandardCharsets.UTF_16)) {
			final String UTF8_BOM = "\uFEFF";
			if (fileString.startsWith(UTF8_BOM)) {
				fileString = fileString.substring(1);
			}
		}
		
		return fileString;
	}
	
	/**
	 * Writes the output files according to their paths. Checks if existing files should be overwritten.
	 * For every processed file prints out a status message.
	 * 
	 * @throws IOException
	 */
	private void saveOutputFiles(List<Path> outputFiles, List<Path> existingFiles, List<T> outputPrograms) {
		boolean overwriteFiles = shouldOverwrite(existingFiles);
		for (int index = 0; index < outputFiles.size(); index++) {
			try {
				if (existingFiles.contains(outputFiles.get(index))) {
					if (overwriteFiles) {
						ModelToXMIExporter.exportToXMI(outputFiles.get(index), outputPrograms.get(index));
						System.out.println("Successfully overwritten \""+outputFiles.get(index)+"\".");
					}
				} else {
					ModelToXMIExporter.exportToXMI(outputFiles.get(index), outputPrograms.get(index));
					System.out.println("Successfully saved \""+outputFiles.get(index)+"\".");
				}
			} catch (IOException e) {
				System.out.println("Save error on \""+outputFiles.get(index)+"\" occured: "+e.getMessage());
				outputFiles.remove(index);
			}
		}
		printParserFinishedMessage(outputFiles);
	}
	
	/**
	 * Sends a status message to the console.
	 * 
	 * @param outputFiles
	 */
	private void printParserFinishedMessage(List<Path> outputFiles) {
		if (!outputFiles.isEmpty()) {
			Path outputDirectory = outputFiles.get(0).getParent();
			if (outputFiles.isEmpty()) {
				System.out.println("No file has been parsed.");
			} else if (outputFiles.size() == 1) {
				System.out.println("1 file has been parsed into \""+outputDirectory.toString()+"\".");
			} else if (outputFiles.size() > 1) {
				System.out.println(outputFiles.size()+" files have been parsed into \""+outputDirectory.toString()+"\".");				
			}			
		}
	}
	
	/**
	 * Determines if files should be overwritten.
	 * 
	 * @param existingFiles file paths that already exist
	 */
	private boolean shouldOverwrite(List<Path> existingFiles) {
		boolean overwriteFiles = true;
		if (!existingFiles.isEmpty()) {
			overwriteFiles = true;// RCPMessageProvider.questionMessage("File(s) Exist", "One or more files already exist. Do you want to overwrite them?");								
		}
		return overwriteFiles;
	}
	
	protected String getFilename(Path file) {
		Path filenamePath = file.getFileName();
		if (Files.isDirectory(filenamePath)) {
			throw new RuntimeException("Input file must not be a directory.");
		}
		
		return filenamePath.toString().split("\\.")[0];
	}
		
	protected String buildFile(String filename, String extension) {
		return filename+"."+extension;
	}

	public IParserFactory getParserFactory() {
		return parserFactory;
	}
	
	@Override
	public boolean isDefault() {
		return isDefault;
	}
	
	@Override
	public String getLabel() {
		return label;
	}
	
	@Override
	public String getDescription() {
		return description;
	}

}

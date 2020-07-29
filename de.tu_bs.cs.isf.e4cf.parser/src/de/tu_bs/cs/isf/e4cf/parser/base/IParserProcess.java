package de.tu_bs.cs.isf.e4cf.parser.base;

import java.nio.file.Path;
import java.util.List;

/**
 * The translation of input file contents to another representation stored in an output directory.
 * It defines a configuration of parsers and node callbacks in the context of creating metamodel instances. 
 * 
 * @author Oliver Urbaniak
 *
 * @see INodeCallback
 * @see IParser
 */
public interface IParserProcess {

	/**
	 * Processes a list of input files and stores the result in the given output directory.
	 * 
	 * @param inputFiles
	 * @param outputDirectory
	 */
	public void start(List<Path> inputFiles, Path outputDirectory);
	
	/**
	 * Returns the compatible parser type for this process. 
	 * 
	 * @return parser type
	 */
	public ParserType getType();
	
	/**
	 * Returns the file formats that this process is able to process.
	 * 
	 * @return the list of compatible file formats
	 */
	public List<String> getCompatibleFileFormats();
	
	/**
	 * Returns the output format of the process result.
	 * 
	 * @return output format
	 */
	public String getOutputFileFormat();
	
	/**
	 * Returns if this is the default process for the used parser type.
	 * 
	 * @return default 
	 */
	public boolean isDefault();
	
	/**
	 * Returns the name of the underlying parser process.
	 * Should encompass the distinct intent of the process. 
	 * 
	 * @return label
	 */
	public String getLabel();
	
	/**
	 * Returns the description for this parser.
	 * 
	 * @return description
	 */
	public String getDescription();
}

package de.tu_bs.cs.isf.e4cf.parser.base;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * This interface should be used to parse hierarchical structures, i.e., XML and JSON. 
 * The parser is able to distribute <i>parser nodes</i> to attached <i>callbacks</i> in order
 * for them to process each node individually. 
 * 
 * @author Oliver Urbaniak
 * 
 * @see INodeCallback
 * @see ParserNode
 */
public interface IParser{
	
	/**
	 * Sets the initial data to parameterize parsing. 
	 * 
	 * @param initData
	 */
	public void setInitializationData(Map<String, String> initData);

	/**
	 * Parses <i>input</i> into a set of parser nodes. This method does not return a result.
	 * Instead, callbacks
	 * 
	 * @param input
	 */
    public void parse(String input);

    /**
     * Attaches <i>nodeCallback</i> to this parser.
     * 
     * @param nodeCallback
     */
    public void addNodeCallable(INodeCallback nodeCallback);
  
    /**
     * Detaches <i>nodeCallback</i> from this parser.
     * 
     * @param nodeCallable
     */
    public void removeNodeCallable(INodeCallback nodeCallable);
    
    /**
     * Detaches all callbacks from this parser.
     */
    public void removeAllNodeCallables();

    /**
     * Returns the list of attached callbacks.
     * 
     * @return attached callbacks
     */
    public List<INodeCallback> getNodeCallables();

    /**
     * Sets the currently processed file.
     * 
     * @param filePath
     */
    public void setFilePath(Path filePath);
    
    /**
     * Returns the currently processed file.
     * 
     * @return processed file
     */
    public Path getFilePath();
    
    /**
     * Returns the type of this parser.
     * 
     * @return parser type
     */
	public ParserType getType();
    
	/**
	 * Returns the description for this parser.
	 * 
	 * @return description
	 */
    public String getDescription();
}


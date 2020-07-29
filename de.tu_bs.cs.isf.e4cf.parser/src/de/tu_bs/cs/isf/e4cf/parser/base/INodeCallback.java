package de.tu_bs.cs.isf.e4cf.parser.base;

/**
 * Node callbacks process parser nodes provided by an {@link IParser}.
 * After parsing the callback is able to return its result. 
 * 
 * @author Oliver Urbaniak
 * 
 * @see ParserNode
 * @see ParserType
 */
public interface INodeCallback {

	/**
	 * Processes each node provided by the underlying parser.
	 * 
	 * @param node
	 */
	public void processNode(ParserNode node);        

	/**
	 * Executes final processing after each node was accepted by this callback.
	 * 
	 * @return status code
	 */
	public int postProcessing();

	/**
	 * Returns the computed result.
	 * 
	 * @return result of this callback for one pass
	 */
	public Object getResult();

	/**
	 * Sets the providing parser.
	 * 
	 * @param parser
	 */
	public void setParser(IParser parser);
	
	/**
	 * Returns the providing parser
	 * 
	 * @return parser
	 */
	public IParser getParser();
	
	/**
	 * Returns the description of this callback.
	 * 
	 * @return description
	 */
	public String getDescription();

	/**
	 * Returns the id of this callback.
	 * 
	 * @return id
	 */
	public String getId();
	
	/**
	 * Returns the type of parser the callback is compatible to.
	 * 
	 * @return parser type
	 */
	public ParserType getType();
}
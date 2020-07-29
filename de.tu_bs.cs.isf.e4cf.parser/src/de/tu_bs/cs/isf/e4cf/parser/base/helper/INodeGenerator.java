package de.tu_bs.cs.isf.e4cf.parser.base.helper;

import de.tu_bs.cs.isf.e4cf.parser.base.ParserNode;

/**
 * Interface for generating parser nodes.
 * 
 * @author Oliver Urbaniak
 * 
 * @see ParserNode
 */
public interface INodeGenerator {

	/**
	 * Generate a new parser node using the provided context.
	 * 
	 * @param context
	 * @return generated parser node
	 */
	ParserNode generate(Object context);
}

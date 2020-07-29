package de.tu_bs.cs.isf.e4cf.parser.base.helper;

import de.tu_bs.cs.isf.e4cf.parser.base.ParserNode;

/**
 * Transformation of a parser node into a target representation, e.g, ecore instances.
 * 
 * @author Oliver Urbaniak
 *
 * @param <T>
 */
public interface IModelBuilder <T> {

	/**
	 * Builds an instance of <<b>T</b>>. 
	 * 
	 * @param rootNode of the tree representing the instance
	 * @return instance of <<b>T</b>>
	 */
	public T build(ParserNode rootNode);
}

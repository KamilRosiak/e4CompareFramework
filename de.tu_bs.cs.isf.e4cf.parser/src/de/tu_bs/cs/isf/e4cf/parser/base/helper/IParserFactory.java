package de.tu_bs.cs.isf.e4cf.parser.base.helper;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.eclipse.core.runtime.CoreException;

import de.tu_bs.cs.isf.e4cf.parser.base.INodeCallback;
import de.tu_bs.cs.isf.e4cf.parser.base.IParser;
import de.tu_bs.cs.isf.e4cf.parser.base.IParserProcess;
import de.tu_bs.cs.isf.e4cf.parser.base.ParserType;

/**
 * Factory interface for creating instances of {@link IParser}, {@link INodeCallback} and {@link IParserProcess}.
 * 
 * @author Oliver Urbaniak
 *
 */
public interface IParserFactory {

	/**
	 * Creates and returns a parser instance denoted by <i>parserId</i>.
	 * 
	 * @param parserId id for the requested parser
	 * @param initData initialization information for the parser
	 * @return parser instance or null if there's no parser with the given id
	 * 
	 * @throws CoreException
	 * @throws NoSuchElementException
	 */
	public IParser createParser(String parserId, Map<String, String> initData) throws CoreException, NoSuchElementException;
	
	/**
	 * Creates and returns instances of all parsers known by this factory.
	 * 
	 * @return
	 * @throws CoreException
	 */
	public List<IParser> getAllParsers() throws  CoreException;

	/**
	 * Return the parser description denoted by <i>parserId</i>.
	 * 
	 * @param parserId
	 * @return parser description
	 */
	public String getParserDescription(String parserId);
	
	/**
	 * Creates and returns a parser application instance denoted by <i>parserAppId</i>.
	 * 
	 * @param parserAppId
	 * @return parser application instance or null if there's no parser with the given id
	 * 
	 * @throws CoreException
	 * @throws NoSuchElementException
	 */
	public IParserProcess createParserApplication(String parserAppId) throws NoSuchElementException, CoreException;
	
	/**
	 * Creates and returns instances of all parser application filtered for a particular parser type.
	 * 
	 * @param parserType
	 * @return list of type-specific parser applications
	 * 
	 * @throws CoreException
	 */
	public List<IParserProcess> getAllParserApplicationsFor(ParserType parserType) throws CoreException;
	
	/**
	 * Creates and returns instances of all parser applications.
	 * 
	 * @return list of type-specific parser applications
	 * 
	 * @throws CoreException
	 */
	public List<IParserProcess> getAllParserApplications() throws CoreException;
	
	/**
	 * Create and returns the default parser application for the given type.
	 * 
	 * @param parserType
	 * @return default parser application
	 * 
	 * @throws CoreException
	 */
	public IParserProcess createDefaultParserApplicationFor(ParserType parserType) throws CoreException;
	
	/**
	 * Return the parser application description denoted by <i>parserAppId</i>.
	 * 
	 * @param parserAppId
	 * @return parser application description
	 */
	public String getParserAppDescription(String parserAppId);

	/**
	 * Create and return a callback instance denoted by <i>callableId</i>.
	 * 
	 * @param callableId
	 * @return callback instance or null, if the there's no callback with the given id
	 * 
	 * @throws NoSuchElementException
	 * @throws CoreException
	 */
	public INodeCallback createNodeCallable(String callableId) throws NoSuchElementException, CoreException;
}

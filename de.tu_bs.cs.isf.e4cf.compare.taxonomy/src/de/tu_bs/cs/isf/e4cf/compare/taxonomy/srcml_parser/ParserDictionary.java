/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.srcml_parser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author developer-olan
 *
 */
public class ParserDictionary {

	public static List<String> languageDictionary = new ArrayList<String>();
	public static List<String> attributeDictionary = new ArrayList<String>();
	public static List<String> attributeKeyDictionary = new ArrayList<String>();
	
	/**
	 * 
	 */
	public ParserDictionary() {
		// TODO Auto-generated constructor stub
	}
	
	public static void implementDictionaryAndAttributes() {
		
		if (languageDictionary.size() == 0) {
			languageDictionary.addAll(new ArrayList<String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = -1130839144112597073L;

				{
//					add("class");
					add("function");
					add("constructor");
					add("decl_stmt");
					add("expr_stmt");
					add("typedef");
					add("if");
					add("else");
					add("for");
					add("while");
					add("do");
					add("return");
				}
			});
		}
		
		if (attributeDictionary.size() == 0) {
			attributeDictionary.addAll(new ArrayList<String>() {/**
				 * 
				 */
				private static final long serialVersionUID = 2768596879716055759L;

			{
					add("expr");
					add("type");
					add("name");
				}
			});
		}
		
		if (attributeKeyDictionary.size() == 0) {
			attributeKeyDictionary.addAll(new ArrayList<String>() {
				/**
				* 
				*/
				private static final long serialVersionUID = -3717663497299769476L;

				{
					add("name");
					add("operator");
					add("literal");
				}
			});
		}
	}


}

// Generated from XMLParser.g4 by ANTLR 4.4
package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.xml.impl;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link XMLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface XMLParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link XMLParser#reference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReference(@NotNull XMLParser.ReferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link XMLParser#component}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComponent(@NotNull XMLParser.ComponentContext ctx);
	/**
	 * Visit a parse tree produced by {@link XMLParser#document}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDocument(@NotNull XMLParser.DocumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link XMLParser#root}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoot(@NotNull XMLParser.RootContext ctx);
	/**
	 * Visit a parse tree produced by {@link XMLParser#chardata}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChardata(@NotNull XMLParser.ChardataContext ctx);
	/**
	 * Visit a parse tree produced by {@link XMLParser#attribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttribute(@NotNull XMLParser.AttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link XMLParser#prolog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProlog(@NotNull XMLParser.PrologContext ctx);
	/**
	 * Visit a parse tree produced by {@link XMLParser#content}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContent(@NotNull XMLParser.ContentContext ctx);
	/**
	 * Visit a parse tree produced by {@link XMLParser#element}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElement(@NotNull XMLParser.ElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link XMLParser#misc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMisc(@NotNull XMLParser.MiscContext ctx);
}
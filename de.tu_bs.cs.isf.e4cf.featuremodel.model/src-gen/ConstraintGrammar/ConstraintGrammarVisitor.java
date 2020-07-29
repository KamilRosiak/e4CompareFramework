// Generated from ConstraintGrammar.g4 by ANTLR 4.7.1
package ConstraintGrammar;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ConstraintGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ConstraintGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ConstraintGrammarParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormula(ConstraintGrammarParser.FormulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConstraintGrammarParser#equals_formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEquals_formula(ConstraintGrammarParser.Equals_formulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConstraintGrammarParser#implies_formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImplies_formula(ConstraintGrammarParser.Implies_formulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConstraintGrammarParser#or_formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr_formula(ConstraintGrammarParser.Or_formulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConstraintGrammarParser#and_formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd_formula(ConstraintGrammarParser.And_formulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConstraintGrammarParser#primitive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitive(ConstraintGrammarParser.PrimitiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConstraintGrammarParser#negation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegation(ConstraintGrammarParser.NegationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConstraintGrammarParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(ConstraintGrammarParser.LiteralContext ctx);
}
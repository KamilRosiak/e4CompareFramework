// Generated from ConstraintGrammar.g4 by ANTLR 4.7.1
package ConstraintGrammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ConstraintGrammarParser}.
 */
public interface ConstraintGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ConstraintGrammarParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterFormula(ConstraintGrammarParser.FormulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConstraintGrammarParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitFormula(ConstraintGrammarParser.FormulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConstraintGrammarParser#equals_formula}.
	 * @param ctx the parse tree
	 */
	void enterEquals_formula(ConstraintGrammarParser.Equals_formulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConstraintGrammarParser#equals_formula}.
	 * @param ctx the parse tree
	 */
	void exitEquals_formula(ConstraintGrammarParser.Equals_formulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConstraintGrammarParser#implies_formula}.
	 * @param ctx the parse tree
	 */
	void enterImplies_formula(ConstraintGrammarParser.Implies_formulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConstraintGrammarParser#implies_formula}.
	 * @param ctx the parse tree
	 */
	void exitImplies_formula(ConstraintGrammarParser.Implies_formulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConstraintGrammarParser#or_formula}.
	 * @param ctx the parse tree
	 */
	void enterOr_formula(ConstraintGrammarParser.Or_formulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConstraintGrammarParser#or_formula}.
	 * @param ctx the parse tree
	 */
	void exitOr_formula(ConstraintGrammarParser.Or_formulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConstraintGrammarParser#and_formula}.
	 * @param ctx the parse tree
	 */
	void enterAnd_formula(ConstraintGrammarParser.And_formulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConstraintGrammarParser#and_formula}.
	 * @param ctx the parse tree
	 */
	void exitAnd_formula(ConstraintGrammarParser.And_formulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConstraintGrammarParser#primitive}.
	 * @param ctx the parse tree
	 */
	void enterPrimitive(ConstraintGrammarParser.PrimitiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConstraintGrammarParser#primitive}.
	 * @param ctx the parse tree
	 */
	void exitPrimitive(ConstraintGrammarParser.PrimitiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConstraintGrammarParser#negation}.
	 * @param ctx the parse tree
	 */
	void enterNegation(ConstraintGrammarParser.NegationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConstraintGrammarParser#negation}.
	 * @param ctx the parse tree
	 */
	void exitNegation(ConstraintGrammarParser.NegationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConstraintGrammarParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(ConstraintGrammarParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConstraintGrammarParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(ConstraintGrammarParser.LiteralContext ctx);
}
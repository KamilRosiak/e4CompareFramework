package de.tu_bs.cs.isf.e4cf.featuremodel.model.parser;

import java.util.Stack;

import org.antlr.v4.runtime.tree.RuleNode;

import ConstraintGrammar.ConstraintGrammarBaseVisitor;
import ConstraintGrammar.ConstraintGrammarParser;
import CrossTreeConstraints.AbstractConstraint;
import CrossTreeConstraints.CrossTreeConstraintsFactory;
import CrossTreeConstraints.Formula;
import CrossTreeConstraints.Literal;
import CrossTreeConstraints.Operator;

public class ConstraintGrammarBaseVisitorImpl extends ConstraintGrammarBaseVisitor<String> {
	private AbstractConstraint constraint;
	private CrossTreeConstraintsFactory factory;
	private Stack<Formula> stack;
	boolean isNegated = false;

	public ConstraintGrammarBaseVisitorImpl() {
		factory = CrossTreeConstraintsFactory.eINSTANCE;
		stack = new Stack<Formula>();
	}
	
	private void pushFormula(Operator op) {
		Formula formula = factory.createFormula();
		formula.setOperator(op);
		formula.setIsNegated(isNegated);
		isNegated = false;
		if(constraint == null) {
			constraint = formula;
		}
		
		if(!stack.isEmpty()) {
			stack.peek().getFormula().add(formula);
		}
		stack.push(formula);
	}
	
	private void popStack() {
		if(!stack.isEmpty()) {
			stack.pop();
		}
	}
	
	/**
	 * This method handles all operator rules by given operator and context
	 */
	private String handleFormula(Operator op, RuleNode node) {
		if(node.getChildCount() > 1) {
			pushFormula(op);
		}
		visitChildren(node); 
		if(node.getChildCount() > 1) {
			popStack();
		}
		return "";
	}
	
	@Override
	public String visitFormula(ConstraintGrammarParser.FormulaContext ctx) { 
		return visitChildren(ctx); 
	}

	@Override 
	public String visitEquals_formula(ConstraintGrammarParser.Equals_formulaContext ctx) {
		return handleFormula(Operator.EQUALS, ctx);
	}

	@Override 
	public String visitImplies_formula(ConstraintGrammarParser.Implies_formulaContext ctx) { 
		return handleFormula(Operator.IMPLIES, ctx);
	}

	@Override 
	public String visitOr_formula(ConstraintGrammarParser.Or_formulaContext ctx) { 
		return handleFormula(Operator.OR, ctx);
	}

	@Override 
	public String visitAnd_formula(ConstraintGrammarParser.And_formulaContext ctx) { 
		return handleFormula(Operator.AND, ctx);
	}

	@Override 
	public String visitNegation(ConstraintGrammarParser.NegationContext ctx) { 
		isNegated = true;
		return visitChildren(ctx); 
	}

	@Override 
	public String visitLiteral(ConstraintGrammarParser.LiteralContext ctx) { 
		Literal literal = factory.createLiteral();
		literal.setName(ctx.getText());
		literal.setIsNegated(isNegated);
		isNegated = false;
		if(constraint == null) {
			constraint = literal;
		}
		if(!stack.isEmpty()) {
			stack.peek().getFormula().add(literal);
		}

		return visitChildren(ctx); 
	}
	
	public AbstractConstraint getConstraint() {
		return constraint;
	}

	public void setConstraint(AbstractConstraint constraint) {
		this.constraint = constraint;
	}
}

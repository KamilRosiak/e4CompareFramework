package de.tu_bs.cs.isf.familymining.ppu_iec.code_gen.st;

import static de.tu_bs.cs.isf.e4cf.core.transform.TransformationHelper.ifInstanceOfThen;
import static de.tu_bs.cs.isf.familymining.ppu_iec.code_gen.st.STSymbolTable.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.tu_bs.cs.isf.e4cf.core.transform.Transformation;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtextexpression.BinaryExpression;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtextexpression.Expression;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtextexpression.FunctionCallExpression;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtextexpression.Literal;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtextexpression.Parameter;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtextexpression.Subrange;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtextexpression.UnaryExpression;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtextexpression.VariableExpression;

public class ExpressionToStringExporter implements Transformation<String> {
	
	List<Class<?>> supportedTypes = Arrays.asList(Expression.class, Parameter.class);
	
	@Override
	public String apply(Object expr) {
		String[] exprText = new String[1];
		ifInstanceOfThen(expr, BinaryExpression.class, binExpr -> {
			exprText[0] = transformBinaryExpression(binExpr);
		});
		ifInstanceOfThen(expr, UnaryExpression.class, unaryExpr -> {
			exprText[0] = transformUnaryExpression(unaryExpr);
		});
		ifInstanceOfThen(expr, Literal.class, literal -> {
			exprText[0] = transformLiteral(literal);
		});
		ifInstanceOfThen(expr, VariableExpression.class, varExpr -> {
			exprText[0] = transformVariableExpression(varExpr);
		});
		ifInstanceOfThen(expr, FunctionCallExpression.class, funcCallExpr -> {
			exprText[0] = transformFunctionCallExpression(funcCallExpr);
		});
		ifInstanceOfThen(expr, Subrange.class, subrange -> {
			exprText[0] = transformSubrange(subrange);
		});
		ifInstanceOfThen(expr, Parameter.class, parameter -> {
			exprText[0] = transformParameter(parameter);
		});
		return exprText[0];
	}

	private String transformBinaryExpression(BinaryExpression binExpr) {
		switch(binExpr.getOperator()) {
			case ADD: 			return apply(binExpr.getLeft()) + WS + PLUS + WS + apply(binExpr.getRight());
			case MULT: 			return apply(binExpr.getLeft()) + WS + MULT + WS + apply(binExpr.getRight());
			case SUBTRACT: 		return apply(binExpr.getLeft()) + WS + MINUS + WS + apply(binExpr.getRight());
			case DIV: 			return apply(binExpr.getLeft()) + WS + SLASH + WS + apply(binExpr.getRight());
			case MOD:			return apply(binExpr.getLeft()) + WS + MOD + WS + apply(binExpr.getRight());
			case EXP: 			return apply(binExpr.getLeft()) + WS + MULT + MULT + WS + apply(binExpr.getRight());
			case GREATER_EQUAL: return apply(binExpr.getLeft()) + WS + GE + WS + apply(binExpr.getRight());
			case GREATER_THAN: 	return apply(binExpr.getLeft()) + WS + GT + WS + apply(binExpr.getRight());
			case LESSER_EQUAL: 	return apply(binExpr.getLeft()) + WS + LE + WS + apply(binExpr.getRight());
			case LESSER_THAN: 	return apply(binExpr.getLeft()) + WS + LT + WS+ apply(binExpr.getRight());
			case EQUAL:			return apply(binExpr.getLeft()) + WS + EQ + WS + apply(binExpr.getRight());
			case NOT_EQUAL: 	return apply(binExpr.getLeft()) + WS + NEQ + WS + apply(binExpr.getRight());
			case AND: 			return apply(binExpr.getLeft()) + WS + AND + WS + apply(binExpr.getRight());
			case OR: 			return apply(binExpr.getLeft()) + WS + OR + WS + apply(binExpr.getRight());
			case XOR: 			return apply(binExpr.getLeft()) + WS + XOR + WS + apply(binExpr.getRight());
			default: return null;
		}
	}

	private String transformUnaryExpression(UnaryExpression unaryExpr) {		
		switch(unaryExpr.getOperator()) {
			case NOT: 			return NOT + WS + apply(unaryExpr.getSubexpression());
			case NEGATIVE: 		return MINUS + WS + apply(unaryExpr.getSubexpression());
			case POSITIVE: 		return PLUS + WS + apply(unaryExpr.getSubexpression());
			case PARENTHESIS:	return LPARENT + apply(unaryExpr.getSubexpression()) + RPARENT;
			default: return null;
		}
	}

	/**
	 * {@code constant : numeric_literal | character_string | time_literal | bit_string | boolean_literal ; }
	 * 
	 * @param literal
	 * @return
	 */
	private String transformLiteral(Literal literal) {
		return literal.getSymbol();
	}

	/**
	 * {@code variable : direct_variable | symbolic_variable ;}<br>
	 * {@code symbolic_variable : variable_name symbolic_variable_helper? ;}<br>
	 * {@code symbolic_variable_helper : array_variable | structured_variable ;}<br>
	 * {@code array_variable : LSQR_BRACKET expression (COMMA expression)* RSQR_BRACKET symbolic_variable_helper? ;}<br>
	 * {@code structured_variable : DOT variable_name symbolic_variable_helper? ;}
	 * 
	 * @param varExpr
	 * @return
	 */
	private String transformVariableExpression(VariableExpression varExpr) {
		String varExprString = new String();
		varExprString += varExpr.getSymbol();
		
		if (!varExpr.getArrayIndices().isEmpty()) {
			List<String> arrayExprs = varExpr.getArrayIndices().stream().map(expr -> apply(expr)).collect(Collectors.toList());
			varExprString += LSQR_BRACKET;
			varExprString += String.join(COMMA + WS, arrayExprs);
			varExprString += RSQR_BRACKET;
		}
		if (varExpr.getInnerVariable() != null) {
			varExprString += DOT + transformVariableExpression(varExpr.getInnerVariable());
		}
		
		return varExprString;
	}

	/**
	 * {@code function_call : function_name LPARENT parameter_list? RPARENT ;}
	 * 
	 * @param funcCallExpr
	 * @return
	 */
	private String transformFunctionCallExpression(FunctionCallExpression funcCallExpr) {
		String funcCallExprString = new String();
		funcCallExprString += funcCallExpr.getSymbol();
		funcCallExprString += LPARENT;
			
		List<String> paramStrings = funcCallExpr.getParameters().stream().map(param -> transformParameter(param)).collect(Collectors.toList());
		funcCallExprString += String.join(COMMA + WS, paramStrings);
		
		funcCallExprString += RPARENT;
		
		return funcCallExprString;
	}

	/**
	 * {@code subrange : constant DOT DOT constant ;}
	 * 
	 * @param subrange
	 * @return
	 */
	private String transformSubrange(Subrange subrange) {
		String subrangeString = new String();
		subrangeString += transformLiteral(subrange.getLowerBound());
		subrangeString += DOT + DOT;
		subrangeString += transformLiteral(subrange.getUpperBound());
		
		return subrangeString;
	}

	/**
	 * {@code param_assignment : ((variable ASSIGN)? expression) ;}
	 * 
	 * @param parameter
	 * @return
	 */
	private String transformParameter(Parameter parameter) {
		String paramString = new String();
		
		// optional variable name
		if (parameter.getInput() != null) {
			paramString += transformVariableExpression(parameter.getInput()) + WS + ASSIGN + WS;			
		}
		paramString += apply(parameter.getAssignedValue());
		
		return paramString;
	}

	@Override
	public boolean canTransform(Object object) {
		return supportedTypes.contains(object.getClass());
	}
	
}

package de.tu_bs.cs.isf.e4cf.featuremodel.model.util;

import CrossTreeConstraints.AbstractConstraint;
import CrossTreeConstraints.Formula;
import CrossTreeConstraints.Literal;
import de.tu_bs.cs.isf.e4cf.featuremodel.model.stringtable.ConstraintGrammerKeywords;

public class ConstraintUtil {
	
	
	
	public static String createStyledConstraintText(AbstractConstraint constraint) {
		String constraintText = createConstraintText(constraint);
		constraintText = constraintText.replace(ConstraintGrammerKeywords.AND_OP,"\u2227" );
		constraintText = constraintText.replace(ConstraintGrammerKeywords.OR_OP,"\u2228" );
		constraintText = constraintText.replace(ConstraintGrammerKeywords.IMPL_OP,"\u21D2" );
		constraintText = constraintText.replace(ConstraintGrammerKeywords.EQUALS_OP,"\u21D4" );
		constraintText = constraintText.replace(ConstraintGrammerKeywords.NOT_OP,"\u00AC" );
		return constraintText;
	}
	
	
	public static String createConstraintText(AbstractConstraint constraint) {
		String constraintText= "";
		
		if(constraint instanceof Literal) {
			constraintText += createLiteralText((Literal)constraint);
		}
		
		if(constraint instanceof Formula) {
			constraintText += createFormulaText((Formula)constraint);
		}
		
		return constraintText;
	}
	
	/**
	 * Transforms a literal to a syntactical correct constraint
	 */
	public static String createLiteralText(Literal literal) {
		String litString = "";
		if(literal.getIsNegated()) {
			litString = ConstraintGrammerKeywords.NOT_OP +" ";
		}
		litString += literal.getName();
		return litString;
	}
	
	
	public static String createFormulaText(Formula formula) {
		String formulaString = "";
		if(formula.getIsNegated()) {
			formulaString = ConstraintGrammerKeywords.NOT_OP +" ";
		}
		formulaString += "(";
		for(AbstractConstraint childFormula : formula.getFormula()) {
			formulaString += createConstraintText(childFormula);
			if(!(formula.getFormula().indexOf(childFormula) == formula.getFormula().size() - 1)) {
				formulaString += " "+ formula.getOperator() + " ";
			}
		}
		formulaString += ")";
		return formulaString;
	}
}

package de.tu_bs.cs.isf.familymining.ppu_iec.code_gen.st;

import static de.tu_bs.cs.isf.e4cf.core.transform.TransformationHelper.ifInstanceOfThen;
import static de.tu_bs.cs.isf.familymining.ppu_iec.code_gen.st.STSymbolTable.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.tu_bs.cs.isf.e4cf.core.transform.Transformation;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.diagram.Return;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.Assignment;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.Case;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.CaseBlock;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.ConditionalBlock;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.ExitStatement;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.ForLoop;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.FunctionCallStatement;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.If;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.Statement;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.StatementType;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.StructuredText;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.UnboundedLoop;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtextexpression.Expression;

public class StructuredTextToStringExporter implements Transformation<String> {

	protected static final String ST_BLOCK_SEPARATOR = "(* ST Block Separator *)";
	
	List<Class<?>> supportedTypes = Arrays.asList(StructuredText.class, Statement.class);

	private Transformation<String> stExpressionToStringExporter;
	
	public StructuredTextToStringExporter() {
		this.stExpressionToStringExporter = new ExpressionToStringExporter();
	}
	
	@Override
	public String apply(Object t) {
		if (t instanceof StructuredText) {
			StructuredText st = (StructuredText) t;
			List<String> statementStrings = new ArrayList<String>();
			for (Statement statement : st.getStatements()) {
				statementStrings.add(transform(statement).trim());
			}
			
			statementStrings.removeIf(statementString -> statementString.trim().isEmpty());
			return String.join(NEWLINE, statementStrings);
		}
		
		if (t instanceof Statement) {
			Statement statement = (Statement) t;
			return transform(statement).trim();
		}
		
		if (t instanceof List) {
			List<?> list = (List<?>) t;
			String structuredTextString = new String();
			for (Object obj : list) {
				if (obj instanceof Statement) {
					Statement statement = (Statement) obj;
					structuredTextString += (structuredTextString.isEmpty() ? "" : NEWLINE);
					structuredTextString += transform(statement);
				} else if (obj instanceof StructuredText) {
					StructuredText st = (StructuredText) obj;
					structuredTextString += (structuredTextString.isEmpty() ? "" : NEWLINE + ST_BLOCK_SEPARATOR);
					structuredTextString += apply(st);
				}
			}
			return structuredTextString.trim();
		}
		
		return null;
	}

	/**
	 * Transforms a statement into a text that conforms to the Structured Text language specification*.
	 * The returned string is minimal with respect to non-content characters.
	 * 
	 * @param statement
	 * @return A valid Structured Text statement in text form.
	 */
	public String transform(Statement statement) {
		String[] statementText = new String[1];
		ifInstanceOfThen(statement, Assignment.class, assignment -> {
			statementText[0] = transformAssignment(assignment);
		});
		ifInstanceOfThen(statement, If.class, _if -> {
			statementText[0] = transformIf(_if);
		});
		ifInstanceOfThen(statement, ConditionalBlock.class, conditionalBlock -> {
			statementText[0] = transformConditionalBlock(IF, conditionalBlock);
		});
		ifInstanceOfThen(statement, Case.class, _case -> {
			statementText[0] = transformCase(_case);
		});
		ifInstanceOfThen(statement, CaseBlock.class, caseBlock -> {
			statementText[0] = transformCaseBlock(caseBlock);
		});
		ifInstanceOfThen(statement, FunctionCallStatement.class, functionCall -> {
			statementText[0] = transformFunctionCall(functionCall);
		});
		ifInstanceOfThen(statement, UnboundedLoop.class, unboundedLoop -> {
			statementText[0] = transformUnboundedLoop(unboundedLoop);
		});
		ifInstanceOfThen(statement, ForLoop.class, forLoop -> {
			statementText[0] = transformForLoop(forLoop);
		});
		ifInstanceOfThen(statement, ExitStatement.class, exit-> {
			statementText[0] = transformExitStatement(exit);
		});
		ifInstanceOfThen(statement, Return.class, _return -> {
			statementText[0] = transformReturn(_return);
		});
		
		return statementText[0];
	}

	/**
	 * {@code assignment_statement : variable ASSIGN expression ;}
	 * 
	 * @param assignment
	 * @return
	 */
	private String transformAssignment(Assignment assignment) {
		return toString(assignment.getLeft()) + WS + ASSIGN + WS + toString(assignment.getRight()) + SEMICOLON;
	}

	/**
	 * {@code if_statement_block : if_statement (elseif_statement)* (else_statement)? END_IF ;}
	 * 
	 * @param _if
	 * @return
	 */
	private String transformIf(If _if) {
		String ifString = new String();
		for (int i = 0; i < _if.getConditionalBlocks().size(); i++) {
			ConditionalBlock conditionalBlock = _if.getConditionalBlocks().get(i);
			if (i == 0) {
				ifString += transformConditionalBlock(IF, conditionalBlock);
			} else if (i > 0 && !conditionalBlock.isElse()) {
				ifString += NEWLINE + transformConditionalBlock(ELSIF, conditionalBlock);
			} else {
				ifString += NEWLINE + transformConditionalBlock(ELSE, conditionalBlock);
			}
		}
		ifString += NEWLINE + END_IF;
		return ifString;
	}
	
	/**
	 * {@code if_statement : IF expression THEN statement_list ;}<br>
	 * {@code elseif_statement : ELSIF expression THEN statement_list ;}<br>
	 * {@code else_statement : ELSE statement_list ;}
	 * 
	 * @param conditionKeyword
	 * @param conditionalBlock
	 * @return
	 */
	private String transformConditionalBlock(String conditionKeyword, ConditionalBlock conditionalBlock) {
		String conditionalBlockString = new String();
		conditionalBlockString += conditionKeyword;
		if (!conditionalBlock.isElse()) {
			conditionalBlockString += WS + toString(conditionalBlock.getCondition()) + WS + THEN;
		}
		conditionalBlockString += NEWLINE;
		conditionalBlockString += apply(conditionalBlock.getSubstatements());
		
		return conditionalBlockString;
	}

	/**
	 * {@code case_statement_block : CASE expression OF case_element+ else_statement? END_CASE ;}
	 * 
	 * @param _case
	 * @return
	 */
	private String transformCase(Case _case) {
		String caseString = new String();
		caseString += CASE + WS + toString(_case.getSwitch()) + WS + OF + NEWLINE;
		for (CaseBlock caseBlock : _case.getCases()) {
			caseString += transformCaseBlock(caseBlock) + NEWLINE;			
		}
		caseString += END_CASE;
		
		return caseString;
	}

	/**
	 * {@code case_element : case_list_element (COMMA case_list_element)* COLON statement_list ;}
	 * 
	 * @param caseBlock
	 * @return
	 */
	private String transformCaseBlock(CaseBlock caseBlock) {
		String caseBlockString = new String();
		
		List<String> caseExprs = caseBlock.getCaseExpressions().stream().map(expr -> toString(expr)).collect(Collectors.toList());
		caseBlockString += String.join(COMMA + WS, caseExprs) + COLON + NEWLINE;
		caseBlockString += apply(caseBlock.getSubstatements());
		
		return caseBlockString;
	}

	/**
	 * {@code function_call_statement : (variable DOT)? function_call ;}
	 * 
	 * @param functionCall
	 * @return
	 */
	private String transformFunctionCall(FunctionCallStatement functionCall) {
		String functionCallString = new String();
		if (functionCall.getInvokingVariable() != null) {
			functionCallString += toString(functionCall.getInvokingVariable()) + DOT;
		}
		functionCallString += toString(functionCall.getFunctionCall()) + SEMICOLON;
		return functionCallString;
	}

	/**
	 * {@code while_statement : WHILE expression DO statement_list END_WHILE ;}<br>
	 * {@code repeat_statement : REPEAT statement_list UNTIL expression END_REPEAT ;}
	 * 
	 * @param unboundedLoop
	 * @return
	 */
	private String transformUnboundedLoop(UnboundedLoop unboundedLoop) {
		String unboundedLoopString = new String();
		
		String expressionString = toString(unboundedLoop.getCondition());
		String statementListString = apply(unboundedLoop.getSubstatements());
		if (unboundedLoop.getStatementType() == StatementType.WHILE) {
			unboundedLoopString += WHILE + WS + expressionString + WS + DO + NEWLINE;
			unboundedLoopString += statementListString + NEWLINE;
			unboundedLoopString += END_WHILE;
		} else if (unboundedLoop.getStatementType() == StatementType.REPEAT) {
			unboundedLoopString += REPEAT + NEWLINE;
			unboundedLoopString += statementListString + NEWLINE;
			unboundedLoopString += UNTIL + WS + expressionString + WS + END_REPEAT;
		} else {
			throw new RuntimeException("An unbounded loop can only be one of 2 statement types: while or repeat.");
		}
		
		return unboundedLoopString;
	}

	/**
	 * {@code for_statement : for_header DO statement_list END_FOR ;} <br>
	 * {@code for_header : FOR expression ASSIGN initial_value TO upper_bound (BY increment)? ;}
	 * 
	 * @param forLoop
	 * @return
	 */
	private String transformForLoop(ForLoop forLoop) {
		String forLoopString = new String();
		forLoopString += FOR + WS + toString(forLoop.getCounter()) + WS + ASSIGN + WS + forLoop.getInitialValue() + WS; 
		forLoopString += TO + forLoop.getUpperBound() + WS + BY + WS + forLoop.getIncrement() + WS + DO + NEWLINE;
		forLoopString += apply(forLoop.getSubstatements()) + NEWLINE;
		forLoopString += END_FOR;
		
		return forLoopString;
	}

	private String transformExitStatement(ExitStatement exit) {
		return EXIT + SEMICOLON;
	}

	private String transformReturn(Return _return) {
		return RETURN + SEMICOLON;
	}
	
	private String toString(Expression expr) {
		return stExpressionToStringExporter.apply(expr);
	}

	@Override
	public boolean canTransform(Object object) {
		if (object == null) {
			return false;
		}
		
		if (object instanceof List) {
			List<?> objects = (List<?>) object;
			return !objects.isEmpty() && objects.stream().allMatch(obj -> canTransform(obj));
		} else {
			return supportedTypes.contains(object.getClass());
		}
	}

}

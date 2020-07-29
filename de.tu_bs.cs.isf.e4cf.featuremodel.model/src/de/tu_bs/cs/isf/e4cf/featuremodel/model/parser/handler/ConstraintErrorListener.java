package de.tu_bs.cs.isf.e4cf.featuremodel.model.parser.handler;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 * This error listener stores a list of syntax error that occurs during the parsing process.
 * @author Kamil Rosiak
 *
 */
public class ConstraintErrorListener extends BaseErrorListener {
	private List<ParserError> errorList = new ArrayList<ParserError>();

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
			int charPositionInLine, String msg, RecognitionException e) {
		errorList.add(new ParserError(line, charPositionInLine, msg));
		super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
	}
	
	public List<ParserError> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<ParserError> errorCharPositions) {
		this.errorList = errorCharPositions;
	}
}

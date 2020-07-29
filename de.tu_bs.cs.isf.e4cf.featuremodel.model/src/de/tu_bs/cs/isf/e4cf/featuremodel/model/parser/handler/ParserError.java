package de.tu_bs.cs.isf.e4cf.featuremodel.model.parser.handler;

/**
 * This class stores errors that can occur during the parsing process.
 */
public class ParserError {
	private int line;
	private int charPositionInLine;
	private String msg;
	
	public ParserError(int line, int charPos, String msg) {
		setLine(line);
		setCharPositionInLine(charPos);
		setMsg(msg);
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getCharPositionInLine() {
		return charPositionInLine;
	}

	public void setCharPositionInLine(int charPositionInLine) {
		this.charPositionInLine = charPositionInLine;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}

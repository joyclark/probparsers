package de.be4.classicalb.core.parser;

import de.be4.classicalb.core.parser.node.Token;

public class Pragma {
	private final int line;
	private final int column;
	public final String content;
	private final Token pred;

	public Pragma(int line, int column, String content, Token pred) {
		this.line = line;
		this.column = column;
		this.content = content;
		this.pred = pred;
	}
	
	@Override
	public String toString() {
		return content + " ("+line+","+column+") pred:"+pred.getText();
	}
}
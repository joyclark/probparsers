package de.be4.classicalb.core.parser.analysis;

import de.be4.classicalb.core.parser.node.AAddExpression;
import de.be4.classicalb.core.parser.node.AArityExpression;
import de.be4.classicalb.core.parser.node.ABinExpression;
import de.be4.classicalb.core.parser.node.ABooleanFalseExpression;
import de.be4.classicalb.core.parser.node.ABooleanTrueExpression;
import de.be4.classicalb.core.parser.node.AIntegerExpression;
import de.be4.classicalb.core.parser.node.TInteger;
import de.be4.classicalb.core.parser.node.TIntegerLiteral;

public class ClassicalBPrinter extends DepthFirstAdapter {

	private final StringBuffer collector = new StringBuffer();

	@Override
	public void caseAAddExpression(AAddExpression node) {
		if (node.getLeft() != null) node.getLeft().apply(this);
		collector.append(" + ");
		if (node.getRight() != null) node.getRight().apply(this);
	}

	@Override
	public void caseTIntegerLiteral(TIntegerLiteral node) {
		collector.append(node.getText());
	}

	@Override
	public void caseABooleanFalseExpression(ABooleanFalseExpression node) {
		collector.append(" FALSE ");
	}

	@Override
	public void caseABooleanTrueExpression(ABooleanTrueExpression node) {
		collector.append(" TRUE ");
	}

	@Override
	public void caseAArityExpression(AArityExpression node) {

		collector.append(" arity(");
		if (node.getExpression1() != null) node.getExpression1().apply(this);
		collector.append(",");
		if (node.getExpression2() != null) node.getExpression2().apply(this);
		collector.append(") ");
	}

	@Override
	public void caseABinExpression(ABinExpression node) {
		collector.append(" bin(");

		if (node.getExpression1() != null) node.getExpression1().apply(this);
		collector.append(",");
		if (node.getExpression2() != null) node.getExpression2().apply(this);
		collector.append(",");
		if (node.getExpression3() != null) node.getExpression3().apply(this);
		collector.append(") ");
	}
	
	

	public String getText() {
		return collector.toString();
	}

}

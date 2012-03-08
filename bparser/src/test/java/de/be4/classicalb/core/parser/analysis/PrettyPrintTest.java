package de.be4.classicalb.core.parser.analysis;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;

import test.PolySuite;
import test.PolySuite.Config;
import test.PolySuite.Configuration;

import de.be4.classicalb.core.parser.BParser;
import de.be4.classicalb.core.parser.exceptions.BException;
import de.be4.classicalb.core.parser.node.Start;

@RunWith(PolySuite.class)
public class PrettyPrintTest {

	private final String expression;

	public PrettyPrintTest(String expression) {
		this.expression = expression;
	}

	static String[] testExpression = { "4 + 5", " arity(1,2) " };

	@Test
	public void genericTest() throws Exception {

		String t = "#EXPRESSION " + expression;
		Start parse = BParser.parse(t);
		ClassicalBPrinter classicalBPrinter = new ClassicalBPrinter();
		parse.apply(classicalBPrinter);
		assertEquals(expression, classicalBPrinter.getText());
	}

	@Config
	public static Configuration getConfig() {
		return new Configuration() {

			public int size() {
				return testExpression.length;
			}

			public String getTestValue(int index) {
				return testExpression[index];
			}

			public String getTestName(int index) {
				return testExpression[index];
			}
		};
	}

}

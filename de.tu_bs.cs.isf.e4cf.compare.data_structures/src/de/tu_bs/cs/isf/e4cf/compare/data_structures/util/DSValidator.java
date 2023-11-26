package de.tu_bs.cs.isf.e4cf.compare.data_structures.util;

import java.util.concurrent.atomic.AtomicBoolean;

import com.github.javaparser.ast.validator.Java15Validator;
import com.github.javaparser.ast.validator.ProblemReporter;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writer.JavaWriter2;

/**
 * Static Helper class helping in validating Node Trees by parsing them back to
 * their original format and running static analysis in some form.
 */
public class DSValidator {
	
	public static boolean VERBOSE = false;
	private final static String INFO_PREFIX = "  DSValidator-Info: ";

	/**
	 * Receives an e4CF node and selects a suitable parser for transformation
	 * @param root node of the e4 Tree to check
	 * @return true iff a format can be determined and the syntax seems correct
	 */
	public static boolean checkSyntax(Node root) {
		
		switch (root.getNodeType()) {
		case "JAVA":
			return checkJavaSyntax(root);
		default:
			//System.out.println(INFO_PREFIX + "No Syntax Validator Available for " + root.getNodeType());
			return false;
		}

	}
	
	/**
	 * Performs a Syntax check using the GitHub JavaParser
	 * @param root e4 Tree to check
	 * @return true iff the file can be parsed and seems syntactically correct
	 */
	public static boolean checkJavaSyntax(Node root) {
		try {
			com.github.javaparser.ast.Node jpNode = new JavaWriter2().parseE4Tree(null, root);
			// FIXME One should probably determine the JavaVersion from a tree, lets assume the newest version?
			Java15Validator validator = new Java15Validator();
			AtomicBoolean isCorrect = new AtomicBoolean(true);
			ProblemReporter reporter = new ProblemReporter(p -> {
				String messageTrim = p.getVerboseMessage();
				print(INFO_PREFIX + "Validation Problem: " + messageTrim);
				isCorrect.set(false);
			});
			validator.accept(jpNode, reporter);
			
			return isCorrect.get();
		} catch (Exception e) {
			System.err.println(INFO_PREFIX + "Encountered an Exception while parsing: " + e.getClass().getSimpleName());
		}
		return false;
	}
	
	private static void print(Object o) {
		if (VERBOSE) {
			System.out.println(o.toString());
		}
	}
	
}

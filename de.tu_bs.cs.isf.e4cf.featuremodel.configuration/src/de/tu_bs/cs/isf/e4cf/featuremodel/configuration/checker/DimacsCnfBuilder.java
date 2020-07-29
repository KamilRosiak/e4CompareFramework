package de.tu_bs.cs.isf.e4cf.featuremodel.configuration.checker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DimacsCnfBuilder {

	private static final String CNF_FORMAT = "cnf";
	private static final String CLAUSE_LINE_POSTFIX = "0";
	public static String COMMENT_LINE_PREFIX = "c";
	public static String PROBLEM_LINE_PREFIX = "p";
	
	protected boolean checked;
	
	protected int varCount;

	protected List<String> comments;
	protected List<List<Integer>> clauses;
	
	protected Set<Integer> variables;
	
	public DimacsCnfBuilder() {
		checked = false;
		comments = new ArrayList<>();
		clauses = new ArrayList<>();
		variables = new HashSet<Integer>();
	}
	
	public void addComment(String comment) {
		comments.add(comment);
		
		checked = false;
	}
	
	public void addClause(List<Integer> clause) {
		if (clause == null || clause.isEmpty()) {
			return;
		}
		
		for (int literal : clause) {
			if (literal == 0) {
				throw new IllegalArgumentException("Clause contains a 0 literal.");
			}
		}

		for (int literal : clause) {
			variables.add(Math.abs(literal));			
		}
		
		clauses.add(new ArrayList<>(clause));
		
		
		checked = false;
	}

	public String print() {
		StringBuilder sb = new StringBuilder();
		
		// add comment lines 
		comments.forEach(comment -> sb.append(createCommentLine(comment)));
		
		// add problem line
		sb.append(createProblemLine());
		
		// add clause lines
		clauses.forEach(clause -> sb.append(createClauseLine(clause)));
		
		return sb.toString();
	}
	
	private String createCommentLine(String comment) {
		return String.join(" ", COMMENT_LINE_PREFIX, comment, "\n");
	}
	
	private String createProblemLine() {
		String format = CNF_FORMAT;
		String variableCount = Integer.toString(variables.size());
		String clauseCount = Integer.toString(clauses.size());
		return String.join(" ", PROBLEM_LINE_PREFIX, format, variableCount, clauseCount, "\n");
	}
	
	private String createClauseLine(List<Integer> clause) {
		List<String> clauseLine = clause.stream()
				.map(i -> Integer.toString(i))
				.collect(Collectors.toList());
		clauseLine.add(CLAUSE_LINE_POSTFIX);
		clauseLine.add("\n");
		
		return String.join(" ", clauseLine);
	}
	
	public boolean checkFormat() {
		checked = true;
		
		// check if all variables in [0, maxVarValue] are present in at least one clause 
		Optional<Integer> maxVarValue = variables.stream().max(Integer::compare);
		if (maxVarValue.orElse(0) > variables.size()) {
			checked = false;
		}
		
		// check for empty clauses
		if (clauses.stream().anyMatch(clause -> clause.size() <= 0)) {
			checked = false;
		}
		
		return checked;
	}
}

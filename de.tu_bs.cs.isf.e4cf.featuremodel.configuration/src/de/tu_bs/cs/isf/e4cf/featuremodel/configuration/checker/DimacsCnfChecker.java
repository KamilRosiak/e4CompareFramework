package de.tu_bs.cs.isf.e4cf.featuremodel.configuration.checker;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramm;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.helper.FeatureDiagramIterator;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfiguration;

public class DimacsCnfChecker implements FeatureConfigurationChecker {

	protected DimacsCnfBuilder dimacsCnfBuilder;
	protected FeatureConfiguration config;
	protected Map<Feature, Integer> featureToVariable;
	
	protected boolean isInit;
	protected boolean isChecked;
	protected boolean checkResult;
	
	public DimacsCnfChecker() {
		this.dimacsCnfBuilder = new DimacsCnfBuilder();
		this.featureToVariable = new HashMap<>();
		this.isInit = false;
		this.isChecked = false;
		this.checkResult = false;
	}
	
	@Override
	public void initialize(FeatureConfiguration fc) {
		config = fc;
		if (config == null || config.getFeatureDiagram() == null) {
			isInit = false;
			return;
		}
		FeatureDiagramm fd = config.getFeatureDiagram();
		
		// assign each feature to an integer
		featureToVariable.clear();
		for (int i = 0; i < config.getFeatureSelection().size(); i++) {
			Feature feature = config.getFeatureSelection().get(i).getKey();
			featureToVariable.put(feature, i+1);
		}
		
		// formulate a satisfiability problem in CNF in the DIMACS format
		for (Iterator<Feature> iterator = new FeatureDiagramIterator(fd); iterator.hasNext();) {
			Feature feature = iterator.next();
			deriveClauses(feature);
		}		
		
		printProblem();
		
		isInit = true;
	}

	private void printProblem() {
		System.out.println("Checking Feature Selection for "+config.getFeatureDiagram().getRoot().getName());
		System.out.println("Feature Mapping: ");
		featureToVariable.forEach((feature, variable) -> System.out.println("\t > "+feature.getName()+" -> "+variable));
		
		System.out.println("Clauses: ");
		for (List<Integer> clause : dimacsCnfBuilder.clauses) {
			String clauseString = String.join(", ", clause.stream().map(variable -> variable.toString()).collect(Collectors.toList()));
			System.out.println("\t > ("+clauseString+")");
		}
		System.out.println("");
	}

	@Override
	public OperationState check() {
		OperationState opState;
		if (isInit) {
			String dimacsCnfString = dimacsCnfBuilder.print();
			
			ISolver solver = SolverFactory.newDefault();
	        solver.setTimeout(100); // 100s timeout
	        
	        Reader reader = new DimacsReader(solver);
	        try {
	        	InputStream is = new ByteArrayInputStream(dimacsCnfString.trim().getBytes());
	            IProblem problem = reader.parseInstance(is);
	            
	            checkResult = problem.isSatisfiable();
				opState = OperationState.SUCCESS;
			} catch (TimeoutException e) {
				opState = OperationState.TIMEOUT;
	        } catch (FileNotFoundException e) {
	        	opState = OperationState.IO_ERROR;
	        	opState.setInfo("File not found.");
	        } catch (ParseFormatException e) {
	            opState = OperationState.PARSE_ERROR;
	            opState.setInfo("Dimacs Parsing Failed: \n\t > "+e.getMessage());
	        } catch (IOException e) {
	            opState = OperationState.IO_ERROR;
	            opState.setInfo(e.getMessage());
	        } catch (ContradictionException e) {
	            opState = OperationState.SUCCESS;
	            opState.setInfo("Feature configuration is invalid due to at least two contradictory clauses.");
	        }
	        
	        isChecked = true;
		} else {
			opState = OperationState.PRECONDITION_ERROR;
			opState.setInfo("The dimacs cnf checker has not been initialized.");			
		}
		
		return opState;
	}

	@Override
	public boolean getResult() {
		if (isChecked) {
			return checkResult;			
		} else {
			throw new RuntimeException("The dimacs cnf checker has not been initialized.");
		}
	}


	/**
	 * Derives a CNF from a feature. It adds the clauses directly to the DIMACS builder instance. 
	 * 
	 * @param feature
	 */
	private void deriveClauses(Feature feature) {
		int featureAtom = featureToVariable.get(feature);
		boolean featureSelected = isSelected(feature);
		
		// add negative unary clause for deselection
		if (!featureSelected) {
			dimacsCnfBuilder.addClause(Arrays.asList(-featureAtom));
		} else {
			// add feature and parent feature facts if the feature is selected but the parent feature is not, thus invalidating the clause
			Feature parentFeature = feature.getParent();
			if (parentFeature != null) {
				int parentFeatureAtom = featureToVariable.get(parentFeature);
				dimacsCnfBuilder.addClause(Arrays.asList(parentFeatureAtom));
				dimacsCnfBuilder.addClause(Arrays.asList(featureAtom));				
			}
				
			if (isSelected(feature) && feature.isMandatory()) {
				// add positive unary clause for selection or mandatory state
				dimacsCnfBuilder.addClause(Arrays.asList(featureAtom));				
			}
		}
		
		// if feature selected and child mandatory, add implication as clause
		for (Feature childFeature : feature.getChildren()) {
			if (childFeature.isMandatory()) {
				int childFeatureAtom = featureToVariable.get(childFeature);
				dimacsCnfBuilder.addClause(Arrays.asList(-featureAtom, childFeatureAtom));
			}
		}
		
		// derive alternative clauses from children
		if (featureSelected && feature.isAlternative()) {
			List<List<Integer>> alternativeClauses = createAlternativeClauses(feature.getChildren());
			for (List<Integer> altClause : alternativeClauses) {
				dimacsCnfBuilder.addClause(altClause);			
			}			
		}
		
		// derive or clause from children
		if (featureSelected && feature.isOr()) {
			List<Integer> childVariables = new ArrayList<>();
			for (Feature childFeature : feature.getChildren()) {
				childVariables.add(featureToVariable.get(childFeature));
			}
			dimacsCnfBuilder.addClause(childVariables);			
		}
		
		// derive and clauses from children
		if (featureSelected && !feature.isOr() && !feature.isAlternative()) {
			for (Feature childFeature : feature.getChildren()) {
				dimacsCnfBuilder.addClause(Arrays.asList(featureToVariable.get(childFeature)));
			}
		}
	}

	private List<List<Integer>> createAlternativeClauses(List<Feature> features) {
		List<List<Integer>> alternativeClauses = new ArrayList<>();
		
		// get feature atoms
		int[] childVariables = new int[features.size()];
		for (int i = 0; i < features.size(); i++) {
			childVariables[i] = featureToVariable.get(features.get(i));
		}
		
		// add clause that enforces to pick at least one feature
		List<Integer> allPositiveClause = new ArrayList<>();
		for (int i = 0; i < childVariables.length; i++) {
			allPositiveClause.add(childVariables[i]);
		}
		alternativeClauses.add(allPositiveClause);
		
		// add clause that enforces that no pair of features are selected at the same time
		for (int i = 0; i < childVariables.length; i++) {
			for (int j = i+1; j < childVariables.length; j++) {
				List<Integer> pairClause = new ArrayList<>();
				pairClause.add(-childVariables[i]);
				pairClause.add(-childVariables[j]);
				alternativeClauses.add(pairClause);
			}
		}
		
		return alternativeClauses;
	}

	private boolean isSelected(Feature feature) {
		if (config != null) {
			Boolean featureSelected = config.getFeatureSelection().get(feature);
			if (featureSelected != null && featureSelected.booleanValue()) {
				return true;
			}
		}
		return false;
	}
}

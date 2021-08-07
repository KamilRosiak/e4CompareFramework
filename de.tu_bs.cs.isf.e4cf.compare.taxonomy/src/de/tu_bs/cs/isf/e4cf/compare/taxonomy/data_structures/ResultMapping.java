/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures;

import java.util.ArrayList;
import java.util.List;

/**
 * @author developer-olan
 *
 */
public class ResultMapping {
	private String keyFragment;
	private List<SimpleResult> resultList; 
	
	public ResultMapping(String _keyFragment, String _leftNodeSignature, String rightNodeSignature, float _similarity) {
		this.keyFragment = _keyFragment;
		this.resultList = new ArrayList<SimpleResult>();
		this.resultList.add(new SimpleResult(_leftNodeSignature, rightNodeSignature, _similarity));
	}

	public String getMappingKey() {
		return keyFragment;
	}
	
	public List<SimpleResult> getMappedResults() {
		return resultList;
	}
	
	public void addResultListToKey(SimpleResult newSimpleResult) {
		this.resultList.add(newSimpleResult);
	}
	
	public <K> void sortResultsBySimilarity() {
		resultList.sort((first, second) -> {
			//Multiply with -1 to sort descending 
			return -1 * Float.compare(first.getSimilarity(), second.getSimilarity());
		});
	}
	
	
}

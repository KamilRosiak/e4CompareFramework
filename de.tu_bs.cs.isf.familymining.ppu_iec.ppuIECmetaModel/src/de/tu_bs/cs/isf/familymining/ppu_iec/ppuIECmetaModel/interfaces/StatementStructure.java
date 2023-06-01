package de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.Statement;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtextexpression.Expression;

public interface StatementStructure {
	
	/**
	 * Utility class for facilitated extraction of elements from the results generated by {@link StatementStructure#getExpressions(Statement)}
	 * and {@link StatementStructure#getSubstatements(Statement)}. The internal map pairs a string and a list of arbitrary elements denoted 
	 * by <b>T</b>.
	 * 
	 * @author Oliver Urbaniak
	 *
	 * @param <T>
	 */
	public static class StructuredMap<T> {
		private Map<String, List<T>> labelledLists;
		
		public StructuredMap(Map<String, List<T>> labelledLists) {
			this.labelledLists = labelledLists;
		}
		
		/**
		 * Retrieves the list of instances of type <b>T</b> stored under <i>key</i>, similar to {@link Map#get(Object)}. 
		 * 
		 * @param key
		 * @return
		 */
		public List<T> get(String key) {
			return labelledLists.containsKey(key) ? labelledLists.get(key) : null;
		}
		
		/**
		 * Retrieves a concrete instance denoted by <i>index</i> out of the list with the key <i>key</i>
		 * 
		 * @param key
		 * @param index
		 * @return
		 */
		public T get(String key, int index) {
			if (labelledLists.containsKey(key)) {
				List<T> singleList = labelledLists.get(key);
				return singleList.size() > index ? singleList.get(index) : null;		
			}
			return null;
		}
		
		public List<T> getValues() {
			List<T> values = new ArrayList<T>();

			labelledLists.values().forEach(values::addAll);
			return values;
		}
		
		public int keySize() {
			return labelledLists.keySet().size();
		}
		
		public boolean isEmpty() {
			return labelledLists.isEmpty();
		}
		
		public int size() {
			return labelledLists.size();
		}
	}
	
	/**
	 * Returns an indexed version of the key string. Necessary when retrieving specific non-unique structures in the map. 
	 * The key without index will retrieve the first block of the structure.
	 * 
	 * @param key one of the predefined keys
	 * @param index zero-based index
	 * @return indexed key string
	 */
	public String getIndexedKey(String key, int index);
	
	/**
	 * Returns a mapping from statement block keys to the corresponding statements. The keys are defined in derived classes. When addressing 
	 * non-unique structures (e.g. case or elsif) provide an indexed key using {@link StatementStructure#getIndexedKey(String, int)}. 
	 * The key without index will retrieve the first block of statements.
	 * 
	 * @param s statement
	 * @return labelled substatements
	 */
	public StructuredMap<Statement> getSubstatements(Statement s);
	
	/**
	 * Returns a mapping from expression keys to the corresponding expressions. The keys are defined in derived classes. When addressing
	 * expressions in non-unique statement structures (e.g. case or elsif) provide an indexed key using {@link StatementStructure#getIndexedKey(String, int)}.
	 * The key without index will retrieve the first expression in the statement structure.
	 * 
	 * @param s statement
	 * @return labelled expressions
	 */
	public StructuredMap<Expression> getExpressions(Statement s);
}
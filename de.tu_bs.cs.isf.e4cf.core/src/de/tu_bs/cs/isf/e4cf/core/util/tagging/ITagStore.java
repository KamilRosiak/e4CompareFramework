package de.tu_bs.cs.isf.e4cf.core.util.tagging;

import java.util.List;
import java.util.Map;

public interface ITagStore {

	/**
	 * Load the available tags
	 * @return loaded available tags
	 */
	public List<Tag> loadAvailableTags();
	
	/**
	 * Store the available tags
	 * @param availableTags tags to store
	 */
	public void storeAvailableTags(List<Tag> availableTags);
	

	/**
	 * Load a map of tags for each file
	 * mapping a workspace file path as key
	 * to a list of tags for that path
	 * @param availableTags because we only load existing tags
	 * @return loaded map with tags
	 */
	public Map<String, List<Tag>> loadTagMap(List<Tag> availableTags);
	
	/**
	 * Store a map of tags for each file
	 * mapping a workspace file path as key
	 * to a list of tags for that path
	 * @param tagMap map of tags to store
	 */
	public void storeTagMap(Map<String, List<Tag>> tagMap);

}

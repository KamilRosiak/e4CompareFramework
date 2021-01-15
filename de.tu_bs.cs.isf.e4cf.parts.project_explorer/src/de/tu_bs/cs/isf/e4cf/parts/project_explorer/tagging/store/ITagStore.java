package de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.store;

import java.util.List;
import java.util.Map;

import de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.Tag;

public interface ITagStore {

	/**
	 * Load the available tags
	 * @return available tags
	 */
	public List<Tag> loadAvailableTags();
	
	/**
	 * Store the available tags
	 * @param availableTags
	 */
	public void storeAvailableTags(List<Tag> availableTags);
	
	/**
	 * Load a map of tags for each file
	 * @return map with tags
	 */
	public Map<String, List<Tag>> loadTagMap(List<Tag> availableTags);
	
	/**
	 * Store a map of tags for each file
	 * @param tagMap
	 */
	public void storeTagMap(Map<String, List<Tag>> tagMap);

}

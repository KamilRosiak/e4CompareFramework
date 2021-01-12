package de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging;

import java.util.List;
import java.util.Map;

public interface ITagStore {

	public List<Tag> loadAvailableTags();
	
	public void storeAvailableTags(List<Tag> availableTags);
	
	public Map<String, List<Tag>> loadTaggedFileTreeElements(List<Tag> availableTags);
	
	public void storeTaggedFileTreeElements(Map<String, List<Tag>> tagMap);

}

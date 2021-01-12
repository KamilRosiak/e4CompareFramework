package de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

public class TagStore {
	
	// TODO store in properties, cashing
	private List<String> availableTags;
	
	public static final String TAG_1 = "tag1";
	public static final String TAG_2 = "tag2";
	
	private List<String> treeElementTags;
	
	public TagStore() {
		// Mock tags
		availableTags = new ArrayList<String>();
		availableTags.add(TAG_1);
		availableTags.add(TAG_2);
		
		treeElementTags = new ArrayList<String>();
		treeElementTags.add(TAG_1);
	}
	
	// Available tags
	public List<String> getAvailableTags() {
		return availableTags;
	}
	
	public void addAvailableTag(String tag) {
		availableTags.add(tag);
	}
	
	public void delteAvailableTag(String tag) {
		getAvailableTags().remove(tag);
	}
	
	// Tags of a concrete element
	public List<String> getTags(FileTreeElement treeElement) {
		return treeElementTags;
	}
	
	public void addTag(FileTreeElement treeElement, String tag) {
		treeElementTags.add(tag);
	}
	
	public void deleteTag(FileTreeElement treeElement, String tag) {
		treeElementTags.remove(tag);
	}
}

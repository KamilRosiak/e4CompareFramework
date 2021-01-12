package de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import javafx.scene.paint.Color;

@Creatable
@Singleton
public class TagStore {
	
	// TODO store in properties, caching
	private List<Tag> availableTags;
	
	public static final Tag TAG_1 = new Tag("tag1", Color.RED);
	public static final Tag TAG_2 = new Tag("tag2", Color.BLUE);
	
	private List<Tag> treeElementTags;
	
	public void init() {
		// Mock tags
		availableTags = new ArrayList<Tag>();
		availableTags.add(TAG_1);
		availableTags.add(TAG_2);
		
		treeElementTags = new ArrayList<Tag>();
		treeElementTags.add(TAG_1);
		treeElementTags.add(TAG_2);
	}
	
	// Available tags
	public List<Tag> getAvailableTags() {
		return availableTags;
	}
	
	public void addAvailableTag(Tag tag) {
		availableTags.add(tag);
	}
	
	public void delteAvailableTag(Tag tag) {
		getAvailableTags().remove(tag);
	}
	
	// Tags of a concrete element
	public List<Tag> getTags(FileTreeElement treeElement) {
		return treeElementTags;
	}
	
	public void addTag(FileTreeElement treeElement, Tag tag) {
		treeElementTags.add(tag);
	}
	
	public void deleteTag(FileTreeElement treeElement, Tag tag) {
		treeElementTags.remove(tag);
	}
}

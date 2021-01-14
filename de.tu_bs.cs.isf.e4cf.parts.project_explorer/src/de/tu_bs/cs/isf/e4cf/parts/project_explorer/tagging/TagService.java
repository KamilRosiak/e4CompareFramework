package de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.store.ITagStore;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.store.MockedTagStore;

@Creatable
@Singleton
/**
 * Provides tagging capabilities for FileTreeElement. Should be synchronized
 * with the file system at startup
 */
public class TagService {

	public static final String TAG_PREFIX = ":";

	// Persists the tags
	private ITagStore tagStore;

	// Fields used for tagging
	private List<Tag> availableTags;
	private Map<String, List<Tag>> tagMap;

	/**
	 * Load data from the tagStore
	 */
	@PostConstruct
	private void load() {
		tagStore = new MockedTagStore();
		availableTags = tagStore.loadAvailableTags();
		tagMap = tagStore.loadTagMap();
	}

	/**
	 * Store data in the tagStore
	 */
	@PreDestroy
	private void store() {
		tagStore.storeAvailableTags(availableTags);
		tagStore.storeTagMap(tagMap);
	}

	/**
	 * Synchronize the tagService data with the current FS state: Create empty tag
	 * list for all unknown FileTreeElements and remove entries for FileTreeElements
	 * that are not longer present in the FS.
	 * 
	 * @param root FileTreeElement
	 */
	public void syncWithFileSystem(FileTreeElement root) {
		// Load the FS Tree and keep track of all file paths that exist
		ArrayList<String> paths = new ArrayList<String>();
		loadFileSystem(root, true, paths);

		// Delete files that do not exist anymore
		tagMap.keySet().removeIf(key -> !paths.contains(key));
	}

	/**
	 * Recursively iterate the FS, initialize tags and store seen paths
	 * 
	 * @param element the current FileTreeElement
	 * @param isRoot  if the current element is the root element
	 * @param paths   List to store seen paths
	 */
	private void loadFileSystem(FileTreeElement element, boolean isRoot, List<String> paths) {

		if (!isRoot) {
			paths.add(element.getRelativePath());
			// Initialize tags for each file
			List<Tag> tags = getTags(element);
			if (tags == null) {
				tags = new ArrayList<Tag>();
			} else {
				// Remove duplicates
				tags = new ArrayList<>(new HashSet<>(tags));
				// Delete tags that are not available
				tags.removeIf(tag -> !availableTags.contains(tag));
			}
			tagMap.put(element.getRelativePath(), tags);
		}

		// Recursion
		for (FileTreeElement child : element.getChildren()) {
			loadFileSystem(child, false, paths);
		}
	}

	/** @return all available tags */
	public List<Tag> getAvailableTags() {
		return availableTags;
	}

	/**
	 * Add an available tag
	 * 
	 * @param tag
	 */
	public void addAvailableTag(Tag tag) {
		availableTags.add(tag);
	}

	/**
	 * Delete an available tag
	 * 
	 * @param tag
	 */
	public void delteAvailableTag(Tag tag) {
		getAvailableTags().remove(tag);

		// Remove all entries of the deleted tag
		for (List<Tag> tags : tagMap.values()) {
			if (tags.contains(tag)) {
				tags.remove(tag);
			}
		}
	}

	private List<Tag> saveGetListFromMap(String path) {
		List<Tag> tags = tagMap.get(path);
		if (tags == null) {
			tags = new ArrayList<Tag>();
			tagMap.put(path, tags);
		}
		return tags;
	}

	/**
	 * Get the tags for a FileTreeElement If no tags exists create an empty list
	 * 
	 * @param treeElement to get tags for
	 * @return List of all tags of the element
	 */
	public List<Tag> getTags(FileTreeElement treeElement) {		
		return saveGetListFromMap(treeElement.getRelativePath());
	}

	/**
	 * Check if a tag can be added to a tag list
	 * 
	 * @param tagList
	 * @param tag
	 * @return true if it is not in the list already and an available tag
	 */
	private boolean canAddTag(List<Tag> tagList, Tag tag) {
		return !tagList.contains(tag) && availableTags.contains(tag);
	}

	/**
	 * Add a list of tags for a treeElement
	 * 
	 * @param treeElement
	 * @param tags
	 */
	public void addTags(FileTreeElement treeElement, List<Tag> tags) {
		List<Tag> tagList = saveGetListFromMap(treeElement.getRelativePath());
		for (Tag tag : tags) {
			if (canAddTag(tagList, tag)) {
				tagList.add(tag);
			}
		}
	}

	/**
	 * Add a tag for a treeElement
	 * 
	 * @param treeElement
	 * @param tag
	 */
	public void addTag(FileTreeElement treeElement, Tag tag) {
		List<Tag> tagList = saveGetListFromMap(treeElement.getRelativePath());
		if (canAddTag(tagList, tag)) {
			tagList.add(tag);
		}
	}

	/**
	 * Delete a tag for a treeElement
	 * 
	 * @param treeElement
	 * @param tag
	 */
	public void deleteTag(FileTreeElement treeElement, Tag tag) {
		List<Tag> tagList = saveGetListFromMap(treeElement.getRelativePath());
		tagList.remove(tag);
	}

	/**
	 * Clear the tags of a treeElement
	 * 
	 * @param treeElement
	 */
	public void clearTags(FileTreeElement treeElement) {
		tagMap.get(treeElement.getRelativePath()).clear();
	}

	/**
	 * Check if the treeElement has the tag
	 * 
	 * @param treeElement
	 * @param tag
	 * @return true if the treeElement has the tag
	 */
	public boolean hasTag(FileTreeElement treeElement, Tag tag) {
		List<Tag> tagList = saveGetListFromMap(treeElement.getRelativePath());
		return tagList.contains(tag);
	}

	/**
	 * Check if the treeElement has all tags
	 * 
	 * @param treeElement
	 * @param tags
	 * @return true if the treeElement has the tags
	 */
	public boolean hasTags(FileTreeElement treeElement, List<Tag> tags) {
		List<Tag> tagList = saveGetListFromMap(treeElement.getRelativePath());
		return tagList.containsAll(tags);
	}
}

package de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

@Creatable
@Singleton
public class TagService {
	
	public static final String TAG_PREFIX = ":";
	
	private List<Tag> availableTags;
	private Map<String, List<Tag>> tagMap;
	
	private ITagStore tagStore;
	
	public TagService() {
		tagStore = new PropertiesTagStore();
		availableTags = tagStore.loadAvailableTags();
		tagMap = tagStore.loadTaggedFileTreeElements(availableTags);
	}
	
	@PreDestroy
	private void store() {
		tagStore.storeAvailableTags(availableTags);
		tagStore.storeTaggedFileTreeElements(tagMap);
	}
	
	public void syncWithFileSystem(FileTreeElement root) {
		syncWithFileSystem(root, true);
	}
	
	private void syncWithFileSystem(FileTreeElement element, boolean isRoot) {
		// TODO: check if all stored tags and files still exists
		// if not delete them
		
		if(!isRoot) {
			List<Tag> tags = getTags(element);
			if(tags == null) {
				tagMap.put(element.getRelativePath(), new ArrayList<Tag>());
			}
		}
		
		for(FileTreeElement child : element.getChildren()) {
			syncWithFileSystem(child, false);
		}
	}
	
	public List<Tag> getAvailableTags() {
		return availableTags;
	}
	
	public void addAvailableTag(Tag tag) {
		availableTags.add(tag);
	}
	
	public void delteAvailableTag(Tag tag) {
		getAvailableTags().remove(tag);
		
		// Remove all entries of the deleted tag
		for (List<Tag> tags : tagMap.values()) {
			if (tags.contains(tag)) {
				tags.remove(tag);
			}
		}
	}
	
	// TODO: caching
	// NPE save
	public List<Tag> getTags(FileTreeElement treeElement) {
		String path = treeElement.getRelativePath();
		
		List<Tag> tags = tagMap.get(path);
		if(tags == null) {
			tags = new ArrayList<Tag>();
			tagMap.put(path, tags);
		}
		return tags;
	}
	
	public void addTag(FileTreeElement treeElement, Tag tag) {
		List<Tag> tagList = tagMap.get(treeElement.getRelativePath());
		tagList.add(tag);
	}
	
	public void deleteTag(FileTreeElement treeElement, Tag tag) {
		List<Tag> tagList = tagMap.get(treeElement.getRelativePath());
		tagList.remove(tag);
	}
}

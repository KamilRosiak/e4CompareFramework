package de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.Tag;
import javafx.scene.paint.Color;

public class MockedTagStore implements ITagStore {

	private Tag tag1 = new Tag("tag1", Color.ALICEBLUE);
	private Tag tag2 = new Tag("tag2", Color.BROWN);
	private Tag tag3 = new Tag("tag3", Color.DARKORANGE);
	private Tag tag4 = new Tag("tag4", Color.HOTPINK);
	
	@Override
	public List<Tag> loadAvailableTags() {
		ArrayList<Tag> availableTags = new ArrayList<Tag>();
		
		availableTags.add(tag1);
		availableTags.add(tag2);
		availableTags.add(tag3);
		availableTags.add(tag4);
		
		return availableTags;
	}

	@Override
	public void storeAvailableTags(List<Tag> availableTags) {
	}

	@Override
	public Map<String, List<Tag>> loadTagMap(List<Tag> availableTags) {
		HashMap<String, List<Tag>> tagMap = new HashMap<String, List<Tag>>();
		
		tagMap.put(" 01 RAW", new ArrayList<Tag>());
		tagMap.put(" 02 Trees", new ArrayList<Tag>(Arrays.asList(tag1, tag1)));
		tagMap.put(" 03 Metrics", new ArrayList<Tag>(Arrays.asList(tag1, tag2)));
		tagMap.put(" 04 Family Models", new ArrayList<Tag>(Arrays.asList(tag1, tag2, tag3)));
		tagMap.put(" 05 Feature Models", new ArrayList<Tag>(Arrays.asList(tag1, tag2, tag3, tag4)));
		
		return tagMap;
	}

	@Override
	public void storeTagMap(Map<String, List<Tag>> tagMap) {
	}

}

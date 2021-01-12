package de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import javafx.scene.paint.Color;

public class PropertiesTagStore implements ITagStore {

	private static final String AVAILABLE_TAGS_PROPERTIES_FILE = "available_tags.properties";
	private static final String TAG_MAP_PROPERTIES_FILE = "tag_map.properties";
	
	private static final String AVAILABLE_TAGS_PROPERTIES_COMMENT = "Map of all available tags and its colors";
	private static final String TAG_MAP_PROPERTIES_COMMENT = "Map of all available paths and its tags";
	
	private File availableTagsFile;
	private File tagMapFile;
	
	public PropertiesTagStore() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		IPath stateLoc = Platform.getStateLocation(bundle);
		
		availableTagsFile = stateLoc.append(AVAILABLE_TAGS_PROPERTIES_FILE).toFile();
		tagMapFile = stateLoc.append(TAG_MAP_PROPERTIES_FILE).toFile();
	}
	
	@Override
	public List<Tag> loadAvailableTags() {
		Properties availableTagsProperties = new Properties();
		
		if(availableTagsFile.exists()) {
			try {
				availableTagsProperties.load(new FileInputStream(availableTagsFile));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		ArrayList<Tag> availableTags = new ArrayList<Tag>();
		
		availableTagsProperties.forEach((name, color) -> {
			System.out.println(name + " " + color);
			availableTags.add(new Tag((String) name, Color.web((String) color)));
		});
		
		return availableTags;
	}

	@Override
	public void storeAvailableTags(List<Tag> availableTags) {
		Properties availableTagsProperties = new Properties();
		
		for (Tag tag : availableTags) {
			availableTagsProperties.put(tag.getName(), toHexString(tag.getColor()));
		}
		
		try {
			availableTagsProperties.store(new FileWriter(availableTagsFile), AVAILABLE_TAGS_PROPERTIES_COMMENT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String format(double val) {
		String in = Integer.toHexString((int) Math.round(val * 255));
		return in.length() == 1 ? "0" + in : in;
	}

	public String toHexString(Color value) {
		return "#" + (
				format(value.getRed()) + 
				format(value.getGreen()) + 
				format(value.getBlue()) + 
				format(value.getOpacity())
			).toUpperCase();
	}

	// TODO hash
	@Override
	public Map<String, List<Tag>> loadTaggedFileTreeElements(List<Tag> availableTags) {
		Properties tagMapProperties = new Properties();
		
		if(tagMapFile.exists()) {
			try {
				tagMapProperties.load(new FileInputStream(tagMapFile));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		HashMap<String, List<Tag>> tagMap = new HashMap<String, List<Tag>>();
		
		tagMapProperties.forEach((file, tags) -> {
			ArrayList<Tag> tagsOfEntry = new ArrayList<Tag>();
			
			String[] tagStrings = ((String) tags).split(TagService.TAG_PREFIX);
			
			if(tagStrings.length > 1) {
				for(int i = 1; i < tagStrings.length; i++) {
					for (Tag tag : availableTags) {
						if(tagStrings[i].equals(tag.getName())) {
							tagsOfEntry.add(tag);
							break;
						}
					}
				}
			}
			
			tagMap.put((String) file, tagsOfEntry);
		});
		
		return tagMap;
	}

	@Override
	public void storeTaggedFileTreeElements(Map<String, List<Tag>> tagMap) {
		Properties tagMapProperties = new Properties();
		
		for (Entry<String, List<Tag>> entry : tagMap.entrySet()) {
			String tags = TagService.TAG_PREFIX + entry.getValue().stream()
					.map(n -> n.getName())
					.collect(Collectors.joining(TagService.TAG_PREFIX));
			
			tagMapProperties.put(entry.getKey(), tags);
		}
		
		try {
			tagMapProperties.store(new FileWriter(tagMapFile), TAG_MAP_PROPERTIES_COMMENT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

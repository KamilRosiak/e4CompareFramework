package de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.Tag;

public class SerializableTagStore implements ITagStore {

	private static final String AVAILABLE_TAGS_FILE = "available_tags.ser";
	private static final String TAG_MAP_FILE = "tag_map.ser";

	private File availableTagsFile;
	private File tagMapFile;

	/**
	 * Default constructor that gets the file locations
	 */
	public SerializableTagStore() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		IPath stateLoc = Platform.getStateLocation(bundle);

		availableTagsFile = stateLoc.append(AVAILABLE_TAGS_FILE).toFile();
		tagMapFile = stateLoc.append(TAG_MAP_FILE).toFile();
	}
	
	/**
	 * Load serializable from file
	 * @param file
	 * @return serializable
	 */
	private Object loadFromFile(File file) {
		Object obj = null; 
		
		if(file.exists()) {
			try {
				ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
				obj = stream.readObject();
				stream.close();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
		
		return obj;
	}
	
	/**
	 * Store serializable in file
	 * @param file
	 * @param serializable
	 */
	private void storeToFile(File file, Object serializable) {
		ObjectOutputStream stream;
		try {
			stream = new ObjectOutputStream(new FileOutputStream(file));
			stream.writeObject(serializable);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tag> loadAvailableTags() {
		List<Tag> availableTags = (List<Tag>) loadFromFile(availableTagsFile);
		if(availableTags == null) {
			availableTags = new ArrayList<Tag>();
		}
		return availableTags;
	}

	@Override
	public void storeAvailableTags(List<Tag> availableTags) {
		storeToFile(availableTagsFile, availableTags);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<Tag>> loadTagMap(List<Tag> availableTags) {
		Map<String, List<Tag>> tagMap = new HashMap<String, List<Tag>>();
		
		Map<String, List<String>> stringMap = (Map<String, List<String>>) loadFromFile(tagMapFile);
		
		if(stringMap != null) {
			for (String key : stringMap.keySet()) {
				List<String> stringList = stringMap.get(key);
				List<Tag> tagList = new ArrayList<Tag>();
				
				for (String stringTag : stringList) {
					for (Tag tag : availableTags) {
						if (stringTag.equals(tag.getName())) {
							tagList.add(tag);
							break;
						}
					}
				}
				
				tagMap.put(key, tagList);
			}
		}
		
		return tagMap;
	}

	@Override
	public void storeTagMap(Map<String, List<Tag>> tagMap) {
		Map<String, List<String>> stringMap = new HashMap<String, List<String>>();
		
		for (String key : tagMap.keySet()) {
			List<String> stringList = new ArrayList<String>();
			
			for (Tag string : tagMap.get(key)) {
				stringList.add(string.getName());
			}
			
			stringMap.put(key, stringList);
		}
		
		storeToFile(tagMapFile, stringMap);
	}
}

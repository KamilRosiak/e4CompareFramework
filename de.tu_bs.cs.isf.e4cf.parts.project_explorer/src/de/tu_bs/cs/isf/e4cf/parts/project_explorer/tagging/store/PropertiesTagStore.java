package de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.store;

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

import de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.Tag;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.TagService;

/**
 * A ITagStore using properties files to persist tagging information
 */
public class PropertiesTagStore implements ITagStore {

	private static final String AVAILABLE_TAGS_PROPERTIES_FILE = "available_tags.properties";
	private static final String TAG_MAP_PROPERTIES_FILE = "tag_map.properties";

	private static final String AVAILABLE_TAGS_PROPERTIES_COMMENT = "Map of all available tags and its colors";
	private static final String TAG_MAP_PROPERTIES_COMMENT = "Map of all available paths and its tags";

	private File availableTagsFile;
	private File tagMapFile;
	/**
	 * Default constructor that gets the file locations
	 */
	public PropertiesTagStore() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		IPath stateLoc = Platform.getStateLocation(bundle);

		availableTagsFile = stateLoc.append(AVAILABLE_TAGS_PROPERTIES_FILE).toFile();
		tagMapFile = stateLoc.append(TAG_MAP_PROPERTIES_FILE).toFile();
	}

	/**
	 * Load properties from a properties file
	 * 
	 * @param properties to load
	 * @param file       to load from
	 */
	private void loadPropertiesFromFile(Properties properties, File file) {
		if (file.exists()) {
			try {
				properties.load(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Store properties in a properties file
	 * 
	 * @param properties to store
	 * @param file       to store into
	 * @param comment    file header comment
	 */
	private void storePropertiesToFile(Properties properties, File file, String comment) {
		try {
			properties.store(new FileWriter(file), comment);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Tag> loadAvailableTags() {
		Properties availableTagsProperties = new Properties();

		loadPropertiesFromFile(availableTagsProperties, availableTagsFile);

		ArrayList<Tag> availableTags = new ArrayList<Tag>();

		availableTagsProperties.forEach((name, color) -> {
			availableTags.add(new Tag((String) name, (String) color));
		});

		return availableTags;
	}

	@Override
	public void storeAvailableTags(List<Tag> availableTags) {
		Properties availableTagsProperties = new Properties();

		for (Tag tag : availableTags) {
			availableTagsProperties.put(tag.getName(), tag.getColorString());
		}

		storePropertiesToFile(availableTagsProperties, availableTagsFile, AVAILABLE_TAGS_PROPERTIES_COMMENT);
	}

	@Override
	public Map<String, List<Tag>> loadTagMap(List<Tag> availableTags) {
		Properties tagMapProperties = new Properties();

		loadPropertiesFromFile(tagMapProperties, tagMapFile);

		HashMap<String, List<Tag>> tagMap = new HashMap<String, List<Tag>>();

		tagMapProperties.forEach((file, tags) -> {
			ArrayList<Tag> tagsOfEntry = new ArrayList<Tag>();

			String[] tagStrings = ((String) tags).split(TagService.TAG_PREFIX);

			if (tagStrings.length > 1) {
				for (int i = 1; i < tagStrings.length; i++) {
					// Lookup the tag by it's name
					for (Tag tag : availableTags) {
						if (tagStrings[i].equals(tag.getName())) {
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
	public void storeTagMap(Map<String, List<Tag>> tagMap) {
		Properties tagMapProperties = new Properties();

		for (Entry<String, List<Tag>> entry : tagMap.entrySet()) {
			String tags = TagService.TAG_PREFIX + entry.getValue().stream().map(n -> n.getName())
					.collect(Collectors.joining(TagService.TAG_PREFIX));

			tagMapProperties.put(entry.getKey(), tags);
		}

		storePropertiesToFile(tagMapProperties, tagMapFile, TAG_MAP_PROPERTIES_COMMENT);
	}

}

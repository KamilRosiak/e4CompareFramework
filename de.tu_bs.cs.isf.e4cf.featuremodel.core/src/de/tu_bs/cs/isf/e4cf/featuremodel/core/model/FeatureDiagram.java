package de.tu_bs.cs.isf.e4cf.featuremodel.core.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;

public class FeatureDiagram implements Serializable {
	private static final long serialVersionUID = 868183902095568314L;
	
	private String name;
	private final UUID uuid = UUID.randomUUID();
	private IFeature root;
	
	public FeatureDiagram(String name, IFeature rootFeature) {
		this.name = name;
		this.root = rootFeature;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the rootFeature
	 */
	public IFeature getRoot() {
		return root;
	}

	/**
	 * @param rootFeature the rootFeature to set
	 */
	public void setRoot(IFeature rootFeature) {
		this.root = rootFeature;
	}

	/**
	 * @return the uuid
	 */
	public UUID getUuid() {
		return uuid;
	}
	
	public boolean contains(IFeature feature) {
		return FDUtil.DFS(root, feature);
	}
	
	public Set<IFeature> getAllFeatures() {
		Set<IFeature> allFeatures = new HashSet<>();
		FDUtil.DFSVisitor(root, allFeatures::add);
		return allFeatures;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, root, uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof FeatureDiagram))
			return false;
		FeatureDiagram other = (FeatureDiagram) obj;
		return Objects.equals(name, other.name) && Objects.equals(root, other.root)
				&& Objects.equals(uuid, other.uuid);
	}

	@Override
	public String toString() {
		return "FeatureDiagram [name=" + name + ", uuid=" + uuid + ", rootFeature=" + root + "]";
	}
	
	public static void writeToFile(File file,  FeatureDiagram diagram) throws IOException {
		FileOutputStream fileOutStream = new FileOutputStream(file);
		ObjectOutputStream objectOutStream = new ObjectOutputStream(fileOutStream);
		objectOutStream.writeObject(diagram);
		objectOutStream.flush();
		objectOutStream.close();
	}
	
	public void writeToFile(File file) throws IOException {
		if (file == null) { // ask for filename, file location
			file = new File(RCPContentProvider.getCurrentWorkspacePath() + getName() + ".fd");
		}
		FeatureDiagram.writeToFile(file, this);
	}
	
	public static FeatureDiagram loadFromFile() throws ClassNotFoundException, IOException {
		String location = RCPMessageProvider.getFilePathDialog("Select a feature model", "");
		if (location.isEmpty()) {
			throw new IOException("No file selected");
		}
		
		File file = new File(location);
		
		FileInputStream fileInStream = new FileInputStream(file);
		ObjectInputStream objectInStream = new ObjectInputStream(fileInStream);
		FeatureDiagram loadedDiagram = (FeatureDiagram) objectInStream.readObject();
		objectInStream.close();
		return loadedDiagram;
	}
	
	
	

}

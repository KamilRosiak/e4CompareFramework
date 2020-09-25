package de.tu_bs.cs.isf.e4cf.compare.data_structures.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;

public class TreeSerializer {
	
	/**
	 * this method serializes a Tree to a given path with a given file extension.
	 */
	public static void encodeTree(Tree artifact, String path, String fileExtension, Boolean overwritte) {
			String fileName = artifact.getTreeName();
	
			File file = new File(path +"/"+ fileName +"("+artifact.getArtifactType()+")."+ fileExtension);
			
			if(file.exists() && overwritte) {
				overwritte = RCPMessageProvider.questionMessage("file exists", "A result with this name already exists.");
			}
			
			if(overwritte) {
				try {
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
					oos.writeObject(artifact);
					oos.close();
					System.out.println("Artifact with the name "+fileName+" saved.");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}	
	}

	public static String buildFilename(String first, String second, String metricName) {
		return first+"_with_"+second+"_config_"+ metricName;
	}
	
	/**
	 * This method deserializes a instance of Tree
	 */
	public static Tree decodeTree(String path) {
		Tree tree = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
			Object obj = ois.readObject();
			ois.close();
			if(obj instanceof Tree) {
				tree = (Tree) obj;
			} else {
				RCPMessageProvider.errorMessage("Type error", "the selected file is no instance of Tree");
			}
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tree;
	}
}

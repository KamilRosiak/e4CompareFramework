package de.tu_bs.cs.isf.e4cf.compare.data_structures.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactWriter;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.string_table.DataStructureST;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;

/**
 * This class contains utility method to support in and output operratin on artifact trees.
 * @author Kamil Rosiak
 *
 */
public class ArtifactIOUtil {
	
	/**
	 * Returns all Artifact reader that extending the AtifactReader extension point. The extension point is defined in schema/ArtifactReader.exsd
	 */
	public static List<AbstractArtifactReader> getAllArtifactReader() {
		List<AbstractArtifactReader> reader = RCPContentProvider.<AbstractArtifactReader>getInstanceFromBundle(DataStructureST.ARTIFACT_READER_SYMBOLIC_NAME, DataStructureST.ARTIFACT_READER_EXTENSION);
		if(reader != null) {
			return reader;
		}
		return new ArrayList<AbstractArtifactReader>();
	}
	
	/**
	 * Returns all Artifact reader that extending the AtifactReader extension point. The extension point is defined in schema/ArtifactWriter.exsd
	 */
	public static List<AbstractArtifactWriter> getAllArtifactWriter() {
		List<AbstractArtifactWriter> writer = RCPContentProvider.<AbstractArtifactWriter>getInstanceFromBundle(DataStructureST.ARTIFACT_WRITER_SYMBOLIC_NAME, DataStructureST.ARTIFACT_WRITER_EXTENSION);
		if(writer != null) {
			return writer;
		}
		return new ArrayList<AbstractArtifactWriter>();
	}
	
	/**
	 * Returns the artifact writer for a given artifact type. The artifact type is the type of the root node.
	 */
	public static AbstractArtifactWriter getWriterForType(String artifactType) {

		for(AbstractArtifactWriter writer : getAllArtifactWriter()) {
			if(writer.getSuppotedNodeType().equals(artifactType)) {
				return writer;
			}
		}
		return null;
	}
	
	/**
	 * This method parses the given files if possible an returns a list of trees.
	 */
	public static List<Tree> parseArtifacts(List<FileTreeElement> files) {
	    List<Tree> artifacts = new ArrayList<Tree>();
	    for(FileTreeElement file : files) {
		Tree tree = null;
		tree = parseArtifact(file);
		if(tree != null) {
		    artifacts.add(tree);
		}
	    }
	    return artifacts;  
	}
	
	/**
	 * This method parsers a file into a tree. if no artifact reader is available for the specific artifact type it returns null.
	 */
	public static Tree parseArtifact(FileTreeElement file) {
	    for(AbstractArtifactReader reader : getAllArtifactReader()) {
		if(reader.isFileSupported(file)) {
		    return reader.readArtifact(file);
		}
	    }
	    return null;
	}
}

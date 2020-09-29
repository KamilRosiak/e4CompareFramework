package de.tu_bs.cs.isf.e4cf.compare.data_structures.util;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactWriter;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.string_table.DataStructureST;
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
}

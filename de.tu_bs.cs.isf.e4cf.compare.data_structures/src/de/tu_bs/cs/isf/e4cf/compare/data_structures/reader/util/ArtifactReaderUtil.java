package de.tu_bs.cs.isf.e4cf.compare.data_structures.reader.util;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.ArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.string_table.DataStructureST;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;

public class ArtifactReaderUtil {
	
	
	public static List<ArtifactReader> getAllArtifactReader() {
		List<ArtifactReader> reader = RCPContentProvider.<ArtifactReader>getInstanceFromBundle(DataStructureST.ARTIFACT_READER_SYMBOLIC_NAME, DataStructureST.ARTIFACT_READER_EXTENSION);
		return reader;
	}
	
	
}

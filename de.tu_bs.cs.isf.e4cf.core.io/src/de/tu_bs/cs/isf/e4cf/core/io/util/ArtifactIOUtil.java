package de.tu_bs.cs.isf.e4cf.core.io.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.io.interfaces.ArtifactReader;
import de.tu_bs.cs.isf.e4cf.core.io.interfaces.ArtifactWriter;
import de.tu_bs.cs.isf.e4cf.core.io.reader.python_reader.PythonFileReader;
import de.tu_bs.cs.isf.e4cf.core.io.string_table.DataStructureST;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;

/**
 * This class contains utility method to support in and output appertain on
 * artifact trees.
 * 
 * @author Kamil Rosiak
 *
 */
public class ArtifactIOUtil {

	/**
	 * Returns all Artifact reader that extending the AtifactReader extension point.
	 * The extension point is defined in schema/ArtifactReader.exsd
	 */
	public static List<ArtifactReader> getAllArtifactReader() {
		List<ArtifactReader> reader = RCPContentProvider.<ArtifactReader>getInstanceFromBundle(
				DataStructureST.ARTIFACT_READER_SYMBOLIC_NAME, DataStructureST.ARTIFACT_READER_EXTENSION);
		
		if (reader != null) {
			PythonFileReader pyReader = new PythonFileReader();
			reader.add(pyReader); //adding pythonReader, so python files can be parsed
			return reader;
		}
		return new ArrayList<ArtifactReader>();
	}
	
	/**
	 * Returns the artifact reader for a given artifact type. The artifact type is
	 * the type of the root node.
	 */
	public static ArtifactReader getReaderForType(FileTreeElement fte, IEclipseContext context) {
		List<ArtifactReader> readerList = getAllArtifactReader();
		readerList.forEach(reader -> {
			ContextInjectionFactory.inject(reader, context);
		});
		for (ArtifactReader reader : readerList) {
			if (reader.isFileSupported(fte)) {
				return reader;
			}
		}
		return null;
	}
	
	
	
	
	
	/**
	 * Returns all Artifact reader that extending the AtifactReader extension point.
	 * The extension point is defined in schema/ArtifactWriter.exsd
	 */
	public static List<ArtifactWriter> getAllArtifactWriter() {
		List<ArtifactWriter> writer = RCPContentProvider.<ArtifactWriter>getInstanceFromBundle(
				DataStructureST.ARTIFACT_WRITER_SYMBOLIC_NAME, DataStructureST.ARTIFACT_WRITER_EXTENSION);
		if (writer != null) {
			return writer;
		}
		return new ArrayList<ArtifactWriter>();
	}

	/**
	 * Returns the artifact writer for a given artifact type. The artifact type is
	 * the type of the root node.
	 */
	public static ArtifactWriter getWriterForType(String artifactType) {
		for (ArtifactWriter writer : getAllArtifactWriter()) {
			if (writer.getSuppotedNodeType().equals(artifactType)) {
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
		for (FileTreeElement file : files) {
			Tree tree = null;
			tree = parseArtifact(file);
			if (tree != null) {
				artifacts.add(tree);
			}
		}
		return artifacts;
	}

	/**
	 * This method parsers a file into a tree. if no artifact reader is available
	 * for the specific artifact type it returns null.
	 */
	public static Tree parseArtifact(FileTreeElement file) {
		for (ArtifactReader reader : getAllArtifactReader()) {
			if (reader.isFileSupported(file)) {
				return reader.readArtifact(file);
			}
		}
		return null;
	}

	/**
	 * TODO
	 */
	public static boolean writeArtifactToFile(Tree tree, String path) {
		for (ArtifactWriter writer : getAllArtifactWriter()) {
			if (writer.isFileSupported(tree.getFileExtension())) {
				writer.writeArtifact(tree, path + "." + tree.getFileExtension());
				return true;
			}
		}
		return false;
	}

}

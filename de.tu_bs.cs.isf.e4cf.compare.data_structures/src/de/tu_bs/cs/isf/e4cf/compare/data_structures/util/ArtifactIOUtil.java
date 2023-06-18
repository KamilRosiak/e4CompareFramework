package de.tu_bs.cs.isf.e4cf.compare.data_structures.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.ArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.ArtifactWriter;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.string_table.DataStructureST;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
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
		List<ArtifactReader> readers = RCPContentProvider.<ArtifactReader>getInstanceFromBundle(
				DataStructureST.ARTIFACT_READER_EXTENSION_ID, DataStructureST.ARTIFACT_READER_EXTENSION);

		return readers;
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
				DataStructureST.ARTIFACT_WRITER_EXTENSION_ID, DataStructureST.ARTIFACT_WRITER_EXTENSION);
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
	public static List<Tree> parseArtifacts(List<FileTreeElement> files, IEclipseContext context) {
		List<Tree> artifacts = new ArrayList<Tree>();
		for (FileTreeElement file : files) {
			Tree tree = null;
			tree = parseArtifact(file, context);
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
	public static Tree parseArtifact(FileTreeElement file, IEclipseContext context) {
		try {
			Tree tree = getReaderForType(file, context).readArtifact(file);
			if (tree != null) {
				return tree;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (!file.isDirectory()) {
			System.err.println("No ArtifactReader found for files of type: " + file.getExtension());
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

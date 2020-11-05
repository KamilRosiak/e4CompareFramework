package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader;

import java.nio.file.Paths;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;

/***
 * 
 * @author Serkan Acar
 * @author Hassan Smaoui
 * @author Pascal Blum
 * @author Paulo Haas
 *
 */

public class JavaReader extends AbstractArtifactReader {
	public final static String[] SUPPORTED_FILE_ENDINGS = {"java"};


	public JavaReader() {
		super(SUPPORTED_FILE_ENDINGS);
	}

	@Override
	public Tree readArtifact(FileTreeElement element) {
		return null;
	}
}

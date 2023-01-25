package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.arch;

import org.eclipse.emf.ecore.EObject;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.familymining.ppu_iec.rcp_e4.EMFModelLoader.impl.EMFModelLoader;

public class ArchReader extends AbstractArtifactReader {
	public final static String[] SUPPORTED_FILE_ENDINGS = { "arch" };

	public ArchReader() {
		super(SUPPORTED_FILE_ENDINGS);
	}

	@Override
	public Tree readArtifact(FileTreeElement fileTreeElement) {
		EObject model = EMFModelLoader.load(fileTreeElement.getAbsolutePath(), "arch");
		Node rootNode = new NodeImpl(NodeType.MODEL);
		return null;
	}

}

package de.tu_bs.cs.isf.e4cf.core.io.reader;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.io.util.ArtifactIOUtil;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.io.interfaces.ArtifactReader;

/**
 * This class manages all artifact reader and creates a tree if file can be
 * read.
 * 
 * @author Kamil Rosiak
 *
 */
@Singleton
@Creatable
public class ReaderManager {
	@Inject IEclipseContext context;

	public Tree readFile(FileTreeElement fte) {
		Tree tree = new TreeImpl(fte.getFileName(), readFileRecursivly(null, fte));
		if (fte.isDirectory()) {
			tree.setFileExtension("DIRECTORY");
		} else {
			tree.setFileExtension(fte.getExtension());
		}

		return tree;
	}

	private Node readFileRecursivly(Node parentNode, FileTreeElement fte) {
		if (fte.isDirectory()) {
			Node nextNode = new NodeImpl(NodeType.DIRECTORY);
			nextNode.addAttribute("DIRECTORY_NAME", new StringValueImpl(fte.getFileName()));
			
			fte.getChildren().stream().forEach(childFte -> nextNode.addChildWithParent(readFileRecursivly(nextNode, childFte)));
			return nextNode;
		} else {
			ArtifactReader reader = ArtifactIOUtil.getReaderForType(fte,context);
			
			if (reader != null) {
				return reader.readArtifact(fte).getRoot();
			} else {
				// store files that can't be processed as files
				Node fileNode = new NodeImpl(NodeType.FILE);
				fileNode.addAttribute("FILE_NAME", new StringValueImpl(fte.getFileName()));
				fileNode.addAttribute("FILE_EXTENSION", new StringValueImpl(fte.getExtension()));
				return fileNode;
			}
		}
	}

}

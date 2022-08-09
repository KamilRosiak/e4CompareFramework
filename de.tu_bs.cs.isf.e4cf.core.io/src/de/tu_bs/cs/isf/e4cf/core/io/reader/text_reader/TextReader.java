package de.tu_bs.cs.isf.e4cf.core.io.reader.text_reader;

import java.nio.file.Paths;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.io.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;

/***
 * 
 * @author Kamil Rosiak
 *
 */
public class TextReader extends AbstractArtifactReader {
	public final static String[] SUPPORTED_FILE_ENDINGS = { "txt" };

	public TextReader() {
		super(SUPPORTED_FILE_ENDINGS);
	}

	@Override
	public Tree readArtifact(FileTreeElement element) {
		Tree tree = null;
		if (isFileSupported(element)) {
			String s = FileStreamUtil.readLineByLine(Paths.get(element.getAbsolutePath()));
			// Spiting the input by lines
			String[] lines = s.split("\n");
			// The name is only the file name
			String name = element.getAbsolutePath().substring(element.getAbsolutePath().lastIndexOf("\\") + 1);

			tree = new TreeImpl(name, new NodeImpl(NodeType.UNDEFINED, TextFileTags.TEXT.toString()));
			tree.setFileExtension("txt");
			for (String line : lines) {
				Node lineNode = new NodeImpl(NodeType.UNDEFINED, TextFileTags.LINE.toString(), tree.getRoot());
				String[] words = line.split(" ");
				for (String word : words) {
					Node wordNode = new NodeImpl(NodeType.UNDEFINED, TextFileTags.WORD.toString(), lineNode);
					wordNode.addAttribute(TextFileTags.TEXT.toString(), new StringValueImpl(word));
				}
			}
		}

		return tree;
	}
}

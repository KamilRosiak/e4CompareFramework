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
 * @author Kamil Rosiak
 *
 */
public class TextReader extends AbstractArtifactReader {
	public final static String[] SUPPORTED_FILE_ENDINGS = {"txt"};

	public TextReader() {
		super(SUPPORTED_FILE_ENDINGS);
	}

	@Override
	public Tree readArtifact(FileTreeElement element) {
		Tree tree = null;
		if(isFileSupported(element)) {
			String s = FileStreamUtil.readLineByLine(Paths.get(element.getAbsolutePath()));
			//Spiting the input by lines
			String[] lines = s.split("\n");
			//The name is only the file name
			String name = element.getAbsolutePath().substring(element.getAbsolutePath().lastIndexOf("\\") + 1);
			
			tree = new TreeImpl(name,new NodeImpl(TextFileTags.TEXT.toString()));
			for(String line :lines) {
				Node lineNode = new NodeImpl(TextFileTags.LINE.toString(), tree.getRoot());
				String[] words = line.split(" ");
				for(String word : words) {
				    Node wordNode = new NodeImpl(TextFileTags.WORD.toString(), lineNode);
				    wordNode.addAttribute(TextFileTags.TEXT.toString(), word);
				}
			}	
		}
		return tree;	
	}
}

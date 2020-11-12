package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.javaparser.ast.CompilationUnit;


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

	private Node recursivelyTreeBuilder(com.github.javaparser.ast.Node node) {
		// Name newNode by class of node
		Node newNode = new NodeImpl(String.valueOf(node.getClass()));
		
		newNode.addAttribute("columnBegin", String.valueOf(node.getTokenRange().get().getBegin().getRange().get().begin.column));
		newNode.addAttribute("columnEnd", String.valueOf(node.getTokenRange().get().getBegin().getRange().get().end.column));
		newNode.addAttribute("lineBegin", String.valueOf(node.getTokenRange().get().getBegin().getRange().get().begin.line));
		newNode.addAttribute("lineEnd", String.valueOf(node.getTokenRange().get().getBegin().getRange().get().end.line));
		
		// fill in attributes
		
		for (com.github.javaparser.ast.Node child : node.getChildNodes()) {
			Node newChildNode = recursivelyTreeBuilder(child); 
			newNode.addChild(newChildNode);
		}
		
		return newNode;
	}
	
	
	public JavaReader() {
		super(SUPPORTED_FILE_ENDINGS);
	}

	@Override
	public Tree readArtifact(FileTreeElement element) {
		Tree tree = null;
		
		if (isFileSupported(element)) {
			String s = FileStreamUtil.readLineByLine(Paths.get(element.getAbsolutePath()));
			String fileName = Paths.get(element.getAbsolutePath()).getFileName().toString();
			CompilationUnit cu = StaticJavaParser.parse(s);
			// do stuff
			
			String sps = cu.toString();
			
			//tree = new TreeImpl("", new NodeImpl(cu.toString()));
			
		}
		

		
		return tree;
	}
}
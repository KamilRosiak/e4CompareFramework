package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader;

import java.nio.file.Paths;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader.factory.NodeFactory;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader.factory.StatementNodeFactory;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter.JavaWriter;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;

/***
 * This reader converts java files into the generic data structure.
 * 
 * @author Serkan Acar
 * @author Hassan Smaoui
 * @author Pascal Blum
 * @author Paulo Haas
 *
 */
public class JavaReader extends AbstractArtifactReader {
	public final static String[] SUPPORTED_FILE_ENDINGS = { "java" };

	public JavaReader() {
		super(SUPPORTED_FILE_ENDINGS);
	}

	/**
	 * Converts java files into a tree.
	 * 
	 * @param element Java file
	 * @return Tree
	 */
	@Override
	public Tree readArtifact(FileTreeElement element) {
		Tree tree = null;

		if (isFileSupported(element)) {
			String s = FileStreamUtil.readLineByLine(Paths.get(element.getAbsolutePath()));
			String fileName = Paths.get(element.getAbsolutePath()).getFileName().toString();
			CompilationUnit cu = StaticJavaParser.parse(s);
			Node rootNode = new NodeImpl(NodeType.FILE, JavaWriter.NODE_TYPE_TREE);
			JavaVisitor visitor = new JavaVisitor(new NodeFactory(new StatementNodeFactory()));
			visitor.visit(cu, rootNode);
			tree = new TreeImpl(fileName, rootNode);
		
		}
		return tree;
	}
}
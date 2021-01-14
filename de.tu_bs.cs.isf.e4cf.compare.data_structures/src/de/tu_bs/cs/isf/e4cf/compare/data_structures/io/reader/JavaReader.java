package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter.JavaWriter;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.util.TreeConverter;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;

import java.io.IOException;
import java.nio.file.Paths;
import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.printer.DotPrinter;

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
			Node rootNode = new NodeImpl(JavaWriter.NODE_TYPE_TREE);
			JavaVisitor visitor = new JavaVisitor();
			visitor.visit(cu, rootNode);
			tree = new TreeImpl(fileName, rootNode);

			// Remove these lines after debug
			System.out.print("\n\n--- JAVA PARSER AST BEGIN ---\n\n");
			System.out.print(new DotPrinter(true).output(cu).replace("\n", "\\n"));
			System.out.print("\n\n--- JAVA PARSER AST END ---\n\n");
			System.out.print("\n\n--- FRAMEWORK TREE BEGIN ---\n\n");
			System.out.println(TreeConverter.treeToDot(tree));
			System.out.print("\n\n--- FRAMEWORK TREE END ---\n\n");
		}

		return tree;
	}
}
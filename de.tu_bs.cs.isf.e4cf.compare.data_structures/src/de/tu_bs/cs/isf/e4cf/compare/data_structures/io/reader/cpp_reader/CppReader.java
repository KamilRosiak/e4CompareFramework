/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.cpp_reader;

import java.nio.file.Paths;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter.cpp_writer.CppWriter;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;

/**
 * @author developer-olan
 *
 */
public class CppReader extends AbstractArtifactReader {
	public final static String[] SUPPORTED_FILE_ENDINGS = { "cpp" };
	/**
	 * @param supportedFiles
	 */
	public CppReader() {
		super(SUPPORTED_FILE_ENDINGS);
	}

	@Override
	public Tree readArtifact(FileTreeElement element) {
		Tree tree = null;
		
		if (isFileSupported(element)) {
			String s = FileStreamUtil.readLineByLine(Paths.get(element.getAbsolutePath()));
			String fileName = Paths.get(element.getAbsolutePath()).getFileName().toString();
//			CompilationUnit cu = StaticJavaParser.parse(s);
			Node rootNode = new NodeImpl(CppWriter.NODE_TYPE_TREE);
//			JavaVisitor visitor = new JavaVisitor(new NodeFactory(new StatementNodeFactory()));
//			visitor.visit(cu, rootNode);
			tree = new TreeImpl(fileName, rootNode);
		
		}
		return tree;
	}

}

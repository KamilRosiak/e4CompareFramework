package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.uml;

import java.io.IOException;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

public class UMLReader extends AbstractArtifactReader {
	public final static String[] SUPPORTED_FILE_ENDINGS = { "uml" };

	public UMLReader() {
		super(SUPPORTED_FILE_ENDINGS);
	}

	@Override
	public Tree readArtifact(FileTreeElement fileTreeElement) {
		CharStream umlFileStream;
		try {
			umlFileStream = new ANTLRFileStream(fileTreeElement.getAbsolutePath());
			UMLLexer umlLexer = new UMLLexer(umlFileStream);
			CommonTokenStream tokens = new CommonTokenStream(umlLexer);
			UMLParser umlParser = new UMLParser(tokens);
			ParseTree umlFileTree = umlParser.document();
			UMLVisitor umlVisitor = new UMLVisitor();

			Node root = umlVisitor.visit(umlFileTree);
			root.addAttribute("filename", new StringValueImpl(fileTreeElement.getFileName()));
			return new TreeImpl(fileTreeElement.getFileName(), root);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new TreeImpl("Error parsing tree");
	}

}

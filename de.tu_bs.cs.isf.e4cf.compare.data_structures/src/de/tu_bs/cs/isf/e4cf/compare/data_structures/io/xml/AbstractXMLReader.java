package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.xml;

import java.io.IOException;
import java.util.Map;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.xml.impl.XMLLexer;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.xml.impl.XMLParser;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

public abstract class AbstractXMLReader extends AbstractArtifactReader {
	private final Map<String, NodeType> nameToType;

	public AbstractXMLReader(String[] supportedFiles, Map<String, NodeType> nameTypeMap) {
		super(supportedFiles);
		this.nameToType = nameTypeMap;
	}
	
	@Override
	public Tree readArtifact(FileTreeElement fileTreeElement) {
		CharStream fileStream;
		try {
			fileStream = new ANTLRFileStream(fileTreeElement.getAbsolutePath());
			XMLLexer lexer = new XMLLexer(fileStream);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			XMLParser parser = new XMLParser(tokens);
			ParseTree parseTree = parser.document();
			XMLVisitor visitor = new XMLVisitor(nameToType);

			Node root = visitor.visit(parseTree);
			root.addAttribute("filename", new StringValueImpl(fileTreeElement.getFileName()));
			Tree tree = new TreeImpl(fileTreeElement.getFileName(), root);
			// TODO set file extension automatically for all readers
			tree.setFileExtension(this.getSupportedFiles()[0]);
			return tree;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new TreeImpl("Error parsing tree");
	}

}

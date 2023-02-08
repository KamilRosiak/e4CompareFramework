package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.arch;

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

public class ArchReader extends AbstractArtifactReader {
	public final static String[] SUPPORTED_FILE_ENDINGS = { "arch" };

	public ArchReader() {
		super(SUPPORTED_FILE_ENDINGS);
	}

	@Override
	public Tree readArtifact(FileTreeElement fileTreeElement) {
		CharStream archFileStream;
		try {
			archFileStream = new ANTLRFileStream(fileTreeElement.getAbsolutePath());
			ArchLexerGrammar archLexer = new ArchLexerGrammar(archFileStream);
			CommonTokenStream tokens = new CommonTokenStream(archLexer);
			ArchParserGrammar archParser = new ArchParserGrammar(tokens);
			ParseTree archFileTree = archParser.archfile();
			ArchVisitor archVisitor = new ArchVisitor();

			Node root = archVisitor.visit(archFileTree);
			root.addAttribute("filename", new StringValueImpl(fileTreeElement.getFileName()));
			Tree archTree = new TreeImpl(fileTreeElement.getFileName(), root);
			// TODO set file extension automatically for all readers
			archTree.setFileExtension(SUPPORTED_FILE_ENDINGS[0]);
			return archTree;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new TreeImpl("Error parsing tree");
	}

}

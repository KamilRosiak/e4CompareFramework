package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.arch;

import java.io.IOException;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.arch.ArchLexer;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.arch.ArchParser;
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
			ArchLexer archLexer = new ArchLexer(archFileStream);
			CommonTokenStream commonTokenStream = new CommonTokenStream(archLexer);
			ArchParser archParser = new ArchParser(commonTokenStream);
			ArchParser.XmlContext xml = archParser.xml();
			
			ParseTreeWalker.DEFAULT.walk(null, xml);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}

package de.tu_bs.cs.isf.e4cf.core.io.writer.cpp_writer;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.io.interfaces.AbstractArtifactWriter;

/**
 * @author developer-olan
 *
 */

public class CppWriter extends AbstractArtifactWriter {
	public final static String FILE_ENDING = "cpp";
	public final static String NODE_TYPE_TREE = "CPP";
	
	public CppWriter() {
		super(FILE_ENDING);
	}

	@Override
	public String getSuppotedNodeType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeArtifact(Tree tree, String path) {
		// TODO Auto-generated method stub
		
	}

}
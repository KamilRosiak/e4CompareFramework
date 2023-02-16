package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.xml.uml;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.xml.AbstractXMLWriter;

public class UMLWriter extends AbstractXMLWriter {
	
	private static final String FILE_ENDING = "uml";

	public UMLWriter() {
		super(FILE_ENDING, UMLTag.MAP.typeToName());
	}

}

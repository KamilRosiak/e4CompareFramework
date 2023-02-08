package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.xml.uml;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.xml.AbstractXMLReader;

public class UMLReader extends AbstractXMLReader {
	public final static String[] SUPPORTED_FILE_ENDINGS = { "uml" };

	public UMLReader() {
		super(SUPPORTED_FILE_ENDINGS, UMLTag.MAP.nameToType());
	}
}

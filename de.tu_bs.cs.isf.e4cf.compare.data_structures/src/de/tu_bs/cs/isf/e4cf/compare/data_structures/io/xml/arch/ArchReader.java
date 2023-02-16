package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.xml.arch;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.xml.AbstractXMLReader;

public class ArchReader extends AbstractXMLReader {
	public final static String[] SUPPORTED_FILE_ENDINGS = { "arch" };

	public ArchReader() {
		super(SUPPORTED_FILE_ENDINGS, ArchTag.MAP.nameToType());
	}

}

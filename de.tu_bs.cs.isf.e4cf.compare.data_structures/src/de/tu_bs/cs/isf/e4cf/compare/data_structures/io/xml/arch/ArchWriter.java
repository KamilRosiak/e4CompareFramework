package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.xml.arch;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.xml.AbstractXMLWriter;

public class ArchWriter extends AbstractXMLWriter {
	private static final String FILE_ENDING = "arch";

	public ArchWriter() {
		super(FILE_ENDING, ArchTag.MAP.typeToName());
	}

}

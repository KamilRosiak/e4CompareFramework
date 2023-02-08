package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.arch;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter.XMIWriter;

public class ArchWriter extends XMIWriter {
	private static final String FILE_ENDING = "arch";

	public ArchWriter() {
		super(FILE_ENDING, ArchTagMapper.getTypeToName());
	}

}

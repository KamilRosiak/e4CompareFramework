package de.tu_bs.cs.isf.familymining.ppu_iec.parser.ST.util;

import de.tu_bs.cs.isf.e4cf.parser.base.ParserNode;

public interface ParserNodeMapping<T> {
	
	public T map(ParserNode node);
}

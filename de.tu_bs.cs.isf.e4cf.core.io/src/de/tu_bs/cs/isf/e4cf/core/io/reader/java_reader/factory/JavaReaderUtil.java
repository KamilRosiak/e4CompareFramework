package de.tu_bs.cs.isf.e4cf.core.io.reader.java_reader.factory;

import java.util.HashSet;

import de.tu_bs.cs.isf.e4cf.core.io.reader.java_reader.AccessModifier;

public class JavaReaderUtil {
	
	
	public static boolean isAccsessModifier(String modifier) {
		HashSet<String> accesModifier = new HashSet<String>();
		for(AccessModifier mod : AccessModifier.values()) {
			accesModifier.add(mod.name());
		}
		return accesModifier.contains(modifier);
	}

}

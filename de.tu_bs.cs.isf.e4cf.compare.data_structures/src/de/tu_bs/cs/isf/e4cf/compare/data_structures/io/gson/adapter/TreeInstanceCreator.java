package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.gson.adapter;

import com.google.gson.InstanceCreator;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

import java.lang.reflect.Type;

/**
 * Factory to create instances implementing the Tree interface.
 */
public class TreeInstanceCreator implements InstanceCreator<Tree> {
	/**
	 * Creates a TreeImpl with an empty string.
	 *
	 * @param type Unused.
	 */
	@Override
	public Tree createInstance(Type type) {
		return new TreeImpl("");
	}
}

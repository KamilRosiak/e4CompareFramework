package de.tu_bs.cs.isf.e4cf.core.import_export.services.gson.adapter;

import com.google.gson.InstanceCreator;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

import java.lang.reflect.Type;

/**
 * This is needed to allow the creation of treeImpl while importing data.
 * The treeImpl class does not have a constructor without a parameter.
 * 
 * @author Team 6
 */
public class TreeInstanceCreator implements InstanceCreator<Tree> {
	@Override
	public Tree createInstance(Type type) {
		return new TreeImpl("");
	}
}

package de.tu_bs.cs.isf.e4cf.core.import_export.services.gson.adapter;

import com.google.gson.InstanceCreator;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

import java.lang.reflect.Type;

/**
<<<<<<< HEAD:de.tu_bs.cs.isf.e4cf.core.compare.remote/src/de/tu_bs/cs/isf/e4cf/core/compare/remote/util/TreeInstanceCreator.java
 * Factory to create instances implementing the Tree interface.
=======
 * Factory to create instaces implementing the Tree interface.
>>>>>>> conflict_fix:de.tu_bs.cs.isf.e4cf.core.import_export/src/de/tu_bs/cs/isf/e4cf/core/import_export/services/gson/adapter/TreeInstanceCreator.java
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

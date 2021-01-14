package de.tu_bs.cs.isf.e4cf.core.import_export.services;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;

/**
 * Interface to allow multiple implementation of ImportService.
 * Can be used to extend functionality with other import formats.
 * 
 * @author Team 6
 *
 * @param <T> Expected Type of imported data.
 */
public interface ImportService<T> {
	TreeImpl importTree(T object);
}

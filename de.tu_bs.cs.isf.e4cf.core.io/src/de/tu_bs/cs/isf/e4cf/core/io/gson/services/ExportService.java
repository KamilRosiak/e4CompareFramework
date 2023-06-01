package de.tu_bs.cs.isf.e4cf.core.io.gson.services;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;

/**
 * Interface to allow multiple implementation of ExportServices.
 * Can be used to extend functionality with other export formats.
 *
 * @author Team 6
 *
 * @param <T> Expected Type of export format.
 */
public interface ExportService<T> {
	T exportTree(TreeImpl tree);
}

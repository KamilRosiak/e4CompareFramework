package de.tu_bs.cs.isf.e4cf.core.import_export.services;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import com.google.gson.Gson;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;

/**
 * Class to convert a TreeImpl instance into a JSON string.
 */
@Creatable
@Singleton
public class ExportService {
	private Gson gson;

	/**
	 * Creates a new ExportService instance.
	 */
	public ExportService() {
		this.gson = new Gson();
	}

	/**
	 * Converts an instance of TreeImpl into a JSON string.
	 *
	 * @param tree The tree to be converted to JSON.
	 * @return A JSON string.
	 */
	public String createJSON(TreeImpl tree) {
	return this.gson.toJson(tree);
    }
}

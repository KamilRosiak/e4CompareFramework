package de.tu_bs.cs.isf.e4cf.core.import_export.services.gson;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import com.google.gson.Gson;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.core.import_export.services.ExportService;

/**
 * Implementation of the export service with GSON.
 * 
 * @author Team 6
 */
@Creatable
@Singleton
public class GsonExportService implements ExportService<String> {
	private Gson gson;
	
	public GsonExportService() {
		this.gson = new Gson();
	}

	@Override
	public String exportTree(TreeImpl tree) {
		return this.gson.toJson(tree);
	}
}

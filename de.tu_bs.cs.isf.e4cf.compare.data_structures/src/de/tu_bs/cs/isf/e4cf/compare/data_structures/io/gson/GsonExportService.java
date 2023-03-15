package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.gson;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.gson.services.ExportService;

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
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		
		this.gson = gsonBuilder.create();
	}

	@Override
	public String exportTree(TreeImpl tree) {
		return this.gson.toJson(tree);
	}
}

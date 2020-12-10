package de.tu_bs.cs.isf.e4cf.core.import_export.services;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import com.google.gson.Gson;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;

@Creatable
@Singleton
public class ExportService {
	private Gson gson;
	
	public ExportService() {
		this.gson = new Gson();
	}
	
	public String createJSON(TreeImpl tree) {
        return this.gson.toJson(tree);
    }
}

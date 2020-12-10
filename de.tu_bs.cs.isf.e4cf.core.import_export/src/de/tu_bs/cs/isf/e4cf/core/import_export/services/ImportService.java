package de.tu_bs.cs.isf.e4cf.core.import_export.services;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.import_export.services.adapter.TreeInstanceCreator;

@Creatable
@Singleton
public class ImportService {
	
	private Gson gson;

	public ImportService() {
		 GsonBuilder gsonBuilder = new GsonBuilder();
	     gsonBuilder.registerTypeAdapter(Tree.class, new TreeInstanceCreator());
	     this.gson = gsonBuilder.create();
	}
	
	public TreeImpl readJSON(String jsonString) {
        TreeImpl tree = (TreeImpl) this.gson.fromJson(jsonString, Tree.class);
        this.reconstructTree(tree.getRoot());
        return tree;
    }

	private void reconstructTree(Node node) {
        if (node == null || node.getChildren() == null) return;
        for (Node children : node.getChildren()) {
            children.setParent(node);
            reconstructTree(children);
        }
    }
}

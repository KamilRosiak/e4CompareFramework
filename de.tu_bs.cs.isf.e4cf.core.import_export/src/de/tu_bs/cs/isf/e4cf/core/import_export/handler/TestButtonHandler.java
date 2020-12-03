package de.tu_bs.cs.isf.e4cf.core.import_export.handler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.e4.core.di.annotations.Execute;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.import_export.services.adapter.TreeInstanceCreator;



public class TestButtonHandler {	
	
	@Execute
	public void execute() {
		 GsonBuilder gsonBuilder = new GsonBuilder();
	        gsonBuilder.registerTypeAdapter(Tree.class, new TreeInstanceCreator());


	        Set<String> stringSet = new HashSet<>();
	        stringSet.add("value1");
	        stringSet.add("value2");


	        List<Attribute> testAttributes = new LinkedList<>();
	        testAttributes.add(new AttributeImpl("Key1","Value"));

	        NodeImpl root = new NodeImpl("Root");
	        root.setAttributes(testAttributes);
	        root.setVariabilityClass(VariabilityClass.MANDATORY);
	        root.setChildren(new LinkedList<>());

	        TreeImpl tree = new TreeImpl("testTree");
	        tree.setArtifactType("type");
	        tree.setRoot(root);

	        System.out.println(createJSON(tree, gsonBuilder));
	        
	        TreeImpl treeResult = (TreeImpl) readJSON(createJSON(tree, gsonBuilder), gsonBuilder);

	        reconstructTree(treeResult.getRoot());

	        System.out.println(treeResult.toString());


	    }

	    private static void reconstructTree(Node node) {
	        if (node == null || node.getChildren() == null) return;
	        for (Node children : node.getChildren()) {
	            children.setParent(node);
	            reconstructTree(children);
	        }
	    }

	    private static String createJSON(Object object, GsonBuilder builder) {
	        Gson gson = builder.create();
	        return gson.toJson(object);
	    }

	    private static Object readJSON(String jsonString, GsonBuilder builder) {
	        Gson gson = builder.create();
	        return gson.fromJson(jsonString, Tree.class);
	    }

	
		
}
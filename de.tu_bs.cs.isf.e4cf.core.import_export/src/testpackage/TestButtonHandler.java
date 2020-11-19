package testpackage;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.e4.core.di.annotations.Execute;
import com.google.gson.Gson;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;
//import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;
//import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
//import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
//import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
//import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractAttribute;
//import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractNode;
//import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractTree;
//import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
//import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractNode;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractTree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;



public class TestButtonHandler {	
	
	@Execute
	public void execute() {
		System.out.println("ayyyy");
		Gson gson = new Gson();
//		Set<String> stringSet = new HashSet<>();
//        stringSet.add("value1");
//        stringSet.add("value2");
////        Gson gson = new Gson();
//
//
////        List<AttributeImpl> testAttributes = new LinkedList<>();
////        testAttributes.add(new AttributeImpl("Key1","Value"));
//
        NodeImpl root = new NodeImpl("Root");
//       // root.setAttributes(testAttributes);
//        root.setVariabilityClass(VariabilityClass.MANDATORY);
//
        TreeImpl tree = new TreeImpl("testTree");
//        tree.setArtifactType("type");
        tree.setRoot(root);
//
//        System.out.println(createJSON(tree));
        
        

		String treeString = gson.toJson(tree);
		System.out.println(treeString);
//		System.out.println(gson.fromJson(treeString,TreeImpl.class));  <-- this is the problem
//
//        TreeImpl treeResult = (TreeImpl) readJSON(createJSON(tree));
//
////        reconstructTree((NodeImpl) treeResult.getRoot());
//
//        System.out.println(treeResult);

	}
	
	
	private static void reconstructTree(NodeImpl children2) {
        if (children2.getChildren() == null) return;
        for (Node children : children2.getChildren()) {
            children.setParent(children2);
            reconstructTree((NodeImpl) children);
        }
    }

    private static String createJSON(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    private static Object readJSON(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, TreeImpl.class);
    }
	
	
		
}
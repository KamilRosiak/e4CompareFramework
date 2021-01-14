
package de.tu_bs.cs.isf.e4cf.core.compare.remote.handler;

import org.eclipse.e4.core.di.annotations.Execute;

import com.google.gson.GsonBuilder;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.compare.remote.util.TreeInstanceCreator;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

public class RestButtonHandler {

	@Execute
	public void execute() {


        Set<String> stringSet = new HashSet<>();
        stringSet.add("value1");
        stringSet.add("value2");


        List<Attribute> testAttributes = new LinkedList<>();
        testAttributes.add(new AttributeImpl("Key1","Value"));

        NodeImpl root = new NodeImpl("Root");
        root.setAttributes(testAttributes);
        root.setVariabilityClass(VariabilityClass.MANDATORY);
        root.setChildren(new LinkedList<>());

        TreeImpl tree1 = new TreeImpl("testTree1");
        tree1.setArtifactType("type");
        tree1.setRoot(root);
        
        
        testAttributes.add(new AttributeImpl("Key2","Value"));
        root.setAttributes(testAttributes);

        TreeImpl tree2 = new TreeImpl("testTree2");
        tree2.setArtifactType("type");
        tree2.setRoot(root);
                
		
        RemoteComparisonStatus statusRequest = RemoteComparisonFactory.createComparisonRequest(tree1, tree2);
        System.out.println(statusRequest.toString());
        
        RemoteComparisonStatus statusResponse = RemoteComparisonFactory.getComparisonStatus(statusRequest.getUuid());
        System.out.println(statusResponse.toString());
        
        TreeImpl resultTree = RemoteComparisonFactory.getComparisonResult(statusRequest.getUuid());
        System.out.println(resultTree.toString());
	}

}
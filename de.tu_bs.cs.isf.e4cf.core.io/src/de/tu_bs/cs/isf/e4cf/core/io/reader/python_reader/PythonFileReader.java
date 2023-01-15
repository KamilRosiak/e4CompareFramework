package de.tu_bs.cs.isf.e4cf.core.io.reader.python_reader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.Directory;
import de.tu_bs.cs.isf.e4cf.core.io.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust.AdjustRename;
import de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust.Const;
import de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust.TreeAdjuster;
import de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_reader.AbstractSAXHandler;
import de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_reader.AttributeDictionary;
import de.tu_bs.cs.isf.e4cf.core.io.reader.python_adjust.AdjustNode;

public class PythonFileReader extends AbstractArtifactReader {

	public static String[] SUPPORTED_FILE_ENDINGS = { Const.PY };

	private AbstractSAXHandler saxHandler;
	private FileToTreeReader fileToTree;

	public PythonFileReader() {
		super(SUPPORTED_FILE_ENDINGS);
		saxHandler = new PythonSAXHandler();
		fileToTree = new FileToTreeReader();
	}

	@Override
	public Tree readArtifact(FileTreeElement element) {
		String fileName = Paths.get(element.getAbsolutePath()).getFileName().toString();
		return readArtifact(element, fileName);
	}

	public Tree readArtifact(FileTreeElement element, String rootName) {
		Node rootNode = new NodeImpl(Const.PYTHON);
		String path = element.getAbsolutePath();
		path = Const.QUOTATION + path.replace(Const.BACKSLASH, Const.DIVIDE_OP) + Const.QUOTATION ;
		
		Node compUnit = new NodeImpl(Const.C_UNIT);
		rootNode.addChild(compUnit);
		compUnit.setParent(rootNode);

		Gson gson = new Gson();
		JsonObject obj = gson.fromJson(fileToTree.getTreeFromFileMocked(path), JsonObject.class);

		generateNodes(obj, compUnit, gson);

		/*
		 * TODO: if (element.isDirectory()) { rootNode = createDirectory(element,
		 * rootName); for (FileTreeElement childElement : element.getChildren()) {
		 * createHierarchy(childElement, rootNode); }
		 * 
		 * } else { if (isFileSupported(element.getExtension())) { rootNode =
		 * readArtifactRoot(element); } else { rootNode = createFile(element); }
		 * 
		 * }
		 */
		
		TreeAdjuster nodeAdjuster = new AdjustNode();
		nodeAdjuster.recursiveAdjust(rootNode);
		
		TreeAdjuster renameAdjuster = new AdjustRename();
		renameAdjuster.recursiveAdjust(rootNode);

		return new TreeImpl(element.getFileName(), rootNode);

	}
	
	
	private void generateNodes(JsonArray jsonArr, Node rootNode, Gson gson) {
		for (int i = 0; i < jsonArr.size(); i++) {
			JsonObject obj = gson.fromJson(jsonArr.get(i).toString(), JsonObject.class);
			generateNodes(obj, rootNode, gson);
		}
	}

	private void generateNodes(JsonObject obj, Node rootNode, Gson gson) {
		JsonObject jsonObj = gson.toJsonTree(obj).getAsJsonObject();
		Node node = null;
		for (Map.Entry<String, JsonElement> entry : jsonObj.entrySet()) {
			if (entry.getKey().equals(Const._TYPE)) {
				node = new NodeImpl(getName(entry.getValue()), rootNode);
			} else if (node != null) {
				try {
					createNodefromJsonObject(gson, node, entry.getValue().toString(), entry.getKey());
				} catch (JsonSyntaxException e) {
					try {
						createNodefromJsonArray(gson, node, entry.getValue().toString(), entry.getKey());
					} catch (JsonSyntaxException b) {
						addAttribute(node, entry.getKey(), entry.getValue());
					}
				}

			}
		}
	}
	
	private void createNodefromJsonObject(Gson gson, Node node, String value, String  entry) {
		JsonArray jsonArr = gson.fromJson(value, JsonArray.class);
		Node nextNode = new NodeImpl(entry, node);
		generateNodes(jsonArr, nextNode, gson);
	}
	
	private void createNodefromJsonArray(Gson gson, Node node, String value, String  entry) {
		JsonObject nextObj = gson.fromJson(value, JsonObject.class);
		Node nextNode = new NodeImpl(entry, node);
		generateNodes(nextObj, nextNode, gson);
	}
	
	
	
	private void addAttribute(Node node, String entry, JsonElement value ) {
		if (!Const.BANED_ATTRIBUTES.contains(entry)) {
			node.addAttribute(entry, getName(value));
		}
	}



	private String getName(JsonElement element) {
		if (element.toString().length() > 2) {
			return element.toString().substring(1, element.toString().length() - 1);
		}
		return element.toString();
	}

}

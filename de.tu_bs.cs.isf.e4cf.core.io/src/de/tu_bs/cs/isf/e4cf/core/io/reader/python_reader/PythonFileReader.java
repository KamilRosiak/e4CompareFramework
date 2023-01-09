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
import com.google.gson.JsonParser;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.Directory;
import de.tu_bs.cs.isf.e4cf.core.io.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust.Const;
import de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_reader.AbstractSAXHandler;
import de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_reader.AttributeDictionary;

public class PythonFileReader extends AbstractArtifactReader {

	public static String[] SUPPORTED_FILE_ENDINGS = { "py" };

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
		Node rootNode = null;
		String path = element.getAbsolutePath();
		path = "\"" + path.replace("\\", "/") + "\"";

		rootNode = new NodeImpl("Python");

		Gson gson = new Gson();
		JsonObject obj = gson.fromJson(fileToTree.getTreeFromFileMocked(path), JsonObject.class);

		
		generateNodes(obj, rootNode, gson);

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

		return new TreeImpl(element.getFileName(), rootNode);

	}

	private void generateNodes(JsonObject obj, Node rootNode, Gson gson) {
		JsonObject jsonObj = gson.toJsonTree(obj).getAsJsonObject();
		Node node = null;
		for (Map.Entry<String, JsonElement> entry : jsonObj.entrySet()) {
			if (entry.getKey().equals("_type")) {
				node = new NodeImpl(getName(entry.getValue()), rootNode);

			} else if (entry.getKey().equals("body")) {
				Node bodyNode = new NodeImpl(Const.BODY, node);
				JsonArray jsonArr = gson.fromJson(entry.getValue().toString(), JsonArray.class);
				generateNodes(jsonArr, bodyNode, gson);

			} else if (node != null) {
				node.addAttribute(new AttributeImpl(entry.getKey(), new StringValueImpl(entry.getValue().toString())));
			}
		}
	}

	private void generateNodes(JsonArray jsonArr, Node rootNode, Gson gson) {
		for (int i = 0; i < jsonArr.size(); i++) {
			JsonObject obj = gson.fromJson(jsonArr.get(i).toString(), JsonObject.class);
			generateNodes(obj, rootNode, gson);
		}
	}
	
	
	private String getName(JsonElement element) {
		if (element.toString().length() > 2) {
			return element.toString().substring(1, element.toString().length() - 1);
		}
		return element.toString();
	}



}

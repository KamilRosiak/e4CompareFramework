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

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;

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

		return new TreeImpl(element.getFileName(), rootNode);

	}

	private void generateNodes(JsonObject obj, Node rootNode, Gson gson) {
		JsonObject jsonObj = gson.toJsonTree(obj).getAsJsonObject();
		Node node = null;
		for (Map.Entry<String, JsonElement> entry : jsonObj.entrySet()) {
			if (entry.getKey().equals(Const._TYPE)) {
				node = new NodeImpl(getName(entry.getValue()), rootNode);

			} else if (node != null) {
				try {
					JsonArray jsonArr = gson.fromJson(entry.getValue().toString(), JsonArray.class);
					Node nextNode = new NodeImpl(entry.getKey(), node);
					generateNodes(jsonArr, nextNode, gson);
				} catch (Exception e) {
					try {
						JsonObject nextObj = gson.fromJson(entry.getValue().toString(), JsonObject.class);
						Node nextNode = new NodeImpl(entry.getKey(), node);
						generateNodes(nextObj, nextNode, gson);
					} catch (Exception f) {
						addAttribute(node, entry.getKey(), entry.getValue());
						//node.addAttribute(entry.getKey(), getName(entry.getValue()));
					}
				}

			}
		}
	}
	
	private void addAttribute(Node node, String entry, JsonElement value ) {
		if (!Const.BANED_ATTRIBUTES.contains(entry)) {
			node.addAttribute(entry, getName(value));
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

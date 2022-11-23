package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_reader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.core.io.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust.AdjustAll;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.Directory;

public class SrcMLReader extends AbstractArtifactReader {

	private String srcMLExePath;

	public static String[] SUPPORTED_FILE_ENDINGS = { "cpp" };

	private AbstractSAXHandler saxHandler;

	public SrcMLReader() {
		super(SUPPORTED_FILE_ENDINGS);
		srcMLExePath = new File(
				(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "lib/srcml/srcml.exe")
						.substring(1)).getPath();
		saxHandler = new SAXHandler();
	}

	public SrcMLReader(String srcMLExePath) {
		super(SUPPORTED_FILE_ENDINGS);
		this.srcMLExePath = srcMLExePath;
		saxHandler = new SAXHandler();
	}

	public Tree readArtifact(FileTreeElement element, String rootName) {
		Node rootNode = null;

		if (element.isDirectory()) {
			rootNode = createDirectory(element, rootName);
			for (FileTreeElement childElement : element.getChildren()) {
				createHierarchy(childElement, rootNode);
			}

		} else {
			if (isFileSupported(element.getExtension())) {
				rootNode = readArtifactRoot(element);
			} else {
				rootNode = createFile(element);
			}

		}

		return new TreeImpl(element.getFileName(), rootNode);

	}

	@Override
	public Tree readArtifact(FileTreeElement element) {
		String fileName = Paths.get(element.getAbsolutePath()).getFileName().toString();
		return readArtifact(element, fileName);
	}

	public Tree readArtifact(File file) {
		return readArtifact(constructFileTreeElement(file));
	}

	public Tree readArtifact(File file, String rootName) {
		return readArtifact(constructFileTreeElement(file), rootName);
	}

	private Node createDirectory(FileTreeElement element) {
		return createDirectory(element, element.getFileName());
	}

	private Node createDirectory(FileTreeElement element, String directoryName) {
		Node directory = new NodeImpl(NodeType.DIRECTORY);
		directory.addAttribute(new AttributeImpl(AttributeDictionary.DIRECTORY_NAME_ATTRIBUTE_KEY,
				new StringValueImpl(directoryName)));

		return directory;
	}

	private Node createFile(FileTreeElement element) {
		Node file = new NodeImpl(NodeType.FILE);
		file.addAttribute(new AttributeImpl(AttributeDictionary.FILE_NAME_ATTRIBUTE_KEY,
				new StringValueImpl(element.getFileName())));

		file.addAttribute(new AttributeImpl(AttributeDictionary.FILE_EXTENSION_ATTRIBUTE_KEY,
				new StringValueImpl(element.getExtension())));

		return file;
	}

	private void createHierarchy(FileTreeElement element, Node currentNode) {

		if (element.isDirectory()) {
			Node directory = createDirectory(element);

			currentNode.addChildWithParent(directory);
			for (FileTreeElement childElement : element.getChildren()) {
				createHierarchy(childElement, directory);
			}
		} else {
			if (isFileSupported(element.getExtension())) {
				Node artifactRoot = readArtifactRoot(element);
				currentNode.addChildWithParent(artifactRoot);
			} else {
				Node file = createFile(element);
				currentNode.addChildWithParent(file);
			}

		}

	}

	private Node readArtifactRoot(FileTreeElement element) {

		Node rootNode = null;
		try {

			String xmlOutputPath = File.createTempFile("Temp_", Long.toString(System.nanoTime())).getAbsolutePath();
			InputStream inputStream = getInputStream(Paths.get(element.getAbsolutePath()), xmlOutputPath);
			if (inputStream != null) {

				SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
				saxParserFactory.setNamespaceAware(true);
				SAXParser saxParser = saxParserFactory.newSAXParser();
				XMLReader xmlReader = saxParser.getXMLReader();
				xmlReader.setContentHandler(saxHandler);
				xmlReader.setErrorHandler(saxHandler);
				saxHandler.setExtension(element.getExtension());

				InputSource inputSource = new InputSource(new InputStreamReader(inputStream, "UTF-8"));
				xmlReader.parse(inputSource);
				
				
				AdjustAll adjuster = new AdjustAll(saxHandler.getRootNode());
				rootNode =  adjuster.adjustAllNodes(); //Adjust Tree Nodes and return new Root
				String fileName = element.getFileName().split(".cpp")[0];
				rootNode.getChildren().get(0).addAttribute(new AttributeImpl("Name", new StringValueImpl(fileName)));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rootNode;

	}

	private InputStream getInputStream(Path fileArgument, String xmlResultPath) throws IOException {

		InputStream inputStream = null;
		ProcessBuilder processBuilder = new ProcessBuilder(srcMLExePath, fileArgument.toString());
		processBuilder.redirectErrorStream(true);

		if (xmlResultPath != null && (!xmlResultPath.equals(""))) {
			File log = new File(xmlResultPath);
			processBuilder.redirectOutput(log);
		}

		processBuilder.redirectOutput(Redirect.PIPE);
		Process process = processBuilder.start();
		inputStream = process.getInputStream();

		return inputStream;
	}

	private FileTreeElement constructFileTreeElement(File file) {

		FileTreeElement element = null;

		if (file.isDirectory()) {

			element = new Directory(file.getAbsolutePath());

			for (File child : file.listFiles()) {

				FileTreeElement childElement = constructFileTreeElement(child);

				element.getChildren().add(childElement);
				childElement.setParent(element);
			}

		} else {
			element = new de.tu_bs.cs.isf.e4cf.core.file_structure.components.File(file.getAbsolutePath());
		}

		return element;

	}

}

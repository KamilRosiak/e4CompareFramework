package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.io.reader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.FileUtils;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.FloatValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.LongValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.util.AttributeDictionary;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.Directory;

public class SrcMLReader extends AbstractArtifactReader {

	private String srcMLExePath;

	public static String[] SUPPORTED_FILE_ENDINGS = { "cpp", "java" };

	private AbstractSAXHandler saxHandler;

	public SrcMLReader() {
		super(SUPPORTED_FILE_ENDINGS);
		srcMLExePath = new File(
				(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "lib/srcml/srcml.exe")
						.substring(1)).getPath();
		saxHandler = new TaxonomySAXHandler();
	}

	public SrcMLReader(String srcMLExePath) {
		super(SUPPORTED_FILE_ENDINGS);
		this.srcMLExePath = srcMLExePath;
		saxHandler = new TaxonomySAXHandler();
	}

	public Tree readArtifact(FileTreeElement element, String treeName) {
		Node rootNode = null;

		int totalNumberOfFiles = getNumberOfNestedFilesInDirectory(element);
		totalNumberOfFiles = totalNumberOfFiles == 0 ? 1 : totalNumberOfFiles;

		long totalNumberOfBytes = FileUtils.sizeOf(new File(element.getAbsolutePath()));
		totalNumberOfBytes = totalNumberOfBytes == 0 ? 1 : totalNumberOfBytes;

		if (element.isDirectory()) {
			rootNode = createDirectory(element, totalNumberOfFiles);
			for (FileTreeElement childElement : element.getChildren()) {
				createHierarchy(childElement, rootNode, totalNumberOfFiles, totalNumberOfBytes);
			}

		} else {
			if (isFileSupported(element.getExtension())) {
				rootNode = readArtifactRoot(element);
			} else {
				rootNode = createFile(element, element.getSize());
			}

		}

		return new TreeImpl(treeName, rootNode);

	}

	@Override
	public Tree readArtifact(FileTreeElement element) {
		String fileName = Paths.get(element.getAbsolutePath()).getFileName().toString();
		return readArtifact(element, fileName);
	}

	public Tree readArtifact(File file) {
		return readArtifact(constructFileTreeElement(file));
	}

	public Tree readArtifact(File file, String treeName) {
		return readArtifact(constructFileTreeElement(file), treeName);
	}

	private Node createDirectory(FileTreeElement element, int totalNumberOfFiles) {
		Node directory = new NodeImpl(NodeType.DIRECTORY);
		directory.addAttribute(new AttributeImpl(AttributeDictionary.DIRECTORY_NAME_ATTRIBUTE_KEY,
				new StringValueImpl(element.getFileName())));
		directory.addAttribute(
				new AttributeImpl(AttributeDictionary.SIZE_ATTRIBUTE_KEY, new LongValueImpl(element.getSize())));

		int numberOfSourceFiles = getNumberOfNestedFilesInDirectory(element);
		float weight = (float) numberOfSourceFiles / totalNumberOfFiles;
		directory.addAttribute(new AttributeImpl(AttributeDictionary.WEIGHT_ATTRIBUTE_KEY, new FloatValueImpl(weight)));

		return directory;
	}

	private int getNumberOfNestedFilesInDirectory(FileTreeElement element) {

		int numberOfFiles = 0;

		for (FileTreeElement child : element.getChildren()) {

			if (!child.isDirectory()) {
				numberOfFiles++;
			}

			numberOfFiles += getNumberOfNestedFilesInDirectory(child);
		}

		return numberOfFiles;

	}

	private Node createFile(FileTreeElement element, long totalNumberOfBytes) {
		Node file = new NodeImpl(NodeType.FILE);
		file.addAttribute(new AttributeImpl(AttributeDictionary.FILE_NAME_ATTRIBUTE_KEY,
				new StringValueImpl(element.getFileName())));

		file.addAttribute(new AttributeImpl(AttributeDictionary.FILE_EXTENSION_ATTRIBUTE_KEY,
				new StringValueImpl(element.getExtension())));

		long fileSize = FileUtils.sizeOf(new File(element.getAbsolutePath()));

		file.addAttribute(new AttributeImpl(AttributeDictionary.SIZE_ATTRIBUTE_KEY, new LongValueImpl(fileSize)));

		float weight = (float) fileSize / totalNumberOfBytes;
		file.addAttribute(new AttributeImpl(AttributeDictionary.WEIGHT_ATTRIBUTE_KEY, new FloatValueImpl(weight)));

		return file;
	}

	private void createHierarchy(FileTreeElement element, Node currentNode, int totalNumberOfFiles,
			long totalNumberOfBytes) {

		if (element.isDirectory()) {
			Node directory = createDirectory(element, totalNumberOfFiles);

			currentNode.addChildWithParent(directory);
			for (FileTreeElement childElement : element.getChildren()) {
				createHierarchy(childElement, directory, totalNumberOfFiles, totalNumberOfBytes);
			}
		} else {
			if (isFileSupported(element.getExtension())) {
				Node artifactRoot = readArtifactRoot(element);
				currentNode.addChildWithParent(artifactRoot);
			} else {
				Node file = createFile(element, totalNumberOfBytes);
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

				InputSource inputSource = new InputSource(inputStream);
				xmlReader.parse(inputSource);

				rootNode = saxHandler.getRootNode();

				rootNode.addAttribute(new AttributeImpl(AttributeDictionary.FILE_NAME_ATTRIBUTE_KEY,
						new StringValueImpl(element.getFileName())));

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

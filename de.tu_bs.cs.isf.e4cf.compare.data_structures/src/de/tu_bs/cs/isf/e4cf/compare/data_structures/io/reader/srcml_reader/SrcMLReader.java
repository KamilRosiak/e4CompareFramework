package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.srcml_reader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

public class SrcMLReader extends AbstractArtifactReader {

	private String srcMLExePath;

	public static String[] SUPPORTED_FILE_ENDINGS = { "cpp" };

	private SAXHandler saxHandler;

	public SrcMLReader() {
		super(SUPPORTED_FILE_ENDINGS);
		srcMLExePath = new File(
				(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "lib/srcml/srcml.exe")
						.substring(1)).getPath();
		saxHandler = new SAXHandler();
	}

	@Override
	public Tree readArtifact(FileTreeElement element) {
		Tree tree = null;

		if (isFileSupported(element)) {

			try {

				String xmlOutputPath = File.createTempFile("Temp_", Long.toString(System.nanoTime())).getAbsolutePath();

				String fileName = Paths.get(element.getAbsolutePath()).getFileName().toString();
				InputStream inputStream = getInputStream(Paths.get(element.getAbsolutePath()), xmlOutputPath);
				if (inputStream != null) {

					SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
					saxParserFactory.setNamespaceAware(true);
					SAXParser saxParser = saxParserFactory.newSAXParser();
					XMLReader xmlReader = saxParser.getXMLReader();
					xmlReader.setContentHandler(saxHandler);
					xmlReader.setErrorHandler(saxHandler);

					InputSource inputSource = new InputSource(inputStream);
					xmlReader.parse(inputSource);

					Node rootNode = saxHandler.getRootNode();
					tree = new TreeImpl(fileName, rootNode);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return tree;

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

}

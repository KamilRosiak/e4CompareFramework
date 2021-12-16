/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.srcml_java_parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter.JavaWriter2;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.srcml_cpp_parser.CppXMLExtractor;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

/**
 * 
 * This reader converts CPP file into generic data structure
 * 
 * @author developer-olan
 *
 */
public class SrcMLJavaReader extends AbstractArtifactReader {
	public final static String[] SUPPORTED_FILE_ENDINGS = { "java" };
	/**
	 * @param supportedFiles
	 * @throws IOException 
	 */
	public SrcMLJavaReader() {
		super(SUPPORTED_FILE_ENDINGS);
	}

	@Override
	public Tree readArtifact(FileTreeElement element) {
		Tree tree = null;
		Node rootNode = new NodeImpl(JavaWriter2.NODE_TYPE_TREE);

		if (isFileSupported(element)) {
			// Try to parse artifact
			try {
				
				String srcMLExePath = new File((this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
						+ "lib/srcml/srcml.exe").substring(1)).getPath();							
				String xmlOutputPath = File.createTempFile("Temp", Long.toString(System.nanoTime())).getAbsolutePath();			
				
				String fileName = Paths.get(element.getAbsolutePath()).getFileName().toString();
				
				InputStream xmlOutputStream = CppXMLExtractor.parseWithsrcML(srcMLExePath, Paths.get(element.getAbsolutePath()), xmlOutputPath);
				if (xmlOutputStream != null) {
					try {
						SAXJavaParser.startParsing(xmlOutputStream, rootNode);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				tree = new TreeImpl(fileName, rootNode);
				tree.setFileExtension(SUPPORTED_FILE_ENDINGS[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return tree;
	}

}

/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.srcml_cpp_parser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter.cpp_writer.CppWriter;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

/**
 * 
 * This reader converts CPP file into generic data structure
 * 
 * @author developer-olan
 *
 */
public class SrcMLCppReader extends AbstractArtifactReader {
	public final static String[] SUPPORTED_FILE_ENDINGS = { "cpp" };
	/**
	 * @param supportedFiles
	 * @throws IOException 
	 */
	public SrcMLCppReader() {
		super(SUPPORTED_FILE_ENDINGS);
	}

	@Override
	public Tree readArtifact(FileTreeElement element) {
		Tree tree = null;
		Node rootNode = new NodeImpl(CppWriter.NODE_TYPE_TREE);

		if (isFileSupported(element)) {
			String srcMLExePath = "C:\\Program Files\\srcML\\srcml.exe";
			String xmlOutputPath = "C:\\Users\\olant\\source\\repos\\e4CompareFramework\\de.tu_bs.cs.isf.e4cf.compare.taxonomy\\src\\de\\tu_bs\\cs\\isf\\e4cf\\compare\\taxonomy\\cpp_parser\\output\\"+Paths.get(element.getAbsolutePath()).getFileName().toString().replace(".cpp","")+"_srcmlOutput.xml";
			String fileName = Paths.get(element.getAbsolutePath()).getFileName().toString();
			
			// Try to parse artifact
			try {
				InputStream xmlOutputStream = CppXMLExtractor.parseWithsrcML(srcMLExePath, Paths.get(element.getAbsolutePath()), xmlOutputPath);
				if (xmlOutputStream != null) {
					try {
						SAXCppParser.startParsing(xmlOutputStream, rootNode);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			tree = new TreeImpl(fileName, rootNode);
			tree.setFileExtension("cpp");
		}
		return tree;
	}

}

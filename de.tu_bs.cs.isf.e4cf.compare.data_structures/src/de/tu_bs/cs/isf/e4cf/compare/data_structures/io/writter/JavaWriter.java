
package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter;

import com.github.javaparser.printer.PrettyPrinterConfiguration;
import com.github.javaparser.printer.PrettyPrinterConfiguration.IndentType;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactWriter;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;

/**
 * 
 * This class converts the generic tree structure back into the original java
 * file.
 * 
 * @author Serkan Acar
 * @author Pascal Blum
 * @author Paulo Haas
 * @author Hassan Smaoui
 *
 */
public class JavaWriter extends AbstractArtifactWriter {
	public final static String FILE_ENDING = "java";
	public final static String NODE_TYPE_TREE = "JAVA";

	/**
	 * Initializes a new instance of class JavaWriter.
	 */
	public JavaWriter() {
		super(FILE_ENDING);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getSuppotedNodeType() {
		return NODE_TYPE_TREE;
	}

	/**
	 * Creates a new file by converting the tree back to source code. The algorithm
	 * starts at the tree's root.
	 * <p>
	 * The file will be created at parameter <code>path</code>. The file ending
	 * {@link JavaWriter#FILE_ENDING} is automatically added to <code>path</code>.
	 * 
	 * @see {@link FileStreamUtil#writeTextToFile(String, String)} for more
	 *      information about this method's behavior
	 * @param tree Tree, which will be converted to source code
	 * @param path Where to create the new file
	 */
	@Override
	public void writeArtifact(Tree tree, String path) {
		if (tree.getArtifactType().equals(NODE_TYPE_TREE)) {
			JavaWriterUtil jwu = new JavaWriterUtil();
			PrettyPrinterConfiguration ppc = new PrettyPrinterConfiguration();
			ppc.setIndentType(IndentType.TABS); // Default is spaces
			ppc.setIndentSize(1); // Only one tab to indent
			FileStreamUtil.writeTextToFile(path + "." + FILE_ENDING,
					jwu.visitWriter(tree.getRoot()).toString(ppc));
		}
	}
}
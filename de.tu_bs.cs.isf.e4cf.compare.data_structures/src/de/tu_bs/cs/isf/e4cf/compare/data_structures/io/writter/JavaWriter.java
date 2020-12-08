package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter;

import java.util.Set;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter.WriterUtil;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactWriter;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
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
	 * {@inheritDoc}
	 */
	@Override
	public void writeArtifact(Tree tree, String path) {
		// TODO Auto-generated method stub
		if (tree.getArtifactType().equals(NODE_TYPE_TREE)) {
			FileStreamUtil.writeTextToFile(path + "." + FILE_ENDING, createFileContent(tree.getRoot()));
		}
	}

	/**
	 * Generates the contents of a java source file recursively.
	 * 
	 * @param root Node of the syntax tree.
	 * @return Contents of a file
	 */
	private String createFileContent(Node root) {
		
		return WriterUtil.visitWriter(root);
		
		/*CompilationUnit cu = new CompilationUnit();
		
		for(Attribute attribute : root.getAttributes()) {
			String key = attribute.getAttributeKey();
			Set<String> value = attribute.getAttributeValues();
			if(key.startsWith(JavaNodeTypes.Package.toString())) {
				// Assumption: If node has key package, it must have the single value of package name
				cu.setPackageDeclaration(value.iterator().next());
			}
		}
		
		// Build the file depth-first
		// If node is leaf then getChildren returns an empty list and the body of the
		// for loop is not executed.
		for (Node child : root.getChildren()) {
			
		}
		return cu.toString();*/
	}
	/*
	private void ImportDeclaration(Node node, CompilationUnit cu) {
		for(Node child : node.getChildren()) {
			cu.addImport(child.getNodeType());
		}
	}
	
	private void ClassOrInterfaceDeclaration(Node node, CompilationUnit cu) {
		ClassOrInterfaceDeclaration coid; 
		Attribute attr = node.getAttributeForKey(JavaNodeTypes.Type.toString());
		if (attr.getAttributeValues().iterator().next().equals(JavaNodeTypes.Class.toString())) {
			coid = cu.addClass(name);
		} else if (attr.getAttributeValues().iterator().next().equals(JavaNodeTypes.Interface.toString())) {
			coid = cu.addInterface(name);
		}
		for(Node chlild : node.getChildren()) {
			
		}
	}*/
}
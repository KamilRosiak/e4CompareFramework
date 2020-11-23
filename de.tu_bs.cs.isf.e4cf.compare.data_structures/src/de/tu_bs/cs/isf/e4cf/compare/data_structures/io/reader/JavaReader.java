package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.type.*;

/***
 * 
 * @author Serkan Acar
 * @author Hassan Smaoui
 * @author Pascal Blum
 * @author Paulo Haas
 *
 */

public class JavaReader extends AbstractArtifactReader {
	public final static String[] SUPPORTED_FILE_ENDINGS = { "java" };

	private Node recursivelyTreeBuilder(com.github.javaparser.ast.Node node) {
		// create a node
		Node newNode = new NodeImpl(node.getClass().getSimpleName());

		// counters
		int modifierCtr = 0;
		int interfaceCtr = 0;
		int superclassCtr = 0;

		// If the node is CU and has package declaration add the name of package as a
		// attribute to the new node
		if (node.getClass().equals(CompilationUnit.class)
				&& ((CompilationUnit) node).getPackageDeclaration().isPresent()) {
			newNode.addAttribute(JavaNodeTypes.Package.toString(),
					((CompilationUnit) node).getPackageDeclaration().orElse(null).getNameAsString());
		}

		// Filter the child nodes to a list only containing node of type SimpleName. The
		// list should contain zero or one element. If the node has a child of type
		// SimpleName add the name as a attribute to the new node.
		List<com.github.javaparser.ast.Node> nameList = node.getChildNodes().stream()
				.filter(n -> n.getClass().equals(SimpleName.class) || n.getClass().equals(Name.class))
				.collect(Collectors.toList());
		if (nameList.size() > 0) {
			com.github.javaparser.ast.Node name = nameList.get(0);
			if (name.getClass().equals(SimpleName.class)) {
				newNode.addAttribute(SimpleName.class.getSimpleName(), ((SimpleName) name).asString());
			} else if (name.getClass().equals(Name.class)) {
				newNode.addAttribute(Name.class.getSimpleName(), ((Name) name).asString());
			}
		}

		if (node.getClass().equals(ClassOrInterfaceDeclaration.class)) {
			newNode.addAttribute(JavaNodeTypes.Interface.toString(), String.valueOf(((ClassOrInterfaceDeclaration) node).isInterface()));
			
			for (Modifier modifier : ((ClassOrInterfaceDeclaration) node).getModifiers()) {
				newNode.addAttribute(Modifier.class.getSimpleName() + modifierCtr, modifier.toString());
				modifierCtr++;
			}
			for (ClassOrInterfaceType coid : ((ClassOrInterfaceDeclaration) node).getImplementedTypes()) {
				newNode.addAttribute(JavaNodeTypes.Interface.toString() + interfaceCtr, coid.getNameAsString());
				interfaceCtr++;
			}
			
			List<com.github.javaparser.ast.Node> extendedTypes = new ArrayList<com.github.javaparser.ast.Node>();
			for (ClassOrInterfaceType coid : ((ClassOrInterfaceDeclaration) node).getExtendedTypes()) {
				newNode.addAttribute(JavaNodeTypes.Superclass.toString() + superclassCtr, coid.getNameAsString());
				superclassCtr++;
				extendedTypes.add(coid);
			}
			extendedTypes.forEach(n -> n.remove());
		}
		
		
		else if (node.getClass().equals(FieldDeclaration.class)) {
			for (Modifier modifier : ((FieldDeclaration) node).getModifiers()) {
				newNode.addAttribute(Modifier.class.getSimpleName() + modifierCtr,  modifier.toString());
				modifierCtr++;
			}
		}
		
		else if (node.getClass().equals(VariableDeclarator.class)) {
			newNode.addAttribute(JavaNodeTypes.Type.toString(), ((VariableDeclarator) node).getType().toString());
		}
		
		
		// Recursive depth search behavior
		for (int i = 0; i < node.getChildNodes().size(); i++) {
			com.github.javaparser.ast.Node child = node.getChildNodes().get(i);

			if (!child.getClass().equals(Modifier.class) && !child.getClass().equals(SimpleName.class)
					&& !child.getClass().equals(Name.class) && !child.getClass().equals(PackageDeclaration.class)) {
				Node newChildNode = recursivelyTreeBuilder(child);
				newNode.addChild(newChildNode);
			}
		}

		newNode.addAttribute("columnBegin",
				String.valueOf(node.getTokenRange().get().getBegin().getRange().get().begin.column));
		newNode.addAttribute("columnEnd",
				String.valueOf(node.getTokenRange().get().getBegin().getRange().get().end.column));
		newNode.addAttribute("lineBegin",
				String.valueOf(node.getTokenRange().get().getBegin().getRange().get().begin.line));
		newNode.addAttribute("lineEnd",
				String.valueOf(node.getTokenRange().get().getBegin().getRange().get().end.line));

		return newNode;
	}

	public JavaReader() {
		super(SUPPORTED_FILE_ENDINGS);
	}

	@Override
	public Tree readArtifact(FileTreeElement element) {
		Tree tree = null;

		if (isFileSupported(element)) {
			String s = FileStreamUtil.readLineByLine(Paths.get(element.getAbsolutePath()));
			String fileName = Paths.get(element.getAbsolutePath()).getFileName().toString();
			CompilationUnit cu = StaticJavaParser.parse(s);

			tree = new TreeImpl(fileName, recursivelyTreeBuilder(cu));
		}

		return tree;
	}
}
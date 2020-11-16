package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.expr.SimpleName;

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
		Node newNode = new NodeImpl(String.valueOf(node.getClass()));

		// counters
		int modifierCtr = 0;

		// If the node is CU and has package declaration add the name of package as a
		// attribute to the new node
		if (node.getClass().equals(CompilationUnit.class)
				&& ((CompilationUnit) node).getPackageDeclaration().isPresent()) {
			newNode.addAttribute(JavaNodeTypes.Package.toString(),
					((CompilationUnit) node).getPackageDeclaration().orElse(null).getNameAsString());
		}

		// set Attributes (Type, SimpleName, Modifier)
		newNode.addAttribute("Type", node.getClass().toString());

		// Filter the child nodes to a list only containing node of type SimpleName. The
		// list should contain zero or one element. If the node has a child of type
		// SimpleName add the name as a attribute to the new node.
		List<com.github.javaparser.ast.Node> simpleNameList = node.getChildNodes().stream()
				.filter(n -> n.getClass().equals(SimpleName.class)).collect(Collectors.toList());
		if (simpleNameList.size() > 0) {
			newNode.addAttribute(SimpleName.class.toString(), ((SimpleName) simpleNameList.get(0)).asString());
		}

		if (node.getClass().equals(ClassOrInterfaceDeclaration.class)) {
			for (Modifier modifier : ((ClassOrInterfaceDeclaration) node).getModifiers()) {
				newNode.addAttribute("Modifier" + modifierCtr, modifier.toString());
				modifierCtr++;
			}
		}

		// Recursive depth search behavior
		for (int i = 0; i < node.getChildNodes().size(); i++) {
			com.github.javaparser.ast.Node child = node.getChildNodes().get(i);

			if (child.getClass().equals(Modifier.class) || child.getClass().equals(SimpleName.class)
					|| child.getClass().equals(PackageDeclaration.class)) {
				node.remove(child);
			} else {
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

		newNode.addAttribute("ModifierCount", "" + modifierCtr);

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
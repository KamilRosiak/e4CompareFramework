package de.tu_bs.cs.isf.e4cf.evaluation.handler;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter.TreeWritter;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneGenerator;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneHelper;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.Taxonomy;

public class TwoDimensionalCloneGenHandler {
	public static int NUMBER_OF_VARIANTS = 4;

	@Execute
	public void execute(IEclipseContext context, ServiceContainer services, ReaderManager readerManager,
			CloneGenerator gen, Taxonomy tax, CloneHelper helper, TreeWritter writter) {

		for (int numberOfVariants = 0; numberOfVariants < NUMBER_OF_VARIANTS; numberOfVariants++) {
			Tree tree = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionFromExplorer());
			changeUUIDs(tree.getRoot());
			helper.setTrackingTree(tree);
			// Get all classes from tree
			List<Node> compilationUnits = tree.getNodesForType("JAVA");

			Random rnd = new Random();
			// select random class from tree
			Node selectedCompilationUnit = compilationUnits.get(rnd.nextInt(compilationUnits.size()));
			Tree compilationUnitTree = helper.deepCopy(new TreeImpl("JAVA", selectedCompilationUnit));

			int modificationAmount = 3;
			for (int i = 0; i < modificationAmount; i++)
				tax.performType3ModificationSyntaxSafe(compilationUnitTree, tax.getType3Method(true));

			List<Node> directories = tree.getNodesForType("DIRECTORY");

			Node directory = directories.get(rnd.nextInt(directories.size()));
			compilationUnitTree.getRoot().setParent(directory);
			directory.getChildren().add(compilationUnitTree.getRoot());

			writter.writeArtifact(tree, services.rcpSelectionService.getCurrentSelectionFromExplorer().getAbsolutePath()
					.replace(".tree", "") + "cloned_" + numberOfVariants + ".tree");
		}
		System.out.println("Goodbye");
	}

	public void changeUUIDs(Node node) {
		node.setUUID(UUID.randomUUID());
		node.getAttributes().forEach(attr -> {
			attr.setUuid(UUID.randomUUID());
			attr.getAttributeValues().forEach(value -> {
				value.setUUID(UUID.randomUUID());
			});
		});

		for (Node childNode : node.getChildren()) {
			changeUUIDs(childNode);
		}
	}

	@Evaluate
	/**
	 * Evaluates if the associated menu contribution is to be displayed. For clone
	 * generation, any single selection may be used.
	 * 
	 * @param services injected to get current Explorer selection
	 * @return true when the clone generator may be invoked
	 */
	public boolean isActive(ServiceContainer services) {
		if (services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() == 1) {
			return true;
		} else {
			return false;
		}
	}
}

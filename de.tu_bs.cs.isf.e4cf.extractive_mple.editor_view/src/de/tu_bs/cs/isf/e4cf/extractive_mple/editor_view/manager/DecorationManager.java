package de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.manager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.impl.FamilyModelNodeDecorator;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.interfaces.NodeDecorator;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.stringtable.IdTable;

@Creatable
@Singleton
public class DecorationManager {
<<<<<<< HEAD
	private NodeDecorator currentDecorator;
=======
	private NodeDecorator currentDecorater;
	private static final String DECORATER_EXTENSION_POINT = "de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.NodeDecorator";
	private static final String DECORATER_ATTR = "node_decorator";
>>>>>>> refs/heads/master_merg

	/**
	 * This method returns all registered tree decorate
	 * 
	 * @return
	 */
	public List<NodeDecorator> getDecoratorFromExtension() {
		return RCPContentProvider.<NodeDecorator>getInstanceFromBundle(IdTable.DECORATOR_EXTENSION_POINT_ID,
				IdTable.DECORATER_EXTENSION_POINT_ATTR);
	}

	/**
	 * Returns a decorator for the given tree type if available else it returns the
	 * family model decorator.
	 */
	public List<NodeDecorator> getDecoratorForTree(Tree tree) {
		List<NodeDecorator> decorators = new ArrayList<NodeDecorator>();

		for (NodeDecorator decorator : getDecoratorFromExtension()) {
			if (decorator.isSupportedTree(tree)) {
				decorators.add(decorator);
			}
		}

		if (decorators.isEmpty()) {
			decorators.add(new FamilyModelNodeDecorator());
		}

		return decorators;
	}

	public NodeDecorator getCurrentDecorater() {
		return currentDecorator;
	}

	public void setCurrentDecorater(NodeDecorator currentDecorater) {
		this.currentDecorator = currentDecorater;
	}
}

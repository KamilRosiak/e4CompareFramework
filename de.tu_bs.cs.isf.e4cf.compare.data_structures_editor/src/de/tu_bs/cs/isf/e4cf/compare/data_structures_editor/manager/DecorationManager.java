package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.impl.FamilyModelNodeDecorator;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.interfaces.NodeDecorator;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;

@Creatable
@Singleton
public class DecorationManager {
	private NodeDecorator currentDecorater;
	private static final String DECORATER_EXTENSION_POINT = "de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.NodeDecorator";
	private static final String DECORATER_ATTR = "node_decorator";

	/**
	 * This method returns all registered tree decorate
	 * 
	 * @return
	 */
	public List<NodeDecorator> getDecoraterFromExtension() {
		return RCPContentProvider.<NodeDecorator>getInstanceFromBundle(DECORATER_EXTENSION_POINT, DECORATER_ATTR);
	}

	/**
	 * Returns a decorator for the given tree type if available else it returns the
	 * family model decorator.
	 */
	public List<NodeDecorator> getDecoratorForTree(Tree tree) {
		List<NodeDecorator> decorator = new ArrayList<NodeDecorator>();
		
		for (NodeDecorator decorater : getDecoraterFromExtension()) {
			if (decorater.isSupportedTree(tree)) {
				decorator.add(decorater);
			}
		}
		
		if (decorator.isEmpty()) {
			decorator.add(new FamilyModelNodeDecorator());
		}

		return decorator;
	}

	public NodeDecorator getCurrentDecorater() {
		return currentDecorater;
	}

	public void setCurrentDecorater(NodeDecorator currentDecorater) {
		this.currentDecorater = currentDecorater;
	}
}

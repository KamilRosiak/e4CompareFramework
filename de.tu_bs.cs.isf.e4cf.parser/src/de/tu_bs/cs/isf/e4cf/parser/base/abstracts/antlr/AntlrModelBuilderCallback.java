package de.tu_bs.cs.isf.e4cf.parser.base.abstracts.antlr;

import org.eclipse.emf.ecore.EObject;

import de.tu_bs.cs.isf.e4cf.parser.base.ParserNode;
import de.tu_bs.cs.isf.e4cf.parser.base.ParserType;
import de.tu_bs.cs.isf.e4cf.parser.base.abstracts.AbstractNodeCallback;
import de.tu_bs.cs.isf.e4cf.parser.base.helper.IModelBuilder;

/**
 * An abstract helper for ANTLR-based callbacks deriving an EObject from the parser node structure.
 * 
 * @author Oliver Urbaniak
 * @see de.tu_bs.cs.isf.e4cf.parser.base.helper.IModelBuilder
 * 
 * @param <T> the type that is built by the {@link IModelBuilder}
 */
public abstract class AntlrModelBuilderCallback <T extends EObject> extends AbstractNodeCallback {

	private ParserNode root;
	private T modelInstance;
	private ParserType type;
	
	public AntlrModelBuilderCallback(String extensionId, ParserType type) {
		super(extensionId);
		this.type = type;
	}

	abstract public IModelBuilder<T> getModelBuilder();
	
	protected abstract boolean isHidden(ParserNode node);

	@Override
	public void processNode(ParserNode node) {
		if (!isHidden(node)) {
			storeIfRoot(node);			
		}
	}

	private void storeIfRoot(ParserNode node) {
		if (!node.hasParent()) {
			root = node;
		}
	}

	@Override
	public int postProcessing() {
		modelInstance = (T) getModelBuilder().build(root);
		return modelInstance != null ? 0 : -1;
	}

	@Override
	public EObject getResult() {
		return modelInstance;
	}

	@Override
	public ParserType getType() {
		return type;
	}

}

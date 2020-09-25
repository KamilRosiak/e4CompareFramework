package de.tu_bs.cs.isf.e4cf.core.compare.templates;

import java.io.Serializable;

import org.eclipse.emf.ecore.EObject;

/**
 * This class represents the ResultElement it provides all functionality that is needed to save and process compare results. 
 * The generic type T extends EObject is needed to map the specific type to the first and second element.
 * @author {Kamil Rosiak}
 *
 * @param <ElementType> Type of elements (e.g. Configuration,POU,Action,Variable,LanguageElement)
 */
public class ResultElement<ElementType extends EObject> implements Serializable {
	private static final long serialVersionUID = 3474325800644746223L;
	private ElementType first;
	private ElementType second;
	private AbstractAttribute attribute;
	private float similarity = 0.00f;

	public ResultElement(ElementType first,ElementType second, float similarity, AbstractAttribute attr) {
		setFirst(first);
		setSecond(second);
		setSimilarity(similarity);
		setAttribute(attr);
	}

	public ElementType getFirst() {
		return first;
	}

	public void setFirst(ElementType first) {
		this.first = first;
	}

	public ElementType getSecond() {
		return second;
	}

	public void setSecond(ElementType second) {
		this.second = second;
	}

	public float getSimilarity() {
		return similarity;
	}

	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}

	public AbstractAttribute getAttribute() {
		return attribute;
	}

	public void setAttribute(AbstractAttribute attribute) {
		this.attribute = attribute;
	}
}

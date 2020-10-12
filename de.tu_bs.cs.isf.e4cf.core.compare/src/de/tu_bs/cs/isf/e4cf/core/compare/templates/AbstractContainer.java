package de.tu_bs.cs.isf.e4cf.core.compare.templates;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import de.tu_bs.cs.isf.e4cf.core.compare.interfaces.IFamilyModelContainer;
import de.tu_bs.cs.isf.e4cf.core.compare.interfaces.ISolutionUpdate;

/**
 * This class represents a abstract container thats holds two elements, the similarity and all results.
 * @author {Kamil Rosiak}
 *
 * @param <ElementType> Type of elements to compare.
 * @param <ResultType> Type of result elements.
 */
public abstract class AbstractContainer<ElementType extends EObject, MetricType> implements Serializable, ISolutionUpdate , IFamilyModelContainer {
	private static final long serialVersionUID = 1521277552874459308L;
	private ElementType first;
	private ElementType second;
	private List<ResultElement<ElementType>> results;
	private float similarity;
	private MetricType metric;
	private boolean isCompared = false;
	private List<AbstractOption> options = new ArrayList<AbstractOption>();
	
	public AbstractContainer(ElementType first, ElementType second, MetricType metric) {
		setFirst(first);
		setSecond(second);
		setMetric(metric);
		results = new ArrayList<ResultElement<ElementType>>();
	}
	
	public abstract void reset();

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

	public List<ResultElement<ElementType>> getResults() {
		return results;
	}
	
	public void addResult(ResultElement<ElementType> result) {
		results.add(result);
	}
	
	public void setResults(List<ResultElement<ElementType>> results) {
		this.results = results;
	}

	public float getSimilarity() {
		return similarity;
	}

	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}

	public MetricType getMetric() {
		return metric;
	}

	public void setMetric(MetricType metric) {
		this.metric = metric;
	}

	public boolean isCompared() {
		return isCompared;
	}

	public void setCompared(boolean isCompared) {
		this.isCompared = isCompared;
	}

	public List<AbstractOption> getOptions() {
		return options;
	}

	public void setOptions(List<AbstractOption> options) {
		this.options = options;
	}
	
	
}

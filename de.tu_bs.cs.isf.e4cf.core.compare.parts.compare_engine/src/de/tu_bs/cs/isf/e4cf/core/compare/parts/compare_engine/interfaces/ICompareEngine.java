package de.tu_bs.cs.isf.e4cf.core.compare.parts.compare_engine.interfaces;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import de.tu_bs.cs.isf.e4cf.core.compare.templates.AbstractContainer;

/**
 * This interfaces describes how a compare engine has to be implemented it provides methods for the 
 * comparison of one model with an other model, a list of models with an other list of models and a iterative approach. The models are compared based on a given metric.
 * @author Kamil Rosiak
 *
 * @param <ModelType>
 * @param <MetricType>
 */
public interface ICompareEngine<ModelType extends EObject, MetricType> {
	/**
	 * This method compares two models of the given ModelType and returns the result that has to be an implementation of the AbstractContainer.
	 */
	public abstract AbstractContainer<ModelType,MetricType> compare(ModelType sourceModel, ModelType targetModel, MetricType metric);
	
	/**
	 * This method compares two lists of models of the given ModelType and returns the result that has to be an implementation of the AbstractContainer (every model with every model).
	 */
	public abstract AbstractContainer<ModelType,MetricType> compare(List<ModelType> sourceModels, List<ModelType> targetModels, MetricType metric);
	
	/**
	 * This method compares a list of models iterative. This comparison means it starts with the first model and compares it with the second.
	 */
	public abstract AbstractContainer<ModelType,MetricType> compareIterative(List<ModelType> sourceModels, MetricType metric);
}

package de.tu_bs.cs.isf.e4cf.core.compare.templates;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.core.compare.interfaces.IFamilyModelOption;
import de.tu_bs.cs.isf.e4cf.core.compare.interfaces.ISolutionUpdate;
/**
 * This class is a template for the OptionAttributes.
 * @author {Kamil Rosiak}
 *
 */
public abstract class AbstractOption<ContainerType extends AbstractContainer<?, MetricType>, MetricType> implements ISolutionUpdate, Serializable, IFamilyModelOption {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3040700778943729353L;
	private float similarity = 0.0f;
	private MetricType metric;
	private OptionAttribute optionAttr;
	private List<ContainerType> containers;
	
	public AbstractOption(MetricType metric, OptionAttribute optionAttr) {
		setMetric(metric);
		setOptionAttr(optionAttr);
		containers = new ArrayList<ContainerType>();
	}
	
	@Override
	public void updateSimilarity() {
		float similarity = 0.0f;
		//updates the languageContainer first then adds the similarity
		for(AbstractContainer<?,?> absContainer : getAllContainer()) {
			absContainer.updateSimilarity();
			similarity += absContainer.getSimilarity();
		}
		//average of all added value
		
		if(getAllContainer().size() > 0) {
			similarity = similarity / getAllContainer().size();
		}
		//update option
		setSimilarity(similarity);
	}
	
	public void addContainer(ContainerType container) {
		this.containers.add(container);
	}
	
	public List<ContainerType> getAllContainer() {
		return containers;
	}
	
	public float getSimilarity() {
		return similarity;
	}
	
	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}

	public OptionAttribute getOptionAttr() {
		return optionAttr;
	}

	public void setOptionAttr(OptionAttribute optionAttr) {
		this.optionAttr = optionAttr;
	}

	public MetricType getMetric() {
		return metric;
	}

	public void setMetric(MetricType metric) {
		this.metric = metric;
	}
}

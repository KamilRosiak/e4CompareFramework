package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.compare;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;

public class TaxonomyMetric extends MetricImpl {

	public TaxonomyMetric(String metricName) {
		super(metricName);		
	}
	
	public TaxonomyMetric(String metricName, Comparator... comparators) {
		this(metricName);
		addComparators(comparators);
	}

	@Override
	public List<Comparator> getComparatorForNodeType(String nodeType) {

		Map<String, List<Comparator>> comparators = getAllComparator();
		List<Comparator> comparatorsForNodeType = new ArrayList<Comparator>();
		if (comparators.containsKey(nodeType)) {
			comparatorsForNodeType = comparators.get(nodeType);
		}

		if (comparatorsForNodeType.isEmpty()) {
			if (comparators.containsKey(Comparator.WILDCARD)) {
				comparatorsForNodeType = comparators.get(Comparator.WILDCARD);
			}

		}
		return comparatorsForNodeType;

	}

}

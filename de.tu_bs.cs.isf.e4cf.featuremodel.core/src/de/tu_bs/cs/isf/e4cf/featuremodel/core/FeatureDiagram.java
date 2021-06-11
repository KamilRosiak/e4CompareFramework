package de.tu_bs.cs.isf.e4cf.featuremodel.core;

import org.eclipse.emf.ecore.util.EcoreUtil;

import FeatureDiagram.impl.FeatureDiagrammImpl;

public class FeatureDiagram extends FeatureDiagrammImpl {
	
    public FeatureDiagram() {
        super();
		super.setUuid(EcoreUtil.generateUUID());
    }
    
    @Override
    public void setUuid(String uuid) {
    	throw new UnsupportedOperationException();
    }
	
    
	
}

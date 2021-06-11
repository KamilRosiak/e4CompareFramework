package de.tu_bs.cs.isf.e4cf.featuremodel.core;

import org.eclipse.emf.ecore.util.EcoreUtil;

import FeatureDiagram.FeatureDiagramm;
import FeatureDiagram.impl.FeatureDiagrammImpl;

public class FeatureDiagram extends FeatureDiagrammImpl {
	
    public FeatureDiagram() {
        super();
		super.setUuid(EcoreUtil.generateUUID());
    }
    
    public FeatureDiagram(FeatureDiagramm diagram) {
        this.root = diagram.getRoot();
        this.identifierIncrement = diagram.getIdentifierIncrement();
        this.constraints = diagram.getConstraints();
        this.uuid = diagram.getUuid();    	
    }
    
    @Override
    public void setUuid(String uuid) {
    	throw new UnsupportedOperationException();
    }
	
    
	
}

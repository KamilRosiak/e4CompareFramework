package de.tu_bs.cs.isf.e4cf.featuremodel.core;

import org.eclipse.emf.ecore.util.EcoreUtil;

import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramm;
import FeatureDiagram.impl.FeatureDiagrammImpl;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.FeatureInitializer;

public class FeatureDiagram extends FeatureDiagrammImpl {
	
    public FeatureDiagram() {
        super();
		super.setUuid(EcoreUtil.generateUUID());
		addRoot(this);
    }
    
    public FeatureDiagram(FeatureDiagramm diagram) {
        this.root = diagram.getRoot();
        this.identifierIncrement = diagram.getIdentifierIncrement();
        this.constraints = diagram.getConstraints();
        this.uuid = diagram.getUuid();    
        this.featureConfiguration = diagram.getFeatureConfiguration();
    }
    
    @Override
    public void setUuid(String uuid) {
    	throw new UnsupportedOperationException();
    }
	
    private void addRoot(FeatureDiagramm diagram) {	
    	Feature root = FeatureInitializer.createFeature(FDStringTable.FD_DEFAULT_FM_NAME, true);
		diagram.setRoot(root);
	}
}

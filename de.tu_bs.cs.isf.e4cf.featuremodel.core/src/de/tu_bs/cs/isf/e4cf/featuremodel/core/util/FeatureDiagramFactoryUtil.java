package de.tu_bs.cs.isf.e4cf.featuremodel.core.util;

import org.eclipse.emf.ecore.util.EcoreUtil;

import FeatureDiagram.FeatureDiagramm;
import FeatureDiagram.impl.FeatureDiagramFactoryImpl;

public class FeatureDiagramFactoryUtil {
	
	public static FeatureDiagramm createFeatureDiagram() {
		FeatureDiagramm diagram = FeatureDiagramFactoryImpl.eINSTANCE.createFeatureDiagramm();
		diagram.setUuid(EcoreUtil.generateUUID());
		return diagram;
	}

}

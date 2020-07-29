package de.tu_bs.cs.isf.e4cf.featuremodel.core.util;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import FeatureDiagram.FeatureDiagramm;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.familymining.ppu_iec.rcp_e4.EMFModelLoader.impl.EMFModelLoader;

public class FeatureDiagramSerialiazer {
	
	
	
	public static FeatureDiagramm load(String path) {
		return (FeatureDiagramm) EMFModelLoader.load(path, "feature-metamodel");
	}
	
	
	/**
	 * this method serializes a MetricContainer to a given path with a given file extension.
	 * @param config serialized unit 
	 */
	public static void save(FeatureDiagramm diagram, String path) {
	        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
	        Map<String, Object> m = reg.getExtensionToFactoryMap();
	        m.put("feature-metamodel", new XMIResourceFactoryImpl());
	        // Obtain a new resource set
	        ResourceSet resSet = new ResourceSetImpl();
	        Resource resource = resSet.createResource(URI.createURI(path));
	        resource.getContents().add(diagram);
	        // now save the content.
	        try {
	            resource.save(Collections.EMPTY_MAP);
	        } catch (IOException e) {
	        	RCPMessageProvider.errorMessage("Exporter", "Could not export FeatureDiagram.");
	        	e.printStackTrace();
	        }
	}
	

}

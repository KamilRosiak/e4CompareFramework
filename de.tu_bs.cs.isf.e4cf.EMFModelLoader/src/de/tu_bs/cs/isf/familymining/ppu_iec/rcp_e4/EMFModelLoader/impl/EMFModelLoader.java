package de.tu_bs.cs.isf.familymining.ppu_iec.rcp_e4.EMFModelLoader.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;

public class EMFModelLoader {

	public static EObject load(String path, String extension) {
		if(!path.equals("")) {
	        // Get the resource
	        Resource resource = loadResource(path, extension);
	        // Get the first model element and cast it to the right type
	        EObject object = resource.getContents().get(0);
	        return object;	
		} else {
			return null;
		}
	}
	
	public static Resource loadResource(String path, String extension) {
		if(!path.equals("")) {
			URI pathURI = URI.createFileURI(path);
			//register the XMI FileExtension add all extensions here 
			Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
			Map<String, Object> m = reg.getExtensionToFactoryMap();
		    m.put(extension, new XMIResourceFactoryImpl());
		    // Obtain a new resource set
	        ResourceSet resSet = new ResourceSetImpl();
	        // Get the resource
	        Resource resource = resSet.getResource(pathURI, true);
	        return resource;
		} else {
			return null;
		}
	}
	
	
	public static String getExtension(String path) {
		return path.substring(path.lastIndexOf("\\.")+1, path.length());
	}
	
	/**
	 * this method serializes a EObject to a given path with a given file extension.
	 * @param EObject 
	 */
	public static void save(EObject object, String metaModel, String path, String fileExtension) {
		String fullPath = path+"."+fileExtension;
		//TODO: Check if the file exists
		/**
		boolean isOverwritte;
			try {
				File tempFile = File.createTempFile(path, fileExtension);
				
				if(tempFile.exists()) {
					RCPMessageProvider.questionMessage("File Exists", "Would you like to overwritte the file ? : " + fullPath);
				}
				System.out.println(tempFile.exists());
				
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			**/
	        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
	        Map<String, Object> m = reg.getExtensionToFactoryMap();
	        m.put(metaModel, new XMIResourceFactoryImpl());
	        // Obtain a new resource set
	        ResourceSet resSet = new ResourceSetImpl();
	        Resource resource = resSet.createResource(URI.createFileURI(fullPath));
	        resource.getContents().add(object);
	        // now save the content.
	        try {
	            resource.save(Collections.EMPTY_MAP);
	        } catch (IOException e) {
	        	RCPMessageProvider.errorMessage("Exporter", "Could not export object.");
	        	e.printStackTrace();
	        }
	}

}

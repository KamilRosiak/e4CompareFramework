package de.tu_bs.cs.isf.e4cf.parser.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

public final class ModelToXMIExporter {

	/**
	 * Exports the root EObject (with contained objects, if specified) to the given location
	 * in the <i>xmi</i> format.<br>
	 * 
	 * "If you persistent an EMF object all dependent object will automatically 
	 * be persistent. Objects which do not have a 'contains relationship' must
	 * be added explicitly to the resource.getContents().add(). 
	 * If objects are not added and not included in a contains 
	 * relationship an exception is thrown when calling the resource.save() method." ~ Vogella
	 * 
	 * @param outputPath
	 * @param root
	 * @return
	 */
	public static Resource exportToXMI(Path target, EObject root) throws IOException {		
		// get registry and insert data format
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		
		String filename = target.getFileName().toString();
		String fileExtension = filename.substring(filename.indexOf("."));
		Map<String,Object> m = reg.getExtensionToFactoryMap();
		m.put(fileExtension , new XMIResourceFactoryImpl());

		
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(URI.createFileURI(target.toString()));
		resource.getContents().add(root);
		
		// TODO: ask user, if they want to overwrite existing file
		
		// create a new file
		if (Files.exists(target)){
			Files.delete(target);
		}
		
		// save xmi representation into the file
		resource.save(Collections.EMPTY_MAP);
		
		return resource;
	}
}

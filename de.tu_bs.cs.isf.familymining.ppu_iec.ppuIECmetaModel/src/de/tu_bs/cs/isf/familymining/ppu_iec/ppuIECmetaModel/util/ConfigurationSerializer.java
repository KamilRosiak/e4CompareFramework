package de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.configuration.Configuration;

public class ConfigurationSerializer {
	
	public static void serializConfiguration(Configuration configuration, ServiceContainer services, String postfix) {
		FileTreeElement root = services.workspaceFileSystem.getWorkspaceDirectory();
		Path rootPath = FileHandlingUtility.getPath(root);
		Path projectPath = rootPath.resolve("");
		
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("feature-metamodel", new XMIResourceFactoryImpl());
        // Obtain a new resource set
        ResourceSet resSet = new ResourceSetImpl();
        Resource resource = resSet.createResource(URI.createURI(projectPath.resolve(E4CStringTable.MODEL_DIRECTORY).toUri() + configuration.getIdentifier()+postfix+"."+E4CStringTable.FILE_ENDING_CONFIGURATION));
        resource.getContents().add(configuration);
        // now save the content.
        try {
            resource.save(Collections.EMPTY_MAP);
        } catch (IOException e) {
        	RCPMessageProvider.errorMessage("Exporter", "Could not export FeatureDiagram.");
        	e.printStackTrace();
        }
	}
}

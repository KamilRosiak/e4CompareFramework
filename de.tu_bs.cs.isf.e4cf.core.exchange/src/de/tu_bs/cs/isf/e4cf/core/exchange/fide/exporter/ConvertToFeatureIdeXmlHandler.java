package de.tu_bs.cs.isf.e4cf.core.exchange.fide.exporter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.xml.sax.SAXException;

import FeatureDiagram.FeatureDiagramm;
import FeatureModel.FeatureModell;
import de.tu_bs.cs.isf.e4cf.core.exchange.fide.util.FamilyModelConverter;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.FeatureDiagramSerialiazer;

/**
 * This class is called when the button to convert from the vat model to the feature ide model is pressed.
 * It handles if the button should be visible (only if a convertable file is selected) and the execution of the conversion
 * 
 * @author Patrick Suszek (y0082857) / Dzhakhar Akperov (y0087205)
 * @date 14.12.19
 */
public class ConvertToFeatureIdeXmlHandler {
	
	@Execute
	public void execute(ServiceContainer serviceContainer) throws ParserConfigurationException, SAXException, IOException {
		String path = serviceContainer.rcpSelectionService.getCurrentSelectionFromExplorer().getAbsolutePath();
	    FeatureDiagramm featureDiagram = FeatureDiagramSerialiazer.load(path);   	    
  		Path projectPath = createUriPath(serviceContainer);
  		
  		FeatureModell fideModel = FamilyModelConverter.convertToFeatureIDE(featureDiagram);
  		String workerPath = projectPath.resolve(E4CStringTable.FEATURE_MODEL_DIRECTORY).toUri() + featureDiagram.getRoot().getName()+".worker";
  		save(fideModel, workerPath);
  		
  		changeFileToBeFideLoadable(workerPath);
	}

	@CanExecute
	public boolean canExecute() {
		return true;
	}
	
	@Evaluate
	public boolean evaluate(ServiceContainer serviceContainer)
	{
		if (serviceContainer.rcpSelectionService.getCurrentSelectionFromExplorer() == null) {
			return false; 
		}
		String path = serviceContainer.rcpSelectionService.getCurrentSelectionFromExplorer().getAbsolutePath();
		if (path.endsWith(".fm")) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Creates Uri Path
	 * @param serviceContainer
	 * @return
	 */
	private Path createUriPath(ServiceContainer serviceContainer) {
		FileTreeElement root = serviceContainer.workspaceFileSystem.getWorkspaceDirectory();
  		Path rootPath = FileHandlingUtility.getPath(root);
  		Path projectPath = rootPath.resolve("");
		return projectPath;
	}
	
	/**
	 * Saves FeatureIDE Model into a file
	 * @param fideModel
	 * @param path
	 */
	private static void save(FeatureModell fideModel, String path) { 
	    Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("featureIDE-metamodel", new XMIResourceFactoryImpl());
        ResourceSet resSet = new ResourceSetImpl();
        Resource resource = resSet.createResource(URI.createURI(path));
        resource.getContents().add(fideModel);
        try {
            resource.save(Collections.EMPTY_MAP);
        } catch (IOException e) {
        	RCPMessageProvider.errorMessage("Exporter", "Could not export FeatureIdeModel.");
        	e.printStackTrace();
        }
	}	
	
	/**
	 * Changes XML Header from ECORE to FeatureIDE Standard
	 * @param path
	 * @throws IOException
	 */
	private void changeFileToBeFideLoadable(String path) throws IOException {
		String pathTemp = formatUri(path);
		
		boolean fileTop = true;
		String readLine = "";
		BufferedReader reader = new BufferedReader(new FileReader(pathTemp));
		BufferedWriter writer = new BufferedWriter(new FileWriter(pathTemp.replace(".worker", ".xml")));
		
		reader.readLine();
		readLine = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
		while (readLine != null) {
			if (readLine.contains("FM:FeatureModell") && fileTop) {
				fileTop = false;
				readLine = "<featureModel>";
			}
			else if (readLine.contains("FM:FeatureModell") && !fileTop){
				readLine = "</featureModel>";
			}
			
			writer.write(readLine, 0, readLine.length());
			writer.newLine();
			readLine = reader.readLine();
		}
		
		reader.close();
		writer.close();
		
		deleteWorkerFile(pathTemp);
	}


	/**
	 * Deletes worket file
	 * @param pathTemp
	 */
	private void deleteWorkerFile(String pathTemp) {
		File f = new File(pathTemp);
		f.delete();
	}

	/**
	 * Formats uri
	 * @param path
	 * @return
	 */
	private String formatUri(String path) {
		path = path.replace("file:///", "");
		path = path.replace("%20", " ");
		return path;
	}
	
	
	
}

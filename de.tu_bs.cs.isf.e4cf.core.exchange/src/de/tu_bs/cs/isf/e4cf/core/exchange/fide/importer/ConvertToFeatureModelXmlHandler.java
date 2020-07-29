package de.tu_bs.cs.isf.e4cf.core.exchange.fide.importer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Execute;
import org.xml.sax.SAXException;

import FeatureDiagram.FeatureDiagramm;
import FeatureModel.FeatureModell;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.FeatureDiagramSerialiazer;
import de.tu_bs.cs.isf.familymining.ppu_iec.rcp_e4.EMFModelLoader.impl.EMFModelLoader;
import de.tu_bs.cs.isf.e4cf.core.exchange.fide.util.FeatureIDEConverter;


/**
 * This class is called when the button to convert from the feature ide model to the vat model is pressed.
 * It handles if the button should be visible (only if a convertible file is selected) and the execution of the conversion
 * 
 * @author Patrick Suszek (y0082857) / Dzhakhar Akperov (y0087205)
 * @date 14.12.19
 */
public class ConvertToFeatureModelXmlHandler {

	@Execute
	public void execute(ServiceContainer serviceContainer) throws ParserConfigurationException, SAXException, IOException {	
		String path = serviceContainer.rcpSelectionService.getCurrentSelectionFromExplorer().getAbsolutePath();
		path = path.replace(".xml", ".worker");
		constructEcoreReadableWorkerFile(path);

		FeatureModell fideImpl = (FeatureModell) EMFModelLoader.load(path, "featureIDE-metamodel");
		deleteWorkerFile(path);
		
		FeatureDiagramm fmdImpl = FeatureIDEConverter.convertToFamilyModel(fideImpl);
		
		saveConvertedModel(serviceContainer, fmdImpl);
	}
	
	@CanExecute
	public boolean canExecute() {
		return true;
	}
	
	@Evaluate
	public boolean isVisible(ServiceContainer serviceContainer)
	{
		if (serviceContainer.rcpSelectionService.getCurrentSelectionFromExplorer() == null) {
			return false; //nothing is selected
		}
		String path = serviceContainer.rcpSelectionService.getCurrentSelectionFromExplorer().getAbsolutePath();
		if (path.endsWith(".xml")) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Saves converted model into a file
	 * @param serviceContainer
	 * @param fmdImpl
	 */
	private void saveConvertedModel(ServiceContainer serviceContainer, FeatureDiagramm fmdImpl) {
		FileTreeElement root = serviceContainer.workspaceFileSystem.getWorkspaceDirectory();
		Path rootPath = FileHandlingUtility.getPath(root);
		Path projectPath = rootPath.resolve("");
		FeatureDiagramSerialiazer.save(fmdImpl, projectPath.resolve(E4CStringTable.FEATURE_MODEL_DIRECTORY).toUri() + fmdImpl.getRoot().getName()+".fm");
	}

	/**
	 * Deletes worker file
	 * @param path
	 */
	private void deleteWorkerFile(String path) {
		File f = new File(path);
		f.delete();
	}
	
	/**
	 * Changes XML Header from FeatureIDE to ECORE Standard
	 * @param path
	 * @throws IOException
	 */
	private void constructEcoreReadableWorkerFile(String path) throws IOException {
		boolean fileTop = true;
		String readLine = "";
		BufferedReader reader = new BufferedReader(new FileReader(path.replace(".worker", ".xml")));
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		
		readLine = reader.readLine();
		while (readLine != null) {
			if (readLine.contains("featureModel>") && fileTop){
				fileTop = false;
				readLine = "<FM:FeatureModell xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:FM=\"www.example.com/featureIdeDiagram\">";
			} else if (readLine.contains("featureModel>") && !fileTop) {
				readLine = "</FM:FeatureModell>";
			}
			
			writer.write(readLine, 0, readLine.length());
			writer.newLine();
			readLine = reader.readLine();
		}
		
		reader.close();
		writer.close();
	}
	

}

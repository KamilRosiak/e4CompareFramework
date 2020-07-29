package de.tu_bs.cs.isf.e4cf.parser.import_model;

import java.nio.file.Path;
import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.parser.base.IParserProcess;
import de.tu_bs.cs.isf.e4cf.parser.import_model.page.ImportModelParserPage;

public class ImportModelWizard extends Wizard {
	
	private final String importModelPageName;
	
	public ImportModelWizard(String importModelTitle) { 
		importModelPageName = importModelTitle;
	}
	
	/**
	 * Checks <b>files</b> for the correct format. 
	 * 
	 * @param inputFiles
	 * @param parserApp
	 * @return true if all files have a format specified in <b>parserApp<b>
	 */
	private boolean checkDataFormat(List<Path> inputFiles, List<String> extensions) {
		return inputFiles.stream()
				.allMatch(file -> extensions.stream().anyMatch(extension -> file.toString().endsWith(extension)));
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
	@Override
	public boolean performCancel() {
		dispose();
		return true;
	}
	
	@Override
	public boolean performFinish() {
		IWizardPage wizardPage = getPage(importModelPageName);
		if (wizardPage instanceof ImportModelParserPage) {
			ImportModelParserPage page = (ImportModelParserPage) wizardPage;
			List<Path> inputFiles = page.getInputFiles();
			Path outputDirectory = page.getOutputDirectory();
			IParserProcess parserApp = page.getChoosenParserApplication();
			performParserExec(inputFiles, outputDirectory, parserApp);
			return true;
		} else {
			RCPMessageProvider.errorMessage("Import Model", "Wizard page can not be collected");
			return false;
		}
	}

	private void performParserExec(List<Path> inputFiles, Path outputDirectory, IParserProcess parserApp) {
		if (checkDataFormat(inputFiles, parserApp.getCompatibleFileFormats())) {
			parserApp.start(inputFiles, outputDirectory);			
		} else {
			throw new IllegalArgumentException(
					"The selected parser execution environment is only compatible with the following file formats: "
					+ parserApp.getCompatibleFileFormats());
		}	
	}
}

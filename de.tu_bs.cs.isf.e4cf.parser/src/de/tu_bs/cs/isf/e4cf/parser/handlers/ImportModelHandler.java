package de.tu_bs.cs.isf.e4cf.parser.handlers;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.Point;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPDialogService;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.parser.import_model.ImportModelWizard;
import de.tu_bs.cs.isf.e4cf.parser.import_model.page.FileAddListener;
import de.tu_bs.cs.isf.e4cf.parser.import_model.page.FileSelectionListener;
import de.tu_bs.cs.isf.e4cf.parser.import_model.page.ImportModelParserPage;
import de.tu_bs.cs.isf.e4cf.parser.import_model.page.OutputDirectoryListener;
import de.tu_bs.cs.isf.e4cf.parser.import_model.page.ParserImplListener;
import de.tu_bs.cs.isf.e4cf.parser.import_model.page.ParserTypeListener;
import de.tu_bs.cs.isf.e4cf.parser.plugin.ParserProcessPlugin;

public class ImportModelHandler {
	static final String IMPORT_MODEL_TITLE = "Import Models";
	static final String WIZARD_ICON_PATH = "icons/ToolBar/model_64.png";
	static final Point PAGE_SIZE = new Point(1366, 786);
		
	@Execute
	public void execute(IEventBroker eventBroker, RCPDialogService dialogService, ServiceContainer serviceContainer) {
		Wizard wizard = new ImportModelWizard(IMPORT_MODEL_TITLE);
		
		// get input information
		ImageDescriptor modelImage = serviceContainer.imageService.getImageDescriptor(null, WIZARD_ICON_PATH);
		List<FileTreeElement> selectedElements = serviceContainer.rcpSelectionService.getCurrentSelectionsFromExplorer();
		List<FileTreeElement> parseableFiles = selectedElements.stream().filter(element -> isParseable(element)).collect(Collectors.toList());
		
		// create wizard page
		ImportModelParserPage page = buildImportModelPage(modelImage, parseableFiles);
		page.attachSelectFileBehaviour(new FileSelectionListener(page));
		page.attachAddFileBehaviour(new FileAddListener(page));
		page.attachSelectOutputDirectoryBehaviour(new OutputDirectoryListener(page));
		page.attachParserTypeModificationBehaviour(new ParserTypeListener(page));
		page.attachParserImplModificationBehaviour(new ParserImplListener(page));
		wizard.addPage(page);
		
		WizardDialog wizardDialog = dialogService.constructDialog(IMPORT_MODEL_TITLE, PAGE_SIZE, wizard);
		wizardDialog.open();
	}

	private ImportModelParserPage buildImportModelPage(ImageDescriptor modelImage, List<FileTreeElement> sourceFiles) {
		return new ImportModelParserPage(IMPORT_MODEL_TITLE, IMPORT_MODEL_TITLE, sourceFiles, modelImage);
	}	
	
	@CanExecute
	public boolean canExecute(RCPSelectionService selectionService) {
		return true;
	}
	
	/**
	 * Checks if <b>element</b> is one of the supported input files.
	 * 
	 * @param element file element from the workspace
	 * @return true if the file extension is parseable, false otherwise
	 */
	private boolean isParseable(FileTreeElement element) {
		return ParserProcessPlugin.getParseableExtensions().stream().
				anyMatch(parseableExtension -> !element.isDirectory() && element.getExtension().equals(parseableExtension));
	}
}
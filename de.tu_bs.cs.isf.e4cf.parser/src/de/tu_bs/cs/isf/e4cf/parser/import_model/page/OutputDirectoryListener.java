package de.tu_bs.cs.isf.e4cf.parser.import_model.page;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.DirectoryDialog;

import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;

public class OutputDirectoryListener implements SelectionListener {
	
	private static final String OUTPUT_DIRECTORY_SELECTION_TITLE = "Output Directory Selection";
	private ImportModelParserPage page;
	
	public OutputDirectoryListener(ImportModelParserPage page) {
		this.page = page;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {	
		this.widgetDefaultSelected(e);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
		// set up dialog
		DirectoryDialog fd = new DirectoryDialog(page.getShell());
		try {
			fd.setFilterPath(RCPContentProvider.getCurrentWorkspacePath());
			String outputDirectory = fd.open();
			Path selectedDirectory = Paths.get(outputDirectory);
			page.outputDirectoryList.removeAll();
			page.outputDirectoryList.add(FileHandlingUtility.getAbsolutePath(selectedDirectory));
			page.outputDirectory = selectedDirectory;
		} catch (NullPointerException e) {
			page.outputDirectoryList.removeAll();
			page.outputDirectory = null;
			RCPMessageProvider.errorMessage(OUTPUT_DIRECTORY_SELECTION_TITLE, fd.getFilterPath()+" was not found or is incorrect");
		} finally {
			page.updateWizard();				
		}
	}
}

package de.tu_bs.cs.isf.e4cf.parser.import_model.page;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;

import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;

public class FileSelectionListener implements SelectionListener {
	
	private ImportModelParserPage page;
	
	public FileSelectionListener(ImportModelParserPage page) {
		this.page = page;
	}
	
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		this.widgetDefaultSelected(e);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// set up dialog
		FileDialog fd = new FileDialog(page.getShell() , SWT.OPEN | SWT.MULTI);
		fd.setFilterPath(RCPContentProvider.getCurrentWorkspacePath());
		fd.open();
		
		if (fd.getFileNames().length > 0) {
			// reset list
			page.selectedFiles.clear();
			page.selectedFileList.removeAll();
		}
		
		// store files and write them to the list
		for (String filename : fd.getFileNames()) {
			Path selectedFile = Paths.get(fd.getFilterPath()+"\\"+filename);
			if (Files.exists(selectedFile)) {
				page.selectedFiles.add(selectedFile);
				page.selectedFileList.add(FileHandlingUtility.getAbsolutePath(selectedFile));
			} else {
				RCPMessageProvider.errorMessage("Input File Selection", fd.getFilterPath()+"\\"+filename+" was not found or is incorrect");
			}
		}
		page.updateWizard();
	}
}

package de.tu_bs.cs.isf.e4cf.core.util.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;

@Creatable
@Singleton
public class RCPDialogService {
	private RCPImageService imageService;
	private Shell shell;
	
	@Inject
	public RCPDialogService(RCPImageService imageService, @Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {
		this.imageService = imageService;
		this.shell = shell;
	
	}
	
	public WizardDialog constructDialog(String dialogTitle, Point dialogSize, Wizard wizard) {
		WizardDialog dialog = new WizardDialog(shell, wizard);
		
		dialog.setTitle(dialogTitle);
		WizardDialog.setDefaultImage(imageService.getImage(null, "icons/red_splat_16.png"));
		dialog.setPageSize(dialogSize);
		dialog.setMinimumPageSize(dialogSize);
		return dialog;
	}
	
	/**
	 * Opens a folder dialog and returns the selected folder.
	 * Uses the workspace as root folder.
	 * 
	 * @param shell the parent shell
	 * @return folder path 
	 */
	public String getFolderFromFileSystem(Shell shell) {
		DirectoryDialog dd = new DirectoryDialog(shell , SWT.NONE);
		dd.setFilterPath(RCPContentProvider.getCurrentWorkspacePath());
		return dd.open();
	} 
	
	/**
	 * Opens a folder dialog and returns the selected folder.
	 * 
	 * @param shell the parent shell
	 * @param rootFolder folder initially displayed in the dialog
	 * @return folder path 
	 */
	public String getFolderFromFileSystem(Shell shell, String rootFolder) {
		DirectoryDialog dd = new DirectoryDialog(shell , SWT.NONE);
		dd.setFilterPath(rootFolder);
		return dd.open();
	} 
	
	/**
	 * Opens a multi-file dialog and returns the selected files.
	 * 
	 * @param shell the parent shell
	 * @param rootFolder folder initially displayed in the dialog
	 * @return A list of files (absolute path) from a folder
	 */
	public List<String> getFilesFromFileSystem(Shell shell, String rootFolder) {
		return _getFilesFromFileSystem(shell, rootFolder, SWT.OPEN | SWT.MULTI);
	}
	
	/**
	 * Opens a single-file dialog and returns the selected files.
	 * 
	 * @param shell the parent shell
	 * @param rootFolder folder initially displayed in the dialog
	 * @return A list of files (absolute path) from a folder
	 */
	public String getFileFromFileSystem(Shell shell, String rootFolder) {
		List<String> filepaths = _getFilesFromFileSystem(shell, rootFolder, SWT.OPEN | SWT.SINGLE);
		return !filepaths.isEmpty() ? filepaths.get(0) : null;
	}

	/**
	 * Opens a multi-file dialog and returns the selected files.
	 * Uses the workspace as root folder.
	 * 
	 * 
	 * @param shell the parent shell
	 * @return A list of files (absolute path) from a folder
	 */
	public List<String> getFilesFromFileSystem(Shell shell) {
		return getFilesFromFileSystem(shell, RCPContentProvider.getCurrentWorkspacePath());
	}
	
	/**
	 * Opens a single-file dialog and returns the selected files.
	 * Uses the workspace as root folder.
	 * 
	 * 
	 * @param shell the parent shell
	 * @param rootFolder folder initially displayed in the dialog
	 * @return A list of files (absolute path) from a folder
	 */
	public String getFileFromFileSystem(Shell shell) {
		return getFileFromFileSystem(shell, RCPContentProvider.getCurrentWorkspacePath());
	}
	
	private List<String> _getFilesFromFileSystem(Shell shell, String rootFolder, int style) {
		// set up dialog
		FileDialog fd = new FileDialog(shell , style);
		fd.setFilterPath(rootFolder);
		fd.open();
		
		return Arrays.stream(fd.getFileNames())
			.map(filename -> fd.getFilterPath()+"\\"+filename)
			.collect(Collectors.toList());
	} 
	
	public Shell getShell() {
		return this.shell;
	}
}

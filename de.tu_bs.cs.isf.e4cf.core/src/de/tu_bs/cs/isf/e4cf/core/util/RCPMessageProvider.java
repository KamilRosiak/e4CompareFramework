package de.tu_bs.cs.isf.e4cf.core.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * This is completely stateless utility class and provides a different choice of message dialogs that are usable within the RCP e4 platform.
 * @author {Kamil Rosiak, Alexander Schlie}
 */
public final class RCPMessageProvider {
    @Inject @Named (IServiceConstants.ACTIVE_SHELL) static Shell shell;
	
	
	public static void errorMessage(String title,String message) {
		MessageDialog.openError(shell, title, message);
	}
	
	public static void informationMessage(String title,String message) {
		MessageDialog.openInformation(shell, title, message);
	}
	
	public static boolean questionMessage(String title,String message) {
		return MessageDialog.openQuestion(shell, title, message);
	}
	
	public static String getFilePathDialog(String title, String directory) {
		final FileDialog dialog = new FileDialog(new Shell(), SWT.OPEN);
		dialog.setText(title);
		dialog.setFilterPath(RCPContentProvider.getCurrentWorkspacePath() + directory);
		String result = dialog.open();
		if(result != null) {
			return result;
		}
			return "";
	}
	
	public static List<String> getFilePathsDialog(String title, String directory) {
		final FileDialog dialog = new FileDialog(new Shell(), SWT.OPEN | SWT.MULTI);
		dialog.setText(title);
		dialog.setFilterPath(RCPContentProvider.getCurrentWorkspacePath() + directory);
		String result = dialog.open();
		if(result != null) {
			String filterPath = dialog.getFilterPath();
			return Arrays.stream(dialog.getFileNames()).map(filename -> filterPath+"\\"+filename).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}
	
	
	public static String inputDialog(String title, String message) {
		InputDialog dialog = new InputDialog(new Shell(), title, message, "", null);
		if(dialog.open() == Window.OK) {
			return dialog.getValue();
		} else {
			return "";
		}
	}
	
	/**
	 * @return 0 if first , 1 if second and 2 if third is selected.
	 */
	public static int optionMessage(String title,String message, String first, String second ,String third) {
		MessageDialog dialog = new MessageDialog(new Shell(), title , null,
				message, MessageDialog.INFORMATION, new String[] { first, second, third }, 0);
			int result = dialog.open();
			return result;
	}
}

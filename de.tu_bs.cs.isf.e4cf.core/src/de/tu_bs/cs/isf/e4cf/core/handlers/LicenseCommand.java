package de.tu_bs.cs.isf.e4cf.core.handlers;

import static de.tu_bs.cs.isf.e4cf.core.stringtable.E4CFileTable.ABOUT_24_ICON_PATH;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;;

public class LicenseCommand {
	@Inject RCPImageService _imageService;

	@Execute
	public void execute() {
		Shell dialog = new Shell(Display.getCurrent());
		dialog.setText("License Information");
		dialog.setLayout(new FillLayout(SWT.VERTICAL));
		dialog.setImage(_imageService.getImage(null, ABOUT_24_ICON_PATH));
		Composite comp = new Composite(dialog, SWT.None);
		comp.setLayout(new FillLayout(SWT.VERTICAL));
		Group licenseGroup = new Group(comp,SWT.None);
		licenseGroup.setLayout(new FillLayout(SWT.VERTICAL));
		licenseGroup.setText("License Key");
		Text kamilText = new Text(licenseGroup, SWT.NONE);
		kamilText.setEnabled(false);
		kamilText.setText("Key should be here");
	}
}

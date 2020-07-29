package de.tu_bs.cs.isf.e4cf.core.gui.swt.dialogs;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class InputDialog extends TitleAreaDialog  {
	private Text _var1;
	private String _var1Name;
	
	public InputDialog(Shell parentShell, String var1Name) {
	    super(parentShell);
	    setDefaultImage(Display.getCurrent().getSystemImage(SWT.ICON_INFORMATION));
	    _var1Name = var1Name;
	}
	
	@Override
	public void create() {
	    super.create();
	    setTitle("InputDialog");
	    setMessage("Please Enter the following information", IMessageProvider.INFORMATION);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
	    Composite area = (Composite) super.createDialogArea(parent);
	    Composite container = new Composite(area, SWT.NONE);
	    container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    GridLayout layout = new GridLayout(2, false);
	    container.setLayout(layout);
	
	    createFirst(container);
	
	    return area;
	}
	
	private void createFirst(Composite container) {
	    Label lbtFirstName = new Label(container, SWT.NONE);
	    lbtFirstName.setText(_var1Name);
	
	    GridData dataFirstName = new GridData();
	    dataFirstName.grabExcessHorizontalSpace = true;
	    dataFirstName.horizontalAlignment = GridData.FILL;
	
	    _var1 = new Text(container, SWT.BORDER);
	    _var1.setLayoutData(dataFirstName);
	}

	@Override
	protected boolean isResizable() {
	    return true;
	}
	
	// save content of the Text fields because they get disposed
	// as soon as the Dialog closes
	private void saveInput() {
	    _var1Name = _var1.getText();
	}
	
	@Override
	protected void okPressed() {
	    saveInput();
	    super.okPressed();
	}
	
	public String getFirstVar() {
	    return _var1Name;
	}
	
}


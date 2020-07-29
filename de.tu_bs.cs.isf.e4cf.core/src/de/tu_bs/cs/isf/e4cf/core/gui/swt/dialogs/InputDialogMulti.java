package de.tu_bs.cs.isf.e4cf.core.gui.swt.dialogs;

import java.util.ArrayList;
import java.util.List;

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

public class InputDialogMulti extends TitleAreaDialog  {
	private String [] varNames;
	private List<Text> varText;
	private List<String> varValues;
	
	public InputDialogMulti(Shell parentShell, String ...varNames) {
	    super(parentShell);
	    setDefaultImage(Display.getCurrent().getSystemImage(SWT.ICON_INFORMATION));
	    varValues = new ArrayList<String>();
	    varText = new ArrayList<Text>();
	    this.varNames = varNames;
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
	    for(String name : varNames) {
		    createVar(container,name);
	    }

	    return area;
	}
	
	private void createVar(Composite container, String varName) {
	    Label lbtFirstName = new Label(container, SWT.NONE);
	    lbtFirstName.setText(varName);
	
	    GridData dataFirstName = new GridData();
	    dataFirstName.grabExcessHorizontalSpace = true;
	    dataFirstName.horizontalAlignment = GridData.FILL;
	    Text text = new Text(container, SWT.BORDER);
	    text.setLayoutData(dataFirstName);
	    varText.add(text);
	}

	@Override
	protected boolean isResizable() {
	    return true;
	}
	
	// save content of the Text fields because they get disposed
	// as soon as the Dialog closes
	private void saveInput() {
	    for(Text txt : varText) {
	    	varValues.add(txt.getText());
	    }
	}
	
	@Override
	protected void okPressed() {
	    saveInput();
	    super.okPressed();
	}
	
	public List<String> getVars() {
	    return varValues;
	}
	
}


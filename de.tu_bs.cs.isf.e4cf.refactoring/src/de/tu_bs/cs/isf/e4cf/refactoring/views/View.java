package de.tu_bs.cs.isf.e4cf.refactoring.views;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;

public abstract class View {

	private Button applyButton;
	private Button abortButton;

	protected Shell shell;
	private Display display;

	public Button getApplyButton() {
		return applyButton;
	}

	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	public void setApplyButton(Button applyButton) {
		this.applyButton = applyButton;
	}

	public Button getAbortButton() {
		return abortButton;
	}

	public void setAbortButton(Button abortButton) {
		this.abortButton = abortButton;
	}

	public View(int numberOfColumns, String header) {
		display = Display.getCurrent();
		shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.BORDER | SWT.OK | SWT.SYSTEM_MODAL);
		shell.setSize(800, 800);
		shell.setText(header);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = numberOfColumns;
		gridLayout.makeColumnsEqualWidth = true;
		shell.setLayout(gridLayout);

		setWidgets();

		applyButton = new Button(shell, SWT.PUSH);
		applyButton.setText("Apply");
		applyButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));

		if (numberOfColumns == 3) {
			Label label = new Label(shell, 0);
		}

		abortButton = new Button(shell, SWT.PUSH);
		abortButton.setText("Abort");
		abortButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));

	}

	protected void showView() {
		Rectangle screenSize = display.getPrimaryMonitor().getBounds();
		shell.setLocation((screenSize.width - shell.getBounds().width) / 2,
				(screenSize.height - shell.getBounds().height) / 2);

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public void closeView() {
		shell.close();
	}

	public void checkTreeRecursively(TreeItem item, boolean checked) {

		item.setChecked(checked);

		for (TreeItem child : item.getItems()) {
			checkTreeRecursively(child, checked);
		}

	}

	public abstract void setWidgets();

}

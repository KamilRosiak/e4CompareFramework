package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NewFolderPage extends WizardPage {

	private static final String SELECT_FOLDER_TEXT = "Folder Name:";

	public Label _folderNameLabel;
	public Text _folderNameText;

	public NewFolderPage(String pageName) {
		super(pageName);
	}

	public NewFolderPage(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	@Override
	public void createControl(Composite parent) {
		Composite mainComposite = new Composite(parent, SWT.NONE);
		mainComposite.setLayout(new GridLayout(4, true));
		mainComposite.setLayoutData(new GridData());

		_folderNameLabel = new Label(mainComposite, SWT.NONE);
		_folderNameLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, true));
		_folderNameLabel.setText(SELECT_FOLDER_TEXT);

		_folderNameText = new Text(mainComposite, SWT.LEFT | SWT.SINGLE | SWT.H_SCROLL | SWT.BORDER);
		_folderNameText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 3, 1));
		_folderNameText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateWizard();
			}
		});

		setControl(mainComposite);
	}

	public String getFolderName() {
		return _folderNameText != null ? _folderNameText.getText() : "defaultFolderName";
	}

	public boolean isFolderNameSpecified() {
		if (_folderNameText != null && !_folderNameText.getText().isEmpty()) {
			String folderName = _folderNameText.getText();
			char firstCharacter = folderName.charAt(0);
			if (folderName.trim().length() > 0 && (Character.isLetter(firstCharacter) || firstCharacter == '_')) {
				return true;
			}
		}
		return false;
	}

	public void updateWizard() {
		getWizard().getContainer().updateButtons();
	}

}

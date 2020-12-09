/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * A Wizard which let's clients copy one or more files into a selected directory
 * or project.
 * 
 * @author Oliver Urbaniak
 *
 */
public class FileImportWizard extends Wizard {
	private static final String WIZARD_PAGE_TEXT = "Choose the files you want to import";
	private static final String WIZARD_PAGE_TITLE = "File Import";
	private static final String WIZARD_TITLE = "File Import Wizard";

	private Shell _shell;
	private ImageDescriptor _image;
	private Text _pathsText;
	private Button _selectFilesButton;
	private List<Path> _selectedPaths = new ArrayList<>();

	public FileImportWizard(Shell shell, ImageDescriptor imageDesc) {
		_shell = shell;
		_image = imageDesc;
	}

	@Override
	public void addPages() {
		this.setWindowTitle(WIZARD_TITLE);
		this.setDefaultPageImageDescriptor(_image);
		this.addPage(new WizardPage(WIZARD_PAGE_TITLE, WIZARD_PAGE_TEXT, _image) {

			@Override
			public void createControl(Composite parent) {
				Composite mainComposite = new Composite(parent, SWT.NONE);
				GridLayout mainLayout = new GridLayout(2, false);
				mainLayout.verticalSpacing = 20;
				mainComposite.setLayout(mainLayout);

				new Label(mainComposite, SWT.NONE).setText(""); // padding
				buildSelectionButton(mainComposite);
				new Label(mainComposite, SWT.NONE).setText(""); // padding
				buildFileOutputText(mainComposite);

				super.setControl(mainComposite);
			}

			private void buildSelectionButton(Composite mainComposite) {
				_selectFilesButton = new Button(mainComposite, SWT.PUSH);
				GridData buttonLayoutData = new GridData(SWT.LEFT, SWT.BOTTOM, true, true);
				_selectFilesButton.setLayoutData(buttonLayoutData);
				_selectFilesButton.setText("Select Files ...");
				_selectFilesButton.addSelectionListener(buildFileSelectionListener());
			}

			private void buildFileOutputText(Composite mainComposite) {
				_pathsText = new Text(mainComposite,
						SWT.MULTI | SWT.READ_ONLY | SWT.LEFT | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
				_pathsText.setText("");
				Color r = new org.eclipse.swt.graphics.Color(null, 255, 255, 255);
				_pathsText.setBackground(r);
				GridData pathsTextGD = new GridData(SWT.FILL, SWT.FILL, true, true);
				pathsTextGD.verticalSpan = 2;
				pathsTextGD.heightHint = 200;
				_pathsText.setLayoutData(pathsTextGD);
			}

			private SelectionListener buildFileSelectionListener() {
				return new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						// open file dialog
						FileDialog fd = new FileDialog(_shell, SWT.MULTI);
						fd.open();
						String[] filenames = fd.getFileNames();
						String absRootPath = fd.getFilterPath();

						addFiles(absRootPath, filenames);
					}

					private void addFiles(String absRootPath, String[] filenames) {
						_pathsText.setText("");
						if (!_selectedPaths.isEmpty())
							_selectedPaths.clear();
						for (String filename : filenames) {
							Path file = Paths.get(absRootPath + "\\" + filename);
							if (Files.exists(file) && Files.isRegularFile(file)) {
								_selectedPaths.add(file);
								_pathsText.append(file.toString() + "\n");
							}
						}
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						this.widgetSelected(e);
					}
				};
			}
		});
	}

	public List<Path> getSourceFiles() {
		return _selectedPaths;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		return !_selectedPaths.isEmpty();
	}

}

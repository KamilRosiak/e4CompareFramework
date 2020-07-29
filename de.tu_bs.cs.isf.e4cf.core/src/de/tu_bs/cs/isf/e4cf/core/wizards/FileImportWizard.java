/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.core.wizards;

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
 * A Wizard which let's clients copy one or more files into a selected directory or project.
 * 
 * @author Oliver Urbaniak
 *
 */
public class FileImportWizard extends Wizard {
	private static final String WIZARD_PAGE_TEXT = "Choose the files you want to import";
	private static final String WIZARD_PAGE_TITLE = "File Import";
	private static final String WIZARD_TITLE = "File Import Wizard";
	
	private Shell shell;
	private ImageDescriptor image;	
	private Text pathsText;
	private Button selectFilesButton;
	private List<Path> selectedPaths = new ArrayList<>();
	
	public FileImportWizard(Shell shell, ImageDescriptor imageDesc) {
		this.shell = shell;
		this.image = imageDesc;
	}
	
	@Override
	 public void addPages() {
		this.setWindowTitle(WIZARD_TITLE);
		this.setDefaultPageImageDescriptor(image);
		this.addPage(new WizardPage(WIZARD_PAGE_TITLE, WIZARD_PAGE_TEXT, image) {
			
			@Override
			public void createControl(Composite parent) {
				Composite mainComposite = new Composite(parent, SWT.NONE);
				GridLayout mainLayout = new GridLayout(2, false);
				mainLayout.verticalSpacing = 20;
				mainComposite.setLayout(mainLayout);
				
				new Label(mainComposite,SWT.NONE).setText(""); // padding
				buildSelectionButton(mainComposite);
				new Label(mainComposite,SWT.NONE).setText(""); // padding
				buildFileOutputText(mainComposite);
								
				super.setControl(mainComposite);
			}
			
			private void buildSelectionButton(Composite mainComposite) {
				selectFilesButton = new Button(mainComposite, SWT.PUSH);
				GridData buttonLayoutData = new GridData(SWT.LEFT, SWT.BOTTOM, true, true);
				selectFilesButton.setLayoutData(buttonLayoutData);
				selectFilesButton.setText("Select Files ...");
				selectFilesButton.addSelectionListener(buildFileSelectionListener());
			}

			private void buildFileOutputText(Composite mainComposite) {
				pathsText = new Text(mainComposite, SWT.MULTI | SWT.READ_ONLY | SWT.LEFT | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
				pathsText.setText("");
				Color r = new org.eclipse.swt.graphics.Color(null, 255, 255, 255);
				pathsText.setBackground(r);
				GridData pathsTextGD = new GridData(SWT.FILL, SWT.FILL, true, true);
				pathsTextGD.verticalSpan = 2;
				pathsTextGD.heightHint = 200;
				pathsText.setLayoutData(pathsTextGD);
			}

			private SelectionListener buildFileSelectionListener() {
				return new SelectionListener() {			
					@Override	
					public void widgetSelected(SelectionEvent e) {				
						// open file dialog
						FileDialog fd = new FileDialog(shell, SWT.MULTI);
						fd.open();
						String[] filenames = fd.getFileNames();
						String absRootPath = fd.getFilterPath();
						
						addFiles(absRootPath, filenames);
					}

					private void addFiles(String absRootPath, String[] filenames) {
						pathsText.setText("");
						if (!selectedPaths.isEmpty()) selectedPaths.clear();
						for (String filename : filenames) {
							Path file = Paths.get(absRootPath+"\\"+filename);
							if (Files.exists(file) && Files.isRegularFile(file)) {
								selectedPaths.add(file);
								pathsText.append(file.toString()+"\n");
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
		return selectedPaths;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {		
		return !selectedPaths.isEmpty();
	}

}

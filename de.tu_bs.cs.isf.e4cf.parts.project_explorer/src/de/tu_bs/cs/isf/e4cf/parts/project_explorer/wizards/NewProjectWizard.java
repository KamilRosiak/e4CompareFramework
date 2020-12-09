package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CEventTable;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;

/**
 * This is a wizard that allows a user to create a new project in the current
 * workspace.
 * 
 * @author {Kamil Rosiak}
 */
public class NewProjectWizard extends Wizard {
	private Text _input;
	private Composite _container;
	private ImageDescriptor _imgDes;

	IEventBroker _eventBroker;
	@Inject
	RCPImageService _imageService;

	public NewProjectWizard(IEventBroker broker, RCPImageService imageService) {
		super();
		_imageService = imageService;
		_eventBroker = broker;
		addPage();
	}

	private void addPage() {
		_imgDes = _imageService.getImageDescriptor(null, "icons/Explorer_View/items/project64.png");
		this.addPage(new WizardPage("New Project Wizard", "Please choose a name for the new project", _imgDes) {
			@Override
			public void createControl(Composite parent) {
				_container = new Composite(parent, SWT.NONE);
				GridLayout layout = new GridLayout();
				_container.setLayout(layout);
				layout.numColumns = 2;
				Label label1 = new Label(_container, SWT.NONE);
				label1.setText("Project Name:");

				_input = new Text(_container, SWT.BORDER | SWT.SINGLE);
				_input.setText("");
				_input.addKeyListener(new KeyListener() {

					@Override
					public void keyPressed(KeyEvent e) {
					}

					@Override
					public void keyReleased(KeyEvent e) {
						if (!_input.getText().isEmpty()) {
							setPageComplete(true);
						}
					}
				});
				GridData gd = new GridData(GridData.FILL_HORIZONTAL);
				_input.setLayoutData(gd);
				// required to avoid an error in the system
				setControl(_container);
				setPageComplete(false);
			}
		});
	}

	@Override
	public boolean performFinish() {
		System.out.println(_input.getText());
		_eventBroker.post(E4CEventTable.NEW_IEC_PROJECT_EVENT, _input.getText());
		return true;
	}
}

package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller.RecursiveCopyOptionsController;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;

/**
 * A Wizard Page that lets the user choose from two different recursive copying
 * options.
 */
public class ChooseRecursiveCopyOptionPage extends WizardPage {

	private IEclipseContext context;

	private boolean copyRecursivly = true;

	public ChooseRecursiveCopyOptionPage(String pageName, IEclipseContext context) {
		super(pageName);
		this.context = context;
	}

	@Override
	public void createControl(Composite parent) {
		FXCanvas canvas = new FXCanvas(parent, SWT.None);
		FXMLLoader<RecursiveCopyOptionsController> loader = new FXMLLoader<RecursiveCopyOptionsController>(context,
				StringTable.BUNDLE_NAME, FileTable.RECURSIVE_COPY_OPTIONS_PAGE_FXML);
		Scene scene = new Scene(loader.getNode());

		canvas.setScene(scene);

		loader.getController().toggleGroup();

		loader.getController().recursiveRB.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				copyRecursivly = isNowSelected;
				update();
			}
		});

		setControl(canvas);
	}

	public boolean copyRecursivly() {
		return copyRecursivly;
	}

	private void update() {
		getWizard().getContainer().updateButtons();
	}

}

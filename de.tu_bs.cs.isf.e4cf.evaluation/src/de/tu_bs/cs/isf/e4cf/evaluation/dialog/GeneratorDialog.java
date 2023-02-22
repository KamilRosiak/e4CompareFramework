package de.tu_bs.cs.isf.e4cf.evaluation.dialog;

import java.nio.file.Paths;
import java.util.Optional;

import org.eclipse.e4.core.contexts.IEclipseContext;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CFileTable;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

public class GeneratorDialog {
	private final static String FXML_PATH = "de/tu_bs/cs/isf/e4cf/evaluation/dialog/CloneGeneratorDialog.fxml";

	private Dialog<GeneratorOptions> dialog;
	private GeneratorViewController generatorViewController;

	public GeneratorDialog(IEclipseContext context, RCPImageService imageService) {

		dialog = new Dialog<GeneratorOptions>();
		dialog.setTitle("Generate Variant Options");

		final DialogPane pane = dialog.getDialogPane();
		pane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		final Stage stage = (Stage) pane.getScene().getWindow();
		stage.getIcons().add(imageService.getFXImage(null, E4CFileTable.FRAMEWORK_LOGO_SMALL).getImage());

		FXMLLoader<GeneratorViewController> loader = new FXMLLoader<>(context, 
				"de.tu_bs.cs.isf.e4cf.evaluation", FXML_PATH);
		generatorViewController = loader.getController();
		generatorViewController.init();
		pane.setContent(loader.getNode());

		dialog.setResultConverter(dialogBtn -> {
			if (dialogBtn == ButtonType.OK) {
				return generatorViewController.getOptions();
			}
			return null;
		});
	}
	
	public Optional<GeneratorOptions> open() {
		return dialog.showAndWait();
	}

}

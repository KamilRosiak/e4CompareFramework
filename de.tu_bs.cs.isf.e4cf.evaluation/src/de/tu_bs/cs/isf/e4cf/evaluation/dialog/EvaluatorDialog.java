package de.tu_bs.cs.isf.e4cf.evaluation.dialog;

import java.util.Optional;

import org.eclipse.e4.core.contexts.IEclipseContext;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CFileTable;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

public class EvaluatorDialog {
	private static final String FXML_PATH = "src/de/tu_bs/cs/isf/e4cf/evaluation/dialog/VariantEvaluatorDialog.fxml";
	
	private Dialog<EvaluatorOptions> dialog;
	private EvaluatorViewController controller;
	
	public EvaluatorDialog(IEclipseContext context, RCPImageService imageService) {
		dialog = new Dialog<>();
		dialog.setTitle("Evaluate generated variants");
		
		final DialogPane pane = dialog.getDialogPane();
		pane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		
		final Stage stage = (Stage) pane.getScene().getWindow();
		stage.getIcons().add(imageService.getFXImage(null, E4CFileTable.FRAMEWORK_LOGO_SMALL).getImage());
		
		FXMLLoader<EvaluatorViewController> loader = new FXMLLoader<>(context, 
				"de.tu_bs.cs.isf.e4cf.evaluation", FXML_PATH);
		controller = loader.getController();
		controller.init();
		pane.setContent(loader.getNode());

		// At least one evaluation method has to be selected
		BooleanBinding atLeastOneSelected = Bindings.createBooleanBinding(
				() -> !(
					controller.checkIntra.isSelected() ||
					controller.checkInter.isSelected() ||
					controller.checkTaxonomy.isSelected()
				), 
				controller.checkIntra.selectedProperty(),
				controller.checkInter.selectedProperty(),
				controller.checkTaxonomy.selectedProperty());
		pane.lookupButton(ButtonType.OK).disableProperty().bind(atLeastOneSelected);
		
		dialog.setResultConverter(dialogBtn -> {
			if (dialogBtn == ButtonType.OK) {
				return controller.getOptions();
			}
			return null;
		});
	}
	
	public Optional<EvaluatorOptions> open() {
		return dialog.showAndWait();
	}
	
}

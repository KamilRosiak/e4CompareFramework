package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.base_wizard;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * basic wizard page with navigation, cancel and finish options
 */
public abstract class WizardPage extends VBox {
	protected Button priorButton = new Button("_Previous");
    protected Button nextButton = new Button("N_ext");
    protected Button cancelButton = new Button("Cancel");
    protected Button finishButton = new Button("_Finish");

    protected WizardPage(String title) {
        Label label = new Label(title);
        label.setStyle("-fx-font-weight: bold; -fx-padding: 0 0 5 0;");
        setId(title);
        setSpacing(5);
        setStyle("-fx-padding:10; -fx-border-color: derive(honeydew, -30%); -fx-border-width: 3;");

        Region spring = new Region();
        VBox.setVgrow(spring, Priority.ALWAYS);
        getChildren().addAll(getContent(), spring, getButtons());

        priorButton.setOnAction(event -> priorPage());
        nextButton.setOnAction(event -> nextPage());
        cancelButton.setOnAction(event -> getWizard().cancel());
        finishButton.setOnAction(event -> getWizard().finish());
    }

    protected HBox getButtons() {
        Region spring = new Region();
        HBox.setHgrow(spring, Priority.ALWAYS);
        HBox buttonBar = new HBox(5);
        cancelButton.setCancelButton(true);
        finishButton.setDefaultButton(true);
        buttonBar.getChildren().addAll(spring, priorButton, nextButton, cancelButton, finishButton);
        return buttonBar;
    }

    abstract protected Parent getContent();

    protected boolean hasNextPage() {
        return getWizard().hasNextPage();
    }

    protected boolean hasPriorPage() {
        return getWizard().hasPriorPage();
    }

    protected void nextPage() {
        getWizard().nextPage();
    }

    protected void priorPage() {
        getWizard().priorPage();
    }

    protected void navTo(String id) {
        getWizard().navTo(id);
    }

    protected Wizard getWizard() {
        return (Wizard) getParent();
    }

    protected void manageButtons() {
        if (!hasPriorPage()) {
            priorButton.setDisable(true);
        }

        if (!hasNextPage()) {
            nextButton.setDisable(true);
        }
    }
    
    public Button createButton(String label, int width) {
    	Button btn = new Button(label);
    	btn.setMinWidth(width);
		return btn;
    	
    }
}
package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.base_wizard;

import java.util.Stack;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;

/**
 * Basic wizard infrastructure class,
 * provides logic for navigating between pages
 * 
 */
public class Wizard extends StackPane {
    private static final int UNDEFINED = -1;
    private ObservableList<WizardPage> pages = FXCollections.observableArrayList();
    private Stack<Integer> history = new Stack<>();
    private int curPageIdx = UNDEFINED;

    public Wizard(WizardPage... nodes) {
        pages.addAll(nodes);
        navTo(0);
        setStyle("-fx-padding: 10;");
    }

    void nextPage() {
        if (hasNextPage()) {
            navTo(curPageIdx + 1);
        }
    }

    void priorPage() {
        if (hasPriorPage()) {
            navTo(history.pop(), false);
        }
    }

    boolean hasNextPage() {
        return (curPageIdx < pages.size() - 1);
    }

    boolean hasPriorPage() {
        return !history.isEmpty();
    }

    void navTo(int nextPageIdx, boolean pushHistory) {
        if (nextPageIdx < 0 || nextPageIdx >= pages.size()) return;
        if (curPageIdx != UNDEFINED) {
            if (pushHistory) {
                history.push(curPageIdx);
            }
        }

        WizardPage nextPage = pages.get(nextPageIdx);
        curPageIdx = nextPageIdx;
        getChildren().clear();
        getChildren().add(nextPage);
        nextPage.manageButtons();
    }

    void navTo(int nextPageIdx) {
        navTo(nextPageIdx, true);
    }

    void navTo(String id) {
        if (id == null) {
            return;
        }

        pages.stream()
                .filter(page -> id.equals(page.getId()))
                .findFirst()
                .ifPresent(page ->
                                navTo(pages.indexOf(page))
                );
    }

    public void finish() {
    }

    public void cancel() {
    }
}



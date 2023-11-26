package de.tu_bs.cs.isf.e4cf.featuremodel.core.handler;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature.FMEditorPaneView;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

public class HotkeyHandler implements EventHandler<KeyEvent> {
	private FMEditorPaneView view;
	
	private final KeyCombination formatCombo = new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
	
	public HotkeyHandler(FMEditorPaneView view) {
		this.view = view;
	}

	@Override
	public void handle(KeyEvent event) {
		if (formatCombo.match(event)) {
			this.view.formatDiagram();
		}
		
	}

}

package de.tu_bs.cs.isf.e4cf.text_editor;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.text_editor.stringtable.EditorST;
import de.tu_bs.cs.isf.e4cf.text_editor.view.TextEditor;
import de.tu_bs.cs.isf.e4cf.text_editor.view.TextEditorMenu;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Sets up supporting structure of the TextEditor and handles events.
 * 
 * @author Cedric Kapalla, Soeren Christmann, Lukas Cronauer, Erwin Wijaya
 *
 */
public class TextEditorViewController {
	private TextEditorMenu textEditor;

	/**
	 * Creates the Text Editor from the given .fxml-file.
	 * 
	 * @param parent   anchors the canvas into the program
	 * @param services
	 * @param context  is needed to load the .fxml
	 * @throws IOException
	 * @author Soeren Christmann, Cedric Kapalla, Lukas Cronauer, Erwin Wijaya
	 */
	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services, IEclipseContext context) throws IOException {
		FXCanvas canvans = new FXCanvas(parent, SWT.None);
		FXMLLoader<TextEditorMenu> loader = new FXMLLoader<TextEditorMenu>(context, EditorST.BUNDLE_NAME,
				EditorST.TEXT_EDITOR_MENU_VIEW_FXML);

		Scene scene = new Scene(loader.getNode());
		loader.getController().setScene(scene);
		textEditor = loader.getController();
		// scene.getStylesheets().add(TEXT_EDITOR_CSS_LOCATION);
		canvans.setScene(scene);
	}

	/**
	 * Receives an event call from FileExtension to open a given file. This got a
	 * custom function because .txt-files containing umlauts can not be opened by
	 * FileStreamUtil; it is the same function, but with ISO_8859_1-encoding instead
	 * of UTF_8.
	 * 
	 * 
	 * @param element path of the element to be opened
	 * @author Cedric Kapalla, Soeren Christmann, Lukas Cronauer, Erwin Wijaya
	 */
	@Optional
	@Inject
	public void openFile(@UIEventTopic(EditorST.FILE_OPENED) FileTreeElement element) {
		Path filePath = Paths.get(element.getAbsolutePath());
		// Special Case for .txt Files
		if (element.getAbsolutePath().endsWith(".txt")) {
			StringBuilder contentBuilder = new StringBuilder();
			try (Stream<String> stream = Files.lines(Paths.get(filePath.toUri()), StandardCharsets.ISO_8859_1)) {
				stream.forEach(s -> contentBuilder.append(s).append("\n"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			String content = contentBuilder.toString();
			textEditor.textEditorViewController.loadTab(element.getAbsolutePath(), content);
		} else {
			String content = FileStreamUtil.readLineByLine(Paths.get(element.getAbsolutePath()));
			textEditor.textEditorViewController.loadTab(element.getAbsolutePath(), content);
		}
	}
}
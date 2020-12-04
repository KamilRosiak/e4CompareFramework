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

import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TextEditorViewController {
	private TextEditor textEditor;

	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services, IEclipseContext context) throws IOException {
		FXCanvas canvans = new FXCanvas(parent, SWT.None);
		FXMLLoader<TextEditor> loader = new FXMLLoader<TextEditor>(context, EditorST.BUNDLE_NAME,
				EditorST.TEXT_EDITOR_VIEW_FXML);

		Scene scene = new Scene(loader.getNode());
		loader.getController().initFileUtils(scene);
		textEditor = loader.getController();
		// scene.getStylesheets().add(TEXT_EDITOR_CSS_LOCATION);
		canvans.setScene(scene);
	}

	/**
	 * Receives an event call from TextFileExtension to open a given file. 
	 * This is a custom function because .txt-files containing umlauts can not be opened
	 * by FileStreamUtil; it is the same function, but with ISO_8859_1-encoding instead of UTF_8. 
	 * 
	 * 
	 * @param element path of the element to be opened
	 * @author Cedric Kapalla, Soeren Christmann, Lukas Cronauer, Erwin Wijaya
	 */
	@Optional
	@Inject
	public void openTxtFile(@UIEventTopic(EditorST.TXT_FILE_OPENED) FileTreeElement element) {
		Path filePath = Paths.get(element.getAbsolutePath());
		StringBuilder contentBuilder = new StringBuilder();

		try (Stream<String> stream = Files.lines(Paths.get(filePath.toUri()), StandardCharsets.ISO_8859_1)) {			  
			  stream.forEach(s -> contentBuilder.append(s).append("\n"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String content = contentBuilder.toString();
		textEditor.loadTab(element.getAbsolutePath(), content);
	}

	/**
	 * Receives an event call from JavaFileExtension to open a given file.
	 * 
	 * @param element path of the element to be opened
	 * @author Cedric Kapalla, Soeren Christmann, Lukas Cronauer, Erwin Wijaya
	 */
	@Optional
	@Inject
	public void openJavaFile(@UIEventTopic(EditorST.JAVA_FILE_OPENED) FileTreeElement element) {
		String content = FileStreamUtil.readLineByLine(Paths.get(element.getAbsolutePath()));
		textEditor.loadTab(element.getAbsolutePath(), content);
	}

	/**
	 * Receives an event call from XMLFileExtension to open a given file.
	 * 
	 * @param element path of the element to be opened
	 * @author Cedric Kapalla, Soeren Christmann, Lukas Cronauer, Erwin Wijaya
	 */
	@Optional
	@Inject
	public void openXmlFile(@UIEventTopic(EditorST.XML_FILE_OPENED) FileTreeElement element) {
		String content = FileStreamUtil.readLineByLine(Paths.get(element.getAbsolutePath()));
		textEditor.loadTab(element.getAbsolutePath(), content);
	}
}

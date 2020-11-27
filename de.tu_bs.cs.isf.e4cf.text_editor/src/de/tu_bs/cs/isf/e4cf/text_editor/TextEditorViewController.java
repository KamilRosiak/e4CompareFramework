package de.tu_bs.cs.isf.e4cf.text_editor;

import java.io.File;
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
import javafx.scene.control.TextArea;

public class TextEditorViewController {
	private static final String TEXT_EDITOR_VIEW_FXML = "/ui/view/TextEditorView.fxml";
	private TextEditor textEditor;
	private FileUtils fileUtils = new FileUtils();
	

	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services, IEclipseContext context) throws IOException {
		FXCanvas canvans = new FXCanvas(parent, SWT.None);
		FXMLLoader<TextEditor> loader = new FXMLLoader<TextEditor>(context, "de.tu_bs.cs.isf.e4cf.text_editor",
				TEXT_EDITOR_VIEW_FXML);

		Scene scene = new Scene(loader.getNode());
		loader.getController().initFileUtils(scene);
		textEditor = loader.getController();
		// scene.getStylesheets().add(TEXT_EDITOR_CSS_LOCATION);
		canvans.setScene(scene);
	}

	/**
	 * Receives an event call from TextFileExtension to open a given file.
	 * 
	 * @param element path of the element to be opened
	 * @author Cedric Kapalla, Soeren Christmann, Lukas Cronauer
	 */
	@Optional
	@Inject
	public void openTxtFile(@UIEventTopic(EditorST.TXT_FILE_OPENED) FileTreeElement element) {
		TextArea textField = textEditor.getTextarea();
		System.out.println(element.getAbsolutePath());	
		File file = new File(element.getAbsolutePath());
	        String content = fileUtils.readFile(file);
	        if (!content.isEmpty()) {
	            textField.setText(content);
	        }
	    }
	/**
	 * Receives an event call from JavaFileExtension to open a given file.
	 * 
	 * @param element path of the element to be opened
	 * @author Cedric Kapalla, Soeren Christmann, Lukas Cronauer
	 */
	@Optional
	@Inject
	public void openJavaFile(@UIEventTopic(EditorST.JAVA_FILE_OPENED) FileTreeElement element) {
		System.out.println(element); // placeholder
	}

	/**
	 * Receives an event call from XMLFileExtension to open a given file.
	 * 
	 * @param element path of the element to be opened
	 * @author Cedric Kapalla, Soeren Christmann, Lukas Cronauer
	 */
	@Optional
	@Inject
	public void openXmlFile(@UIEventTopic(EditorST.XML_FILE_OPENED) FileTreeElement element) {
		System.out.println(element); // placeholder
	}
}

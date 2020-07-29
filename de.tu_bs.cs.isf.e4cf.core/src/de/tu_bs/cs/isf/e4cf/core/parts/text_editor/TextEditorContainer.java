
package de.tu_bs.cs.isf.e4cf.core.parts.text_editor;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CEventTable;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;

public class TextEditorContainer {

	@Inject private MPart _textEditorPart;
	
	private ITextEditor _textEditor = null;
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		
		// get the file extension
		String fileExtension;
		File toBeOpenedFile = new File(_textEditorPart.getPersistedState().get(ITextEditor.ABSTRACT_OBJECT_PATH));
		if (!toBeOpenedFile.exists() || !toBeOpenedFile.isFile()) {
			constructErrorMessage();
			return;
		} else {
			String[] filenameAndExtension = toBeOpenedFile.toPath().getFileName().toString().split("\\.");
			if (filenameAndExtension.length == 2) {
				fileExtension = filenameAndExtension[1];				
			} else {
				fileExtension = ITextEditor.ALL_EXTENSIONS;
			}
		}
		
		try {
			// create text editor
			_textEditor = TextEditorFactory.getEditorFor(fileExtension);
			_textEditor.createControls(parent);
			_textEditor.openFile(toBeOpenedFile);
			
			// listener for modification
			_textEditor.addListener(SWT.Modify, new Listener() {
				@Override
				public void handleEvent(Event event) {
					_textEditorPart.setDirty(_textEditor.isDirty());
				}
			});
			
		} catch (IOException e) {
			e.printStackTrace();
			constructErrorMessage();
		}
		
	}

	@PreDestroy
	public void preDestroy() {
		_textEditor.closeFile();
	}

	@Focus
	public void onFocus() {
	}

	@Persist
	public void save(IEventBroker eventBroker) {
		try {
			_textEditor.save();
			eventBroker.send(E4CEventTable.EVENT_REFRESH_PROJECT_VIEWER, null);
			_textEditorPart.setDirty(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void constructErrorMessage() {
		RCPMessageProvider.errorMessage("Open File in Text Editor", "The file cannot be displayed correctly");
	}
}
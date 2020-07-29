package de.tu_bs.cs.isf.e4cf.simple_text_editor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import de.tu_bs.cs.isf.e4cf.core.parts.text_editor.ITextEditor;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;

public class SimpleTextEditor implements ITextEditor {

	protected File _currentFile;
	protected Text _textField;
	protected boolean _opened;
	
	public SimpleTextEditor() {
		_opened = false;
	}
	
	@Override
	public void createControls(Composite parent) {
		_textField = new Text(parent, SWT.LEFT | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		_textField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		_textField.setParent(parent);
		_textField.setText("");
	}

	public void addListener(int eventType, Listener listener) {
		_textField.addListener(eventType, listener);
	}

	public void removeListener(int eventType, Listener listener) {
		_textField.removeListener(eventType, listener);
	}
	
	@Override
	public void openFile(File sourceFile) throws IOException {
		
		Runnable readAndUpdateText = new Runnable() {
			@Override
			public void run() {
				try {
					String fullText = "";
					List<String> lines = Files.readAllLines(Paths.get(sourceFile.toURI()), StandardCharsets.UTF_8);
					for (String line : lines) {
						fullText += line + "\n";
					}
							
					_currentFile = sourceFile;
					_opened = true;
					initializeText(fullText.substring(0, fullText.length() - 1));							
							
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		};
		
		new Thread(readAndUpdateText).start();
	}

	@Override
	public void closeFile() {
	}

	@Override
	public void save() throws IOException {
		saveToFile(_currentFile);
	}

	@Override
	public void saveToFile(File destination) throws IOException {		
		if (!_opened) return;
		if (!destination.exists()) destination.createNewFile();	
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(destination);
			if (_textField.getText().trim().length() > 0) {
				writer.write(_textField.getText());	
			}			
		} finally {			
			writer.close();
		}
	}
	
	@Override
	public File getSourceFile() {
		return _currentFile;
	}
	
	@Override
	public synchronized void append(String text) {
		_textField.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				_textField.append(text);			
			}
		});
	}
	
	@Override
	public synchronized void insert(String text, int index) {
		if (!_opened) {
			RCPMessageProvider.informationMessage("Insert Text", "The file is not loaded or was not opened until now.");
			return;
		}
		
		if (0 <= index && index <= _textField.getLineCount()) {
			List<String> lines = getDisplayedText();
			lines.add(index, text);
			
			StringBuilder sb = new StringBuilder();
			for (String line : lines) {
				sb.append(line+"\n");
			}
			sb.deleteCharAt(sb.length() - 2);
			
			_textField.getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					_textField.setText(sb.toString().trim());
				}
			});
		}
	}
	
	public synchronized void initializeText(String text) {
		_textField.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				_textField.setText(text);
			}
		});
	}
	
	@Override
	public synchronized void resetText(String text) {
		_textField.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				_textField.setText(text);
			}
		});
	}

	@Override
	public List<String> getDisplayedText() {
		String displayedText = _textField.getText();
		List<String> lines = new ArrayList<>();
		
		String[] displayedTextLines = displayedText.split("\n | \r\n");
		
		for (String line : displayedTextLines) {
			lines.add(line);
		}
		
		return lines;
	}

	@Override
	public boolean isDefault() {
		return true;
	}

	@Override
	public List<String> supportedExtensions() {
		List<String> list = new ArrayList<>();
		list.add(ALL_EXTENSIONS);
		return list;
	}

	@Override
	public boolean isDirty() {
		return true;
	}

	@Override
	public String getId() {
		return "simple_text_editor";
	}


}

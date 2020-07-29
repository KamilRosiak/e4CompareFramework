package de.tu_bs.cs.isf.e4cf.core.parts.text_editor;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;

public interface ITextEditor {
	
	// corresponds to the abstract object path 
	public static final String ABSTRACT_OBJECT_PATH = "path";	
	
	public static final String ALL_EXTENSIONS = "*";
	
	//////////////////////////////////
	// File IO
	//////////////////////////////////
	
	
	public void openFile(File sourceFile) throws IOException;
	
	public void closeFile();
	
	public void save() throws IOException;
	
	public void saveToFile(File destination) throws IOException;
	
	public File getSourceFile();
	
	//////////////////////////////////
	// SWT Structure
	//////////////////////////////////
	
	
	public void createControls(Composite parent);	
	
	public void addListener(int eventType, Listener listener);

	public void removeListener(int eventType, Listener listener);

	//////////////////////////////////
	// Text IO
	//////////////////////////////////
	
	public List<String> getDisplayedText();
	
	public void append(String text);
	
	public void insert(String text, int index);
	
	public void resetText(String text);
	
	//////////////////////////////////
	// Editor Information
	//////////////////////////////////
	
	public boolean isDirty();
	
	public boolean isDefault();
	
	public List<String> supportedExtensions();

	public String getId();
}

package de.tu_bs.cs.isf.e4cf.parser.import_model.page;

import static de.tu_bs.cs.isf.e4cf.core.stringtable.ImportModelWizardStringTable.DEFAULT_PARSER_TYPE;
import static de.tu_bs.cs.isf.e4cf.core.stringtable.ImportModelWizardStringTable.DESCRIPTION;
import static de.tu_bs.cs.isf.e4cf.core.stringtable.ImportModelWizardStringTable.FILE_SELECTION_ADD_BUTTON_LABEL;
import static de.tu_bs.cs.isf.e4cf.core.stringtable.ImportModelWizardStringTable.FILE_SELECTION_BUTTON_LABEL;
import static de.tu_bs.cs.isf.e4cf.core.stringtable.ImportModelWizardStringTable.OUTPUT_DIRECTORY_BUTTON_LABEL;
import static de.tu_bs.cs.isf.e4cf.core.stringtable.ImportModelWizardStringTable.PARSER_IMPL_LABEL_TEXT;
import static de.tu_bs.cs.isf.e4cf.core.stringtable.ImportModelWizardStringTable.PARSER_PROCESS_LABEL_TEXT;
import static de.tu_bs.cs.isf.e4cf.core.stringtable.ImportModelWizardStringTable.PARSER_TYPE_LABEL_TEXT;
import static de.tu_bs.cs.isf.e4cf.core.stringtable.ImportModelWizardStringTable.SELECT_FILE_GROUP_TITLE;
import static de.tu_bs.cs.isf.e4cf.core.stringtable.ImportModelWizardStringTable.SELECT_OUTPUT_DIRECTORY_GROUP_TITLE;
import static de.tu_bs.cs.isf.e4cf.core.stringtable.ImportModelWizardStringTable.SELECT_PARSER_GROUP_TITLE;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.parser.base.IParserProcess;
import de.tu_bs.cs.isf.e4cf.parser.base.ParserType;
import de.tu_bs.cs.isf.e4cf.parser.base.helper.IParserFactory;
import de.tu_bs.cs.isf.e4cf.parser.impl.DefaultParserFactory;

public class ImportModelParserPage extends WizardPage {
	
	private static final int SELECTED_FILE_LIST_MAX_HEIGHT = 80;
	private static final int SELECTED_FILE_LIST_MIN_HEIGHT = 60;
	
	protected Group selectOutputDirectoryGroup;
	protected Group selectFileGroup;
	protected Group selectParserGroup;
	
	protected Button selectFileButton;
	protected Button addFileButton;
	protected List selectedFileList;
	
	protected Button selectOutputDirectoryButton;
	protected List outputDirectoryList;
	
	protected Label parserTypeLabel;
	protected Label parserProcessLabel;
	protected Label parserOutputLabel;
	protected Combo parserTypeCombo;
	protected Combo parserProcessCombo;
	protected Label parserOutputFormat;
	protected Text parserDesc;
	
	protected java.util.List<Path> selectedFiles;
	protected Path outputDirectory;
	
	protected IParserFactory parserFactory;
	protected IParserProcess choosenParserApp;
	
	protected Map<ParserType, java.util.List<IParserProcess>> parsersMap;
	protected java.util.List<IParserProcess> parserProcessList;

	protected java.util.List<FileTreeElement> sourceFiles;
	
	protected SelectionListener selectFileBehaviour;
	protected SelectionListener addFileBehaviour;

	protected SelectionListener selectOutputDirectoryBehaviour;
	
	protected ModifyListener modifyParserTypeBehaviour;
	protected ModifyListener modifyParserImplBehaviour;
	
	public ImportModelParserPage(String pageName,String title, java.util.List<FileTreeElement> sourceFiles, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
		setDescription(DESCRIPTION);
		this.parserFactory = new DefaultParserFactory();
		this.selectedFiles = new ArrayList<>(); 
		this.sourceFiles = sourceFiles;
		collectParsers();
	}

	/**
	 * Collect the parsers that are available through other plugins.
	 */
	private void collectParsers() {
		try {
			parsersMap = new HashMap<>();
			parserProcessList = parserFactory.getAllParserApplications();
			parserProcessList.forEach(parser -> addToTypedParserList(parser));
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private void addToTypedParserList(IParserProcess parser) {
		if (!parsersMap.containsKey(parser.getType())) {
			parsersMap.put(parser.getType(), new ArrayList<IParserProcess>());
		}
		parsersMap.get(parser.getType()).add(parser);
	}
	
	@Override
	public void createControl(Composite parent) {
		Composite pageComp = new Composite(parent, SWT.NONE);
		GridLayout pageLayout = new GridLayout(3, true);
		pageComp.setLayout(pageLayout);

		this.setControl(pageComp);
		this.setPageComplete(false);

		Composite upperComp =  decorateComposite(new Composite(pageComp, SWT.NONE), 1, new Point(3, 1));
		Composite middleComp = decorateComposite(new Composite(pageComp, SWT.NONE), 1, new Point(3, 1));
		Composite lowerComp = decorateComposite(new Composite(pageComp, SWT.NONE), 1, new Point(3, 1));
		
		selectFileGroup = decorateGroup(new Group(upperComp, SWT.NONE), 8, new Point(1, 1));
		selectFileGroup.setText(SELECT_FILE_GROUP_TITLE);
		selectOutputDirectoryGroup = decorateGroup(new Group(middleComp, SWT.NONE), 4, new Point(1, 1));
		selectOutputDirectoryGroup.setText(SELECT_OUTPUT_DIRECTORY_GROUP_TITLE);
		selectParserGroup = decorateGroup(new Group(lowerComp, SWT.NONE), 8, new Point(1, 1));
		selectParserGroup.setText(SELECT_PARSER_GROUP_TITLE);
		
		createSelectFileGroupContent();
		new Label(selectOutputDirectoryGroup, SWT.NONE); // padding
		createSelectOutputDirectoryGroupContent();
		new Label(selectOutputDirectoryGroup, SWT.NONE); // padding
		createParserSelectionGroupContent();
		addSelectedFiles();
	}
	
	private Group decorateGroup(Group group, int  numberOfColumns, Point expansion) {
		return createContainer(group, numberOfColumns, expansion);
	}
	
	private Composite decorateComposite(Composite composite, int numberOfColumns, Point expansion) {
		return createContainer(composite, numberOfColumns, expansion);
	}
	
	private <T extends Composite> T createContainer(T container, int numberOfColumns, Point expansion) {
		container.setLayout(new GridLayout(numberOfColumns, true));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, expansion.x, expansion.y));
		return container;
	}
	
	@Override
	public boolean isPageComplete() {
		boolean inputFilesSelected = selectedFiles.stream().allMatch(file -> Files.exists(file));
		boolean parserSelected = choosenParserApp != null;
		boolean outputDirectorySelected = outputDirectory != null && Files.exists(outputDirectory) && Files.isDirectory(outputDirectory);
		
		return inputFilesSelected && parserSelected && outputDirectorySelected;
	}
	
	public void createSelectFileGroupContent() {
		selectFileButton = createButton(selectFileGroup, new Point(1, 1),  FILE_SELECTION_BUTTON_LABEL);
		if (selectFileBehaviour != null) {
			selectFileButton.addSelectionListener(selectFileBehaviour);			
		} else {
			throw createNullBehaviourException("file selection");
		}
		addFileButton = createButton(selectFileGroup, new Point(1,1), FILE_SELECTION_ADD_BUTTON_LABEL);
		if (selectFileBehaviour != null) {
			addFileButton.addSelectionListener(addFileBehaviour);			
		} else {
			throw createNullBehaviourException("file addition");
		}
		
		//padding
		Label l = new Label(selectFileGroup, SWT.NONE);
		l.setLayoutData(new GridData(SWT.FILL,SWT.FILL, true, true,6,1	));
		
		selectedFileList = new List(selectFileGroup, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY | SWT.BORDER);
		GridData selectedFileListGD = new GridData(SWT.FILL, SWT.FILL, true, true, 8, 1);
		selectedFileListGD.heightHint = SELECTED_FILE_LIST_MAX_HEIGHT;
		selectedFileListGD.minimumHeight = SELECTED_FILE_LIST_MIN_HEIGHT;
		selectedFileList.setLayoutData(selectedFileListGD);
	}
	
	private void createSelectOutputDirectoryGroupContent() {
		selectOutputDirectoryButton = createButton(selectOutputDirectoryGroup, new Point(4, 1), OUTPUT_DIRECTORY_BUTTON_LABEL);	
		if (selectOutputDirectoryBehaviour != null) {
			selectOutputDirectoryButton.addSelectionListener(selectOutputDirectoryBehaviour);
		} else {
			throw createNullBehaviourException("output directory selection");
		}
		
		outputDirectoryList = new List(selectOutputDirectoryGroup, SWT.LEFT | SWT.H_SCROLL | SWT.SINGLE | SWT.READ_ONLY | SWT.BORDER);
		outputDirectoryList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));
	}
	
	public Button createButton(Composite parent, Point expansion, String text) {
		Button button = new Button(parent, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, expansion.x, expansion.y));	
		button.setText(text);
		return button;
	}
	
	private void createParserSelectionGroupContent() {
		// parser type label
		parserTypeLabel = createLabel(selectParserGroup, new Point(1, 1), PARSER_TYPE_LABEL_TEXT);
		
		// parser type combo
		parserTypeCombo = createDropDownCombo(selectParserGroup);
		fillParserTypeCombo();
		if (modifyParserTypeBehaviour != null) {
			parserTypeCombo.addModifyListener(modifyParserTypeBehaviour);			
		} else {
			throw createNullBehaviourException("parser type modification");
		}
				
		// parser implementation description
		parserDesc = new Text(selectParserGroup, SWT.LEFT | SWT.MULTI | SWT.READ_ONLY | SWT.WRAP | SWT.BORDER);
		parserDesc.setBackground(new Color(getShell().getDisplay(), 255, 255, 255));
		GridData parserDescGD = new GridData(SWT.FILL, SWT.FILL, true, true);
		parserDescGD.verticalSpan = 3;
		parserDescGD.horizontalSpan = 5;
		parserDesc.setLayoutData(parserDescGD);
		parserDesc.setText("");
		
		// parser process label
		parserProcessLabel = createLabel(selectParserGroup, new Point(1, 1), PARSER_PROCESS_LABEL_TEXT);
		
		// concrete parser process combo
		parserProcessCombo = createDropDownCombo(selectParserGroup);
		if (modifyParserImplBehaviour != null) {
			parserProcessCombo.addModifyListener(modifyParserImplBehaviour);
		} else {
			throw createNullBehaviourException("parser process modification");
		}
		
		// parser output label
		parserOutputLabel = createLabel(selectParserGroup, new Point(1, 1), PARSER_IMPL_LABEL_TEXT);
		
		// concrete parser output format
		parserOutputFormat = createLabel(selectParserGroup, new Point(2,1), "");
				
		setDefaultParserConfiguration();
	}
	
	private Label createLabel(Composite parent, Point expansion, String text) {
		Label label = new Label(parent, SWT.PUSH);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, expansion.x, expansion.y));	
		label.setText(text);
		return label;
	}
	
	private Combo createDropDownCombo(Composite parent) {
		Combo combo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1));		
		combo.clearSelection();
		return combo;
	}

	private void fillParserTypeCombo() {
		parsersMap.keySet().stream().forEach(type -> parserTypeCombo.add(type.getName()));
	}

	private void setDefaultParserConfiguration() {
		for (int i = 0; i <parserTypeCombo.getItemCount(); i++) {
			if (parserTypeCombo.getItem(i).equals(DEFAULT_PARSER_TYPE)) {
				parserTypeCombo.select(i);
			}
		}
	}
	
	public void updateWizard() {
		getWizard().getContainer().updateButtons();
	}
	
	/**
	 * Adds the selected files, if any, to the list of the selected input files.
	 */
	private void addSelectedFiles() {
		selectedFileList.removeAll();
		for (FileTreeElement element : sourceFiles) {
			selectedFiles.add(FileHandlingUtility.getPath(element));
			selectedFileList.add(element.getAbsolutePath());
		}
		
		updateWizard();
	}
	
	public IParserProcess getChoosenParserApplication() {
		return choosenParserApp;
	}
	
	public java.util.List<Path> getInputFiles() {
		return selectedFiles;
	}
	
	public Path getOutputDirectory() {
		return outputDirectory;
	}
	
	public void setOutputDirectory(Path outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public Button getSelectFileButton() {
		return selectFileButton;
	}

	public List getSelectedFileList() {
		return selectedFileList;
	}

	public Button getSelectOutputDirectoryButton() {
		return selectOutputDirectoryButton;
	}

	public List getOutputDirectoryList() {
		return outputDirectoryList;
	}

	public Combo getParserTypeCombo() {
		return parserTypeCombo;
	}

	public Label getParserImplCombo() {
		return parserOutputFormat;
	}

	public Text getParserDesc() {
		return parserDesc;
	}

	public java.util.List<Path> getSelectedFiles() {
		return selectedFiles;
	}

	public IParserProcess getChoosenParserApp() {
		return choosenParserApp;
	}
	
	public void setChoosenParserApp(IParserProcess parserApp) {
		choosenParserApp = parserApp;
	}

	public Map<ParserType, java.util.List<IParserProcess>> getParsersMap() {
		return parsersMap;
	}

	public java.util.List<IParserProcess> getParserApplicationList() {
		return parserProcessList;
	}

	public java.util.List<FileTreeElement> getSourceFiles() {
		return sourceFiles;
	}

	public void attachSelectFileBehaviour(SelectionListener fileSelectionListener) {
		this.selectFileBehaviour = fileSelectionListener;
	}

	public void attachAddFileBehaviour(SelectionListener fileSelectionListener) {
		this.addFileBehaviour = fileSelectionListener;
	}
	
	public void attachSelectOutputDirectoryBehaviour(SelectionListener outputDirectoryListener) {
		this.selectOutputDirectoryBehaviour = outputDirectoryListener;		
	}

	public void attachParserTypeModificationBehaviour(ModifyListener parserTypeListener) {
		this.modifyParserTypeBehaviour = parserTypeListener;
	}

	public void attachParserImplModificationBehaviour(ModifyListener parserImplListener) {
		this.modifyParserImplBehaviour = parserImplListener;
	}
	
	private NullPointerException createNullBehaviourException(String subjectBehaviour) {
		return new NullPointerException("Behaviour for "+subjectBehaviour+" is null");

	}
}
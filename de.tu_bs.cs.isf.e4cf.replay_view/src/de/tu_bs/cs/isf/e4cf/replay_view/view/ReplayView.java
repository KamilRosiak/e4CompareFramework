package de.tu_bs.cs.isf.e4cf.replay_view.view;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.replay_view.ReplayViewController;
import de.tu_bs.cs.isf.e4cf.replay_view.replay.state.ReplayerState;
import de.tu_bs.cs.isf.e4cf.replay_view.replay.state.ReplayerStateMachine.StateChangeListener;
import de.tu_bs.cs.isf.e4cf.replay_view.util.ReplayViewStringTable;
import de.tu_bs.cs.isf.e4cf.replay_view.view.menu.JumpToAction;

public class ReplayView implements StateChangeListener {
	
	public static final String PAUSE_BUTTON_NAME = "pause";
	public static final String RESUME_BUTTON_NAME = "resume";
	
	private ReplayViewController controller; 
	private ServiceContainer serviceContainer;
	private TabFolder mainComposite;
	
	// Replay Files
	private CheckboxTreeViewer modificationSetTreeViewer;
	private Button applyReplayButton;
	private Button pauseResumeReplayButton;
	private Button cancelReplayButton;
	private Button formatCheckbox;
	
	private Button addReplayCommandButton;
	private Button removeReplayCommandButton;
	private Button moveReplayCommandUpButton;
	private Button moveReplayCommandDownButton;
	private Button checkButton;
	private Button uncheckButton;
	private Button checkAllButton;
	private Button uncheckAllButton;
	
	private KeyListener replayCommandKeyBehaviour;
	private SelectionListener applyBehaviour;
	private SelectionListener pauseResumeBehaviour;
	private SelectionListener cancelBehaviour;
	private SelectionListener removeReplayCommandBehaviour;
	private SelectionListener addReplayCommandBehaviour;
	private SelectionListener checkBehaviour;
	private SelectionListener uncheckBehaviour;
	private SelectionListener checkAllBehaviour;
	private SelectionListener uncheckAllBehaviour;
	private IDoubleClickListener treeDoubleClickBehaviour;
	private ICheckStateListener treeItemCheckBehaviour;
		
	public ReplayView(ReplayViewController controller, ServiceContainer serviceContainer) {
		this.controller = controller;
		this.serviceContainer = serviceContainer;
	}
	
	public void createControl(Composite parent) {
		mainComposite = new TabFolder(parent, SWT.TOP);
		mainComposite.setLayout(new GridLayout(10, true));
		mainComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createViewContent(mainComposite);		
		createContextMenu();
	}

	private void createViewContent(Composite parent) {
		// initialize table viewer in item
		modificationSetTreeViewer = new CheckboxTreeViewer(parent, SWT.BORDER | SWT.MULTI |SWT.V_SCROLL | SWT.CHECK | SWT.FULL_SELECTION);
		modificationSetTreeViewer.setContentProvider(new ReplayContentProvider());
		modificationSetTreeViewer.setLabelProvider(new ReplayLabelProvider());
		modificationSetTreeViewer.setComparator(new ReplayViewComparator());
		
		if (treeDoubleClickBehaviour != null) {
			modificationSetTreeViewer.addDoubleClickListener(treeDoubleClickBehaviour);			
		}
		if (treeItemCheckBehaviour != null) {
			modificationSetTreeViewer.addCheckStateListener(treeItemCheckBehaviour);
		}
		
		Tree t = modificationSetTreeViewer.getTree();
		t.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 8, 2));
		t.setHeaderVisible(true);
		t.setLinesVisible(true);
		t.removeAll();
		t.addListener(SWT.MeasureItem, 	e -> e.height = 50);
		t.addListener(SWT.Expand, 		e -> t.getDisplay().asyncExec(() -> packTreeColumns()));
		t.addListener(SWT.Collapse, 	e -> t.getDisplay().asyncExec(() -> packTreeColumns()));
				
		Image applyButtonImage = serviceContainer.imageService.getImage(ReplayViewStringTable.BUNDLE_NAME, "icons/submit_16.png");
		t.setData(ReplayViewStringTable.TABLE_ITEM_APPLY_IMAGE_KEY, applyButtonImage);
		
		if (replayCommandKeyBehaviour != null) {
			t.addKeyListener(replayCommandKeyBehaviour);
		}
			
		TreeColumn applyColumn = new TreeColumn(t, SWT.CENTER);
		applyColumn.setText(ReplayViewStringTable.APPLY_COLUMN_NAME);
		
		new TreeColumn(t, SWT.CENTER).setText(ReplayViewStringTable.NAME_COLUMN_NAME);
		new TreeColumn(t, SWT.CENTER).setText(ReplayViewStringTable.TIMESTAMP_COLUMN_NAME);
		new TreeColumn(t, SWT.CENTER).setText(ReplayViewStringTable.MOD_TYPE_COLUMN_NAME);
		new TreeColumn(t, SWT.CENTER).setText(ReplayViewStringTable.FEATURE_ID_COLUMN_NAME);
		new TreeColumn(t, SWT.CENTER).setText(ReplayViewStringTable.DELTA_PROPERTY_COLUMN_NAME);
		new TreeColumn(t, SWT.CENTER).setText(ReplayViewStringTable.DELTA_PRE_VALUE_COLUMN_NAME);
		new TreeColumn(t, SWT.CENTER).setText(ReplayViewStringTable.DELTA_POST_VALUE_COLUMN_NAME);
		
		packTreeColumns();
		
		// file management label
		Group fileManagementGroup = decorateGroup(new Group(parent, SWT.NONE), 2, new Point(2, 1));
		((GridData)fileManagementGroup.getLayoutData()).verticalAlignment = SWT.TOP;
		((GridData)fileManagementGroup.getLayoutData()).horizontalAlignment = SWT.FILL;
		((GridData)fileManagementGroup.getLayoutData()).grabExcessHorizontalSpace = true;
		fileManagementGroup.setText("Replay");
		fileManagementGroup.setToolTipText("Contains change operations on command files.");

		applyReplayButton = createButton(fileManagementGroup, "apply");
		applyReplayButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		if (applyBehaviour!= null) {
			applyReplayButton.addSelectionListener(applyBehaviour);
		}
		
		pauseResumeReplayButton = createButton(fileManagementGroup, "pause");
		pauseResumeReplayButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		pauseResumeReplayButton.setEnabled(false);
		if (pauseResumeBehaviour!= null) {
			pauseResumeReplayButton.addSelectionListener(pauseResumeBehaviour);
		}
		
		cancelReplayButton = createButton(fileManagementGroup, "cancel");
		cancelReplayButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1));
		cancelReplayButton.setEnabled(false);
		if (cancelBehaviour!= null) {
			cancelReplayButton.addSelectionListener(cancelBehaviour);
		}
		
		formatCheckbox = createCheckbox(fileManagementGroup, "format diagram after each step");
		formatCheckbox.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1));
		
		// separator label
		Label separator = new Label(fileManagementGroup, SWT.NONE);
		separator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false	, 2, 1));
		
		addReplayCommandButton = createButton(fileManagementGroup, "add");
		addReplayCommandButton.setToolTipText("Select additional command files omitting duplicates.");
		addReplayCommandButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		if (addReplayCommandBehaviour!= null) {
			addReplayCommandButton.addSelectionListener(addReplayCommandBehaviour);
		}
		
		removeReplayCommandButton = createButton(fileManagementGroup, "remove");
		removeReplayCommandButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		if (removeReplayCommandBehaviour != null) {
			removeReplayCommandButton.addSelectionListener(removeReplayCommandBehaviour);
		}
		
		checkButton = createButton(fileManagementGroup, "check");
		checkButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		if (checkBehaviour != null) {
			checkButton.addSelectionListener(checkBehaviour);
		}
		
		uncheckButton = createButton(fileManagementGroup, "uncheck");
		uncheckButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		if (uncheckBehaviour != null) {
			uncheckButton.addSelectionListener(uncheckBehaviour);
		}
		
		checkAllButton = createButton(fileManagementGroup, "check all");
		checkAllButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		if (checkAllBehaviour != null) {
			checkAllButton.addSelectionListener(checkAllBehaviour);
		}
		
		uncheckAllButton = createButton(fileManagementGroup, "uncheck all");
		uncheckAllButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		if (uncheckAllBehaviour != null) {
			uncheckAllButton.addSelectionListener(uncheckAllBehaviour);
		}
		
		parent.layout();
		parent.update();
	}
	
	private void createContextMenu() {
		MenuManager contextMenu = new MenuManager();
		
		// add menu actions
		contextMenu.add(new JumpToAction(this));

		// setup menu for viewer
	    Menu menu = contextMenu.createContextMenu(modificationSetTreeViewer.getControl());
	    modificationSetTreeViewer.getControl().setMenu(menu);
	}
	
	public void focusItem(Object treeObject) {
		ReplayLabelProvider labelProvider = (ReplayLabelProvider) modificationSetTreeViewer.getLabelProvider();
		labelProvider.setFocusedObject(treeObject);
		packTreeColumns();
		modificationSetTreeViewer.refresh();
	}
	
	/**
	 * Show a pausing state for a replay in the view.
	 * 
	 * @param pausing if true, changes the view to a pausing state, false resets the state
	 */
	public void setPausingState(boolean pausing) {
		if (pausing) {
			pauseResumeReplayButton.setText(RESUME_BUTTON_NAME);
		} else {
			pauseResumeReplayButton.setText(PAUSE_BUTTON_NAME);
		}
	}
	
	/**
	 * Returns the pausing state of the view.
	 * This has no effect on the replay logic.
	 * 
	 * @return
	 */
	public boolean getPausingState() {
		return pauseResumeReplayButton.getText().equals(PAUSE_BUTTON_NAME);
	}

	public void packTreeColumns() {
		Arrays.asList(modificationSetTreeViewer.getTree().getColumns()).forEach(col -> col.pack());
	}
	
	public static String getRelativePathString(Path selectedFile) {
		return Paths.get(RCPContentProvider.getCurrentWorkspacePath()).relativize(selectedFile).toString();
	}
	
	private Group decorateGroup(Group group, int numberOfColumns, boolean equalColumns) {
		return decorateGroup(group, numberOfColumns, equalColumns, new Point(1,1));
	}
	
	private Group decorateGroup(Group group, int  numberOfColumns, Point expansion) {
		return decorateGroup(group, numberOfColumns, true, expansion);
	}
	
	private Group decorateGroup(Group group, int  numberOfColumns, boolean equalColumns, Point expansion) {
		return createContainer(group, numberOfColumns, equalColumns, expansion);
	}
	
	private Composite decorateComposite(Composite composite, int numberOfColumns, Point expansion) {
		return createContainer(composite, numberOfColumns, expansion);
	}
	
	private <T extends Composite> T createContainer(T container, int numberOfColumns, Point expansion) {
		return createContainer(container, numberOfColumns, true, expansion);
	}
	
	private <T extends Composite> T createContainer(T container, int numberOfColumns, boolean equalColumns, Point expansion) {
		container.setLayout(new GridLayout(numberOfColumns, equalColumns));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, expansion.x, expansion.y));
		return container;
	}
	
	public Button createButton(Composite parent, String text) {
		Button button = new Button(parent, SWT.PUSH | SWT.WRAP);
		button.setText(text);
		return button;
	}
	
	public Button createCheckbox(Composite parent, String text) {
		Button button = new Button(parent, SWT.CHECK | SWT.WRAP);
		button.setText(text);
		return button;
	}
	
	public Button createButton(Composite parent, Point expansion, String text) {
		Button button = new Button(parent, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, expansion.x, expansion.y));	
		button.setText(text);
		return button;
	}
	
	public void attachReplayCommandKeyBehaviour(KeyListener replayCommandKeyBehaviour) {
		this.replayCommandKeyBehaviour = replayCommandKeyBehaviour;
	}

	public void attachApplyBehaviour(SelectionListener applyBehaviour) {
		this.applyBehaviour = applyBehaviour;
	}
	
	public void attachPauseResumeBehaviour(SelectionListener pauseResumeBehaviour) {
		this.pauseResumeBehaviour = pauseResumeBehaviour;
	}
	
	public void attachCancelBehaviour(SelectionListener cancelBehaviour) {
		this.cancelBehaviour = cancelBehaviour;
	}

	public void attachRemoveReplayCommandBehaviour(SelectionListener removeReplayCommandBehaviour) {
		this.removeReplayCommandBehaviour = removeReplayCommandBehaviour;
	}

	public void attachAddReplayCommandBehaviour(SelectionListener addReplayCommandBehaviour) {
		this.addReplayCommandBehaviour = addReplayCommandBehaviour;
	}

	public void attachCheckBehaviour(SelectionListener checkBehaviour) {
		this.checkBehaviour = checkBehaviour;
	}

	public void attachUncheckBehaviour(SelectionListener uncheckBehaviour) {
		this.uncheckBehaviour = uncheckBehaviour;
	}

	public void attachCheckAllBehaviour(SelectionListener checkAllBehaviour) {
		this.checkAllBehaviour = checkAllBehaviour;
	}

	public void attachUncheckAllBehaviour(SelectionListener uncheckAllBehaviour) {
		this.uncheckAllBehaviour = uncheckAllBehaviour;
	}
	
	public void attachTreeDoubleClickBehaviour(IDoubleClickListener tableDoubleClickBehaviour) {
		this.treeDoubleClickBehaviour = tableDoubleClickBehaviour;
	}
	
	public void attachTreeItemCheckBehaviour(ICheckStateListener treeItemCheckBehaviour) {
		this.treeItemCheckBehaviour = treeItemCheckBehaviour;
	}
	
	public ReplayViewController getController() {
		return this.controller;
	}

	public ServiceContainer getServiceContainer() {
		return serviceContainer;
	}

	public Tree getModificationSetTree() {
		return modificationSetTreeViewer.getTree();
	}
	
	public CheckboxTreeViewer getModificationSetTreeViewer() {
		return modificationSetTreeViewer;
	}

	public Button getApplyReplayButton() {
		return applyReplayButton;
	}
	
	public Button getPauseResumeCommandButton() {
		return pauseResumeReplayButton;
	}
	
	public Button getCancelReplayButton() {
		return cancelReplayButton;
	}
	
	public Button getFormatCheckbox() {
		return formatCheckbox;
	}

	public Button getAddReplayCommandButton() {
		return addReplayCommandButton;
	}

	public Button getRemoveReplayCommandButton() {
		return removeReplayCommandButton;
	}

	public Button getMoveReplayCommandUpButton() {
		return moveReplayCommandUpButton;
	}

	public Button getMoveReplayCommandDownButton() {
		return moveReplayCommandDownButton;
	}

	public Button getCheckButton() {
		return checkButton;
	}

	public Button getUncheckButton() {
		return uncheckButton;
	}

	public Button getCheckAllButton() {
		return checkAllButton;
	}

	public Button getUncheckAllButton() {
		return uncheckAllButton;
	}

	@Override
	public void stateChanged(ReplayerState state) {
		Display.getDefault().syncExec(() ->  {
			// check for disposed view
			if (this.mainComposite.isDisposed()) {
				return;
			}
			
			if (state == ReplayerState.INACTIVE) {
				applyReplayButton.setEnabled(true);
				cancelReplayButton.setEnabled(false);
				pauseResumeReplayButton.setEnabled(false);
				setPausingState(false);
				focusItem(null);
			} else if (state == ReplayerState.PLAYING) {
				// disable apply button, enable cancel button
				applyReplayButton.setEnabled(false);
				cancelReplayButton.setEnabled(true);
				pauseResumeReplayButton.setEnabled(true);
				setPausingState(false);
			} else if (state == ReplayerState.PAUSED) {
				// disable apply button, enable cancel button
				applyReplayButton.setEnabled(false);
				cancelReplayButton.setEnabled(true);
				pauseResumeReplayButton.setEnabled(true);
				setPausingState(true);
			} else if (state == ReplayerState.UNDEFINED) {
				
			} else if (state == ReplayerState.ERROR) {
				
			}
		});
	}
}

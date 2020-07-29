package de.tu_bs.cs.isf.e4cf.core.parts.perspective_switch;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import de.tu_bs.cs.isf.e4cf.core.parts.perspective_switch.commands.SwitchPerspectiveCommand;
import de.tu_bs.cs.isf.e4cf.core.parts.perspective_switch.util.RCPPerspectiveConstans;

/**
 * This is a perspective switch that allows to switch the perspective.
 * To register a new perspective you have to do the following steps:
 * 1. At first you have to add a new perspective to the perspective stack in the Application.e4xmi.
 * 2. Give this perspective a unique id and write the id into the "util.RCPPerspectiveConstans.java" to register it within the switch.  
 * @author {Kamil Rosiak}
 *
 */
public class PerspectivSwitchComboControl {
	@Inject MApplication  _mApplication;
	@Inject EPartService _partService;
	@Inject EModelService _modelService;

	@PostConstruct
	public void createGui(final Composite parent) {
		Group group = new Group(parent,SWT.NULL);
		group.setLayout(new GridLayout(2,false));
		group.setText("PerspectiveSwitch");

		ComboViewer combo = new ComboViewer(group, SWT.READ_ONLY);
		combo.getControl().setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false));
		for(String pers : RCPPerspectiveConstans.perspectives) {
			combo.add(pers.substring(pers.lastIndexOf('.')+1));
		}
		combo.getCombo().select(0);

		combo.addSelectionChangedListener(e->{
			int selectionIndex = combo.getCombo().getSelectionIndex();
			new SwitchPerspectiveCommand(_mApplication, _partService , _modelService, combo.getCombo().getItem(selectionIndex));
		});
	  }
	
	
}

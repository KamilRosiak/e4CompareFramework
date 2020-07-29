package de.tu_bs.cs.isf.e4cf.core.parts.perspective_switch.commands;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import de.tu_bs.cs.isf.e4cf.core.parts.perspective_switch.util.RCPPerspectiveConstans;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;

/**
 * This command switching the current perspective with a chosen one. When the perspective doesn't exist its switch's to the default one.
 * @author {Kamil Rosiak}
 *
 */
public class SwitchPerspectiveCommand {
	private String _perspectiveID;

	public SwitchPerspectiveCommand(MApplication app, EPartService partService, EModelService modelService, String perspectiveID) {
		//verifying if the chosen perspective exists
		for(String pers : RCPPerspectiveConstans.perspectives) {
			if(pers.substring(pers.lastIndexOf('.')+1).equals(perspectiveID)) {
				_perspectiveID = pers;
			} else {
				_perspectiveID = E4CStringTable.PERSPECTIVE_DEFAULT;
			}
		}
		execute(app, partService, modelService);
	}
	

	@Execute
	public void execute(MApplication app, EPartService partService, EModelService modelService) {
		MPerspective element = (MPerspective) modelService.find(_perspectiveID, app);
		// Now switch perspectives
		if(element!=null){
			partService.switchPerspective(element);
		}
	}
}
	
	
	
	
	

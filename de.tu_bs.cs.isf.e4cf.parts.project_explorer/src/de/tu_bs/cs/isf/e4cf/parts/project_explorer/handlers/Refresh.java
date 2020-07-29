 
package de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;

import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CEventTable;

public class Refresh {
	@Execute
	public void execute(IEventBroker eventBroker) {
		eventBroker.send(E4CEventTable.EVENT_REFRESH_PROJECT_VIEWER, null);
	}
		
}
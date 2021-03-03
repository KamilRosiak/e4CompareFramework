package de.tu_bs.cs.isf.e4cf.core.application_model;

import org.eclipse.e4.ui.internal.workbench.swt.IEventLoopAdvisor;
import org.eclipse.swt.widgets.Display;

public class EventLoopAdvisior implements IEventLoopAdvisor {

	@Override
	public void eventLoopIdle(Display display) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventLoopException(Throwable exception) {
		exception.printStackTrace();
		
	}

}

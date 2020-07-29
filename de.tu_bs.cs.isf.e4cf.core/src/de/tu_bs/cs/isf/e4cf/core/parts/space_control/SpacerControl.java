package de.tu_bs.cs.isf.e4cf.core.parts.space_control;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * This class just presents a space filler for the trimBar to get the perspective switch in the right corner.
 * @author {Kamil Rosiak}
 *
 */
public class SpacerControl {
	@PostConstruct
	  public void postConstruct(Composite parent) {
	    Composite body = new Composite(parent, SWT.NONE);
	    body.setLayout(new FillLayout());
	  }
}

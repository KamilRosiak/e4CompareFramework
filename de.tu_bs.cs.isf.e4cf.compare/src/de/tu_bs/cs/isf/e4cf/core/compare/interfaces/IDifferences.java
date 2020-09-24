package de.tu_bs.cs.isf.e4cf.core.compare.interfaces;

import org.eclipse.emf.ecore.EObject;

public interface IDifferences {
	
	public void leftToRight();
	public void rightToLeft();
	public EObject diff();
}

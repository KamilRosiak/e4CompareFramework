package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.elements;

public class FXFamilyModelElement  {
	
	private static final String NO_LABEL = "<no label>";
	
	private Object element;
	private String label;
	
	public FXFamilyModelElement(Object element, String label) {
		this.element = element;
		this.label = label;
	}
	
	public FXFamilyModelElement(Object element) {
		this(element, NO_LABEL);
	}

	public Object get() {
		return element;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return label;
	}
}

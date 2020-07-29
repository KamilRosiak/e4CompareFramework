package de.tu_bs.cs.isf.e4cf.featuremodel.constraint_view.view.elements;

import CrossTreeConstraints.AbstractConstraint;
import de.tu_bs.cs.isf.e4cf.featuremodel.model.util.ConstraintUtil;

public class FXContraint {
	private AbstractConstraint constraint;
	private String constraintText;
	private String constraintDescription;
	
	public FXContraint(AbstractConstraint constraint) {
		setConstraint(constraint);
		setConstraintText(ConstraintUtil.createStyledConstraintText(constraint));
		setConstraintDescription(constraint.getDescription());
	}

	public String getConstraintText() {
		return constraintText;
	}

	public void setConstraintText(String constraintText) {
		this.constraintText = constraintText;
	}

	public String getConstraintDescription() {
		return constraintDescription;
	}

	public void setConstraintDescription(String constraintDescription) {
		this.constraintDescription = constraintDescription;
	}

	public AbstractConstraint getConstraint() {
		return constraint;
	}

	public void setConstraint(AbstractConstraint constraint) {
		this.constraint = constraint;
	}
	
}

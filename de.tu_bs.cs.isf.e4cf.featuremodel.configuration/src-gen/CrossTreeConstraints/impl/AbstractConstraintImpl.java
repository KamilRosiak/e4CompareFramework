/**
 */
package CrossTreeConstraints.impl;

import CrossTreeConstraints.AbstractConstraint;
import CrossTreeConstraints.CrossTreeConstraintsPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Constraint</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link CrossTreeConstraints.impl.AbstractConstraintImpl#getIsNegated <em>Is Negated</em>}</li>
 *   <li>{@link CrossTreeConstraints.impl.AbstractConstraintImpl#getDescription <em>Description</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AbstractConstraintImpl extends MinimalEObjectImpl.Container implements AbstractConstraint {
	/**
	 * The default value of the '{@link #getIsNegated() <em>Is Negated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsNegated()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean IS_NEGATED_EDEFAULT = Boolean.FALSE;

	/**
	 * The cached value of the '{@link #getIsNegated() <em>Is Negated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsNegated()
	 * @generated
	 * @ordered
	 */
	protected Boolean isNegated = IS_NEGATED_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = "\"\"";

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractConstraintImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CrossTreeConstraintsPackage.Literals.ABSTRACT_CONSTRAINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Boolean getIsNegated() {
		return isNegated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIsNegated(Boolean newIsNegated) {
		Boolean oldIsNegated = isNegated;
		isNegated = newIsNegated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CrossTreeConstraintsPackage.ABSTRACT_CONSTRAINT__IS_NEGATED, oldIsNegated, isNegated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CrossTreeConstraintsPackage.ABSTRACT_CONSTRAINT__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CrossTreeConstraintsPackage.ABSTRACT_CONSTRAINT__IS_NEGATED:
				return getIsNegated();
			case CrossTreeConstraintsPackage.ABSTRACT_CONSTRAINT__DESCRIPTION:
				return getDescription();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CrossTreeConstraintsPackage.ABSTRACT_CONSTRAINT__IS_NEGATED:
				setIsNegated((Boolean)newValue);
				return;
			case CrossTreeConstraintsPackage.ABSTRACT_CONSTRAINT__DESCRIPTION:
				setDescription((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CrossTreeConstraintsPackage.ABSTRACT_CONSTRAINT__IS_NEGATED:
				setIsNegated(IS_NEGATED_EDEFAULT);
				return;
			case CrossTreeConstraintsPackage.ABSTRACT_CONSTRAINT__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CrossTreeConstraintsPackage.ABSTRACT_CONSTRAINT__IS_NEGATED:
				return IS_NEGATED_EDEFAULT == null ? isNegated != null : !IS_NEGATED_EDEFAULT.equals(isNegated);
			case CrossTreeConstraintsPackage.ABSTRACT_CONSTRAINT__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (isNegated: ");
		result.append(isNegated);
		result.append(", description: ");
		result.append(description);
		result.append(')');
		return result.toString();
	}

} //AbstractConstraintImpl

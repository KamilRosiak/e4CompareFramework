/**
 */
package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.impl;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.CarPackage;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Wheel;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Wheel</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.impl.WheelImpl#getTraction <em>Traction</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.impl.WheelImpl#getModel <em>Model</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WheelImpl extends MinimalEObjectImpl.Container implements Wheel {
	/**
	 * The default value of the '{@link #getTraction() <em>Traction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTraction()
	 * @generated
	 * @ordered
	 */
	protected static final int TRACTION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTraction() <em>Traction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTraction()
	 * @generated
	 * @ordered
	 */
	protected int traction = TRACTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getModel() <em>Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModel()
	 * @generated
	 * @ordered
	 */
	protected static final String MODEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getModel() <em>Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModel()
	 * @generated
	 * @ordered
	 */
	protected String model = MODEL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WheelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CarPackage.Literals.WHEEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getTraction() {
		return traction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTraction(int newTraction) {
		int oldTraction = traction;
		traction = newTraction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CarPackage.WHEEL__TRACTION, oldTraction, traction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getModel() {
		return model;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setModel(String newModel) {
		String oldModel = model;
		model = newModel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CarPackage.WHEEL__MODEL, oldModel, model));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CarPackage.WHEEL__TRACTION:
				return getTraction();
			case CarPackage.WHEEL__MODEL:
				return getModel();
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
			case CarPackage.WHEEL__TRACTION:
				setTraction((Integer)newValue);
				return;
			case CarPackage.WHEEL__MODEL:
				setModel((String)newValue);
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
			case CarPackage.WHEEL__TRACTION:
				setTraction(TRACTION_EDEFAULT);
				return;
			case CarPackage.WHEEL__MODEL:
				setModel(MODEL_EDEFAULT);
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
			case CarPackage.WHEEL__TRACTION:
				return traction != TRACTION_EDEFAULT;
			case CarPackage.WHEEL__MODEL:
				return MODEL_EDEFAULT == null ? model != null : !MODEL_EDEFAULT.equals(model);
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
		result.append(" (traction: ");
		result.append(traction);
		result.append(", model: ");
		result.append(model);
		result.append(')');
		return result.toString();
	}

} //WheelImpl

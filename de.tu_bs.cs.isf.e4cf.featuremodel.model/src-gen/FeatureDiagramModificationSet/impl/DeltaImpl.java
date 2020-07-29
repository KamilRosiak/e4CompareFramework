/**
 */
package FeatureDiagramModificationSet.impl;

import FeatureDiagramModificationSet.Delta;
import FeatureDiagramModificationSet.DeltaProperties;
import FeatureDiagramModificationSet.FeatureDiagramModificationSetPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Delta</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link FeatureDiagramModificationSet.impl.DeltaImpl#getProperty <em>Property</em>}</li>
 *   <li>{@link FeatureDiagramModificationSet.impl.DeltaImpl#getValuePriorChange <em>Value Prior Change</em>}</li>
 *   <li>{@link FeatureDiagramModificationSet.impl.DeltaImpl#getValueAfterChange <em>Value After Change</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DeltaImpl extends MinimalEObjectImpl.Container implements Delta {
	/**
	 * The default value of the '{@link #getProperty() <em>Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperty()
	 * @generated
	 * @ordered
	 */
	protected static final DeltaProperties PROPERTY_EDEFAULT = DeltaProperties.FEATURE_NAME;

	/**
	 * The cached value of the '{@link #getProperty() <em>Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperty()
	 * @generated
	 * @ordered
	 */
	protected DeltaProperties property = PROPERTY_EDEFAULT;

	/**
	 * The default value of the '{@link #getValuePriorChange() <em>Value Prior Change</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValuePriorChange()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_PRIOR_CHANGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValuePriorChange() <em>Value Prior Change</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValuePriorChange()
	 * @generated
	 * @ordered
	 */
	protected String valuePriorChange = VALUE_PRIOR_CHANGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getValueAfterChange() <em>Value After Change</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueAfterChange()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_AFTER_CHANGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValueAfterChange() <em>Value After Change</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueAfterChange()
	 * @generated
	 * @ordered
	 */
	protected String valueAfterChange = VALUE_AFTER_CHANGE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DeltaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FeatureDiagramModificationSetPackage.Literals.DELTA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DeltaProperties getProperty() {
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProperty(DeltaProperties newProperty) {
		DeltaProperties oldProperty = property;
		property = newProperty == null ? PROPERTY_EDEFAULT : newProperty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramModificationSetPackage.DELTA__PROPERTY, oldProperty, property));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getValuePriorChange() {
		return valuePriorChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValuePriorChange(String newValuePriorChange) {
		String oldValuePriorChange = valuePriorChange;
		valuePriorChange = newValuePriorChange;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramModificationSetPackage.DELTA__VALUE_PRIOR_CHANGE, oldValuePriorChange, valuePriorChange));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getValueAfterChange() {
		return valueAfterChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValueAfterChange(String newValueAfterChange) {
		String oldValueAfterChange = valueAfterChange;
		valueAfterChange = newValueAfterChange;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramModificationSetPackage.DELTA__VALUE_AFTER_CHANGE, oldValueAfterChange, valueAfterChange));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FeatureDiagramModificationSetPackage.DELTA__PROPERTY:
				return getProperty();
			case FeatureDiagramModificationSetPackage.DELTA__VALUE_PRIOR_CHANGE:
				return getValuePriorChange();
			case FeatureDiagramModificationSetPackage.DELTA__VALUE_AFTER_CHANGE:
				return getValueAfterChange();
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
			case FeatureDiagramModificationSetPackage.DELTA__PROPERTY:
				setProperty((DeltaProperties)newValue);
				return;
			case FeatureDiagramModificationSetPackage.DELTA__VALUE_PRIOR_CHANGE:
				setValuePriorChange((String)newValue);
				return;
			case FeatureDiagramModificationSetPackage.DELTA__VALUE_AFTER_CHANGE:
				setValueAfterChange((String)newValue);
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
			case FeatureDiagramModificationSetPackage.DELTA__PROPERTY:
				setProperty(PROPERTY_EDEFAULT);
				return;
			case FeatureDiagramModificationSetPackage.DELTA__VALUE_PRIOR_CHANGE:
				setValuePriorChange(VALUE_PRIOR_CHANGE_EDEFAULT);
				return;
			case FeatureDiagramModificationSetPackage.DELTA__VALUE_AFTER_CHANGE:
				setValueAfterChange(VALUE_AFTER_CHANGE_EDEFAULT);
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
			case FeatureDiagramModificationSetPackage.DELTA__PROPERTY:
				return property != PROPERTY_EDEFAULT;
			case FeatureDiagramModificationSetPackage.DELTA__VALUE_PRIOR_CHANGE:
				return VALUE_PRIOR_CHANGE_EDEFAULT == null ? valuePriorChange != null : !VALUE_PRIOR_CHANGE_EDEFAULT.equals(valuePriorChange);
			case FeatureDiagramModificationSetPackage.DELTA__VALUE_AFTER_CHANGE:
				return VALUE_AFTER_CHANGE_EDEFAULT == null ? valueAfterChange != null : !VALUE_AFTER_CHANGE_EDEFAULT.equals(valueAfterChange);
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
		result.append(" (property: ");
		result.append(property);
		result.append(", valuePriorChange: ");
		result.append(valuePriorChange);
		result.append(", valueAfterChange: ");
		result.append(valueAfterChange);
		result.append(')');
		return result.toString();
	}

} //DeltaImpl

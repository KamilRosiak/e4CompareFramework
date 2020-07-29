/**
 */
package FeatureDiagramModificationSet.impl;

import FeatureDiagramModificationSet.FeatureDiagramModificationSetPackage;
import FeatureDiagramModificationSet.FeatureModelModificationSet;
import FeatureDiagramModificationSet.Modification;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature Model Modification Set</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link FeatureDiagramModificationSet.impl.FeatureModelModificationSetImpl#getModifications <em>Modifications</em>}</li>
 *   <li>{@link FeatureDiagramModificationSet.impl.FeatureModelModificationSetImpl#getAffectedFeatureModelName <em>Affected Feature Model Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FeatureModelModificationSetImpl extends MinimalEObjectImpl.Container implements FeatureModelModificationSet {
	/**
	 * The cached value of the '{@link #getModifications() <em>Modifications</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModifications()
	 * @generated
	 * @ordered
	 */
	protected EList<Modification> modifications;

	/**
	 * The default value of the '{@link #getAffectedFeatureModelName() <em>Affected Feature Model Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAffectedFeatureModelName()
	 * @generated
	 * @ordered
	 */
	protected static final String AFFECTED_FEATURE_MODEL_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAffectedFeatureModelName() <em>Affected Feature Model Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAffectedFeatureModelName()
	 * @generated
	 * @ordered
	 */
	protected String affectedFeatureModelName = AFFECTED_FEATURE_MODEL_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FeatureModelModificationSetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FeatureDiagramModificationSetPackage.Literals.FEATURE_MODEL_MODIFICATION_SET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Modification> getModifications() {
		if (modifications == null) {
			modifications = new EObjectContainmentEList<Modification>(Modification.class, this, FeatureDiagramModificationSetPackage.FEATURE_MODEL_MODIFICATION_SET__MODIFICATIONS);
		}
		return modifications;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getAffectedFeatureModelName() {
		return affectedFeatureModelName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAffectedFeatureModelName(String newAffectedFeatureModelName) {
		String oldAffectedFeatureModelName = affectedFeatureModelName;
		affectedFeatureModelName = newAffectedFeatureModelName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramModificationSetPackage.FEATURE_MODEL_MODIFICATION_SET__AFFECTED_FEATURE_MODEL_NAME, oldAffectedFeatureModelName, affectedFeatureModelName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FeatureDiagramModificationSetPackage.FEATURE_MODEL_MODIFICATION_SET__MODIFICATIONS:
				return ((InternalEList<?>)getModifications()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FeatureDiagramModificationSetPackage.FEATURE_MODEL_MODIFICATION_SET__MODIFICATIONS:
				return getModifications();
			case FeatureDiagramModificationSetPackage.FEATURE_MODEL_MODIFICATION_SET__AFFECTED_FEATURE_MODEL_NAME:
				return getAffectedFeatureModelName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case FeatureDiagramModificationSetPackage.FEATURE_MODEL_MODIFICATION_SET__MODIFICATIONS:
				getModifications().clear();
				getModifications().addAll((Collection<? extends Modification>)newValue);
				return;
			case FeatureDiagramModificationSetPackage.FEATURE_MODEL_MODIFICATION_SET__AFFECTED_FEATURE_MODEL_NAME:
				setAffectedFeatureModelName((String)newValue);
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
			case FeatureDiagramModificationSetPackage.FEATURE_MODEL_MODIFICATION_SET__MODIFICATIONS:
				getModifications().clear();
				return;
			case FeatureDiagramModificationSetPackage.FEATURE_MODEL_MODIFICATION_SET__AFFECTED_FEATURE_MODEL_NAME:
				setAffectedFeatureModelName(AFFECTED_FEATURE_MODEL_NAME_EDEFAULT);
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
			case FeatureDiagramModificationSetPackage.FEATURE_MODEL_MODIFICATION_SET__MODIFICATIONS:
				return modifications != null && !modifications.isEmpty();
			case FeatureDiagramModificationSetPackage.FEATURE_MODEL_MODIFICATION_SET__AFFECTED_FEATURE_MODEL_NAME:
				return AFFECTED_FEATURE_MODEL_NAME_EDEFAULT == null ? affectedFeatureModelName != null : !AFFECTED_FEATURE_MODEL_NAME_EDEFAULT.equals(affectedFeatureModelName);
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
		result.append(" (affectedFeatureModelName: ");
		result.append(affectedFeatureModelName);
		result.append(')');
		return result.toString();
	}

} //FeatureModelModificationSetImpl

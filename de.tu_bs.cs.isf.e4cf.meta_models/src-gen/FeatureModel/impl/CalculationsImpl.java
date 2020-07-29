/**
 */
package FeatureModel.impl;

import FeatureModel.Calculations;
import FeatureModel.FeatureModelPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Calculations</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link FeatureModel.impl.CalculationsImpl#isAuto <em>Auto</em>}</li>
 *   <li>{@link FeatureModel.impl.CalculationsImpl#isConstraints <em>Constraints</em>}</li>
 *   <li>{@link FeatureModel.impl.CalculationsImpl#isFeatures <em>Features</em>}</li>
 *   <li>{@link FeatureModel.impl.CalculationsImpl#isRedundant <em>Redundant</em>}</li>
 *   <li>{@link FeatureModel.impl.CalculationsImpl#isTautology <em>Tautology</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CalculationsImpl extends MinimalEObjectImpl.Container implements Calculations {
	/**
	 * The default value of the '{@link #isAuto() <em>Auto</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAuto()
	 * @generated
	 * @ordered
	 */
	protected static final boolean AUTO_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAuto() <em>Auto</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAuto()
	 * @generated
	 * @ordered
	 */
	protected boolean auto = AUTO_EDEFAULT;

	/**
	 * The default value of the '{@link #isConstraints() <em>Constraints</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConstraints()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CONSTRAINTS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isConstraints() <em>Constraints</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConstraints()
	 * @generated
	 * @ordered
	 */
	protected boolean constraints = CONSTRAINTS_EDEFAULT;

	/**
	 * The default value of the '{@link #isFeatures() <em>Features</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFeatures()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FEATURES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFeatures() <em>Features</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFeatures()
	 * @generated
	 * @ordered
	 */
	protected boolean features = FEATURES_EDEFAULT;

	/**
	 * The default value of the '{@link #isRedundant() <em>Redundant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRedundant()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REDUNDANT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRedundant() <em>Redundant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRedundant()
	 * @generated
	 * @ordered
	 */
	protected boolean redundant = REDUNDANT_EDEFAULT;

	/**
	 * The default value of the '{@link #isTautology() <em>Tautology</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTautology()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TAUTOLOGY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isTautology() <em>Tautology</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTautology()
	 * @generated
	 * @ordered
	 */
	protected boolean tautology = TAUTOLOGY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CalculationsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FeatureModelPackage.Literals.CALCULATIONS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAuto() {
		return auto;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAuto(boolean newAuto) {
		boolean oldAuto = auto;
		auto = newAuto;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureModelPackage.CALCULATIONS__AUTO, oldAuto, auto));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isConstraints() {
		return constraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setConstraints(boolean newConstraints) {
		boolean oldConstraints = constraints;
		constraints = newConstraints;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureModelPackage.CALCULATIONS__CONSTRAINTS, oldConstraints, constraints));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFeatures() {
		return features;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFeatures(boolean newFeatures) {
		boolean oldFeatures = features;
		features = newFeatures;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureModelPackage.CALCULATIONS__FEATURES, oldFeatures, features));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isRedundant() {
		return redundant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRedundant(boolean newRedundant) {
		boolean oldRedundant = redundant;
		redundant = newRedundant;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureModelPackage.CALCULATIONS__REDUNDANT, oldRedundant, redundant));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isTautology() {
		return tautology;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTautology(boolean newTautology) {
		boolean oldTautology = tautology;
		tautology = newTautology;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureModelPackage.CALCULATIONS__TAUTOLOGY, oldTautology, tautology));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FeatureModelPackage.CALCULATIONS__AUTO:
				return isAuto();
			case FeatureModelPackage.CALCULATIONS__CONSTRAINTS:
				return isConstraints();
			case FeatureModelPackage.CALCULATIONS__FEATURES:
				return isFeatures();
			case FeatureModelPackage.CALCULATIONS__REDUNDANT:
				return isRedundant();
			case FeatureModelPackage.CALCULATIONS__TAUTOLOGY:
				return isTautology();
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
			case FeatureModelPackage.CALCULATIONS__AUTO:
				setAuto((Boolean)newValue);
				return;
			case FeatureModelPackage.CALCULATIONS__CONSTRAINTS:
				setConstraints((Boolean)newValue);
				return;
			case FeatureModelPackage.CALCULATIONS__FEATURES:
				setFeatures((Boolean)newValue);
				return;
			case FeatureModelPackage.CALCULATIONS__REDUNDANT:
				setRedundant((Boolean)newValue);
				return;
			case FeatureModelPackage.CALCULATIONS__TAUTOLOGY:
				setTautology((Boolean)newValue);
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
			case FeatureModelPackage.CALCULATIONS__AUTO:
				setAuto(AUTO_EDEFAULT);
				return;
			case FeatureModelPackage.CALCULATIONS__CONSTRAINTS:
				setConstraints(CONSTRAINTS_EDEFAULT);
				return;
			case FeatureModelPackage.CALCULATIONS__FEATURES:
				setFeatures(FEATURES_EDEFAULT);
				return;
			case FeatureModelPackage.CALCULATIONS__REDUNDANT:
				setRedundant(REDUNDANT_EDEFAULT);
				return;
			case FeatureModelPackage.CALCULATIONS__TAUTOLOGY:
				setTautology(TAUTOLOGY_EDEFAULT);
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
			case FeatureModelPackage.CALCULATIONS__AUTO:
				return auto != AUTO_EDEFAULT;
			case FeatureModelPackage.CALCULATIONS__CONSTRAINTS:
				return constraints != CONSTRAINTS_EDEFAULT;
			case FeatureModelPackage.CALCULATIONS__FEATURES:
				return features != FEATURES_EDEFAULT;
			case FeatureModelPackage.CALCULATIONS__REDUNDANT:
				return redundant != REDUNDANT_EDEFAULT;
			case FeatureModelPackage.CALCULATIONS__TAUTOLOGY:
				return tautology != TAUTOLOGY_EDEFAULT;
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
		result.append(" (Auto: ");
		result.append(auto);
		result.append(", Constraints: ");
		result.append(constraints);
		result.append(", Features: ");
		result.append(features);
		result.append(", Redundant: ");
		result.append(redundant);
		result.append(", Tautology: ");
		result.append(tautology);
		result.append(')');
		return result.toString();
	}

} //CalculationsImpl

/**
 */
package familyModel.impl;

import familyModel.FamilyModel;
import familyModel.FamilyModelPackage;
import familyModel.VariabilityGroup;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Family Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link familyModel.impl.FamilyModelImpl#getModels <em>Models</em>}</li>
 *   <li>{@link familyModel.impl.FamilyModelImpl#getVariabilyGroups <em>Variabily Groups</em>}</li>
 *   <li>{@link familyModel.impl.FamilyModelImpl#getFaMoName <em>Fa Mo Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FamilyModelImpl extends MinimalEObjectImpl.Container implements FamilyModel {
	/**
	 * The cached value of the '{@link #getModels() <em>Models</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModels()
	 * @generated
	 * @ordered
	 */
	protected EList<String> models;

	/**
	 * The cached value of the '{@link #getVariabilyGroups() <em>Variabily Groups</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariabilyGroups()
	 * @generated
	 * @ordered
	 */
	protected EList<VariabilityGroup> variabilyGroups;

	/**
	 * The default value of the '{@link #getFaMoName() <em>Fa Mo Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFaMoName()
	 * @generated
	 * @ordered
	 */
	protected static final String FA_MO_NAME_EDEFAULT = "familModel";

	/**
	 * The cached value of the '{@link #getFaMoName() <em>Fa Mo Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFaMoName()
	 * @generated
	 * @ordered
	 */
	protected String faMoName = FA_MO_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FamilyModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FamilyModelPackage.Literals.FAMILY_MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getModels() {
		if (models == null) {
			models = new EDataTypeUniqueEList<String>(String.class, this, FamilyModelPackage.FAMILY_MODEL__MODELS);
		}
		return models;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VariabilityGroup> getVariabilyGroups() {
		if (variabilyGroups == null) {
			variabilyGroups = new EObjectContainmentEList<VariabilityGroup>(VariabilityGroup.class, this, FamilyModelPackage.FAMILY_MODEL__VARIABILY_GROUPS);
		}
		return variabilyGroups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getFaMoName() {
		return faMoName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFaMoName(String newFaMoName) {
		String oldFaMoName = faMoName;
		faMoName = newFaMoName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FamilyModelPackage.FAMILY_MODEL__FA_MO_NAME, oldFaMoName, faMoName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FamilyModelPackage.FAMILY_MODEL__VARIABILY_GROUPS:
				return ((InternalEList<?>)getVariabilyGroups()).basicRemove(otherEnd, msgs);
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
			case FamilyModelPackage.FAMILY_MODEL__MODELS:
				return getModels();
			case FamilyModelPackage.FAMILY_MODEL__VARIABILY_GROUPS:
				return getVariabilyGroups();
			case FamilyModelPackage.FAMILY_MODEL__FA_MO_NAME:
				return getFaMoName();
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
			case FamilyModelPackage.FAMILY_MODEL__MODELS:
				getModels().clear();
				getModels().addAll((Collection<? extends String>)newValue);
				return;
			case FamilyModelPackage.FAMILY_MODEL__VARIABILY_GROUPS:
				getVariabilyGroups().clear();
				getVariabilyGroups().addAll((Collection<? extends VariabilityGroup>)newValue);
				return;
			case FamilyModelPackage.FAMILY_MODEL__FA_MO_NAME:
				setFaMoName((String)newValue);
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
			case FamilyModelPackage.FAMILY_MODEL__MODELS:
				getModels().clear();
				return;
			case FamilyModelPackage.FAMILY_MODEL__VARIABILY_GROUPS:
				getVariabilyGroups().clear();
				return;
			case FamilyModelPackage.FAMILY_MODEL__FA_MO_NAME:
				setFaMoName(FA_MO_NAME_EDEFAULT);
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
			case FamilyModelPackage.FAMILY_MODEL__MODELS:
				return models != null && !models.isEmpty();
			case FamilyModelPackage.FAMILY_MODEL__VARIABILY_GROUPS:
				return variabilyGroups != null && !variabilyGroups.isEmpty();
			case FamilyModelPackage.FAMILY_MODEL__FA_MO_NAME:
				return FA_MO_NAME_EDEFAULT == null ? faMoName != null : !FA_MO_NAME_EDEFAULT.equals(faMoName);
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
		result.append(" (models: ");
		result.append(models);
		result.append(", faMoName: ");
		result.append(faMoName);
		result.append(')');
		return result.toString();
	}

} //FamilyModelImpl

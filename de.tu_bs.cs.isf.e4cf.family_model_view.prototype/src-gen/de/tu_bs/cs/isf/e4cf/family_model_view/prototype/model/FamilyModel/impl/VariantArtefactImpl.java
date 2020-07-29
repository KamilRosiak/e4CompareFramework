/**
 */
package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelPackage;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariantArtefact;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Variant Artefact</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.VariantArtefactImpl#getArtefacts <em>Artefacts</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.VariantArtefactImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.VariantArtefactImpl#getOrigins <em>Origins</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VariantArtefactImpl extends MinimalEObjectImpl.Container implements VariantArtefact {
	/**
	 * The cached value of the '{@link #getArtefacts() <em>Artefacts</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArtefacts()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> artefacts;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOrigins() <em>Origins</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrigins()
	 * @generated
	 * @ordered
	 */
	protected EList<Variant> origins;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VariantArtefactImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FamilyModelPackage.Literals.VARIANT_ARTEFACT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EObject> getArtefacts() {
		if (artefacts == null) {
			artefacts = new EObjectResolvingEList<EObject>(EObject.class, this, FamilyModelPackage.VARIANT_ARTEFACT__ARTEFACTS);
		}
		return artefacts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FamilyModelPackage.VARIANT_ARTEFACT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Variant> getOrigins() {
		if (origins == null) {
			origins = new EObjectResolvingEList<Variant>(Variant.class, this, FamilyModelPackage.VARIANT_ARTEFACT__ORIGINS);
		}
		return origins;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FamilyModelPackage.VARIANT_ARTEFACT__ARTEFACTS:
				return getArtefacts();
			case FamilyModelPackage.VARIANT_ARTEFACT__NAME:
				return getName();
			case FamilyModelPackage.VARIANT_ARTEFACT__ORIGINS:
				return getOrigins();
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
			case FamilyModelPackage.VARIANT_ARTEFACT__ARTEFACTS:
				getArtefacts().clear();
				getArtefacts().addAll((Collection<? extends EObject>)newValue);
				return;
			case FamilyModelPackage.VARIANT_ARTEFACT__NAME:
				setName((String)newValue);
				return;
			case FamilyModelPackage.VARIANT_ARTEFACT__ORIGINS:
				getOrigins().clear();
				getOrigins().addAll((Collection<? extends Variant>)newValue);
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
			case FamilyModelPackage.VARIANT_ARTEFACT__ARTEFACTS:
				getArtefacts().clear();
				return;
			case FamilyModelPackage.VARIANT_ARTEFACT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case FamilyModelPackage.VARIANT_ARTEFACT__ORIGINS:
				getOrigins().clear();
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
			case FamilyModelPackage.VARIANT_ARTEFACT__ARTEFACTS:
				return artefacts != null && !artefacts.isEmpty();
			case FamilyModelPackage.VARIANT_ARTEFACT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case FamilyModelPackage.VARIANT_ARTEFACT__ORIGINS:
				return origins != null && !origins.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //VariantArtefactImpl

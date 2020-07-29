/**
 */
package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelPackage;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariabilityCategory;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariantArtefact;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint;

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
 * An implementation of the model object '<em><b>Variation Point</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.VariationPointImpl#getVariantArtefacts <em>Variant Artefacts</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.VariationPointImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.VariationPointImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.VariationPointImpl#getVariabilityCategory <em>Variability Category</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.VariationPointImpl#getName <em>Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VariationPointImpl extends MinimalEObjectImpl.Container implements VariationPoint {
	/**
	 * The cached value of the '{@link #getVariantArtefacts() <em>Variant Artefacts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariantArtefacts()
	 * @generated
	 * @ordered
	 */
	protected EList<VariantArtefact> variantArtefacts;

	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<VariationPoint> children;

	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected VariationPoint parent;

	/**
	 * The default value of the '{@link #getVariabilityCategory() <em>Variability Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariabilityCategory()
	 * @generated
	 * @ordered
	 */
	protected static final VariabilityCategory VARIABILITY_CATEGORY_EDEFAULT = VariabilityCategory.UNSET;

	/**
	 * The cached value of the '{@link #getVariabilityCategory() <em>Variability Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariabilityCategory()
	 * @generated
	 * @ordered
	 */
	protected VariabilityCategory variabilityCategory = VARIABILITY_CATEGORY_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VariationPointImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FamilyModelPackage.Literals.VARIATION_POINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VariantArtefact> getVariantArtefacts() {
		if (variantArtefacts == null) {
			variantArtefacts = new EObjectContainmentEList<VariantArtefact>(VariantArtefact.class, this, FamilyModelPackage.VARIATION_POINT__VARIANT_ARTEFACTS);
		}
		return variantArtefacts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VariationPoint> getChildren() {
		if (children == null) {
			children = new EObjectContainmentEList<VariationPoint>(VariationPoint.class, this, FamilyModelPackage.VARIATION_POINT__CHILDREN);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VariationPoint getParent() {
		if (parent != null && parent.eIsProxy()) {
			InternalEObject oldParent = (InternalEObject)parent;
			parent = (VariationPoint)eResolveProxy(oldParent);
			if (parent != oldParent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FamilyModelPackage.VARIATION_POINT__PARENT, oldParent, parent));
			}
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VariationPoint basicGetParent() {
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setParent(VariationPoint newParent) {
		VariationPoint oldParent = parent;
		parent = newParent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FamilyModelPackage.VARIATION_POINT__PARENT, oldParent, parent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VariabilityCategory getVariabilityCategory() {
		return variabilityCategory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVariabilityCategory(VariabilityCategory newVariabilityCategory) {
		VariabilityCategory oldVariabilityCategory = variabilityCategory;
		variabilityCategory = newVariabilityCategory == null ? VARIABILITY_CATEGORY_EDEFAULT : newVariabilityCategory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FamilyModelPackage.VARIATION_POINT__VARIABILITY_CATEGORY, oldVariabilityCategory, variabilityCategory));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FamilyModelPackage.VARIATION_POINT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FamilyModelPackage.VARIATION_POINT__VARIANT_ARTEFACTS:
				return ((InternalEList<?>)getVariantArtefacts()).basicRemove(otherEnd, msgs);
			case FamilyModelPackage.VARIATION_POINT__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
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
			case FamilyModelPackage.VARIATION_POINT__VARIANT_ARTEFACTS:
				return getVariantArtefacts();
			case FamilyModelPackage.VARIATION_POINT__CHILDREN:
				return getChildren();
			case FamilyModelPackage.VARIATION_POINT__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
			case FamilyModelPackage.VARIATION_POINT__VARIABILITY_CATEGORY:
				return getVariabilityCategory();
			case FamilyModelPackage.VARIATION_POINT__NAME:
				return getName();
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
			case FamilyModelPackage.VARIATION_POINT__VARIANT_ARTEFACTS:
				getVariantArtefacts().clear();
				getVariantArtefacts().addAll((Collection<? extends VariantArtefact>)newValue);
				return;
			case FamilyModelPackage.VARIATION_POINT__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends VariationPoint>)newValue);
				return;
			case FamilyModelPackage.VARIATION_POINT__PARENT:
				setParent((VariationPoint)newValue);
				return;
			case FamilyModelPackage.VARIATION_POINT__VARIABILITY_CATEGORY:
				setVariabilityCategory((VariabilityCategory)newValue);
				return;
			case FamilyModelPackage.VARIATION_POINT__NAME:
				setName((String)newValue);
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
			case FamilyModelPackage.VARIATION_POINT__VARIANT_ARTEFACTS:
				getVariantArtefacts().clear();
				return;
			case FamilyModelPackage.VARIATION_POINT__CHILDREN:
				getChildren().clear();
				return;
			case FamilyModelPackage.VARIATION_POINT__PARENT:
				setParent((VariationPoint)null);
				return;
			case FamilyModelPackage.VARIATION_POINT__VARIABILITY_CATEGORY:
				setVariabilityCategory(VARIABILITY_CATEGORY_EDEFAULT);
				return;
			case FamilyModelPackage.VARIATION_POINT__NAME:
				setName(NAME_EDEFAULT);
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
			case FamilyModelPackage.VARIATION_POINT__VARIANT_ARTEFACTS:
				return variantArtefacts != null && !variantArtefacts.isEmpty();
			case FamilyModelPackage.VARIATION_POINT__CHILDREN:
				return children != null && !children.isEmpty();
			case FamilyModelPackage.VARIATION_POINT__PARENT:
				return parent != null;
			case FamilyModelPackage.VARIATION_POINT__VARIABILITY_CATEGORY:
				return variabilityCategory != VARIABILITY_CATEGORY_EDEFAULT;
			case FamilyModelPackage.VARIATION_POINT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
		result.append(" (variabilityCategory: ");
		result.append(variabilityCategory);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //VariationPointImpl

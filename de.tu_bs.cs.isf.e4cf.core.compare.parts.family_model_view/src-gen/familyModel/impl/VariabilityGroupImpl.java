/**
 */
package familyModel.impl;

import familyModel.Element;
import familyModel.FamilyModelPackage;
import familyModel.VariabilityCategory;
import familyModel.VariabilityGroup;

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
 * An implementation of the model object '<em><b>Variability Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link familyModel.impl.VariabilityGroupImpl#getVariability <em>Variability</em>}</li>
 *   <li>{@link familyModel.impl.VariabilityGroupImpl#getSubGroups <em>Sub Groups</em>}</li>
 *   <li>{@link familyModel.impl.VariabilityGroupImpl#getElements <em>Elements</em>}</li>
 *   <li>{@link familyModel.impl.VariabilityGroupImpl#getGroupName <em>Group Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VariabilityGroupImpl extends MinimalEObjectImpl.Container implements VariabilityGroup {
	/**
	 * The default value of the '{@link #getVariability() <em>Variability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariability()
	 * @generated
	 * @ordered
	 */
	protected static final VariabilityCategory VARIABILITY_EDEFAULT = VariabilityCategory.UNDEFINED;

	/**
	 * The cached value of the '{@link #getVariability() <em>Variability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariability()
	 * @generated
	 * @ordered
	 */
	protected VariabilityCategory variability = VARIABILITY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSubGroups() <em>Sub Groups</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubGroups()
	 * @generated
	 * @ordered
	 */
	protected EList<VariabilityGroup> subGroups;

	/**
	 * The cached value of the '{@link #getElements() <em>Elements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElements()
	 * @generated
	 * @ordered
	 */
	protected EList<Element> elements;

	/**
	 * The default value of the '{@link #getGroupName() <em>Group Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroupName()
	 * @generated
	 * @ordered
	 */
	protected static final String GROUP_NAME_EDEFAULT = "\"\"";

	/**
	 * The cached value of the '{@link #getGroupName() <em>Group Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroupName()
	 * @generated
	 * @ordered
	 */
	protected String groupName = GROUP_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VariabilityGroupImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FamilyModelPackage.Literals.VARIABILITY_GROUP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VariabilityCategory getVariability() {
		return variability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVariability(VariabilityCategory newVariability) {
		VariabilityCategory oldVariability = variability;
		variability = newVariability == null ? VARIABILITY_EDEFAULT : newVariability;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FamilyModelPackage.VARIABILITY_GROUP__VARIABILITY, oldVariability, variability));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VariabilityGroup> getSubGroups() {
		if (subGroups == null) {
			subGroups = new EObjectContainmentEList<VariabilityGroup>(VariabilityGroup.class, this, FamilyModelPackage.VARIABILITY_GROUP__SUB_GROUPS);
		}
		return subGroups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Element> getElements() {
		if (elements == null) {
			elements = new EObjectContainmentEList<Element>(Element.class, this, FamilyModelPackage.VARIABILITY_GROUP__ELEMENTS);
		}
		return elements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getGroupName() {
		return groupName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGroupName(String newGroupName) {
		String oldGroupName = groupName;
		groupName = newGroupName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FamilyModelPackage.VARIABILITY_GROUP__GROUP_NAME, oldGroupName, groupName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FamilyModelPackage.VARIABILITY_GROUP__SUB_GROUPS:
				return ((InternalEList<?>)getSubGroups()).basicRemove(otherEnd, msgs);
			case FamilyModelPackage.VARIABILITY_GROUP__ELEMENTS:
				return ((InternalEList<?>)getElements()).basicRemove(otherEnd, msgs);
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
			case FamilyModelPackage.VARIABILITY_GROUP__VARIABILITY:
				return getVariability();
			case FamilyModelPackage.VARIABILITY_GROUP__SUB_GROUPS:
				return getSubGroups();
			case FamilyModelPackage.VARIABILITY_GROUP__ELEMENTS:
				return getElements();
			case FamilyModelPackage.VARIABILITY_GROUP__GROUP_NAME:
				return getGroupName();
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
			case FamilyModelPackage.VARIABILITY_GROUP__VARIABILITY:
				setVariability((VariabilityCategory)newValue);
				return;
			case FamilyModelPackage.VARIABILITY_GROUP__SUB_GROUPS:
				getSubGroups().clear();
				getSubGroups().addAll((Collection<? extends VariabilityGroup>)newValue);
				return;
			case FamilyModelPackage.VARIABILITY_GROUP__ELEMENTS:
				getElements().clear();
				getElements().addAll((Collection<? extends Element>)newValue);
				return;
			case FamilyModelPackage.VARIABILITY_GROUP__GROUP_NAME:
				setGroupName((String)newValue);
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
			case FamilyModelPackage.VARIABILITY_GROUP__VARIABILITY:
				setVariability(VARIABILITY_EDEFAULT);
				return;
			case FamilyModelPackage.VARIABILITY_GROUP__SUB_GROUPS:
				getSubGroups().clear();
				return;
			case FamilyModelPackage.VARIABILITY_GROUP__ELEMENTS:
				getElements().clear();
				return;
			case FamilyModelPackage.VARIABILITY_GROUP__GROUP_NAME:
				setGroupName(GROUP_NAME_EDEFAULT);
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
			case FamilyModelPackage.VARIABILITY_GROUP__VARIABILITY:
				return variability != VARIABILITY_EDEFAULT;
			case FamilyModelPackage.VARIABILITY_GROUP__SUB_GROUPS:
				return subGroups != null && !subGroups.isEmpty();
			case FamilyModelPackage.VARIABILITY_GROUP__ELEMENTS:
				return elements != null && !elements.isEmpty();
			case FamilyModelPackage.VARIABILITY_GROUP__GROUP_NAME:
				return GROUP_NAME_EDEFAULT == null ? groupName != null : !GROUP_NAME_EDEFAULT.equals(groupName);
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
		result.append(" (variability: ");
		result.append(variability);
		result.append(", groupName: ");
		result.append(groupName);
		result.append(')');
		return result.toString();
	}

} //VariabilityGroupImpl

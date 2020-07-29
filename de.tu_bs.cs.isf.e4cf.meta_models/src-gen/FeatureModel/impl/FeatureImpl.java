/**
 */
package FeatureModel.impl;

import FeatureModel.Feature;
import FeatureModel.FeatureModelPackage;
import FeatureModel.Graphic;

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
 * An implementation of the model object '<em><b>Feature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link FeatureModel.impl.FeatureImpl#getName <em>Name</em>}</li>
 *   <li>{@link FeatureModel.impl.FeatureImpl#isMandatory <em>Mandatory</em>}</li>
 *   <li>{@link FeatureModel.impl.FeatureImpl#getAnd <em>And</em>}</li>
 *   <li>{@link FeatureModel.impl.FeatureImpl#getOr <em>Or</em>}</li>
 *   <li>{@link FeatureModel.impl.FeatureImpl#getFeature <em>Feature</em>}</li>
 *   <li>{@link FeatureModel.impl.FeatureImpl#isHidden <em>Hidden</em>}</li>
 *   <li>{@link FeatureModel.impl.FeatureImpl#isAbstract <em>Abstract</em>}</li>
 *   <li>{@link FeatureModel.impl.FeatureImpl#getAlt <em>Alt</em>}</li>
 *   <li>{@link FeatureModel.impl.FeatureImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link FeatureModel.impl.FeatureImpl#getGraphics <em>Graphics</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FeatureImpl extends MinimalEObjectImpl.Container implements Feature {
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
	 * The default value of the '{@link #isMandatory() <em>Mandatory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMandatory()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MANDATORY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMandatory() <em>Mandatory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMandatory()
	 * @generated
	 * @ordered
	 */
	protected boolean mandatory = MANDATORY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAnd() <em>And</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnd()
	 * @generated
	 * @ordered
	 */
	protected EList<Feature> and;

	/**
	 * The cached value of the '{@link #getOr() <em>Or</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOr()
	 * @generated
	 * @ordered
	 */
	protected EList<Feature> or;

	/**
	 * The cached value of the '{@link #getFeature() <em>Feature</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeature()
	 * @generated
	 * @ordered
	 */
	protected EList<Feature> feature;

	/**
	 * The default value of the '{@link #isHidden() <em>Hidden</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHidden()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HIDDEN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHidden() <em>Hidden</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHidden()
	 * @generated
	 * @ordered
	 */
	protected boolean hidden = HIDDEN_EDEFAULT;

	/**
	 * The default value of the '{@link #isAbstract() <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbstract()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ABSTRACT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAbstract() <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbstract()
	 * @generated
	 * @ordered
	 */
	protected boolean abstract_ = ABSTRACT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAlt() <em>Alt</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlt()
	 * @generated
	 * @ordered
	 */
	protected EList<Feature> alt;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

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
	 * The cached value of the '{@link #getGraphics() <em>Graphics</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGraphics()
	 * @generated
	 * @ordered
	 */
	protected Graphic graphics;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FeatureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FeatureModelPackage.Literals.FEATURE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureModelPackage.FEATURE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isMandatory() {
		return mandatory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMandatory(boolean newMandatory) {
		boolean oldMandatory = mandatory;
		mandatory = newMandatory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureModelPackage.FEATURE__MANDATORY, oldMandatory, mandatory));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Feature> getAnd() {
		if (and == null) {
			and = new EObjectContainmentEList<Feature>(Feature.class, this, FeatureModelPackage.FEATURE__AND);
		}
		return and;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Feature> getOr() {
		if (or == null) {
			or = new EObjectContainmentEList<Feature>(Feature.class, this, FeatureModelPackage.FEATURE__OR);
		}
		return or;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Feature> getFeature() {
		if (feature == null) {
			feature = new EObjectContainmentEList<Feature>(Feature.class, this, FeatureModelPackage.FEATURE__FEATURE);
		}
		return feature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHidden(boolean newHidden) {
		boolean oldHidden = hidden;
		hidden = newHidden;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureModelPackage.FEATURE__HIDDEN, oldHidden, hidden));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAbstract() {
		return abstract_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAbstract(boolean newAbstract) {
		boolean oldAbstract = abstract_;
		abstract_ = newAbstract;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureModelPackage.FEATURE__ABSTRACT, oldAbstract, abstract_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Feature> getAlt() {
		if (alt == null) {
			alt = new EObjectContainmentEList<Feature>(Feature.class, this, FeatureModelPackage.FEATURE__ALT);
		}
		return alt;
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
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureModelPackage.FEATURE__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Graphic getGraphics() {
		if (graphics != null && graphics.eIsProxy()) {
			InternalEObject oldGraphics = (InternalEObject)graphics;
			graphics = (Graphic)eResolveProxy(oldGraphics);
			if (graphics != oldGraphics) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FeatureModelPackage.FEATURE__GRAPHICS, oldGraphics, graphics));
			}
		}
		return graphics;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Graphic basicGetGraphics() {
		return graphics;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGraphics(Graphic newGraphics) {
		Graphic oldGraphics = graphics;
		graphics = newGraphics;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureModelPackage.FEATURE__GRAPHICS, oldGraphics, graphics));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FeatureModelPackage.FEATURE__AND:
				return ((InternalEList<?>)getAnd()).basicRemove(otherEnd, msgs);
			case FeatureModelPackage.FEATURE__OR:
				return ((InternalEList<?>)getOr()).basicRemove(otherEnd, msgs);
			case FeatureModelPackage.FEATURE__FEATURE:
				return ((InternalEList<?>)getFeature()).basicRemove(otherEnd, msgs);
			case FeatureModelPackage.FEATURE__ALT:
				return ((InternalEList<?>)getAlt()).basicRemove(otherEnd, msgs);
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
			case FeatureModelPackage.FEATURE__NAME:
				return getName();
			case FeatureModelPackage.FEATURE__MANDATORY:
				return isMandatory();
			case FeatureModelPackage.FEATURE__AND:
				return getAnd();
			case FeatureModelPackage.FEATURE__OR:
				return getOr();
			case FeatureModelPackage.FEATURE__FEATURE:
				return getFeature();
			case FeatureModelPackage.FEATURE__HIDDEN:
				return isHidden();
			case FeatureModelPackage.FEATURE__ABSTRACT:
				return isAbstract();
			case FeatureModelPackage.FEATURE__ALT:
				return getAlt();
			case FeatureModelPackage.FEATURE__DESCRIPTION:
				return getDescription();
			case FeatureModelPackage.FEATURE__GRAPHICS:
				if (resolve) return getGraphics();
				return basicGetGraphics();
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
			case FeatureModelPackage.FEATURE__NAME:
				setName((String)newValue);
				return;
			case FeatureModelPackage.FEATURE__MANDATORY:
				setMandatory((Boolean)newValue);
				return;
			case FeatureModelPackage.FEATURE__AND:
				getAnd().clear();
				getAnd().addAll((Collection<? extends Feature>)newValue);
				return;
			case FeatureModelPackage.FEATURE__OR:
				getOr().clear();
				getOr().addAll((Collection<? extends Feature>)newValue);
				return;
			case FeatureModelPackage.FEATURE__FEATURE:
				getFeature().clear();
				getFeature().addAll((Collection<? extends Feature>)newValue);
				return;
			case FeatureModelPackage.FEATURE__HIDDEN:
				setHidden((Boolean)newValue);
				return;
			case FeatureModelPackage.FEATURE__ABSTRACT:
				setAbstract((Boolean)newValue);
				return;
			case FeatureModelPackage.FEATURE__ALT:
				getAlt().clear();
				getAlt().addAll((Collection<? extends Feature>)newValue);
				return;
			case FeatureModelPackage.FEATURE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case FeatureModelPackage.FEATURE__GRAPHICS:
				setGraphics((Graphic)newValue);
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
			case FeatureModelPackage.FEATURE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case FeatureModelPackage.FEATURE__MANDATORY:
				setMandatory(MANDATORY_EDEFAULT);
				return;
			case FeatureModelPackage.FEATURE__AND:
				getAnd().clear();
				return;
			case FeatureModelPackage.FEATURE__OR:
				getOr().clear();
				return;
			case FeatureModelPackage.FEATURE__FEATURE:
				getFeature().clear();
				return;
			case FeatureModelPackage.FEATURE__HIDDEN:
				setHidden(HIDDEN_EDEFAULT);
				return;
			case FeatureModelPackage.FEATURE__ABSTRACT:
				setAbstract(ABSTRACT_EDEFAULT);
				return;
			case FeatureModelPackage.FEATURE__ALT:
				getAlt().clear();
				return;
			case FeatureModelPackage.FEATURE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case FeatureModelPackage.FEATURE__GRAPHICS:
				setGraphics((Graphic)null);
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
			case FeatureModelPackage.FEATURE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case FeatureModelPackage.FEATURE__MANDATORY:
				return mandatory != MANDATORY_EDEFAULT;
			case FeatureModelPackage.FEATURE__AND:
				return and != null && !and.isEmpty();
			case FeatureModelPackage.FEATURE__OR:
				return or != null && !or.isEmpty();
			case FeatureModelPackage.FEATURE__FEATURE:
				return feature != null && !feature.isEmpty();
			case FeatureModelPackage.FEATURE__HIDDEN:
				return hidden != HIDDEN_EDEFAULT;
			case FeatureModelPackage.FEATURE__ABSTRACT:
				return abstract_ != ABSTRACT_EDEFAULT;
			case FeatureModelPackage.FEATURE__ALT:
				return alt != null && !alt.isEmpty();
			case FeatureModelPackage.FEATURE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case FeatureModelPackage.FEATURE__GRAPHICS:
				return graphics != null;
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
		result.append(", mandatory: ");
		result.append(mandatory);
		result.append(", hidden: ");
		result.append(hidden);
		result.append(", abstract: ");
		result.append(abstract_);
		result.append(", description: ");
		result.append(description);
		result.append(')');
		return result.toString();
	}

} //FeatureImpl

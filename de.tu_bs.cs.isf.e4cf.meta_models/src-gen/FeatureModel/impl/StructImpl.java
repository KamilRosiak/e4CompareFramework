/**
 */
package FeatureModel.impl;

import FeatureModel.Feature;
import FeatureModel.FeatureModelPackage;
import FeatureModel.Struct;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Struct</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link FeatureModel.impl.StructImpl#getAnd <em>And</em>}</li>
 *   <li>{@link FeatureModel.impl.StructImpl#getOr <em>Or</em>}</li>
 *   <li>{@link FeatureModel.impl.StructImpl#getFeature <em>Feature</em>}</li>
 *   <li>{@link FeatureModel.impl.StructImpl#getAlt <em>Alt</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StructImpl extends MinimalEObjectImpl.Container implements Struct {
	/**
	 * The cached value of the '{@link #getAnd() <em>And</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnd()
	 * @generated
	 * @ordered
	 */
	protected Feature and;

	/**
	 * The cached value of the '{@link #getOr() <em>Or</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOr()
	 * @generated
	 * @ordered
	 */
	protected Feature or;

	/**
	 * The cached value of the '{@link #getFeature() <em>Feature</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeature()
	 * @generated
	 * @ordered
	 */
	protected Feature feature;

	/**
	 * The cached value of the '{@link #getAlt() <em>Alt</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlt()
	 * @generated
	 * @ordered
	 */
	protected Feature alt;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StructImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FeatureModelPackage.Literals.STRUCT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Feature getAnd() {
		return and;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAnd(Feature newAnd, NotificationChain msgs) {
		Feature oldAnd = and;
		and = newAnd;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FeatureModelPackage.STRUCT__AND, oldAnd, newAnd);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAnd(Feature newAnd) {
		if (newAnd != and) {
			NotificationChain msgs = null;
			if (and != null)
				msgs = ((InternalEObject)and).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FeatureModelPackage.STRUCT__AND, null, msgs);
			if (newAnd != null)
				msgs = ((InternalEObject)newAnd).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FeatureModelPackage.STRUCT__AND, null, msgs);
			msgs = basicSetAnd(newAnd, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureModelPackage.STRUCT__AND, newAnd, newAnd));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Feature getOr() {
		return or;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOr(Feature newOr, NotificationChain msgs) {
		Feature oldOr = or;
		or = newOr;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FeatureModelPackage.STRUCT__OR, oldOr, newOr);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOr(Feature newOr) {
		if (newOr != or) {
			NotificationChain msgs = null;
			if (or != null)
				msgs = ((InternalEObject)or).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FeatureModelPackage.STRUCT__OR, null, msgs);
			if (newOr != null)
				msgs = ((InternalEObject)newOr).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FeatureModelPackage.STRUCT__OR, null, msgs);
			msgs = basicSetOr(newOr, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureModelPackage.STRUCT__OR, newOr, newOr));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Feature getFeature() {
		return feature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFeature(Feature newFeature, NotificationChain msgs) {
		Feature oldFeature = feature;
		feature = newFeature;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FeatureModelPackage.STRUCT__FEATURE, oldFeature, newFeature);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFeature(Feature newFeature) {
		if (newFeature != feature) {
			NotificationChain msgs = null;
			if (feature != null)
				msgs = ((InternalEObject)feature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FeatureModelPackage.STRUCT__FEATURE, null, msgs);
			if (newFeature != null)
				msgs = ((InternalEObject)newFeature).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FeatureModelPackage.STRUCT__FEATURE, null, msgs);
			msgs = basicSetFeature(newFeature, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureModelPackage.STRUCT__FEATURE, newFeature, newFeature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Feature getAlt() {
		return alt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAlt(Feature newAlt, NotificationChain msgs) {
		Feature oldAlt = alt;
		alt = newAlt;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FeatureModelPackage.STRUCT__ALT, oldAlt, newAlt);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAlt(Feature newAlt) {
		if (newAlt != alt) {
			NotificationChain msgs = null;
			if (alt != null)
				msgs = ((InternalEObject)alt).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FeatureModelPackage.STRUCT__ALT, null, msgs);
			if (newAlt != null)
				msgs = ((InternalEObject)newAlt).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FeatureModelPackage.STRUCT__ALT, null, msgs);
			msgs = basicSetAlt(newAlt, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureModelPackage.STRUCT__ALT, newAlt, newAlt));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FeatureModelPackage.STRUCT__AND:
				return basicSetAnd(null, msgs);
			case FeatureModelPackage.STRUCT__OR:
				return basicSetOr(null, msgs);
			case FeatureModelPackage.STRUCT__FEATURE:
				return basicSetFeature(null, msgs);
			case FeatureModelPackage.STRUCT__ALT:
				return basicSetAlt(null, msgs);
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
			case FeatureModelPackage.STRUCT__AND:
				return getAnd();
			case FeatureModelPackage.STRUCT__OR:
				return getOr();
			case FeatureModelPackage.STRUCT__FEATURE:
				return getFeature();
			case FeatureModelPackage.STRUCT__ALT:
				return getAlt();
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
			case FeatureModelPackage.STRUCT__AND:
				setAnd((Feature)newValue);
				return;
			case FeatureModelPackage.STRUCT__OR:
				setOr((Feature)newValue);
				return;
			case FeatureModelPackage.STRUCT__FEATURE:
				setFeature((Feature)newValue);
				return;
			case FeatureModelPackage.STRUCT__ALT:
				setAlt((Feature)newValue);
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
			case FeatureModelPackage.STRUCT__AND:
				setAnd((Feature)null);
				return;
			case FeatureModelPackage.STRUCT__OR:
				setOr((Feature)null);
				return;
			case FeatureModelPackage.STRUCT__FEATURE:
				setFeature((Feature)null);
				return;
			case FeatureModelPackage.STRUCT__ALT:
				setAlt((Feature)null);
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
			case FeatureModelPackage.STRUCT__AND:
				return and != null;
			case FeatureModelPackage.STRUCT__OR:
				return or != null;
			case FeatureModelPackage.STRUCT__FEATURE:
				return feature != null;
			case FeatureModelPackage.STRUCT__ALT:
				return alt != null;
		}
		return super.eIsSet(featureID);
	}

} //StructImpl

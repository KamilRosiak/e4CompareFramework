/**
 */
package FeatureDiagram.impl;

import FeatureDiagram.ComponentFeature;
import FeatureDiagram.FeatureDiagramPackage;
import FeatureDiagram.FeatureDiagramm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Component Feature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link FeatureDiagram.impl.ComponentFeatureImpl#getFeaturediagramm <em>Featurediagramm</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ComponentFeatureImpl extends FeatureImpl implements ComponentFeature {
	/**
	 * The cached value of the '{@link #getFeaturediagramm() <em>Featurediagramm</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeaturediagramm()
	 * @generated
	 * @ordered
	 */
	protected FeatureDiagramm featurediagramm;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentFeatureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FeatureDiagramPackage.Literals.COMPONENT_FEATURE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureDiagramm getFeaturediagramm() {
		return featurediagramm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFeaturediagramm(FeatureDiagramm newFeaturediagramm, NotificationChain msgs) {
		FeatureDiagramm oldFeaturediagramm = featurediagramm;
		featurediagramm = newFeaturediagramm;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FeatureDiagramPackage.COMPONENT_FEATURE__FEATUREDIAGRAMM, oldFeaturediagramm, newFeaturediagramm);
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
	public void setFeaturediagramm(FeatureDiagramm newFeaturediagramm) {
		if (newFeaturediagramm != featurediagramm) {
			NotificationChain msgs = null;
			if (featurediagramm != null)
				msgs = ((InternalEObject)featurediagramm).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FeatureDiagramPackage.COMPONENT_FEATURE__FEATUREDIAGRAMM, null, msgs);
			if (newFeaturediagramm != null)
				msgs = ((InternalEObject)newFeaturediagramm).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FeatureDiagramPackage.COMPONENT_FEATURE__FEATUREDIAGRAMM, null, msgs);
			msgs = basicSetFeaturediagramm(newFeaturediagramm, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramPackage.COMPONENT_FEATURE__FEATUREDIAGRAMM, newFeaturediagramm, newFeaturediagramm));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FeatureDiagramPackage.COMPONENT_FEATURE__FEATUREDIAGRAMM:
				return basicSetFeaturediagramm(null, msgs);
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
			case FeatureDiagramPackage.COMPONENT_FEATURE__FEATUREDIAGRAMM:
				return getFeaturediagramm();
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
			case FeatureDiagramPackage.COMPONENT_FEATURE__FEATUREDIAGRAMM:
				setFeaturediagramm((FeatureDiagramm)newValue);
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
			case FeatureDiagramPackage.COMPONENT_FEATURE__FEATUREDIAGRAMM:
				setFeaturediagramm((FeatureDiagramm)null);
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
			case FeatureDiagramPackage.COMPONENT_FEATURE__FEATUREDIAGRAMM:
				return featurediagramm != null;
		}
		return super.eIsSet(featureID);
	}

} //ComponentFeatureImpl

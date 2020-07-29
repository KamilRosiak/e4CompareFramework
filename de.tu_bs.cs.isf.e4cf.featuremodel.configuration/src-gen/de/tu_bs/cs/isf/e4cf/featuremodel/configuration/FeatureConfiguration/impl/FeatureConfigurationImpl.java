/**
 */
package de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.impl;

import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramm;

import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfiguration;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfigurationPackage;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.impl.FeatureConfigurationImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.impl.FeatureConfigurationImpl#getFeatureSelection <em>Feature Selection</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.impl.FeatureConfigurationImpl#getFeatureDiagram <em>Feature Diagram</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FeatureConfigurationImpl extends MinimalEObjectImpl.Container implements FeatureConfiguration {
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
	 * The cached value of the '{@link #getFeatureSelection() <em>Feature Selection</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureSelection()
	 * @generated
	 * @ordered
	 */
	protected EMap<Feature, Boolean> featureSelection;

	/**
	 * The cached value of the '{@link #getFeatureDiagram() <em>Feature Diagram</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureDiagram()
	 * @generated
	 * @ordered
	 */
	protected FeatureDiagramm featureDiagram;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FeatureConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FeatureConfigurationPackage.Literals.FEATURE_CONFIGURATION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureConfigurationPackage.FEATURE_CONFIGURATION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<Feature, Boolean> getFeatureSelection() {
		if (featureSelection == null) {
			featureSelection = new EcoreEMap<Feature,Boolean>(FeatureConfigurationPackage.Literals.FEATURE_TO_BOOLEAN_MAP, FeatureToBooleanMapImpl.class, this, FeatureConfigurationPackage.FEATURE_CONFIGURATION__FEATURE_SELECTION);
		}
		return featureSelection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureDiagramm getFeatureDiagram() {
		if (featureDiagram != null && featureDiagram.eIsProxy()) {
			InternalEObject oldFeatureDiagram = (InternalEObject)featureDiagram;
			featureDiagram = (FeatureDiagramm)eResolveProxy(oldFeatureDiagram);
			if (featureDiagram != oldFeatureDiagram) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FeatureConfigurationPackage.FEATURE_CONFIGURATION__FEATURE_DIAGRAM, oldFeatureDiagram, featureDiagram));
			}
		}
		return featureDiagram;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureDiagramm basicGetFeatureDiagram() {
		return featureDiagram;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFeatureDiagram(FeatureDiagramm newFeatureDiagram) {
		FeatureDiagramm oldFeatureDiagram = featureDiagram;
		featureDiagram = newFeatureDiagram;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureConfigurationPackage.FEATURE_CONFIGURATION__FEATURE_DIAGRAM, oldFeatureDiagram, featureDiagram));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FeatureConfigurationPackage.FEATURE_CONFIGURATION__FEATURE_SELECTION:
				return ((InternalEList<?>)getFeatureSelection()).basicRemove(otherEnd, msgs);
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
			case FeatureConfigurationPackage.FEATURE_CONFIGURATION__NAME:
				return getName();
			case FeatureConfigurationPackage.FEATURE_CONFIGURATION__FEATURE_SELECTION:
				if (coreType) return getFeatureSelection();
				else return getFeatureSelection().map();
			case FeatureConfigurationPackage.FEATURE_CONFIGURATION__FEATURE_DIAGRAM:
				if (resolve) return getFeatureDiagram();
				return basicGetFeatureDiagram();
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
			case FeatureConfigurationPackage.FEATURE_CONFIGURATION__NAME:
				setName((String)newValue);
				return;
			case FeatureConfigurationPackage.FEATURE_CONFIGURATION__FEATURE_SELECTION:
				((EStructuralFeature.Setting)getFeatureSelection()).set(newValue);
				return;
			case FeatureConfigurationPackage.FEATURE_CONFIGURATION__FEATURE_DIAGRAM:
				setFeatureDiagram((FeatureDiagramm)newValue);
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
			case FeatureConfigurationPackage.FEATURE_CONFIGURATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case FeatureConfigurationPackage.FEATURE_CONFIGURATION__FEATURE_SELECTION:
				getFeatureSelection().clear();
				return;
			case FeatureConfigurationPackage.FEATURE_CONFIGURATION__FEATURE_DIAGRAM:
				setFeatureDiagram((FeatureDiagramm)null);
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
			case FeatureConfigurationPackage.FEATURE_CONFIGURATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case FeatureConfigurationPackage.FEATURE_CONFIGURATION__FEATURE_SELECTION:
				return featureSelection != null && !featureSelection.isEmpty();
			case FeatureConfigurationPackage.FEATURE_CONFIGURATION__FEATURE_DIAGRAM:
				return featureDiagram != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case FeatureConfigurationPackage.FEATURE_CONFIGURATION___TO_STRING:
				return toString();
		}
		return super.eInvoke(operationID, arguments);
	}

} //FeatureConfigurationImpl

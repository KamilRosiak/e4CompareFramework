/**
 */
package FeatureDiagram.impl;

import FeatureDiagram.ConfigurationFeature;
import FeatureDiagram.FeatureDiagramPackage;

import featureConfiguration.FeatureConfiguration;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Configuration Feature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link FeatureDiagram.impl.ConfigurationFeatureImpl#getConfigurationfeature <em>Configurationfeature</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConfigurationFeatureImpl extends FeatureImpl implements ConfigurationFeature {
	/**
	 * The cached value of the '{@link #getConfigurationfeature() <em>Configurationfeature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfigurationfeature()
	 * @generated
	 * @ordered
	 */
	protected FeatureConfiguration configurationfeature;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConfigurationFeatureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FeatureDiagramPackage.Literals.CONFIGURATION_FEATURE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureConfiguration getConfigurationfeature() {
		if (configurationfeature != null && configurationfeature.eIsProxy()) {
			InternalEObject oldConfigurationfeature = (InternalEObject)configurationfeature;
			configurationfeature = (FeatureConfiguration)eResolveProxy(oldConfigurationfeature);
			if (configurationfeature != oldConfigurationfeature) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FeatureDiagramPackage.CONFIGURATION_FEATURE__CONFIGURATIONFEATURE, oldConfigurationfeature, configurationfeature));
			}
		}
		return configurationfeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureConfiguration basicGetConfigurationfeature() {
		return configurationfeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setConfigurationfeature(FeatureConfiguration newConfigurationfeature) {
		FeatureConfiguration oldConfigurationfeature = configurationfeature;
		configurationfeature = newConfigurationfeature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramPackage.CONFIGURATION_FEATURE__CONFIGURATIONFEATURE, oldConfigurationfeature, configurationfeature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FeatureDiagramPackage.CONFIGURATION_FEATURE__CONFIGURATIONFEATURE:
				if (resolve) return getConfigurationfeature();
				return basicGetConfigurationfeature();
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
			case FeatureDiagramPackage.CONFIGURATION_FEATURE__CONFIGURATIONFEATURE:
				setConfigurationfeature((FeatureConfiguration)newValue);
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
			case FeatureDiagramPackage.CONFIGURATION_FEATURE__CONFIGURATIONFEATURE:
				setConfigurationfeature((FeatureConfiguration)null);
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
			case FeatureDiagramPackage.CONFIGURATION_FEATURE__CONFIGURATIONFEATURE:
				return configurationfeature != null;
		}
		return super.eIsSet(featureID);
	}

} //ConfigurationFeatureImpl

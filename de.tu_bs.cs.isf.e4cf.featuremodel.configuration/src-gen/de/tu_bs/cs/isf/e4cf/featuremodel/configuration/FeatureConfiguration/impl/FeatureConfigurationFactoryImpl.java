/**
 */
package de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.impl;

import FeatureDiagram.Feature;

import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.*;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class FeatureConfigurationFactoryImpl extends EFactoryImpl implements FeatureConfigurationFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FeatureConfigurationFactory init() {
		try {
			FeatureConfigurationFactory theFeatureConfigurationFactory = (FeatureConfigurationFactory)EPackage.Registry.INSTANCE.getEFactory(FeatureConfigurationPackage.eNS_URI);
			if (theFeatureConfigurationFactory != null) {
				return theFeatureConfigurationFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new FeatureConfigurationFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureConfigurationFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case FeatureConfigurationPackage.FEATURE_CONFIGURATION: return createFeatureConfiguration();
			case FeatureConfigurationPackage.FEATURE_TO_BOOLEAN_MAP: return (EObject)createFeatureToBooleanMap();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureConfiguration createFeatureConfiguration() {
		FeatureConfigurationImpl featureConfiguration = new FeatureConfigurationImpl();
		return featureConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<Feature, Boolean> createFeatureToBooleanMap() {
		FeatureToBooleanMapImpl featureToBooleanMap = new FeatureToBooleanMapImpl();
		return featureToBooleanMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureConfigurationPackage getFeatureConfigurationPackage() {
		return (FeatureConfigurationPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static FeatureConfigurationPackage getPackage() {
		return FeatureConfigurationPackage.eINSTANCE;
	}

} //FeatureConfigurationFactoryImpl

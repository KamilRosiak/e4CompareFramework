/**
 */
package de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfigurationFactory
 * @model kind="package"
 * @generated
 */
public interface FeatureConfigurationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "FeatureConfiguration";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "www.feature.configuration.de";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "fc";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FeatureConfigurationPackage eINSTANCE = de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.impl.FeatureConfigurationPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.impl.FeatureConfigurationImpl <em>Feature Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.impl.FeatureConfigurationImpl
	 * @see de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.impl.FeatureConfigurationPackageImpl#getFeatureConfiguration()
	 * @generated
	 */
	int FEATURE_CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CONFIGURATION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Feature Selection</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CONFIGURATION__FEATURE_SELECTION = 1;

	/**
	 * The feature id for the '<em><b>Feature Diagram</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CONFIGURATION__FEATURE_DIAGRAM = 2;

	/**
	 * The number of structural features of the '<em>Feature Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CONFIGURATION_FEATURE_COUNT = 3;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CONFIGURATION___TO_STRING = 0;

	/**
	 * The number of operations of the '<em>Feature Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CONFIGURATION_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.impl.FeatureToBooleanMapImpl <em>Feature To Boolean Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.impl.FeatureToBooleanMapImpl
	 * @see de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.impl.FeatureConfigurationPackageImpl#getFeatureToBooleanMap()
	 * @generated
	 */
	int FEATURE_TO_BOOLEAN_MAP = 1;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_TO_BOOLEAN_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_TO_BOOLEAN_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Feature To Boolean Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_TO_BOOLEAN_MAP_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Feature To Boolean Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_TO_BOOLEAN_MAP_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfiguration <em>Feature Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature Configuration</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfiguration
	 * @generated
	 */
	EClass getFeatureConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfiguration#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfiguration#getName()
	 * @see #getFeatureConfiguration()
	 * @generated
	 */
	EAttribute getFeatureConfiguration_Name();

	/**
	 * Returns the meta object for the map '{@link de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfiguration#getFeatureSelection <em>Feature Selection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Feature Selection</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfiguration#getFeatureSelection()
	 * @see #getFeatureConfiguration()
	 * @generated
	 */
	EReference getFeatureConfiguration_FeatureSelection();

	/**
	 * Returns the meta object for the reference '{@link de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfiguration#getFeatureDiagram <em>Feature Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Feature Diagram</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfiguration#getFeatureDiagram()
	 * @see #getFeatureConfiguration()
	 * @generated
	 */
	EReference getFeatureConfiguration_FeatureDiagram();

	/**
	 * Returns the meta object for the '{@link de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfiguration#toString() <em>To String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>To String</em>' operation.
	 * @see de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfiguration#toString()
	 * @generated
	 */
	EOperation getFeatureConfiguration__ToString();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Feature To Boolean Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature To Boolean Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="FeatureDiagram.Feature"
	 *        valueDefault="false" valueDataType="org.eclipse.emf.ecore.EBooleanObject"
	 * @generated
	 */
	EClass getFeatureToBooleanMap();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getFeatureToBooleanMap()
	 * @generated
	 */
	EReference getFeatureToBooleanMap_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getFeatureToBooleanMap()
	 * @generated
	 */
	EAttribute getFeatureToBooleanMap_Value();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	FeatureConfigurationFactory getFeatureConfigurationFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.impl.FeatureConfigurationImpl <em>Feature Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.impl.FeatureConfigurationImpl
		 * @see de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.impl.FeatureConfigurationPackageImpl#getFeatureConfiguration()
		 * @generated
		 */
		EClass FEATURE_CONFIGURATION = eINSTANCE.getFeatureConfiguration();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_CONFIGURATION__NAME = eINSTANCE.getFeatureConfiguration_Name();

		/**
		 * The meta object literal for the '<em><b>Feature Selection</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_CONFIGURATION__FEATURE_SELECTION = eINSTANCE.getFeatureConfiguration_FeatureSelection();

		/**
		 * The meta object literal for the '<em><b>Feature Diagram</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_CONFIGURATION__FEATURE_DIAGRAM = eINSTANCE.getFeatureConfiguration_FeatureDiagram();

		/**
		 * The meta object literal for the '<em><b>To String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FEATURE_CONFIGURATION___TO_STRING = eINSTANCE.getFeatureConfiguration__ToString();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.impl.FeatureToBooleanMapImpl <em>Feature To Boolean Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.impl.FeatureToBooleanMapImpl
		 * @see de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.impl.FeatureConfigurationPackageImpl#getFeatureToBooleanMap()
		 * @generated
		 */
		EClass FEATURE_TO_BOOLEAN_MAP = eINSTANCE.getFeatureToBooleanMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_TO_BOOLEAN_MAP__KEY = eINSTANCE.getFeatureToBooleanMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_TO_BOOLEAN_MAP__VALUE = eINSTANCE.getFeatureToBooleanMap_Value();

	}

} //FeatureConfigurationPackage

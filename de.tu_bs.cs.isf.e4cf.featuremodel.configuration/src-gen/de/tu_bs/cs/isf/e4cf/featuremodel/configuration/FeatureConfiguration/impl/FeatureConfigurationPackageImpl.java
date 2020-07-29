/**
 */
package de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.impl;

import CrossTreeConstraints.CrossTreeConstraintsPackage;

import CrossTreeConstraints.impl.CrossTreeConstraintsPackageImpl;

import FeatureDiagram.FeatureDiagramPackage;

import FeatureDiagram.impl.FeatureDiagramPackageImpl;

import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfiguration;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfigurationFactory;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfigurationPackage;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class FeatureConfigurationPackageImpl extends EPackageImpl implements FeatureConfigurationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass featureConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass featureToBooleanMapEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfigurationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private FeatureConfigurationPackageImpl() {
		super(eNS_URI, FeatureConfigurationFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link FeatureConfigurationPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static FeatureConfigurationPackage init() {
		if (isInited) return (FeatureConfigurationPackage)EPackage.Registry.INSTANCE.getEPackage(FeatureConfigurationPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredFeatureConfigurationPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		FeatureConfigurationPackageImpl theFeatureConfigurationPackage = registeredFeatureConfigurationPackage instanceof FeatureConfigurationPackageImpl ? (FeatureConfigurationPackageImpl)registeredFeatureConfigurationPackage : new FeatureConfigurationPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(FeatureDiagramPackage.eNS_URI);
		FeatureDiagramPackageImpl theFeatureDiagramPackage = (FeatureDiagramPackageImpl)(registeredPackage instanceof FeatureDiagramPackageImpl ? registeredPackage : FeatureDiagramPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(CrossTreeConstraintsPackage.eNS_URI);
		CrossTreeConstraintsPackageImpl theCrossTreeConstraintsPackage = (CrossTreeConstraintsPackageImpl)(registeredPackage instanceof CrossTreeConstraintsPackageImpl ? registeredPackage : CrossTreeConstraintsPackage.eINSTANCE);

		// Create package meta-data objects
		theFeatureConfigurationPackage.createPackageContents();
		theFeatureDiagramPackage.createPackageContents();
		theCrossTreeConstraintsPackage.createPackageContents();

		// Initialize created meta-data
		theFeatureConfigurationPackage.initializePackageContents();
		theFeatureDiagramPackage.initializePackageContents();
		theCrossTreeConstraintsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theFeatureConfigurationPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(FeatureConfigurationPackage.eNS_URI, theFeatureConfigurationPackage);
		return theFeatureConfigurationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFeatureConfiguration() {
		return featureConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureConfiguration_Name() {
		return (EAttribute)featureConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFeatureConfiguration_FeatureSelection() {
		return (EReference)featureConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFeatureConfiguration_FeatureDiagram() {
		return (EReference)featureConfigurationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getFeatureConfiguration__ToString() {
		return featureConfigurationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFeatureToBooleanMap() {
		return featureToBooleanMapEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFeatureToBooleanMap_Key() {
		return (EReference)featureToBooleanMapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureToBooleanMap_Value() {
		return (EAttribute)featureToBooleanMapEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureConfigurationFactory getFeatureConfigurationFactory() {
		return (FeatureConfigurationFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		featureConfigurationEClass = createEClass(FEATURE_CONFIGURATION);
		createEAttribute(featureConfigurationEClass, FEATURE_CONFIGURATION__NAME);
		createEReference(featureConfigurationEClass, FEATURE_CONFIGURATION__FEATURE_SELECTION);
		createEReference(featureConfigurationEClass, FEATURE_CONFIGURATION__FEATURE_DIAGRAM);
		createEOperation(featureConfigurationEClass, FEATURE_CONFIGURATION___TO_STRING);

		featureToBooleanMapEClass = createEClass(FEATURE_TO_BOOLEAN_MAP);
		createEReference(featureToBooleanMapEClass, FEATURE_TO_BOOLEAN_MAP__KEY);
		createEAttribute(featureToBooleanMapEClass, FEATURE_TO_BOOLEAN_MAP__VALUE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		FeatureDiagramPackage theFeatureDiagramPackage = (FeatureDiagramPackage)EPackage.Registry.INSTANCE.getEPackage(FeatureDiagramPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(featureConfigurationEClass, FeatureConfiguration.class, "FeatureConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFeatureConfiguration_Name(), ecorePackage.getEString(), "name", null, 0, 1, FeatureConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFeatureConfiguration_FeatureSelection(), this.getFeatureToBooleanMap(), null, "featureSelection", null, 0, -1, FeatureConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFeatureConfiguration_FeatureDiagram(), theFeatureDiagramPackage.getFeatureDiagramm(), null, "featureDiagram", null, 0, 1, FeatureConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getFeatureConfiguration__ToString(), ecorePackage.getEString(), "toString", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(featureToBooleanMapEClass, Map.Entry.class, "FeatureToBooleanMap", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFeatureToBooleanMap_Key(), theFeatureDiagramPackage.getFeature(), null, "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeatureToBooleanMap_Value(), ecorePackage.getEBooleanObject(), "value", "false", 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //FeatureConfigurationPackageImpl

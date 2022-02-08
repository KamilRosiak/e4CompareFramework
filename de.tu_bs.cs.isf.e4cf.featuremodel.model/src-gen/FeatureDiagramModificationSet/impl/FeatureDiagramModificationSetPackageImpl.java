/**
 */
package FeatureDiagramModificationSet.impl;

import CrossTreeConstraints.CrossTreeConstraintsPackage;

import FeatureDiagram.FeatureDiagramPackage;

import FeatureDiagramModificationSet.Delta;
import FeatureDiagramModificationSet.DeltaProperties;
import FeatureDiagramModificationSet.FeatureDiagramModificationSetFactory;
import FeatureDiagramModificationSet.FeatureDiagramModificationSetPackage;
import FeatureDiagramModificationSet.FeatureModelModificationSet;
import FeatureDiagramModificationSet.Modification;
import featureConfiguration.FeatureConfigurationPackage;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class FeatureDiagramModificationSetPackageImpl extends EPackageImpl implements FeatureDiagramModificationSetPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass featureModelModificationSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deltaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum deltaPropertiesEEnum = null;

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
	 * @see FeatureDiagramModificationSet.FeatureDiagramModificationSetPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private FeatureDiagramModificationSetPackageImpl() {
		super(eNS_URI, FeatureDiagramModificationSetFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link FeatureDiagramModificationSetPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static FeatureDiagramModificationSetPackage init() {
		if (isInited) return (FeatureDiagramModificationSetPackage)EPackage.Registry.INSTANCE.getEPackage(FeatureDiagramModificationSetPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredFeatureDiagramModificationSetPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		FeatureDiagramModificationSetPackageImpl theFeatureDiagramModificationSetPackage = registeredFeatureDiagramModificationSetPackage instanceof FeatureDiagramModificationSetPackageImpl ? (FeatureDiagramModificationSetPackageImpl)registeredFeatureDiagramModificationSetPackage : new FeatureDiagramModificationSetPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		FeatureDiagramPackage.eINSTANCE.eClass();
		CrossTreeConstraintsPackage.eINSTANCE.eClass();
		FeatureConfigurationPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theFeatureDiagramModificationSetPackage.createPackageContents();

		// Initialize created meta-data
		theFeatureDiagramModificationSetPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theFeatureDiagramModificationSetPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(FeatureDiagramModificationSetPackage.eNS_URI, theFeatureDiagramModificationSetPackage);
		return theFeatureDiagramModificationSetPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFeatureModelModificationSet() {
		return featureModelModificationSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFeatureModelModificationSet_Modifications() {
		return (EReference)featureModelModificationSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureModelModificationSet_AffectedFeatureModelName() {
		return (EAttribute)featureModelModificationSetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getModification() {
		return modificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getModification_FeatureID() {
		return (EAttribute)modificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getModification_TimeStamp() {
		return (EAttribute)modificationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getModification_Delta() {
		return (EReference)modificationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getModification_ModificationType() {
		return (EAttribute)modificationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getModification_PrecisionTime() {
		return (EAttribute)modificationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getModification_ReferencedArtifacts() {
		return (EReference)modificationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDelta() {
		return deltaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDelta_Property() {
		return (EAttribute)deltaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDelta_ValuePriorChange() {
		return (EAttribute)deltaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDelta_ValueAfterChange() {
		return (EAttribute)deltaEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getDeltaProperties() {
		return deltaPropertiesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureDiagramModificationSetFactory getFeatureDiagramModificationSetFactory() {
		return (FeatureDiagramModificationSetFactory)getEFactoryInstance();
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
		featureModelModificationSetEClass = createEClass(FEATURE_MODEL_MODIFICATION_SET);
		createEReference(featureModelModificationSetEClass, FEATURE_MODEL_MODIFICATION_SET__MODIFICATIONS);
		createEAttribute(featureModelModificationSetEClass, FEATURE_MODEL_MODIFICATION_SET__AFFECTED_FEATURE_MODEL_NAME);

		modificationEClass = createEClass(MODIFICATION);
		createEAttribute(modificationEClass, MODIFICATION__FEATURE_ID);
		createEAttribute(modificationEClass, MODIFICATION__TIME_STAMP);
		createEReference(modificationEClass, MODIFICATION__DELTA);
		createEAttribute(modificationEClass, MODIFICATION__MODIFICATION_TYPE);
		createEAttribute(modificationEClass, MODIFICATION__PRECISION_TIME);
		createEReference(modificationEClass, MODIFICATION__REFERENCED_ARTIFACTS);

		deltaEClass = createEClass(DELTA);
		createEAttribute(deltaEClass, DELTA__PROPERTY);
		createEAttribute(deltaEClass, DELTA__VALUE_PRIOR_CHANGE);
		createEAttribute(deltaEClass, DELTA__VALUE_AFTER_CHANGE);

		// Create enums
		deltaPropertiesEEnum = createEEnum(DELTA_PROPERTIES);
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
		initEClass(featureModelModificationSetEClass, FeatureModelModificationSet.class, "FeatureModelModificationSet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFeatureModelModificationSet_Modifications(), this.getModification(), null, "modifications", null, 0, -1, FeatureModelModificationSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeatureModelModificationSet_AffectedFeatureModelName(), ecorePackage.getEString(), "affectedFeatureModelName", null, 1, 1, FeatureModelModificationSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(modificationEClass, Modification.class, "Modification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getModification_FeatureID(), ecorePackage.getEInt(), "featureID", "-1", 1, 1, Modification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModification_TimeStamp(), ecorePackage.getELong(), "timeStamp", null, 1, 1, Modification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModification_Delta(), this.getDelta(), null, "delta", null, 0, 1, Modification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModification_ModificationType(), ecorePackage.getEString(), "modificationType", null, 0, 1, Modification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModification_PrecisionTime(), ecorePackage.getELong(), "precisionTime", null, 0, 1, Modification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModification_ReferencedArtifacts(), theFeatureDiagramPackage.getArtifactReference(), null, "referencedArtifacts", null, 0, -1, Modification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(deltaEClass, Delta.class, "Delta", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDelta_Property(), this.getDeltaProperties(), "property", null, 1, 1, Delta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDelta_ValuePriorChange(), ecorePackage.getEString(), "valuePriorChange", null, 1, 1, Delta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDelta_ValueAfterChange(), ecorePackage.getEString(), "valueAfterChange", null, 1, 1, Delta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(deltaPropertiesEEnum, DeltaProperties.class, "DeltaProperties");
		addEEnumLiteral(deltaPropertiesEEnum, DeltaProperties.FEATURE_NAME);
		addEEnumLiteral(deltaPropertiesEEnum, DeltaProperties.FEATURE_ABSTRACTION);
		addEEnumLiteral(deltaPropertiesEEnum, DeltaProperties.FEATURE_VISIBILITY);
		addEEnumLiteral(deltaPropertiesEEnum, DeltaProperties.FEATURE_VARIABILITY);
		addEEnumLiteral(deltaPropertiesEEnum, DeltaProperties.FEATURE_GROUP_VARIABILITY);
		addEEnumLiteral(deltaPropertiesEEnum, DeltaProperties.LINE_TO_PARENT_RESET);

		// Create resource
		createResource(eNS_URI);
	}

} //FeatureDiagramModificationSetPackageImpl

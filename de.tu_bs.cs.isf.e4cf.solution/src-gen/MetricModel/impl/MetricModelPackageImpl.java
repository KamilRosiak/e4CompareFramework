/**
 */
package MetricModel.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import MetricModel.AbstractAttribute;
import MetricModel.AbstractMetric;
import MetricModel.AbstractMetricElement;
import MetricModel.AbstractOption;
import MetricModel.MetricModelFactory;
import MetricModel.MetricModelPackage;
import SolutionModel.SolutionModelPackage;
import SolutionModel.impl.SolutionModelPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MetricModelPackageImpl extends EPackageImpl implements MetricModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractOptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractMetricElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractMetricEClass = null;

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
	 * @see MetricModel.MetricModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private MetricModelPackageImpl() {
		super(eNS_URI, MetricModelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link MetricModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static MetricModelPackage init() {
		if (isInited) return (MetricModelPackage)EPackage.Registry.INSTANCE.getEPackage(MetricModelPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredMetricModelPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		MetricModelPackageImpl theMetricModelPackage = registeredMetricModelPackage instanceof MetricModelPackageImpl ? (MetricModelPackageImpl)registeredMetricModelPackage : new MetricModelPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(SolutionModelPackage.eNS_URI);
		SolutionModelPackageImpl theSolutionModelPackage = (SolutionModelPackageImpl)(registeredPackage instanceof SolutionModelPackageImpl ? registeredPackage : SolutionModelPackage.eINSTANCE);

		// Create package meta-data objects
		theMetricModelPackage.createPackageContents();
		theSolutionModelPackage.createPackageContents();

		// Initialize created meta-data
		theMetricModelPackage.initializePackageContents();
		theSolutionModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMetricModelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(MetricModelPackage.eNS_URI, theMetricModelPackage);
		return theMetricModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbstractOption() {
		return abstractOptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractOption_Attributes() {
		return (EReference)abstractOptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractOption_Options() {
		return (EReference)abstractOptionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbstractAttribute() {
		return abstractAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAbstractAttribute__Compare() {
		return abstractAttributeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbstractMetricElement() {
		return abstractMetricElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbstractMetricElement_IsActive() {
		return (EAttribute)abstractMetricElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbstractMetricElement_Weight() {
		return (EAttribute)abstractMetricElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbstractMetricElement_Name() {
		return (EAttribute)abstractMetricElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbstractMetricElement_Description() {
		return (EAttribute)abstractMetricElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbstractMetric() {
		return abstractMetricEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractMetric_Root() {
		return (EReference)abstractMetricEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbstractMetric_IsWeighted() {
		return (EAttribute)abstractMetricEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MetricModelFactory getMetricModelFactory() {
		return (MetricModelFactory)getEFactoryInstance();
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
		abstractOptionEClass = createEClass(ABSTRACT_OPTION);
		createEReference(abstractOptionEClass, ABSTRACT_OPTION__ATTRIBUTES);
		createEReference(abstractOptionEClass, ABSTRACT_OPTION__OPTIONS);

		abstractAttributeEClass = createEClass(ABSTRACT_ATTRIBUTE);
		createEOperation(abstractAttributeEClass, ABSTRACT_ATTRIBUTE___COMPARE);

		abstractMetricElementEClass = createEClass(ABSTRACT_METRIC_ELEMENT);
		createEAttribute(abstractMetricElementEClass, ABSTRACT_METRIC_ELEMENT__IS_ACTIVE);
		createEAttribute(abstractMetricElementEClass, ABSTRACT_METRIC_ELEMENT__WEIGHT);
		createEAttribute(abstractMetricElementEClass, ABSTRACT_METRIC_ELEMENT__NAME);
		createEAttribute(abstractMetricElementEClass, ABSTRACT_METRIC_ELEMENT__DESCRIPTION);

		abstractMetricEClass = createEClass(ABSTRACT_METRIC);
		createEReference(abstractMetricEClass, ABSTRACT_METRIC__ROOT);
		createEAttribute(abstractMetricEClass, ABSTRACT_METRIC__IS_WEIGHTED);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		abstractOptionEClass.getESuperTypes().add(this.getAbstractMetricElement());
		abstractAttributeEClass.getESuperTypes().add(this.getAbstractMetricElement());

		// Initialize classes, features, and operations; add parameters
		initEClass(abstractOptionEClass, AbstractOption.class, "AbstractOption", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAbstractOption_Attributes(), this.getAbstractAttribute(), null, "attributes", null, 0, -1, AbstractOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractOption_Options(), this.getAbstractOption(), null, "options", null, 0, -1, AbstractOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abstractAttributeEClass, AbstractAttribute.class, "AbstractAttribute", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getAbstractAttribute__Compare(), null, "compare", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(abstractMetricElementEClass, AbstractMetricElement.class, "AbstractMetricElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractMetricElement_IsActive(), ecorePackage.getEBooleanObject(), "isActive", "FALSE", 1, 1, AbstractMetricElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractMetricElement_Weight(), ecorePackage.getEFloat(), "weight", "0.0", 1, 1, AbstractMetricElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractMetricElement_Name(), ecorePackage.getEString(), "name", null, 1, 1, AbstractMetricElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractMetricElement_Description(), ecorePackage.getEString(), "description", null, 1, 1, AbstractMetricElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abstractMetricEClass, AbstractMetric.class, "AbstractMetric", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAbstractMetric_Root(), this.getAbstractOption(), null, "root", null, 1, 1, AbstractMetric.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractMetric_IsWeighted(), ecorePackage.getEBooleanObject(), "isWeighted", null, 1, 1, AbstractMetric.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //MetricModelPackageImpl

/**
 */
package SolutionModel.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import MetricModel.MetricModelPackage;
import MetricModel.impl.MetricModelPackageImpl;
import SolutionModel.AbstractContainer;
import SolutionModel.AbstractOption;
import SolutionModel.AbstractResult;
import SolutionModel.AbstractSolutionElement;
import SolutionModel.IFamilyModelAdapter;
import SolutionModel.IUpdate;
import SolutionModel.SolutionModelFactory;
import SolutionModel.SolutionModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SolutionModelPackageImpl extends EPackageImpl implements SolutionModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractSolutionElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractContainerEClass = null;

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
	private EClass abstractResultEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iUpdateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iFamilyModelAdapterEClass = null;

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
	 * @see SolutionModel.SolutionModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SolutionModelPackageImpl() {
		super(eNS_URI, SolutionModelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link SolutionModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SolutionModelPackage init() {
		if (isInited) return (SolutionModelPackage)EPackage.Registry.INSTANCE.getEPackage(SolutionModelPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredSolutionModelPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		SolutionModelPackageImpl theSolutionModelPackage = registeredSolutionModelPackage instanceof SolutionModelPackageImpl ? (SolutionModelPackageImpl)registeredSolutionModelPackage : new SolutionModelPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(MetricModelPackage.eNS_URI);
		MetricModelPackageImpl theMetricModelPackage = (MetricModelPackageImpl)(registeredPackage instanceof MetricModelPackageImpl ? registeredPackage : MetricModelPackage.eINSTANCE);

		// Create package meta-data objects
		theSolutionModelPackage.createPackageContents();
		theMetricModelPackage.createPackageContents();

		// Initialize created meta-data
		theSolutionModelPackage.initializePackageContents();
		theMetricModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSolutionModelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SolutionModelPackage.eNS_URI, theSolutionModelPackage);
		return theSolutionModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbstractSolutionElement() {
		return abstractSolutionElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbstractSolutionElement_Similarity() {
		return (EAttribute)abstractSolutionElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbstractSolutionElement_LeftElement() {
		return (EAttribute)abstractSolutionElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbstractSolutionElement_RightElement() {
		return (EAttribute)abstractSolutionElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAbstractSolutionElement__ResetElement() {
		return abstractSolutionElementEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbstractContainer() {
		return abstractContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractContainer_Abstractoption() {
		return (EReference)abstractContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractContainer_Abstractresult() {
		return (EReference)abstractContainerEClass.getEStructuralFeatures().get(1);
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
	public EReference getAbstractOption_Abstractcontainer() {
		return (EReference)abstractOptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbstractResult() {
		return abstractResultEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractResult_Attribute() {
		return (EReference)abstractResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIUpdate() {
		return iUpdateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getIUpdate__UpdateSimilarity() {
		return iUpdateEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIFamilyModelAdapter() {
		return iFamilyModelAdapterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getIFamilyModelAdapter__GetLeftLabel() {
		return iFamilyModelAdapterEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getIFamilyModelAdapter__GetRightLabel() {
		return iFamilyModelAdapterEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SolutionModelFactory getSolutionModelFactory() {
		return (SolutionModelFactory)getEFactoryInstance();
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
		abstractSolutionElementEClass = createEClass(ABSTRACT_SOLUTION_ELEMENT);
		createEAttribute(abstractSolutionElementEClass, ABSTRACT_SOLUTION_ELEMENT__SIMILARITY);
		createEAttribute(abstractSolutionElementEClass, ABSTRACT_SOLUTION_ELEMENT__LEFT_ELEMENT);
		createEAttribute(abstractSolutionElementEClass, ABSTRACT_SOLUTION_ELEMENT__RIGHT_ELEMENT);
		createEOperation(abstractSolutionElementEClass, ABSTRACT_SOLUTION_ELEMENT___RESET_ELEMENT);

		abstractContainerEClass = createEClass(ABSTRACT_CONTAINER);
		createEReference(abstractContainerEClass, ABSTRACT_CONTAINER__ABSTRACTOPTION);
		createEReference(abstractContainerEClass, ABSTRACT_CONTAINER__ABSTRACTRESULT);

		abstractOptionEClass = createEClass(ABSTRACT_OPTION);
		createEReference(abstractOptionEClass, ABSTRACT_OPTION__ABSTRACTCONTAINER);

		abstractResultEClass = createEClass(ABSTRACT_RESULT);
		createEReference(abstractResultEClass, ABSTRACT_RESULT__ATTRIBUTE);

		iUpdateEClass = createEClass(IUPDATE);
		createEOperation(iUpdateEClass, IUPDATE___UPDATE_SIMILARITY);

		iFamilyModelAdapterEClass = createEClass(IFAMILY_MODEL_ADAPTER);
		createEOperation(iFamilyModelAdapterEClass, IFAMILY_MODEL_ADAPTER___GET_LEFT_LABEL);
		createEOperation(iFamilyModelAdapterEClass, IFAMILY_MODEL_ADAPTER___GET_RIGHT_LABEL);
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
		MetricModelPackage theMetricModelPackage = (MetricModelPackage)EPackage.Registry.INSTANCE.getEPackage(MetricModelPackage.eNS_URI);

		// Create type parameters
		ETypeParameter abstractSolutionElementEClass_T = addETypeParameter(abstractSolutionElementEClass, "T");
		ETypeParameter abstractContainerEClass_T = addETypeParameter(abstractContainerEClass, "T");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(ecorePackage.getEObject());
		abstractSolutionElementEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEObject());
		abstractContainerEClass_T.getEBounds().add(g1);

		// Add supertypes to classes
		abstractSolutionElementEClass.getESuperTypes().add(this.getIUpdate());
		abstractSolutionElementEClass.getESuperTypes().add(this.getIFamilyModelAdapter());
		abstractContainerEClass.getESuperTypes().add(this.getAbstractSolutionElement());
		abstractOptionEClass.getESuperTypes().add(this.getAbstractSolutionElement());

		// Initialize classes, features, and operations; add parameters
		initEClass(abstractSolutionElementEClass, AbstractSolutionElement.class, "AbstractSolutionElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractSolutionElement_Similarity(), ecorePackage.getEFloat(), "Similarity", "0.0", 1, 1, AbstractSolutionElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(abstractSolutionElementEClass_T);
		initEAttribute(getAbstractSolutionElement_LeftElement(), g1, "leftElement", null, 0, 1, AbstractSolutionElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(abstractSolutionElementEClass_T);
		initEAttribute(getAbstractSolutionElement_RightElement(), g1, "rightElement", null, 0, 1, AbstractSolutionElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getAbstractSolutionElement__ResetElement(), null, "resetElement", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(abstractContainerEClass, AbstractContainer.class, "AbstractContainer", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAbstractContainer_Abstractoption(), this.getAbstractOption(), null, "abstractoption", null, 0, -1, AbstractContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractContainer_Abstractresult(), this.getAbstractResult(), null, "abstractresult", null, 0, -1, AbstractContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abstractOptionEClass, AbstractOption.class, "AbstractOption", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAbstractOption_Abstractcontainer(), this.getAbstractContainer(), null, "abstractcontainer", null, 0, -1, AbstractOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abstractResultEClass, AbstractResult.class, "AbstractResult", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAbstractResult_Attribute(), theMetricModelPackage.getAbstractAttribute(), null, "attribute", null, 0, 1, AbstractResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iUpdateEClass, IUpdate.class, "IUpdate", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getIUpdate__UpdateSimilarity(), ecorePackage.getEFloatObject(), "updateSimilarity", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(iFamilyModelAdapterEClass, IFamilyModelAdapter.class, "IFamilyModelAdapter", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getIFamilyModelAdapter__GetLeftLabel(), ecorePackage.getEString(), "getLeftLabel", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEOperation(getIFamilyModelAdapter__GetRightLabel(), ecorePackage.getEString(), "getRightLabel", 1, 1, IS_UNIQUE, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //SolutionModelPackageImpl

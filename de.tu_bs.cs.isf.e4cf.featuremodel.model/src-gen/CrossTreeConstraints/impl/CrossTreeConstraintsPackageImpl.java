/**
 */
package CrossTreeConstraints.impl;

import CrossTreeConstraints.AbstractConstraint;
import CrossTreeConstraints.CrossTreeConstraintsFactory;
import CrossTreeConstraints.CrossTreeConstraintsPackage;
import CrossTreeConstraints.Formula;
import CrossTreeConstraints.Literal;
import CrossTreeConstraints.Operator;

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
public class CrossTreeConstraintsPackageImpl extends EPackageImpl implements CrossTreeConstraintsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass formulaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass literalEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum operatorEEnum = null;

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
	 * @see CrossTreeConstraints.CrossTreeConstraintsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CrossTreeConstraintsPackageImpl() {
		super(eNS_URI, CrossTreeConstraintsFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link CrossTreeConstraintsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CrossTreeConstraintsPackage init() {
		if (isInited) return (CrossTreeConstraintsPackage)EPackage.Registry.INSTANCE.getEPackage(CrossTreeConstraintsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredCrossTreeConstraintsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		CrossTreeConstraintsPackageImpl theCrossTreeConstraintsPackage = registeredCrossTreeConstraintsPackage instanceof CrossTreeConstraintsPackageImpl ? (CrossTreeConstraintsPackageImpl)registeredCrossTreeConstraintsPackage : new CrossTreeConstraintsPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theCrossTreeConstraintsPackage.createPackageContents();

		// Initialize created meta-data
		theCrossTreeConstraintsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCrossTreeConstraintsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CrossTreeConstraintsPackage.eNS_URI, theCrossTreeConstraintsPackage);
		return theCrossTreeConstraintsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFormula() {
		return formulaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFormula_Operator() {
		return (EAttribute)formulaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFormula_Formula() {
		return (EReference)formulaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLiteral() {
		return literalEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLiteral_Name() {
		return (EAttribute)literalEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbstractConstraint() {
		return abstractConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbstractConstraint_IsNegated() {
		return (EAttribute)abstractConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbstractConstraint_Description() {
		return (EAttribute)abstractConstraintEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getOperator() {
		return operatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CrossTreeConstraintsFactory getCrossTreeConstraintsFactory() {
		return (CrossTreeConstraintsFactory)getEFactoryInstance();
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
		formulaEClass = createEClass(FORMULA);
		createEAttribute(formulaEClass, FORMULA__OPERATOR);
		createEReference(formulaEClass, FORMULA__FORMULA);

		literalEClass = createEClass(LITERAL);
		createEAttribute(literalEClass, LITERAL__NAME);

		abstractConstraintEClass = createEClass(ABSTRACT_CONSTRAINT);
		createEAttribute(abstractConstraintEClass, ABSTRACT_CONSTRAINT__IS_NEGATED);
		createEAttribute(abstractConstraintEClass, ABSTRACT_CONSTRAINT__DESCRIPTION);

		// Create enums
		operatorEEnum = createEEnum(OPERATOR);
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
		formulaEClass.getESuperTypes().add(this.getAbstractConstraint());
		literalEClass.getESuperTypes().add(this.getAbstractConstraint());

		// Initialize classes, features, and operations; add parameters
		initEClass(formulaEClass, Formula.class, "Formula", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFormula_Operator(), this.getOperator(), "operator", "NONE", 0, 1, Formula.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFormula_Formula(), this.getAbstractConstraint(), null, "formula", null, 0, -1, Formula.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(literalEClass, Literal.class, "Literal", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLiteral_Name(), ecorePackage.getEString(), "name", "", 0, 1, Literal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abstractConstraintEClass, AbstractConstraint.class, "AbstractConstraint", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractConstraint_IsNegated(), ecorePackage.getEBooleanObject(), "isNegated", "false", 1, 1, AbstractConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractConstraint_Description(), ecorePackage.getEString(), "description", "\"\"", 0, 1, AbstractConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(operatorEEnum, Operator.class, "Operator");
		addEEnumLiteral(operatorEEnum, Operator.AND);
		addEEnumLiteral(operatorEEnum, Operator.OR);
		addEEnumLiteral(operatorEEnum, Operator.IMPLIES);
		addEEnumLiteral(operatorEEnum, Operator.EQUALS);
		addEEnumLiteral(operatorEEnum, Operator.NONE);

		// Create resource
		createResource(eNS_URI);
	}

} //CrossTreeConstraintsPackageImpl

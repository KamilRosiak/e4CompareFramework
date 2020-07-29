/**
 */
package familyModel.impl;

import familyModel.Element;
import familyModel.FamilyModel;
import familyModel.FamilyModelFactory;
import familyModel.FamilyModelPackage;
import familyModel.VariabilityCategory;
import familyModel.VariabilityGroup;

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
public class FamilyModelPackageImpl extends EPackageImpl implements FamilyModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass familyModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variabilityGroupEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass elementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum variabilityCategoryEEnum = null;

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
	 * @see familyModel.FamilyModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private FamilyModelPackageImpl() {
		super(eNS_URI, FamilyModelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link FamilyModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static FamilyModelPackage init() {
		if (isInited) return (FamilyModelPackage)EPackage.Registry.INSTANCE.getEPackage(FamilyModelPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredFamilyModelPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		FamilyModelPackageImpl theFamilyModelPackage = registeredFamilyModelPackage instanceof FamilyModelPackageImpl ? (FamilyModelPackageImpl)registeredFamilyModelPackage : new FamilyModelPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theFamilyModelPackage.createPackageContents();

		// Initialize created meta-data
		theFamilyModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theFamilyModelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(FamilyModelPackage.eNS_URI, theFamilyModelPackage);
		return theFamilyModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFamilyModel() {
		return familyModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFamilyModel_Models() {
		return (EAttribute)familyModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFamilyModel_VariabilyGroups() {
		return (EReference)familyModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFamilyModel_FaMoName() {
		return (EAttribute)familyModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVariabilityGroup() {
		return variabilityGroupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVariabilityGroup_Variability() {
		return (EAttribute)variabilityGroupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVariabilityGroup_SubGroups() {
		return (EReference)variabilityGroupEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVariabilityGroup_Elements() {
		return (EReference)variabilityGroupEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVariabilityGroup_GroupName() {
		return (EAttribute)variabilityGroupEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getElement() {
		return elementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getElement_Element() {
		return (EReference)elementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getElement_Origins() {
		return (EAttribute)elementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getVariabilityCategory() {
		return variabilityCategoryEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FamilyModelFactory getFamilyModelFactory() {
		return (FamilyModelFactory)getEFactoryInstance();
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
		familyModelEClass = createEClass(FAMILY_MODEL);
		createEAttribute(familyModelEClass, FAMILY_MODEL__MODELS);
		createEReference(familyModelEClass, FAMILY_MODEL__VARIABILY_GROUPS);
		createEAttribute(familyModelEClass, FAMILY_MODEL__FA_MO_NAME);

		variabilityGroupEClass = createEClass(VARIABILITY_GROUP);
		createEAttribute(variabilityGroupEClass, VARIABILITY_GROUP__VARIABILITY);
		createEReference(variabilityGroupEClass, VARIABILITY_GROUP__SUB_GROUPS);
		createEReference(variabilityGroupEClass, VARIABILITY_GROUP__ELEMENTS);
		createEAttribute(variabilityGroupEClass, VARIABILITY_GROUP__GROUP_NAME);

		elementEClass = createEClass(ELEMENT);
		createEReference(elementEClass, ELEMENT__ELEMENT);
		createEAttribute(elementEClass, ELEMENT__ORIGINS);

		// Create enums
		variabilityCategoryEEnum = createEEnum(VARIABILITY_CATEGORY);
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

		// Initialize classes, features, and operations; add parameters
		initEClass(familyModelEClass, FamilyModel.class, "FamilyModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFamilyModel_Models(), ecorePackage.getEString(), "models", null, 0, -1, FamilyModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFamilyModel_VariabilyGroups(), this.getVariabilityGroup(), null, "variabilyGroups", null, 0, -1, FamilyModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFamilyModel_FaMoName(), ecorePackage.getEString(), "faMoName", "familModel", 1, 1, FamilyModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(variabilityGroupEClass, VariabilityGroup.class, "VariabilityGroup", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVariabilityGroup_Variability(), this.getVariabilityCategory(), "variability", null, 0, 1, VariabilityGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVariabilityGroup_SubGroups(), this.getVariabilityGroup(), null, "subGroups", null, 0, -1, VariabilityGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVariabilityGroup_Elements(), this.getElement(), null, "elements", null, 0, -1, VariabilityGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVariabilityGroup_GroupName(), ecorePackage.getEString(), "groupName", "\"\"", 1, 1, VariabilityGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(elementEClass, Element.class, "Element", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getElement_Element(), ecorePackage.getEObject(), null, "element", null, 1, 1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getElement_Origins(), ecorePackage.getEString(), "origins", null, 1, -1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(variabilityCategoryEEnum, VariabilityCategory.class, "VariabilityCategory");
		addEEnumLiteral(variabilityCategoryEEnum, VariabilityCategory.UNDEFINED);
		addEEnumLiteral(variabilityCategoryEEnum, VariabilityCategory.OPTIONAL);
		addEEnumLiteral(variabilityCategoryEEnum, VariabilityCategory.ALTERNATIVE);
		addEEnumLiteral(variabilityCategoryEEnum, VariabilityCategory.MANDATORY);

		// Create resource
		createResource(eNS_URI);
	}

} //FamilyModelPackageImpl

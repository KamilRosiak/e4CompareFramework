/**
 */
package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelFactory;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelPackage;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariabilityCategory;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariantArtefact;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint;

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
	private EClass variantEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variationPointEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variantArtefactEClass = null;

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
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelPackage#eNS_URI
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
	public EAttribute getFamilyModel_Name() {
		return (EAttribute)familyModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFamilyModel_VariationPoints() {
		return (EReference)familyModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFamilyModel_Variants() {
		return (EReference)familyModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVariant() {
		return variantEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVariant_Instance() {
		return (EReference)variantEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVariant_Identifier() {
		return (EAttribute)variantEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVariationPoint() {
		return variationPointEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVariationPoint_VariantArtefacts() {
		return (EReference)variationPointEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVariationPoint_Children() {
		return (EReference)variationPointEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVariationPoint_Parent() {
		return (EReference)variationPointEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVariationPoint_VariabilityCategory() {
		return (EAttribute)variationPointEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVariationPoint_Name() {
		return (EAttribute)variationPointEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVariantArtefact() {
		return variantArtefactEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVariantArtefact_Artefacts() {
		return (EReference)variantArtefactEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVariantArtefact_Name() {
		return (EAttribute)variantArtefactEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVariantArtefact_Origins() {
		return (EReference)variantArtefactEClass.getEStructuralFeatures().get(2);
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
		createEAttribute(familyModelEClass, FAMILY_MODEL__NAME);
		createEReference(familyModelEClass, FAMILY_MODEL__VARIATION_POINTS);
		createEReference(familyModelEClass, FAMILY_MODEL__VARIANTS);

		variantEClass = createEClass(VARIANT);
		createEReference(variantEClass, VARIANT__INSTANCE);
		createEAttribute(variantEClass, VARIANT__IDENTIFIER);

		variationPointEClass = createEClass(VARIATION_POINT);
		createEReference(variationPointEClass, VARIATION_POINT__VARIANT_ARTEFACTS);
		createEReference(variationPointEClass, VARIATION_POINT__CHILDREN);
		createEReference(variationPointEClass, VARIATION_POINT__PARENT);
		createEAttribute(variationPointEClass, VARIATION_POINT__VARIABILITY_CATEGORY);
		createEAttribute(variationPointEClass, VARIATION_POINT__NAME);

		variantArtefactEClass = createEClass(VARIANT_ARTEFACT);
		createEReference(variantArtefactEClass, VARIANT_ARTEFACT__ARTEFACTS);
		createEAttribute(variantArtefactEClass, VARIANT_ARTEFACT__NAME);
		createEReference(variantArtefactEClass, VARIANT_ARTEFACT__ORIGINS);

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
		initEAttribute(getFamilyModel_Name(), ecorePackage.getEString(), "name", null, 0, 1, FamilyModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFamilyModel_VariationPoints(), this.getVariationPoint(), null, "variationPoints", null, 0, -1, FamilyModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFamilyModel_Variants(), this.getVariant(), null, "variants", null, 0, -1, FamilyModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(variantEClass, Variant.class, "Variant", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVariant_Instance(), ecorePackage.getEObject(), null, "instance", null, 0, 1, Variant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVariant_Identifier(), ecorePackage.getEString(), "identifier", null, 0, 1, Variant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(variationPointEClass, VariationPoint.class, "VariationPoint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVariationPoint_VariantArtefacts(), this.getVariantArtefact(), null, "variantArtefacts", null, 1, -1, VariationPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVariationPoint_Children(), this.getVariationPoint(), null, "children", null, 0, -1, VariationPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVariationPoint_Parent(), this.getVariationPoint(), null, "parent", null, 0, 1, VariationPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVariationPoint_VariabilityCategory(), this.getVariabilityCategory(), "variabilityCategory", "UNSET", 0, 1, VariationPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVariationPoint_Name(), ecorePackage.getEString(), "name", null, 0, 1, VariationPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(variantArtefactEClass, VariantArtefact.class, "VariantArtefact", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVariantArtefact_Artefacts(), ecorePackage.getEObject(), null, "artefacts", null, 1, -1, VariantArtefact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVariantArtefact_Name(), ecorePackage.getEString(), "name", null, 0, 1, VariantArtefact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVariantArtefact_Origins(), this.getVariant(), null, "origins", null, 0, -1, VariantArtefact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(variabilityCategoryEEnum, VariabilityCategory.class, "VariabilityCategory");
		addEEnumLiteral(variabilityCategoryEEnum, VariabilityCategory.UNSET);
		addEEnumLiteral(variabilityCategoryEEnum, VariabilityCategory.MANDATORY);
		addEEnumLiteral(variabilityCategoryEEnum, VariabilityCategory.ALTERNATIVE);
		addEEnumLiteral(variabilityCategoryEEnum, VariabilityCategory.OPTIONAL);

		// Create resource
		createResource(eNS_URI);
	}

} //FamilyModelPackageImpl

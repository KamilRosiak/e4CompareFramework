/**
 */
package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelFactory
 * @model kind="package"
 * @generated
 */
public interface FamilyModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "FamilyModel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "www.example.com/family_model_prototype";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "fam";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FamilyModelPackage eINSTANCE = de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.FamilyModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.FamilyModelImpl <em>Family Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.FamilyModelImpl
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.FamilyModelPackageImpl#getFamilyModel()
	 * @generated
	 */
	int FAMILY_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAMILY_MODEL__NAME = 0;

	/**
	 * The feature id for the '<em><b>Variation Points</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAMILY_MODEL__VARIATION_POINTS = 1;

	/**
	 * The feature id for the '<em><b>Variants</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAMILY_MODEL__VARIANTS = 2;

	/**
	 * The number of structural features of the '<em>Family Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAMILY_MODEL_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Family Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAMILY_MODEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.VariantImpl <em>Variant</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.VariantImpl
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.FamilyModelPackageImpl#getVariant()
	 * @generated
	 */
	int VARIANT = 1;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIANT__INSTANCE = 0;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIANT__IDENTIFIER = 1;

	/**
	 * The number of structural features of the '<em>Variant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIANT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Variant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIANT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.VariationPointImpl <em>Variation Point</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.VariationPointImpl
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.FamilyModelPackageImpl#getVariationPoint()
	 * @generated
	 */
	int VARIATION_POINT = 2;

	/**
	 * The feature id for the '<em><b>Variant Artefacts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIATION_POINT__VARIANT_ARTEFACTS = 0;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIATION_POINT__CHILDREN = 1;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIATION_POINT__PARENT = 2;

	/**
	 * The feature id for the '<em><b>Variability Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIATION_POINT__VARIABILITY_CATEGORY = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIATION_POINT__NAME = 4;

	/**
	 * The number of structural features of the '<em>Variation Point</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIATION_POINT_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Variation Point</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIATION_POINT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.VariantArtefactImpl <em>Variant Artefact</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.VariantArtefactImpl
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.FamilyModelPackageImpl#getVariantArtefact()
	 * @generated
	 */
	int VARIANT_ARTEFACT = 3;

	/**
	 * The feature id for the '<em><b>Artefacts</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIANT_ARTEFACT__ARTEFACTS = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIANT_ARTEFACT__NAME = 1;

	/**
	 * The feature id for the '<em><b>Origins</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIANT_ARTEFACT__ORIGINS = 2;

	/**
	 * The number of structural features of the '<em>Variant Artefact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIANT_ARTEFACT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Variant Artefact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIANT_ARTEFACT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariabilityCategory <em>Variability Category</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariabilityCategory
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.FamilyModelPackageImpl#getVariabilityCategory()
	 * @generated
	 */
	int VARIABILITY_CATEGORY = 4;


	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel <em>Family Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Family Model</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel
	 * @generated
	 */
	EClass getFamilyModel();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel#getName()
	 * @see #getFamilyModel()
	 * @generated
	 */
	EAttribute getFamilyModel_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel#getVariationPoints <em>Variation Points</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variation Points</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel#getVariationPoints()
	 * @see #getFamilyModel()
	 * @generated
	 */
	EReference getFamilyModel_VariationPoints();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel#getVariants <em>Variants</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variants</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel#getVariants()
	 * @see #getFamilyModel()
	 * @generated
	 */
	EReference getFamilyModel_Variants();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant <em>Variant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variant</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant
	 * @generated
	 */
	EClass getVariant();

	/**
	 * Returns the meta object for the reference '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant#getInstance <em>Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Instance</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant#getInstance()
	 * @see #getVariant()
	 * @generated
	 */
	EReference getVariant_Instance();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant#getIdentifier <em>Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Identifier</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant#getIdentifier()
	 * @see #getVariant()
	 * @generated
	 */
	EAttribute getVariant_Identifier();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint <em>Variation Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variation Point</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint
	 * @generated
	 */
	EClass getVariationPoint();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint#getVariantArtefacts <em>Variant Artefacts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variant Artefacts</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint#getVariantArtefacts()
	 * @see #getVariationPoint()
	 * @generated
	 */
	EReference getVariationPoint_VariantArtefacts();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint#getChildren()
	 * @see #getVariationPoint()
	 * @generated
	 */
	EReference getVariationPoint_Children();

	/**
	 * Returns the meta object for the reference '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint#getParent()
	 * @see #getVariationPoint()
	 * @generated
	 */
	EReference getVariationPoint_Parent();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint#getVariabilityCategory <em>Variability Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Variability Category</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint#getVariabilityCategory()
	 * @see #getVariationPoint()
	 * @generated
	 */
	EAttribute getVariationPoint_VariabilityCategory();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint#getName()
	 * @see #getVariationPoint()
	 * @generated
	 */
	EAttribute getVariationPoint_Name();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariantArtefact <em>Variant Artefact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variant Artefact</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariantArtefact
	 * @generated
	 */
	EClass getVariantArtefact();

	/**
	 * Returns the meta object for the reference list '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariantArtefact#getArtefacts <em>Artefacts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Artefacts</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariantArtefact#getArtefacts()
	 * @see #getVariantArtefact()
	 * @generated
	 */
	EReference getVariantArtefact_Artefacts();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariantArtefact#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariantArtefact#getName()
	 * @see #getVariantArtefact()
	 * @generated
	 */
	EAttribute getVariantArtefact_Name();

	/**
	 * Returns the meta object for the reference list '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariantArtefact#getOrigins <em>Origins</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Origins</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariantArtefact#getOrigins()
	 * @see #getVariantArtefact()
	 * @generated
	 */
	EReference getVariantArtefact_Origins();

	/**
	 * Returns the meta object for enum '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariabilityCategory <em>Variability Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Variability Category</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariabilityCategory
	 * @generated
	 */
	EEnum getVariabilityCategory();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	FamilyModelFactory getFamilyModelFactory();

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
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.FamilyModelImpl <em>Family Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.FamilyModelImpl
		 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.FamilyModelPackageImpl#getFamilyModel()
		 * @generated
		 */
		EClass FAMILY_MODEL = eINSTANCE.getFamilyModel();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FAMILY_MODEL__NAME = eINSTANCE.getFamilyModel_Name();

		/**
		 * The meta object literal for the '<em><b>Variation Points</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FAMILY_MODEL__VARIATION_POINTS = eINSTANCE.getFamilyModel_VariationPoints();

		/**
		 * The meta object literal for the '<em><b>Variants</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FAMILY_MODEL__VARIANTS = eINSTANCE.getFamilyModel_Variants();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.VariantImpl <em>Variant</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.VariantImpl
		 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.FamilyModelPackageImpl#getVariant()
		 * @generated
		 */
		EClass VARIANT = eINSTANCE.getVariant();

		/**
		 * The meta object literal for the '<em><b>Instance</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIANT__INSTANCE = eINSTANCE.getVariant_Instance();

		/**
		 * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIANT__IDENTIFIER = eINSTANCE.getVariant_Identifier();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.VariationPointImpl <em>Variation Point</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.VariationPointImpl
		 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.FamilyModelPackageImpl#getVariationPoint()
		 * @generated
		 */
		EClass VARIATION_POINT = eINSTANCE.getVariationPoint();

		/**
		 * The meta object literal for the '<em><b>Variant Artefacts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIATION_POINT__VARIANT_ARTEFACTS = eINSTANCE.getVariationPoint_VariantArtefacts();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIATION_POINT__CHILDREN = eINSTANCE.getVariationPoint_Children();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIATION_POINT__PARENT = eINSTANCE.getVariationPoint_Parent();

		/**
		 * The meta object literal for the '<em><b>Variability Category</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIATION_POINT__VARIABILITY_CATEGORY = eINSTANCE.getVariationPoint_VariabilityCategory();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIATION_POINT__NAME = eINSTANCE.getVariationPoint_Name();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.VariantArtefactImpl <em>Variant Artefact</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.VariantArtefactImpl
		 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.FamilyModelPackageImpl#getVariantArtefact()
		 * @generated
		 */
		EClass VARIANT_ARTEFACT = eINSTANCE.getVariantArtefact();

		/**
		 * The meta object literal for the '<em><b>Artefacts</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIANT_ARTEFACT__ARTEFACTS = eINSTANCE.getVariantArtefact_Artefacts();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIANT_ARTEFACT__NAME = eINSTANCE.getVariantArtefact_Name();

		/**
		 * The meta object literal for the '<em><b>Origins</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIANT_ARTEFACT__ORIGINS = eINSTANCE.getVariantArtefact_Origins();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariabilityCategory <em>Variability Category</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariabilityCategory
		 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.impl.FamilyModelPackageImpl#getVariabilityCategory()
		 * @generated
		 */
		EEnum VARIABILITY_CATEGORY = eINSTANCE.getVariabilityCategory();

	}

} //FamilyModelPackage

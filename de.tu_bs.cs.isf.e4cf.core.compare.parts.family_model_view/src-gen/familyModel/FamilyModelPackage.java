/**
 */
package familyModel;

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
 * @see familyModel.FamilyModelFactory
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
	String eNAME = "familyModel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.tu-braunschweig.de/isf/familyMining/simulink/familyModel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "familyModel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FamilyModelPackage eINSTANCE = familyModel.impl.FamilyModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link familyModel.impl.FamilyModelImpl <em>Family Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see familyModel.impl.FamilyModelImpl
	 * @see familyModel.impl.FamilyModelPackageImpl#getFamilyModel()
	 * @generated
	 */
	int FAMILY_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Models</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAMILY_MODEL__MODELS = 0;

	/**
	 * The feature id for the '<em><b>Variabily Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAMILY_MODEL__VARIABILY_GROUPS = 1;

	/**
	 * The feature id for the '<em><b>Fa Mo Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAMILY_MODEL__FA_MO_NAME = 2;

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
	 * The meta object id for the '{@link familyModel.impl.VariabilityGroupImpl <em>Variability Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see familyModel.impl.VariabilityGroupImpl
	 * @see familyModel.impl.FamilyModelPackageImpl#getVariabilityGroup()
	 * @generated
	 */
	int VARIABILITY_GROUP = 1;

	/**
	 * The feature id for the '<em><b>Variability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABILITY_GROUP__VARIABILITY = 0;

	/**
	 * The feature id for the '<em><b>Sub Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABILITY_GROUP__SUB_GROUPS = 1;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABILITY_GROUP__ELEMENTS = 2;

	/**
	 * The feature id for the '<em><b>Group Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABILITY_GROUP__GROUP_NAME = 3;

	/**
	 * The number of structural features of the '<em>Variability Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABILITY_GROUP_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Variability Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABILITY_GROUP_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link familyModel.impl.ElementImpl <em>Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see familyModel.impl.ElementImpl
	 * @see familyModel.impl.FamilyModelPackageImpl#getElement()
	 * @generated
	 */
	int ELEMENT = 2;

	/**
	 * The feature id for the '<em><b>Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT__ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Origins</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT__ORIGINS = 1;

	/**
	 * The number of structural features of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link familyModel.VariabilityCategory <em>Variability Category</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see familyModel.VariabilityCategory
	 * @see familyModel.impl.FamilyModelPackageImpl#getVariabilityCategory()
	 * @generated
	 */
	int VARIABILITY_CATEGORY = 3;


	/**
	 * Returns the meta object for class '{@link familyModel.FamilyModel <em>Family Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Family Model</em>'.
	 * @see familyModel.FamilyModel
	 * @generated
	 */
	EClass getFamilyModel();

	/**
	 * Returns the meta object for the attribute list '{@link familyModel.FamilyModel#getModels <em>Models</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Models</em>'.
	 * @see familyModel.FamilyModel#getModels()
	 * @see #getFamilyModel()
	 * @generated
	 */
	EAttribute getFamilyModel_Models();

	/**
	 * Returns the meta object for the containment reference list '{@link familyModel.FamilyModel#getVariabilyGroups <em>Variabily Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variabily Groups</em>'.
	 * @see familyModel.FamilyModel#getVariabilyGroups()
	 * @see #getFamilyModel()
	 * @generated
	 */
	EReference getFamilyModel_VariabilyGroups();

	/**
	 * Returns the meta object for the attribute '{@link familyModel.FamilyModel#getFaMoName <em>Fa Mo Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fa Mo Name</em>'.
	 * @see familyModel.FamilyModel#getFaMoName()
	 * @see #getFamilyModel()
	 * @generated
	 */
	EAttribute getFamilyModel_FaMoName();

	/**
	 * Returns the meta object for class '{@link familyModel.VariabilityGroup <em>Variability Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variability Group</em>'.
	 * @see familyModel.VariabilityGroup
	 * @generated
	 */
	EClass getVariabilityGroup();

	/**
	 * Returns the meta object for the attribute '{@link familyModel.VariabilityGroup#getVariability <em>Variability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Variability</em>'.
	 * @see familyModel.VariabilityGroup#getVariability()
	 * @see #getVariabilityGroup()
	 * @generated
	 */
	EAttribute getVariabilityGroup_Variability();

	/**
	 * Returns the meta object for the containment reference list '{@link familyModel.VariabilityGroup#getSubGroups <em>Sub Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sub Groups</em>'.
	 * @see familyModel.VariabilityGroup#getSubGroups()
	 * @see #getVariabilityGroup()
	 * @generated
	 */
	EReference getVariabilityGroup_SubGroups();

	/**
	 * Returns the meta object for the containment reference list '{@link familyModel.VariabilityGroup#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Elements</em>'.
	 * @see familyModel.VariabilityGroup#getElements()
	 * @see #getVariabilityGroup()
	 * @generated
	 */
	EReference getVariabilityGroup_Elements();

	/**
	 * Returns the meta object for the attribute '{@link familyModel.VariabilityGroup#getGroupName <em>Group Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Group Name</em>'.
	 * @see familyModel.VariabilityGroup#getGroupName()
	 * @see #getVariabilityGroup()
	 * @generated
	 */
	EAttribute getVariabilityGroup_GroupName();

	/**
	 * Returns the meta object for class '{@link familyModel.Element <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element</em>'.
	 * @see familyModel.Element
	 * @generated
	 */
	EClass getElement();

	/**
	 * Returns the meta object for the reference '{@link familyModel.Element#getElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Element</em>'.
	 * @see familyModel.Element#getElement()
	 * @see #getElement()
	 * @generated
	 */
	EReference getElement_Element();

	/**
	 * Returns the meta object for the attribute list '{@link familyModel.Element#getOrigins <em>Origins</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Origins</em>'.
	 * @see familyModel.Element#getOrigins()
	 * @see #getElement()
	 * @generated
	 */
	EAttribute getElement_Origins();

	/**
	 * Returns the meta object for enum '{@link familyModel.VariabilityCategory <em>Variability Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Variability Category</em>'.
	 * @see familyModel.VariabilityCategory
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
		 * The meta object literal for the '{@link familyModel.impl.FamilyModelImpl <em>Family Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see familyModel.impl.FamilyModelImpl
		 * @see familyModel.impl.FamilyModelPackageImpl#getFamilyModel()
		 * @generated
		 */
		EClass FAMILY_MODEL = eINSTANCE.getFamilyModel();

		/**
		 * The meta object literal for the '<em><b>Models</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FAMILY_MODEL__MODELS = eINSTANCE.getFamilyModel_Models();

		/**
		 * The meta object literal for the '<em><b>Variabily Groups</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FAMILY_MODEL__VARIABILY_GROUPS = eINSTANCE.getFamilyModel_VariabilyGroups();

		/**
		 * The meta object literal for the '<em><b>Fa Mo Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FAMILY_MODEL__FA_MO_NAME = eINSTANCE.getFamilyModel_FaMoName();

		/**
		 * The meta object literal for the '{@link familyModel.impl.VariabilityGroupImpl <em>Variability Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see familyModel.impl.VariabilityGroupImpl
		 * @see familyModel.impl.FamilyModelPackageImpl#getVariabilityGroup()
		 * @generated
		 */
		EClass VARIABILITY_GROUP = eINSTANCE.getVariabilityGroup();

		/**
		 * The meta object literal for the '<em><b>Variability</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABILITY_GROUP__VARIABILITY = eINSTANCE.getVariabilityGroup_Variability();

		/**
		 * The meta object literal for the '<em><b>Sub Groups</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABILITY_GROUP__SUB_GROUPS = eINSTANCE.getVariabilityGroup_SubGroups();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABILITY_GROUP__ELEMENTS = eINSTANCE.getVariabilityGroup_Elements();

		/**
		 * The meta object literal for the '<em><b>Group Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABILITY_GROUP__GROUP_NAME = eINSTANCE.getVariabilityGroup_GroupName();

		/**
		 * The meta object literal for the '{@link familyModel.impl.ElementImpl <em>Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see familyModel.impl.ElementImpl
		 * @see familyModel.impl.FamilyModelPackageImpl#getElement()
		 * @generated
		 */
		EClass ELEMENT = eINSTANCE.getElement();

		/**
		 * The meta object literal for the '<em><b>Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT__ELEMENT = eINSTANCE.getElement_Element();

		/**
		 * The meta object literal for the '<em><b>Origins</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT__ORIGINS = eINSTANCE.getElement_Origins();

		/**
		 * The meta object literal for the '{@link familyModel.VariabilityCategory <em>Variability Category</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see familyModel.VariabilityCategory
		 * @see familyModel.impl.FamilyModelPackageImpl#getVariabilityCategory()
		 * @generated
		 */
		EEnum VARIABILITY_CATEGORY = eINSTANCE.getVariabilityCategory();

	}

} //FamilyModelPackage

/**
 */
package FeatureModel;

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
 * @see FeatureModel.FeatureModelFactory
 * @model kind="package"
 * @generated
 */
public interface FeatureModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "FeatureModel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "www.example.com/featureIdeDiagram";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "FM";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FeatureModelPackage eINSTANCE = FeatureModel.impl.FeatureModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link FeatureModel.impl.FeatureModellImpl <em>Feature Modell</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see FeatureModel.impl.FeatureModellImpl
	 * @see FeatureModel.impl.FeatureModelPackageImpl#getFeatureModell()
	 * @generated
	 */
	int FEATURE_MODELL = 0;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MODELL__PROPERTIES = 0;

	/**
	 * The feature id for the '<em><b>Struct</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MODELL__STRUCT = 1;

	/**
	 * The feature id for the '<em><b>Constraints</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MODELL__CONSTRAINTS = 2;

	/**
	 * The feature id for the '<em><b>Calculations</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MODELL__CALCULATIONS = 3;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MODELL__COMMENTS = 4;

	/**
	 * The feature id for the '<em><b>Feature Order</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MODELL__FEATURE_ORDER = 5;

	/**
	 * The number of structural features of the '<em>Feature Modell</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MODELL_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Feature Modell</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MODELL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link FeatureModel.impl.PropertiesImpl <em>Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see FeatureModel.impl.PropertiesImpl
	 * @see FeatureModel.impl.FeatureModelPackageImpl#getProperties()
	 * @generated
	 */
	int PROPERTIES = 1;

	/**
	 * The feature id for the '<em><b>Graphics</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTIES__GRAPHICS = 0;

	/**
	 * The number of structural features of the '<em>Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTIES_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTIES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link FeatureModel.impl.GraphicImpl <em>Graphic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see FeatureModel.impl.GraphicImpl
	 * @see FeatureModel.impl.FeatureModelPackageImpl#getGraphic()
	 * @generated
	 */
	int GRAPHIC = 2;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPHIC__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPHIC__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Graphic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPHIC_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Graphic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPHIC_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link FeatureModel.impl.StructImpl <em>Struct</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see FeatureModel.impl.StructImpl
	 * @see FeatureModel.impl.FeatureModelPackageImpl#getStruct()
	 * @generated
	 */
	int STRUCT = 3;

	/**
	 * The feature id for the '<em><b>And</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT__AND = 0;

	/**
	 * The feature id for the '<em><b>Or</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT__OR = 1;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT__FEATURE = 2;

	/**
	 * The feature id for the '<em><b>Alt</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT__ALT = 3;

	/**
	 * The number of structural features of the '<em>Struct</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Struct</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link FeatureModel.impl.FeatureImpl <em>Feature</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see FeatureModel.impl.FeatureImpl
	 * @see FeatureModel.impl.FeatureModelPackageImpl#getFeature()
	 * @generated
	 */
	int FEATURE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Mandatory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__MANDATORY = 1;

	/**
	 * The feature id for the '<em><b>And</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__AND = 2;

	/**
	 * The feature id for the '<em><b>Or</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__OR = 3;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__FEATURE = 4;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__HIDDEN = 5;

	/**
	 * The feature id for the '<em><b>Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__ABSTRACT = 6;

	/**
	 * The feature id for the '<em><b>Alt</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__ALT = 7;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__DESCRIPTION = 8;

	/**
	 * The feature id for the '<em><b>Graphics</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__GRAPHICS = 9;

	/**
	 * The number of structural features of the '<em>Feature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_FEATURE_COUNT = 10;

	/**
	 * The number of operations of the '<em>Feature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link FeatureModel.impl.ConstraintsImpl <em>Constraints</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see FeatureModel.impl.ConstraintsImpl
	 * @see FeatureModel.impl.FeatureModelPackageImpl#getConstraints()
	 * @generated
	 */
	int CONSTRAINTS = 5;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINTS__RULE = 0;

	/**
	 * The number of structural features of the '<em>Constraints</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINTS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Constraints</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINTS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link FeatureModel.impl.RuleImpl <em>Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see FeatureModel.impl.RuleImpl
	 * @see FeatureModel.impl.FeatureModelPackageImpl#getRule()
	 * @generated
	 */
	int RULE = 6;

	/**
	 * The feature id for the '<em><b>Imp</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__IMP = 0;

	/**
	 * The feature id for the '<em><b>Not</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__NOT = 1;

	/**
	 * The feature id for the '<em><b>Disj</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__DISJ = 2;

	/**
	 * The feature id for the '<em><b>Conj</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__CONJ = 3;

	/**
	 * The feature id for the '<em><b>Iff</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__IFF = 4;

	/**
	 * The number of structural features of the '<em>Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link FeatureModel.impl.OpearationImpl <em>Opearation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see FeatureModel.impl.OpearationImpl
	 * @see FeatureModel.impl.FeatureModelPackageImpl#getOpearation()
	 * @generated
	 */
	int OPEARATION = 7;

	/**
	 * The feature id for the '<em><b>Var</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPEARATION__VAR = 0;

	/**
	 * The feature id for the '<em><b>Imp</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPEARATION__IMP = 1;

	/**
	 * The feature id for the '<em><b>Not</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPEARATION__NOT = 2;

	/**
	 * The feature id for the '<em><b>Disj</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPEARATION__DISJ = 3;

	/**
	 * The feature id for the '<em><b>Conj</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPEARATION__CONJ = 4;

	/**
	 * The feature id for the '<em><b>Iff</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPEARATION__IFF = 5;

	/**
	 * The number of structural features of the '<em>Opearation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPEARATION_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Opearation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPEARATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link FeatureModel.impl.CalculationsImpl <em>Calculations</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see FeatureModel.impl.CalculationsImpl
	 * @see FeatureModel.impl.FeatureModelPackageImpl#getCalculations()
	 * @generated
	 */
	int CALCULATIONS = 8;

	/**
	 * The feature id for the '<em><b>Auto</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALCULATIONS__AUTO = 0;

	/**
	 * The feature id for the '<em><b>Constraints</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALCULATIONS__CONSTRAINTS = 1;

	/**
	 * The feature id for the '<em><b>Features</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALCULATIONS__FEATURES = 2;

	/**
	 * The feature id for the '<em><b>Redundant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALCULATIONS__REDUNDANT = 3;

	/**
	 * The feature id for the '<em><b>Tautology</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALCULATIONS__TAUTOLOGY = 4;

	/**
	 * The number of structural features of the '<em>Calculations</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALCULATIONS_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Calculations</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALCULATIONS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link FeatureModel.impl.CommentsImpl <em>Comments</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see FeatureModel.impl.CommentsImpl
	 * @see FeatureModel.impl.FeatureModelPackageImpl#getComments()
	 * @generated
	 */
	int COMMENTS = 9;

	/**
	 * The feature id for the '<em><b>C</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENTS__C = 0;

	/**
	 * The number of structural features of the '<em>Comments</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENTS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Comments</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENTS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link FeatureModel.impl.CImpl <em>C</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see FeatureModel.impl.CImpl
	 * @see FeatureModel.impl.FeatureModelPackageImpl#getC()
	 * @generated
	 */
	int C = 10;

	/**
	 * The number of structural features of the '<em>C</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int C_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>C</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int C_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link FeatureModel.impl.FeatureOrderImpl <em>Feature Order</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see FeatureModel.impl.FeatureOrderImpl
	 * @see FeatureModel.impl.FeatureModelPackageImpl#getFeatureOrder()
	 * @generated
	 */
	int FEATURE_ORDER = 11;

	/**
	 * The feature id for the '<em><b>User Defined</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_ORDER__USER_DEFINED = 0;

	/**
	 * The number of structural features of the '<em>Feature Order</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_ORDER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Feature Order</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_ORDER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link FeatureModel.Operator <em>Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see FeatureModel.Operator
	 * @see FeatureModel.impl.FeatureModelPackageImpl#getOperator()
	 * @generated
	 */
	int OPERATOR = 12;


	/**
	 * Returns the meta object for class '{@link FeatureModel.FeatureModell <em>Feature Modell</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature Modell</em>'.
	 * @see FeatureModel.FeatureModell
	 * @generated
	 */
	EClass getFeatureModell();

	/**
	 * Returns the meta object for the containment reference '{@link FeatureModel.FeatureModell#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Properties</em>'.
	 * @see FeatureModel.FeatureModell#getProperties()
	 * @see #getFeatureModell()
	 * @generated
	 */
	EReference getFeatureModell_Properties();

	/**
	 * Returns the meta object for the containment reference '{@link FeatureModel.FeatureModell#getStruct <em>Struct</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Struct</em>'.
	 * @see FeatureModel.FeatureModell#getStruct()
	 * @see #getFeatureModell()
	 * @generated
	 */
	EReference getFeatureModell_Struct();

	/**
	 * Returns the meta object for the containment reference '{@link FeatureModel.FeatureModell#getConstraints <em>Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Constraints</em>'.
	 * @see FeatureModel.FeatureModell#getConstraints()
	 * @see #getFeatureModell()
	 * @generated
	 */
	EReference getFeatureModell_Constraints();

	/**
	 * Returns the meta object for the reference '{@link FeatureModel.FeatureModell#getCalculations <em>Calculations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Calculations</em>'.
	 * @see FeatureModel.FeatureModell#getCalculations()
	 * @see #getFeatureModell()
	 * @generated
	 */
	EReference getFeatureModell_Calculations();

	/**
	 * Returns the meta object for the reference '{@link FeatureModel.FeatureModell#getComments <em>Comments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Comments</em>'.
	 * @see FeatureModel.FeatureModell#getComments()
	 * @see #getFeatureModell()
	 * @generated
	 */
	EReference getFeatureModell_Comments();

	/**
	 * Returns the meta object for the reference '{@link FeatureModel.FeatureModell#getFeatureOrder <em>Feature Order</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Feature Order</em>'.
	 * @see FeatureModel.FeatureModell#getFeatureOrder()
	 * @see #getFeatureModell()
	 * @generated
	 */
	EReference getFeatureModell_FeatureOrder();

	/**
	 * Returns the meta object for class '{@link FeatureModel.Properties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Properties</em>'.
	 * @see FeatureModel.Properties
	 * @generated
	 */
	EClass getProperties();

	/**
	 * Returns the meta object for the reference '{@link FeatureModel.Properties#getGraphics <em>Graphics</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Graphics</em>'.
	 * @see FeatureModel.Properties#getGraphics()
	 * @see #getProperties()
	 * @generated
	 */
	EReference getProperties_Graphics();

	/**
	 * Returns the meta object for class '{@link FeatureModel.Graphic <em>Graphic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Graphic</em>'.
	 * @see FeatureModel.Graphic
	 * @generated
	 */
	EClass getGraphic();

	/**
	 * Returns the meta object for the attribute '{@link FeatureModel.Graphic#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see FeatureModel.Graphic#getKey()
	 * @see #getGraphic()
	 * @generated
	 */
	EAttribute getGraphic_Key();

	/**
	 * Returns the meta object for the attribute '{@link FeatureModel.Graphic#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see FeatureModel.Graphic#getValue()
	 * @see #getGraphic()
	 * @generated
	 */
	EAttribute getGraphic_Value();

	/**
	 * Returns the meta object for class '{@link FeatureModel.Struct <em>Struct</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Struct</em>'.
	 * @see FeatureModel.Struct
	 * @generated
	 */
	EClass getStruct();

	/**
	 * Returns the meta object for the containment reference '{@link FeatureModel.Struct#getAnd <em>And</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>And</em>'.
	 * @see FeatureModel.Struct#getAnd()
	 * @see #getStruct()
	 * @generated
	 */
	EReference getStruct_And();

	/**
	 * Returns the meta object for the containment reference '{@link FeatureModel.Struct#getOr <em>Or</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Or</em>'.
	 * @see FeatureModel.Struct#getOr()
	 * @see #getStruct()
	 * @generated
	 */
	EReference getStruct_Or();

	/**
	 * Returns the meta object for the containment reference '{@link FeatureModel.Struct#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Feature</em>'.
	 * @see FeatureModel.Struct#getFeature()
	 * @see #getStruct()
	 * @generated
	 */
	EReference getStruct_Feature();

	/**
	 * Returns the meta object for the containment reference '{@link FeatureModel.Struct#getAlt <em>Alt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Alt</em>'.
	 * @see FeatureModel.Struct#getAlt()
	 * @see #getStruct()
	 * @generated
	 */
	EReference getStruct_Alt();

	/**
	 * Returns the meta object for class '{@link FeatureModel.Feature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature</em>'.
	 * @see FeatureModel.Feature
	 * @generated
	 */
	EClass getFeature();

	/**
	 * Returns the meta object for the attribute '{@link FeatureModel.Feature#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see FeatureModel.Feature#getName()
	 * @see #getFeature()
	 * @generated
	 */
	EAttribute getFeature_Name();

	/**
	 * Returns the meta object for the attribute '{@link FeatureModel.Feature#isMandatory <em>Mandatory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mandatory</em>'.
	 * @see FeatureModel.Feature#isMandatory()
	 * @see #getFeature()
	 * @generated
	 */
	EAttribute getFeature_Mandatory();

	/**
	 * Returns the meta object for the containment reference list '{@link FeatureModel.Feature#getAnd <em>And</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>And</em>'.
	 * @see FeatureModel.Feature#getAnd()
	 * @see #getFeature()
	 * @generated
	 */
	EReference getFeature_And();

	/**
	 * Returns the meta object for the containment reference list '{@link FeatureModel.Feature#getOr <em>Or</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Or</em>'.
	 * @see FeatureModel.Feature#getOr()
	 * @see #getFeature()
	 * @generated
	 */
	EReference getFeature_Or();

	/**
	 * Returns the meta object for the containment reference list '{@link FeatureModel.Feature#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Feature</em>'.
	 * @see FeatureModel.Feature#getFeature()
	 * @see #getFeature()
	 * @generated
	 */
	EReference getFeature_Feature();

	/**
	 * Returns the meta object for the attribute '{@link FeatureModel.Feature#isHidden <em>Hidden</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hidden</em>'.
	 * @see FeatureModel.Feature#isHidden()
	 * @see #getFeature()
	 * @generated
	 */
	EAttribute getFeature_Hidden();

	/**
	 * Returns the meta object for the attribute '{@link FeatureModel.Feature#isAbstract <em>Abstract</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Abstract</em>'.
	 * @see FeatureModel.Feature#isAbstract()
	 * @see #getFeature()
	 * @generated
	 */
	EAttribute getFeature_Abstract();

	/**
	 * Returns the meta object for the containment reference list '{@link FeatureModel.Feature#getAlt <em>Alt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Alt</em>'.
	 * @see FeatureModel.Feature#getAlt()
	 * @see #getFeature()
	 * @generated
	 */
	EReference getFeature_Alt();

	/**
	 * Returns the meta object for the attribute '{@link FeatureModel.Feature#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see FeatureModel.Feature#getDescription()
	 * @see #getFeature()
	 * @generated
	 */
	EAttribute getFeature_Description();

	/**
	 * Returns the meta object for the reference '{@link FeatureModel.Feature#getGraphics <em>Graphics</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Graphics</em>'.
	 * @see FeatureModel.Feature#getGraphics()
	 * @see #getFeature()
	 * @generated
	 */
	EReference getFeature_Graphics();

	/**
	 * Returns the meta object for class '{@link FeatureModel.Constraints <em>Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constraints</em>'.
	 * @see FeatureModel.Constraints
	 * @generated
	 */
	EClass getConstraints();

	/**
	 * Returns the meta object for the containment reference list '{@link FeatureModel.Constraints#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rule</em>'.
	 * @see FeatureModel.Constraints#getRule()
	 * @see #getConstraints()
	 * @generated
	 */
	EReference getConstraints_Rule();

	/**
	 * Returns the meta object for class '{@link FeatureModel.Rule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule</em>'.
	 * @see FeatureModel.Rule
	 * @generated
	 */
	EClass getRule();

	/**
	 * Returns the meta object for the containment reference list '{@link FeatureModel.Rule#getImp <em>Imp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Imp</em>'.
	 * @see FeatureModel.Rule#getImp()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_Imp();

	/**
	 * Returns the meta object for the containment reference list '{@link FeatureModel.Rule#getNot <em>Not</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Not</em>'.
	 * @see FeatureModel.Rule#getNot()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_Not();

	/**
	 * Returns the meta object for the containment reference list '{@link FeatureModel.Rule#getDisj <em>Disj</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Disj</em>'.
	 * @see FeatureModel.Rule#getDisj()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_Disj();

	/**
	 * Returns the meta object for the containment reference list '{@link FeatureModel.Rule#getConj <em>Conj</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Conj</em>'.
	 * @see FeatureModel.Rule#getConj()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_Conj();

	/**
	 * Returns the meta object for the reference list '{@link FeatureModel.Rule#getIff <em>Iff</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Iff</em>'.
	 * @see FeatureModel.Rule#getIff()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_Iff();

	/**
	 * Returns the meta object for class '{@link FeatureModel.Opearation <em>Opearation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Opearation</em>'.
	 * @see FeatureModel.Opearation
	 * @generated
	 */
	EClass getOpearation();

	/**
	 * Returns the meta object for the attribute list '{@link FeatureModel.Opearation#getVar <em>Var</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Var</em>'.
	 * @see FeatureModel.Opearation#getVar()
	 * @see #getOpearation()
	 * @generated
	 */
	EAttribute getOpearation_Var();

	/**
	 * Returns the meta object for the containment reference list '{@link FeatureModel.Opearation#getImp <em>Imp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Imp</em>'.
	 * @see FeatureModel.Opearation#getImp()
	 * @see #getOpearation()
	 * @generated
	 */
	EReference getOpearation_Imp();

	/**
	 * Returns the meta object for the containment reference list '{@link FeatureModel.Opearation#getNot <em>Not</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Not</em>'.
	 * @see FeatureModel.Opearation#getNot()
	 * @see #getOpearation()
	 * @generated
	 */
	EReference getOpearation_Not();

	/**
	 * Returns the meta object for the containment reference list '{@link FeatureModel.Opearation#getDisj <em>Disj</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Disj</em>'.
	 * @see FeatureModel.Opearation#getDisj()
	 * @see #getOpearation()
	 * @generated
	 */
	EReference getOpearation_Disj();

	/**
	 * Returns the meta object for the containment reference list '{@link FeatureModel.Opearation#getConj <em>Conj</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Conj</em>'.
	 * @see FeatureModel.Opearation#getConj()
	 * @see #getOpearation()
	 * @generated
	 */
	EReference getOpearation_Conj();

	/**
	 * Returns the meta object for the reference list '{@link FeatureModel.Opearation#getIff <em>Iff</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Iff</em>'.
	 * @see FeatureModel.Opearation#getIff()
	 * @see #getOpearation()
	 * @generated
	 */
	EReference getOpearation_Iff();

	/**
	 * Returns the meta object for class '{@link FeatureModel.Calculations <em>Calculations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Calculations</em>'.
	 * @see FeatureModel.Calculations
	 * @generated
	 */
	EClass getCalculations();

	/**
	 * Returns the meta object for the attribute '{@link FeatureModel.Calculations#isAuto <em>Auto</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Auto</em>'.
	 * @see FeatureModel.Calculations#isAuto()
	 * @see #getCalculations()
	 * @generated
	 */
	EAttribute getCalculations_Auto();

	/**
	 * Returns the meta object for the attribute '{@link FeatureModel.Calculations#isConstraints <em>Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Constraints</em>'.
	 * @see FeatureModel.Calculations#isConstraints()
	 * @see #getCalculations()
	 * @generated
	 */
	EAttribute getCalculations_Constraints();

	/**
	 * Returns the meta object for the attribute '{@link FeatureModel.Calculations#isFeatures <em>Features</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Features</em>'.
	 * @see FeatureModel.Calculations#isFeatures()
	 * @see #getCalculations()
	 * @generated
	 */
	EAttribute getCalculations_Features();

	/**
	 * Returns the meta object for the attribute '{@link FeatureModel.Calculations#isRedundant <em>Redundant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Redundant</em>'.
	 * @see FeatureModel.Calculations#isRedundant()
	 * @see #getCalculations()
	 * @generated
	 */
	EAttribute getCalculations_Redundant();

	/**
	 * Returns the meta object for the attribute '{@link FeatureModel.Calculations#isTautology <em>Tautology</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tautology</em>'.
	 * @see FeatureModel.Calculations#isTautology()
	 * @see #getCalculations()
	 * @generated
	 */
	EAttribute getCalculations_Tautology();

	/**
	 * Returns the meta object for class '{@link FeatureModel.Comments <em>Comments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comments</em>'.
	 * @see FeatureModel.Comments
	 * @generated
	 */
	EClass getComments();

	/**
	 * Returns the meta object for the reference '{@link FeatureModel.Comments#getC <em>C</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>C</em>'.
	 * @see FeatureModel.Comments#getC()
	 * @see #getComments()
	 * @generated
	 */
	EReference getComments_C();

	/**
	 * Returns the meta object for class '{@link FeatureModel.C <em>C</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>C</em>'.
	 * @see FeatureModel.C
	 * @generated
	 */
	EClass getC();

	/**
	 * Returns the meta object for class '{@link FeatureModel.FeatureOrder <em>Feature Order</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature Order</em>'.
	 * @see FeatureModel.FeatureOrder
	 * @generated
	 */
	EClass getFeatureOrder();

	/**
	 * Returns the meta object for the attribute '{@link FeatureModel.FeatureOrder#isUserDefined <em>User Defined</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>User Defined</em>'.
	 * @see FeatureModel.FeatureOrder#isUserDefined()
	 * @see #getFeatureOrder()
	 * @generated
	 */
	EAttribute getFeatureOrder_UserDefined();

	/**
	 * Returns the meta object for enum '{@link FeatureModel.Operator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Operator</em>'.
	 * @see FeatureModel.Operator
	 * @generated
	 */
	EEnum getOperator();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	FeatureModelFactory getFeatureModelFactory();

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
		 * The meta object literal for the '{@link FeatureModel.impl.FeatureModellImpl <em>Feature Modell</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see FeatureModel.impl.FeatureModellImpl
		 * @see FeatureModel.impl.FeatureModelPackageImpl#getFeatureModell()
		 * @generated
		 */
		EClass FEATURE_MODELL = eINSTANCE.getFeatureModell();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_MODELL__PROPERTIES = eINSTANCE.getFeatureModell_Properties();

		/**
		 * The meta object literal for the '<em><b>Struct</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_MODELL__STRUCT = eINSTANCE.getFeatureModell_Struct();

		/**
		 * The meta object literal for the '<em><b>Constraints</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_MODELL__CONSTRAINTS = eINSTANCE.getFeatureModell_Constraints();

		/**
		 * The meta object literal for the '<em><b>Calculations</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_MODELL__CALCULATIONS = eINSTANCE.getFeatureModell_Calculations();

		/**
		 * The meta object literal for the '<em><b>Comments</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_MODELL__COMMENTS = eINSTANCE.getFeatureModell_Comments();

		/**
		 * The meta object literal for the '<em><b>Feature Order</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_MODELL__FEATURE_ORDER = eINSTANCE.getFeatureModell_FeatureOrder();

		/**
		 * The meta object literal for the '{@link FeatureModel.impl.PropertiesImpl <em>Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see FeatureModel.impl.PropertiesImpl
		 * @see FeatureModel.impl.FeatureModelPackageImpl#getProperties()
		 * @generated
		 */
		EClass PROPERTIES = eINSTANCE.getProperties();

		/**
		 * The meta object literal for the '<em><b>Graphics</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTIES__GRAPHICS = eINSTANCE.getProperties_Graphics();

		/**
		 * The meta object literal for the '{@link FeatureModel.impl.GraphicImpl <em>Graphic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see FeatureModel.impl.GraphicImpl
		 * @see FeatureModel.impl.FeatureModelPackageImpl#getGraphic()
		 * @generated
		 */
		EClass GRAPHIC = eINSTANCE.getGraphic();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRAPHIC__KEY = eINSTANCE.getGraphic_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRAPHIC__VALUE = eINSTANCE.getGraphic_Value();

		/**
		 * The meta object literal for the '{@link FeatureModel.impl.StructImpl <em>Struct</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see FeatureModel.impl.StructImpl
		 * @see FeatureModel.impl.FeatureModelPackageImpl#getStruct()
		 * @generated
		 */
		EClass STRUCT = eINSTANCE.getStruct();

		/**
		 * The meta object literal for the '<em><b>And</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRUCT__AND = eINSTANCE.getStruct_And();

		/**
		 * The meta object literal for the '<em><b>Or</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRUCT__OR = eINSTANCE.getStruct_Or();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRUCT__FEATURE = eINSTANCE.getStruct_Feature();

		/**
		 * The meta object literal for the '<em><b>Alt</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRUCT__ALT = eINSTANCE.getStruct_Alt();

		/**
		 * The meta object literal for the '{@link FeatureModel.impl.FeatureImpl <em>Feature</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see FeatureModel.impl.FeatureImpl
		 * @see FeatureModel.impl.FeatureModelPackageImpl#getFeature()
		 * @generated
		 */
		EClass FEATURE = eINSTANCE.getFeature();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE__NAME = eINSTANCE.getFeature_Name();

		/**
		 * The meta object literal for the '<em><b>Mandatory</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE__MANDATORY = eINSTANCE.getFeature_Mandatory();

		/**
		 * The meta object literal for the '<em><b>And</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE__AND = eINSTANCE.getFeature_And();

		/**
		 * The meta object literal for the '<em><b>Or</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE__OR = eINSTANCE.getFeature_Or();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE__FEATURE = eINSTANCE.getFeature_Feature();

		/**
		 * The meta object literal for the '<em><b>Hidden</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE__HIDDEN = eINSTANCE.getFeature_Hidden();

		/**
		 * The meta object literal for the '<em><b>Abstract</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE__ABSTRACT = eINSTANCE.getFeature_Abstract();

		/**
		 * The meta object literal for the '<em><b>Alt</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE__ALT = eINSTANCE.getFeature_Alt();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE__DESCRIPTION = eINSTANCE.getFeature_Description();

		/**
		 * The meta object literal for the '<em><b>Graphics</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE__GRAPHICS = eINSTANCE.getFeature_Graphics();

		/**
		 * The meta object literal for the '{@link FeatureModel.impl.ConstraintsImpl <em>Constraints</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see FeatureModel.impl.ConstraintsImpl
		 * @see FeatureModel.impl.FeatureModelPackageImpl#getConstraints()
		 * @generated
		 */
		EClass CONSTRAINTS = eINSTANCE.getConstraints();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINTS__RULE = eINSTANCE.getConstraints_Rule();

		/**
		 * The meta object literal for the '{@link FeatureModel.impl.RuleImpl <em>Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see FeatureModel.impl.RuleImpl
		 * @see FeatureModel.impl.FeatureModelPackageImpl#getRule()
		 * @generated
		 */
		EClass RULE = eINSTANCE.getRule();

		/**
		 * The meta object literal for the '<em><b>Imp</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE__IMP = eINSTANCE.getRule_Imp();

		/**
		 * The meta object literal for the '<em><b>Not</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE__NOT = eINSTANCE.getRule_Not();

		/**
		 * The meta object literal for the '<em><b>Disj</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE__DISJ = eINSTANCE.getRule_Disj();

		/**
		 * The meta object literal for the '<em><b>Conj</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE__CONJ = eINSTANCE.getRule_Conj();

		/**
		 * The meta object literal for the '<em><b>Iff</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE__IFF = eINSTANCE.getRule_Iff();

		/**
		 * The meta object literal for the '{@link FeatureModel.impl.OpearationImpl <em>Opearation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see FeatureModel.impl.OpearationImpl
		 * @see FeatureModel.impl.FeatureModelPackageImpl#getOpearation()
		 * @generated
		 */
		EClass OPEARATION = eINSTANCE.getOpearation();

		/**
		 * The meta object literal for the '<em><b>Var</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPEARATION__VAR = eINSTANCE.getOpearation_Var();

		/**
		 * The meta object literal for the '<em><b>Imp</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPEARATION__IMP = eINSTANCE.getOpearation_Imp();

		/**
		 * The meta object literal for the '<em><b>Not</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPEARATION__NOT = eINSTANCE.getOpearation_Not();

		/**
		 * The meta object literal for the '<em><b>Disj</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPEARATION__DISJ = eINSTANCE.getOpearation_Disj();

		/**
		 * The meta object literal for the '<em><b>Conj</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPEARATION__CONJ = eINSTANCE.getOpearation_Conj();

		/**
		 * The meta object literal for the '<em><b>Iff</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPEARATION__IFF = eINSTANCE.getOpearation_Iff();

		/**
		 * The meta object literal for the '{@link FeatureModel.impl.CalculationsImpl <em>Calculations</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see FeatureModel.impl.CalculationsImpl
		 * @see FeatureModel.impl.FeatureModelPackageImpl#getCalculations()
		 * @generated
		 */
		EClass CALCULATIONS = eINSTANCE.getCalculations();

		/**
		 * The meta object literal for the '<em><b>Auto</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CALCULATIONS__AUTO = eINSTANCE.getCalculations_Auto();

		/**
		 * The meta object literal for the '<em><b>Constraints</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CALCULATIONS__CONSTRAINTS = eINSTANCE.getCalculations_Constraints();

		/**
		 * The meta object literal for the '<em><b>Features</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CALCULATIONS__FEATURES = eINSTANCE.getCalculations_Features();

		/**
		 * The meta object literal for the '<em><b>Redundant</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CALCULATIONS__REDUNDANT = eINSTANCE.getCalculations_Redundant();

		/**
		 * The meta object literal for the '<em><b>Tautology</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CALCULATIONS__TAUTOLOGY = eINSTANCE.getCalculations_Tautology();

		/**
		 * The meta object literal for the '{@link FeatureModel.impl.CommentsImpl <em>Comments</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see FeatureModel.impl.CommentsImpl
		 * @see FeatureModel.impl.FeatureModelPackageImpl#getComments()
		 * @generated
		 */
		EClass COMMENTS = eINSTANCE.getComments();

		/**
		 * The meta object literal for the '<em><b>C</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMENTS__C = eINSTANCE.getComments_C();

		/**
		 * The meta object literal for the '{@link FeatureModel.impl.CImpl <em>C</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see FeatureModel.impl.CImpl
		 * @see FeatureModel.impl.FeatureModelPackageImpl#getC()
		 * @generated
		 */
		EClass C = eINSTANCE.getC();

		/**
		 * The meta object literal for the '{@link FeatureModel.impl.FeatureOrderImpl <em>Feature Order</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see FeatureModel.impl.FeatureOrderImpl
		 * @see FeatureModel.impl.FeatureModelPackageImpl#getFeatureOrder()
		 * @generated
		 */
		EClass FEATURE_ORDER = eINSTANCE.getFeatureOrder();

		/**
		 * The meta object literal for the '<em><b>User Defined</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_ORDER__USER_DEFINED = eINSTANCE.getFeatureOrder_UserDefined();

		/**
		 * The meta object literal for the '{@link FeatureModel.Operator <em>Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see FeatureModel.Operator
		 * @see FeatureModel.impl.FeatureModelPackageImpl#getOperator()
		 * @generated
		 */
		EEnum OPERATOR = eINSTANCE.getOperator();

	}

} //FeatureModelPackage

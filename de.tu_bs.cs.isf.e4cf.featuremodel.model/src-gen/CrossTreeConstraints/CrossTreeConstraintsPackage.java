/**
 */
package CrossTreeConstraints;

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
 * @see CrossTreeConstraints.CrossTreeConstraintsFactory
 * @model kind="package"
 * @generated
 */
public interface CrossTreeConstraintsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "CrossTreeConstraints";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "www.example.com/CrossTreeConstraints";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "CTC";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CrossTreeConstraintsPackage eINSTANCE = CrossTreeConstraints.impl.CrossTreeConstraintsPackageImpl.init();

	/**
	 * The meta object id for the '{@link CrossTreeConstraints.impl.AbstractConstraintImpl <em>Abstract Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see CrossTreeConstraints.impl.AbstractConstraintImpl
	 * @see CrossTreeConstraints.impl.CrossTreeConstraintsPackageImpl#getAbstractConstraint()
	 * @generated
	 */
	int ABSTRACT_CONSTRAINT = 2;

	/**
	 * The feature id for the '<em><b>Is Negated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONSTRAINT__IS_NEGATED = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONSTRAINT__DESCRIPTION = 1;

	/**
	 * The number of structural features of the '<em>Abstract Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONSTRAINT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Abstract Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONSTRAINT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link CrossTreeConstraints.impl.FormulaImpl <em>Formula</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see CrossTreeConstraints.impl.FormulaImpl
	 * @see CrossTreeConstraints.impl.CrossTreeConstraintsPackageImpl#getFormula()
	 * @generated
	 */
	int FORMULA = 0;

	/**
	 * The feature id for the '<em><b>Is Negated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMULA__IS_NEGATED = ABSTRACT_CONSTRAINT__IS_NEGATED;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMULA__DESCRIPTION = ABSTRACT_CONSTRAINT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMULA__OPERATOR = ABSTRACT_CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Formula</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMULA__FORMULA = ABSTRACT_CONSTRAINT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Formula</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMULA_FEATURE_COUNT = ABSTRACT_CONSTRAINT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Formula</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMULA_OPERATION_COUNT = ABSTRACT_CONSTRAINT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link CrossTreeConstraints.impl.LiteralImpl <em>Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see CrossTreeConstraints.impl.LiteralImpl
	 * @see CrossTreeConstraints.impl.CrossTreeConstraintsPackageImpl#getLiteral()
	 * @generated
	 */
	int LITERAL = 1;

	/**
	 * The feature id for the '<em><b>Is Negated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL__IS_NEGATED = ABSTRACT_CONSTRAINT__IS_NEGATED;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL__DESCRIPTION = ABSTRACT_CONSTRAINT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL__NAME = ABSTRACT_CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_FEATURE_COUNT = ABSTRACT_CONSTRAINT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_OPERATION_COUNT = ABSTRACT_CONSTRAINT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link CrossTreeConstraints.Operator <em>Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see CrossTreeConstraints.Operator
	 * @see CrossTreeConstraints.impl.CrossTreeConstraintsPackageImpl#getOperator()
	 * @generated
	 */
	int OPERATOR = 3;


	/**
	 * Returns the meta object for class '{@link CrossTreeConstraints.Formula <em>Formula</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Formula</em>'.
	 * @see CrossTreeConstraints.Formula
	 * @generated
	 */
	EClass getFormula();

	/**
	 * Returns the meta object for the attribute '{@link CrossTreeConstraints.Formula#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see CrossTreeConstraints.Formula#getOperator()
	 * @see #getFormula()
	 * @generated
	 */
	EAttribute getFormula_Operator();

	/**
	 * Returns the meta object for the containment reference list '{@link CrossTreeConstraints.Formula#getFormula <em>Formula</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Formula</em>'.
	 * @see CrossTreeConstraints.Formula#getFormula()
	 * @see #getFormula()
	 * @generated
	 */
	EReference getFormula_Formula();

	/**
	 * Returns the meta object for class '{@link CrossTreeConstraints.Literal <em>Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Literal</em>'.
	 * @see CrossTreeConstraints.Literal
	 * @generated
	 */
	EClass getLiteral();

	/**
	 * Returns the meta object for the attribute '{@link CrossTreeConstraints.Literal#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see CrossTreeConstraints.Literal#getName()
	 * @see #getLiteral()
	 * @generated
	 */
	EAttribute getLiteral_Name();

	/**
	 * Returns the meta object for class '{@link CrossTreeConstraints.AbstractConstraint <em>Abstract Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Constraint</em>'.
	 * @see CrossTreeConstraints.AbstractConstraint
	 * @generated
	 */
	EClass getAbstractConstraint();

	/**
	 * Returns the meta object for the attribute '{@link CrossTreeConstraints.AbstractConstraint#getIsNegated <em>Is Negated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Negated</em>'.
	 * @see CrossTreeConstraints.AbstractConstraint#getIsNegated()
	 * @see #getAbstractConstraint()
	 * @generated
	 */
	EAttribute getAbstractConstraint_IsNegated();

	/**
	 * Returns the meta object for the attribute '{@link CrossTreeConstraints.AbstractConstraint#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see CrossTreeConstraints.AbstractConstraint#getDescription()
	 * @see #getAbstractConstraint()
	 * @generated
	 */
	EAttribute getAbstractConstraint_Description();

	/**
	 * Returns the meta object for enum '{@link CrossTreeConstraints.Operator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Operator</em>'.
	 * @see CrossTreeConstraints.Operator
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
	CrossTreeConstraintsFactory getCrossTreeConstraintsFactory();

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
		 * The meta object literal for the '{@link CrossTreeConstraints.impl.FormulaImpl <em>Formula</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see CrossTreeConstraints.impl.FormulaImpl
		 * @see CrossTreeConstraints.impl.CrossTreeConstraintsPackageImpl#getFormula()
		 * @generated
		 */
		EClass FORMULA = eINSTANCE.getFormula();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FORMULA__OPERATOR = eINSTANCE.getFormula_Operator();

		/**
		 * The meta object literal for the '<em><b>Formula</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FORMULA__FORMULA = eINSTANCE.getFormula_Formula();

		/**
		 * The meta object literal for the '{@link CrossTreeConstraints.impl.LiteralImpl <em>Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see CrossTreeConstraints.impl.LiteralImpl
		 * @see CrossTreeConstraints.impl.CrossTreeConstraintsPackageImpl#getLiteral()
		 * @generated
		 */
		EClass LITERAL = eINSTANCE.getLiteral();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LITERAL__NAME = eINSTANCE.getLiteral_Name();

		/**
		 * The meta object literal for the '{@link CrossTreeConstraints.impl.AbstractConstraintImpl <em>Abstract Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see CrossTreeConstraints.impl.AbstractConstraintImpl
		 * @see CrossTreeConstraints.impl.CrossTreeConstraintsPackageImpl#getAbstractConstraint()
		 * @generated
		 */
		EClass ABSTRACT_CONSTRAINT = eINSTANCE.getAbstractConstraint();

		/**
		 * The meta object literal for the '<em><b>Is Negated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_CONSTRAINT__IS_NEGATED = eINSTANCE.getAbstractConstraint_IsNegated();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_CONSTRAINT__DESCRIPTION = eINSTANCE.getAbstractConstraint_Description();

		/**
		 * The meta object literal for the '{@link CrossTreeConstraints.Operator <em>Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see CrossTreeConstraints.Operator
		 * @see CrossTreeConstraints.impl.CrossTreeConstraintsPackageImpl#getOperator()
		 * @generated
		 */
		EEnum OPERATOR = eINSTANCE.getOperator();

	}

} //CrossTreeConstraintsPackage

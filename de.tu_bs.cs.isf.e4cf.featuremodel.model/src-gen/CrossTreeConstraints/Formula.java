/**
 */
package CrossTreeConstraints;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Formula</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link CrossTreeConstraints.Formula#getOperator <em>Operator</em>}</li>
 *   <li>{@link CrossTreeConstraints.Formula#getFormula <em>Formula</em>}</li>
 * </ul>
 *
 * @see CrossTreeConstraints.CrossTreeConstraintsPackage#getFormula()
 * @model
 * @generated
 */
public interface Formula extends AbstractConstraint {
	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The default value is <code>"NONE"</code>.
	 * The literals are from the enumeration {@link CrossTreeConstraints.Operator}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see CrossTreeConstraints.Operator
	 * @see #setOperator(Operator)
	 * @see CrossTreeConstraints.CrossTreeConstraintsPackage#getFormula_Operator()
	 * @model default="NONE"
	 * @generated
	 */
	Operator getOperator();

	/**
	 * Sets the value of the '{@link CrossTreeConstraints.Formula#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see CrossTreeConstraints.Operator
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(Operator value);

	/**
	 * Returns the value of the '<em><b>Formula</b></em>' containment reference list.
	 * The list contents are of type {@link CrossTreeConstraints.AbstractConstraint}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Formula</em>' containment reference list.
	 * @see CrossTreeConstraints.CrossTreeConstraintsPackage#getFormula_Formula()
	 * @model containment="true"
	 * @generated
	 */
	EList<AbstractConstraint> getFormula();

} // Formula

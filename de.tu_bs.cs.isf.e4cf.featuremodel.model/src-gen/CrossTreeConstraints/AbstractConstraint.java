/**
 */
package CrossTreeConstraints;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link CrossTreeConstraints.AbstractConstraint#getIsNegated <em>Is Negated</em>}</li>
 *   <li>{@link CrossTreeConstraints.AbstractConstraint#getDescription <em>Description</em>}</li>
 * </ul>
 *
 * @see CrossTreeConstraints.CrossTreeConstraintsPackage#getAbstractConstraint()
 * @model abstract="true"
 * @generated
 */
public interface AbstractConstraint extends EObject {
	/**
	 * Returns the value of the '<em><b>Is Negated</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Negated</em>' attribute.
	 * @see #setIsNegated(Boolean)
	 * @see CrossTreeConstraints.CrossTreeConstraintsPackage#getAbstractConstraint_IsNegated()
	 * @model default="false" required="true"
	 * @generated
	 */
	Boolean getIsNegated();

	/**
	 * Sets the value of the '{@link CrossTreeConstraints.AbstractConstraint#getIsNegated <em>Is Negated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Negated</em>' attribute.
	 * @see #getIsNegated()
	 * @generated
	 */
	void setIsNegated(Boolean value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * The default value is <code>"\"\""</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see CrossTreeConstraints.CrossTreeConstraintsPackage#getAbstractConstraint_Description()
	 * @model default="\"\""
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link CrossTreeConstraints.AbstractConstraint#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

} // AbstractConstraint

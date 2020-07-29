/**
 */
package CrossTreeConstraints;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Literal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link CrossTreeConstraints.Literal#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see CrossTreeConstraints.CrossTreeConstraintsPackage#getLiteral()
 * @model
 * @generated
 */
public interface Literal extends AbstractConstraint {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see CrossTreeConstraints.CrossTreeConstraintsPackage#getLiteral_Name()
	 * @model default=""
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link CrossTreeConstraints.Literal#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // Literal

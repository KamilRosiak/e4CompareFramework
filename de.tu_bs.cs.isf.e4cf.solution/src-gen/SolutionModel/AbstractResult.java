/**
 */
package SolutionModel;

import org.eclipse.emf.ecore.EObject;

import MetricModel.AbstractAttribute;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Result</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link SolutionModel.AbstractResult#getAttribute <em>Attribute</em>}</li>
 * </ul>
 *
 * @see SolutionModel.SolutionModelPackage#getAbstractResult()
 * @model abstract="true"
 * @generated
 */
public interface AbstractResult extends EObject {
	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' reference.
	 * @see #setAttribute(AbstractAttribute)
	 * @see SolutionModel.SolutionModelPackage#getAbstractResult_Attribute()
	 * @model
	 * @generated
	 */
	AbstractAttribute getAttribute();

	/**
	 * Sets the value of the '{@link SolutionModel.AbstractResult#getAttribute <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' reference.
	 * @see #getAttribute()
	 * @generated
	 */
	void setAttribute(AbstractAttribute value);

} // AbstractResult

/**
 */
package SolutionModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Solution Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link SolutionModel.AbstractSolutionElement#getSimilarity <em>Similarity</em>}</li>
 *   <li>{@link SolutionModel.AbstractSolutionElement#getLeftElement <em>Left Element</em>}</li>
 *   <li>{@link SolutionModel.AbstractSolutionElement#getRightElement <em>Right Element</em>}</li>
 * </ul>
 *
 * @see SolutionModel.SolutionModelPackage#getAbstractSolutionElement()
 * @model abstract="true"
 * @generated
 */
public interface AbstractSolutionElement<T extends EObject> extends IUpdate, IFamilyModelAdapter {
	/**
	 * Returns the value of the '<em><b>Similarity</b></em>' attribute.
	 * The default value is <code>"0.0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Similarity</em>' attribute.
	 * @see #setSimilarity(float)
	 * @see SolutionModel.SolutionModelPackage#getAbstractSolutionElement_Similarity()
	 * @model default="0.0" required="true"
	 * @generated
	 */
	float getSimilarity();

	/**
	 * Sets the value of the '{@link SolutionModel.AbstractSolutionElement#getSimilarity <em>Similarity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Similarity</em>' attribute.
	 * @see #getSimilarity()
	 * @generated
	 */
	void setSimilarity(float value);

	/**
	 * Returns the value of the '<em><b>Left Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Left Element</em>' attribute.
	 * @see #setLeftElement(EObject)
	 * @see SolutionModel.SolutionModelPackage#getAbstractSolutionElement_LeftElement()
	 * @model
	 * @generated
	 */
	T getLeftElement();

	/**
	 * Sets the value of the '{@link SolutionModel.AbstractSolutionElement#getLeftElement <em>Left Element</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Left Element</em>' attribute.
	 * @see #getLeftElement()
	 * @generated
	 */
	void setLeftElement(T value);

	/**
	 * Returns the value of the '<em><b>Right Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Right Element</em>' attribute.
	 * @see #setRightElement(EObject)
	 * @see SolutionModel.SolutionModelPackage#getAbstractSolutionElement_RightElement()
	 * @model
	 * @generated
	 */
	T getRightElement();

	/**
	 * Sets the value of the '{@link SolutionModel.AbstractSolutionElement#getRightElement <em>Right Element</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Right Element</em>' attribute.
	 * @see #getRightElement()
	 * @generated
	 */
	void setRightElement(T value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void resetElement();

} // AbstractSolutionElement

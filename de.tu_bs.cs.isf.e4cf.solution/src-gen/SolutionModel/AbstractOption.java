/**
 */
package SolutionModel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Option</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link SolutionModel.AbstractOption#getAbstractcontainer <em>Abstractcontainer</em>}</li>
 * </ul>
 *
 * @see SolutionModel.SolutionModelPackage#getAbstractOption()
 * @model abstract="true"
 * @generated
 */
public interface AbstractOption extends AbstractSolutionElement {
	/**
	 * Returns the value of the '<em><b>Abstractcontainer</b></em>' containment reference list.
	 * The list contents are of type {@link SolutionModel.AbstractContainer}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abstractcontainer</em>' containment reference list.
	 * @see SolutionModel.SolutionModelPackage#getAbstractOption_Abstractcontainer()
	 * @model containment="true"
	 * @generated
	 */
	EList<AbstractContainer> getAbstractcontainer();

} // AbstractOption

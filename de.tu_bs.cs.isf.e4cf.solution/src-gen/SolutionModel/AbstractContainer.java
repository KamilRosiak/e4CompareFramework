/**
 */
package SolutionModel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link SolutionModel.AbstractContainer#getAbstractoption <em>Abstractoption</em>}</li>
 *   <li>{@link SolutionModel.AbstractContainer#getAbstractresult <em>Abstractresult</em>}</li>
 * </ul>
 *
 * @see SolutionModel.SolutionModelPackage#getAbstractContainer()
 * @model abstract="true"
 * @generated
 */
public interface AbstractContainer<T extends EObject> extends AbstractSolutionElement {
	/**
	 * Returns the value of the '<em><b>Abstractoption</b></em>' containment reference list.
	 * The list contents are of type {@link SolutionModel.AbstractOption}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abstractoption</em>' containment reference list.
	 * @see SolutionModel.SolutionModelPackage#getAbstractContainer_Abstractoption()
	 * @model containment="true"
	 * @generated
	 */
	EList<AbstractOption> getAbstractoption();

	/**
	 * Returns the value of the '<em><b>Abstractresult</b></em>' containment reference list.
	 * The list contents are of type {@link SolutionModel.AbstractResult}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abstractresult</em>' containment reference list.
	 * @see SolutionModel.SolutionModelPackage#getAbstractContainer_Abstractresult()
	 * @model containment="true"
	 * @generated
	 */
	EList<AbstractResult> getAbstractresult();

} // AbstractContainer

/**
 */
package FeatureModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constraints</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link FeatureModel.Constraints#getRule <em>Rule</em>}</li>
 * </ul>
 *
 * @see FeatureModel.FeatureModelPackage#getConstraints()
 * @model
 * @generated
 */
public interface Constraints extends EObject {
	/**
	 * Returns the value of the '<em><b>Rule</b></em>' containment reference list.
	 * The list contents are of type {@link FeatureModel.Rule}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule</em>' containment reference list.
	 * @see FeatureModel.FeatureModelPackage#getConstraints_Rule()
	 * @model containment="true"
	 * @generated
	 */
	EList<Rule> getRule();

} // Constraints

/**
 */
package FeatureModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature Order</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link FeatureModel.FeatureOrder#isUserDefined <em>User Defined</em>}</li>
 * </ul>
 *
 * @see FeatureModel.FeatureModelPackage#getFeatureOrder()
 * @model
 * @generated
 */
public interface FeatureOrder extends EObject {
	/**
	 * Returns the value of the '<em><b>User Defined</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Defined</em>' attribute.
	 * @see #setUserDefined(boolean)
	 * @see FeatureModel.FeatureModelPackage#getFeatureOrder_UserDefined()
	 * @model
	 * @generated
	 */
	boolean isUserDefined();

	/**
	 * Sets the value of the '{@link FeatureModel.FeatureOrder#isUserDefined <em>User Defined</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Defined</em>' attribute.
	 * @see #isUserDefined()
	 * @generated
	 */
	void setUserDefined(boolean value);

} // FeatureOrder

/**
 */
package FeatureModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature Modell</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link FeatureModel.FeatureModell#getProperties <em>Properties</em>}</li>
 *   <li>{@link FeatureModel.FeatureModell#getStruct <em>Struct</em>}</li>
 *   <li>{@link FeatureModel.FeatureModell#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link FeatureModel.FeatureModell#getCalculations <em>Calculations</em>}</li>
 *   <li>{@link FeatureModel.FeatureModell#getComments <em>Comments</em>}</li>
 *   <li>{@link FeatureModel.FeatureModell#getFeatureOrder <em>Feature Order</em>}</li>
 * </ul>
 *
 * @see FeatureModel.FeatureModelPackage#getFeatureModell()
 * @model
 * @generated
 */
public interface FeatureModell extends EObject {
	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference.
	 * @see #setProperties(Properties)
	 * @see FeatureModel.FeatureModelPackage#getFeatureModell_Properties()
	 * @model containment="true"
	 * @generated
	 */
	Properties getProperties();

	/**
	 * Sets the value of the '{@link FeatureModel.FeatureModell#getProperties <em>Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Properties</em>' containment reference.
	 * @see #getProperties()
	 * @generated
	 */
	void setProperties(Properties value);

	/**
	 * Returns the value of the '<em><b>Struct</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Struct</em>' containment reference.
	 * @see #setStruct(Struct)
	 * @see FeatureModel.FeatureModelPackage#getFeatureModell_Struct()
	 * @model containment="true"
	 * @generated
	 */
	Struct getStruct();

	/**
	 * Sets the value of the '{@link FeatureModel.FeatureModell#getStruct <em>Struct</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Struct</em>' containment reference.
	 * @see #getStruct()
	 * @generated
	 */
	void setStruct(Struct value);

	/**
	 * Returns the value of the '<em><b>Constraints</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraints</em>' containment reference.
	 * @see #setConstraints(Constraints)
	 * @see FeatureModel.FeatureModelPackage#getFeatureModell_Constraints()
	 * @model containment="true"
	 * @generated
	 */
	Constraints getConstraints();

	/**
	 * Sets the value of the '{@link FeatureModel.FeatureModell#getConstraints <em>Constraints</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Constraints</em>' containment reference.
	 * @see #getConstraints()
	 * @generated
	 */
	void setConstraints(Constraints value);

	/**
	 * Returns the value of the '<em><b>Calculations</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Calculations</em>' reference.
	 * @see #setCalculations(Calculations)
	 * @see FeatureModel.FeatureModelPackage#getFeatureModell_Calculations()
	 * @model
	 * @generated
	 */
	Calculations getCalculations();

	/**
	 * Sets the value of the '{@link FeatureModel.FeatureModell#getCalculations <em>Calculations</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Calculations</em>' reference.
	 * @see #getCalculations()
	 * @generated
	 */
	void setCalculations(Calculations value);

	/**
	 * Returns the value of the '<em><b>Comments</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comments</em>' reference.
	 * @see #setComments(Comments)
	 * @see FeatureModel.FeatureModelPackage#getFeatureModell_Comments()
	 * @model
	 * @generated
	 */
	Comments getComments();

	/**
	 * Sets the value of the '{@link FeatureModel.FeatureModell#getComments <em>Comments</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comments</em>' reference.
	 * @see #getComments()
	 * @generated
	 */
	void setComments(Comments value);

	/**
	 * Returns the value of the '<em><b>Feature Order</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature Order</em>' reference.
	 * @see #setFeatureOrder(FeatureOrder)
	 * @see FeatureModel.FeatureModelPackage#getFeatureModell_FeatureOrder()
	 * @model
	 * @generated
	 */
	FeatureOrder getFeatureOrder();

	/**
	 * Sets the value of the '{@link FeatureModel.FeatureModell#getFeatureOrder <em>Feature Order</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature Order</em>' reference.
	 * @see #getFeatureOrder()
	 * @generated
	 */
	void setFeatureOrder(FeatureOrder value);

} // FeatureModell

/**
 */
package FeatureModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Struct</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link FeatureModel.Struct#getAnd <em>And</em>}</li>
 *   <li>{@link FeatureModel.Struct#getOr <em>Or</em>}</li>
 *   <li>{@link FeatureModel.Struct#getFeature <em>Feature</em>}</li>
 *   <li>{@link FeatureModel.Struct#getAlt <em>Alt</em>}</li>
 * </ul>
 *
 * @see FeatureModel.FeatureModelPackage#getStruct()
 * @model
 * @generated
 */
public interface Struct extends EObject {
	/**
	 * Returns the value of the '<em><b>And</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>And</em>' containment reference.
	 * @see #setAnd(Feature)
	 * @see FeatureModel.FeatureModelPackage#getStruct_And()
	 * @model containment="true"
	 * @generated
	 */
	Feature getAnd();

	/**
	 * Sets the value of the '{@link FeatureModel.Struct#getAnd <em>And</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>And</em>' containment reference.
	 * @see #getAnd()
	 * @generated
	 */
	void setAnd(Feature value);

	/**
	 * Returns the value of the '<em><b>Or</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Or</em>' containment reference.
	 * @see #setOr(Feature)
	 * @see FeatureModel.FeatureModelPackage#getStruct_Or()
	 * @model containment="true"
	 * @generated
	 */
	Feature getOr();

	/**
	 * Sets the value of the '{@link FeatureModel.Struct#getOr <em>Or</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Or</em>' containment reference.
	 * @see #getOr()
	 * @generated
	 */
	void setOr(Feature value);

	/**
	 * Returns the value of the '<em><b>Feature</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature</em>' containment reference.
	 * @see #setFeature(Feature)
	 * @see FeatureModel.FeatureModelPackage#getStruct_Feature()
	 * @model containment="true"
	 * @generated
	 */
	Feature getFeature();

	/**
	 * Sets the value of the '{@link FeatureModel.Struct#getFeature <em>Feature</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature</em>' containment reference.
	 * @see #getFeature()
	 * @generated
	 */
	void setFeature(Feature value);

	/**
	 * Returns the value of the '<em><b>Alt</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alt</em>' containment reference.
	 * @see #setAlt(Feature)
	 * @see FeatureModel.FeatureModelPackage#getStruct_Alt()
	 * @model containment="true"
	 * @generated
	 */
	Feature getAlt();

	/**
	 * Sets the value of the '{@link FeatureModel.Struct#getAlt <em>Alt</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alt</em>' containment reference.
	 * @see #getAlt()
	 * @generated
	 */
	void setAlt(Feature value);

} // Struct

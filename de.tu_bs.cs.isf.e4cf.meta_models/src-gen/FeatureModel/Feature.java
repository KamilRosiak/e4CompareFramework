/**
 */
package FeatureModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link FeatureModel.Feature#getName <em>Name</em>}</li>
 *   <li>{@link FeatureModel.Feature#isMandatory <em>Mandatory</em>}</li>
 *   <li>{@link FeatureModel.Feature#getAnd <em>And</em>}</li>
 *   <li>{@link FeatureModel.Feature#getOr <em>Or</em>}</li>
 *   <li>{@link FeatureModel.Feature#getFeature <em>Feature</em>}</li>
 *   <li>{@link FeatureModel.Feature#isHidden <em>Hidden</em>}</li>
 *   <li>{@link FeatureModel.Feature#isAbstract <em>Abstract</em>}</li>
 *   <li>{@link FeatureModel.Feature#getAlt <em>Alt</em>}</li>
 *   <li>{@link FeatureModel.Feature#getDescription <em>Description</em>}</li>
 *   <li>{@link FeatureModel.Feature#getGraphics <em>Graphics</em>}</li>
 * </ul>
 *
 * @see FeatureModel.FeatureModelPackage#getFeature()
 * @model
 * @generated
 */
public interface Feature extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see FeatureModel.FeatureModelPackage#getFeature_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link FeatureModel.Feature#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Mandatory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mandatory</em>' attribute.
	 * @see #setMandatory(boolean)
	 * @see FeatureModel.FeatureModelPackage#getFeature_Mandatory()
	 * @model
	 * @generated
	 */
	boolean isMandatory();

	/**
	 * Sets the value of the '{@link FeatureModel.Feature#isMandatory <em>Mandatory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mandatory</em>' attribute.
	 * @see #isMandatory()
	 * @generated
	 */
	void setMandatory(boolean value);

	/**
	 * Returns the value of the '<em><b>And</b></em>' containment reference list.
	 * The list contents are of type {@link FeatureModel.Feature}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>And</em>' containment reference list.
	 * @see FeatureModel.FeatureModelPackage#getFeature_And()
	 * @model containment="true"
	 * @generated
	 */
	EList<Feature> getAnd();

	/**
	 * Returns the value of the '<em><b>Or</b></em>' containment reference list.
	 * The list contents are of type {@link FeatureModel.Feature}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Or</em>' containment reference list.
	 * @see FeatureModel.FeatureModelPackage#getFeature_Or()
	 * @model containment="true"
	 * @generated
	 */
	EList<Feature> getOr();

	/**
	 * Returns the value of the '<em><b>Feature</b></em>' containment reference list.
	 * The list contents are of type {@link FeatureModel.Feature}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature</em>' containment reference list.
	 * @see FeatureModel.FeatureModelPackage#getFeature_Feature()
	 * @model containment="true"
	 * @generated
	 */
	EList<Feature> getFeature();

	/**
	 * Returns the value of the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hidden</em>' attribute.
	 * @see #setHidden(boolean)
	 * @see FeatureModel.FeatureModelPackage#getFeature_Hidden()
	 * @model
	 * @generated
	 */
	boolean isHidden();

	/**
	 * Sets the value of the '{@link FeatureModel.Feature#isHidden <em>Hidden</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hidden</em>' attribute.
	 * @see #isHidden()
	 * @generated
	 */
	void setHidden(boolean value);

	/**
	 * Returns the value of the '<em><b>Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abstract</em>' attribute.
	 * @see #setAbstract(boolean)
	 * @see FeatureModel.FeatureModelPackage#getFeature_Abstract()
	 * @model
	 * @generated
	 */
	boolean isAbstract();

	/**
	 * Sets the value of the '{@link FeatureModel.Feature#isAbstract <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Abstract</em>' attribute.
	 * @see #isAbstract()
	 * @generated
	 */
	void setAbstract(boolean value);

	/**
	 * Returns the value of the '<em><b>Alt</b></em>' containment reference list.
	 * The list contents are of type {@link FeatureModel.Feature}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alt</em>' containment reference list.
	 * @see FeatureModel.FeatureModelPackage#getFeature_Alt()
	 * @model containment="true"
	 * @generated
	 */
	EList<Feature> getAlt();

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see FeatureModel.FeatureModelPackage#getFeature_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link FeatureModel.Feature#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Graphics</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Graphics</em>' reference.
	 * @see #setGraphics(Graphic)
	 * @see FeatureModel.FeatureModelPackage#getFeature_Graphics()
	 * @model
	 * @generated
	 */
	Graphic getGraphics();

	/**
	 * Sets the value of the '{@link FeatureModel.Feature#getGraphics <em>Graphics</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Graphics</em>' reference.
	 * @see #getGraphics()
	 * @generated
	 */
	void setGraphics(Graphic value);

} // Feature

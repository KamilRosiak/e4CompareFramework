/**
 */
package FeatureModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Properties</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link FeatureModel.Properties#getGraphics <em>Graphics</em>}</li>
 * </ul>
 *
 * @see FeatureModel.FeatureModelPackage#getProperties()
 * @model
 * @generated
 */
public interface Properties extends EObject {
	/**
	 * Returns the value of the '<em><b>Graphics</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Graphics</em>' reference.
	 * @see #setGraphics(Graphic)
	 * @see FeatureModel.FeatureModelPackage#getProperties_Graphics()
	 * @model
	 * @generated
	 */
	Graphic getGraphics();

	/**
	 * Sets the value of the '{@link FeatureModel.Properties#getGraphics <em>Graphics</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Graphics</em>' reference.
	 * @see #getGraphics()
	 * @generated
	 */
	void setGraphics(Graphic value);

} // Properties

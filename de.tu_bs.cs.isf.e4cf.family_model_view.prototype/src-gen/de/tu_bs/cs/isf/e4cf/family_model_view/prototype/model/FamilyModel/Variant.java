/**
 */
package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variant</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant#getInstance <em>Instance</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant#getIdentifier <em>Identifier</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelPackage#getVariant()
 * @model
 * @generated
 */
public interface Variant extends EObject {
	/**
	 * Returns the value of the '<em><b>Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instance</em>' reference.
	 * @see #setInstance(EObject)
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelPackage#getVariant_Instance()
	 * @model
	 * @generated
	 */
	EObject getInstance();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant#getInstance <em>Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instance</em>' reference.
	 * @see #getInstance()
	 * @generated
	 */
	void setInstance(EObject value);

	/**
	 * Returns the value of the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Identifier</em>' attribute.
	 * @see #setIdentifier(String)
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelPackage#getVariant_Identifier()
	 * @model
	 * @generated
	 */
	String getIdentifier();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant#getIdentifier <em>Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Identifier</em>' attribute.
	 * @see #getIdentifier()
	 * @generated
	 */
	void setIdentifier(String value);

} // Variant

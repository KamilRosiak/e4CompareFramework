/**
 */
package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variant Artefact</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariantArtefact#getArtefacts <em>Artefacts</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariantArtefact#getName <em>Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariantArtefact#getOrigins <em>Origins</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelPackage#getVariantArtefact()
 * @model
 * @generated
 */
public interface VariantArtefact extends EObject {
	/**
	 * Returns the value of the '<em><b>Artefacts</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Artefacts</em>' reference list.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelPackage#getVariantArtefact_Artefacts()
	 * @model required="true"
	 * @generated
	 */
	EList<EObject> getArtefacts();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelPackage#getVariantArtefact_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariantArtefact#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Origins</b></em>' reference list.
	 * The list contents are of type {@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Origins</em>' reference list.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelPackage#getVariantArtefact_Origins()
	 * @model
	 * @generated
	 */
	EList<Variant> getOrigins();

} // VariantArtefact

/**
 */
package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variation Point</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint#getVariantArtefacts <em>Variant Artefacts</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint#getChildren <em>Children</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint#getParent <em>Parent</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint#getVariabilityCategory <em>Variability Category</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelPackage#getVariationPoint()
 * @model
 * @generated
 */
public interface VariationPoint extends EObject {
	/**
	 * Returns the value of the '<em><b>Variant Artefacts</b></em>' containment reference list.
	 * The list contents are of type {@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariantArtefact}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variant Artefacts</em>' containment reference list.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelPackage#getVariationPoint_VariantArtefacts()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<VariantArtefact> getVariantArtefacts();

	/**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelPackage#getVariationPoint_Children()
	 * @model containment="true"
	 * @generated
	 */
	EList<VariationPoint> getChildren();

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(VariationPoint)
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelPackage#getVariationPoint_Parent()
	 * @model
	 * @generated
	 */
	VariationPoint getParent();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(VariationPoint value);

	/**
	 * Returns the value of the '<em><b>Variability Category</b></em>' attribute.
	 * The default value is <code>"UNSET"</code>.
	 * The literals are from the enumeration {@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariabilityCategory}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variability Category</em>' attribute.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariabilityCategory
	 * @see #setVariabilityCategory(VariabilityCategory)
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelPackage#getVariationPoint_VariabilityCategory()
	 * @model default="UNSET"
	 * @generated
	 */
	VariabilityCategory getVariabilityCategory();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint#getVariabilityCategory <em>Variability Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variability Category</em>' attribute.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariabilityCategory
	 * @see #getVariabilityCategory()
	 * @generated
	 */
	void setVariabilityCategory(VariabilityCategory value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelPackage#getVariationPoint_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // VariationPoint

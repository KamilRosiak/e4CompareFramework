/**
 */
package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Wheel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Wheel#getTraction <em>Traction</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Wheel#getModel <em>Model</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.CarPackage#getWheel()
 * @model
 * @generated
 */
public interface Wheel extends EObject {
	/**
	 * Returns the value of the '<em><b>Traction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Traction</em>' attribute.
	 * @see #setTraction(int)
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.CarPackage#getWheel_Traction()
	 * @model
	 * @generated
	 */
	int getTraction();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Wheel#getTraction <em>Traction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Traction</em>' attribute.
	 * @see #getTraction()
	 * @generated
	 */
	void setTraction(int value);

	/**
	 * Returns the value of the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model</em>' attribute.
	 * @see #setModel(String)
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.CarPackage#getWheel_Model()
	 * @model
	 * @generated
	 */
	String getModel();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Wheel#getModel <em>Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model</em>' attribute.
	 * @see #getModel()
	 * @generated
	 */
	void setModel(String value);

} // Wheel

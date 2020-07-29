/**
 */
package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Car</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Car#getLabel <em>Label</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Car#getWheels <em>Wheels</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.CarPackage#getCar()
 * @model
 * @generated
 */
public interface Car extends EObject {
	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.CarPackage#getCar_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Car#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Wheels</b></em>' containment reference list.
	 * The list contents are of type {@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Wheel}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Wheels</em>' containment reference list.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.CarPackage#getCar_Wheels()
	 * @model containment="true" lower="4" upper="4"
	 * @generated
	 */
	EList<Wheel> getWheels();

} // Car

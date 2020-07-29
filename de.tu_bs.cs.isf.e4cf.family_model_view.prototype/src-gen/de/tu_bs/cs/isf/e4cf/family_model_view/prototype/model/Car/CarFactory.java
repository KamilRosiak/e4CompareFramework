/**
 */
package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.CarPackage
 * @generated
 */
public interface CarFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CarFactory eINSTANCE = de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.impl.CarFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Car</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Car</em>'.
	 * @generated
	 */
	Car createCar();

	/**
	 * Returns a new object of class '<em>Wheel</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Wheel</em>'.
	 * @generated
	 */
	Wheel createWheel();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CarPackage getCarPackage();

} //CarFactory

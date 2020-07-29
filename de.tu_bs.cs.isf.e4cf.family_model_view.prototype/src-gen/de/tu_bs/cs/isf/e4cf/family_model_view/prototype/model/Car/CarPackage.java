/**
 */
package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.CarFactory
 * @model kind="package"
 * @generated
 */
public interface CarPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "Car";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "www.example.com/Car";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "car";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CarPackage eINSTANCE = de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.impl.CarPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.impl.CarImpl <em>Car</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.impl.CarImpl
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.impl.CarPackageImpl#getCar()
	 * @generated
	 */
	int CAR = 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAR__LABEL = 0;

	/**
	 * The feature id for the '<em><b>Wheels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAR__WHEELS = 1;

	/**
	 * The number of structural features of the '<em>Car</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAR_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Car</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.impl.WheelImpl <em>Wheel</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.impl.WheelImpl
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.impl.CarPackageImpl#getWheel()
	 * @generated
	 */
	int WHEEL = 1;

	/**
	 * The feature id for the '<em><b>Traction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WHEEL__TRACTION = 0;

	/**
	 * The feature id for the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WHEEL__MODEL = 1;

	/**
	 * The number of structural features of the '<em>Wheel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WHEEL_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Wheel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WHEEL_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Car <em>Car</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Car</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Car
	 * @generated
	 */
	EClass getCar();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Car#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Car#getLabel()
	 * @see #getCar()
	 * @generated
	 */
	EAttribute getCar_Label();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Car#getWheels <em>Wheels</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Wheels</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Car#getWheels()
	 * @see #getCar()
	 * @generated
	 */
	EReference getCar_Wheels();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Wheel <em>Wheel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Wheel</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Wheel
	 * @generated
	 */
	EClass getWheel();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Wheel#getTraction <em>Traction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Traction</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Wheel#getTraction()
	 * @see #getWheel()
	 * @generated
	 */
	EAttribute getWheel_Traction();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Wheel#getModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Model</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Wheel#getModel()
	 * @see #getWheel()
	 * @generated
	 */
	EAttribute getWheel_Model();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CarFactory getCarFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.impl.CarImpl <em>Car</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.impl.CarImpl
		 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.impl.CarPackageImpl#getCar()
		 * @generated
		 */
		EClass CAR = eINSTANCE.getCar();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CAR__LABEL = eINSTANCE.getCar_Label();

		/**
		 * The meta object literal for the '<em><b>Wheels</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CAR__WHEELS = eINSTANCE.getCar_Wheels();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.impl.WheelImpl <em>Wheel</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.impl.WheelImpl
		 * @see de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.impl.CarPackageImpl#getWheel()
		 * @generated
		 */
		EClass WHEEL = eINSTANCE.getWheel();

		/**
		 * The meta object literal for the '<em><b>Traction</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WHEEL__TRACTION = eINSTANCE.getWheel_Traction();

		/**
		 * The meta object literal for the '<em><b>Model</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WHEEL__MODEL = eINSTANCE.getWheel_Model();

	}

} //CarPackage

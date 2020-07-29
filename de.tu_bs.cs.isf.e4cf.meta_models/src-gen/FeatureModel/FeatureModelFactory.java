/**
 */
package FeatureModel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see FeatureModel.FeatureModelPackage
 * @generated
 */
public interface FeatureModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FeatureModelFactory eINSTANCE = FeatureModel.impl.FeatureModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Feature Modell</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Feature Modell</em>'.
	 * @generated
	 */
	FeatureModell createFeatureModell();

	/**
	 * Returns a new object of class '<em>Properties</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Properties</em>'.
	 * @generated
	 */
	Properties createProperties();

	/**
	 * Returns a new object of class '<em>Graphic</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Graphic</em>'.
	 * @generated
	 */
	Graphic createGraphic();

	/**
	 * Returns a new object of class '<em>Struct</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Struct</em>'.
	 * @generated
	 */
	Struct createStruct();

	/**
	 * Returns a new object of class '<em>Feature</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Feature</em>'.
	 * @generated
	 */
	Feature createFeature();

	/**
	 * Returns a new object of class '<em>Constraints</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Constraints</em>'.
	 * @generated
	 */
	Constraints createConstraints();

	/**
	 * Returns a new object of class '<em>Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule</em>'.
	 * @generated
	 */
	Rule createRule();

	/**
	 * Returns a new object of class '<em>Opearation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Opearation</em>'.
	 * @generated
	 */
	Opearation createOpearation();

	/**
	 * Returns a new object of class '<em>Calculations</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Calculations</em>'.
	 * @generated
	 */
	Calculations createCalculations();

	/**
	 * Returns a new object of class '<em>Comments</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Comments</em>'.
	 * @generated
	 */
	Comments createComments();

	/**
	 * Returns a new object of class '<em>C</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>C</em>'.
	 * @generated
	 */
	C createC();

	/**
	 * Returns a new object of class '<em>Feature Order</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Feature Order</em>'.
	 * @generated
	 */
	FeatureOrder createFeatureOrder();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	FeatureModelPackage getFeatureModelPackage();

} //FeatureModelFactory

/**
 */
package FeatureModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Calculations</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link FeatureModel.Calculations#isAuto <em>Auto</em>}</li>
 *   <li>{@link FeatureModel.Calculations#isConstraints <em>Constraints</em>}</li>
 *   <li>{@link FeatureModel.Calculations#isFeatures <em>Features</em>}</li>
 *   <li>{@link FeatureModel.Calculations#isRedundant <em>Redundant</em>}</li>
 *   <li>{@link FeatureModel.Calculations#isTautology <em>Tautology</em>}</li>
 * </ul>
 *
 * @see FeatureModel.FeatureModelPackage#getCalculations()
 * @model
 * @generated
 */
public interface Calculations extends EObject {
	/**
	 * Returns the value of the '<em><b>Auto</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auto</em>' attribute.
	 * @see #setAuto(boolean)
	 * @see FeatureModel.FeatureModelPackage#getCalculations_Auto()
	 * @model
	 * @generated
	 */
	boolean isAuto();

	/**
	 * Sets the value of the '{@link FeatureModel.Calculations#isAuto <em>Auto</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Auto</em>' attribute.
	 * @see #isAuto()
	 * @generated
	 */
	void setAuto(boolean value);

	/**
	 * Returns the value of the '<em><b>Constraints</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraints</em>' attribute.
	 * @see #setConstraints(boolean)
	 * @see FeatureModel.FeatureModelPackage#getCalculations_Constraints()
	 * @model
	 * @generated
	 */
	boolean isConstraints();

	/**
	 * Sets the value of the '{@link FeatureModel.Calculations#isConstraints <em>Constraints</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Constraints</em>' attribute.
	 * @see #isConstraints()
	 * @generated
	 */
	void setConstraints(boolean value);

	/**
	 * Returns the value of the '<em><b>Features</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Features</em>' attribute.
	 * @see #setFeatures(boolean)
	 * @see FeatureModel.FeatureModelPackage#getCalculations_Features()
	 * @model
	 * @generated
	 */
	boolean isFeatures();

	/**
	 * Sets the value of the '{@link FeatureModel.Calculations#isFeatures <em>Features</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Features</em>' attribute.
	 * @see #isFeatures()
	 * @generated
	 */
	void setFeatures(boolean value);

	/**
	 * Returns the value of the '<em><b>Redundant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Redundant</em>' attribute.
	 * @see #setRedundant(boolean)
	 * @see FeatureModel.FeatureModelPackage#getCalculations_Redundant()
	 * @model
	 * @generated
	 */
	boolean isRedundant();

	/**
	 * Sets the value of the '{@link FeatureModel.Calculations#isRedundant <em>Redundant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Redundant</em>' attribute.
	 * @see #isRedundant()
	 * @generated
	 */
	void setRedundant(boolean value);

	/**
	 * Returns the value of the '<em><b>Tautology</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tautology</em>' attribute.
	 * @see #setTautology(boolean)
	 * @see FeatureModel.FeatureModelPackage#getCalculations_Tautology()
	 * @model
	 * @generated
	 */
	boolean isTautology();

	/**
	 * Sets the value of the '{@link FeatureModel.Calculations#isTautology <em>Tautology</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tautology</em>' attribute.
	 * @see #isTautology()
	 * @generated
	 */
	void setTautology(boolean value);

} // Calculations

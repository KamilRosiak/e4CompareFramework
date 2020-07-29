/**
 */
package familyModel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see familyModel.FamilyModelPackage
 * @generated
 */
public interface FamilyModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FamilyModelFactory eINSTANCE = familyModel.impl.FamilyModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Family Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Family Model</em>'.
	 * @generated
	 */
	FamilyModel createFamilyModel();

	/**
	 * Returns a new object of class '<em>Variability Group</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Variability Group</em>'.
	 * @generated
	 */
	VariabilityGroup createVariabilityGroup();

	/**
	 * Returns a new object of class '<em>Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Element</em>'.
	 * @generated
	 */
	Element createElement();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	FamilyModelPackage getFamilyModelPackage();

} //FamilyModelFactory

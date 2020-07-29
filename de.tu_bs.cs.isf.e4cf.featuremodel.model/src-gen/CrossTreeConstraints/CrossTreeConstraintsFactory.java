/**
 */
package CrossTreeConstraints;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see CrossTreeConstraints.CrossTreeConstraintsPackage
 * @generated
 */
public interface CrossTreeConstraintsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CrossTreeConstraintsFactory eINSTANCE = CrossTreeConstraints.impl.CrossTreeConstraintsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Formula</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Formula</em>'.
	 * @generated
	 */
	Formula createFormula();

	/**
	 * Returns a new object of class '<em>Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Literal</em>'.
	 * @generated
	 */
	Literal createLiteral();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CrossTreeConstraintsPackage getCrossTreeConstraintsPackage();

} //CrossTreeConstraintsFactory

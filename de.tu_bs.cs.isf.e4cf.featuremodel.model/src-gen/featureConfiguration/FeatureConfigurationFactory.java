/**
 */
package featureConfiguration;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see featureConfiguration.FeatureConfigurationPackage
 * @generated
 */
public interface FeatureConfigurationFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FeatureConfigurationFactory eINSTANCE = featureConfiguration.impl.FeatureConfigurationFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Feature Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Feature Configuration</em>'.
	 * @generated
	 */
	FeatureConfiguration createFeatureConfiguration();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	FeatureConfigurationPackage getFeatureConfigurationPackage();

} //FeatureConfigurationFactory

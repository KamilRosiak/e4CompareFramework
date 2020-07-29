/**
 */
package FeatureDiagramModificationSet;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see FeatureDiagramModificationSet.FeatureDiagramModificationSetPackage
 * @generated
 */
public interface FeatureDiagramModificationSetFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FeatureDiagramModificationSetFactory eINSTANCE = FeatureDiagramModificationSet.impl.FeatureDiagramModificationSetFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Feature Model Modification Set</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Feature Model Modification Set</em>'.
	 * @generated
	 */
	FeatureModelModificationSet createFeatureModelModificationSet();

	/**
	 * Returns a new object of class '<em>Modification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Modification</em>'.
	 * @generated
	 */
	Modification createModification();

	/**
	 * Returns a new object of class '<em>Delta</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Delta</em>'.
	 * @generated
	 */
	Delta createDelta();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	FeatureDiagramModificationSetPackage getFeatureDiagramModificationSetPackage();

} //FeatureDiagramModificationSetFactory

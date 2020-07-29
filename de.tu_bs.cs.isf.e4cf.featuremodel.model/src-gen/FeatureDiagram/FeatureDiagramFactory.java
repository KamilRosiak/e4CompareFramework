/**
 */
package FeatureDiagram;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see FeatureDiagram.FeatureDiagramPackage
 * @generated
 */
public interface FeatureDiagramFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FeatureDiagramFactory eINSTANCE = FeatureDiagram.impl.FeatureDiagramFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Feature Diagramm</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Feature Diagramm</em>'.
	 * @generated
	 */
	FeatureDiagramm createFeatureDiagramm();

	/**
	 * Returns a new object of class '<em>Feature</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Feature</em>'.
	 * @generated
	 */
	Feature createFeature();

	/**
	 * Returns a new object of class '<em>Graphical Feature</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Graphical Feature</em>'.
	 * @generated
	 */
	GraphicalFeature createGraphicalFeature();

	/**
	 * Returns a new object of class '<em>Artifact Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Artifact Reference</em>'.
	 * @generated
	 */
	ArtifactReference createArtifactReference();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	FeatureDiagramPackage getFeatureDiagramPackage();

} //FeatureDiagramFactory

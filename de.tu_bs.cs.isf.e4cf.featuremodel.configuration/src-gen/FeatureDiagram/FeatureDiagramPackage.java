/**
 */
package FeatureDiagram;

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
 * @see FeatureDiagram.FeatureDiagramFactory
 * @model kind="package"
 * @generated
 */
public interface FeatureDiagramPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "FeatureDiagram";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "www.example.com/featureDiagram";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "FMD";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FeatureDiagramPackage eINSTANCE = FeatureDiagram.impl.FeatureDiagramPackageImpl.init();

	/**
	 * The meta object id for the '{@link FeatureDiagram.impl.FeatureDiagrammImpl <em>Feature Diagramm</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see FeatureDiagram.impl.FeatureDiagrammImpl
	 * @see FeatureDiagram.impl.FeatureDiagramPackageImpl#getFeatureDiagramm()
	 * @generated
	 */
	int FEATURE_DIAGRAMM = 0;

	/**
	 * The feature id for the '<em><b>Root</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_DIAGRAMM__ROOT = 0;

	/**
	 * The feature id for the '<em><b>Identifier Increment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_DIAGRAMM__IDENTIFIER_INCREMENT = 1;

	/**
	 * The feature id for the '<em><b>Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_DIAGRAMM__CONSTRAINTS = 2;

	/**
	 * The number of structural features of the '<em>Feature Diagramm</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_DIAGRAMM_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Feature Diagramm</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_DIAGRAMM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link FeatureDiagram.impl.FeatureImpl <em>Feature</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see FeatureDiagram.impl.FeatureImpl
	 * @see FeatureDiagram.impl.FeatureDiagramPackageImpl#getFeature()
	 * @generated
	 */
	int FEATURE = 1;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__CHILDREN = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__NAME = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Mandatory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__MANDATORY = 3;

	/**
	 * The feature id for the '<em><b>Alternative</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__ALTERNATIVE = 4;

	/**
	 * The feature id for the '<em><b>Or</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__OR = 5;

	/**
	 * The feature id for the '<em><b>Graphicalfeature</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__GRAPHICALFEATURE = 6;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__PARENT = 7;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__IDENTIFIER = 8;

	/**
	 * The feature id for the '<em><b>Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__ABSTRACT = 9;

	/**
	 * The feature id for the '<em><b>Hidden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__HIDDEN = 10;

	/**
	 * The feature id for the '<em><b>Artifact References</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__ARTIFACT_REFERENCES = 11;

	/**
	 * The number of structural features of the '<em>Feature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_FEATURE_COUNT = 12;

	/**
	 * The number of operations of the '<em>Feature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link FeatureDiagram.impl.GraphicalFeatureImpl <em>Graphical Feature</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see FeatureDiagram.impl.GraphicalFeatureImpl
	 * @see FeatureDiagram.impl.FeatureDiagramPackageImpl#getGraphicalFeature()
	 * @generated
	 */
	int GRAPHICAL_FEATURE = 2;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPHICAL_FEATURE__X = 0;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPHICAL_FEATURE__Y = 1;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPHICAL_FEATURE__WIDTH = 2;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPHICAL_FEATURE__HEIGHT = 3;

	/**
	 * The number of structural features of the '<em>Graphical Feature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPHICAL_FEATURE_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Graphical Feature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPHICAL_FEATURE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link FeatureDiagram.impl.ArtifactReferenceImpl <em>Artifact Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see FeatureDiagram.impl.ArtifactReferenceImpl
	 * @see FeatureDiagram.impl.FeatureDiagramPackageImpl#getArtifactReference()
	 * @generated
	 */
	int ARTIFACT_REFERENCE = 3;

	/**
	 * The feature id for the '<em><b>Artifact Clear Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_REFERENCE__ARTIFACT_CLEAR_NAME = 0;

	/**
	 * The feature id for the '<em><b>Referenced Artifact IDs</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_REFERENCE__REFERENCED_ARTIFACT_IDS = 1;

	/**
	 * The number of structural features of the '<em>Artifact Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_REFERENCE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Artifact Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_REFERENCE_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link FeatureDiagram.FeatureDiagramm <em>Feature Diagramm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature Diagramm</em>'.
	 * @see FeatureDiagram.FeatureDiagramm
	 * @generated
	 */
	EClass getFeatureDiagramm();

	/**
	 * Returns the meta object for the containment reference '{@link FeatureDiagram.FeatureDiagramm#getRoot <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Root</em>'.
	 * @see FeatureDiagram.FeatureDiagramm#getRoot()
	 * @see #getFeatureDiagramm()
	 * @generated
	 */
	EReference getFeatureDiagramm_Root();

	/**
	 * Returns the meta object for the attribute '{@link FeatureDiagram.FeatureDiagramm#getIdentifierIncrement <em>Identifier Increment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Identifier Increment</em>'.
	 * @see FeatureDiagram.FeatureDiagramm#getIdentifierIncrement()
	 * @see #getFeatureDiagramm()
	 * @generated
	 */
	EAttribute getFeatureDiagramm_IdentifierIncrement();

	/**
	 * Returns the meta object for the containment reference list '{@link FeatureDiagram.FeatureDiagramm#getConstraints <em>Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constraints</em>'.
	 * @see FeatureDiagram.FeatureDiagramm#getConstraints()
	 * @see #getFeatureDiagramm()
	 * @generated
	 */
	EReference getFeatureDiagramm_Constraints();

	/**
	 * Returns the meta object for class '{@link FeatureDiagram.Feature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature</em>'.
	 * @see FeatureDiagram.Feature
	 * @generated
	 */
	EClass getFeature();

	/**
	 * Returns the meta object for the containment reference list '{@link FeatureDiagram.Feature#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see FeatureDiagram.Feature#getChildren()
	 * @see #getFeature()
	 * @generated
	 */
	EReference getFeature_Children();

	/**
	 * Returns the meta object for the attribute '{@link FeatureDiagram.Feature#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see FeatureDiagram.Feature#getName()
	 * @see #getFeature()
	 * @generated
	 */
	EAttribute getFeature_Name();

	/**
	 * Returns the meta object for the attribute '{@link FeatureDiagram.Feature#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see FeatureDiagram.Feature#getDescription()
	 * @see #getFeature()
	 * @generated
	 */
	EAttribute getFeature_Description();

	/**
	 * Returns the meta object for the attribute '{@link FeatureDiagram.Feature#isMandatory <em>Mandatory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mandatory</em>'.
	 * @see FeatureDiagram.Feature#isMandatory()
	 * @see #getFeature()
	 * @generated
	 */
	EAttribute getFeature_Mandatory();

	/**
	 * Returns the meta object for the attribute '{@link FeatureDiagram.Feature#isAlternative <em>Alternative</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Alternative</em>'.
	 * @see FeatureDiagram.Feature#isAlternative()
	 * @see #getFeature()
	 * @generated
	 */
	EAttribute getFeature_Alternative();

	/**
	 * Returns the meta object for the attribute '{@link FeatureDiagram.Feature#isOr <em>Or</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Or</em>'.
	 * @see FeatureDiagram.Feature#isOr()
	 * @see #getFeature()
	 * @generated
	 */
	EAttribute getFeature_Or();

	/**
	 * Returns the meta object for the containment reference '{@link FeatureDiagram.Feature#getGraphicalfeature <em>Graphicalfeature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Graphicalfeature</em>'.
	 * @see FeatureDiagram.Feature#getGraphicalfeature()
	 * @see #getFeature()
	 * @generated
	 */
	EReference getFeature_Graphicalfeature();

	/**
	 * Returns the meta object for the reference '{@link FeatureDiagram.Feature#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see FeatureDiagram.Feature#getParent()
	 * @see #getFeature()
	 * @generated
	 */
	EReference getFeature_Parent();

	/**
	 * Returns the meta object for the attribute '{@link FeatureDiagram.Feature#getIdentifier <em>Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Identifier</em>'.
	 * @see FeatureDiagram.Feature#getIdentifier()
	 * @see #getFeature()
	 * @generated
	 */
	EAttribute getFeature_Identifier();

	/**
	 * Returns the meta object for the attribute '{@link FeatureDiagram.Feature#isAbstract <em>Abstract</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Abstract</em>'.
	 * @see FeatureDiagram.Feature#isAbstract()
	 * @see #getFeature()
	 * @generated
	 */
	EAttribute getFeature_Abstract();

	/**
	 * Returns the meta object for the attribute '{@link FeatureDiagram.Feature#isHidden <em>Hidden</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hidden</em>'.
	 * @see FeatureDiagram.Feature#isHidden()
	 * @see #getFeature()
	 * @generated
	 */
	EAttribute getFeature_Hidden();

	/**
	 * Returns the meta object for the containment reference list '{@link FeatureDiagram.Feature#getArtifactReferences <em>Artifact References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Artifact References</em>'.
	 * @see FeatureDiagram.Feature#getArtifactReferences()
	 * @see #getFeature()
	 * @generated
	 */
	EReference getFeature_ArtifactReferences();

	/**
	 * Returns the meta object for class '{@link FeatureDiagram.GraphicalFeature <em>Graphical Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Graphical Feature</em>'.
	 * @see FeatureDiagram.GraphicalFeature
	 * @generated
	 */
	EClass getGraphicalFeature();

	/**
	 * Returns the meta object for the attribute '{@link FeatureDiagram.GraphicalFeature#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see FeatureDiagram.GraphicalFeature#getX()
	 * @see #getGraphicalFeature()
	 * @generated
	 */
	EAttribute getGraphicalFeature_X();

	/**
	 * Returns the meta object for the attribute '{@link FeatureDiagram.GraphicalFeature#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see FeatureDiagram.GraphicalFeature#getY()
	 * @see #getGraphicalFeature()
	 * @generated
	 */
	EAttribute getGraphicalFeature_Y();

	/**
	 * Returns the meta object for the attribute '{@link FeatureDiagram.GraphicalFeature#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see FeatureDiagram.GraphicalFeature#getWidth()
	 * @see #getGraphicalFeature()
	 * @generated
	 */
	EAttribute getGraphicalFeature_Width();

	/**
	 * Returns the meta object for the attribute '{@link FeatureDiagram.GraphicalFeature#getHeight <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see FeatureDiagram.GraphicalFeature#getHeight()
	 * @see #getGraphicalFeature()
	 * @generated
	 */
	EAttribute getGraphicalFeature_Height();

	/**
	 * Returns the meta object for class '{@link FeatureDiagram.ArtifactReference <em>Artifact Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Artifact Reference</em>'.
	 * @see FeatureDiagram.ArtifactReference
	 * @generated
	 */
	EClass getArtifactReference();

	/**
	 * Returns the meta object for the attribute '{@link FeatureDiagram.ArtifactReference#getArtifactClearName <em>Artifact Clear Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Artifact Clear Name</em>'.
	 * @see FeatureDiagram.ArtifactReference#getArtifactClearName()
	 * @see #getArtifactReference()
	 * @generated
	 */
	EAttribute getArtifactReference_ArtifactClearName();

	/**
	 * Returns the meta object for the attribute list '{@link FeatureDiagram.ArtifactReference#getReferencedArtifactIDs <em>Referenced Artifact IDs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Referenced Artifact IDs</em>'.
	 * @see FeatureDiagram.ArtifactReference#getReferencedArtifactIDs()
	 * @see #getArtifactReference()
	 * @generated
	 */
	EAttribute getArtifactReference_ReferencedArtifactIDs();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	FeatureDiagramFactory getFeatureDiagramFactory();

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
		 * The meta object literal for the '{@link FeatureDiagram.impl.FeatureDiagrammImpl <em>Feature Diagramm</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see FeatureDiagram.impl.FeatureDiagrammImpl
		 * @see FeatureDiagram.impl.FeatureDiagramPackageImpl#getFeatureDiagramm()
		 * @generated
		 */
		EClass FEATURE_DIAGRAMM = eINSTANCE.getFeatureDiagramm();

		/**
		 * The meta object literal for the '<em><b>Root</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_DIAGRAMM__ROOT = eINSTANCE.getFeatureDiagramm_Root();

		/**
		 * The meta object literal for the '<em><b>Identifier Increment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_DIAGRAMM__IDENTIFIER_INCREMENT = eINSTANCE.getFeatureDiagramm_IdentifierIncrement();

		/**
		 * The meta object literal for the '<em><b>Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_DIAGRAMM__CONSTRAINTS = eINSTANCE.getFeatureDiagramm_Constraints();

		/**
		 * The meta object literal for the '{@link FeatureDiagram.impl.FeatureImpl <em>Feature</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see FeatureDiagram.impl.FeatureImpl
		 * @see FeatureDiagram.impl.FeatureDiagramPackageImpl#getFeature()
		 * @generated
		 */
		EClass FEATURE = eINSTANCE.getFeature();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE__CHILDREN = eINSTANCE.getFeature_Children();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE__NAME = eINSTANCE.getFeature_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE__DESCRIPTION = eINSTANCE.getFeature_Description();

		/**
		 * The meta object literal for the '<em><b>Mandatory</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE__MANDATORY = eINSTANCE.getFeature_Mandatory();

		/**
		 * The meta object literal for the '<em><b>Alternative</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE__ALTERNATIVE = eINSTANCE.getFeature_Alternative();

		/**
		 * The meta object literal for the '<em><b>Or</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE__OR = eINSTANCE.getFeature_Or();

		/**
		 * The meta object literal for the '<em><b>Graphicalfeature</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE__GRAPHICALFEATURE = eINSTANCE.getFeature_Graphicalfeature();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE__PARENT = eINSTANCE.getFeature_Parent();

		/**
		 * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE__IDENTIFIER = eINSTANCE.getFeature_Identifier();

		/**
		 * The meta object literal for the '<em><b>Abstract</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE__ABSTRACT = eINSTANCE.getFeature_Abstract();

		/**
		 * The meta object literal for the '<em><b>Hidden</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE__HIDDEN = eINSTANCE.getFeature_Hidden();

		/**
		 * The meta object literal for the '<em><b>Artifact References</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE__ARTIFACT_REFERENCES = eINSTANCE.getFeature_ArtifactReferences();

		/**
		 * The meta object literal for the '{@link FeatureDiagram.impl.GraphicalFeatureImpl <em>Graphical Feature</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see FeatureDiagram.impl.GraphicalFeatureImpl
		 * @see FeatureDiagram.impl.FeatureDiagramPackageImpl#getGraphicalFeature()
		 * @generated
		 */
		EClass GRAPHICAL_FEATURE = eINSTANCE.getGraphicalFeature();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRAPHICAL_FEATURE__X = eINSTANCE.getGraphicalFeature_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRAPHICAL_FEATURE__Y = eINSTANCE.getGraphicalFeature_Y();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRAPHICAL_FEATURE__WIDTH = eINSTANCE.getGraphicalFeature_Width();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRAPHICAL_FEATURE__HEIGHT = eINSTANCE.getGraphicalFeature_Height();

		/**
		 * The meta object literal for the '{@link FeatureDiagram.impl.ArtifactReferenceImpl <em>Artifact Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see FeatureDiagram.impl.ArtifactReferenceImpl
		 * @see FeatureDiagram.impl.FeatureDiagramPackageImpl#getArtifactReference()
		 * @generated
		 */
		EClass ARTIFACT_REFERENCE = eINSTANCE.getArtifactReference();

		/**
		 * The meta object literal for the '<em><b>Artifact Clear Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARTIFACT_REFERENCE__ARTIFACT_CLEAR_NAME = eINSTANCE.getArtifactReference_ArtifactClearName();

		/**
		 * The meta object literal for the '<em><b>Referenced Artifact IDs</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARTIFACT_REFERENCE__REFERENCED_ARTIFACT_IDS = eINSTANCE.getArtifactReference_ReferencedArtifactIDs();

	}

} //FeatureDiagramPackage

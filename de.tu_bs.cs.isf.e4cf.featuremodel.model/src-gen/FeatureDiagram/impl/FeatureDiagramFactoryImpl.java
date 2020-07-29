/**
 */
package FeatureDiagram.impl;

import FeatureDiagram.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class FeatureDiagramFactoryImpl extends EFactoryImpl implements FeatureDiagramFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FeatureDiagramFactory init() {
		try {
			FeatureDiagramFactory theFeatureDiagramFactory = (FeatureDiagramFactory)EPackage.Registry.INSTANCE.getEFactory(FeatureDiagramPackage.eNS_URI);
			if (theFeatureDiagramFactory != null) {
				return theFeatureDiagramFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new FeatureDiagramFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureDiagramFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case FeatureDiagramPackage.FEATURE_DIAGRAMM: return createFeatureDiagramm();
			case FeatureDiagramPackage.FEATURE: return createFeature();
			case FeatureDiagramPackage.GRAPHICAL_FEATURE: return createGraphicalFeature();
			case FeatureDiagramPackage.ARTIFACT_REFERENCE: return createArtifactReference();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureDiagramm createFeatureDiagramm() {
		FeatureDiagrammImpl featureDiagramm = new FeatureDiagrammImpl();
		return featureDiagramm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Feature createFeature() {
		FeatureImpl feature = new FeatureImpl();
		return feature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GraphicalFeature createGraphicalFeature() {
		GraphicalFeatureImpl graphicalFeature = new GraphicalFeatureImpl();
		return graphicalFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ArtifactReference createArtifactReference() {
		ArtifactReferenceImpl artifactReference = new ArtifactReferenceImpl();
		return artifactReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureDiagramPackage getFeatureDiagramPackage() {
		return (FeatureDiagramPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static FeatureDiagramPackage getPackage() {
		return FeatureDiagramPackage.eINSTANCE;
	}

} //FeatureDiagramFactoryImpl

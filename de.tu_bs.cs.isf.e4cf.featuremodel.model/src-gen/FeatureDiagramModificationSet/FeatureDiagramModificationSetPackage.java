/**
 */
package FeatureDiagramModificationSet;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see FeatureDiagramModificationSet.FeatureDiagramModificationSetFactory
 * @model kind="package"
 * @generated
 */
public interface FeatureDiagramModificationSetPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "FeatureDiagramModificationSet";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "www.example.com/featureDiagramChangeOperation";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "FDCO";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FeatureDiagramModificationSetPackage eINSTANCE = FeatureDiagramModificationSet.impl.FeatureDiagramModificationSetPackageImpl.init();

	/**
	 * The meta object id for the '{@link FeatureDiagramModificationSet.impl.FeatureModelModificationSetImpl <em>Feature Model Modification Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see FeatureDiagramModificationSet.impl.FeatureModelModificationSetImpl
	 * @see FeatureDiagramModificationSet.impl.FeatureDiagramModificationSetPackageImpl#getFeatureModelModificationSet()
	 * @generated
	 */
	int FEATURE_MODEL_MODIFICATION_SET = 0;

	/**
	 * The feature id for the '<em><b>Modifications</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MODEL_MODIFICATION_SET__MODIFICATIONS = 0;

	/**
	 * The feature id for the '<em><b>Affected Feature Model Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MODEL_MODIFICATION_SET__AFFECTED_FEATURE_MODEL_NAME = 1;

	/**
	 * The number of structural features of the '<em>Feature Model Modification Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MODEL_MODIFICATION_SET_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Feature Model Modification Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MODEL_MODIFICATION_SET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link FeatureDiagramModificationSet.impl.ModificationImpl <em>Modification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see FeatureDiagramModificationSet.impl.ModificationImpl
	 * @see FeatureDiagramModificationSet.impl.FeatureDiagramModificationSetPackageImpl#getModification()
	 * @generated
	 */
	int MODIFICATION = 1;

	/**
	 * The feature id for the '<em><b>Feature ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFICATION__FEATURE_ID = 0;

	/**
	 * The feature id for the '<em><b>Time Stamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFICATION__TIME_STAMP = 1;

	/**
	 * The feature id for the '<em><b>Delta</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFICATION__DELTA = 2;

	/**
	 * The feature id for the '<em><b>Modification Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFICATION__MODIFICATION_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Precision Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFICATION__PRECISION_TIME = 4;

	/**
	 * The feature id for the '<em><b>Referenced Artifacts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFICATION__REFERENCED_ARTIFACTS = 5;

	/**
	 * The number of structural features of the '<em>Modification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFICATION_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Modification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFICATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link FeatureDiagramModificationSet.impl.DeltaImpl <em>Delta</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see FeatureDiagramModificationSet.impl.DeltaImpl
	 * @see FeatureDiagramModificationSet.impl.FeatureDiagramModificationSetPackageImpl#getDelta()
	 * @generated
	 */
	int DELTA = 2;

	/**
	 * The feature id for the '<em><b>Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELTA__PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Value Prior Change</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELTA__VALUE_PRIOR_CHANGE = 1;

	/**
	 * The feature id for the '<em><b>Value After Change</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELTA__VALUE_AFTER_CHANGE = 2;

	/**
	 * The number of structural features of the '<em>Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELTA_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELTA_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link FeatureDiagramModificationSet.DeltaProperties <em>Delta Properties</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see FeatureDiagramModificationSet.DeltaProperties
	 * @see FeatureDiagramModificationSet.impl.FeatureDiagramModificationSetPackageImpl#getDeltaProperties()
	 * @generated
	 */
	int DELTA_PROPERTIES = 3;


	/**
	 * Returns the meta object for class '{@link FeatureDiagramModificationSet.FeatureModelModificationSet <em>Feature Model Modification Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature Model Modification Set</em>'.
	 * @see FeatureDiagramModificationSet.FeatureModelModificationSet
	 * @generated
	 */
	EClass getFeatureModelModificationSet();

	/**
	 * Returns the meta object for the containment reference list '{@link FeatureDiagramModificationSet.FeatureModelModificationSet#getModifications <em>Modifications</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Modifications</em>'.
	 * @see FeatureDiagramModificationSet.FeatureModelModificationSet#getModifications()
	 * @see #getFeatureModelModificationSet()
	 * @generated
	 */
	EReference getFeatureModelModificationSet_Modifications();

	/**
	 * Returns the meta object for the attribute '{@link FeatureDiagramModificationSet.FeatureModelModificationSet#getAffectedFeatureModelName <em>Affected Feature Model Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Affected Feature Model Name</em>'.
	 * @see FeatureDiagramModificationSet.FeatureModelModificationSet#getAffectedFeatureModelName()
	 * @see #getFeatureModelModificationSet()
	 * @generated
	 */
	EAttribute getFeatureModelModificationSet_AffectedFeatureModelName();

	/**
	 * Returns the meta object for class '{@link FeatureDiagramModificationSet.Modification <em>Modification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Modification</em>'.
	 * @see FeatureDiagramModificationSet.Modification
	 * @generated
	 */
	EClass getModification();

	/**
	 * Returns the meta object for the attribute '{@link FeatureDiagramModificationSet.Modification#getFeatureID <em>Feature ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Feature ID</em>'.
	 * @see FeatureDiagramModificationSet.Modification#getFeatureID()
	 * @see #getModification()
	 * @generated
	 */
	EAttribute getModification_FeatureID();

	/**
	 * Returns the meta object for the attribute '{@link FeatureDiagramModificationSet.Modification#getTimeStamp <em>Time Stamp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time Stamp</em>'.
	 * @see FeatureDiagramModificationSet.Modification#getTimeStamp()
	 * @see #getModification()
	 * @generated
	 */
	EAttribute getModification_TimeStamp();

	/**
	 * Returns the meta object for the containment reference '{@link FeatureDiagramModificationSet.Modification#getDelta <em>Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Delta</em>'.
	 * @see FeatureDiagramModificationSet.Modification#getDelta()
	 * @see #getModification()
	 * @generated
	 */
	EReference getModification_Delta();

	/**
	 * Returns the meta object for the attribute '{@link FeatureDiagramModificationSet.Modification#getModificationType <em>Modification Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Modification Type</em>'.
	 * @see FeatureDiagramModificationSet.Modification#getModificationType()
	 * @see #getModification()
	 * @generated
	 */
	EAttribute getModification_ModificationType();

	/**
	 * Returns the meta object for the attribute '{@link FeatureDiagramModificationSet.Modification#getPrecisionTime <em>Precision Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Precision Time</em>'.
	 * @see FeatureDiagramModificationSet.Modification#getPrecisionTime()
	 * @see #getModification()
	 * @generated
	 */
	EAttribute getModification_PrecisionTime();

	/**
	 * Returns the meta object for the containment reference list '{@link FeatureDiagramModificationSet.Modification#getReferencedArtifacts <em>Referenced Artifacts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Referenced Artifacts</em>'.
	 * @see FeatureDiagramModificationSet.Modification#getReferencedArtifacts()
	 * @see #getModification()
	 * @generated
	 */
	EReference getModification_ReferencedArtifacts();

	/**
	 * Returns the meta object for class '{@link FeatureDiagramModificationSet.Delta <em>Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Delta</em>'.
	 * @see FeatureDiagramModificationSet.Delta
	 * @generated
	 */
	EClass getDelta();

	/**
	 * Returns the meta object for the attribute '{@link FeatureDiagramModificationSet.Delta#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property</em>'.
	 * @see FeatureDiagramModificationSet.Delta#getProperty()
	 * @see #getDelta()
	 * @generated
	 */
	EAttribute getDelta_Property();

	/**
	 * Returns the meta object for the attribute '{@link FeatureDiagramModificationSet.Delta#getValuePriorChange <em>Value Prior Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value Prior Change</em>'.
	 * @see FeatureDiagramModificationSet.Delta#getValuePriorChange()
	 * @see #getDelta()
	 * @generated
	 */
	EAttribute getDelta_ValuePriorChange();

	/**
	 * Returns the meta object for the attribute '{@link FeatureDiagramModificationSet.Delta#getValueAfterChange <em>Value After Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value After Change</em>'.
	 * @see FeatureDiagramModificationSet.Delta#getValueAfterChange()
	 * @see #getDelta()
	 * @generated
	 */
	EAttribute getDelta_ValueAfterChange();

	/**
	 * Returns the meta object for enum '{@link FeatureDiagramModificationSet.DeltaProperties <em>Delta Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Delta Properties</em>'.
	 * @see FeatureDiagramModificationSet.DeltaProperties
	 * @generated
	 */
	EEnum getDeltaProperties();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	FeatureDiagramModificationSetFactory getFeatureDiagramModificationSetFactory();

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
		 * The meta object literal for the '{@link FeatureDiagramModificationSet.impl.FeatureModelModificationSetImpl <em>Feature Model Modification Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see FeatureDiagramModificationSet.impl.FeatureModelModificationSetImpl
		 * @see FeatureDiagramModificationSet.impl.FeatureDiagramModificationSetPackageImpl#getFeatureModelModificationSet()
		 * @generated
		 */
		EClass FEATURE_MODEL_MODIFICATION_SET = eINSTANCE.getFeatureModelModificationSet();

		/**
		 * The meta object literal for the '<em><b>Modifications</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_MODEL_MODIFICATION_SET__MODIFICATIONS = eINSTANCE.getFeatureModelModificationSet_Modifications();

		/**
		 * The meta object literal for the '<em><b>Affected Feature Model Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_MODEL_MODIFICATION_SET__AFFECTED_FEATURE_MODEL_NAME = eINSTANCE.getFeatureModelModificationSet_AffectedFeatureModelName();

		/**
		 * The meta object literal for the '{@link FeatureDiagramModificationSet.impl.ModificationImpl <em>Modification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see FeatureDiagramModificationSet.impl.ModificationImpl
		 * @see FeatureDiagramModificationSet.impl.FeatureDiagramModificationSetPackageImpl#getModification()
		 * @generated
		 */
		EClass MODIFICATION = eINSTANCE.getModification();

		/**
		 * The meta object literal for the '<em><b>Feature ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODIFICATION__FEATURE_ID = eINSTANCE.getModification_FeatureID();

		/**
		 * The meta object literal for the '<em><b>Time Stamp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODIFICATION__TIME_STAMP = eINSTANCE.getModification_TimeStamp();

		/**
		 * The meta object literal for the '<em><b>Delta</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODIFICATION__DELTA = eINSTANCE.getModification_Delta();

		/**
		 * The meta object literal for the '<em><b>Modification Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODIFICATION__MODIFICATION_TYPE = eINSTANCE.getModification_ModificationType();

		/**
		 * The meta object literal for the '<em><b>Precision Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODIFICATION__PRECISION_TIME = eINSTANCE.getModification_PrecisionTime();

		/**
		 * The meta object literal for the '<em><b>Referenced Artifacts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODIFICATION__REFERENCED_ARTIFACTS = eINSTANCE.getModification_ReferencedArtifacts();

		/**
		 * The meta object literal for the '{@link FeatureDiagramModificationSet.impl.DeltaImpl <em>Delta</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see FeatureDiagramModificationSet.impl.DeltaImpl
		 * @see FeatureDiagramModificationSet.impl.FeatureDiagramModificationSetPackageImpl#getDelta()
		 * @generated
		 */
		EClass DELTA = eINSTANCE.getDelta();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DELTA__PROPERTY = eINSTANCE.getDelta_Property();

		/**
		 * The meta object literal for the '<em><b>Value Prior Change</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DELTA__VALUE_PRIOR_CHANGE = eINSTANCE.getDelta_ValuePriorChange();

		/**
		 * The meta object literal for the '<em><b>Value After Change</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DELTA__VALUE_AFTER_CHANGE = eINSTANCE.getDelta_ValueAfterChange();

		/**
		 * The meta object literal for the '{@link FeatureDiagramModificationSet.DeltaProperties <em>Delta Properties</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see FeatureDiagramModificationSet.DeltaProperties
		 * @see FeatureDiagramModificationSet.impl.FeatureDiagramModificationSetPackageImpl#getDeltaProperties()
		 * @generated
		 */
		EEnum DELTA_PROPERTIES = eINSTANCE.getDeltaProperties();

	}

} //FeatureDiagramModificationSetPackage

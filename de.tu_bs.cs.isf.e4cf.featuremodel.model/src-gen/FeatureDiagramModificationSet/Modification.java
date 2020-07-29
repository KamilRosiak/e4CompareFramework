/**
 */
package FeatureDiagramModificationSet;

import FeatureDiagram.ArtifactReference;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Modification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link FeatureDiagramModificationSet.Modification#getFeatureID <em>Feature ID</em>}</li>
 *   <li>{@link FeatureDiagramModificationSet.Modification#getTimeStamp <em>Time Stamp</em>}</li>
 *   <li>{@link FeatureDiagramModificationSet.Modification#getDelta <em>Delta</em>}</li>
 *   <li>{@link FeatureDiagramModificationSet.Modification#getModificationType <em>Modification Type</em>}</li>
 *   <li>{@link FeatureDiagramModificationSet.Modification#getPrecisionTime <em>Precision Time</em>}</li>
 *   <li>{@link FeatureDiagramModificationSet.Modification#getReferencedArtifacts <em>Referenced Artifacts</em>}</li>
 * </ul>
 *
 * @see FeatureDiagramModificationSet.FeatureDiagramModificationSetPackage#getModification()
 * @model
 * @generated
 */
public interface Modification extends EObject {
	/**
	 * Returns the value of the '<em><b>Feature ID</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature ID</em>' attribute.
	 * @see #setFeatureID(int)
	 * @see FeatureDiagramModificationSet.FeatureDiagramModificationSetPackage#getModification_FeatureID()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getFeatureID();

	/**
	 * Sets the value of the '{@link FeatureDiagramModificationSet.Modification#getFeatureID <em>Feature ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature ID</em>' attribute.
	 * @see #getFeatureID()
	 * @generated
	 */
	void setFeatureID(int value);

	/**
	 * Returns the value of the '<em><b>Time Stamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time Stamp</em>' attribute.
	 * @see #setTimeStamp(long)
	 * @see FeatureDiagramModificationSet.FeatureDiagramModificationSetPackage#getModification_TimeStamp()
	 * @model required="true"
	 * @generated
	 */
	long getTimeStamp();

	/**
	 * Sets the value of the '{@link FeatureDiagramModificationSet.Modification#getTimeStamp <em>Time Stamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time Stamp</em>' attribute.
	 * @see #getTimeStamp()
	 * @generated
	 */
	void setTimeStamp(long value);

	/**
	 * Returns the value of the '<em><b>Delta</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delta</em>' containment reference.
	 * @see #setDelta(Delta)
	 * @see FeatureDiagramModificationSet.FeatureDiagramModificationSetPackage#getModification_Delta()
	 * @model containment="true"
	 * @generated
	 */
	Delta getDelta();

	/**
	 * Sets the value of the '{@link FeatureDiagramModificationSet.Modification#getDelta <em>Delta</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delta</em>' containment reference.
	 * @see #getDelta()
	 * @generated
	 */
	void setDelta(Delta value);

	/**
	 * Returns the value of the '<em><b>Modification Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modification Type</em>' attribute.
	 * @see #setModificationType(String)
	 * @see FeatureDiagramModificationSet.FeatureDiagramModificationSetPackage#getModification_ModificationType()
	 * @model
	 * @generated
	 */
	String getModificationType();

	/**
	 * Sets the value of the '{@link FeatureDiagramModificationSet.Modification#getModificationType <em>Modification Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Modification Type</em>' attribute.
	 * @see #getModificationType()
	 * @generated
	 */
	void setModificationType(String value);

	/**
	 * Returns the value of the '<em><b>Precision Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Precision Time</em>' attribute.
	 * @see #setPrecisionTime(long)
	 * @see FeatureDiagramModificationSet.FeatureDiagramModificationSetPackage#getModification_PrecisionTime()
	 * @model
	 * @generated
	 */
	long getPrecisionTime();

	/**
	 * Sets the value of the '{@link FeatureDiagramModificationSet.Modification#getPrecisionTime <em>Precision Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Precision Time</em>' attribute.
	 * @see #getPrecisionTime()
	 * @generated
	 */
	void setPrecisionTime(long value);

	/**
	 * Returns the value of the '<em><b>Referenced Artifacts</b></em>' containment reference list.
	 * The list contents are of type {@link FeatureDiagram.ArtifactReference}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Referenced Artifacts</em>' containment reference list.
	 * @see FeatureDiagramModificationSet.FeatureDiagramModificationSetPackage#getModification_ReferencedArtifacts()
	 * @model containment="true"
	 * @generated
	 */
	EList<ArtifactReference> getReferencedArtifacts();

} // Modification

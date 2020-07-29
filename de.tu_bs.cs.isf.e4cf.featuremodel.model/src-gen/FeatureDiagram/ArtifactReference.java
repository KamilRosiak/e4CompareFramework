/**
 */
package FeatureDiagram;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Artifact Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link FeatureDiagram.ArtifactReference#getArtifactClearName <em>Artifact Clear Name</em>}</li>
 *   <li>{@link FeatureDiagram.ArtifactReference#getReferencedArtifactIDs <em>Referenced Artifact IDs</em>}</li>
 * </ul>
 *
 * @see FeatureDiagram.FeatureDiagramPackage#getArtifactReference()
 * @model
 * @generated
 */
public interface ArtifactReference extends EObject {
	/**
	 * Returns the value of the '<em><b>Artifact Clear Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Artifact Clear Name</em>' attribute.
	 * @see #setArtifactClearName(String)
	 * @see FeatureDiagram.FeatureDiagramPackage#getArtifactReference_ArtifactClearName()
	 * @model required="true"
	 * @generated
	 */
	String getArtifactClearName();

	/**
	 * Sets the value of the '{@link FeatureDiagram.ArtifactReference#getArtifactClearName <em>Artifact Clear Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Artifact Clear Name</em>' attribute.
	 * @see #getArtifactClearName()
	 * @generated
	 */
	void setArtifactClearName(String value);

	/**
	 * Returns the value of the '<em><b>Referenced Artifact IDs</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Referenced Artifact IDs</em>' attribute list.
	 * @see FeatureDiagram.FeatureDiagramPackage#getArtifactReference_ReferencedArtifactIDs()
	 * @model
	 * @generated
	 */
	EList<String> getReferencedArtifactIDs();

} // ArtifactReference

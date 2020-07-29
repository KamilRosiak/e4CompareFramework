/**
 */
package FeatureDiagram;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link FeatureDiagram.Feature#getChildren <em>Children</em>}</li>
 *   <li>{@link FeatureDiagram.Feature#getName <em>Name</em>}</li>
 *   <li>{@link FeatureDiagram.Feature#getDescription <em>Description</em>}</li>
 *   <li>{@link FeatureDiagram.Feature#isMandatory <em>Mandatory</em>}</li>
 *   <li>{@link FeatureDiagram.Feature#isAlternative <em>Alternative</em>}</li>
 *   <li>{@link FeatureDiagram.Feature#isOr <em>Or</em>}</li>
 *   <li>{@link FeatureDiagram.Feature#getGraphicalfeature <em>Graphicalfeature</em>}</li>
 *   <li>{@link FeatureDiagram.Feature#getParent <em>Parent</em>}</li>
 *   <li>{@link FeatureDiagram.Feature#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link FeatureDiagram.Feature#isAbstract <em>Abstract</em>}</li>
 *   <li>{@link FeatureDiagram.Feature#isHidden <em>Hidden</em>}</li>
 *   <li>{@link FeatureDiagram.Feature#getArtifactReferences <em>Artifact References</em>}</li>
 * </ul>
 *
 * @see FeatureDiagram.FeatureDiagramPackage#getFeature()
 * @model
 * @generated
 */
public interface Feature extends EObject {
	/**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link FeatureDiagram.Feature}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see FeatureDiagram.FeatureDiagramPackage#getFeature_Children()
	 * @model containment="true"
	 * @generated
	 */
	EList<Feature> getChildren();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * The default value is <code>"newFeature"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see FeatureDiagram.FeatureDiagramPackage#getFeature_Name()
	 * @model default="newFeature" required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link FeatureDiagram.Feature#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * The default value is <code>"description"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see FeatureDiagram.FeatureDiagramPackage#getFeature_Description()
	 * @model default="description"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link FeatureDiagram.Feature#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Mandatory</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mandatory</em>' attribute.
	 * @see #setMandatory(boolean)
	 * @see FeatureDiagram.FeatureDiagramPackage#getFeature_Mandatory()
	 * @model default="false" required="true"
	 * @generated
	 */
	boolean isMandatory();

	/**
	 * Sets the value of the '{@link FeatureDiagram.Feature#isMandatory <em>Mandatory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mandatory</em>' attribute.
	 * @see #isMandatory()
	 * @generated
	 */
	void setMandatory(boolean value);

	/**
	 * Returns the value of the '<em><b>Alternative</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alternative</em>' attribute.
	 * @see #setAlternative(boolean)
	 * @see FeatureDiagram.FeatureDiagramPackage#getFeature_Alternative()
	 * @model default="false" required="true"
	 * @generated
	 */
	boolean isAlternative();

	/**
	 * Sets the value of the '{@link FeatureDiagram.Feature#isAlternative <em>Alternative</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alternative</em>' attribute.
	 * @see #isAlternative()
	 * @generated
	 */
	void setAlternative(boolean value);

	/**
	 * Returns the value of the '<em><b>Or</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Or</em>' attribute.
	 * @see #setOr(boolean)
	 * @see FeatureDiagram.FeatureDiagramPackage#getFeature_Or()
	 * @model required="true"
	 * @generated
	 */
	boolean isOr();

	/**
	 * Sets the value of the '{@link FeatureDiagram.Feature#isOr <em>Or</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Or</em>' attribute.
	 * @see #isOr()
	 * @generated
	 */
	void setOr(boolean value);

	/**
	 * Returns the value of the '<em><b>Graphicalfeature</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Graphicalfeature</em>' containment reference.
	 * @see #setGraphicalfeature(GraphicalFeature)
	 * @see FeatureDiagram.FeatureDiagramPackage#getFeature_Graphicalfeature()
	 * @model containment="true" required="true"
	 * @generated
	 */
	GraphicalFeature getGraphicalfeature();

	/**
	 * Sets the value of the '{@link FeatureDiagram.Feature#getGraphicalfeature <em>Graphicalfeature</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Graphicalfeature</em>' containment reference.
	 * @see #getGraphicalfeature()
	 * @generated
	 */
	void setGraphicalfeature(GraphicalFeature value);

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(Feature)
	 * @see FeatureDiagram.FeatureDiagramPackage#getFeature_Parent()
	 * @model
	 * @generated
	 */
	Feature getParent();

	/**
	 * Sets the value of the '{@link FeatureDiagram.Feature#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(Feature value);

	/**
	 * Returns the value of the '<em><b>Identifier</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Identifier</em>' attribute.
	 * @see #setIdentifier(int)
	 * @see FeatureDiagram.FeatureDiagramPackage#getFeature_Identifier()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getIdentifier();

	/**
	 * Sets the value of the '{@link FeatureDiagram.Feature#getIdentifier <em>Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Identifier</em>' attribute.
	 * @see #getIdentifier()
	 * @generated
	 */
	void setIdentifier(int value);

	/**
	 * Returns the value of the '<em><b>Abstract</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abstract</em>' attribute.
	 * @see #setAbstract(boolean)
	 * @see FeatureDiagram.FeatureDiagramPackage#getFeature_Abstract()
	 * @model default="false" required="true"
	 * @generated
	 */
	boolean isAbstract();

	/**
	 * Sets the value of the '{@link FeatureDiagram.Feature#isAbstract <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Abstract</em>' attribute.
	 * @see #isAbstract()
	 * @generated
	 */
	void setAbstract(boolean value);

	/**
	 * Returns the value of the '<em><b>Hidden</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hidden</em>' attribute.
	 * @see #setHidden(boolean)
	 * @see FeatureDiagram.FeatureDiagramPackage#getFeature_Hidden()
	 * @model default="false" required="true"
	 * @generated
	 */
	boolean isHidden();

	/**
	 * Sets the value of the '{@link FeatureDiagram.Feature#isHidden <em>Hidden</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hidden</em>' attribute.
	 * @see #isHidden()
	 * @generated
	 */
	void setHidden(boolean value);

	/**
	 * Returns the value of the '<em><b>Artifact References</b></em>' containment reference list.
	 * The list contents are of type {@link FeatureDiagram.ArtifactReference}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Artifact References</em>' containment reference list.
	 * @see FeatureDiagram.FeatureDiagramPackage#getFeature_ArtifactReferences()
	 * @model containment="true"
	 * @generated
	 */
	EList<ArtifactReference> getArtifactReferences();

} // Feature

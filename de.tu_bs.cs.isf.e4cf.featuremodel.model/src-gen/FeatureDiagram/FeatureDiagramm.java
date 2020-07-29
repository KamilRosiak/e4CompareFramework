/**
 */
package FeatureDiagram;

import CrossTreeConstraints.AbstractConstraint;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature Diagramm</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link FeatureDiagram.FeatureDiagramm#getRoot <em>Root</em>}</li>
 *   <li>{@link FeatureDiagram.FeatureDiagramm#getIdentifierIncrement <em>Identifier Increment</em>}</li>
 *   <li>{@link FeatureDiagram.FeatureDiagramm#getConstraints <em>Constraints</em>}</li>
 * </ul>
 *
 * @see FeatureDiagram.FeatureDiagramPackage#getFeatureDiagramm()
 * @model
 * @generated
 */
public interface FeatureDiagramm extends EObject {
	/**
	 * Returns the value of the '<em><b>Root</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root</em>' containment reference.
	 * @see #setRoot(Feature)
	 * @see FeatureDiagram.FeatureDiagramPackage#getFeatureDiagramm_Root()
	 * @model containment="true"
	 * @generated
	 */
	Feature getRoot();

	/**
	 * Sets the value of the '{@link FeatureDiagram.FeatureDiagramm#getRoot <em>Root</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root</em>' containment reference.
	 * @see #getRoot()
	 * @generated
	 */
	void setRoot(Feature value);

	/**
	 * Returns the value of the '<em><b>Identifier Increment</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Identifier Increment</em>' attribute.
	 * @see #setIdentifierIncrement(int)
	 * @see FeatureDiagram.FeatureDiagramPackage#getFeatureDiagramm_IdentifierIncrement()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getIdentifierIncrement();

	/**
	 * Sets the value of the '{@link FeatureDiagram.FeatureDiagramm#getIdentifierIncrement <em>Identifier Increment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Identifier Increment</em>' attribute.
	 * @see #getIdentifierIncrement()
	 * @generated
	 */
	void setIdentifierIncrement(int value);

	/**
	 * Returns the value of the '<em><b>Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link CrossTreeConstraints.AbstractConstraint}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraints</em>' containment reference list.
	 * @see FeatureDiagram.FeatureDiagramPackage#getFeatureDiagramm_Constraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<AbstractConstraint> getConstraints();

} // FeatureDiagramm

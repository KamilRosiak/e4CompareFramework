/**
 */
package FeatureDiagram;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Component Feature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link FeatureDiagram.ComponentFeature#getFeaturediagramm <em>Featurediagramm</em>}</li>
 * </ul>
 *
 * @see FeatureDiagram.FeatureDiagramPackage#getComponentFeature()
 * @model
 * @generated
 */
public interface ComponentFeature extends Feature {
	/**
	 * Returns the value of the '<em><b>Featurediagramm</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Featurediagramm</em>' containment reference.
	 * @see #setFeaturediagramm(FeatureDiagramm)
	 * @see FeatureDiagram.FeatureDiagramPackage#getComponentFeature_Featurediagramm()
	 * @model containment="true" required="true"
	 * @generated
	 */
	FeatureDiagramm getFeaturediagramm();

	/**
	 * Sets the value of the '{@link FeatureDiagram.ComponentFeature#getFeaturediagramm <em>Featurediagramm</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Featurediagramm</em>' containment reference.
	 * @see #getFeaturediagramm()
	 * @generated
	 */
	void setFeaturediagramm(FeatureDiagramm value);

} // ComponentFeature

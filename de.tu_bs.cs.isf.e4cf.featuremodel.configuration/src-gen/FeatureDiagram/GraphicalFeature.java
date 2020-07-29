/**
 */
package FeatureDiagram;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Graphical Feature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link FeatureDiagram.GraphicalFeature#getX <em>X</em>}</li>
 *   <li>{@link FeatureDiagram.GraphicalFeature#getY <em>Y</em>}</li>
 *   <li>{@link FeatureDiagram.GraphicalFeature#getWidth <em>Width</em>}</li>
 *   <li>{@link FeatureDiagram.GraphicalFeature#getHeight <em>Height</em>}</li>
 * </ul>
 *
 * @see FeatureDiagram.FeatureDiagramPackage#getGraphicalFeature()
 * @model
 * @generated
 */
public interface GraphicalFeature extends EObject {
	/**
	 * Returns the value of the '<em><b>X</b></em>' attribute.
	 * The default value is <code>"0.0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>X</em>' attribute.
	 * @see #setX(Double)
	 * @see FeatureDiagram.FeatureDiagramPackage#getGraphicalFeature_X()
	 * @model default="0.0" required="true"
	 * @generated
	 */
	Double getX();

	/**
	 * Sets the value of the '{@link FeatureDiagram.GraphicalFeature#getX <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>X</em>' attribute.
	 * @see #getX()
	 * @generated
	 */
	void setX(Double value);

	/**
	 * Returns the value of the '<em><b>Y</b></em>' attribute.
	 * The default value is <code>"0.0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Y</em>' attribute.
	 * @see #setY(Double)
	 * @see FeatureDiagram.FeatureDiagramPackage#getGraphicalFeature_Y()
	 * @model default="0.0" required="true"
	 * @generated
	 */
	Double getY();

	/**
	 * Sets the value of the '{@link FeatureDiagram.GraphicalFeature#getY <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Y</em>' attribute.
	 * @see #getY()
	 * @generated
	 */
	void setY(Double value);

	/**
	 * Returns the value of the '<em><b>Width</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Width</em>' attribute.
	 * @see #setWidth(Double)
	 * @see FeatureDiagram.FeatureDiagramPackage#getGraphicalFeature_Width()
	 * @model default="0" required="true"
	 * @generated
	 */
	Double getWidth();

	/**
	 * Sets the value of the '{@link FeatureDiagram.GraphicalFeature#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Width</em>' attribute.
	 * @see #getWidth()
	 * @generated
	 */
	void setWidth(Double value);

	/**
	 * Returns the value of the '<em><b>Height</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Height</em>' attribute.
	 * @see #setHeight(Double)
	 * @see FeatureDiagram.FeatureDiagramPackage#getGraphicalFeature_Height()
	 * @model default="0" required="true"
	 * @generated
	 */
	Double getHeight();

	/**
	 * Sets the value of the '{@link FeatureDiagram.GraphicalFeature#getHeight <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Height</em>' attribute.
	 * @see #getHeight()
	 * @generated
	 */
	void setHeight(Double value);

} // GraphicalFeature

/**
 */
package de.tu_bs.cs.isf.e4cf.graph;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Graphic Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.graph.GraphicElement#getWidth <em>Width</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.graph.GraphicElement#getHeight <em>Height</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.graph.GraphicElement#getXPostion <em>XPostion</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.graph.GraphicElement#getYPostion <em>YPostion</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.e4cf.graph.GraphPackage#getGraphicElement()
 * @model abstract="true"
 * @generated
 */
public interface GraphicElement extends Node {
	/**
	 * Returns the value of the '<em><b>Width</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Width</em>' attribute.
	 * @see #setWidth(Double)
	 * @see de.tu_bs.cs.isf.e4cf.graph.GraphPackage#getGraphicElement_Width()
	 * @model default="0" required="true"
	 * @generated
	 */
	Double getWidth();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.e4cf.graph.GraphicElement#getWidth <em>Width</em>}' attribute.
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
	 * @see de.tu_bs.cs.isf.e4cf.graph.GraphPackage#getGraphicElement_Height()
	 * @model default="0" required="true"
	 * @generated
	 */
	Double getHeight();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.e4cf.graph.GraphicElement#getHeight <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Height</em>' attribute.
	 * @see #getHeight()
	 * @generated
	 */
	void setHeight(Double value);

	/**
	 * Returns the value of the '<em><b>XPostion</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XPostion</em>' attribute.
	 * @see #setXPostion(Double)
	 * @see de.tu_bs.cs.isf.e4cf.graph.GraphPackage#getGraphicElement_XPostion()
	 * @model default="0" required="true"
	 * @generated
	 */
	Double getXPostion();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.e4cf.graph.GraphicElement#getXPostion <em>XPostion</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>XPostion</em>' attribute.
	 * @see #getXPostion()
	 * @generated
	 */
	void setXPostion(Double value);

	/**
	 * Returns the value of the '<em><b>YPostion</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>YPostion</em>' attribute.
	 * @see #setYPostion(Double)
	 * @see de.tu_bs.cs.isf.e4cf.graph.GraphPackage#getGraphicElement_YPostion()
	 * @model default="0" required="true"
	 * @generated
	 */
	Double getYPostion();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.e4cf.graph.GraphicElement#getYPostion <em>YPostion</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>YPostion</em>' attribute.
	 * @see #getYPostion()
	 * @generated
	 */
	void setYPostion(Double value);

} // GraphicElement

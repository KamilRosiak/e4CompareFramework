/**
 */
package de.tu_bs.cs.isf.e4cf.graph;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Graph</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.graph.Graph#getName <em>Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.graph.Graph#getGraph <em>Graph</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.graph.Graph#getEdge <em>Edge</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.e4cf.graph.GraphPackage#getGraph()
 * @model
 * @generated
 */
public interface Graph extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see de.tu_bs.cs.isf.e4cf.graph.GraphPackage#getGraph_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.e4cf.graph.Graph#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Graph</b></em>' containment reference list.
	 * The list contents are of type {@link de.tu_bs.cs.isf.e4cf.graph.Node}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Graph</em>' containment reference list.
	 * @see de.tu_bs.cs.isf.e4cf.graph.GraphPackage#getGraph_Graph()
	 * @model containment="true"
	 * @generated
	 */
	EList<Node> getGraph();

	/**
	 * Returns the value of the '<em><b>Edge</b></em>' containment reference list.
	 * The list contents are of type {@link de.tu_bs.cs.isf.e4cf.graph.Edge}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edge</em>' containment reference list.
	 * @see de.tu_bs.cs.isf.e4cf.graph.GraphPackage#getGraph_Edge()
	 * @model containment="true"
	 * @generated
	 */
	EList<Edge> getEdge();

} // Graph

/**
 */
package de.tu_bs.cs.isf.e4cf.graph;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see de.tu_bs.cs.isf.e4cf.graph.GraphFactory
 * @model kind="package"
 * @generated
 */
public interface GraphPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "graph";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "https://www.tu-braunschweig.de/isf/graph";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "G";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GraphPackage eINSTANCE = de.tu_bs.cs.isf.e4cf.graph.impl.GraphPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.e4cf.graph.impl.GraphImpl <em>Graph</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.e4cf.graph.impl.GraphImpl
	 * @see de.tu_bs.cs.isf.e4cf.graph.impl.GraphPackageImpl#getGraph()
	 * @generated
	 */
	int GRAPH = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH__NAME = 0;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH__GRAPH = 1;

	/**
	 * The feature id for the '<em><b>Edge</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH__EDGE = 2;

	/**
	 * The number of structural features of the '<em>Graph</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Graph</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.e4cf.graph.impl.EdgeImpl <em>Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.e4cf.graph.impl.EdgeImpl
	 * @see de.tu_bs.cs.isf.e4cf.graph.impl.GraphPackageImpl#getEdge()
	 * @generated
	 */
	int EDGE = 1;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__TARGET = 1;

	/**
	 * The number of structural features of the '<em>Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.e4cf.graph.impl.NodeImpl <em>Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.e4cf.graph.impl.NodeImpl
	 * @see de.tu_bs.cs.isf.e4cf.graph.impl.GraphPackageImpl#getNode()
	 * @generated
	 */
	int NODE = 2;

	/**
	 * The number of structural features of the '<em>Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.e4cf.graph.impl.GraphicElementImpl <em>Graphic Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.e4cf.graph.impl.GraphicElementImpl
	 * @see de.tu_bs.cs.isf.e4cf.graph.impl.GraphPackageImpl#getGraphicElement()
	 * @generated
	 */
	int GRAPHIC_ELEMENT = 3;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPHIC_ELEMENT__WIDTH = NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPHIC_ELEMENT__HEIGHT = NODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>XPostion</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPHIC_ELEMENT__XPOSTION = NODE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>YPostion</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPHIC_ELEMENT__YPOSTION = NODE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Graphic Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPHIC_ELEMENT_FEATURE_COUNT = NODE_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Graphic Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPHIC_ELEMENT_OPERATION_COUNT = NODE_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.e4cf.graph.Graph <em>Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Graph</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.graph.Graph
	 * @generated
	 */
	EClass getGraph();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.e4cf.graph.Graph#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.graph.Graph#getName()
	 * @see #getGraph()
	 * @generated
	 */
	EAttribute getGraph_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.e4cf.graph.Graph#getGraph <em>Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Graph</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.graph.Graph#getGraph()
	 * @see #getGraph()
	 * @generated
	 */
	EReference getGraph_Graph();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.e4cf.graph.Graph#getEdge <em>Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Edge</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.graph.Graph#getEdge()
	 * @see #getGraph()
	 * @generated
	 */
	EReference getGraph_Edge();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.e4cf.graph.Edge <em>Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Edge</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.graph.Edge
	 * @generated
	 */
	EClass getEdge();

	/**
	 * Returns the meta object for the reference '{@link de.tu_bs.cs.isf.e4cf.graph.Edge#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.graph.Edge#getSource()
	 * @see #getEdge()
	 * @generated
	 */
	EReference getEdge_Source();

	/**
	 * Returns the meta object for the reference '{@link de.tu_bs.cs.isf.e4cf.graph.Edge#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.graph.Edge#getTarget()
	 * @see #getEdge()
	 * @generated
	 */
	EReference getEdge_Target();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.e4cf.graph.Node <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.graph.Node
	 * @generated
	 */
	EClass getNode();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.e4cf.graph.GraphicElement <em>Graphic Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Graphic Element</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.graph.GraphicElement
	 * @generated
	 */
	EClass getGraphicElement();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.e4cf.graph.GraphicElement#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.graph.GraphicElement#getWidth()
	 * @see #getGraphicElement()
	 * @generated
	 */
	EAttribute getGraphicElement_Width();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.e4cf.graph.GraphicElement#getHeight <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.graph.GraphicElement#getHeight()
	 * @see #getGraphicElement()
	 * @generated
	 */
	EAttribute getGraphicElement_Height();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.e4cf.graph.GraphicElement#getXPostion <em>XPostion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>XPostion</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.graph.GraphicElement#getXPostion()
	 * @see #getGraphicElement()
	 * @generated
	 */
	EAttribute getGraphicElement_XPostion();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.e4cf.graph.GraphicElement#getYPostion <em>YPostion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>YPostion</em>'.
	 * @see de.tu_bs.cs.isf.e4cf.graph.GraphicElement#getYPostion()
	 * @see #getGraphicElement()
	 * @generated
	 */
	EAttribute getGraphicElement_YPostion();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	GraphFactory getGraphFactory();

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
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.e4cf.graph.impl.GraphImpl <em>Graph</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.e4cf.graph.impl.GraphImpl
		 * @see de.tu_bs.cs.isf.e4cf.graph.impl.GraphPackageImpl#getGraph()
		 * @generated
		 */
		EClass GRAPH = eINSTANCE.getGraph();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRAPH__NAME = eINSTANCE.getGraph_Name();

		/**
		 * The meta object literal for the '<em><b>Graph</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GRAPH__GRAPH = eINSTANCE.getGraph_Graph();

		/**
		 * The meta object literal for the '<em><b>Edge</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GRAPH__EDGE = eINSTANCE.getGraph_Edge();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.e4cf.graph.impl.EdgeImpl <em>Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.e4cf.graph.impl.EdgeImpl
		 * @see de.tu_bs.cs.isf.e4cf.graph.impl.GraphPackageImpl#getEdge()
		 * @generated
		 */
		EClass EDGE = eINSTANCE.getEdge();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE__SOURCE = eINSTANCE.getEdge_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE__TARGET = eINSTANCE.getEdge_Target();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.e4cf.graph.impl.NodeImpl <em>Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.e4cf.graph.impl.NodeImpl
		 * @see de.tu_bs.cs.isf.e4cf.graph.impl.GraphPackageImpl#getNode()
		 * @generated
		 */
		EClass NODE = eINSTANCE.getNode();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.e4cf.graph.impl.GraphicElementImpl <em>Graphic Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.e4cf.graph.impl.GraphicElementImpl
		 * @see de.tu_bs.cs.isf.e4cf.graph.impl.GraphPackageImpl#getGraphicElement()
		 * @generated
		 */
		EClass GRAPHIC_ELEMENT = eINSTANCE.getGraphicElement();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRAPHIC_ELEMENT__WIDTH = eINSTANCE.getGraphicElement_Width();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRAPHIC_ELEMENT__HEIGHT = eINSTANCE.getGraphicElement_Height();

		/**
		 * The meta object literal for the '<em><b>XPostion</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRAPHIC_ELEMENT__XPOSTION = eINSTANCE.getGraphicElement_XPostion();

		/**
		 * The meta object literal for the '<em><b>YPostion</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRAPHIC_ELEMENT__YPOSTION = eINSTANCE.getGraphicElement_YPostion();

	}

} //GraphPackage

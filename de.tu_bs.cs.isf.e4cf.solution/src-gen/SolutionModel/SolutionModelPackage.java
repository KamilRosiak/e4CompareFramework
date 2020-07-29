/**
 */
package SolutionModel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
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
 * @see SolutionModel.SolutionModelFactory
 * @model kind="package"
 * @generated
 */
public interface SolutionModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "SolutionModel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "www.tu-bs.de/isf/solution_model";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "SM";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SolutionModelPackage eINSTANCE = SolutionModel.impl.SolutionModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link SolutionModel.IUpdate <em>IUpdate</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SolutionModel.IUpdate
	 * @see SolutionModel.impl.SolutionModelPackageImpl#getIUpdate()
	 * @generated
	 */
	int IUPDATE = 4;

	/**
	 * The number of structural features of the '<em>IUpdate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IUPDATE_FEATURE_COUNT = 0;

	/**
	 * The operation id for the '<em>Update Similarity</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IUPDATE___UPDATE_SIMILARITY = 0;

	/**
	 * The number of operations of the '<em>IUpdate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IUPDATE_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link SolutionModel.impl.AbstractSolutionElementImpl <em>Abstract Solution Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SolutionModel.impl.AbstractSolutionElementImpl
	 * @see SolutionModel.impl.SolutionModelPackageImpl#getAbstractSolutionElement()
	 * @generated
	 */
	int ABSTRACT_SOLUTION_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Similarity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SOLUTION_ELEMENT__SIMILARITY = IUPDATE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Left Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SOLUTION_ELEMENT__LEFT_ELEMENT = IUPDATE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Right Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SOLUTION_ELEMENT__RIGHT_ELEMENT = IUPDATE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Abstract Solution Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SOLUTION_ELEMENT_FEATURE_COUNT = IUPDATE_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Update Similarity</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SOLUTION_ELEMENT___UPDATE_SIMILARITY = IUPDATE___UPDATE_SIMILARITY;

	/**
	 * The operation id for the '<em>Get Left Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SOLUTION_ELEMENT___GET_LEFT_LABEL = IUPDATE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Right Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SOLUTION_ELEMENT___GET_RIGHT_LABEL = IUPDATE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Reset Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SOLUTION_ELEMENT___RESET_ELEMENT = IUPDATE_OPERATION_COUNT + 2;

	/**
	 * The number of operations of the '<em>Abstract Solution Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SOLUTION_ELEMENT_OPERATION_COUNT = IUPDATE_OPERATION_COUNT + 3;

	/**
	 * The meta object id for the '{@link SolutionModel.impl.AbstractContainerImpl <em>Abstract Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SolutionModel.impl.AbstractContainerImpl
	 * @see SolutionModel.impl.SolutionModelPackageImpl#getAbstractContainer()
	 * @generated
	 */
	int ABSTRACT_CONTAINER = 1;

	/**
	 * The feature id for the '<em><b>Similarity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER__SIMILARITY = ABSTRACT_SOLUTION_ELEMENT__SIMILARITY;

	/**
	 * The feature id for the '<em><b>Left Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER__LEFT_ELEMENT = ABSTRACT_SOLUTION_ELEMENT__LEFT_ELEMENT;

	/**
	 * The feature id for the '<em><b>Right Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER__RIGHT_ELEMENT = ABSTRACT_SOLUTION_ELEMENT__RIGHT_ELEMENT;

	/**
	 * The feature id for the '<em><b>Abstractoption</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER__ABSTRACTOPTION = ABSTRACT_SOLUTION_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Abstractresult</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER__ABSTRACTRESULT = ABSTRACT_SOLUTION_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Abstract Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER_FEATURE_COUNT = ABSTRACT_SOLUTION_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Update Similarity</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER___UPDATE_SIMILARITY = ABSTRACT_SOLUTION_ELEMENT___UPDATE_SIMILARITY;

	/**
	 * The operation id for the '<em>Get Left Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER___GET_LEFT_LABEL = ABSTRACT_SOLUTION_ELEMENT___GET_LEFT_LABEL;

	/**
	 * The operation id for the '<em>Get Right Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER___GET_RIGHT_LABEL = ABSTRACT_SOLUTION_ELEMENT___GET_RIGHT_LABEL;

	/**
	 * The operation id for the '<em>Reset Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER___RESET_ELEMENT = ABSTRACT_SOLUTION_ELEMENT___RESET_ELEMENT;

	/**
	 * The number of operations of the '<em>Abstract Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER_OPERATION_COUNT = ABSTRACT_SOLUTION_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link SolutionModel.impl.AbstractOptionImpl <em>Abstract Option</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SolutionModel.impl.AbstractOptionImpl
	 * @see SolutionModel.impl.SolutionModelPackageImpl#getAbstractOption()
	 * @generated
	 */
	int ABSTRACT_OPTION = 2;

	/**
	 * The feature id for the '<em><b>Similarity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_OPTION__SIMILARITY = ABSTRACT_SOLUTION_ELEMENT__SIMILARITY;

	/**
	 * The feature id for the '<em><b>Left Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_OPTION__LEFT_ELEMENT = ABSTRACT_SOLUTION_ELEMENT__LEFT_ELEMENT;

	/**
	 * The feature id for the '<em><b>Right Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_OPTION__RIGHT_ELEMENT = ABSTRACT_SOLUTION_ELEMENT__RIGHT_ELEMENT;

	/**
	 * The feature id for the '<em><b>Abstractcontainer</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_OPTION__ABSTRACTCONTAINER = ABSTRACT_SOLUTION_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Abstract Option</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_OPTION_FEATURE_COUNT = ABSTRACT_SOLUTION_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Update Similarity</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_OPTION___UPDATE_SIMILARITY = ABSTRACT_SOLUTION_ELEMENT___UPDATE_SIMILARITY;

	/**
	 * The operation id for the '<em>Get Left Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_OPTION___GET_LEFT_LABEL = ABSTRACT_SOLUTION_ELEMENT___GET_LEFT_LABEL;

	/**
	 * The operation id for the '<em>Get Right Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_OPTION___GET_RIGHT_LABEL = ABSTRACT_SOLUTION_ELEMENT___GET_RIGHT_LABEL;

	/**
	 * The operation id for the '<em>Reset Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_OPTION___RESET_ELEMENT = ABSTRACT_SOLUTION_ELEMENT___RESET_ELEMENT;

	/**
	 * The number of operations of the '<em>Abstract Option</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_OPTION_OPERATION_COUNT = ABSTRACT_SOLUTION_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link SolutionModel.impl.AbstractResultImpl <em>Abstract Result</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SolutionModel.impl.AbstractResultImpl
	 * @see SolutionModel.impl.SolutionModelPackageImpl#getAbstractResult()
	 * @generated
	 */
	int ABSTRACT_RESULT = 3;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_RESULT__ATTRIBUTE = 0;

	/**
	 * The number of structural features of the '<em>Abstract Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_RESULT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Abstract Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_RESULT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link SolutionModel.IFamilyModelAdapter <em>IFamily Model Adapter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SolutionModel.IFamilyModelAdapter
	 * @see SolutionModel.impl.SolutionModelPackageImpl#getIFamilyModelAdapter()
	 * @generated
	 */
	int IFAMILY_MODEL_ADAPTER = 5;

	/**
	 * The number of structural features of the '<em>IFamily Model Adapter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IFAMILY_MODEL_ADAPTER_FEATURE_COUNT = 0;

	/**
	 * The operation id for the '<em>Get Left Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IFAMILY_MODEL_ADAPTER___GET_LEFT_LABEL = 0;

	/**
	 * The operation id for the '<em>Get Right Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IFAMILY_MODEL_ADAPTER___GET_RIGHT_LABEL = 1;

	/**
	 * The number of operations of the '<em>IFamily Model Adapter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IFAMILY_MODEL_ADAPTER_OPERATION_COUNT = 2;


	/**
	 * Returns the meta object for class '{@link SolutionModel.AbstractSolutionElement <em>Abstract Solution Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Solution Element</em>'.
	 * @see SolutionModel.AbstractSolutionElement
	 * @generated
	 */
	EClass getAbstractSolutionElement();

	/**
	 * Returns the meta object for the attribute '{@link SolutionModel.AbstractSolutionElement#getSimilarity <em>Similarity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Similarity</em>'.
	 * @see SolutionModel.AbstractSolutionElement#getSimilarity()
	 * @see #getAbstractSolutionElement()
	 * @generated
	 */
	EAttribute getAbstractSolutionElement_Similarity();

	/**
	 * Returns the meta object for the attribute '{@link SolutionModel.AbstractSolutionElement#getLeftElement <em>Left Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Left Element</em>'.
	 * @see SolutionModel.AbstractSolutionElement#getLeftElement()
	 * @see #getAbstractSolutionElement()
	 * @generated
	 */
	EAttribute getAbstractSolutionElement_LeftElement();

	/**
	 * Returns the meta object for the attribute '{@link SolutionModel.AbstractSolutionElement#getRightElement <em>Right Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Right Element</em>'.
	 * @see SolutionModel.AbstractSolutionElement#getRightElement()
	 * @see #getAbstractSolutionElement()
	 * @generated
	 */
	EAttribute getAbstractSolutionElement_RightElement();

	/**
	 * Returns the meta object for the '{@link SolutionModel.AbstractSolutionElement#resetElement() <em>Reset Element</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Reset Element</em>' operation.
	 * @see SolutionModel.AbstractSolutionElement#resetElement()
	 * @generated
	 */
	EOperation getAbstractSolutionElement__ResetElement();

	/**
	 * Returns the meta object for class '{@link SolutionModel.AbstractContainer <em>Abstract Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Container</em>'.
	 * @see SolutionModel.AbstractContainer
	 * @generated
	 */
	EClass getAbstractContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link SolutionModel.AbstractContainer#getAbstractoption <em>Abstractoption</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Abstractoption</em>'.
	 * @see SolutionModel.AbstractContainer#getAbstractoption()
	 * @see #getAbstractContainer()
	 * @generated
	 */
	EReference getAbstractContainer_Abstractoption();

	/**
	 * Returns the meta object for the containment reference list '{@link SolutionModel.AbstractContainer#getAbstractresult <em>Abstractresult</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Abstractresult</em>'.
	 * @see SolutionModel.AbstractContainer#getAbstractresult()
	 * @see #getAbstractContainer()
	 * @generated
	 */
	EReference getAbstractContainer_Abstractresult();

	/**
	 * Returns the meta object for class '{@link SolutionModel.AbstractOption <em>Abstract Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Option</em>'.
	 * @see SolutionModel.AbstractOption
	 * @generated
	 */
	EClass getAbstractOption();

	/**
	 * Returns the meta object for the containment reference list '{@link SolutionModel.AbstractOption#getAbstractcontainer <em>Abstractcontainer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Abstractcontainer</em>'.
	 * @see SolutionModel.AbstractOption#getAbstractcontainer()
	 * @see #getAbstractOption()
	 * @generated
	 */
	EReference getAbstractOption_Abstractcontainer();

	/**
	 * Returns the meta object for class '{@link SolutionModel.AbstractResult <em>Abstract Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Result</em>'.
	 * @see SolutionModel.AbstractResult
	 * @generated
	 */
	EClass getAbstractResult();

	/**
	 * Returns the meta object for the reference '{@link SolutionModel.AbstractResult#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Attribute</em>'.
	 * @see SolutionModel.AbstractResult#getAttribute()
	 * @see #getAbstractResult()
	 * @generated
	 */
	EReference getAbstractResult_Attribute();

	/**
	 * Returns the meta object for class '{@link SolutionModel.IUpdate <em>IUpdate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IUpdate</em>'.
	 * @see SolutionModel.IUpdate
	 * @generated
	 */
	EClass getIUpdate();

	/**
	 * Returns the meta object for the '{@link SolutionModel.IUpdate#updateSimilarity() <em>Update Similarity</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Update Similarity</em>' operation.
	 * @see SolutionModel.IUpdate#updateSimilarity()
	 * @generated
	 */
	EOperation getIUpdate__UpdateSimilarity();

	/**
	 * Returns the meta object for class '{@link SolutionModel.IFamilyModelAdapter <em>IFamily Model Adapter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IFamily Model Adapter</em>'.
	 * @see SolutionModel.IFamilyModelAdapter
	 * @generated
	 */
	EClass getIFamilyModelAdapter();

	/**
	 * Returns the meta object for the '{@link SolutionModel.IFamilyModelAdapter#getLeftLabel() <em>Get Left Label</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Left Label</em>' operation.
	 * @see SolutionModel.IFamilyModelAdapter#getLeftLabel()
	 * @generated
	 */
	EOperation getIFamilyModelAdapter__GetLeftLabel();

	/**
	 * Returns the meta object for the '{@link SolutionModel.IFamilyModelAdapter#getRightLabel() <em>Get Right Label</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Right Label</em>' operation.
	 * @see SolutionModel.IFamilyModelAdapter#getRightLabel()
	 * @generated
	 */
	EOperation getIFamilyModelAdapter__GetRightLabel();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SolutionModelFactory getSolutionModelFactory();

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
		 * The meta object literal for the '{@link SolutionModel.impl.AbstractSolutionElementImpl <em>Abstract Solution Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SolutionModel.impl.AbstractSolutionElementImpl
		 * @see SolutionModel.impl.SolutionModelPackageImpl#getAbstractSolutionElement()
		 * @generated
		 */
		EClass ABSTRACT_SOLUTION_ELEMENT = eINSTANCE.getAbstractSolutionElement();

		/**
		 * The meta object literal for the '<em><b>Similarity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_SOLUTION_ELEMENT__SIMILARITY = eINSTANCE.getAbstractSolutionElement_Similarity();

		/**
		 * The meta object literal for the '<em><b>Left Element</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_SOLUTION_ELEMENT__LEFT_ELEMENT = eINSTANCE.getAbstractSolutionElement_LeftElement();

		/**
		 * The meta object literal for the '<em><b>Right Element</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_SOLUTION_ELEMENT__RIGHT_ELEMENT = eINSTANCE.getAbstractSolutionElement_RightElement();

		/**
		 * The meta object literal for the '<em><b>Reset Element</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ABSTRACT_SOLUTION_ELEMENT___RESET_ELEMENT = eINSTANCE.getAbstractSolutionElement__ResetElement();

		/**
		 * The meta object literal for the '{@link SolutionModel.impl.AbstractContainerImpl <em>Abstract Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SolutionModel.impl.AbstractContainerImpl
		 * @see SolutionModel.impl.SolutionModelPackageImpl#getAbstractContainer()
		 * @generated
		 */
		EClass ABSTRACT_CONTAINER = eINSTANCE.getAbstractContainer();

		/**
		 * The meta object literal for the '<em><b>Abstractoption</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_CONTAINER__ABSTRACTOPTION = eINSTANCE.getAbstractContainer_Abstractoption();

		/**
		 * The meta object literal for the '<em><b>Abstractresult</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_CONTAINER__ABSTRACTRESULT = eINSTANCE.getAbstractContainer_Abstractresult();

		/**
		 * The meta object literal for the '{@link SolutionModel.impl.AbstractOptionImpl <em>Abstract Option</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SolutionModel.impl.AbstractOptionImpl
		 * @see SolutionModel.impl.SolutionModelPackageImpl#getAbstractOption()
		 * @generated
		 */
		EClass ABSTRACT_OPTION = eINSTANCE.getAbstractOption();

		/**
		 * The meta object literal for the '<em><b>Abstractcontainer</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_OPTION__ABSTRACTCONTAINER = eINSTANCE.getAbstractOption_Abstractcontainer();

		/**
		 * The meta object literal for the '{@link SolutionModel.impl.AbstractResultImpl <em>Abstract Result</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SolutionModel.impl.AbstractResultImpl
		 * @see SolutionModel.impl.SolutionModelPackageImpl#getAbstractResult()
		 * @generated
		 */
		EClass ABSTRACT_RESULT = eINSTANCE.getAbstractResult();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_RESULT__ATTRIBUTE = eINSTANCE.getAbstractResult_Attribute();

		/**
		 * The meta object literal for the '{@link SolutionModel.IUpdate <em>IUpdate</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SolutionModel.IUpdate
		 * @see SolutionModel.impl.SolutionModelPackageImpl#getIUpdate()
		 * @generated
		 */
		EClass IUPDATE = eINSTANCE.getIUpdate();

		/**
		 * The meta object literal for the '<em><b>Update Similarity</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation IUPDATE___UPDATE_SIMILARITY = eINSTANCE.getIUpdate__UpdateSimilarity();

		/**
		 * The meta object literal for the '{@link SolutionModel.IFamilyModelAdapter <em>IFamily Model Adapter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SolutionModel.IFamilyModelAdapter
		 * @see SolutionModel.impl.SolutionModelPackageImpl#getIFamilyModelAdapter()
		 * @generated
		 */
		EClass IFAMILY_MODEL_ADAPTER = eINSTANCE.getIFamilyModelAdapter();

		/**
		 * The meta object literal for the '<em><b>Get Left Label</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation IFAMILY_MODEL_ADAPTER___GET_LEFT_LABEL = eINSTANCE.getIFamilyModelAdapter__GetLeftLabel();

		/**
		 * The meta object literal for the '<em><b>Get Right Label</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation IFAMILY_MODEL_ADAPTER___GET_RIGHT_LABEL = eINSTANCE.getIFamilyModelAdapter__GetRightLabel();

	}

} //SolutionModelPackage

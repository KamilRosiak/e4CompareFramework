/**
 */
package MetricModel;

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
 * @see MetricModel.MetricModelFactory
 * @model kind="package"
 * @generated
 */
public interface MetricModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "MetricModel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "www.tu-bs.de/isf/metric_model";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "MM";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MetricModelPackage eINSTANCE = MetricModel.impl.MetricModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link MetricModel.impl.AbstractMetricElementImpl <em>Abstract Metric Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see MetricModel.impl.AbstractMetricElementImpl
	 * @see MetricModel.impl.MetricModelPackageImpl#getAbstractMetricElement()
	 * @generated
	 */
	int ABSTRACT_METRIC_ELEMENT = 2;

	/**
	 * The feature id for the '<em><b>Is Active</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_METRIC_ELEMENT__IS_ACTIVE = 0;

	/**
	 * The feature id for the '<em><b>Weight</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_METRIC_ELEMENT__WEIGHT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_METRIC_ELEMENT__NAME = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_METRIC_ELEMENT__DESCRIPTION = 3;

	/**
	 * The number of structural features of the '<em>Abstract Metric Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_METRIC_ELEMENT_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Abstract Metric Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_METRIC_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link MetricModel.impl.AbstractOptionImpl <em>Abstract Option</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see MetricModel.impl.AbstractOptionImpl
	 * @see MetricModel.impl.MetricModelPackageImpl#getAbstractOption()
	 * @generated
	 */
	int ABSTRACT_OPTION = 0;

	/**
	 * The feature id for the '<em><b>Is Active</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_OPTION__IS_ACTIVE = ABSTRACT_METRIC_ELEMENT__IS_ACTIVE;

	/**
	 * The feature id for the '<em><b>Weight</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_OPTION__WEIGHT = ABSTRACT_METRIC_ELEMENT__WEIGHT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_OPTION__NAME = ABSTRACT_METRIC_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_OPTION__DESCRIPTION = ABSTRACT_METRIC_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_OPTION__ATTRIBUTES = ABSTRACT_METRIC_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Options</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_OPTION__OPTIONS = ABSTRACT_METRIC_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Abstract Option</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_OPTION_FEATURE_COUNT = ABSTRACT_METRIC_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Abstract Option</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_OPTION_OPERATION_COUNT = ABSTRACT_METRIC_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link MetricModel.impl.AbstractAttributeImpl <em>Abstract Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see MetricModel.impl.AbstractAttributeImpl
	 * @see MetricModel.impl.MetricModelPackageImpl#getAbstractAttribute()
	 * @generated
	 */
	int ABSTRACT_ATTRIBUTE = 1;

	/**
	 * The feature id for the '<em><b>Is Active</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ATTRIBUTE__IS_ACTIVE = ABSTRACT_METRIC_ELEMENT__IS_ACTIVE;

	/**
	 * The feature id for the '<em><b>Weight</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ATTRIBUTE__WEIGHT = ABSTRACT_METRIC_ELEMENT__WEIGHT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ATTRIBUTE__NAME = ABSTRACT_METRIC_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ATTRIBUTE__DESCRIPTION = ABSTRACT_METRIC_ELEMENT__DESCRIPTION;

	/**
	 * The number of structural features of the '<em>Abstract Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ATTRIBUTE_FEATURE_COUNT = ABSTRACT_METRIC_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Compare</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ATTRIBUTE___COMPARE = ABSTRACT_METRIC_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Abstract Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ATTRIBUTE_OPERATION_COUNT = ABSTRACT_METRIC_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link MetricModel.impl.AbstractMetricImpl <em>Abstract Metric</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see MetricModel.impl.AbstractMetricImpl
	 * @see MetricModel.impl.MetricModelPackageImpl#getAbstractMetric()
	 * @generated
	 */
	int ABSTRACT_METRIC = 3;

	/**
	 * The feature id for the '<em><b>Root</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_METRIC__ROOT = 0;

	/**
	 * The feature id for the '<em><b>Is Weighted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_METRIC__IS_WEIGHTED = 1;

	/**
	 * The number of structural features of the '<em>Abstract Metric</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_METRIC_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Abstract Metric</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_METRIC_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link MetricModel.AbstractOption <em>Abstract Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Option</em>'.
	 * @see MetricModel.AbstractOption
	 * @generated
	 */
	EClass getAbstractOption();

	/**
	 * Returns the meta object for the containment reference list '{@link MetricModel.AbstractOption#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see MetricModel.AbstractOption#getAttributes()
	 * @see #getAbstractOption()
	 * @generated
	 */
	EReference getAbstractOption_Attributes();

	/**
	 * Returns the meta object for the containment reference list '{@link MetricModel.AbstractOption#getOptions <em>Options</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Options</em>'.
	 * @see MetricModel.AbstractOption#getOptions()
	 * @see #getAbstractOption()
	 * @generated
	 */
	EReference getAbstractOption_Options();

	/**
	 * Returns the meta object for class '{@link MetricModel.AbstractAttribute <em>Abstract Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Attribute</em>'.
	 * @see MetricModel.AbstractAttribute
	 * @generated
	 */
	EClass getAbstractAttribute();

	/**
	 * Returns the meta object for the '{@link MetricModel.AbstractAttribute#compare() <em>Compare</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Compare</em>' operation.
	 * @see MetricModel.AbstractAttribute#compare()
	 * @generated
	 */
	EOperation getAbstractAttribute__Compare();

	/**
	 * Returns the meta object for class '{@link MetricModel.AbstractMetricElement <em>Abstract Metric Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Metric Element</em>'.
	 * @see MetricModel.AbstractMetricElement
	 * @generated
	 */
	EClass getAbstractMetricElement();

	/**
	 * Returns the meta object for the attribute '{@link MetricModel.AbstractMetricElement#getIsActive <em>Is Active</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Active</em>'.
	 * @see MetricModel.AbstractMetricElement#getIsActive()
	 * @see #getAbstractMetricElement()
	 * @generated
	 */
	EAttribute getAbstractMetricElement_IsActive();

	/**
	 * Returns the meta object for the attribute '{@link MetricModel.AbstractMetricElement#getWeight <em>Weight</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Weight</em>'.
	 * @see MetricModel.AbstractMetricElement#getWeight()
	 * @see #getAbstractMetricElement()
	 * @generated
	 */
	EAttribute getAbstractMetricElement_Weight();

	/**
	 * Returns the meta object for the attribute '{@link MetricModel.AbstractMetricElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see MetricModel.AbstractMetricElement#getName()
	 * @see #getAbstractMetricElement()
	 * @generated
	 */
	EAttribute getAbstractMetricElement_Name();

	/**
	 * Returns the meta object for the attribute '{@link MetricModel.AbstractMetricElement#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see MetricModel.AbstractMetricElement#getDescription()
	 * @see #getAbstractMetricElement()
	 * @generated
	 */
	EAttribute getAbstractMetricElement_Description();

	/**
	 * Returns the meta object for class '{@link MetricModel.AbstractMetric <em>Abstract Metric</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Metric</em>'.
	 * @see MetricModel.AbstractMetric
	 * @generated
	 */
	EClass getAbstractMetric();

	/**
	 * Returns the meta object for the containment reference '{@link MetricModel.AbstractMetric#getRoot <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Root</em>'.
	 * @see MetricModel.AbstractMetric#getRoot()
	 * @see #getAbstractMetric()
	 * @generated
	 */
	EReference getAbstractMetric_Root();

	/**
	 * Returns the meta object for the attribute '{@link MetricModel.AbstractMetric#getIsWeighted <em>Is Weighted</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Weighted</em>'.
	 * @see MetricModel.AbstractMetric#getIsWeighted()
	 * @see #getAbstractMetric()
	 * @generated
	 */
	EAttribute getAbstractMetric_IsWeighted();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MetricModelFactory getMetricModelFactory();

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
		 * The meta object literal for the '{@link MetricModel.impl.AbstractOptionImpl <em>Abstract Option</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see MetricModel.impl.AbstractOptionImpl
		 * @see MetricModel.impl.MetricModelPackageImpl#getAbstractOption()
		 * @generated
		 */
		EClass ABSTRACT_OPTION = eINSTANCE.getAbstractOption();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_OPTION__ATTRIBUTES = eINSTANCE.getAbstractOption_Attributes();

		/**
		 * The meta object literal for the '<em><b>Options</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_OPTION__OPTIONS = eINSTANCE.getAbstractOption_Options();

		/**
		 * The meta object literal for the '{@link MetricModel.impl.AbstractAttributeImpl <em>Abstract Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see MetricModel.impl.AbstractAttributeImpl
		 * @see MetricModel.impl.MetricModelPackageImpl#getAbstractAttribute()
		 * @generated
		 */
		EClass ABSTRACT_ATTRIBUTE = eINSTANCE.getAbstractAttribute();

		/**
		 * The meta object literal for the '<em><b>Compare</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ABSTRACT_ATTRIBUTE___COMPARE = eINSTANCE.getAbstractAttribute__Compare();

		/**
		 * The meta object literal for the '{@link MetricModel.impl.AbstractMetricElementImpl <em>Abstract Metric Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see MetricModel.impl.AbstractMetricElementImpl
		 * @see MetricModel.impl.MetricModelPackageImpl#getAbstractMetricElement()
		 * @generated
		 */
		EClass ABSTRACT_METRIC_ELEMENT = eINSTANCE.getAbstractMetricElement();

		/**
		 * The meta object literal for the '<em><b>Is Active</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_METRIC_ELEMENT__IS_ACTIVE = eINSTANCE.getAbstractMetricElement_IsActive();

		/**
		 * The meta object literal for the '<em><b>Weight</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_METRIC_ELEMENT__WEIGHT = eINSTANCE.getAbstractMetricElement_Weight();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_METRIC_ELEMENT__NAME = eINSTANCE.getAbstractMetricElement_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_METRIC_ELEMENT__DESCRIPTION = eINSTANCE.getAbstractMetricElement_Description();

		/**
		 * The meta object literal for the '{@link MetricModel.impl.AbstractMetricImpl <em>Abstract Metric</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see MetricModel.impl.AbstractMetricImpl
		 * @see MetricModel.impl.MetricModelPackageImpl#getAbstractMetric()
		 * @generated
		 */
		EClass ABSTRACT_METRIC = eINSTANCE.getAbstractMetric();

		/**
		 * The meta object literal for the '<em><b>Root</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_METRIC__ROOT = eINSTANCE.getAbstractMetric_Root();

		/**
		 * The meta object literal for the '<em><b>Is Weighted</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_METRIC__IS_WEIGHTED = eINSTANCE.getAbstractMetric_IsWeighted();

	}

} //MetricModelPackage

/**
 */
package MetricModel.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import MetricModel.AbstractAttribute;
import MetricModel.AbstractMetric;
import MetricModel.AbstractMetricElement;
import MetricModel.AbstractOption;
import MetricModel.MetricModelPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see MetricModel.MetricModelPackage
 * @generated
 */
public class MetricModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static MetricModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = MetricModelPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MetricModelSwitch<Adapter> modelSwitch =
		new MetricModelSwitch<Adapter>() {
			@Override
			public Adapter caseAbstractOption(AbstractOption object) {
				return createAbstractOptionAdapter();
			}
			@Override
			public Adapter caseAbstractAttribute(AbstractAttribute object) {
				return createAbstractAttributeAdapter();
			}
			@Override
			public Adapter caseAbstractMetricElement(AbstractMetricElement object) {
				return createAbstractMetricElementAdapter();
			}
			@Override
			public Adapter caseAbstractMetric(AbstractMetric object) {
				return createAbstractMetricAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link MetricModel.AbstractOption <em>Abstract Option</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see MetricModel.AbstractOption
	 * @generated
	 */
	public Adapter createAbstractOptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link MetricModel.AbstractAttribute <em>Abstract Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see MetricModel.AbstractAttribute
	 * @generated
	 */
	public Adapter createAbstractAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link MetricModel.AbstractMetricElement <em>Abstract Metric Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see MetricModel.AbstractMetricElement
	 * @generated
	 */
	public Adapter createAbstractMetricElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link MetricModel.AbstractMetric <em>Abstract Metric</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see MetricModel.AbstractMetric
	 * @generated
	 */
	public Adapter createAbstractMetricAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //MetricModelAdapterFactory

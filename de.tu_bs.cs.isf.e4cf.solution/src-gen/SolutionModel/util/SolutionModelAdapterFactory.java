/**
 */
package SolutionModel.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import SolutionModel.AbstractContainer;
import SolutionModel.AbstractOption;
import SolutionModel.AbstractResult;
import SolutionModel.AbstractSolutionElement;
import SolutionModel.IFamilyModelAdapter;
import SolutionModel.IUpdate;
import SolutionModel.SolutionModelPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see SolutionModel.SolutionModelPackage
 * @generated
 */
public class SolutionModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static SolutionModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SolutionModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = SolutionModelPackage.eINSTANCE;
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
	protected SolutionModelSwitch<Adapter> modelSwitch =
		new SolutionModelSwitch<Adapter>() {
			@Override
			public <T extends EObject> Adapter caseAbstractSolutionElement(AbstractSolutionElement<T> object) {
				return createAbstractSolutionElementAdapter();
			}
			@Override
			public <T extends EObject> Adapter caseAbstractContainer(AbstractContainer<T> object) {
				return createAbstractContainerAdapter();
			}
			@Override
			public Adapter caseAbstractOption(AbstractOption object) {
				return createAbstractOptionAdapter();
			}
			@Override
			public Adapter caseAbstractResult(AbstractResult object) {
				return createAbstractResultAdapter();
			}
			@Override
			public Adapter caseIUpdate(IUpdate object) {
				return createIUpdateAdapter();
			}
			@Override
			public Adapter caseIFamilyModelAdapter(IFamilyModelAdapter object) {
				return createIFamilyModelAdapterAdapter();
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
	 * Creates a new adapter for an object of class '{@link SolutionModel.AbstractSolutionElement <em>Abstract Solution Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see SolutionModel.AbstractSolutionElement
	 * @generated
	 */
	public Adapter createAbstractSolutionElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link SolutionModel.AbstractContainer <em>Abstract Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see SolutionModel.AbstractContainer
	 * @generated
	 */
	public Adapter createAbstractContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link SolutionModel.AbstractOption <em>Abstract Option</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see SolutionModel.AbstractOption
	 * @generated
	 */
	public Adapter createAbstractOptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link SolutionModel.AbstractResult <em>Abstract Result</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see SolutionModel.AbstractResult
	 * @generated
	 */
	public Adapter createAbstractResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link SolutionModel.IUpdate <em>IUpdate</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see SolutionModel.IUpdate
	 * @generated
	 */
	public Adapter createIUpdateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link SolutionModel.IFamilyModelAdapter <em>IFamily Model Adapter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see SolutionModel.IFamilyModelAdapter
	 * @generated
	 */
	public Adapter createIFamilyModelAdapterAdapter() {
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

} //SolutionModelAdapterFactory

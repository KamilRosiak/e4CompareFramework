/**
 */
package SolutionModel.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import SolutionModel.AbstractContainer;
import SolutionModel.AbstractOption;
import SolutionModel.AbstractResult;
import SolutionModel.AbstractSolutionElement;
import SolutionModel.IFamilyModelAdapter;
import SolutionModel.IUpdate;
import SolutionModel.SolutionModelPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see SolutionModel.SolutionModelPackage
 * @generated
 */
public class SolutionModelSwitch<T1> extends Switch<T1> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static SolutionModelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SolutionModelSwitch() {
		if (modelPackage == null) {
			modelPackage = SolutionModelPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T1 doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case SolutionModelPackage.ABSTRACT_SOLUTION_ELEMENT: {
				AbstractSolutionElement<?> abstractSolutionElement = (AbstractSolutionElement<?>)theEObject;
				T1 result = caseAbstractSolutionElement(abstractSolutionElement);
				if (result == null) result = caseIUpdate(abstractSolutionElement);
				if (result == null) result = caseIFamilyModelAdapter(abstractSolutionElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SolutionModelPackage.ABSTRACT_CONTAINER: {
				AbstractContainer<?> abstractContainer = (AbstractContainer<?>)theEObject;
				T1 result = caseAbstractContainer(abstractContainer);
				if (result == null) result = (T1)caseAbstractSolutionElement(abstractContainer);
				if (result == null) result = caseIUpdate(abstractContainer);
				if (result == null) result = caseIFamilyModelAdapter(abstractContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SolutionModelPackage.ABSTRACT_OPTION: {
				AbstractOption abstractOption = (AbstractOption)theEObject;
				T1 result = caseAbstractOption(abstractOption);
				if (result == null) result = (T1)caseAbstractSolutionElement(abstractOption);
				if (result == null) result = caseIUpdate(abstractOption);
				if (result == null) result = caseIFamilyModelAdapter(abstractOption);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SolutionModelPackage.ABSTRACT_RESULT: {
				AbstractResult abstractResult = (AbstractResult)theEObject;
				T1 result = caseAbstractResult(abstractResult);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SolutionModelPackage.IUPDATE: {
				IUpdate iUpdate = (IUpdate)theEObject;
				T1 result = caseIUpdate(iUpdate);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SolutionModelPackage.IFAMILY_MODEL_ADAPTER: {
				IFamilyModelAdapter iFamilyModelAdapter = (IFamilyModelAdapter)theEObject;
				T1 result = caseIFamilyModelAdapter(iFamilyModelAdapter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Solution Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Solution Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends EObject> T1 caseAbstractSolutionElement(AbstractSolutionElement<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends EObject> T1 caseAbstractContainer(AbstractContainer<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Option</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Option</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseAbstractOption(AbstractOption object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Result</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseAbstractResult(AbstractResult object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IUpdate</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IUpdate</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseIUpdate(IUpdate object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IFamily Model Adapter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IFamily Model Adapter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseIFamilyModelAdapter(IFamilyModelAdapter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T1 defaultCase(EObject object) {
		return null;
	}

} //SolutionModelSwitch

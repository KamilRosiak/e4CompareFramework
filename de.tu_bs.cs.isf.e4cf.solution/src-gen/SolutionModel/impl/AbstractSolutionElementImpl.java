/**
 */
package SolutionModel.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import SolutionModel.AbstractSolutionElement;
import SolutionModel.IFamilyModelAdapter;
import SolutionModel.SolutionModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Solution Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link SolutionModel.impl.AbstractSolutionElementImpl#getSimilarity <em>Similarity</em>}</li>
 *   <li>{@link SolutionModel.impl.AbstractSolutionElementImpl#getLeftElement <em>Left Element</em>}</li>
 *   <li>{@link SolutionModel.impl.AbstractSolutionElementImpl#getRightElement <em>Right Element</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AbstractSolutionElementImpl<T extends EObject> extends MinimalEObjectImpl.Container implements AbstractSolutionElement<T> {
	/**
	 * The default value of the '{@link #getSimilarity() <em>Similarity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSimilarity()
	 * @generated
	 * @ordered
	 */
	protected static final float SIMILARITY_EDEFAULT = 0.0F;

	/**
	 * The cached value of the '{@link #getSimilarity() <em>Similarity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSimilarity()
	 * @generated
	 * @ordered
	 */
	protected float similarity = SIMILARITY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLeftElement() <em>Left Element</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLeftElement()
	 * @generated
	 * @ordered
	 */
	protected T leftElement;

	/**
	 * The cached value of the '{@link #getRightElement() <em>Right Element</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRightElement()
	 * @generated
	 * @ordered
	 */
	protected T rightElement;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractSolutionElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SolutionModelPackage.Literals.ABSTRACT_SOLUTION_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public float getSimilarity() {
		return similarity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSimilarity(float newSimilarity) {
		float oldSimilarity = similarity;
		similarity = newSimilarity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SolutionModelPackage.ABSTRACT_SOLUTION_ELEMENT__SIMILARITY, oldSimilarity, similarity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public T getLeftElement() {
		return leftElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLeftElement(T newLeftElement) {
		T oldLeftElement = leftElement;
		leftElement = newLeftElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SolutionModelPackage.ABSTRACT_SOLUTION_ELEMENT__LEFT_ELEMENT, oldLeftElement, leftElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public T getRightElement() {
		return rightElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRightElement(T newRightElement) {
		T oldRightElement = rightElement;
		rightElement = newRightElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SolutionModelPackage.ABSTRACT_SOLUTION_ELEMENT__RIGHT_ELEMENT, oldRightElement, rightElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void resetElement() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLeftLabel() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getRightLabel() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Float updateSimilarity() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SolutionModelPackage.ABSTRACT_SOLUTION_ELEMENT__SIMILARITY:
				return getSimilarity();
			case SolutionModelPackage.ABSTRACT_SOLUTION_ELEMENT__LEFT_ELEMENT:
				return getLeftElement();
			case SolutionModelPackage.ABSTRACT_SOLUTION_ELEMENT__RIGHT_ELEMENT:
				return getRightElement();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SolutionModelPackage.ABSTRACT_SOLUTION_ELEMENT__SIMILARITY:
				setSimilarity((Float)newValue);
				return;
			case SolutionModelPackage.ABSTRACT_SOLUTION_ELEMENT__LEFT_ELEMENT:
				setLeftElement((T)newValue);
				return;
			case SolutionModelPackage.ABSTRACT_SOLUTION_ELEMENT__RIGHT_ELEMENT:
				setRightElement((T)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case SolutionModelPackage.ABSTRACT_SOLUTION_ELEMENT__SIMILARITY:
				setSimilarity(SIMILARITY_EDEFAULT);
				return;
			case SolutionModelPackage.ABSTRACT_SOLUTION_ELEMENT__LEFT_ELEMENT:
				setLeftElement((T)null);
				return;
			case SolutionModelPackage.ABSTRACT_SOLUTION_ELEMENT__RIGHT_ELEMENT:
				setRightElement((T)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SolutionModelPackage.ABSTRACT_SOLUTION_ELEMENT__SIMILARITY:
				return similarity != SIMILARITY_EDEFAULT;
			case SolutionModelPackage.ABSTRACT_SOLUTION_ELEMENT__LEFT_ELEMENT:
				return leftElement != null;
			case SolutionModelPackage.ABSTRACT_SOLUTION_ELEMENT__RIGHT_ELEMENT:
				return rightElement != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == IFamilyModelAdapter.class) {
			switch (baseOperationID) {
				case SolutionModelPackage.IFAMILY_MODEL_ADAPTER___GET_LEFT_LABEL: return SolutionModelPackage.ABSTRACT_SOLUTION_ELEMENT___GET_LEFT_LABEL;
				case SolutionModelPackage.IFAMILY_MODEL_ADAPTER___GET_RIGHT_LABEL: return SolutionModelPackage.ABSTRACT_SOLUTION_ELEMENT___GET_RIGHT_LABEL;
				default: return -1;
			}
		}
		return super.eDerivedOperationID(baseOperationID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case SolutionModelPackage.ABSTRACT_SOLUTION_ELEMENT___RESET_ELEMENT:
				resetElement();
				return null;
			case SolutionModelPackage.ABSTRACT_SOLUTION_ELEMENT___GET_LEFT_LABEL:
				return getLeftLabel();
			case SolutionModelPackage.ABSTRACT_SOLUTION_ELEMENT___GET_RIGHT_LABEL:
				return getRightLabel();
			case SolutionModelPackage.ABSTRACT_SOLUTION_ELEMENT___UPDATE_SIMILARITY:
				return updateSimilarity();
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (Similarity: ");
		result.append(similarity);
		result.append(", leftElement: ");
		result.append(leftElement);
		result.append(", rightElement: ");
		result.append(rightElement);
		result.append(')');
		return result.toString();
	}

} //AbstractSolutionElementImpl

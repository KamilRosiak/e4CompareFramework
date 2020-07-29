/**
 */
package SolutionModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import SolutionModel.AbstractContainer;
import SolutionModel.AbstractOption;
import SolutionModel.AbstractResult;
import SolutionModel.SolutionModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link SolutionModel.impl.AbstractContainerImpl#getAbstractoption <em>Abstractoption</em>}</li>
 *   <li>{@link SolutionModel.impl.AbstractContainerImpl#getAbstractresult <em>Abstractresult</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AbstractContainerImpl<T extends EObject> extends AbstractSolutionElementImpl implements AbstractContainer<T> {
	/**
	 * The cached value of the '{@link #getAbstractoption() <em>Abstractoption</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAbstractoption()
	 * @generated
	 * @ordered
	 */
	protected EList<AbstractOption> abstractoption;

	/**
	 * The cached value of the '{@link #getAbstractresult() <em>Abstractresult</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAbstractresult()
	 * @generated
	 * @ordered
	 */
	protected EList<AbstractResult> abstractresult;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractContainerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SolutionModelPackage.Literals.ABSTRACT_CONTAINER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AbstractOption> getAbstractoption() {
		if (abstractoption == null) {
			abstractoption = new EObjectContainmentEList<AbstractOption>(AbstractOption.class, this, SolutionModelPackage.ABSTRACT_CONTAINER__ABSTRACTOPTION);
		}
		return abstractoption;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AbstractResult> getAbstractresult() {
		if (abstractresult == null) {
			abstractresult = new EObjectContainmentEList<AbstractResult>(AbstractResult.class, this, SolutionModelPackage.ABSTRACT_CONTAINER__ABSTRACTRESULT);
		}
		return abstractresult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SolutionModelPackage.ABSTRACT_CONTAINER__ABSTRACTOPTION:
				return ((InternalEList<?>)getAbstractoption()).basicRemove(otherEnd, msgs);
			case SolutionModelPackage.ABSTRACT_CONTAINER__ABSTRACTRESULT:
				return ((InternalEList<?>)getAbstractresult()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SolutionModelPackage.ABSTRACT_CONTAINER__ABSTRACTOPTION:
				return getAbstractoption();
			case SolutionModelPackage.ABSTRACT_CONTAINER__ABSTRACTRESULT:
				return getAbstractresult();
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
			case SolutionModelPackage.ABSTRACT_CONTAINER__ABSTRACTOPTION:
				getAbstractoption().clear();
				getAbstractoption().addAll((Collection<? extends AbstractOption>)newValue);
				return;
			case SolutionModelPackage.ABSTRACT_CONTAINER__ABSTRACTRESULT:
				getAbstractresult().clear();
				getAbstractresult().addAll((Collection<? extends AbstractResult>)newValue);
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
			case SolutionModelPackage.ABSTRACT_CONTAINER__ABSTRACTOPTION:
				getAbstractoption().clear();
				return;
			case SolutionModelPackage.ABSTRACT_CONTAINER__ABSTRACTRESULT:
				getAbstractresult().clear();
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
			case SolutionModelPackage.ABSTRACT_CONTAINER__ABSTRACTOPTION:
				return abstractoption != null && !abstractoption.isEmpty();
			case SolutionModelPackage.ABSTRACT_CONTAINER__ABSTRACTRESULT:
				return abstractresult != null && !abstractresult.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //AbstractContainerImpl

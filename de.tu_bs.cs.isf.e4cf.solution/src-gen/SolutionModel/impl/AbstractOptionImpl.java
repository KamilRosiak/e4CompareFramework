/**
 */
package SolutionModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import SolutionModel.AbstractContainer;
import SolutionModel.AbstractOption;
import SolutionModel.SolutionModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Option</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link SolutionModel.impl.AbstractOptionImpl#getAbstractcontainer <em>Abstractcontainer</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AbstractOptionImpl extends AbstractSolutionElementImpl implements AbstractOption {
	/**
	 * The cached value of the '{@link #getAbstractcontainer() <em>Abstractcontainer</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAbstractcontainer()
	 * @generated
	 * @ordered
	 */
	protected EList<AbstractContainer> abstractcontainer;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractOptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SolutionModelPackage.Literals.ABSTRACT_OPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AbstractContainer> getAbstractcontainer() {
		if (abstractcontainer == null) {
			abstractcontainer = new EObjectContainmentEList<AbstractContainer>(AbstractContainer.class, this, SolutionModelPackage.ABSTRACT_OPTION__ABSTRACTCONTAINER);
		}
		return abstractcontainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SolutionModelPackage.ABSTRACT_OPTION__ABSTRACTCONTAINER:
				return ((InternalEList<?>)getAbstractcontainer()).basicRemove(otherEnd, msgs);
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
			case SolutionModelPackage.ABSTRACT_OPTION__ABSTRACTCONTAINER:
				return getAbstractcontainer();
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
			case SolutionModelPackage.ABSTRACT_OPTION__ABSTRACTCONTAINER:
				getAbstractcontainer().clear();
				getAbstractcontainer().addAll((Collection<? extends AbstractContainer>)newValue);
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
			case SolutionModelPackage.ABSTRACT_OPTION__ABSTRACTCONTAINER:
				getAbstractcontainer().clear();
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
			case SolutionModelPackage.ABSTRACT_OPTION__ABSTRACTCONTAINER:
				return abstractcontainer != null && !abstractcontainer.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //AbstractOptionImpl

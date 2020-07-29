/**
 */
package MetricModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import MetricModel.AbstractAttribute;
import MetricModel.AbstractOption;
import MetricModel.MetricModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Option</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link MetricModel.impl.AbstractOptionImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link MetricModel.impl.AbstractOptionImpl#getOptions <em>Options</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AbstractOptionImpl extends AbstractMetricElementImpl implements AbstractOption {
	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<AbstractAttribute> attributes;

	/**
	 * The cached value of the '{@link #getOptions() <em>Options</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOptions()
	 * @generated
	 * @ordered
	 */
	protected EList<AbstractOption> options;

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
		return MetricModelPackage.Literals.ABSTRACT_OPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AbstractAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentEList<AbstractAttribute>(AbstractAttribute.class, this, MetricModelPackage.ABSTRACT_OPTION__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AbstractOption> getOptions() {
		if (options == null) {
			options = new EObjectContainmentEList<AbstractOption>(AbstractOption.class, this, MetricModelPackage.ABSTRACT_OPTION__OPTIONS);
		}
		return options;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MetricModelPackage.ABSTRACT_OPTION__ATTRIBUTES:
				return ((InternalEList<?>)getAttributes()).basicRemove(otherEnd, msgs);
			case MetricModelPackage.ABSTRACT_OPTION__OPTIONS:
				return ((InternalEList<?>)getOptions()).basicRemove(otherEnd, msgs);
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
			case MetricModelPackage.ABSTRACT_OPTION__ATTRIBUTES:
				return getAttributes();
			case MetricModelPackage.ABSTRACT_OPTION__OPTIONS:
				return getOptions();
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
			case MetricModelPackage.ABSTRACT_OPTION__ATTRIBUTES:
				getAttributes().clear();
				getAttributes().addAll((Collection<? extends AbstractAttribute>)newValue);
				return;
			case MetricModelPackage.ABSTRACT_OPTION__OPTIONS:
				getOptions().clear();
				getOptions().addAll((Collection<? extends AbstractOption>)newValue);
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
			case MetricModelPackage.ABSTRACT_OPTION__ATTRIBUTES:
				getAttributes().clear();
				return;
			case MetricModelPackage.ABSTRACT_OPTION__OPTIONS:
				getOptions().clear();
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
			case MetricModelPackage.ABSTRACT_OPTION__ATTRIBUTES:
				return attributes != null && !attributes.isEmpty();
			case MetricModelPackage.ABSTRACT_OPTION__OPTIONS:
				return options != null && !options.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //AbstractOptionImpl

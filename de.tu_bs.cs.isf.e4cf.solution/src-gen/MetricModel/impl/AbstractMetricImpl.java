/**
 */
package MetricModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import MetricModel.AbstractMetric;
import MetricModel.AbstractOption;
import MetricModel.MetricModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Metric</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link MetricModel.impl.AbstractMetricImpl#getRoot <em>Root</em>}</li>
 *   <li>{@link MetricModel.impl.AbstractMetricImpl#getIsWeighted <em>Is Weighted</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AbstractMetricImpl extends MinimalEObjectImpl.Container implements AbstractMetric {
	/**
	 * The cached value of the '{@link #getRoot() <em>Root</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRoot()
	 * @generated
	 * @ordered
	 */
	protected AbstractOption root;

	/**
	 * The default value of the '{@link #getIsWeighted() <em>Is Weighted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsWeighted()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean IS_WEIGHTED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIsWeighted() <em>Is Weighted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsWeighted()
	 * @generated
	 * @ordered
	 */
	protected Boolean isWeighted = IS_WEIGHTED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractMetricImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetricModelPackage.Literals.ABSTRACT_METRIC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AbstractOption getRoot() {
		return root;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRoot(AbstractOption newRoot, NotificationChain msgs) {
		AbstractOption oldRoot = root;
		root = newRoot;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MetricModelPackage.ABSTRACT_METRIC__ROOT, oldRoot, newRoot);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRoot(AbstractOption newRoot) {
		if (newRoot != root) {
			NotificationChain msgs = null;
			if (root != null)
				msgs = ((InternalEObject)root).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MetricModelPackage.ABSTRACT_METRIC__ROOT, null, msgs);
			if (newRoot != null)
				msgs = ((InternalEObject)newRoot).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MetricModelPackage.ABSTRACT_METRIC__ROOT, null, msgs);
			msgs = basicSetRoot(newRoot, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricModelPackage.ABSTRACT_METRIC__ROOT, newRoot, newRoot));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Boolean getIsWeighted() {
		return isWeighted;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIsWeighted(Boolean newIsWeighted) {
		Boolean oldIsWeighted = isWeighted;
		isWeighted = newIsWeighted;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetricModelPackage.ABSTRACT_METRIC__IS_WEIGHTED, oldIsWeighted, isWeighted));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MetricModelPackage.ABSTRACT_METRIC__ROOT:
				return basicSetRoot(null, msgs);
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
			case MetricModelPackage.ABSTRACT_METRIC__ROOT:
				return getRoot();
			case MetricModelPackage.ABSTRACT_METRIC__IS_WEIGHTED:
				return getIsWeighted();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MetricModelPackage.ABSTRACT_METRIC__ROOT:
				setRoot((AbstractOption)newValue);
				return;
			case MetricModelPackage.ABSTRACT_METRIC__IS_WEIGHTED:
				setIsWeighted((Boolean)newValue);
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
			case MetricModelPackage.ABSTRACT_METRIC__ROOT:
				setRoot((AbstractOption)null);
				return;
			case MetricModelPackage.ABSTRACT_METRIC__IS_WEIGHTED:
				setIsWeighted(IS_WEIGHTED_EDEFAULT);
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
			case MetricModelPackage.ABSTRACT_METRIC__ROOT:
				return root != null;
			case MetricModelPackage.ABSTRACT_METRIC__IS_WEIGHTED:
				return IS_WEIGHTED_EDEFAULT == null ? isWeighted != null : !IS_WEIGHTED_EDEFAULT.equals(isWeighted);
		}
		return super.eIsSet(featureID);
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
		result.append(" (isWeighted: ");
		result.append(isWeighted);
		result.append(')');
		return result.toString();
	}

} //AbstractMetricImpl

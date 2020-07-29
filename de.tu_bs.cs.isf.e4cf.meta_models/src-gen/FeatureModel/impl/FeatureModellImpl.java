/**
 */
package FeatureModel.impl;

import FeatureModel.Calculations;
import FeatureModel.Comments;
import FeatureModel.Constraints;
import FeatureModel.FeatureModelPackage;
import FeatureModel.FeatureModell;
import FeatureModel.FeatureOrder;
import FeatureModel.Properties;
import FeatureModel.Struct;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature Modell</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link FeatureModel.impl.FeatureModellImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link FeatureModel.impl.FeatureModellImpl#getStruct <em>Struct</em>}</li>
 *   <li>{@link FeatureModel.impl.FeatureModellImpl#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link FeatureModel.impl.FeatureModellImpl#getCalculations <em>Calculations</em>}</li>
 *   <li>{@link FeatureModel.impl.FeatureModellImpl#getComments <em>Comments</em>}</li>
 *   <li>{@link FeatureModel.impl.FeatureModellImpl#getFeatureOrder <em>Feature Order</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FeatureModellImpl extends MinimalEObjectImpl.Container implements FeatureModell {
	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected Properties properties;

	/**
	 * The cached value of the '{@link #getStruct() <em>Struct</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStruct()
	 * @generated
	 * @ordered
	 */
	protected Struct struct;

	/**
	 * The cached value of the '{@link #getConstraints() <em>Constraints</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraints()
	 * @generated
	 * @ordered
	 */
	protected Constraints constraints;

	/**
	 * The cached value of the '{@link #getCalculations() <em>Calculations</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCalculations()
	 * @generated
	 * @ordered
	 */
	protected Calculations calculations;

	/**
	 * The cached value of the '{@link #getComments() <em>Comments</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComments()
	 * @generated
	 * @ordered
	 */
	protected Comments comments;

	/**
	 * The cached value of the '{@link #getFeatureOrder() <em>Feature Order</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureOrder()
	 * @generated
	 * @ordered
	 */
	protected FeatureOrder featureOrder;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FeatureModellImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FeatureModelPackage.Literals.FEATURE_MODELL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Properties getProperties() {
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProperties(Properties newProperties, NotificationChain msgs) {
		Properties oldProperties = properties;
		properties = newProperties;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FeatureModelPackage.FEATURE_MODELL__PROPERTIES, oldProperties, newProperties);
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
	public void setProperties(Properties newProperties) {
		if (newProperties != properties) {
			NotificationChain msgs = null;
			if (properties != null)
				msgs = ((InternalEObject)properties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FeatureModelPackage.FEATURE_MODELL__PROPERTIES, null, msgs);
			if (newProperties != null)
				msgs = ((InternalEObject)newProperties).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FeatureModelPackage.FEATURE_MODELL__PROPERTIES, null, msgs);
			msgs = basicSetProperties(newProperties, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureModelPackage.FEATURE_MODELL__PROPERTIES, newProperties, newProperties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Struct getStruct() {
		return struct;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStruct(Struct newStruct, NotificationChain msgs) {
		Struct oldStruct = struct;
		struct = newStruct;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FeatureModelPackage.FEATURE_MODELL__STRUCT, oldStruct, newStruct);
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
	public void setStruct(Struct newStruct) {
		if (newStruct != struct) {
			NotificationChain msgs = null;
			if (struct != null)
				msgs = ((InternalEObject)struct).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FeatureModelPackage.FEATURE_MODELL__STRUCT, null, msgs);
			if (newStruct != null)
				msgs = ((InternalEObject)newStruct).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FeatureModelPackage.FEATURE_MODELL__STRUCT, null, msgs);
			msgs = basicSetStruct(newStruct, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureModelPackage.FEATURE_MODELL__STRUCT, newStruct, newStruct));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Constraints getConstraints() {
		return constraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConstraints(Constraints newConstraints, NotificationChain msgs) {
		Constraints oldConstraints = constraints;
		constraints = newConstraints;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FeatureModelPackage.FEATURE_MODELL__CONSTRAINTS, oldConstraints, newConstraints);
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
	public void setConstraints(Constraints newConstraints) {
		if (newConstraints != constraints) {
			NotificationChain msgs = null;
			if (constraints != null)
				msgs = ((InternalEObject)constraints).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FeatureModelPackage.FEATURE_MODELL__CONSTRAINTS, null, msgs);
			if (newConstraints != null)
				msgs = ((InternalEObject)newConstraints).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FeatureModelPackage.FEATURE_MODELL__CONSTRAINTS, null, msgs);
			msgs = basicSetConstraints(newConstraints, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureModelPackage.FEATURE_MODELL__CONSTRAINTS, newConstraints, newConstraints));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Calculations getCalculations() {
		if (calculations != null && calculations.eIsProxy()) {
			InternalEObject oldCalculations = (InternalEObject)calculations;
			calculations = (Calculations)eResolveProxy(oldCalculations);
			if (calculations != oldCalculations) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FeatureModelPackage.FEATURE_MODELL__CALCULATIONS, oldCalculations, calculations));
			}
		}
		return calculations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Calculations basicGetCalculations() {
		return calculations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCalculations(Calculations newCalculations) {
		Calculations oldCalculations = calculations;
		calculations = newCalculations;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureModelPackage.FEATURE_MODELL__CALCULATIONS, oldCalculations, calculations));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Comments getComments() {
		if (comments != null && comments.eIsProxy()) {
			InternalEObject oldComments = (InternalEObject)comments;
			comments = (Comments)eResolveProxy(oldComments);
			if (comments != oldComments) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FeatureModelPackage.FEATURE_MODELL__COMMENTS, oldComments, comments));
			}
		}
		return comments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Comments basicGetComments() {
		return comments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setComments(Comments newComments) {
		Comments oldComments = comments;
		comments = newComments;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureModelPackage.FEATURE_MODELL__COMMENTS, oldComments, comments));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureOrder getFeatureOrder() {
		if (featureOrder != null && featureOrder.eIsProxy()) {
			InternalEObject oldFeatureOrder = (InternalEObject)featureOrder;
			featureOrder = (FeatureOrder)eResolveProxy(oldFeatureOrder);
			if (featureOrder != oldFeatureOrder) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FeatureModelPackage.FEATURE_MODELL__FEATURE_ORDER, oldFeatureOrder, featureOrder));
			}
		}
		return featureOrder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureOrder basicGetFeatureOrder() {
		return featureOrder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFeatureOrder(FeatureOrder newFeatureOrder) {
		FeatureOrder oldFeatureOrder = featureOrder;
		featureOrder = newFeatureOrder;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureModelPackage.FEATURE_MODELL__FEATURE_ORDER, oldFeatureOrder, featureOrder));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FeatureModelPackage.FEATURE_MODELL__PROPERTIES:
				return basicSetProperties(null, msgs);
			case FeatureModelPackage.FEATURE_MODELL__STRUCT:
				return basicSetStruct(null, msgs);
			case FeatureModelPackage.FEATURE_MODELL__CONSTRAINTS:
				return basicSetConstraints(null, msgs);
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
			case FeatureModelPackage.FEATURE_MODELL__PROPERTIES:
				return getProperties();
			case FeatureModelPackage.FEATURE_MODELL__STRUCT:
				return getStruct();
			case FeatureModelPackage.FEATURE_MODELL__CONSTRAINTS:
				return getConstraints();
			case FeatureModelPackage.FEATURE_MODELL__CALCULATIONS:
				if (resolve) return getCalculations();
				return basicGetCalculations();
			case FeatureModelPackage.FEATURE_MODELL__COMMENTS:
				if (resolve) return getComments();
				return basicGetComments();
			case FeatureModelPackage.FEATURE_MODELL__FEATURE_ORDER:
				if (resolve) return getFeatureOrder();
				return basicGetFeatureOrder();
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
			case FeatureModelPackage.FEATURE_MODELL__PROPERTIES:
				setProperties((Properties)newValue);
				return;
			case FeatureModelPackage.FEATURE_MODELL__STRUCT:
				setStruct((Struct)newValue);
				return;
			case FeatureModelPackage.FEATURE_MODELL__CONSTRAINTS:
				setConstraints((Constraints)newValue);
				return;
			case FeatureModelPackage.FEATURE_MODELL__CALCULATIONS:
				setCalculations((Calculations)newValue);
				return;
			case FeatureModelPackage.FEATURE_MODELL__COMMENTS:
				setComments((Comments)newValue);
				return;
			case FeatureModelPackage.FEATURE_MODELL__FEATURE_ORDER:
				setFeatureOrder((FeatureOrder)newValue);
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
			case FeatureModelPackage.FEATURE_MODELL__PROPERTIES:
				setProperties((Properties)null);
				return;
			case FeatureModelPackage.FEATURE_MODELL__STRUCT:
				setStruct((Struct)null);
				return;
			case FeatureModelPackage.FEATURE_MODELL__CONSTRAINTS:
				setConstraints((Constraints)null);
				return;
			case FeatureModelPackage.FEATURE_MODELL__CALCULATIONS:
				setCalculations((Calculations)null);
				return;
			case FeatureModelPackage.FEATURE_MODELL__COMMENTS:
				setComments((Comments)null);
				return;
			case FeatureModelPackage.FEATURE_MODELL__FEATURE_ORDER:
				setFeatureOrder((FeatureOrder)null);
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
			case FeatureModelPackage.FEATURE_MODELL__PROPERTIES:
				return properties != null;
			case FeatureModelPackage.FEATURE_MODELL__STRUCT:
				return struct != null;
			case FeatureModelPackage.FEATURE_MODELL__CONSTRAINTS:
				return constraints != null;
			case FeatureModelPackage.FEATURE_MODELL__CALCULATIONS:
				return calculations != null;
			case FeatureModelPackage.FEATURE_MODELL__COMMENTS:
				return comments != null;
			case FeatureModelPackage.FEATURE_MODELL__FEATURE_ORDER:
				return featureOrder != null;
		}
		return super.eIsSet(featureID);
	}

} //FeatureModellImpl

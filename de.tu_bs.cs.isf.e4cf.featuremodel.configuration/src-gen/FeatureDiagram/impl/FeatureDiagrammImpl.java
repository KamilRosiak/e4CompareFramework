/**
 */
package FeatureDiagram.impl;

import CrossTreeConstraints.AbstractConstraint;

import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramPackage;
import FeatureDiagram.FeatureDiagramm;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature Diagramm</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link FeatureDiagram.impl.FeatureDiagrammImpl#getRoot <em>Root</em>}</li>
 *   <li>{@link FeatureDiagram.impl.FeatureDiagrammImpl#getIdentifierIncrement <em>Identifier Increment</em>}</li>
 *   <li>{@link FeatureDiagram.impl.FeatureDiagrammImpl#getConstraints <em>Constraints</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FeatureDiagrammImpl extends MinimalEObjectImpl.Container implements FeatureDiagramm {
	/**
	 * The cached value of the '{@link #getRoot() <em>Root</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRoot()
	 * @generated
	 * @ordered
	 */
	protected Feature root;

	/**
	 * The default value of the '{@link #getIdentifierIncrement() <em>Identifier Increment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdentifierIncrement()
	 * @generated
	 * @ordered
	 */
	protected static final int IDENTIFIER_INCREMENT_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getIdentifierIncrement() <em>Identifier Increment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdentifierIncrement()
	 * @generated
	 * @ordered
	 */
	protected int identifierIncrement = IDENTIFIER_INCREMENT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getConstraints() <em>Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<AbstractConstraint> constraints;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FeatureDiagrammImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FeatureDiagramPackage.Literals.FEATURE_DIAGRAMM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Feature getRoot() {
		return root;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRoot(Feature newRoot, NotificationChain msgs) {
		Feature oldRoot = root;
		root = newRoot;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FeatureDiagramPackage.FEATURE_DIAGRAMM__ROOT, oldRoot, newRoot);
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
	public void setRoot(Feature newRoot) {
		if (newRoot != root) {
			NotificationChain msgs = null;
			if (root != null)
				msgs = ((InternalEObject)root).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FeatureDiagramPackage.FEATURE_DIAGRAMM__ROOT, null, msgs);
			if (newRoot != null)
				msgs = ((InternalEObject)newRoot).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FeatureDiagramPackage.FEATURE_DIAGRAMM__ROOT, null, msgs);
			msgs = basicSetRoot(newRoot, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramPackage.FEATURE_DIAGRAMM__ROOT, newRoot, newRoot));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getIdentifierIncrement() {
		return identifierIncrement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIdentifierIncrement(int newIdentifierIncrement) {
		int oldIdentifierIncrement = identifierIncrement;
		identifierIncrement = newIdentifierIncrement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramPackage.FEATURE_DIAGRAMM__IDENTIFIER_INCREMENT, oldIdentifierIncrement, identifierIncrement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AbstractConstraint> getConstraints() {
		if (constraints == null) {
			constraints = new EObjectContainmentEList<AbstractConstraint>(AbstractConstraint.class, this, FeatureDiagramPackage.FEATURE_DIAGRAMM__CONSTRAINTS);
		}
		return constraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FeatureDiagramPackage.FEATURE_DIAGRAMM__ROOT:
				return basicSetRoot(null, msgs);
			case FeatureDiagramPackage.FEATURE_DIAGRAMM__CONSTRAINTS:
				return ((InternalEList<?>)getConstraints()).basicRemove(otherEnd, msgs);
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
			case FeatureDiagramPackage.FEATURE_DIAGRAMM__ROOT:
				return getRoot();
			case FeatureDiagramPackage.FEATURE_DIAGRAMM__IDENTIFIER_INCREMENT:
				return getIdentifierIncrement();
			case FeatureDiagramPackage.FEATURE_DIAGRAMM__CONSTRAINTS:
				return getConstraints();
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
			case FeatureDiagramPackage.FEATURE_DIAGRAMM__ROOT:
				setRoot((Feature)newValue);
				return;
			case FeatureDiagramPackage.FEATURE_DIAGRAMM__IDENTIFIER_INCREMENT:
				setIdentifierIncrement((Integer)newValue);
				return;
			case FeatureDiagramPackage.FEATURE_DIAGRAMM__CONSTRAINTS:
				getConstraints().clear();
				getConstraints().addAll((Collection<? extends AbstractConstraint>)newValue);
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
			case FeatureDiagramPackage.FEATURE_DIAGRAMM__ROOT:
				setRoot((Feature)null);
				return;
			case FeatureDiagramPackage.FEATURE_DIAGRAMM__IDENTIFIER_INCREMENT:
				setIdentifierIncrement(IDENTIFIER_INCREMENT_EDEFAULT);
				return;
			case FeatureDiagramPackage.FEATURE_DIAGRAMM__CONSTRAINTS:
				getConstraints().clear();
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
			case FeatureDiagramPackage.FEATURE_DIAGRAMM__ROOT:
				return root != null;
			case FeatureDiagramPackage.FEATURE_DIAGRAMM__IDENTIFIER_INCREMENT:
				return identifierIncrement != IDENTIFIER_INCREMENT_EDEFAULT;
			case FeatureDiagramPackage.FEATURE_DIAGRAMM__CONSTRAINTS:
				return constraints != null && !constraints.isEmpty();
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
		result.append(" (identifierIncrement: ");
		result.append(identifierIncrement);
		result.append(')');
		return result.toString();
	}

} //FeatureDiagrammImpl

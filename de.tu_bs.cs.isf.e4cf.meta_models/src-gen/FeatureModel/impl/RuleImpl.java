/**
 */
package FeatureModel.impl;

import FeatureModel.FeatureModelPackage;
import FeatureModel.Opearation;
import FeatureModel.Rule;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link FeatureModel.impl.RuleImpl#getImp <em>Imp</em>}</li>
 *   <li>{@link FeatureModel.impl.RuleImpl#getNot <em>Not</em>}</li>
 *   <li>{@link FeatureModel.impl.RuleImpl#getDisj <em>Disj</em>}</li>
 *   <li>{@link FeatureModel.impl.RuleImpl#getConj <em>Conj</em>}</li>
 *   <li>{@link FeatureModel.impl.RuleImpl#getIff <em>Iff</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RuleImpl extends MinimalEObjectImpl.Container implements Rule {
	/**
	 * The cached value of the '{@link #getImp() <em>Imp</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImp()
	 * @generated
	 * @ordered
	 */
	protected EList<Opearation> imp;

	/**
	 * The cached value of the '{@link #getNot() <em>Not</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNot()
	 * @generated
	 * @ordered
	 */
	protected EList<Opearation> not;

	/**
	 * The cached value of the '{@link #getDisj() <em>Disj</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisj()
	 * @generated
	 * @ordered
	 */
	protected EList<Opearation> disj;

	/**
	 * The cached value of the '{@link #getConj() <em>Conj</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConj()
	 * @generated
	 * @ordered
	 */
	protected EList<Opearation> conj;

	/**
	 * The cached value of the '{@link #getIff() <em>Iff</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIff()
	 * @generated
	 * @ordered
	 */
	protected EList<Opearation> iff;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FeatureModelPackage.Literals.RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Opearation> getImp() {
		if (imp == null) {
			imp = new EObjectContainmentEList<Opearation>(Opearation.class, this, FeatureModelPackage.RULE__IMP);
		}
		return imp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Opearation> getNot() {
		if (not == null) {
			not = new EObjectContainmentEList<Opearation>(Opearation.class, this, FeatureModelPackage.RULE__NOT);
		}
		return not;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Opearation> getDisj() {
		if (disj == null) {
			disj = new EObjectContainmentEList<Opearation>(Opearation.class, this, FeatureModelPackage.RULE__DISJ);
		}
		return disj;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Opearation> getConj() {
		if (conj == null) {
			conj = new EObjectContainmentEList<Opearation>(Opearation.class, this, FeatureModelPackage.RULE__CONJ);
		}
		return conj;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Opearation> getIff() {
		if (iff == null) {
			iff = new EObjectResolvingEList<Opearation>(Opearation.class, this, FeatureModelPackage.RULE__IFF);
		}
		return iff;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FeatureModelPackage.RULE__IMP:
				return ((InternalEList<?>)getImp()).basicRemove(otherEnd, msgs);
			case FeatureModelPackage.RULE__NOT:
				return ((InternalEList<?>)getNot()).basicRemove(otherEnd, msgs);
			case FeatureModelPackage.RULE__DISJ:
				return ((InternalEList<?>)getDisj()).basicRemove(otherEnd, msgs);
			case FeatureModelPackage.RULE__CONJ:
				return ((InternalEList<?>)getConj()).basicRemove(otherEnd, msgs);
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
			case FeatureModelPackage.RULE__IMP:
				return getImp();
			case FeatureModelPackage.RULE__NOT:
				return getNot();
			case FeatureModelPackage.RULE__DISJ:
				return getDisj();
			case FeatureModelPackage.RULE__CONJ:
				return getConj();
			case FeatureModelPackage.RULE__IFF:
				return getIff();
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
			case FeatureModelPackage.RULE__IMP:
				getImp().clear();
				getImp().addAll((Collection<? extends Opearation>)newValue);
				return;
			case FeatureModelPackage.RULE__NOT:
				getNot().clear();
				getNot().addAll((Collection<? extends Opearation>)newValue);
				return;
			case FeatureModelPackage.RULE__DISJ:
				getDisj().clear();
				getDisj().addAll((Collection<? extends Opearation>)newValue);
				return;
			case FeatureModelPackage.RULE__CONJ:
				getConj().clear();
				getConj().addAll((Collection<? extends Opearation>)newValue);
				return;
			case FeatureModelPackage.RULE__IFF:
				getIff().clear();
				getIff().addAll((Collection<? extends Opearation>)newValue);
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
			case FeatureModelPackage.RULE__IMP:
				getImp().clear();
				return;
			case FeatureModelPackage.RULE__NOT:
				getNot().clear();
				return;
			case FeatureModelPackage.RULE__DISJ:
				getDisj().clear();
				return;
			case FeatureModelPackage.RULE__CONJ:
				getConj().clear();
				return;
			case FeatureModelPackage.RULE__IFF:
				getIff().clear();
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
			case FeatureModelPackage.RULE__IMP:
				return imp != null && !imp.isEmpty();
			case FeatureModelPackage.RULE__NOT:
				return not != null && !not.isEmpty();
			case FeatureModelPackage.RULE__DISJ:
				return disj != null && !disj.isEmpty();
			case FeatureModelPackage.RULE__CONJ:
				return conj != null && !conj.isEmpty();
			case FeatureModelPackage.RULE__IFF:
				return iff != null && !iff.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //RuleImpl

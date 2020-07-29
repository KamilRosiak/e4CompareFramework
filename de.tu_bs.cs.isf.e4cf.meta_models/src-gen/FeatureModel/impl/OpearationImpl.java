/**
 */
package FeatureModel.impl;

import FeatureModel.FeatureModelPackage;
import FeatureModel.Opearation;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Opearation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link FeatureModel.impl.OpearationImpl#getVar <em>Var</em>}</li>
 *   <li>{@link FeatureModel.impl.OpearationImpl#getImp <em>Imp</em>}</li>
 *   <li>{@link FeatureModel.impl.OpearationImpl#getNot <em>Not</em>}</li>
 *   <li>{@link FeatureModel.impl.OpearationImpl#getDisj <em>Disj</em>}</li>
 *   <li>{@link FeatureModel.impl.OpearationImpl#getConj <em>Conj</em>}</li>
 *   <li>{@link FeatureModel.impl.OpearationImpl#getIff <em>Iff</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OpearationImpl extends MinimalEObjectImpl.Container implements Opearation {
	/**
	 * The cached value of the '{@link #getVar() <em>Var</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVar()
	 * @generated
	 * @ordered
	 */
	protected EList<String> var;

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
	protected OpearationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FeatureModelPackage.Literals.OPEARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getVar() {
		if (var == null) {
			var = new EDataTypeUniqueEList<String>(String.class, this, FeatureModelPackage.OPEARATION__VAR);
		}
		return var;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Opearation> getImp() {
		if (imp == null) {
			imp = new EObjectContainmentEList<Opearation>(Opearation.class, this, FeatureModelPackage.OPEARATION__IMP);
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
			not = new EObjectContainmentEList<Opearation>(Opearation.class, this, FeatureModelPackage.OPEARATION__NOT);
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
			disj = new EObjectContainmentEList<Opearation>(Opearation.class, this, FeatureModelPackage.OPEARATION__DISJ);
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
			conj = new EObjectContainmentEList<Opearation>(Opearation.class, this, FeatureModelPackage.OPEARATION__CONJ);
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
			iff = new EObjectResolvingEList<Opearation>(Opearation.class, this, FeatureModelPackage.OPEARATION__IFF);
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
			case FeatureModelPackage.OPEARATION__IMP:
				return ((InternalEList<?>)getImp()).basicRemove(otherEnd, msgs);
			case FeatureModelPackage.OPEARATION__NOT:
				return ((InternalEList<?>)getNot()).basicRemove(otherEnd, msgs);
			case FeatureModelPackage.OPEARATION__DISJ:
				return ((InternalEList<?>)getDisj()).basicRemove(otherEnd, msgs);
			case FeatureModelPackage.OPEARATION__CONJ:
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
			case FeatureModelPackage.OPEARATION__VAR:
				return getVar();
			case FeatureModelPackage.OPEARATION__IMP:
				return getImp();
			case FeatureModelPackage.OPEARATION__NOT:
				return getNot();
			case FeatureModelPackage.OPEARATION__DISJ:
				return getDisj();
			case FeatureModelPackage.OPEARATION__CONJ:
				return getConj();
			case FeatureModelPackage.OPEARATION__IFF:
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
			case FeatureModelPackage.OPEARATION__VAR:
				getVar().clear();
				getVar().addAll((Collection<? extends String>)newValue);
				return;
			case FeatureModelPackage.OPEARATION__IMP:
				getImp().clear();
				getImp().addAll((Collection<? extends Opearation>)newValue);
				return;
			case FeatureModelPackage.OPEARATION__NOT:
				getNot().clear();
				getNot().addAll((Collection<? extends Opearation>)newValue);
				return;
			case FeatureModelPackage.OPEARATION__DISJ:
				getDisj().clear();
				getDisj().addAll((Collection<? extends Opearation>)newValue);
				return;
			case FeatureModelPackage.OPEARATION__CONJ:
				getConj().clear();
				getConj().addAll((Collection<? extends Opearation>)newValue);
				return;
			case FeatureModelPackage.OPEARATION__IFF:
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
			case FeatureModelPackage.OPEARATION__VAR:
				getVar().clear();
				return;
			case FeatureModelPackage.OPEARATION__IMP:
				getImp().clear();
				return;
			case FeatureModelPackage.OPEARATION__NOT:
				getNot().clear();
				return;
			case FeatureModelPackage.OPEARATION__DISJ:
				getDisj().clear();
				return;
			case FeatureModelPackage.OPEARATION__CONJ:
				getConj().clear();
				return;
			case FeatureModelPackage.OPEARATION__IFF:
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
			case FeatureModelPackage.OPEARATION__VAR:
				return var != null && !var.isEmpty();
			case FeatureModelPackage.OPEARATION__IMP:
				return imp != null && !imp.isEmpty();
			case FeatureModelPackage.OPEARATION__NOT:
				return not != null && !not.isEmpty();
			case FeatureModelPackage.OPEARATION__DISJ:
				return disj != null && !disj.isEmpty();
			case FeatureModelPackage.OPEARATION__CONJ:
				return conj != null && !conj.isEmpty();
			case FeatureModelPackage.OPEARATION__IFF:
				return iff != null && !iff.isEmpty();
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
		result.append(" (var: ");
		result.append(var);
		result.append(')');
		return result.toString();
	}

} //OpearationImpl

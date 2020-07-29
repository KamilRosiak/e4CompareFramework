/**
 */
package FeatureModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Opearation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link FeatureModel.Opearation#getVar <em>Var</em>}</li>
 *   <li>{@link FeatureModel.Opearation#getImp <em>Imp</em>}</li>
 *   <li>{@link FeatureModel.Opearation#getNot <em>Not</em>}</li>
 *   <li>{@link FeatureModel.Opearation#getDisj <em>Disj</em>}</li>
 *   <li>{@link FeatureModel.Opearation#getConj <em>Conj</em>}</li>
 *   <li>{@link FeatureModel.Opearation#getIff <em>Iff</em>}</li>
 * </ul>
 *
 * @see FeatureModel.FeatureModelPackage#getOpearation()
 * @model
 * @generated
 */
public interface Opearation extends EObject {
	/**
	 * Returns the value of the '<em><b>Var</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Var</em>' attribute list.
	 * @see FeatureModel.FeatureModelPackage#getOpearation_Var()
	 * @model
	 * @generated
	 */
	EList<String> getVar();

	/**
	 * Returns the value of the '<em><b>Imp</b></em>' containment reference list.
	 * The list contents are of type {@link FeatureModel.Opearation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Imp</em>' containment reference list.
	 * @see FeatureModel.FeatureModelPackage#getOpearation_Imp()
	 * @model containment="true"
	 * @generated
	 */
	EList<Opearation> getImp();

	/**
	 * Returns the value of the '<em><b>Not</b></em>' containment reference list.
	 * The list contents are of type {@link FeatureModel.Opearation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Not</em>' containment reference list.
	 * @see FeatureModel.FeatureModelPackage#getOpearation_Not()
	 * @model containment="true"
	 * @generated
	 */
	EList<Opearation> getNot();

	/**
	 * Returns the value of the '<em><b>Disj</b></em>' containment reference list.
	 * The list contents are of type {@link FeatureModel.Opearation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Disj</em>' containment reference list.
	 * @see FeatureModel.FeatureModelPackage#getOpearation_Disj()
	 * @model containment="true"
	 * @generated
	 */
	EList<Opearation> getDisj();

	/**
	 * Returns the value of the '<em><b>Conj</b></em>' containment reference list.
	 * The list contents are of type {@link FeatureModel.Opearation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Conj</em>' containment reference list.
	 * @see FeatureModel.FeatureModelPackage#getOpearation_Conj()
	 * @model containment="true"
	 * @generated
	 */
	EList<Opearation> getConj();

	/**
	 * Returns the value of the '<em><b>Iff</b></em>' reference list.
	 * The list contents are of type {@link FeatureModel.Opearation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Iff</em>' reference list.
	 * @see FeatureModel.FeatureModelPackage#getOpearation_Iff()
	 * @model
	 * @generated
	 */
	EList<Opearation> getIff();

} // Opearation

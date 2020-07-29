/**
 */
package FeatureDiagramModificationSet;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature Model Modification Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link FeatureDiagramModificationSet.FeatureModelModificationSet#getModifications <em>Modifications</em>}</li>
 *   <li>{@link FeatureDiagramModificationSet.FeatureModelModificationSet#getAffectedFeatureModelName <em>Affected Feature Model Name</em>}</li>
 * </ul>
 *
 * @see FeatureDiagramModificationSet.FeatureDiagramModificationSetPackage#getFeatureModelModificationSet()
 * @model
 * @generated
 */
public interface FeatureModelModificationSet extends EObject {
	/**
	 * Returns the value of the '<em><b>Modifications</b></em>' containment reference list.
	 * The list contents are of type {@link FeatureDiagramModificationSet.Modification}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modifications</em>' containment reference list.
	 * @see FeatureDiagramModificationSet.FeatureDiagramModificationSetPackage#getFeatureModelModificationSet_Modifications()
	 * @model containment="true"
	 * @generated
	 */
	EList<Modification> getModifications();

	/**
	 * Returns the value of the '<em><b>Affected Feature Model Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Affected Feature Model Name</em>' attribute.
	 * @see #setAffectedFeatureModelName(String)
	 * @see FeatureDiagramModificationSet.FeatureDiagramModificationSetPackage#getFeatureModelModificationSet_AffectedFeatureModelName()
	 * @model required="true"
	 * @generated
	 */
	String getAffectedFeatureModelName();

	/**
	 * Sets the value of the '{@link FeatureDiagramModificationSet.FeatureModelModificationSet#getAffectedFeatureModelName <em>Affected Feature Model Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Affected Feature Model Name</em>' attribute.
	 * @see #getAffectedFeatureModelName()
	 * @generated
	 */
	void setAffectedFeatureModelName(String value);

} // FeatureModelModificationSet

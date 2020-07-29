/**
 */
package FeatureDiagramModificationSet;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Delta</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link FeatureDiagramModificationSet.Delta#getProperty <em>Property</em>}</li>
 *   <li>{@link FeatureDiagramModificationSet.Delta#getValuePriorChange <em>Value Prior Change</em>}</li>
 *   <li>{@link FeatureDiagramModificationSet.Delta#getValueAfterChange <em>Value After Change</em>}</li>
 * </ul>
 *
 * @see FeatureDiagramModificationSet.FeatureDiagramModificationSetPackage#getDelta()
 * @model
 * @generated
 */
public interface Delta extends EObject {
	/**
	 * Returns the value of the '<em><b>Property</b></em>' attribute.
	 * The literals are from the enumeration {@link FeatureDiagramModificationSet.DeltaProperties}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' attribute.
	 * @see FeatureDiagramModificationSet.DeltaProperties
	 * @see #setProperty(DeltaProperties)
	 * @see FeatureDiagramModificationSet.FeatureDiagramModificationSetPackage#getDelta_Property()
	 * @model required="true"
	 * @generated
	 */
	DeltaProperties getProperty();

	/**
	 * Sets the value of the '{@link FeatureDiagramModificationSet.Delta#getProperty <em>Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property</em>' attribute.
	 * @see FeatureDiagramModificationSet.DeltaProperties
	 * @see #getProperty()
	 * @generated
	 */
	void setProperty(DeltaProperties value);

	/**
	 * Returns the value of the '<em><b>Value Prior Change</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Prior Change</em>' attribute.
	 * @see #setValuePriorChange(String)
	 * @see FeatureDiagramModificationSet.FeatureDiagramModificationSetPackage#getDelta_ValuePriorChange()
	 * @model required="true"
	 * @generated
	 */
	String getValuePriorChange();

	/**
	 * Sets the value of the '{@link FeatureDiagramModificationSet.Delta#getValuePriorChange <em>Value Prior Change</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Prior Change</em>' attribute.
	 * @see #getValuePriorChange()
	 * @generated
	 */
	void setValuePriorChange(String value);

	/**
	 * Returns the value of the '<em><b>Value After Change</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value After Change</em>' attribute.
	 * @see #setValueAfterChange(String)
	 * @see FeatureDiagramModificationSet.FeatureDiagramModificationSetPackage#getDelta_ValueAfterChange()
	 * @model required="true"
	 * @generated
	 */
	String getValueAfterChange();

	/**
	 * Sets the value of the '{@link FeatureDiagramModificationSet.Delta#getValueAfterChange <em>Value After Change</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value After Change</em>' attribute.
	 * @see #getValueAfterChange()
	 * @generated
	 */
	void setValueAfterChange(String value);

} // Delta

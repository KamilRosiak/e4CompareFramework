/**
 */
package MetricModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Metric</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link MetricModel.AbstractMetric#getRoot <em>Root</em>}</li>
 *   <li>{@link MetricModel.AbstractMetric#getIsWeighted <em>Is Weighted</em>}</li>
 * </ul>
 *
 * @see MetricModel.MetricModelPackage#getAbstractMetric()
 * @model abstract="true"
 * @generated
 */
public interface AbstractMetric extends EObject {
	/**
	 * Returns the value of the '<em><b>Root</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root</em>' containment reference.
	 * @see #setRoot(AbstractOption)
	 * @see MetricModel.MetricModelPackage#getAbstractMetric_Root()
	 * @model containment="true" required="true"
	 * @generated
	 */
	AbstractOption getRoot();

	/**
	 * Sets the value of the '{@link MetricModel.AbstractMetric#getRoot <em>Root</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root</em>' containment reference.
	 * @see #getRoot()
	 * @generated
	 */
	void setRoot(AbstractOption value);

	/**
	 * Returns the value of the '<em><b>Is Weighted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Weighted</em>' attribute.
	 * @see #setIsWeighted(Boolean)
	 * @see MetricModel.MetricModelPackage#getAbstractMetric_IsWeighted()
	 * @model required="true"
	 * @generated
	 */
	Boolean getIsWeighted();

	/**
	 * Sets the value of the '{@link MetricModel.AbstractMetric#getIsWeighted <em>Is Weighted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Weighted</em>' attribute.
	 * @see #getIsWeighted()
	 * @generated
	 */
	void setIsWeighted(Boolean value);

} // AbstractMetric

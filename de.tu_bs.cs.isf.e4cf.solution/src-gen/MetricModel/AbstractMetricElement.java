/**
 */
package MetricModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Metric Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link MetricModel.AbstractMetricElement#getIsActive <em>Is Active</em>}</li>
 *   <li>{@link MetricModel.AbstractMetricElement#getWeight <em>Weight</em>}</li>
 *   <li>{@link MetricModel.AbstractMetricElement#getName <em>Name</em>}</li>
 *   <li>{@link MetricModel.AbstractMetricElement#getDescription <em>Description</em>}</li>
 * </ul>
 *
 * @see MetricModel.MetricModelPackage#getAbstractMetricElement()
 * @model abstract="true"
 * @generated
 */
public interface AbstractMetricElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Is Active</b></em>' attribute.
	 * The default value is <code>"FALSE"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Active</em>' attribute.
	 * @see #setIsActive(Boolean)
	 * @see MetricModel.MetricModelPackage#getAbstractMetricElement_IsActive()
	 * @model default="FALSE" required="true"
	 * @generated
	 */
	Boolean getIsActive();

	/**
	 * Sets the value of the '{@link MetricModel.AbstractMetricElement#getIsActive <em>Is Active</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Active</em>' attribute.
	 * @see #getIsActive()
	 * @generated
	 */
	void setIsActive(Boolean value);

	/**
	 * Returns the value of the '<em><b>Weight</b></em>' attribute.
	 * The default value is <code>"0.0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Weight</em>' attribute.
	 * @see #setWeight(float)
	 * @see MetricModel.MetricModelPackage#getAbstractMetricElement_Weight()
	 * @model default="0.0" required="true"
	 * @generated
	 */
	float getWeight();

	/**
	 * Sets the value of the '{@link MetricModel.AbstractMetricElement#getWeight <em>Weight</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Weight</em>' attribute.
	 * @see #getWeight()
	 * @generated
	 */
	void setWeight(float value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see MetricModel.MetricModelPackage#getAbstractMetricElement_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link MetricModel.AbstractMetricElement#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see MetricModel.MetricModelPackage#getAbstractMetricElement_Description()
	 * @model required="true"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link MetricModel.AbstractMetricElement#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

} // AbstractMetricElement

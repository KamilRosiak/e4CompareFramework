/**
 */
package MetricModel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Option</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link MetricModel.AbstractOption#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link MetricModel.AbstractOption#getOptions <em>Options</em>}</li>
 * </ul>
 *
 * @see MetricModel.MetricModelPackage#getAbstractOption()
 * @model abstract="true"
 * @generated
 */
public interface AbstractOption extends AbstractMetricElement {
	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link MetricModel.AbstractAttribute}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see MetricModel.MetricModelPackage#getAbstractOption_Attributes()
	 * @model containment="true"
	 * @generated
	 */
	EList<AbstractAttribute> getAttributes();

	/**
	 * Returns the value of the '<em><b>Options</b></em>' containment reference list.
	 * The list contents are of type {@link MetricModel.AbstractOption}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Options</em>' containment reference list.
	 * @see MetricModel.MetricModelPackage#getAbstractOption_Options()
	 * @model containment="true"
	 * @generated
	 */
	EList<AbstractOption> getOptions();

} // AbstractOption

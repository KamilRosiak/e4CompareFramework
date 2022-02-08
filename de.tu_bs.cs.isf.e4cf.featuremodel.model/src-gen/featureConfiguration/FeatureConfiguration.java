/**
 */
package featureConfiguration;

import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramm;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link featureConfiguration.FeatureConfiguration#getName <em>Name</em>}</li>
 *   <li>{@link featureConfiguration.FeatureConfiguration#getFeatureSelection <em>Feature Selection</em>}</li>
 *   <li>{@link featureConfiguration.FeatureConfiguration#getFeatureDiagram <em>Feature Diagram</em>}</li>
 * </ul>
 *
 * @see featureConfiguration.FeatureConfigurationPackage#getFeatureConfiguration()
 * @model
 * @generated
 */
public interface FeatureConfiguration extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see featureConfiguration.FeatureConfigurationPackage#getFeatureConfiguration_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link featureConfiguration.FeatureConfiguration#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Feature Selection</b></em>' map.
	 * The key is of type {@link FeatureDiagram.Feature},
	 * and the value is of type {@link java.lang.Boolean},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature Selection</em>' map.
	 * @see featureConfiguration.FeatureConfigurationPackage#getFeatureConfiguration_FeatureSelection()
	 * @model mapType="featureConfiguration.FeatureToBooleanMap&lt;FeatureDiagram.Feature, org.eclipse.emf.ecore.EBooleanObject&gt;"
	 * @generated
	 */
	EMap<Feature, Boolean> getFeatureSelection();

	/**
	 * Returns the value of the '<em><b>Feature Diagram</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature Diagram</em>' reference.
	 * @see #setFeatureDiagram(FeatureDiagramm)
	 * @see featureConfiguration.FeatureConfigurationPackage#getFeatureConfiguration_FeatureDiagram()
	 * @model
	 * @generated
	 */
	FeatureDiagramm getFeatureDiagram();

	/**
	 * Sets the value of the '{@link featureConfiguration.FeatureConfiguration#getFeatureDiagram <em>Feature Diagram</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature Diagram</em>' reference.
	 * @see #getFeatureDiagram()
	 * @generated
	 */
	void setFeatureDiagram(FeatureDiagramm value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	String toString();

} // FeatureConfiguration

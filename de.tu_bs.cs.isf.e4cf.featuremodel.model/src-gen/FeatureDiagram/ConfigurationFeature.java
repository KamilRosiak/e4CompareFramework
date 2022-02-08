/**
 */
package FeatureDiagram;

import featureConfiguration.FeatureConfiguration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Configuration Feature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link FeatureDiagram.ConfigurationFeature#getConfigurationfeature <em>Configurationfeature</em>}</li>
 * </ul>
 *
 * @see FeatureDiagram.FeatureDiagramPackage#getConfigurationFeature()
 * @model
 * @generated
 */
public interface ConfigurationFeature extends Feature {
	/**
	 * Returns the value of the '<em><b>Configurationfeature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Configurationfeature</em>' reference.
	 * @see #setConfigurationfeature(FeatureConfiguration)
	 * @see FeatureDiagram.FeatureDiagramPackage#getConfigurationFeature_Configurationfeature()
	 * @model required="true"
	 * @generated
	 */
	FeatureConfiguration getConfigurationfeature();

	/**
	 * Sets the value of the '{@link FeatureDiagram.ConfigurationFeature#getConfigurationfeature <em>Configurationfeature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Configurationfeature</em>' reference.
	 * @see #getConfigurationfeature()
	 * @generated
	 */
	void setConfigurationfeature(FeatureConfiguration value);

} // ConfigurationFeature

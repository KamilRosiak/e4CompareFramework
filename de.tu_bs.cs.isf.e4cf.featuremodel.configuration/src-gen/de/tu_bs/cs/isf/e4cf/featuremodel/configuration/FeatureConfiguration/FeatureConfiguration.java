/**
 */
package de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration;

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
 *   <li>{@link de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfiguration#getName <em>Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfiguration#getFeatureSelection <em>Feature Selection</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfiguration#getFeatureDiagram <em>Feature Diagram</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfigurationPackage#getFeatureConfiguration()
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
	 * @see de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfigurationPackage#getFeatureConfiguration_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfiguration#getName <em>Name</em>}' attribute.
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
	 * @see de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfigurationPackage#getFeatureConfiguration_FeatureSelection()
	 * @model mapType="de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureToBooleanMap&lt;FeatureDiagram.Feature, org.eclipse.emf.ecore.EBooleanObject&gt;"
	 * @generated
	 */
	EMap<Feature, Boolean> getFeatureSelection();

	/**
	 * Returns the value of the '<em><b>Feature Diagram</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature Diagram</em>' reference.
	 * @see #setFeatureDiagram(FeatureDiagramm)
	 * @see de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfigurationPackage#getFeatureConfiguration_FeatureDiagram()
	 * @model
	 * @generated
	 */
	FeatureDiagramm getFeatureDiagram();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfiguration#getFeatureDiagram <em>Feature Diagram</em>}' reference.
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

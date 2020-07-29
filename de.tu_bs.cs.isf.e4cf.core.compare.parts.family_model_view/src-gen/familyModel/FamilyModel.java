/**
 */
package familyModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Family Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link familyModel.FamilyModel#getModels <em>Models</em>}</li>
 *   <li>{@link familyModel.FamilyModel#getVariabilyGroups <em>Variabily Groups</em>}</li>
 *   <li>{@link familyModel.FamilyModel#getFaMoName <em>Fa Mo Name</em>}</li>
 * </ul>
 *
 * @see familyModel.FamilyModelPackage#getFamilyModel()
 * @model
 * @generated
 */
public interface FamilyModel extends EObject {
	/**
	 * Returns the value of the '<em><b>Models</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Models</em>' attribute list.
	 * @see familyModel.FamilyModelPackage#getFamilyModel_Models()
	 * @model
	 * @generated
	 */
	EList<String> getModels();

	/**
	 * Returns the value of the '<em><b>Variabily Groups</b></em>' containment reference list.
	 * The list contents are of type {@link familyModel.VariabilityGroup}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variabily Groups</em>' containment reference list.
	 * @see familyModel.FamilyModelPackage#getFamilyModel_VariabilyGroups()
	 * @model containment="true"
	 * @generated
	 */
	EList<VariabilityGroup> getVariabilyGroups();

	/**
	 * Returns the value of the '<em><b>Fa Mo Name</b></em>' attribute.
	 * The default value is <code>"familModel"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fa Mo Name</em>' attribute.
	 * @see #setFaMoName(String)
	 * @see familyModel.FamilyModelPackage#getFamilyModel_FaMoName()
	 * @model default="familModel" required="true"
	 * @generated
	 */
	String getFaMoName();

	/**
	 * Sets the value of the '{@link familyModel.FamilyModel#getFaMoName <em>Fa Mo Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fa Mo Name</em>' attribute.
	 * @see #getFaMoName()
	 * @generated
	 */
	void setFaMoName(String value);

} // FamilyModel

/**
 */
package familyModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variability Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link familyModel.VariabilityGroup#getVariability <em>Variability</em>}</li>
 *   <li>{@link familyModel.VariabilityGroup#getSubGroups <em>Sub Groups</em>}</li>
 *   <li>{@link familyModel.VariabilityGroup#getElements <em>Elements</em>}</li>
 *   <li>{@link familyModel.VariabilityGroup#getGroupName <em>Group Name</em>}</li>
 * </ul>
 *
 * @see familyModel.FamilyModelPackage#getVariabilityGroup()
 * @model
 * @generated
 */
public interface VariabilityGroup extends EObject {
	/**
	 * Returns the value of the '<em><b>Variability</b></em>' attribute.
	 * The literals are from the enumeration {@link familyModel.VariabilityCategory}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variability</em>' attribute.
	 * @see familyModel.VariabilityCategory
	 * @see #setVariability(VariabilityCategory)
	 * @see familyModel.FamilyModelPackage#getVariabilityGroup_Variability()
	 * @model
	 * @generated
	 */
	VariabilityCategory getVariability();

	/**
	 * Sets the value of the '{@link familyModel.VariabilityGroup#getVariability <em>Variability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variability</em>' attribute.
	 * @see familyModel.VariabilityCategory
	 * @see #getVariability()
	 * @generated
	 */
	void setVariability(VariabilityCategory value);

	/**
	 * Returns the value of the '<em><b>Sub Groups</b></em>' containment reference list.
	 * The list contents are of type {@link familyModel.VariabilityGroup}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Groups</em>' containment reference list.
	 * @see familyModel.FamilyModelPackage#getVariabilityGroup_SubGroups()
	 * @model containment="true"
	 * @generated
	 */
	EList<VariabilityGroup> getSubGroups();

	/**
	 * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
	 * The list contents are of type {@link familyModel.Element}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elements</em>' containment reference list.
	 * @see familyModel.FamilyModelPackage#getVariabilityGroup_Elements()
	 * @model containment="true"
	 * @generated
	 */
	EList<Element> getElements();

	/**
	 * Returns the value of the '<em><b>Group Name</b></em>' attribute.
	 * The default value is <code>"\"\""</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group Name</em>' attribute.
	 * @see #setGroupName(String)
	 * @see familyModel.FamilyModelPackage#getVariabilityGroup_GroupName()
	 * @model default="\"\"" required="true"
	 * @generated
	 */
	String getGroupName();

	/**
	 * Sets the value of the '{@link familyModel.VariabilityGroup#getGroupName <em>Group Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Group Name</em>' attribute.
	 * @see #getGroupName()
	 * @generated
	 */
	void setGroupName(String value);

} // VariabilityGroup

/**
 */
package FeatureDiagram.impl;

import FeatureDiagram.ArtifactReference;
import FeatureDiagram.FeatureDiagramPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Artifact Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link FeatureDiagram.impl.ArtifactReferenceImpl#getArtifactClearName <em>Artifact Clear Name</em>}</li>
 *   <li>{@link FeatureDiagram.impl.ArtifactReferenceImpl#getReferencedArtifactIDs <em>Referenced Artifact IDs</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ArtifactReferenceImpl extends MinimalEObjectImpl.Container implements ArtifactReference {
	/**
	 * The default value of the '{@link #getArtifactClearName() <em>Artifact Clear Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArtifactClearName()
	 * @generated
	 * @ordered
	 */
	protected static final String ARTIFACT_CLEAR_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getArtifactClearName() <em>Artifact Clear Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArtifactClearName()
	 * @generated
	 * @ordered
	 */
	protected String artifactClearName = ARTIFACT_CLEAR_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getReferencedArtifactIDs() <em>Referenced Artifact IDs</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferencedArtifactIDs()
	 * @generated
	 * @ordered
	 */
	protected EList<String> referencedArtifactIDs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArtifactReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FeatureDiagramPackage.Literals.ARTIFACT_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getArtifactClearName() {
		return artifactClearName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setArtifactClearName(String newArtifactClearName) {
		String oldArtifactClearName = artifactClearName;
		artifactClearName = newArtifactClearName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramPackage.ARTIFACT_REFERENCE__ARTIFACT_CLEAR_NAME, oldArtifactClearName, artifactClearName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getReferencedArtifactIDs() {
		if (referencedArtifactIDs == null) {
			referencedArtifactIDs = new EDataTypeUniqueEList<String>(String.class, this, FeatureDiagramPackage.ARTIFACT_REFERENCE__REFERENCED_ARTIFACT_IDS);
		}
		return referencedArtifactIDs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FeatureDiagramPackage.ARTIFACT_REFERENCE__ARTIFACT_CLEAR_NAME:
				return getArtifactClearName();
			case FeatureDiagramPackage.ARTIFACT_REFERENCE__REFERENCED_ARTIFACT_IDS:
				return getReferencedArtifactIDs();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case FeatureDiagramPackage.ARTIFACT_REFERENCE__ARTIFACT_CLEAR_NAME:
				setArtifactClearName((String)newValue);
				return;
			case FeatureDiagramPackage.ARTIFACT_REFERENCE__REFERENCED_ARTIFACT_IDS:
				getReferencedArtifactIDs().clear();
				getReferencedArtifactIDs().addAll((Collection<? extends String>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case FeatureDiagramPackage.ARTIFACT_REFERENCE__ARTIFACT_CLEAR_NAME:
				setArtifactClearName(ARTIFACT_CLEAR_NAME_EDEFAULT);
				return;
			case FeatureDiagramPackage.ARTIFACT_REFERENCE__REFERENCED_ARTIFACT_IDS:
				getReferencedArtifactIDs().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case FeatureDiagramPackage.ARTIFACT_REFERENCE__ARTIFACT_CLEAR_NAME:
				return ARTIFACT_CLEAR_NAME_EDEFAULT == null ? artifactClearName != null : !ARTIFACT_CLEAR_NAME_EDEFAULT.equals(artifactClearName);
			case FeatureDiagramPackage.ARTIFACT_REFERENCE__REFERENCED_ARTIFACT_IDS:
				return referencedArtifactIDs != null && !referencedArtifactIDs.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (artifactClearName: ");
		result.append(artifactClearName);
		result.append(", referencedArtifactIDs: ");
		result.append(referencedArtifactIDs);
		result.append(')');
		return result.toString();
	}

} //ArtifactReferenceImpl

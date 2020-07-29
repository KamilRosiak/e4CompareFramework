/**
 */
package FeatureDiagram.impl;

import FeatureDiagram.ArtifactReference;
import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramPackage;
import FeatureDiagram.GraphicalFeature;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link FeatureDiagram.impl.FeatureImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link FeatureDiagram.impl.FeatureImpl#getName <em>Name</em>}</li>
 *   <li>{@link FeatureDiagram.impl.FeatureImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link FeatureDiagram.impl.FeatureImpl#isMandatory <em>Mandatory</em>}</li>
 *   <li>{@link FeatureDiagram.impl.FeatureImpl#isAlternative <em>Alternative</em>}</li>
 *   <li>{@link FeatureDiagram.impl.FeatureImpl#isOr <em>Or</em>}</li>
 *   <li>{@link FeatureDiagram.impl.FeatureImpl#getGraphicalfeature <em>Graphicalfeature</em>}</li>
 *   <li>{@link FeatureDiagram.impl.FeatureImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link FeatureDiagram.impl.FeatureImpl#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link FeatureDiagram.impl.FeatureImpl#isAbstract <em>Abstract</em>}</li>
 *   <li>{@link FeatureDiagram.impl.FeatureImpl#isHidden <em>Hidden</em>}</li>
 *   <li>{@link FeatureDiagram.impl.FeatureImpl#getArtifactReferences <em>Artifact References</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FeatureImpl extends MinimalEObjectImpl.Container implements Feature {
	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<Feature> children;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = "newFeature";

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = "description";

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The default value of the '{@link #isMandatory() <em>Mandatory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMandatory()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MANDATORY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMandatory() <em>Mandatory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMandatory()
	 * @generated
	 * @ordered
	 */
	protected boolean mandatory = MANDATORY_EDEFAULT;

	/**
	 * The default value of the '{@link #isAlternative() <em>Alternative</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAlternative()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ALTERNATIVE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAlternative() <em>Alternative</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAlternative()
	 * @generated
	 * @ordered
	 */
	protected boolean alternative = ALTERNATIVE_EDEFAULT;

	/**
	 * The default value of the '{@link #isOr() <em>Or</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOr()
	 * @generated
	 * @ordered
	 */
	protected static final boolean OR_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isOr() <em>Or</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOr()
	 * @generated
	 * @ordered
	 */
	protected boolean or = OR_EDEFAULT;

	/**
	 * The cached value of the '{@link #getGraphicalfeature() <em>Graphicalfeature</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGraphicalfeature()
	 * @generated
	 * @ordered
	 */
	protected GraphicalFeature graphicalfeature;

	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected Feature parent;

	/**
	 * The default value of the '{@link #getIdentifier() <em>Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdentifier()
	 * @generated
	 * @ordered
	 */
	protected static final int IDENTIFIER_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getIdentifier() <em>Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdentifier()
	 * @generated
	 * @ordered
	 */
	protected int identifier = IDENTIFIER_EDEFAULT;

	/**
	 * The default value of the '{@link #isAbstract() <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbstract()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ABSTRACT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAbstract() <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbstract()
	 * @generated
	 * @ordered
	 */
	protected boolean abstract_ = ABSTRACT_EDEFAULT;

	/**
	 * The default value of the '{@link #isHidden() <em>Hidden</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHidden()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HIDDEN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHidden() <em>Hidden</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHidden()
	 * @generated
	 * @ordered
	 */
	protected boolean hidden = HIDDEN_EDEFAULT;

	/**
	 * The cached value of the '{@link #getArtifactReferences() <em>Artifact References</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArtifactReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<ArtifactReference> artifactReferences;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FeatureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FeatureDiagramPackage.Literals.FEATURE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Feature> getChildren() {
		if (children == null) {
			children = new EObjectContainmentEList<Feature>(Feature.class, this, FeatureDiagramPackage.FEATURE__CHILDREN);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramPackage.FEATURE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramPackage.FEATURE__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isMandatory() {
		return mandatory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMandatory(boolean newMandatory) {
		boolean oldMandatory = mandatory;
		mandatory = newMandatory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramPackage.FEATURE__MANDATORY, oldMandatory, mandatory));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAlternative() {
		return alternative;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAlternative(boolean newAlternative) {
		boolean oldAlternative = alternative;
		alternative = newAlternative;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramPackage.FEATURE__ALTERNATIVE, oldAlternative, alternative));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isOr() {
		return or;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOr(boolean newOr) {
		boolean oldOr = or;
		or = newOr;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramPackage.FEATURE__OR, oldOr, or));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GraphicalFeature getGraphicalfeature() {
		return graphicalfeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGraphicalfeature(GraphicalFeature newGraphicalfeature, NotificationChain msgs) {
		GraphicalFeature oldGraphicalfeature = graphicalfeature;
		graphicalfeature = newGraphicalfeature;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FeatureDiagramPackage.FEATURE__GRAPHICALFEATURE, oldGraphicalfeature, newGraphicalfeature);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGraphicalfeature(GraphicalFeature newGraphicalfeature) {
		if (newGraphicalfeature != graphicalfeature) {
			NotificationChain msgs = null;
			if (graphicalfeature != null)
				msgs = ((InternalEObject)graphicalfeature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FeatureDiagramPackage.FEATURE__GRAPHICALFEATURE, null, msgs);
			if (newGraphicalfeature != null)
				msgs = ((InternalEObject)newGraphicalfeature).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FeatureDiagramPackage.FEATURE__GRAPHICALFEATURE, null, msgs);
			msgs = basicSetGraphicalfeature(newGraphicalfeature, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramPackage.FEATURE__GRAPHICALFEATURE, newGraphicalfeature, newGraphicalfeature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Feature getParent() {
		if (parent != null && parent.eIsProxy()) {
			InternalEObject oldParent = (InternalEObject)parent;
			parent = (Feature)eResolveProxy(oldParent);
			if (parent != oldParent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FeatureDiagramPackage.FEATURE__PARENT, oldParent, parent));
			}
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Feature basicGetParent() {
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setParent(Feature newParent) {
		Feature oldParent = parent;
		parent = newParent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramPackage.FEATURE__PARENT, oldParent, parent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getIdentifier() {
		return identifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIdentifier(int newIdentifier) {
		int oldIdentifier = identifier;
		identifier = newIdentifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramPackage.FEATURE__IDENTIFIER, oldIdentifier, identifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAbstract() {
		return abstract_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAbstract(boolean newAbstract) {
		boolean oldAbstract = abstract_;
		abstract_ = newAbstract;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramPackage.FEATURE__ABSTRACT, oldAbstract, abstract_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHidden(boolean newHidden) {
		boolean oldHidden = hidden;
		hidden = newHidden;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramPackage.FEATURE__HIDDEN, oldHidden, hidden));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ArtifactReference> getArtifactReferences() {
		if (artifactReferences == null) {
			artifactReferences = new EObjectContainmentEList<ArtifactReference>(ArtifactReference.class, this, FeatureDiagramPackage.FEATURE__ARTIFACT_REFERENCES);
		}
		return artifactReferences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FeatureDiagramPackage.FEATURE__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
			case FeatureDiagramPackage.FEATURE__GRAPHICALFEATURE:
				return basicSetGraphicalfeature(null, msgs);
			case FeatureDiagramPackage.FEATURE__ARTIFACT_REFERENCES:
				return ((InternalEList<?>)getArtifactReferences()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FeatureDiagramPackage.FEATURE__CHILDREN:
				return getChildren();
			case FeatureDiagramPackage.FEATURE__NAME:
				return getName();
			case FeatureDiagramPackage.FEATURE__DESCRIPTION:
				return getDescription();
			case FeatureDiagramPackage.FEATURE__MANDATORY:
				return isMandatory();
			case FeatureDiagramPackage.FEATURE__ALTERNATIVE:
				return isAlternative();
			case FeatureDiagramPackage.FEATURE__OR:
				return isOr();
			case FeatureDiagramPackage.FEATURE__GRAPHICALFEATURE:
				return getGraphicalfeature();
			case FeatureDiagramPackage.FEATURE__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
			case FeatureDiagramPackage.FEATURE__IDENTIFIER:
				return getIdentifier();
			case FeatureDiagramPackage.FEATURE__ABSTRACT:
				return isAbstract();
			case FeatureDiagramPackage.FEATURE__HIDDEN:
				return isHidden();
			case FeatureDiagramPackage.FEATURE__ARTIFACT_REFERENCES:
				return getArtifactReferences();
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
			case FeatureDiagramPackage.FEATURE__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends Feature>)newValue);
				return;
			case FeatureDiagramPackage.FEATURE__NAME:
				setName((String)newValue);
				return;
			case FeatureDiagramPackage.FEATURE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case FeatureDiagramPackage.FEATURE__MANDATORY:
				setMandatory((Boolean)newValue);
				return;
			case FeatureDiagramPackage.FEATURE__ALTERNATIVE:
				setAlternative((Boolean)newValue);
				return;
			case FeatureDiagramPackage.FEATURE__OR:
				setOr((Boolean)newValue);
				return;
			case FeatureDiagramPackage.FEATURE__GRAPHICALFEATURE:
				setGraphicalfeature((GraphicalFeature)newValue);
				return;
			case FeatureDiagramPackage.FEATURE__PARENT:
				setParent((Feature)newValue);
				return;
			case FeatureDiagramPackage.FEATURE__IDENTIFIER:
				setIdentifier((Integer)newValue);
				return;
			case FeatureDiagramPackage.FEATURE__ABSTRACT:
				setAbstract((Boolean)newValue);
				return;
			case FeatureDiagramPackage.FEATURE__HIDDEN:
				setHidden((Boolean)newValue);
				return;
			case FeatureDiagramPackage.FEATURE__ARTIFACT_REFERENCES:
				getArtifactReferences().clear();
				getArtifactReferences().addAll((Collection<? extends ArtifactReference>)newValue);
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
			case FeatureDiagramPackage.FEATURE__CHILDREN:
				getChildren().clear();
				return;
			case FeatureDiagramPackage.FEATURE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case FeatureDiagramPackage.FEATURE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case FeatureDiagramPackage.FEATURE__MANDATORY:
				setMandatory(MANDATORY_EDEFAULT);
				return;
			case FeatureDiagramPackage.FEATURE__ALTERNATIVE:
				setAlternative(ALTERNATIVE_EDEFAULT);
				return;
			case FeatureDiagramPackage.FEATURE__OR:
				setOr(OR_EDEFAULT);
				return;
			case FeatureDiagramPackage.FEATURE__GRAPHICALFEATURE:
				setGraphicalfeature((GraphicalFeature)null);
				return;
			case FeatureDiagramPackage.FEATURE__PARENT:
				setParent((Feature)null);
				return;
			case FeatureDiagramPackage.FEATURE__IDENTIFIER:
				setIdentifier(IDENTIFIER_EDEFAULT);
				return;
			case FeatureDiagramPackage.FEATURE__ABSTRACT:
				setAbstract(ABSTRACT_EDEFAULT);
				return;
			case FeatureDiagramPackage.FEATURE__HIDDEN:
				setHidden(HIDDEN_EDEFAULT);
				return;
			case FeatureDiagramPackage.FEATURE__ARTIFACT_REFERENCES:
				getArtifactReferences().clear();
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
			case FeatureDiagramPackage.FEATURE__CHILDREN:
				return children != null && !children.isEmpty();
			case FeatureDiagramPackage.FEATURE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case FeatureDiagramPackage.FEATURE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case FeatureDiagramPackage.FEATURE__MANDATORY:
				return mandatory != MANDATORY_EDEFAULT;
			case FeatureDiagramPackage.FEATURE__ALTERNATIVE:
				return alternative != ALTERNATIVE_EDEFAULT;
			case FeatureDiagramPackage.FEATURE__OR:
				return or != OR_EDEFAULT;
			case FeatureDiagramPackage.FEATURE__GRAPHICALFEATURE:
				return graphicalfeature != null;
			case FeatureDiagramPackage.FEATURE__PARENT:
				return parent != null;
			case FeatureDiagramPackage.FEATURE__IDENTIFIER:
				return identifier != IDENTIFIER_EDEFAULT;
			case FeatureDiagramPackage.FEATURE__ABSTRACT:
				return abstract_ != ABSTRACT_EDEFAULT;
			case FeatureDiagramPackage.FEATURE__HIDDEN:
				return hidden != HIDDEN_EDEFAULT;
			case FeatureDiagramPackage.FEATURE__ARTIFACT_REFERENCES:
				return artifactReferences != null && !artifactReferences.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(", description: ");
		result.append(description);
		result.append(", mandatory: ");
		result.append(mandatory);
		result.append(", alternative: ");
		result.append(alternative);
		result.append(", or: ");
		result.append(or);
		result.append(", identifier: ");
		result.append(identifier);
		result.append(", abstract: ");
		result.append(abstract_);
		result.append(", hidden: ");
		result.append(hidden);
		result.append(')');
		return result.toString();
	}

} //FeatureImpl

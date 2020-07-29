/**
 */
package FeatureDiagramModificationSet.impl;

import FeatureDiagram.ArtifactReference;

import FeatureDiagramModificationSet.Delta;
import FeatureDiagramModificationSet.FeatureDiagramModificationSetPackage;
import FeatureDiagramModificationSet.Modification;

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
 * An implementation of the model object '<em><b>Modification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link FeatureDiagramModificationSet.impl.ModificationImpl#getFeatureID <em>Feature ID</em>}</li>
 *   <li>{@link FeatureDiagramModificationSet.impl.ModificationImpl#getTimeStamp <em>Time Stamp</em>}</li>
 *   <li>{@link FeatureDiagramModificationSet.impl.ModificationImpl#getDelta <em>Delta</em>}</li>
 *   <li>{@link FeatureDiagramModificationSet.impl.ModificationImpl#getModificationType <em>Modification Type</em>}</li>
 *   <li>{@link FeatureDiagramModificationSet.impl.ModificationImpl#getPrecisionTime <em>Precision Time</em>}</li>
 *   <li>{@link FeatureDiagramModificationSet.impl.ModificationImpl#getReferencedArtifacts <em>Referenced Artifacts</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ModificationImpl extends MinimalEObjectImpl.Container implements Modification {
	/**
	 * The default value of the '{@link #getFeatureID() <em>Feature ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureID()
	 * @generated
	 * @ordered
	 */
	protected static final int FEATURE_ID_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getFeatureID() <em>Feature ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureID()
	 * @generated
	 * @ordered
	 */
	protected int featureID = FEATURE_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getTimeStamp() <em>Time Stamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeStamp()
	 * @generated
	 * @ordered
	 */
	protected static final long TIME_STAMP_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getTimeStamp() <em>Time Stamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeStamp()
	 * @generated
	 * @ordered
	 */
	protected long timeStamp = TIME_STAMP_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDelta() <em>Delta</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDelta()
	 * @generated
	 * @ordered
	 */
	protected Delta delta;

	/**
	 * The default value of the '{@link #getModificationType() <em>Modification Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModificationType()
	 * @generated
	 * @ordered
	 */
	protected static final String MODIFICATION_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getModificationType() <em>Modification Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModificationType()
	 * @generated
	 * @ordered
	 */
	protected String modificationType = MODIFICATION_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getPrecisionTime() <em>Precision Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrecisionTime()
	 * @generated
	 * @ordered
	 */
	protected static final long PRECISION_TIME_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getPrecisionTime() <em>Precision Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrecisionTime()
	 * @generated
	 * @ordered
	 */
	protected long precisionTime = PRECISION_TIME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getReferencedArtifacts() <em>Referenced Artifacts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferencedArtifacts()
	 * @generated
	 * @ordered
	 */
	protected EList<ArtifactReference> referencedArtifacts;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModificationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FeatureDiagramModificationSetPackage.Literals.MODIFICATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getFeatureID() {
		return featureID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFeatureID(int newFeatureID) {
		int oldFeatureID = featureID;
		featureID = newFeatureID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramModificationSetPackage.MODIFICATION__FEATURE_ID, oldFeatureID, featureID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTimeStamp(long newTimeStamp) {
		long oldTimeStamp = timeStamp;
		timeStamp = newTimeStamp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramModificationSetPackage.MODIFICATION__TIME_STAMP, oldTimeStamp, timeStamp));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Delta getDelta() {
		return delta;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDelta(Delta newDelta, NotificationChain msgs) {
		Delta oldDelta = delta;
		delta = newDelta;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FeatureDiagramModificationSetPackage.MODIFICATION__DELTA, oldDelta, newDelta);
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
	public void setDelta(Delta newDelta) {
		if (newDelta != delta) {
			NotificationChain msgs = null;
			if (delta != null)
				msgs = ((InternalEObject)delta).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FeatureDiagramModificationSetPackage.MODIFICATION__DELTA, null, msgs);
			if (newDelta != null)
				msgs = ((InternalEObject)newDelta).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FeatureDiagramModificationSetPackage.MODIFICATION__DELTA, null, msgs);
			msgs = basicSetDelta(newDelta, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramModificationSetPackage.MODIFICATION__DELTA, newDelta, newDelta));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getModificationType() {
		return modificationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setModificationType(String newModificationType) {
		String oldModificationType = modificationType;
		modificationType = newModificationType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramModificationSetPackage.MODIFICATION__MODIFICATION_TYPE, oldModificationType, modificationType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getPrecisionTime() {
		return precisionTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPrecisionTime(long newPrecisionTime) {
		long oldPrecisionTime = precisionTime;
		precisionTime = newPrecisionTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeatureDiagramModificationSetPackage.MODIFICATION__PRECISION_TIME, oldPrecisionTime, precisionTime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ArtifactReference> getReferencedArtifacts() {
		if (referencedArtifacts == null) {
			referencedArtifacts = new EObjectContainmentEList<ArtifactReference>(ArtifactReference.class, this, FeatureDiagramModificationSetPackage.MODIFICATION__REFERENCED_ARTIFACTS);
		}
		return referencedArtifacts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FeatureDiagramModificationSetPackage.MODIFICATION__DELTA:
				return basicSetDelta(null, msgs);
			case FeatureDiagramModificationSetPackage.MODIFICATION__REFERENCED_ARTIFACTS:
				return ((InternalEList<?>)getReferencedArtifacts()).basicRemove(otherEnd, msgs);
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
			case FeatureDiagramModificationSetPackage.MODIFICATION__FEATURE_ID:
				return getFeatureID();
			case FeatureDiagramModificationSetPackage.MODIFICATION__TIME_STAMP:
				return getTimeStamp();
			case FeatureDiagramModificationSetPackage.MODIFICATION__DELTA:
				return getDelta();
			case FeatureDiagramModificationSetPackage.MODIFICATION__MODIFICATION_TYPE:
				return getModificationType();
			case FeatureDiagramModificationSetPackage.MODIFICATION__PRECISION_TIME:
				return getPrecisionTime();
			case FeatureDiagramModificationSetPackage.MODIFICATION__REFERENCED_ARTIFACTS:
				return getReferencedArtifacts();
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
			case FeatureDiagramModificationSetPackage.MODIFICATION__FEATURE_ID:
				setFeatureID((Integer)newValue);
				return;
			case FeatureDiagramModificationSetPackage.MODIFICATION__TIME_STAMP:
				setTimeStamp((Long)newValue);
				return;
			case FeatureDiagramModificationSetPackage.MODIFICATION__DELTA:
				setDelta((Delta)newValue);
				return;
			case FeatureDiagramModificationSetPackage.MODIFICATION__MODIFICATION_TYPE:
				setModificationType((String)newValue);
				return;
			case FeatureDiagramModificationSetPackage.MODIFICATION__PRECISION_TIME:
				setPrecisionTime((Long)newValue);
				return;
			case FeatureDiagramModificationSetPackage.MODIFICATION__REFERENCED_ARTIFACTS:
				getReferencedArtifacts().clear();
				getReferencedArtifacts().addAll((Collection<? extends ArtifactReference>)newValue);
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
			case FeatureDiagramModificationSetPackage.MODIFICATION__FEATURE_ID:
				setFeatureID(FEATURE_ID_EDEFAULT);
				return;
			case FeatureDiagramModificationSetPackage.MODIFICATION__TIME_STAMP:
				setTimeStamp(TIME_STAMP_EDEFAULT);
				return;
			case FeatureDiagramModificationSetPackage.MODIFICATION__DELTA:
				setDelta((Delta)null);
				return;
			case FeatureDiagramModificationSetPackage.MODIFICATION__MODIFICATION_TYPE:
				setModificationType(MODIFICATION_TYPE_EDEFAULT);
				return;
			case FeatureDiagramModificationSetPackage.MODIFICATION__PRECISION_TIME:
				setPrecisionTime(PRECISION_TIME_EDEFAULT);
				return;
			case FeatureDiagramModificationSetPackage.MODIFICATION__REFERENCED_ARTIFACTS:
				getReferencedArtifacts().clear();
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
			case FeatureDiagramModificationSetPackage.MODIFICATION__FEATURE_ID:
				return this.featureID != FEATURE_ID_EDEFAULT;
			case FeatureDiagramModificationSetPackage.MODIFICATION__TIME_STAMP:
				return timeStamp != TIME_STAMP_EDEFAULT;
			case FeatureDiagramModificationSetPackage.MODIFICATION__DELTA:
				return delta != null;
			case FeatureDiagramModificationSetPackage.MODIFICATION__MODIFICATION_TYPE:
				return MODIFICATION_TYPE_EDEFAULT == null ? modificationType != null : !MODIFICATION_TYPE_EDEFAULT.equals(modificationType);
			case FeatureDiagramModificationSetPackage.MODIFICATION__PRECISION_TIME:
				return precisionTime != PRECISION_TIME_EDEFAULT;
			case FeatureDiagramModificationSetPackage.MODIFICATION__REFERENCED_ARTIFACTS:
				return referencedArtifacts != null && !referencedArtifacts.isEmpty();
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
		result.append(" (featureID: ");
		result.append(featureID);
		result.append(", timeStamp: ");
		result.append(timeStamp);
		result.append(", modificationType: ");
		result.append(modificationType);
		result.append(", precisionTime: ");
		result.append(precisionTime);
		result.append(')');
		return result.toString();
	}

} //ModificationImpl

/**
 */
package FeatureDiagramModificationSet.impl;

import FeatureDiagramModificationSet.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class FeatureDiagramModificationSetFactoryImpl extends EFactoryImpl implements FeatureDiagramModificationSetFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FeatureDiagramModificationSetFactory init() {
		try {
			FeatureDiagramModificationSetFactory theFeatureDiagramModificationSetFactory = (FeatureDiagramModificationSetFactory)EPackage.Registry.INSTANCE.getEFactory(FeatureDiagramModificationSetPackage.eNS_URI);
			if (theFeatureDiagramModificationSetFactory != null) {
				return theFeatureDiagramModificationSetFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new FeatureDiagramModificationSetFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureDiagramModificationSetFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case FeatureDiagramModificationSetPackage.FEATURE_MODEL_MODIFICATION_SET: return createFeatureModelModificationSet();
			case FeatureDiagramModificationSetPackage.MODIFICATION: return createModification();
			case FeatureDiagramModificationSetPackage.DELTA: return createDelta();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case FeatureDiagramModificationSetPackage.DELTA_PROPERTIES:
				return createDeltaPropertiesFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case FeatureDiagramModificationSetPackage.DELTA_PROPERTIES:
				return convertDeltaPropertiesToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureModelModificationSet createFeatureModelModificationSet() {
		FeatureModelModificationSetImpl featureModelModificationSet = new FeatureModelModificationSetImpl();
		return featureModelModificationSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Modification createModification() {
		ModificationImpl modification = new ModificationImpl();
		return modification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Delta createDelta() {
		DeltaImpl delta = new DeltaImpl();
		return delta;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeltaProperties createDeltaPropertiesFromString(EDataType eDataType, String initialValue) {
		DeltaProperties result = DeltaProperties.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDeltaPropertiesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureDiagramModificationSetPackage getFeatureDiagramModificationSetPackage() {
		return (FeatureDiagramModificationSetPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static FeatureDiagramModificationSetPackage getPackage() {
		return FeatureDiagramModificationSetPackage.eINSTANCE;
	}

} //FeatureDiagramModificationSetFactoryImpl

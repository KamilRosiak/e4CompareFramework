/**
 */
package FeatureDiagramModificationSet;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Delta Properties</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see FeatureDiagramModificationSet.FeatureDiagramModificationSetPackage#getDeltaProperties()
 * @model
 * @generated
 */
public enum DeltaProperties implements Enumerator {
	/**
	 * The '<em><b>FEATURE NAME</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FEATURE_NAME_VALUE
	 * @generated
	 * @ordered
	 */
	FEATURE_NAME(0, "FEATURE_NAME", "FEATURE_NAME"),

	/**
	 * The '<em><b>FEATURE ABSTRACTION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FEATURE_ABSTRACTION_VALUE
	 * @generated
	 * @ordered
	 */
	FEATURE_ABSTRACTION(1, "FEATURE_ABSTRACTION", "FEATURE_ABSTRACTION"),

	/**
	 * The '<em><b>FEATURE VISIBILITY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FEATURE_VISIBILITY_VALUE
	 * @generated
	 * @ordered
	 */
	FEATURE_VISIBILITY(2, "FEATURE_VISIBILITY", "FEATURE_VISIBILITY"),

	/**
	 * The '<em><b>FEATURE VARIABILITY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FEATURE_VARIABILITY_VALUE
	 * @generated
	 * @ordered
	 */
	FEATURE_VARIABILITY(3, "FEATURE_VARIABILITY", "FEATURE_VARIABILITY"),

	/**
	 * The '<em><b>FEATURE GROUP VARIABILITY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FEATURE_GROUP_VARIABILITY_VALUE
	 * @generated
	 * @ordered
	 */
	FEATURE_GROUP_VARIABILITY(4, "FEATURE_GROUP_VARIABILITY", "FEATURE_GROUP_VARIABILITY"),

	/**
	 * The '<em><b>LINE TO PARENT RESET</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LINE_TO_PARENT_RESET_VALUE
	 * @generated
	 * @ordered
	 */
	LINE_TO_PARENT_RESET(5, "LINE_TO_PARENT_RESET", "LINE_TO_PARENT_RESET");

	/**
	 * The '<em><b>FEATURE NAME</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FEATURE_NAME
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int FEATURE_NAME_VALUE = 0;

	/**
	 * The '<em><b>FEATURE ABSTRACTION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FEATURE_ABSTRACTION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int FEATURE_ABSTRACTION_VALUE = 1;

	/**
	 * The '<em><b>FEATURE VISIBILITY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FEATURE_VISIBILITY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int FEATURE_VISIBILITY_VALUE = 2;

	/**
	 * The '<em><b>FEATURE VARIABILITY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FEATURE_VARIABILITY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int FEATURE_VARIABILITY_VALUE = 3;

	/**
	 * The '<em><b>FEATURE GROUP VARIABILITY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FEATURE_GROUP_VARIABILITY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int FEATURE_GROUP_VARIABILITY_VALUE = 4;

	/**
	 * The '<em><b>LINE TO PARENT RESET</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LINE_TO_PARENT_RESET
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int LINE_TO_PARENT_RESET_VALUE = 5;

	/**
	 * An array of all the '<em><b>Delta Properties</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final DeltaProperties[] VALUES_ARRAY =
		new DeltaProperties[] {
			FEATURE_NAME,
			FEATURE_ABSTRACTION,
			FEATURE_VISIBILITY,
			FEATURE_VARIABILITY,
			FEATURE_GROUP_VARIABILITY,
			LINE_TO_PARENT_RESET,
		};

	/**
	 * A public read-only list of all the '<em><b>Delta Properties</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<DeltaProperties> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Delta Properties</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static DeltaProperties get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DeltaProperties result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Delta Properties</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static DeltaProperties getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DeltaProperties result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Delta Properties</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static DeltaProperties get(int value) {
		switch (value) {
			case FEATURE_NAME_VALUE: return FEATURE_NAME;
			case FEATURE_ABSTRACTION_VALUE: return FEATURE_ABSTRACTION;
			case FEATURE_VISIBILITY_VALUE: return FEATURE_VISIBILITY;
			case FEATURE_VARIABILITY_VALUE: return FEATURE_VARIABILITY;
			case FEATURE_GROUP_VARIABILITY_VALUE: return FEATURE_GROUP_VARIABILITY;
			case LINE_TO_PARENT_RESET_VALUE: return LINE_TO_PARENT_RESET;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private DeltaProperties(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
	  return value;
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
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //DeltaProperties

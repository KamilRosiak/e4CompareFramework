/**
 */
package de.tu_bs.cs.isf.e4cf.graph.impl;

import de.tu_bs.cs.isf.e4cf.graph.GraphPackage;
import de.tu_bs.cs.isf.e4cf.graph.GraphicElement;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Graphic Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.graph.impl.GraphicElementImpl#getWidth <em>Width</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.graph.impl.GraphicElementImpl#getHeight <em>Height</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.graph.impl.GraphicElementImpl#getXPostion <em>XPostion</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.e4cf.graph.impl.GraphicElementImpl#getYPostion <em>YPostion</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class GraphicElementImpl extends NodeImpl implements GraphicElement {
	/**
	 * The default value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected static final Double WIDTH_EDEFAULT = new Double(0.0);

	/**
	 * The cached value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected Double width = WIDTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected static final Double HEIGHT_EDEFAULT = new Double(0.0);

	/**
	 * The cached value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected Double height = HEIGHT_EDEFAULT;

	/**
	 * The default value of the '{@link #getXPostion() <em>XPostion</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXPostion()
	 * @generated
	 * @ordered
	 */
	protected static final Double XPOSTION_EDEFAULT = new Double(0.0);

	/**
	 * The cached value of the '{@link #getXPostion() <em>XPostion</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXPostion()
	 * @generated
	 * @ordered
	 */
	protected Double xPostion = XPOSTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getYPostion() <em>YPostion</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getYPostion()
	 * @generated
	 * @ordered
	 */
	protected static final Double YPOSTION_EDEFAULT = new Double(0.0);

	/**
	 * The cached value of the '{@link #getYPostion() <em>YPostion</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getYPostion()
	 * @generated
	 * @ordered
	 */
	protected Double yPostion = YPOSTION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GraphicElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GraphPackage.Literals.GRAPHIC_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Double getWidth() {
		return width;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setWidth(Double newWidth) {
		Double oldWidth = width;
		width = newWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GRAPHIC_ELEMENT__WIDTH, oldWidth, width));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Double getHeight() {
		return height;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHeight(Double newHeight) {
		Double oldHeight = height;
		height = newHeight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GRAPHIC_ELEMENT__HEIGHT, oldHeight, height));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Double getXPostion() {
		return xPostion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setXPostion(Double newXPostion) {
		Double oldXPostion = xPostion;
		xPostion = newXPostion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GRAPHIC_ELEMENT__XPOSTION, oldXPostion, xPostion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Double getYPostion() {
		return yPostion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setYPostion(Double newYPostion) {
		Double oldYPostion = yPostion;
		yPostion = newYPostion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GRAPHIC_ELEMENT__YPOSTION, oldYPostion, yPostion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GraphPackage.GRAPHIC_ELEMENT__WIDTH:
				return getWidth();
			case GraphPackage.GRAPHIC_ELEMENT__HEIGHT:
				return getHeight();
			case GraphPackage.GRAPHIC_ELEMENT__XPOSTION:
				return getXPostion();
			case GraphPackage.GRAPHIC_ELEMENT__YPOSTION:
				return getYPostion();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case GraphPackage.GRAPHIC_ELEMENT__WIDTH:
				setWidth((Double)newValue);
				return;
			case GraphPackage.GRAPHIC_ELEMENT__HEIGHT:
				setHeight((Double)newValue);
				return;
			case GraphPackage.GRAPHIC_ELEMENT__XPOSTION:
				setXPostion((Double)newValue);
				return;
			case GraphPackage.GRAPHIC_ELEMENT__YPOSTION:
				setYPostion((Double)newValue);
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
			case GraphPackage.GRAPHIC_ELEMENT__WIDTH:
				setWidth(WIDTH_EDEFAULT);
				return;
			case GraphPackage.GRAPHIC_ELEMENT__HEIGHT:
				setHeight(HEIGHT_EDEFAULT);
				return;
			case GraphPackage.GRAPHIC_ELEMENT__XPOSTION:
				setXPostion(XPOSTION_EDEFAULT);
				return;
			case GraphPackage.GRAPHIC_ELEMENT__YPOSTION:
				setYPostion(YPOSTION_EDEFAULT);
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
			case GraphPackage.GRAPHIC_ELEMENT__WIDTH:
				return WIDTH_EDEFAULT == null ? width != null : !WIDTH_EDEFAULT.equals(width);
			case GraphPackage.GRAPHIC_ELEMENT__HEIGHT:
				return HEIGHT_EDEFAULT == null ? height != null : !HEIGHT_EDEFAULT.equals(height);
			case GraphPackage.GRAPHIC_ELEMENT__XPOSTION:
				return XPOSTION_EDEFAULT == null ? xPostion != null : !XPOSTION_EDEFAULT.equals(xPostion);
			case GraphPackage.GRAPHIC_ELEMENT__YPOSTION:
				return YPOSTION_EDEFAULT == null ? yPostion != null : !YPOSTION_EDEFAULT.equals(yPostion);
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
		result.append(" (width: ");
		result.append(width);
		result.append(", height: ");
		result.append(height);
		result.append(", xPostion: ");
		result.append(xPostion);
		result.append(", yPostion: ");
		result.append(yPostion);
		result.append(')');
		return result.toString();
	}

} //GraphicElementImpl

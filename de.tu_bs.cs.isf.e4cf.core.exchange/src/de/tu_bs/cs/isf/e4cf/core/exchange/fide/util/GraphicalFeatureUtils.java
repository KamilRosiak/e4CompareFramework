package de.tu_bs.cs.isf.e4cf.core.exchange.fide.util;

/**
 * Utility class to handle sizing of a graphical feature representation.
 * 
 * @author Dibo Gonda (y0085182)
 * @date 12.09.19
 */
public class GraphicalFeatureUtils {
	
	private static final double ROOT_POS_X = 0.0;
	private static final double ROOT_POS_Y = 0.0;
	private static final double MARGIN_X = 10.0;
	private static final double MARGIN_Y = 20.0;
	
	private static final double HEIGHT = 38.0;
	private static final double WIDTH_PER_CHAR = 10.0;
	
	/**
	 * @return x-position of the root feature
	 */
	public static double getRootPosX() {
		return ROOT_POS_X;
	}
	
	/**
	 * @return y-position of the root feature
	 */
	public static double getRootPosY() {
		return ROOT_POS_Y;
	}
	
	/**
	 * @return margin between child features
	 */
	public static double getMarginX() {
		return MARGIN_X;
	}
	
	/**
	 * @return minimal margin between a child and a parent feature
	 */
	public static double getMarginY() {
		return MARGIN_Y;
	}
	
	/**
	 * @return height of a feature
	 */
	public static double getHeight() {
		return HEIGHT;
	}
	
	/**
	 * @param name of the feature
	 * @return width of a feature based on the length of its name
	 */
	public static double getWidth(String name) {
		return WIDTH_PER_CHAR * name.length();
	}

}

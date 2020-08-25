package de.tu_bs.cs.isf.e4cf.core.transform;

import java.util.function.Consumer;

public class TransformationHelper {

	/**
	 * A helper class to facilitate performing actions on objects after type checking.
	 * 
	 * @author Oliver Urbaniak
	 *
	 * @param <T>
	 */
	public interface Routine<T> {
		
		/**
		 * Applies mapping on casted object.
		 * 
		 * @param clazz
		 * @param object
		 * @return <tt>true</tt> if the mapping was successful
		 */
		default public boolean apply(Class<T> clazz, Object object) {			
			if (clazz.isInstance(object)) {
				map(clazz.cast(object));
				return true;
			} 
			return false;
		}
		
		public void map(T instance);
		
	};
	
	/**
	 * If <b>object</b> is instance of <b>class</b>, then execute <b>routine</b>.
	 * The routine behaves in the same way as {@link Consumer}.
	 * 
	 * @param <T>
	 * @param clazz
	 * @param object
	 * @param routine
	 * @return <tt>true</tt> if the object is an instance of clazz
	 */
	public static <T> boolean ifInstanceOfThen(Object object, Class<T> clazz, Routine<T> routine) {
		return routine.apply(clazz, object);
	}
	
	
}

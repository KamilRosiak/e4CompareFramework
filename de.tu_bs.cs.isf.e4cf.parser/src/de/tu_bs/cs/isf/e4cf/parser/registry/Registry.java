package de.tu_bs.cs.isf.e4cf.parser.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Acts as a registry for elements of type <<b>T</b>>. Actions can be performed on registry elements identified by their key.
 * The execution of such actions may be postponed if the target element is not registered at that time guaranteeing 
 * that every action will be performed.<br>
 * The registry uses a entry-to-key mapping to distinguish entries e.g. variables are mapped onto their respective names.
 * 
 * @author Oliver Urbaniak
 *
 * @param <T>
 */
public class Registry<T> {
	
	/**
	 * Registry action performed on exactly one instance of type <<b>T</b>>. 
	 * It can be used to reference distant elements within the compiler pass.
	 * 
	 * @author Oliver Urbaniak
	 *
	 * @param <T>
	 */
	public interface RegistryAction<T> {
		
		/**
		 * Performs the action on the instance
		 * 
		 * @param instance
		 * @return
		 */
		public void execute(T instance);
	}
	
	Map<String, T> instances;
	Map<String, List<RegistryAction<T>>> actions;
	Function<T, String> keyProvider;
	
	public Registry(Function<T, String> keyProvider) {
		this.keyProvider = keyProvider;
		this.instances = new HashMap<>();
		this.actions = new HashMap<>();
	}
	
	/**
	 * Returns the registered object belonging to the provided key
	 * 
	 * @param <T>
	 * @param key
	 * @return object of type <b><T></b>
	 */
	public T get(String key) {
		return (T) instances.get(key);
	}
	
	/**
	 * Registers <i>instance</i> into the registry performing the stored actions on this instance.
	 * 
	 * @param instance
	 */
	public void register(T instance) {
		if (instance == null) {
			throw new NullPointerException("instance is null.");
		}
		 
		String instanceKey = keyProvider.apply(instance);
		if (instanceKey == null) {
			throw new NullPointerException("instance of type "+instance.getClass().getSimpleName()+" has not valid key.");
		}
		
		// store the instance and perform available actions on this instance
		instances.put(instanceKey, instance);
		List<RegistryAction<T>> actionsForInstance = actions.get(instanceKey);
		if (actionsForInstance != null) {
			actionsForInstance.forEach(action -> action.execute(instance));
			actionsForInstance.clear();
			actions.remove(instanceKey);
		}
	}
	
	public void deregister(T instance) {
		if (instance == null) {
			throw new NullPointerException("instance is null.");
		}
		 
		String instanceKey = keyProvider.apply(instance);
		if (instanceKey != null) {
			instances.remove(instanceKey);
			actions.remove(instanceKey);
		}
	}
	
	public boolean isRegistered(String symbol) {
		return instances.containsKey(symbol);
	}
	
	/**
	 * Perform action <i>regAction</i> on the element identified by <i>symbol</i>.
	 * This method performs the action either immediately if the element is found in the registry
	 * or upon registration of the particular element.
	 * 
	 * @param symbol
	 * @param regAction
	 */
	public void performAction(String symbol, RegistryAction<T> regAction) {
		if (regAction == null) {
			throw new NullPointerException("the registry action is null.");
		}
		
		// if instance is already registered perform action on it, otherwise store action for further registrations
		T targetInstance = instances.get(symbol);
		if (targetInstance != null) {
			regAction.execute(targetInstance);
		} else {
			List<RegistryAction<T>> namedActions = actions.get(symbol);
			if (namedActions == null) {
				namedActions = new ArrayList<RegistryAction<T>>();
				actions.put(symbol, namedActions);
				
			}
			if (!namedActions.contains(regAction)) {
				namedActions.add(regAction);
			}
		}
	}
	
	public boolean hasRemainingActions() {
		return !actions.isEmpty();
	}
	
	/**
	 * Clear the stored instances and action.
	 */
	public void clear() {
		instances.clear();
		actions.clear();
	}
}

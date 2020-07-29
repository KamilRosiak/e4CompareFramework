package de.tu_bs.cs.isf.e4cf.parser.registry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A service that offers a collection of typed registrys for parsing.
 * 
 * @author Oliver Urbaniak
 */
public class RegistryService <E extends INamedEntry> {
	
	private static RegistryService<INamedEntry> instance = null;
	private Map<E, Registry<?>> iecRegistrys;
	
	public RegistryService() {
		iecRegistrys = new HashMap<>();
	}

	@SuppressWarnings("unchecked")
	public static <T extends INamedEntry> RegistryService<T> getInstance() {
		return (RegistryService<T>) (instance != null ? instance : (instance = new RegistryService<INamedEntry>()));
	}
	
	/**
	 * Provide a registry <i>iecRegistry</i> for the given <i>type</i>.
	 * 
	 * @param <T>
	 * @param type
	 * @param iecRegistry
	 */
	public <T> void provide(E type, Registry<T> iecRegistry) {
		if (iecRegistry != null) {
			iecRegistrys.put(type, iecRegistry);
		}
	}
	
	/**
	 * Removes all registered entries from the service. 
	 * After calling, this service holds <b>no</b> registries.
	 */
	public void clear() {
		iecRegistrys.clear();
	}
	
	/**
	 * Removes the registry with the specified type from the service.
	 * 
	 * @param type
	 */
	public void clear(E type) {
		iecRegistrys.remove(type);
	}
	
	/**
	 * Resets each of the stored registries.
	 * After calling, they are still provided but empty.
	 */
	public void reset(boolean allowRemainingActions) {
		Collection<Registry<?>> registries = iecRegistrys.values();
		registries.forEach(registry -> {
			if (allowRemainingActions) {
				registry.clear();								
			} else if (registry.hasRemainingActions()) {
				throw new RuntimeException("Registry() is not allowed to have remaining actions.");
			} 
		});
	}
	
	/**
	 * Resets the registry with the specified type in the service.
	 * Can be parameterized to raise an exception if unused actions are still remaining.
	 * After calling, it is still provided but empty.
	 * 
	 * @param type
	 * @param allowRemainingActions when false raises exception if not all the actions are consumed
	 */
	public void reset(E type, boolean allowRemainingActions) {
		Registry<?> registry = iecRegistrys.get(type);
		if (registry != null) {
			if (allowRemainingActions) {
				registry.clear();				
			} else if (registry.hasRemainingActions()) {
				throw new RuntimeException("Registry("+type.getName()+") is not allowed to have remaining actions.");
			}
		}
	}
	
	/**
	 * Query the typed registry denoted by <i>type</i>.
	 * 
	 * @param <T>
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> Registry<T> getService(E type) {
		Registry<?> reg = iecRegistrys.get(type);
  		if (reg == null) {
  			throw new NullPointerException("Desired registry("+type.getName()+") is not available");
  		}
  		
  		return (Registry<T>) reg;
	}
}

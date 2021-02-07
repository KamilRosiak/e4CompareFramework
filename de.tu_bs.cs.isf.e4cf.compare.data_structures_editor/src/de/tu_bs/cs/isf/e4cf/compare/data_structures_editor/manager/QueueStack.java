package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Class to initialize a List for Undo Actions and its operations
 * 
 * @author Team05
 *
 */

public class QueueStack<T> {

	private List<T> dataCollection;

	QueueStack() {
		dataCollection = new LinkedList<>();
	}

	/**
	 * Pushes an item on to the stack
	 * 
	 * @param item
	 */
	void push(T item) {
		dataCollection.add(0, item);
	}

	/**
	 * Pops an item from the stack
	 * 
	 * @return item which was popped
	 */
	Optional<T> pop() {
		if (dataCollection.size() > 0)
			return Optional.of(dataCollection.remove(dataCollection.size() - 1));
		else
			return Optional.empty();
	}
}

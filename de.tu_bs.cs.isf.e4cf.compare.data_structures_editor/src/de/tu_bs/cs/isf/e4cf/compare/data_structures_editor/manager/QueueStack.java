package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class QueueStack<T> {

	private List<T> dataCollection;

	QueueStack() {
		dataCollection = new LinkedList<>();
	}

	void push(T item) {
		dataCollection.add(0, item);
	}

	Optional<T> pop() {
		if (dataCollection.size() > 0)
			return Optional.of(dataCollection.remove(dataCollection.size() - 1));
		else
			return Optional.empty();
	}

	void clear() {
		dataCollection.clear();
	}

}
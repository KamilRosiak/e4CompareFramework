package de.tu_bs.cs.isf.e4cf.core.file_structure.tree.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

public class DepthFirstTreeIterator implements Iterator<FileTreeElement> {
	
	Deque<FileTreeElement> _iterableElements = new ArrayDeque<>();
	
	public DepthFirstTreeIterator(FileTreeElement element) {
		_iterableElements.push(element);
	}
	
	@Override
	public boolean hasNext() {
		return !_iterableElements.isEmpty();
	}

	@Override
	public FileTreeElement next() {
		if (!_iterableElements.isEmpty()) {
			FileTreeElement next = _iterableElements.pop();
			_iterableElements.addAll(next.getChildren());
			return next;
		} else {
			throw new NoSuchElementException();
		}
	}

}

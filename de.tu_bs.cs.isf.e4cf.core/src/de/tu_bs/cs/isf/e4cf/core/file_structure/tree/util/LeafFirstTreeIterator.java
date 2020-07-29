package de.tu_bs.cs.isf.e4cf.core.file_structure.tree.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

public class LeafFirstTreeIterator implements Iterator<FileTreeElement> {
	
	Deque<FileTreeElement> _iterableElements = new ArrayDeque<>();
	
	public LeafFirstTreeIterator(FileTreeElement element) {
		recurse(element);
	}
	
	public void recurse(FileTreeElement element) {
		_iterableElements.push(element);
		for (FileTreeElement e : element.getChildren()) {
			recurse(e);
		}
	}

	@Override
	public boolean hasNext() {
		return !_iterableElements.isEmpty();
	}

	@Override
	public FileTreeElement next() {
		return _iterableElements.pop();
	}

}

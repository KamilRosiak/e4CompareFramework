package de.tu_bs.cs.isf.e4cf.compare.data_structures.impl;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * An Iterator for the e4cf Node interface.
 * Based on: https://stackoverflow.com/a/57964192
 *
 */
public class NodeIterator implements Iterator<Node> {
	
    private final Deque<Iterator<Node>> iterators = new ArrayDeque<>();
    private final boolean breadthFirst;
    
    public NodeIterator(Node node, boolean breadthFirst) {
        this.iterators.add(Collections.singleton(node).iterator());
        this.breadthFirst = breadthFirst;
    }
    
    @Override
    public boolean hasNext() {
        return !this.iterators.isEmpty();
    }
    
    @Override
    public Node next() {
        Iterator<Node> iterator = this.iterators.removeFirst();
        Node node = iterator.next();
        if (iterator.hasNext()) {
            this.iterators.addFirst(iterator);
        }
        if (!node.getChildren().isEmpty()) {
            if (this.breadthFirst) {
                this.iterators.addLast(node.getChildren().iterator());
            } else {
                this.iterators.addFirst(node.getChildren().iterator());
            }
        }
        return node;
    }
}

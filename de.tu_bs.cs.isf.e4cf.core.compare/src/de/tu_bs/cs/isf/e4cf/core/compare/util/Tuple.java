package de.tu_bs.cs.isf.e4cf.core.compare.util;

/**
 * 
 * @author Kamil Rosiak
 *
 * @param <F> The type of the first element in this tuple
 * @param <S> The type of the second element in this tuple
 */
public class Tuple<F,S> {
    private F firstElement;
    private S secondElement;
    
    public Tuple(F firstElement, S secondElement) {
	setFirstElement(firstElement);
	setSecondElement(secondElement);
    }

    public F getFirstElement() {
	return firstElement;
    }

    public void setFirstElement(F firstElement) {
	this.firstElement = firstElement;
    }

    public S getSecondElement() {
	return secondElement;
    }

    public void setSecondElement(S secondElement) {
	this.secondElement = secondElement;
    }
}

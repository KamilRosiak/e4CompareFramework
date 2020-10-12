package de.tu_bs.cs.isf.e4cf.core.compare.util;

/**
 * 
 * @author Kamil Rosiak
 *
 * @param <F> The type of the first element in this triple
 * @param <S> The type of the second element in this triple
 * @param <T> The type of the third element in this triple
 */
public class Triple <F,S,T> {
    private F firstElement;
    private S secondElement;
    private T thirdElement;
    
    public Triple(F firstElement, S secondElement, T thirdElement) {
	setFirstElement(firstElement);
	setSecondElement(secondElement);
	setThirdElement(thirdElement);
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

    public T getThirdElement() {
	return thirdElement;
    }

    public void setThirdElement(T thirdElement) {
	this.thirdElement = thirdElement;
    }
}
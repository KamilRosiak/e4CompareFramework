package de.tu_bs.cs.isf.e4cf.compare.interfaces;

public abstract class AbstractComparsion<K> implements Comparison<K> {
    private K leftElement;
    private K rightElement;
    private float similarity;
    
    public AbstractComparsion(K leftElement, K rightElement) {
	setLeftElement(leftElement);
	setRightElement(rightElement);
    }
    
    @Override
    public K getLeftElement() {
	return leftElement;
    }

    @Override
    public K getRightElement() {
	return rightElement;
    }

    @Override
    public float getSimilarityValue() {
	return similarity;
    }
    @Override
    public void setLeftElement(K leftElement) {
	this.leftElement = leftElement;
    }
    @Override
    public void setRightElement(K rightElement) {
	this.rightElement = rightElement;
    }
    @Override
    public void setSimilarity(float similarity) {
	this.similarity = similarity;
    }

}

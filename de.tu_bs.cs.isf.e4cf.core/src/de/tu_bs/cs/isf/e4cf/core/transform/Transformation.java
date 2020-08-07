package de.tu_bs.cs.isf.e4cf.core.transform;

import java.util.function.Function;

public interface Transformation<R> extends Function<Object, R> {
	
	public boolean canTransform(Object object);
}

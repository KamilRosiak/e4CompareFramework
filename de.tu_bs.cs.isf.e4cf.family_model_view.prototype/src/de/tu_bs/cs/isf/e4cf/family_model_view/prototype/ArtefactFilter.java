package de.tu_bs.cs.isf.e4cf.family_model_view.prototype;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Accepts or rejects elements according to a filter.
 * Can be used in conjunction with {@link Stream}.
 * 
 * @author Oliver Urbaniak
 *
 */
public interface ArtefactFilter extends Function<Object, Boolean> {

}

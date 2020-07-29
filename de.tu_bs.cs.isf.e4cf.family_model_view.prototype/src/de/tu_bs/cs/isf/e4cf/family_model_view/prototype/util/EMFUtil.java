package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.util;

import java.util.function.BiFunction;

import org.eclipse.emf.ecore.EObject;

public class EMFUtil {

	public static <R> void applyOnContainmentStructure(R parent, EObject eobject, BiFunction<R, EObject, R> parentObjectConsumer) {
		R newParent = parentObjectConsumer.apply(parent, eobject);
		for (EObject containedObject: eobject.eContents()) {
			applyOnContainmentStructure(newParent, containedObject, parentObjectConsumer);
		}
	}
}

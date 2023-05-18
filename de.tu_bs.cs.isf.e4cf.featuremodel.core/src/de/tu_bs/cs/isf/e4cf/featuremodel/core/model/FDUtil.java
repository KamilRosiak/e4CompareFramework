package de.tu_bs.cs.isf.e4cf.featuremodel.core.model;

import java.util.function.Consumer;

public class FDUtil {
	
	public static boolean DFS(IFeature startFeature, IFeature findFeature) {
		if (startFeature.equals(findFeature)) {
			return true;
		} else {
			for (IFeature child : startFeature.getChildren()) {
				if (DFS(child, findFeature)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static void DFSVisitor(IFeature startFeature, Consumer<IFeature> featureConsumer) {
		featureConsumer.accept(startFeature);
		for (IFeature child : startFeature.getChildren()) {
			DFSVisitor(child, featureConsumer);
		}
	}

}

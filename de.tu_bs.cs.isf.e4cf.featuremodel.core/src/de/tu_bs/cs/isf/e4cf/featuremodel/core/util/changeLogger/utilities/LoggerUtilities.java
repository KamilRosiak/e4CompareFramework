package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.changeLogger.utilities;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import FeatureDiagram.ArtifactReference;
import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramFactory;
import FeatureDiagramModificationSet.Delta;
import FeatureDiagramModificationSet.DeltaProperties;
import FeatureDiagramModificationSet.FeatureDiagramModificationSetFactory;
import FeatureDiagramModificationSet.Modification;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.elements.FXGraphicalFeature;

public class LoggerUtilities {
	
	/**
	 * TODO comment
	 * @return
	 */
	public static String getFeatureVariabilityGroup(Feature feature) {
		if (feature.isAlternative()) {
			return "alternative";
		} else if (feature.isOr()) {
			return "or";
		} else {
			return "and";
		}
	}
	
	/**
	 * FOR FXGRAPHICAL FEATURES
	 * Creates a Feature Diagram Modification Object and sets the affected features ID and current system time as timestamp
	 * @param feature The feature affected by the change
	 * @return Feature Diagram Change object 
	 */
	public static Modification createModification(FXGraphicalFeature feature, String modificationType) {
		return createModification(feature.getFeature(), modificationType);
	}
	
	/**
	 * This method copies the referencedArtifactID objects from the provided feature, hence creating a new object,
	 * and adds the list of copied referencedArtifactIDs to the modification
	 * @param modification A modification to which the copied list of referencedArtifactID objects is added to
	 * @param artifactReferences The list of referencedArtifactID object from a certain feature, which corresponds
	 * to the modification
	 */
	private static void processArtifactReferences(Modification modification, EList<ArtifactReference> artifactReferences) {
		List<ArtifactReference> copiedReferences = new ArrayList<ArtifactReference>();
		for (ArtifactReference reference : artifactReferences) {
			ArtifactReference copiedReference = FeatureDiagramFactory.eINSTANCE.createArtifactReference();
			copiedReference.setArtifactClearName(reference.getArtifactClearName());
			for (String artifactID : reference.getReferencedArtifactIDs()) {
				copiedReference.getReferencedArtifactIDs().add(artifactID);
			}
			copiedReferences.add(copiedReference);
		}
		modification.getReferencedArtifacts().addAll(copiedReferences);
		
	}

	/**
	 * FOR FEATURES
	 * Creates a Feature Diagram Modification Object and sets the affected features ID and current system time as timestamp
	 * @param feature The feature affected by the change
	 * @return Feature Diagram Change object 
	 */
	public static Modification createModification(Feature feature, String modificationType) {
		Modification modification = FeatureDiagramModificationSetFactory.eINSTANCE.createModification();
		modification.setTimeStamp(System.currentTimeMillis());
		modification.setPrecisionTime(System.nanoTime());
		modification.setFeatureID(feature.getIdentifier());
		modification.setModificationType(modificationType);	
		processArtifactReferences(modification, feature.getArtifactReferences());
		return modification;
	}
	
	public static Modification createModification(String modificationType) {
		Modification modification = FeatureDiagramModificationSetFactory.eINSTANCE.createModification();
		modification.setTimeStamp(System.currentTimeMillis());
		modification.setModificationType(modificationType);	
		return modification;
	}
	
	/**
	 * todo comment
	 * @param feature
	 * @param modification
	 * @return
	 */
	public static Delta createFeatureDelta(Modification modification, DeltaProperties deltaProperty, 
			String valuePriorChange, String valueAfterChange) {
		Delta delta = FeatureDiagramModificationSetFactory.eINSTANCE.createDelta();
		delta.setProperty(deltaProperty);
		delta.setValuePriorChange(valuePriorChange);
		delta.setValueAfterChange(valueAfterChange);
		modification.setDelta(delta);
		return delta;
	}
}

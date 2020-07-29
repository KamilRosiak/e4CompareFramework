package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.algorithms.AbegoPlacement;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.algorithms.AestheticPlacement;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.algorithms.RecursivelyPlacement;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.algorithms.WalkersAlgorithm;

public class PlacementAlgoFactory {
	
	   public static PlacementAlgorithm getPlacementAlgorithm(String placementAlgorithm){
	      if(placementAlgorithm == null){
	         return null;
	      }		
	      if(placementAlgorithm.equals(PlacemantConsts.WALKERS_ALGORITHM)) {
	    	  return new WalkersAlgorithm();
	      }
	      else if (placementAlgorithm.equals(PlacemantConsts.MAX_WIDTH_PLACEMENT_ALGORITHM)) {
	    	  return new AestheticPlacement();
	      }
	      else if (placementAlgorithm.equals(PlacemantConsts.REC_PLACEMENT)) {
	    	  return new RecursivelyPlacement();
	      }
	      else if(placementAlgorithm.equals(PlacemantConsts.ABEGO_PLACEMENT)) {
	    	  return new AbegoPlacement();
	      }
	      
	      //other algos here
//	      else if(placementAlgorithm.equalsIgnoreCase(Whatever)){
//	         return new OtherPlacementalgo();
//	         
//	      } 
	      
	      return null;
	   }

}

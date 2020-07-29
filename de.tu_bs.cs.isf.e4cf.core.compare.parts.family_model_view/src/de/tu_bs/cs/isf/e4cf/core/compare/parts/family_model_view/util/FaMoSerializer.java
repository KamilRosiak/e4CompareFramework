package de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.util;

import de.tu_bs.cs.isf.familymining.ppu_iec.rcp_e4.EMFModelLoader.impl.EMFModelLoader;
import familyModel.FamilyModel;

public class FaMoSerializer {

	/**
	 * This method serializes a FamilyModel 
	 */
	public static void encode(FamilyModel faMo, String path, String extension) {
		EMFModelLoader.save(faMo, "familModel", path, extension);		
	}
}

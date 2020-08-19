package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.components;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.IconProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewFiles;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariabilityCategory;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint;
import javafx.scene.image.Image;

public class DefaultFamilyModelIconProvider implements IconProvider {

	@Override
	public List<Image> getIcon(Object object) {
		// for a variation Point return the standard variability icons
		if (object instanceof VariationPoint) {
			VariationPoint varPoint = (VariationPoint) object;
			Image icon = getVariabilityIcon(varPoint.getVariabilityCategory());
			if (icon != null) {
				return Arrays.asList(icon);
			}
		}
		
		// for a family model root return its corresponding icon
		if (object instanceof FamilyModel) {
			return Arrays.asList(new Image(FamilyModelViewFiles.FV_ROOT_16));
		}
		
		return Collections.emptyList();
	}

	private Image getVariabilityIcon(VariabilityCategory variabilityCategory) {
		switch (variabilityCategory) {
			case MANDATORY: 	return new Image(FamilyModelViewFiles.FV_MANDATORY_16);
			case ALTERNATIVE: 	return new Image(FamilyModelViewFiles.FV_ALTERNATIVE_16);
			case OPTIONAL:		return new Image(FamilyModelViewFiles.FV_OPTIONAL_16);
			case UNSET: 		return new Image(FamilyModelViewFiles.FV_SUB_SYSTEM_16);
			default:			return null;
		}
	}

}

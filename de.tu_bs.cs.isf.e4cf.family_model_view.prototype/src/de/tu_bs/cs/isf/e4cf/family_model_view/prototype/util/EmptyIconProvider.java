package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.util;

import java.util.Collections;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.IconProvider;
import javafx.scene.image.Image;

public class EmptyIconProvider implements IconProvider {

	@Override
	public List<Image> getIcon(Object object) {
		return Collections.emptyList();
	}

}

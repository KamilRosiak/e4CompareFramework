package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.data;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.LabelProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Car;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Wheel;

public class CarLabelProvider implements LabelProvider {

	@Override
	public String getLabel(Object artefact) {
		if (artefact instanceof Car) { return ((Car) artefact).getLabel(); }
		else if (artefact instanceof Wheel) { return ((Wheel) artefact).getModel(); }
		else { return "<no label>";}
	}

}

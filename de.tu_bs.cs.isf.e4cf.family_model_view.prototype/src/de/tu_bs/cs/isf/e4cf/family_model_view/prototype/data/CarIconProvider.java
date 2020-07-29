package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.IconProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Car;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Wheel;
import javafx.scene.image.Image;

public class CarIconProvider implements IconProvider {

	@Override
	public List<Image> getIcon(Object object) {
		if (object instanceof Car) {
			return Arrays.asList(new Image("icons/car/car_16.png"));
		}
		if (object instanceof Wheel) {
			return Arrays.asList(new Image("icons/car/wheel_16.png"));
		}
		
		return Collections.emptyList();
	}

}

package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.data;

import java.util.Arrays;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Car;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.CarFactory;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Wheel;

public class CarModelBuilder {

	protected static CarFactory cf = CarFactory.eINSTANCE;
	
	public static Wheel createWheel(int traction, String model) {
		Wheel wheel = cf.createWheel();
		wheel.setTraction(traction);
		wheel.setModel(model);
		return wheel;
	}
	
	public static Car createCar(String label, Wheel[] wheels) {
		Car car = cf.createCar();
		car.setLabel(label);
		car.getWheels().addAll(Arrays.asList(wheels));
		return car;
	}
}

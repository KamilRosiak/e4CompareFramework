package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.tests;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.data.CarModelBuilder;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.data.FamilyModelDataGen;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Car;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.CarPackage;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.Car.Wheel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelPackage;

public class CarExampleBuilder {

	public static void createAndStoreCarFamilyModel(String path) {
		FamilyModel carFamilyModel = createCarFamilyModel();
		
	}
	
	/**
	 * Creates an example blockoriented family model with blocks as primary elements.
	 * Stores the created model in the workspace path with a fixed name.
	 */
	public static FamilyModel createCarFamilyModel() {
		FamilyModelPackage fmPackage = FamilyModelPackage.eINSTANCE;
		CarPackage carPackage = CarPackage.eINSTANCE;
		
		Car car1 = CarModelBuilder.createCar(
				"car1", 
				new Wheel[] {
						CarModelBuilder.createWheel(10, "VISCO"),
						CarModelBuilder.createWheel(20, "VISCO"),
						CarModelBuilder.createWheel(30, "VISCO"),
						CarModelBuilder.createWheel(40, "VISCO")
				}
		);
		
		Car car2 = CarModelBuilder.createCar(
				"car2", 
				new Wheel[] {
						CarModelBuilder.createWheel(50, "AMD"),
						CarModelBuilder.createWheel(60, "AMD"),
						CarModelBuilder.createWheel(70, "AMD"),
						CarModelBuilder.createWheel(80, "AMD")
				}
		);
		
		FamilyModelDataGen fmDataGenerator = new FamilyModelDataGen();
		FamilyModel fm = fmDataGenerator.createExampleGenericFamilyModel(
				car1, car2, 
				car1.getWheels().get(0), car2.getWheels().get(1), car1.getWheels().get(1));
		
		return fm;
	}
	
	
}

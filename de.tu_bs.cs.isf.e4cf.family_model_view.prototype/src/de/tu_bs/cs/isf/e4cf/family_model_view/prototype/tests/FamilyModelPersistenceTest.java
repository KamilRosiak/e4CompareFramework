package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.tests;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.GenericFamilyModel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.data.CarExtensionProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.persistence.SimpleFMResourceManager;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewStrings;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.components.DefaultFamilyModelExtensionProvider;

class FamilyModelPersistenceTest {

	private static final String FM_ROOT_DIR = "resources/tests/persistence/";
	
	private static final String VARIANT1_REL_PATH = FM_ROOT_DIR+"variant1."+FamilyModelViewStrings.TEST_CAR_FILE_EXTENSION;
	private static final String VARIANT2_REL_PATH = FM_ROOT_DIR+"variant2."+FamilyModelViewStrings.TEST_CAR_FILE_EXTENSION;
	private static final String FM_PATH = 			FM_ROOT_DIR+"ExampleFamilyModel."+FamilyModelViewStrings.FM_DEFAULT_FILE_EXT;
	
	public GenericFamilyModel genericFamilyModel;
	
	@AfterAll
	static void cleanUp() throws Exception {
		new File(VARIANT1_REL_PATH).delete();
		new File(VARIANT2_REL_PATH).delete();
		new File(FM_PATH).delete();
	}
	
	@BeforeEach
	void setUp() throws Exception {
		genericFamilyModel = new GenericFamilyModel(new SimpleFMResourceManager(new CarExtensionProvider(), new DefaultFamilyModelExtensionProvider()));
		genericFamilyModel.setInternalFamilyModel(CarExampleBuilder.createCarFamilyModel());
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	/**
	 * Succeeds if the file was written.
	 */
	@Test
	void successfulSaveFamilyModel() {		
		// manually set the correct variants URIs
		FamilyModel fm = genericFamilyModel.getInternalFamilyModel();
		
		// save the generic family model --> produces several files 
		try {
			Map<EObject, String> resourceMap = new HashMap<>();
			resourceMap.put(fm, FM_PATH);
			resourceMap.put(fm.getVariants().get(0).getInstance(), VARIANT1_REL_PATH);
			resourceMap.put(fm.getVariants().get(1).getInstance(), VARIANT2_REL_PATH);
			
			genericFamilyModel.save(resourceMap);
		} catch (IOException e) {
			fail("IOException was thrown");
		} catch (RuntimeException e1) {
			fail ("RuntimeException was thrown");
		}
		
		// check for the file
		File fmFile = new File(FM_PATH);
		assertTrue(fmFile.exists());
	}
	
	@Test
	void succesfulLoadFamilyModel() {				
		// store a copy of the runtime family model before deserializing the new one
		FamilyModel oldFamilyModel = EcoreUtil.copy(genericFamilyModel.getInternalFamilyModel());
		
		// Manually set the correct variants URIs
		try {
			genericFamilyModel.load(FM_PATH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			fail("exception: "+e.getMessage());
		} catch (RuntimeException e1) {
			fail("exception: "+e1.getMessage());
		}	
		
		FamilyModel familyModel = genericFamilyModel.getInternalFamilyModel();
		assertTrue(EcoreUtil.equals(familyModel, oldFamilyModel), "Both family models are supposed to have the same value after deserialization");
	}
}

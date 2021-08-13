package de.tu_bs.cs.isf.e4cf.compare.data_structures.unittest.roundtrip;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.unittest.TestHelper;

/**
 * Automatically Run JavaReader and Writer on setup suites in resources/test/roundtrip
 * Must be run as JUnit Plug-In Test
 * TODO: Validate Reader
 */
public class RoundtripTests {
	
	private final String ROUNDTRIP_DIR = "resources/test/roundtrip/";
	private final String IN = "IN/";
	private final String OUT = "Out/";
	private final String REF = "Ref/";
	
	private final String SUITE_CORE = "Core/";
	private final String SUITE_ADVANCED = "Advanced/";
	private final String SUITE_INSANE = "Insane/";
	
	private boolean testScenario(String suite, String name) {
		String suite_dir = ROUNDTRIP_DIR + suite;
		String in = suite_dir + IN;
		String out = suite_dir + OUT;
		String ref = suite_dir + REF;
		return TestHelper.testScenario(name, in, out, ref);
	}
	
	private void testSuite(String suite) {
		List<String> scenarios = TestHelper.getScenarios(ROUNDTRIP_DIR + suite + IN);
		
		for (String scenario : scenarios) {
			assertTrue("Scenario Failure: " + suite + scenario, 
					testScenario(suite, scenario));
		}
		System.out.println("Tested " + scenarios.size() + " Scenarios in Suite " + suite);
	}
	
	@Test
	void coreTests() {
		TestHelper.deleteOutputs(ROUNDTRIP_DIR + SUITE_CORE + OUT);
		testSuite(SUITE_CORE);
	}
	
	@Test
	void advancedTests() {
		TestHelper.deleteOutputs(ROUNDTRIP_DIR + SUITE_ADVANCED + OUT);
		testSuite(SUITE_ADVANCED);
	}
	
	@Test
	void insaneTests() {
		TestHelper.deleteOutputs(ROUNDTRIP_DIR + SUITE_INSANE + OUT);
		testSuite(SUITE_INSANE);
	}
	
	
}

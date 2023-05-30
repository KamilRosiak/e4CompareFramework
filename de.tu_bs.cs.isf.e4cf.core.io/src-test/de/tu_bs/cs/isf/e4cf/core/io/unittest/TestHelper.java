package de.tu_bs.cs.isf.e4cf.core.io.unittest;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.github.javaparser.printer.PrettyPrinterConfiguration;
import com.github.javaparser.printer.PrettyPrinterConfiguration.IndentType;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.File;
import de.tu_bs.cs.isf.e4cf.core.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.core.io.writer.JavaWriter2;

public class TestHelper {
	
	public static boolean testScenario(String scenario, String input_dir, String output_dir, String reference_dir) {
		try {
			// Parse Input
			Path inputPath = Paths.get(input_dir + scenario + ".java");
			FileTreeElement input = new File(inputPath.toAbsolutePath().toString());
			Tree t = new ReaderManager().readFile(input);
		
			// Parse Output
			// Generate Content
			PrettyPrinterConfiguration ppc = new PrettyPrinterConfiguration();
			ppc.setIndentType(IndentType.TABS); // Default is spaces
			ppc.setIndentSize(1); // Only one tab to indent
			com.github.javaparser.ast.Node jpRoot = new JavaWriter2().parseE4Tree(null, t.getRoot());
			String outputContent = jpRoot.toString(ppc);
			// Write
			java.io.File output = Paths.get(output_dir + scenario + ".java.java.java").toFile();
			FileUtils.writeStringToFile(output, outputContent, "UTF-8", false);
			
			// Compare Output <-> Reference
			java.io.File reference = Paths.get(reference_dir + scenario + ".java.java.java").toFile();
		
			return FileUtils.contentEquals(output, reference);
		} catch (IOException e) {
			fail("Test Failed because of IO Exception in scenario: " + scenario);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Test Failed because of " + e.getClass().getSimpleName() + " in Scenario " + scenario);
		}
		return false;
	}
	
	public static List<String> getScenarios(String input_dir) {
		ArrayList<String> scenarios = new ArrayList<>();
		java.io.File[] files = Paths.get(input_dir).toFile().listFiles();
		
		if (files == null) {
			fail("No Scenarios Provided in DIR: " + input_dir);
		}
		
		for (int i = 0 ; i < files.length; i++) {
			scenarios.add(files[i].getName().split("\\.")[0]);
		}
		return scenarios;
	}
	
	public static void deleteOutputs(String outputDir) {
		try {
			FileUtils.deleteDirectory(Paths.get(outputDir).toFile());
		} catch (IOException e) {
			System.err.println("Can not delete " + outputDir);
			fail("Could not delete Output directory, are files open somewhere?");
		} catch (IllegalArgumentException e) {
			// Folder doesn't exist, no big deal
		}
	}

}

package de.tu_bs.cs.isf.e4cf.extractive_mple;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.gson.GsonImportService;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.tree.TreeReader;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLEPlatformUtil;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;

public class Evaluation {

	public static void main(String[] args) {
		evaluate();
	}

	public static void evaluate() {
		try {
			MPLPlatform platform = new MPLPlatform();
			TreeReader reader = new TreeReader(new GsonImportService());

			List<Tree> trees = new ArrayList<Tree>();
			System.out.println("loading trees");
			for (File file : new File("J://ApoGames").listFiles()) {
				trees.add(reader.readArtifact(
						new de.tu_bs.cs.isf.e4cf.core.file_structure.components.File(file.getAbsolutePath())));
			}
			System.out.println("Starting Variability Mining");
			for (Tree tree : trees) {
				LocalTime start = LocalTime.now();
				platform.insertVariant(tree);
				LocalTime end = LocalTime.now();
				
				System.out.println(tree.getTreeName() + " inseration: " + Duration.between(start, end));
			}
			MPLEPlatformUtil.storePlatform("D://apoGames.mpl", platform);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void evaluateStoreAfterEach() {
		try {
			MPLPlatform platform = new MPLPlatform();
			TreeReader reader = new TreeReader(new GsonImportService());
			DateFormat dateFormat = new SimpleDateFormat("mm:ss");

			for (File file : new File("D://ApoGames").listFiles()) {
				if (!platform.configurations.isEmpty())
					platform = MPLEPlatformUtil.loadPlatform(new File("D://apoGames.mpl"));
				LocalTime start = LocalTime.now();
				float currentTime = System.nanoTime();
				platform.insertVariant(reader.readArtifact(
						new de.tu_bs.cs.isf.e4cf.core.file_structure.components.File(file.getAbsolutePath())));
				LocalTime end = LocalTime.now();

				System.out.println("inseration:" + Duration.between(start, end));
				MPLEPlatformUtil.storePlatform("D://apoGames.mpl", platform);
				System.out.println(file.getAbsolutePath());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package de.tu_bs.cs.isf.e4cf.extractive_mple;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
			TreeReader reader = new TreeReader(new GsonImportService());
			for (int i = 0; i < 11; i++) {
				List<Tree> trees = new ArrayList<Tree>();
				for (File file : new File("J://trees/MobileMediaSPL").listFiles()) {
					trees.add(reader.readArtifact(
							new de.tu_bs.cs.isf.e4cf.core.file_structure.components.File(file.getAbsolutePath())));
				}

				MPLPlatform platform = new MPLPlatform();
				System.out.println("Starting Variability Mining Run " + i);
				for (Tree tree : trees) {
					LocalTime now = LocalTime.now();
					System.out.println("next variant " + tree.getTreeName() + " " + now.getHour() + ":"
							+ now.getMinute() + ":" + now.getSecond());
					platform.insertVariant(tree);
				}
				System.out.println("-------------------------------");
			}
			// MPLEPlatformUtil.storePlatform("D://apoGames.mpl", platform);
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

				platform.insertVariant(reader.readArtifact(
						new de.tu_bs.cs.isf.e4cf.core.file_structure.components.File(file.getAbsolutePath())));

				// MPLEPlatformUtil.storePlatform("D://apoGames.mpl", platform);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package de.tu_bs.cs.isf.e4cf.compare.metric.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.compare.stringtable.CompareST;
import de.tu_bs.cs.isf.e4cf.core.gui.swt.dialogs.InputDialog;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;

public class MetricUtil {
	public static final String FILE_EXISTS = "file exists";
	public static final String METRIC_WITH_NAME_EXISTS = "A metric with this name already exists.";

	/**
	 * Serializes a Metric to the Metrics folder.
	 */
	public static void serializesMetric(MetricImpl metric) {
		File file = new File(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString() + "/"
				+ CompareST.METRICS_FOLDER + "/" + metric.getMetricName() + "." + CompareST.FILE_ENDING_METRIC);

		int choice = 0;
		int inputChoice = 1;

		if (file.exists()) {
			choice = RCPMessageProvider.optionMessage(FILE_EXISTS, METRIC_WITH_NAME_EXISTS, E4CStringTable.DIALOG_OK,
					E4CStringTable.DIALOG_RENAME, E4CStringTable.DIALOG_CANCEL);
		}
		if (choice == 1) {
			InputDialog dialog = new InputDialog(new Shell(), "Name");
			inputChoice = dialog.open();
			if (inputChoice == 0) {
				file = new File(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString() + "/"
						+ CompareST.METRICS_FOLDER + "/" + dialog.getFirstVar() + "." + CompareST.FILE_ENDING_METRIC);
				metric.setMetricName(dialog.getFirstVar());
				if (file.exists()) {
					choice = 1;
					RCPMessageProvider.errorMessage(FILE_EXISTS, METRIC_WITH_NAME_EXISTS);
				}
			}
		}

		if (choice == 0 || (choice == 1 && inputChoice == 0)) {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
				oos.writeObject(metric);
				oos.close();
				System.out.println("Metric: " + file.getAbsolutePath() + " stored.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * DeSerializes a Metric from a given path
	 */
	public static MetricImpl deSerializesMetric(String path) {
		if (!path.equals("")) {
			MetricImpl loadedUnit = null;
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
				Object obj = ois.readObject();
				ois.close();
				if (obj instanceof MetricImpl) {
					loadedUnit = (MetricImpl) obj;
				} else {
					RCPMessageProvider.errorMessage("Type error", "no metric selected");
				}
			} catch (IOException | ClassNotFoundException e) {
				String metricName = path.substring(path.lastIndexOf("\\") + 1, path.lastIndexOf("."));
				RCPMessageProvider.errorMessage("Warning",
						"The following Metric , " + metricName + ", has not been found");
			}
			return loadedUnit;
		}
		return null;
	}

}

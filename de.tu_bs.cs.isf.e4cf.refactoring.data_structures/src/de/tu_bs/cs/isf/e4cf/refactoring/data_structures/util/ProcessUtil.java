package de.tu_bs.cs.isf.e4cf.refactoring.data_structures.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ProcessUtil {

	private boolean isProcessReady;
	private BufferedReader reader;
	private BufferedWriter writer;
	private Process process;
	

	public void startProcess(String path, boolean isPython) {
		try {

			ProcessBuilder builder = null;
			if (!isPython) {
				builder = new ProcessBuilder(path);
			} else {
				builder = new ProcessBuilder("py", path);
			}

			builder.redirectErrorStream(true);
			process = builder.start();
			InputStream inputStream = process.getInputStream();
			reader = new BufferedReader(new InputStreamReader(inputStream));
			OutputStream outputStream = process.getOutputStream();
			writer = new BufferedWriter(new OutputStreamWriter(outputStream));
			isProcessReady = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedReader getReader() {
		return reader;
	}

	public BufferedWriter getWriter() {
		return writer;
	}

	public boolean isReady() {
		return isProcessReady;
	}

	public void stop() {
		process.destroyForcibly();
	}

}

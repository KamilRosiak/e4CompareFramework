package de.tu_bs.cs.isf.e4cf.refactoring.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ProcessUtil {

	private static boolean isProcessReady;
	private static BufferedReader reader;
	private static BufferedWriter writer;
	private static Process process;

	public static void startProcess(String path) {
		try {
			ProcessBuilder builder = new ProcessBuilder("py", path);
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
	
	public static BufferedReader getReader() {
		return reader;
	}

	public static BufferedWriter getWriter() {
		return writer;
	}
	
	public static boolean isReady() {
		return isProcessReady;
	}

}

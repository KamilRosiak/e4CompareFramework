package de.tu_bs.cs.isf.e4cf.extractive_mple.structure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MPLEPlatformUtil {

	/**
	 * Store multi product line platform to file
	 */
	public static void storePlatform(String fileName, MPLPlatform platform) {
		try {
			FileOutputStream file = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(platform);
			out.close();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Store multi product line platform to file
	 */
	public static MPLPlatform loadPlatform(File file) {
		FileInputStream in;
		try {
			in = new FileInputStream(file);
			ObjectInputStream oin = new ObjectInputStream(in);
			Object obj = oin.readObject();
			oin.close();
			in.close();
			if (obj instanceof MPLPlatform) {
				MPLPlatform platform = (MPLPlatform) obj;
				String filename = file.getName().substring(0, file.getName().lastIndexOf("."));
				platform.fileName = filename;
				return platform;
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}

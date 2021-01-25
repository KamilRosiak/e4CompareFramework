package de.tu_bs.cs.isf.e4cf.core.compare.remote.handler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.compare.remote.util.TreeInstanceCreator;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;

public class RemoteComparisonFactory {

	public static RemoteComparisonStatus createComparisonRequest(TreeImpl tree1, TreeImpl tree2) {

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Tree.class, new TreeInstanceCreator());

		String tree1Json = createJSON(tree1, gsonBuilder);
		String tree2Json = createJSON(tree2, gsonBuilder);
		StringBuilder stringBuilderUrl = new StringBuilder();
		StringBuilder stringBuilderPayload = new StringBuilder();
		stringBuilderUrl.append("http://")
				.append(PreferencesUtil.getValueAsString("de.tu_bs.cs.isf.e4cf.compare.remote", "ip")).append(":")
				.append(PreferencesUtil.getValueAsString("de.tu_bs.cs.isf.e4cf.compare.remote", "port"))
				.append("/createRequest/");
		stringBuilderPayload.append("[").append(tree1Json).append(",").append(tree2Json).append("]");

		String result = executePost(stringBuilderUrl.toString(), stringBuilderPayload.toString());
		RemoteComparisonStatus status = (RemoteComparisonStatus) readJSON(result, gsonBuilder,
				RemoteComparisonStatus.class);

		return status;
	}

	public static RemoteComparisonStatus getComparisonStatus(String uuid) {

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Tree.class, new TreeInstanceCreator());

		StringBuilder stringBuilderUrl = new StringBuilder();
		stringBuilderUrl.append("http://")
				.append(PreferencesUtil.getValueAsString("de.tu_bs.cs.isf.e4cf.compare.remote", "ip")).append(":")
				.append(PreferencesUtil.getValueAsString("de.tu_bs.cs.isf.e4cf.compare.remote", "port"))
				.append("/status?uuid=").append(uuid);

		String result = executeGet(stringBuilderUrl.toString());
		RemoteComparisonStatus status = (RemoteComparisonStatus) readJSON(result, gsonBuilder,
				RemoteComparisonStatus.class);

		return status;
	}

	public static TreeImpl getComparisonResult(String uuid) {

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Tree.class, new TreeInstanceCreator());

		StringBuilder stringBuilderUrl = new StringBuilder();
		stringBuilderUrl.append("http://")
				.append(PreferencesUtil.getValueAsString("de.tu_bs.cs.isf.e4cf.compare.remote", "ip")).append(":")
				.append(PreferencesUtil.getValueAsString("de.tu_bs.cs.isf.e4cf.compare.remote", "port"))
				.append("/status?uuid=").append(uuid);

		String result = executeGet(stringBuilderUrl.toString());
		RemoteComparisonStatus status = (RemoteComparisonStatus) readJSON(result, gsonBuilder,
				RemoteComparisonStatus.class);
		TreeImpl tree = (TreeImpl) readJSON(status.getResult(), gsonBuilder, Tree.class);
		reconstructTree(tree.getRoot());
		return tree;

	}

	private static void reconstructTree(Node node) {
		if (node == null || node.getChildren() == null)
			return;
		for (Node children : node.getChildren()) {
			children.setParent(node);
			reconstructTree(children);
		}
	}

	private static String createJSON(Object object, GsonBuilder builder) {
		Gson gson = builder.create();
		return gson.toJson(object);
	}

	private static <T> Object readJSON(String jsonString, GsonBuilder builder, Class<T> c) {
		Gson gson = builder.create();
		return gson.fromJson(jsonString, c);
	}

	public static String executeGet(String targetURL) {
		HttpURLConnection connection = null;

		try {
			// Create connection
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");

			connection.setUseCaches(false);
			connection.setDoOutput(true);

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public static String executePost(String targetURL, String payload) {
		HttpURLConnection connection = null;

		try {
			// Create connection
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json");

			connection.setUseCaches(false);
			connection.setDoOutput(true);

//		    Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(payload);
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

}

package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.evaluation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

public class GitExtractor {

	public static List<File> extract(String evaluationDirectory) {

		List<File> projectDirectories = new ArrayList<File>();

		try {

			List<String> lines = Files.readAllLines(Paths.get(evaluationDirectory, "repositories.txt"));

			for (String line : lines) {

				String[] splittedLine = line.split(";");

				String repositoryName = splittedLine[0];
				String repositoryPath = splittedLine[1];

				String workingDirectoryPath = Paths.get(evaluationDirectory, repositoryName).toString();
				projectDirectories.add(new File(workingDirectoryPath));

				File workingDirectory = new File(workingDirectoryPath);
				if (workingDirectory.exists()) {

					File[] files = new File(workingDirectoryPath).listFiles();
					for (File file : files) {
						if (file.isDirectory()) {
							FileUtils.deleteDirectory(file);
						} else {
							file.delete();
						}

					}
				} else {
					workingDirectory.mkdir();
				}

				String sourcePath = Paths.get(workingDirectoryPath, "repository").toString();
				String variantsPath = Paths.get(workingDirectoryPath, "variants").toString();

				File variantsDirectory = new File(variantsPath);
				variantsDirectory.mkdirs();

				Git git = Git.cloneRepository().setURI(repositoryPath).setDirectory(new File(sourcePath)).call();
				Iterable<RevCommit> commits = git.log().all().call();			
				
				
				String truthLines = "";
				for (RevCommit commit : commits) {

					if (commit.getParentCount() == 0) {
						truthLines += "->" + commit.getName();
					} else {
						for (RevCommit parent : commit.getParents()) {
							truthLines += parent.getName() + "->" + commit.getName() + System.lineSeparator();
						}
					}

					git.checkout().setName(commit.getName()).call();

					File commitDirectory = new File(Paths.get(variantsPath, commit.getName()).toString());
					commitDirectory.mkdirs();

					copyFiles(new File(sourcePath).listFiles(File::isFile), commitDirectory.getAbsolutePath());
					copyDirectories(new File(sourcePath).listFiles(File::isDirectory),
							commitDirectory.getAbsolutePath());

				}

				Files.write(Paths.get(workingDirectoryPath, "groundTruth.txt"), truthLines.getBytes());

			}

		} catch (GitAPIException | IOException e) {
			e.printStackTrace();
		}

		return projectDirectories;

	}

	private static void copyDirectories(File[] directories, String path) throws IOException {

		for (File directory : directories) {

			if (!directory.getName().equals(".git")) {
				String directoryPath = Paths.get(path, directory.getName()).toString();
				File newDirectory = new File(directoryPath);
				newDirectory.mkdirs();

				copyFiles(directory.listFiles(File::isFile), directoryPath);
				copyDirectories(directory.listFiles(File::isDirectory), directoryPath);

			}

		}

	}

	private static void copyFiles(File[] files, String path) throws IOException {

		for (File file : files) {
			Files.copy(Paths.get(file.getAbsolutePath()), Paths.get(path, file.getName()),
					StandardCopyOption.REPLACE_EXISTING);
		}

	}

}

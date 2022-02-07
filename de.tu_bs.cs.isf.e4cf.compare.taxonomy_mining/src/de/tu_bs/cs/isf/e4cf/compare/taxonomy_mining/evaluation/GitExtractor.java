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

	public static final String REPOSITORY_DIRECTORY_NAME = "repository";
	public static final String VARIANTS_DIRECTORY_NAME = "variants";

	public static final String REPOSITORY_FILE_NAME = "repositories.txt";

	public static final String GROUND_TRUTH_FILE_NAME = "groundTruth.txt";

	public static final String DERIVED_TAXONOMY_FILE_NAME = "derivedTaxonomy.txt";

	public static final String GIT_DIRECTORY_NAME = ".git";

	public static List<File> extractLocal(String evaluationDirectoryPath) {

		List<File> projectDirectories = new ArrayList<File>();

		try {

			File workingDirectory = Paths.get(evaluationDirectoryPath).toFile();

			File[] repositoryDirectories = workingDirectory.listFiles(File::isDirectory);

			for (File repositoryDirectory : repositoryDirectories) {

				String repositoryName = repositoryDirectory.getName();

				boolean isAlreadyInitialized = false;

				for (File content : repositoryDirectory.listFiles(File::isFile)) {

					if (content.getName().equals(GROUND_TRUTH_FILE_NAME)) {
						isAlreadyInitialized = true;
						break;
					}

				}

				if (isAlreadyInitialized) {
					File derivedTaxonomy = Paths.get(repositoryDirectory.getAbsolutePath(), DERIVED_TAXONOMY_FILE_NAME)
							.toFile();

					if (derivedTaxonomy.exists()) {
						derivedTaxonomy.delete();
					}

					projectDirectories.add(repositoryDirectory);

				} else {
					File temporaryDirectory = Paths.get(workingDirectory.getAbsolutePath(), "tmp_" + repositoryName)
							.toFile();
					temporaryDirectory.mkdir();

					File newRepositoryDirectory = Paths
							.get(temporaryDirectory.getAbsolutePath(), REPOSITORY_DIRECTORY_NAME).toFile();
					newRepositoryDirectory.mkdir();

					Files.move(repositoryDirectory.toPath(), newRepositoryDirectory.toPath(),
							StandardCopyOption.REPLACE_EXISTING);

					temporaryDirectory.renameTo(Paths.get(workingDirectory.getAbsolutePath(), repositoryName).toFile());

					temporaryDirectory = Paths.get(workingDirectory.getAbsolutePath(), repositoryName).toFile();
					repositoryDirectory = Paths.get(repositoryDirectory.getAbsolutePath(), REPOSITORY_DIRECTORY_NAME)
							.toFile();

					File variantsDirectory = Paths.get(temporaryDirectory.getAbsolutePath(), VARIANTS_DIRECTORY_NAME)
							.toFile();
					variantsDirectory.mkdir();

					Git git = Git.open(Paths.get(repositoryDirectory.getAbsolutePath(), GIT_DIRECTORY_NAME).toFile());

					extractVariantsAndGroundTruth(temporaryDirectory.getAbsolutePath(),
							repositoryDirectory.getAbsolutePath(), variantsDirectory.getAbsolutePath(), git);

					projectDirectories.add(temporaryDirectory);
				}

			}
		} catch (GitAPIException | IOException e) {
			e.printStackTrace();
		}

		return projectDirectories;
	}

	public static List<File> extractRemote(String evaluationDirectory) {

		List<File> projectDirectories = new ArrayList<File>();

		try {

			List<String> lines = Files.readAllLines(Paths.get(evaluationDirectory, REPOSITORY_FILE_NAME));

			for (String line : lines) {

				String[] splittedLine = line.split(";");

				String repositoryName = splittedLine[0];
				String repositoryRemotePath = splittedLine[1];

				File workingDirectory = Paths.get(evaluationDirectory, repositoryName).toFile();
				projectDirectories.add(workingDirectory);

				if (workingDirectory.exists()) {

					File[] files = workingDirectory.listFiles();
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

				File repositoryDirectory = Paths.get(workingDirectory.getAbsolutePath(), REPOSITORY_DIRECTORY_NAME)
						.toFile();
				File variantsDirectory = Paths.get(workingDirectory.getAbsolutePath(), VARIANTS_DIRECTORY_NAME)
						.toFile();
				variantsDirectory.mkdirs();

				Git git = Git.cloneRepository().setURI(repositoryRemotePath).setDirectory(repositoryDirectory).call();
				extractVariantsAndGroundTruth(workingDirectory.getAbsolutePath(), repositoryDirectory.getAbsolutePath(),
						variantsDirectory.getAbsolutePath(), git);

			}

		} catch (GitAPIException | IOException e) {
			e.printStackTrace();
		}

		return projectDirectories;

	}

	private static void extractVariantsAndGroundTruth(String workingDirectoryPath, String repositoryPath,
			String variantsPath, Git git) throws GitAPIException, IOException {
		Iterable<RevCommit> commits = git.log().all().call();

		String truthLines = "";
		String root = "";
		for (RevCommit commit : commits) {

			if (commit.getParentCount() == 0) {
				root = "->" + commit.getName();
			} else {
				for (RevCommit parent : commit.getParents()) {
					truthLines += parent.getName() + "->" + commit.getName() + System.lineSeparator();
				}
			}

			git.checkout().setName(commit.getName()).call();

			File commitDirectory = Paths.get(variantsPath, commit.getName()).toFile();
			commitDirectory.mkdirs();

			copyFiles(new File(repositoryPath).listFiles(File::isFile), commitDirectory.getAbsolutePath());
			copyDirectories(new File(repositoryPath).listFiles(File::isDirectory), commitDirectory.getAbsolutePath(),
					false);

		}

		truthLines += root;

		Files.write(Paths.get(workingDirectoryPath, GROUND_TRUTH_FILE_NAME), truthLines.getBytes());
	}

	private static void copyDirectories(File[] directories, String path, boolean includeGitDirectory)
			throws IOException {

		for (File directory : directories) {

			if (!directory.getName().equals(GIT_DIRECTORY_NAME) || includeGitDirectory) {
				String directoryPath = Paths.get(path, directory.getName()).toString();
				File newDirectory = new File(directoryPath);
				newDirectory.mkdirs();

				copyFiles(directory.listFiles(File::isFile), directoryPath);
				copyDirectories(directory.listFiles(File::isDirectory), directoryPath, includeGitDirectory);

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

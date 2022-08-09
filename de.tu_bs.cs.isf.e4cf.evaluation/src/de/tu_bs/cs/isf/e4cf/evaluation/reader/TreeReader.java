package de.tu_bs.cs.isf.e4cf.evaluation.reader;

import java.nio.file.Paths;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.io.gson.GsonImportService;
import de.tu_bs.cs.isf.e4cf.core.io.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;

public class TreeReader extends AbstractArtifactReader {

	public final static String[] SUPPORTED_FILE_ENDINGS = { "tree" };

	GsonImportService importService;

	public TreeReader() {
		super(SUPPORTED_FILE_ENDINGS);
		importService = new GsonImportService();
	}

	@Override
	public Tree readArtifact(FileTreeElement element) {
		Tree tree = null;

		if (isFileSupported(element)) {
			String s = FileStreamUtil.readLineByLine(Paths.get(element.getAbsolutePath()));

			tree = importService.importTree(s);

		}
		return tree;
	}

}

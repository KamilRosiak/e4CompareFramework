package de.tu_bs.cs.isf.e4cf.core.io.reader.tree_reader;

import java.nio.file.Paths;

import javax.inject.Inject;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.io.gson.GsonImportService;
import de.tu_bs.cs.isf.e4cf.core.io.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;

public class TreeReader extends AbstractArtifactReader {
	public final static String[] SUPPORTED_FILE_ENDINGS = { "tree" };
	@Inject
	GsonImportService importer;

	public TreeReader() {
		super(SUPPORTED_FILE_ENDINGS);
	}

	@Override
	public Tree readArtifact(FileTreeElement element) {
		return importer.importTree(FileStreamUtil.readLineByLine(Paths.get(element.getAbsolutePath())));
	}

}

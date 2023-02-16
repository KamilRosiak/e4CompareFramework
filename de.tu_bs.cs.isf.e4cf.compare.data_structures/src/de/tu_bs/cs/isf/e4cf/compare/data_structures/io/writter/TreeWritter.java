package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter;

import javax.inject.Inject;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactWriter;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.gson.GsonExportService;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;

public class TreeWritter extends AbstractArtifactWriter {
	public final static String FILE_ENDING = "tree";
	public final static String NODE_TYPE_TREE = "TREE";
	@Inject
	GsonExportService exporter;

	public TreeWritter() {
		super(FILE_ENDING);
	}

	@Override
	public void writeArtifact(Tree tree, String path) {
		// If the artifact is not of type text
		FileStreamUtil.writeTextToFile(path + "." + FILE_ENDING, exporter.exportTree((TreeImpl) tree));
	}

	@Override
	public String getSuppotedNodeType() {
		return NODE_TYPE_TREE;
	}

}

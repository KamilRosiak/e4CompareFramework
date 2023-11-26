package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.iec_611331;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.iec_611331.util.IEC61131Util;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.configuration.Configuration;
import de.tu_bs.cs.isf.familymining.ppu_iec.rcp_e4.EMFModelLoader.impl.EMFModelLoader;

public class IECReader extends AbstractArtifactReader {
	public final static String[] SUPPORTED_FILE_ENDINGS = { "project" };

	public IECReader() {
		super(SUPPORTED_FILE_ENDINGS);
	}

	@Override
	public Tree readArtifact(FileTreeElement element) {
		// TODO: integrate XML parser from family mining for IEC
		// IECProjectConverter converter = new IECProjectConverter();
		// Configuration config = converter.parse(Paths.get(new
		// File(element.getAbsolutePath()).toURI());
		Configuration config = (Configuration) EMFModelLoader.load(element.getAbsolutePath(), "project");
		return createTreeFromConfiguration(config);
	}

	public Tree createTreeFromConfiguration(Configuration config) {
		TreeImpl tree = null;
		try {
			tree = new TreeImpl(config.getIdentifier());
			tree.setFileExtension("project");
			tree.setRoot(IEC61131Util.createConfigurationRoot(config));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tree;
	}

}

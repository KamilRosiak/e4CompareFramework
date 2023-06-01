package de.tu_bs.cs.isf.e4cf.core.io.reader.uml_reader;



import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.io.interfaces.AbstractArtifactReader;

public class UMLReader extends AbstractArtifactReader {
	public final static String[] SUPPORTED_FILE_ENDINGS = { "uml" };
	
	
	
	public UMLReader() {
		super(SUPPORTED_FILE_ENDINGS);
	}



	@Override
	public Tree readArtifact(FileTreeElement element) {
		//Model model = ModelUtil.loadModel(element.getAbsolutePath());
		//System.out.println(model);
		
		return null;
	}

}

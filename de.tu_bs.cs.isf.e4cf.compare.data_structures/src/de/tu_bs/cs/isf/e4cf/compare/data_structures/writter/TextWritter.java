package de.tu_bs.cs.isf.e4cf.compare.data_structures.writter;

import javax.inject.Inject;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactWriter;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPDialogService;

public class TextWritter extends AbstractArtifactWriter {
	public final static String FILE_ENDING = "txt";
	public final static String NODE_TYPE_TREE = "TEXT";
	public ServiceContainer services;
	@Inject public RCPDialogService dialog;
	
	public TextWritter(ServiceContainer services) {
		super(FILE_ENDING);
		this.services = services;
	}

	@Override
	public void writeArtifact(Tree tree) {
		//If the artifact is not of type text 
		if(!tree.getArtifactType().equals(NODE_TYPE_TREE)) {
			return;
		} else {
			FileStreamUtil.writeTextToFile(dialog.getFolderFromFileSystem(dialog.getShell()), createFileContent(tree));
		}
		
		
	}
	
	
	private String createFileContent(Tree tree) {
		String fileContent = "";
		for(Node lineNode : tree.getRoot().getChildren()) {
			for(Node wordNode : lineNode.getChildren()) {
				//fileContent += wordNode.getValuesForKey(TextReader.VALUE_TYPE_TEXT).getValues().get(0)+ " ";
			}
			fileContent += "/n";
		}
		
		
		return fileContent;
	}

}

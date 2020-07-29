package de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces;

import java.util.ArrayList;
import java.util.List;

public class WorkspaceStructureTemplate implements IWorkspaceStructure {
	private List<String> directories = new ArrayList<String>(); 
	
	@Override
	public void addDirectory(String directory) {
		directories.add(directory);	
	}

	@Override
	public List<String> getDirectories() {
		return directories;
	}
}

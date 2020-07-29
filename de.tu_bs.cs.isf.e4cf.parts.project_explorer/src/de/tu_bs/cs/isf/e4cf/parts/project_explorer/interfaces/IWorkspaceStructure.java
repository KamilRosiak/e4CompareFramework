package de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces;

import java.util.List;

/**
 * This interface serves as extension point for the initial workspace structure.
 */
public interface IWorkspaceStructure {
	public List<String> getDirectories();
	public void addDirectory(String directory);
}

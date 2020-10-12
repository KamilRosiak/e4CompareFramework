package de.tu_bs.cs.isf.e4cf.compare.extensions.workspace;

import de.tu_bs.cs.isf.e4cf.compare.stringtable.CompareST;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.WorkspaceStructureTemplate;


/**
 * This class represents a initial workspace structure for the comparison process to store all artifacts that are required.
 * @author Kamil Rosiak
 *
 */
public class CompareWorkspace extends WorkspaceStructureTemplate {

    public CompareWorkspace() {
	addDirectory(CompareST.RAW_FOLDER);
	addDirectory(CompareST.TREE_FOLDER);
	addDirectory(CompareST.METRICS_FOLDER);
	addDirectory(CompareST.FAMILY_MODELS);
	addDirectory(CompareST.FEATURE_MODELS);
    }

}

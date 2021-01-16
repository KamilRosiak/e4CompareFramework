package de.tu_bs.cs.isf.e4cf.parts.project_explorer;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

/**
 * This is a content provider that tells the explorer view how to handle the
 * objects.
 * 
 * @author {Kamil Rosiak}
 *
 */
public class ProjectExplorerContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		List<FileTreeElement> children = ((FileTreeElement) parentElement).getChildren();
		return children.toArray();
	}

	@Override
	public Object getParent(Object element) {
		return ((FileTreeElement) element).getParent();
	}

	@Override
	public boolean hasChildren(Object element) {
		return !((FileTreeElement) element).getChildren().isEmpty();
	}
}

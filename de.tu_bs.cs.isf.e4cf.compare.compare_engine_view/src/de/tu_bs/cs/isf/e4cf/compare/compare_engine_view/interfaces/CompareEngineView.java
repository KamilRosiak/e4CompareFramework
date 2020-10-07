package de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.interfaces;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces.Matcher;
import javafx.scene.Scene;

public interface CompareEngineView {
	
	/**
	 * This method compares two object of type tree and returns the composed tree
	 */
	public Tree compare(Tree firstArtifact, Tree secondArtifact);
	
	/**
	 * This method returns the scene with all widgets and components of this view.
	 */
	public Scene createScene();
	
	public Tree compare(List<Tree> artifacts);
	
	public Matcher getSelectedMatcher();
	
	
	
	
	public void showArtifacts(List<Tree> artifacts);
}

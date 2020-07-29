package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.animation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javafx.scene.Node;

/**
 * Stores and controls the animations for a number of JavaFX Nodes.
 * Only one animation is played per node at the same time.
 * 
 * 
 * @author Oliver Urbaniak
 */
public class AnimationMap {

	Map<Node, INodeAnimator> animatorMap;
	
	public AnimationMap() {
		this.animatorMap = new HashMap<>();
	}
	
	public void startAnimation(Node node, INodeAnimator animator) {
		if (!isAnimated(node)) {
			animatorMap.put(node, animator);
			animator.start();
		} else {
			throw new RuntimeException("A JavaFX Node animation is already running. It must be stopped before running a new animation.");
		}
	}
	
	public void stopAnimation(Node node) {
		INodeAnimator animator = animatorMap.remove(node);
		if (animator != null) {
			animator.stop();			
		}
	}
	
	public void stopAllAnimations() {
		animatorMap.values().forEach(animator -> animator.stop());
		animatorMap.clear();
	}
	
	/**
	 * Check for nodes removed from the scene and cancel their animation.
	 */
	public void refresh() {
		Iterator<Map.Entry<Node, INodeAnimator>> it = animatorMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Node, INodeAnimator> entry = it.next();
			if (entry.getKey().getScene() == null) {
				// remove nodes that are not part of the scene anymore
				entry.getValue().stop();
				it.remove();
			}
		}
	}
	
	public boolean isAnimated(Node node) {
		return animatorMap.containsKey(node);
	}
}

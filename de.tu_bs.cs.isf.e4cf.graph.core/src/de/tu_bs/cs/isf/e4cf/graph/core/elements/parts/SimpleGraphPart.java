package de.tu_bs.cs.isf.e4cf.graph.core.elements.parts;

import java.util.List;

import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;

import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.SimpleGraph;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.templates.AbstractGraphItem;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.templates.AbstractGraphPart;
import javafx.scene.Group;
import javafx.scene.Node;

public class SimpleGraphPart extends AbstractGraphPart<SimpleGraph, Group> {
	
	@Override
	protected void doAddChildVisual(IVisualPart<? extends Node> child, int index) {
	    getVisual().getChildren().add(child.getVisual());
	}
	
	@Override
	protected void doAddContentChild(Object contentChild, int index) {
	    if (contentChild instanceof AbstractGraphItem) {
	        getContent().addChildElement((AbstractGraphItem) contentChild, index);
	    } else {
	        throw new IllegalArgumentException("contentChild has invalid type: " + contentChild.getClass());
	    }
	}
	
	@Override
	protected Group doCreateVisual() {
	    // the visual is just a container for our child visuals (nodes and
	    // connections)
	    return new Group();
	}
	
	@Override
	protected SetMultimap<? extends Object, String> doGetContentAnchorages() {
	    return HashMultimap.create();
	}
	
	@Override
	protected List<? extends Object> doGetContentChildren() {
	    return Lists.newArrayList(getContent().getChildElements());
	}
	
	@Override
	protected void doRefreshVisual(Group visual) {
	    // no refreshing necessary, just a Group
	}
	
	@Override
	protected void doRemoveChildVisual(IVisualPart<? extends Node> child, int index) {
	    getVisual().getChildren().remove(child.getVisual());
	}
	
	@Override
	protected void doRemoveContentChild(Object contentChild) {
	    if (contentChild instanceof AbstractGraphItem) {
	        getContent().removeChildElement((AbstractGraphItem) contentChild);
	    } else {
	        throw new IllegalArgumentException("contentChild has invalid type: " + contentChild.getClass());
	    }
	}
	
	@Override
	public SimpleGraph getContent() {
	    return (SimpleGraph) super.getContent();
	}
}
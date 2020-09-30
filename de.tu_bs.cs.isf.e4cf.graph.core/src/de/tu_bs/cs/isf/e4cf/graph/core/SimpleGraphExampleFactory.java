package de.tu_bs.cs.isf.e4cf.graph.core;

import org.eclipse.gef.geometry.planar.Rectangle;

import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.GraphEdge;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.GraphNode;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.SimpleGraph;
import javafx.scene.paint.Color;

public class SimpleGraphExampleFactory {
	    private static final double WIDTH = 150;
	    
	    public SimpleGraph createGraphFromModel() {
	    	return new SimpleGraph();
	    }
	    
	    public SimpleGraph createComplexExample() {
	    	SimpleGraph mindMap = new SimpleGraph();

	        GraphNode center = new GraphNode();
	        center.setTitle("The Core Idea");
	        center.setDescription("This is my Core idea");
	        center.setColor(Color.GREENYELLOW);
	        center.setBounds(new Rectangle(250, 50, WIDTH, 100));

	        mindMap.addChildElement(center);

	        GraphNode child = null;
	        for (int i = 0; i < 5; i++) {
	            child = new GraphNode();
	            child.setTitle("Association #" + i);
	            child.setDescription("Node1");
	            child.setColor(Color.ALICEBLUE);

	            child.setBounds(new Rectangle(50 + (i * 200), 250, WIDTH, 100));
	            mindMap.addChildElement(child);

	            GraphEdge conn = new GraphEdge();
	            conn.connect(center, child);
	            mindMap.addChildElement(conn);
	        }

	        GraphNode child2 = new GraphNode();
	        child2.setTitle("Association #4-2");
	        child2.setDescription("Node 2");
	        child2.setColor(Color.LIGHTGRAY);
	        child2.setBounds(new Rectangle(250, 550, WIDTH, 100));
	        mindMap.addChildElement(child2);

	        GraphEdge conn = new GraphEdge();
	        conn.connect(child, child2);
	        mindMap.addChildElement(conn);

	        return mindMap;
	    }

	    public SimpleGraph createSingleNodeExample() {
	    	SimpleGraph mindMap = new SimpleGraph();

	        GraphNode center = new GraphNode();
	        center.setTitle("The Core Idea");
	        center.setDescription("This is my Core idea. I need a larger Explanation to it, so I can test the warpping.");
	        center.setColor(Color.GREENYELLOW);
	        center.setBounds(new Rectangle(20, 50, WIDTH, 100));

	        mindMap.addChildElement(center);

	        return mindMap;
	    }
}


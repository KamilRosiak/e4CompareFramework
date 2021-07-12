/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.graph.artifact_graph;

import java.util.Comparator;
import java.util.List;

import org.eclipse.gef.geometry.planar.Rectangle;

import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.GraphEdge;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.GraphNode;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.SimpleGraph;
import javafx.scene.paint.Color;

/**
 * @author developer-olan
 *
 */
public class ArtifactGraphFactory {
	private static final double WIDTH = 150;
	private double xAxisOffset;
	private double yAxisOffset;
	public List<ArtifactComparison> artifactComparisonList;
	public SimpleGraph mindMap;

	public ArtifactGraphFactory() {
		mindMap = new SimpleGraph();
	}
	
	public ArtifactGraphFactory(List<ArtifactComparison> _artifactComparisonList) {
		mindMap = new SimpleGraph();
		artifactComparisonList = _artifactComparisonList;
	}
	
	public void sortBySimilarityValues() {
		artifactComparisonList.sort((first, second) -> {
			//Multiply with -1 to sort descending 
			return -1 * Float.compare(first.getNodeComparison().getSimilarity(), second.getNodeComparison().getSimilarity());
		});
	}

	public SimpleGraph createNodesAndGraph() {
		
		for (ArtifactComparison artifactComparison : artifactComparisonList) {
			xAxisOffset = 1;
			yAxisOffset = 1;
			Node leftNode = artifactComparison.getNodeComparison().getLeftArtifact();
			Node rightNode = artifactComparison.getNodeComparison().getRightArtifact();
			GraphNode leftArtifactNode = createGraphNode(leftNode, artifactComparison.getLeftArtifactName());
			GraphNode rightArtifactNode = createGraphNode(rightNode, artifactComparison.getRightArtifactName());

			mindMap.addChildElement(leftArtifactNode);
			mindMap.addChildElement(rightArtifactNode);

			GraphEdge conn = new GraphEdge();
			conn.connect(leftArtifactNode, rightArtifactNode);
			conn.setWeight(artifactComparison.getNodeComparison().getSimilarity());
			mindMap.addChildElement(conn);

		}
		return mindMap;
	}

	public GraphNode createGraphNode(Node artifact, String artifactName) {
		GraphNode center = new GraphNode();
		center.setTitle(artifact.getNodeType());
		center.setDescription(artifactName);
		center.setColor(Color.ALICEBLUE);
		center.setBounds(new Rectangle(50 + (xAxisOffset * 200), 50 + (yAxisOffset * 550), WIDTH, 90));
		xAxisOffset ++;
		yAxisOffset ++;
		return center;
	}

	public SimpleGraph createSingleNodeExample() {
	
	        GraphNode center = new GraphNode();
	        center.setTitle("The Core Idea");
	        center.setDescription("This is my Core idea. I need a larger Explanation to it, so I can test the warpping.");
	        center.setColor(Color.GREENYELLOW);
	        center.setBounds(new Rectangle(20, 50, WIDTH, 100));

	        mindMap.addChildElement(center);

	        return mindMap;
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

}

package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.eclipse.gef.geometry.planar.Rectangle;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.compare.DirectoryComparator;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.compare.FileComparator;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.compare.SourceCodeComparator;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.compare.TaxonomyMetric;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.GraphEdge;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.GraphNode;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.SimpleGraph;
import javafx.scene.paint.Color;

public abstract class TaxonomyGraph {

	protected List<Tree> variants;

	protected List<TaxonomyEdge> edges;

	protected Tree rootVariant;

	protected CompareEngineHierarchical compareEngine;

	public TaxonomyGraph(List<Tree> variants) {
		this.variants = variants;
		this.compareEngine = new CompareEngineHierarchical(new SortingMatcher(), new TaxonomyMetric("TaxonomyMetric",
				new SourceCodeComparator(), new DirectoryComparator(), new FileComparator()));
		this.edges = new ArrayList<TaxonomyEdge>();
	}

	public abstract void build();

	public List<TaxonomyEdge> getEdges() {
		return edges;
	}

	public Tree getRootVariant() {
		return rootVariant;
	}

	public void setRootVariant(Tree rootVariant) {
		this.rootVariant = rootVariant;
	}

	public void addEdge(TaxonomyEdge edge) {
		this.edges.add(edge);
	}

	public void toFile(Path path) throws IOException {

		String output = "";

		for (TaxonomyEdge edge : edges) {

			output += edge.getVariant1().getTreeName() + "->" + edge.getVariant2().getTreeName()
					+ System.lineSeparator();
		}

		output += "->" + rootVariant.getTreeName();
		Files.write(path, output.getBytes());
	}

	public SimpleGraph createSimpleGraph() {

		SimpleGraph simpleGraph = new SimpleGraph();

		Map<Tree, GraphNode> graphMap = createGraphNodes();

		for (GraphNode graphNode : graphMap.values()) {
			simpleGraph.addChildElement(graphNode);
		}

		for (TaxonomyEdge edge : this.edges) {

			GraphNode from = graphMap.get(edge.getVariant1());
			GraphNode to = graphMap.get(edge.getVariant2());

			GraphEdge graphEdge = new GraphEdge();
			graphEdge.connect(from, to);
			graphEdge.setWeight(edge.getWeight());

			simpleGraph.addChildElement(graphEdge);

		}

		return simpleGraph;

	}

	private Map<Tree, GraphNode> createGraphNodes() {

		Map<Tree, Color> colorMap = new HashMap<Tree, Color>();
		Map<Tree, GraphNode> graphNodes = new HashMap<Tree, GraphNode>();

		for (Tree variant : variants) {

			Random random = new Random();
			Color color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
			colorMap.put(variant, color);

			GraphNode graphNode = new GraphNode();
			graphNode.setTitle(variant.getTreeName());
			graphNode.setDescription(variant.getTreeName());
			graphNode.setColor(colorMap.get(variant));
			graphNode.setBounds(new Rectangle(250, 550, 225, 30));

			graphNodes.put(variant, graphNode);

		}

		return graphNodes;

	}

	public void fromFile(Path path, Map<String, Tree> fileToTreeMap) throws IOException {
		List<String> lines = Files.readAllLines(path);

		for (int i = 0; i < lines.size(); i++) {
			String[] splittedLine = lines.get(i).split("->");
			String commitId1 = splittedLine[0];
			String commitId2 = splittedLine[1];
			if (i == lines.size() - 1) {
				setRootVariant(fileToTreeMap.get(commitId2));
			} else {
				Tree variant1 = fileToTreeMap.get(commitId1);
				Tree variant2 = fileToTreeMap.get(commitId2);
				TaxonomyEdge edge = new TaxonomyEdge(variant1, variant2, 0);
				addEdge(edge);
			}

		}
	}
	
	

}

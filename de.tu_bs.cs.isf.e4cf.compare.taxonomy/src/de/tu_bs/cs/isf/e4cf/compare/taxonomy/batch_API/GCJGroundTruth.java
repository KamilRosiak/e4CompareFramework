/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.batch_API;

import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.VariantTaxonomyNode;

/**
 * @author developer-olan
 *
 */
public class GCJGroundTruth {
	

	/**
	 * 2008 Ground Truth Data (SP-SD-SL)
	 */
	
	// Ground Truth Creators /(Tests)
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createProjectExampleGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("stmicsBasicStackVariant.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("stmicsOverflowStackVariant.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("stmicsLoggingStackVariant.java", 2,
				level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}

	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCodeJamGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2016-2-A-rank-3-eatmore.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2016-3-A-rank-6-eatmore.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2016-FR-A-rank-4-eatmore.java", 2,
				level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	
	/**
	 * 2008 Ground Truth Data (SP-SD-SL)
	 */
	// N = 3

	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-halyavin-D
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3halyavinDGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-2-D-halyavin.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-3-D-halyavin.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-EMEA-D-halyavin.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}

	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-JediKnight-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3jediknightBGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1C-B-JediKnight.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-B-JediKnight.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-B-JediKnight.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}

	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-kotehok-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3kotehokAGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1A-A-kotehok.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-A-kotehok.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-FR-A-kotehok.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}

	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-kotehok-D
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3kotehokDGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-2-D-kotehok.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-3-D-kotehok.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-FR-D-kotehok.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}

	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-wata-D
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3wataDGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-3-D-wata.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-APAC-D-wata.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-FR-D-wata.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}

	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-ymatsux-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3ymatsuxAGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-APAC-A-ymatsux.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-FR-A-ymatsux.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}

	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-ymatsux-C
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3ymatsuxCGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-C-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-APAC-C-ymatsux.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-FR-C-ymatsux.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}

	/**
	 * N = 4
	 */

	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-halyavin-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4halyavinBGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1B-B-halyavin.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-B-halyavin.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-EMEA-B-halyavin.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-FR-B-halyavin.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-halyavin-C
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4halyavinCGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1B-C-halyavin.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-3-C-halyavin.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-EMEA-C-halyavin.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-FR-C-halyavin.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-kotehok-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4kotehokBGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1A-B-kotehok.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-B-kotehok.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-B-kotehok.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-FR-B-kotehok.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-kotehok-C
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4kotehokCGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1A-C-kotehok.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-C-kotehok.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-C-kotehok.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-FR-C-kotehok.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-mystic-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4mysticBGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1B-B-mystic.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-B-mystic.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-EMEA-B-mystic.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-FR-B-mystic.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-mystic-D
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4mysticDGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-2-D-mystic.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-3-D-mystic.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-EMEA-D-mystic.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-FR-D-mystic.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-wata-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4wataAGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-wata.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-3-A-wata.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-APAC-A-wata.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-FR-A-wata.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-wata-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4wataBGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-17-wata.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-3-B-wata.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-APAC-B-wata.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-FR-B-wata.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-wata-C
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4wataCGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-C-17-wata.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-3-C-wata.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-APAC-C-wata.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-FR-C-wata.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	
	/**
	 * N = 5
	 */
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N5-halyavin-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N5halyavinAGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1B-A-halyavin.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-A-halyavin.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-A-halyavin.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-EMEA-A-halyavin.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-FR-A-halyavin.java", 4, level3ChildNode);

		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N5-mystic-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N5mysticAGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1B-A-mystic.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-A-mystic.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-A-mystic.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-EMEA-A-mystic.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-FR-A-mystic.java", 4, level3ChildNode);

		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N5-mystic-C
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N5mysticCGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1B-C-mystic.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-C-mystic.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-C-mystic.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-EMEA-C-mystic.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-FR-C-mystic.java", 4, level3ChildNode);

		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	

	/**
	 * End of 2008 Ground Truth (SP-SD-SL) Data
	 */
	
	
	/**
	 * 2008 Ground Truth Data (SP-DD-SL)
	 */
	
	// N = 3 (Problem A)

	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-Set1-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3Set1AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1A-A-kotehok.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-Set2-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3Set2AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1B-A-halyavin.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-Set3-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3Set3AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-APAC-A-wata.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-Set4-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3Set4AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-EMEA-A-dgozman.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-Set5-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3Set5AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-A-halyavin.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-Set6-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3Set6AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-Set7-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3Set7AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-APAC-A-wata.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-FR-A-mystic.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-Set8-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3Set8AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-APAC-A-wata.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-FR-A-mystic.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	// N = 3 (Problem B)

	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-Set1-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3Set1BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1A-B-kotehok.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}

	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-Set2-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3Set2BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1B-B-halyavin.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}

	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-Set3-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3Set3BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-APAC-B-wata.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}

	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-Set4-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3Set4BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-EMEA-B-dgozman.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}

	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-Set5-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3Set5BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-B-halyavin.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}

	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-Set6-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3Set6BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}

	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-Set7-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3Set7BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-APAC-B-wata.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-FR-B-mystic.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}

	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N3-Set8-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N3Set8BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-APAC-B-wata.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-FR-B-mystic.java", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}

	
	/**
	 * N = 4 (Problem A)
	 */

	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-Set1-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4Set1AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1A-A-kotehok.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-APAC-A-wata.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-Set2-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4Set2AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1B-A-halyavin.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-EMEA-A-dgozman.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-Set3-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4Set3AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-APAC-A-wata.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-FR-A-mystic.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-Set4-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4Set4AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1A-A-kotehok.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-EMEA-A-dgozman.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-Set5-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4Set5AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-A-halyavin.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-APAC-A-wata.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-Set6-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4Set6AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-A-halyavin.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-Set7-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4Set7AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-APAC-A-wata.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-FR-A-mystic.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-Set8-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4Set8AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-A-halyavin.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-APAC-A-wata.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-FR-A-mystic.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	
	/**
	 * N = 4 (Problem B)
	 */

	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-Set1-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4Set1BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1A-B-kotehok.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-APAC-B-wata.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-Set2-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4Set2BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1B-B-halyavin.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-EMEA-B-dgozman.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-Set3-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4Set3BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-APAC-B-wata.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-FR-B-mystic.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-Set4-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4Set4BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1A-B-kotehok.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-EMEA-B-dgozman.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-Set5-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4Set5BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-B-halyavin.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-APAC-B-wata.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-Set6-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4Set6BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-B-halyavin.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-Set7-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4Set7BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-APAC-B-wata.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-FR-B-mystic.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N4-Set8-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N4Set8BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-B-halyavin.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-APAC-B-wata.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-FR-B-mystic.java", 3, level2ChildNode);

		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	
	
	/**
	 * N = 5 (Problem A)
	 */
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N5-Set1-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N5Set1AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1A-A-kotehok.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-APAC-A-wata.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-FR-A-mystic.java", 4, level3ChildNode);

		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N5-Set2-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N5Set2AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1B-A-halyavin.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-EMEA-A-dgozman.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-FR-A-mystic.java", 4, level3ChildNode);

		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N5-Set3-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N5Set3AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1B-A-halyavin.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-APAC-A-wata.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-FR-A-mystic.java", 4, level3ChildNode);

		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N5-Set4-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N5Set4AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1A-A-kotehok.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-EMEA-A-dgozman.java", 4, level3ChildNode);

		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N5-Set5-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N5Set5AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-A-halyavin.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-APAC-A-wata.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-FR-A-mystic.java", 4, level3ChildNode);

		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N5-Set6-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N5Set6AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-A-halyavin.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-EMEA-A-dgozman.java", 4, level3ChildNode);

		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N5-Set7-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N5Set7AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-APAC-A-wata.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-FR-A-mystic.java", 4, level3ChildNode);

		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N5-Set8-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N5Set8AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-A-halyavin.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-APAC-A-wata.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-FR-A-mystic.java", 4, level3ChildNode);

		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}

	
	/**
	 * N = 5 (Problem B)
	 */
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N5-Set1-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N5Set1BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1A-B-kotehok.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-APAC-B-wata.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-FR-B-mystic.java", 4, level3ChildNode);

		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N5-Set2-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N5Set2BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1B-B-halyavin.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-EMEA-B-dgozman.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-FR-B-mystic.java", 4, level3ChildNode);

		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N5-Set3-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N5Set3BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1B-B-halyavin.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-APAC-B-wata.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-FR-B-mystic.java", 4, level3ChildNode);

		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N5-Set4-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N5Set4BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1A-B-kotehok.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-EMEA-B-dgozman.java", 4, level3ChildNode);

		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N5-Set5-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N5Set5BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-B-halyavin.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-APAC-B-wata.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-FR-B-mystic.java", 4, level3ChildNode);

		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N5-Set6-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N5Set6BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-B-halyavin.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-EMEA-B-dgozman.java", 4, level3ChildNode);

		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N5-Set7-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N5Set7BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-APAC-B-wata.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-FR-B-mystic.java", 4, level3ChildNode);

		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N5-Set8-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N5Set8BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-B-halyavin.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-APAC-B-wata.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-FR-B-mystic.java", 4, level3ChildNode);

		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}

	
	/**
	 * N = 6 (Problem A)
	 */
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Set1-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N6Set1AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1A-A-kotehok.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-APAC-A-wata.java", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("2008-FR-A-mystic.java", 5, level3ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Set2-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N6Set2AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-A-halyavin.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-EMEA-A-dgozman.java", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("2008-FR-A-mystic.java", 5, level3ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Set3-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N6Set3AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-A-halyavin.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-APAC-A-wata.java", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("2008-FR-A-mystic.java", 5, level3ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Set4-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N6Set4AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1A-A-kotehok.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-EMEA-A-dgozman.java", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("2008-FR-A-mystic.java", 5, level3ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	

	/**
	 * N = 6 (Problem B)
	 */
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Set1-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N6Set1BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1A-B-kotehok.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-APAC-B-wata.java", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("2008-FR-B-mystic.java", 5, level3ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Set2-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N6Set2BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-B-halyavin.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-EMEA-B-dgozman.java", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("2008-FR-B-mystic.java", 5, level3ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Set3-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N6Set3BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-B-halyavin.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-APAC-B-wata.java", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("2008-FR-B-mystic.java", 5, level3ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Set4-B
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N6Set4BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1A-B-kotehok.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-EMEA-B-dgozman.java", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("2008-FR-B-mystic.java", 5, level3ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	
	/**
	 * Running Example Git GroundTruth Data
	 */
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph RunningExampleBranches Time ground truth
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createRunningExampleBranchesTimeGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("MA-CalcSPL-BasicCalc", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("MA-CalcSPL-DotCalc", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("MA-CalcSPL-PowCalc", 2, level1ChildNode);

		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph RunningExampleBranches Space ground truth
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createRunningExampleBranchesSpaceGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("MA-CalcSPL-BasicCalc", 0, null);
		VariantTaxonomyNode level1Child1Node = new VariantTaxonomyNode("MA-CalcSPL-DotCalc", 1, rootNode);
		VariantTaxonomyNode level1Child2Node = new VariantTaxonomyNode("MA-CalcSPL-PowCalc", 1, rootNode);

		rootNode.addChildNode(level1Child1Node);
		rootNode.addChildNode(level1Child2Node);

		return rootNode;
	}
	
	
	/**
	 * GIT javapoet time GroundTruth Data
	 */
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Time-Set1 (javapoet)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITJavapoetN6TimeSet1GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("javapoet-3-4d856", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("javapoet-4-f591e", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("javapoet-5-ec4ab", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("javapoet-6-d5fad", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("javapoet-7-316a6", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("javapoet-8-c5b6b", 5, level3ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Time-Set2 (javapoet)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITJavapoetN6TimeSet2GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("javapoet-6-d5fad", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("javapoet-7-316a6", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("javapoet-8-c5b6b", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("javapoet-9-e8cd8", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("javapoet-10-d228e", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("javapoet-11-a7fcb", 5, level3ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Time-Set3 (javapoet)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITJavapoetN6TimeSet3GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("javapoet-9-e8cd8", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("javapoet-10-d228e", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("javapoet-11-a7fcb", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("javapoet-12-19fe0", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("javapoet-13-f1bf5", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("javapoet-14-97c75", 5, level3ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Time-Set4 (javapoet)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITJavapoetN6TimeSet4GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("javapoet-13-f1bf5", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("javapoet-14-97c75", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("javapoet-15-1a169", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("javapoet-16-1d763", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("javapoet-17-4b0bd", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("javapoet-18-90064", 5, level3ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
 	
}




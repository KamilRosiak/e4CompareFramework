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
	 * Creates Ground Truth VariantTaxonomy Tree/Graph
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createSpareTimeGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("pm.cpp", 0, null);
		VariantTaxonomyNode level1ChildNode1 = new VariantTaxonomyNode("pm-singl.cpp", 1, rootNode);
		VariantTaxonomyNode level1ChildNode2 = new VariantTaxonomyNode("pm-multi.cpp", 1, rootNode);

		VariantTaxonomyNode level2ChildNode1one = new VariantTaxonomyNode("pm-kmp.cpp", 2,
				level1ChildNode1);
		VariantTaxonomyNode level2ChildNode1two = new VariantTaxonomyNode("pm-bm.cpp", 2,
				level1ChildNode1);
		VariantTaxonomyNode level2ChildNode1three = new VariantTaxonomyNode("pm-bfsin.cpp", 2,
				level1ChildNode1);
		
		VariantTaxonomyNode level2ChildNode2one = new VariantTaxonomyNode("pm-ac.cpp", 2,
				level1ChildNode2);
		VariantTaxonomyNode level2ChildNode2two = new VariantTaxonomyNode("pm-bfmul.cpp", 2,
				level1ChildNode2);
		VariantTaxonomyNode level2ChildNode2three = new VariantTaxonomyNode("pm-cw.cpp", 2,
				level1ChildNode2);
		
		

		level1ChildNode1.addChildNode(level2ChildNode1one);
		level1ChildNode1.addChildNode(level2ChildNode1two);
		level1ChildNode1.addChildNode(level2ChildNode1three);

		level1ChildNode2.addChildNode(level2ChildNode2one);
		level1ChildNode2.addChildNode(level2ChildNode2two);
		level1ChildNode2.addChildNode(level2ChildNode2three);


		rootNode.addChildNode(level1ChildNode1);
		rootNode.addChildNode(level1ChildNode2);


		return rootNode;
	}
	
	
	/*
	 ** ###############################( 2008 Ground Truth Data (SP-SD-SL) - N3, N4, N5, N6)#############################################
	 **		
	 * */
	
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
	
	
	/*
	 ** ###############################( 2008 Ground Truth Data (SP-DD-DL))#############################################
	 **		
	 * */
	
	/**
	 * N = 5 (Problem A)
	 */
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N5-Set1-A
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCode2008N5DLSet1AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-rem.cpp", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1A-A-kotehok.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-A-misof.cpp", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-APAC-A-ACRush.cpp", 4, level3ChildNode);

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
	public static VariantTaxonomyNode createGoogleCode2008N5DLSet2AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-A-misof.cpp", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-EMEA-A-bmerry.cpp", 3, level2ChildNode);
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
	public static VariantTaxonomyNode createGoogleCode2008N5DLSet3AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-A-misof.cpp", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-A-misof.cpp", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-APAC-A-ACRush.cpp", 3, level2ChildNode);
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
	public static VariantTaxonomyNode createGoogleCode2008N5DLSet4AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1A-A-kotehok.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-A-misof.cpp", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-EMEA-A-bmerry.cpp", 3, level2ChildNode);
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
	public static VariantTaxonomyNode createGoogleCode2008N5DLSet1BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-rem.cpp", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1A-B-kotehok.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-B-misof.cpp", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-APAC-B-ACRush.cpp", 4, level3ChildNode);

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
	public static VariantTaxonomyNode createGoogleCode2008N5DLSet2BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-B-misof.cpp", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-EMEA-B-bmerry.cpp", 3, level2ChildNode);
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
	public static VariantTaxonomyNode createGoogleCode2008N5DLSet3BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-B-misof.cpp", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-B-misof.cpp", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-APAC-B-ACRush.cpp", 3, level2ChildNode);
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
	public static VariantTaxonomyNode createGoogleCode2008N5DLSet4BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-1A-B-kotehok.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-2-B-misof.cpp", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-EMEA-B-bmerry.cpp", 3, level2ChildNode);
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
	public static VariantTaxonomyNode createGoogleCode2008N6DLSet1AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-rem.cpp", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1A-A-kotehok.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-A-misof.cpp", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-APAC-A-ACRush.cpp", 4, level3ChildNode);
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
	public static VariantTaxonomyNode createGoogleCode2008N6DLSet2AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-A-misof.cpp", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-A-darnley.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-EMEA-A-bmerry.cpp", 4, level3ChildNode);
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
	public static VariantTaxonomyNode createGoogleCode2008N6DLSet3AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-A-misof.cpp", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-A-misof.cpp", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-APAC-A-ACRush.cpp", 4, level3ChildNode);
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
	public static VariantTaxonomyNode createGoogleCode2008N6DLSet4AGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-A-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1A-A-kotehok.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-A-misof.cpp", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-A-elizarov.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-EMEA-A-bmerry.cpp", 4, level3ChildNode);
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
	public static VariantTaxonomyNode createGoogleCode2008N6DLSet1BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-rem.cpp", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1A-B-kotehok.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-B-misof.cpp", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-APAC-B-ACRush.cpp", 4, level3ChildNode);
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
	public static VariantTaxonomyNode createGoogleCode2008N6DLSet2BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-B-misof.cpp", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-B-darnley.java", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-EMEA-B-bmerry.cpp", 4, level3ChildNode);
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
	public static VariantTaxonomyNode createGoogleCode2008N6DLSet3BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1B-B-misof.cpp", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-B-misof.cpp", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-APAC-B-ACRush.cpp", 4, level3ChildNode);
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
	public static VariantTaxonomyNode createGoogleCode2008N6DLSet4BGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2008-QR-B-2-ymatsux.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2008-1A-B-kotehok.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2008-2-B-misof.cpp", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("2008-3-B-elizarov.java", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("2008-EMEA-B-bmerry.cpp", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("2008-FR-B-mystic.java", 5, level3ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	

	/*
	 ** ###############################( Running Example Git GroundTruth Data)#############################################
	 **		
	 * */
	
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
	 * GIT time GroundTruth Data, N=6
	 */
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Time-Set1 (logger)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITLoggerN6TimeSet1GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("0-logger-7806d-0", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("1-logger-c32b6-1", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2-logger-fa9dc-2", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("3-logger-42fe4-3", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("4-logger-ab3e4-4", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("5-logger-a1f5f-5", 5, level4ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Time-Set2 (logger)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITLoggerN6TimeSet2GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("0-logger-7806d-0", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("1-logger-c32b6-1", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2-logger-fa9dc-2", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("6-logger-79f1e-6", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("7-logger-c5271-7", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("8-logger-9002b-8", 5, level4ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Time-Set3 (logger)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITLoggerN6TimeSet3GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("3-logger-42fe4-3", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("4-logger-ab3e4-4", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("5-logger-a1f5f-5", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("6-logger-79f1e-6", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("7-logger-c5271-7", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("8-logger-9002b-8", 5, level4ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Time-Set4 (logger)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITLoggerN6TimeSet4GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("0-logger-7806d-0", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("3-logger-42fe4-3", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("4-logger-ab3e4-4", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("5-logger-a1f5f-5", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("6-logger-79f1e-6", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("8-logger-9002b-8", 5, level3ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}

	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Time-Set1 (material-animations)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITMaterialAnimationsN6TimeSet1GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("0-Material-Animations-8b3b1-0", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("1-Material-Animations-d70bc-1", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2-Material-Animations-6297c-2", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("3-Material-Animations-400c1-3", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("4-Material-Animations-321c5-4", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("5-Material-Animations-ca00c-5", 5, level4ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Time-Set2 (material-animations)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITMaterialAnimationsN6TimeSet2GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("7-Material-Animations-ed323-7", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("8-Material-Animations-af4db-8", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("9-Material-Animations-918f8-9", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("10-Material-Animations-e6c86-10", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("11-Material-Animations-33c82-11", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("12-Material-Animations-e62af-12", 5, level4ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Time-Set3 (material-animations)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITMaterialAnimationsN6TimeSet3GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("1-Material-Animations-d70bc-1", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2-Material-Animations-6297c-2", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("3-Material-Animations-400c1-3", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("7-Material-Animations-ed323-7", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("8-Material-Animations-af4db-8", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("9-Material-Animations-918f8-9", 5, level4ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Time-Set4 (material-animations)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITMaterialAnimationsN6TimeSet4GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("0-Material-Animations-8b3b1-0", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("1-Material-Animations-d70bc-1", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2-Material-Animations-6297c-2", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("10-Material-Animations-e6c86-10", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("11-Material-Animations-33c82-11", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("12-Material-Animations-e62af-12", 5, level4ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}

	

	/**
	 * GIT logger Time GroundTruth Data, N=7
	 */
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N7-Time-Set1 (logger)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITLoggerN7TimeSet1GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("0-logger-7806d-0", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("1-logger-c32b6-1", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2-logger-fa9dc-2", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("3-logger-42fe4-3", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("4-logger-ab3e4-4", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("7-logger-c5271-7", 5, level4ChildNode);
		VariantTaxonomyNode level6ChildNode = new VariantTaxonomyNode("8-logger-9002b-8", 6, level5ChildNode);

		level5ChildNode.addChildNode(level6ChildNode);
		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N7-Time-Set2 (logger)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITLoggerN7TimeSet2GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2-logger-fa9dc-2", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("3-logger-42fe4-3", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("4-logger-ab3e4-4", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("5-logger-a1f5f-5", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("6-logger-79f1e-6", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("7-logger-c5271-7", 5, level4ChildNode);
		VariantTaxonomyNode level6ChildNode = new VariantTaxonomyNode("8-logger-9002b-8", 6, level5ChildNode);

		level5ChildNode.addChildNode(level6ChildNode);
		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N7-Time-Set3 (logger)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITLoggerN7TimeSet3GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("0-logger-7806d-0", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("1-logger-c32b6-1", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("3-logger-42fe4-3", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("4-logger-ab3e4-4", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("5-logger-a1f5f-5", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("6-logger-79f1e-6", 5, level4ChildNode);
		VariantTaxonomyNode level6ChildNode = new VariantTaxonomyNode("8-logger-9002b-8", 6, level5ChildNode);

		level5ChildNode.addChildNode(level6ChildNode);
		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N7-Time-Set4 (logger)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITLoggerN7TimeSet4GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("0-logger-7806d-0", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("1-logger-c32b6-1", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2-logger-fa9dc-2", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("4-logger-ab3e4-4", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("5-logger-a1f5f-5", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("7-logger-c5271-7", 5, level4ChildNode);
		VariantTaxonomyNode level6ChildNode = new VariantTaxonomyNode("8-logger-9002b-8", 6, level5ChildNode);

		level5ChildNode.addChildNode(level6ChildNode);
		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
 	
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N7-Time-Set1 (MaterialAnimations)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITMaterialAnimationsN7TimeSet1GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("0-Material-Animations-8b3b1-0", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("1-Material-Animations-d70bc-1", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2-Material-Animations-6297c-2", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("5-Material-Animations-ca00c-5", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("7-Material-Animations-ed323-7", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("9-Material-Animations-918f8-9", 5, level4ChildNode);
		VariantTaxonomyNode level6ChildNode = new VariantTaxonomyNode("10-Material-Animations-e6c86-10", 6, level5ChildNode);

		level5ChildNode.addChildNode(level6ChildNode);
		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N7-Time-Set2 (MaterialAnimations)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITMaterialAnimationsN7TimeSet2GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("3-Material-Animations-400c1-3", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("4-Material-Animations-321c5-4", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("5-Material-Animations-ca00c-5", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("6-Material-Animations-b6dbd-6", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("7-Material-Animations-ed323-7", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("8-Material-Animations-af4db-8", 5, level4ChildNode);
		VariantTaxonomyNode level6ChildNode = new VariantTaxonomyNode("11-Material-Animations-33c82-11", 6, level5ChildNode);

		level5ChildNode.addChildNode(level6ChildNode);
		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N7-Time-Set3 (MaterialAnimations)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITMaterialAnimationsN7TimeSet3GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("0-Material-Animations-8b3b1-0", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("3-Material-Animations-400c1-3", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("5-Material-Animations-ca00c-5", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("7-Material-Animations-ed323-7", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("8-Material-Animations-af4db-8", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("9-Material-Animations-918f8-9", 5, level4ChildNode);
		VariantTaxonomyNode level6ChildNode = new VariantTaxonomyNode("12-Material-Animations-e62af-12", 6, level5ChildNode);

		level5ChildNode.addChildNode(level6ChildNode);
		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N7-Time-Set4 (MaterialAnimations)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITMaterialAnimationsN7TimeSet4GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("1-Material-Animations-d70bc-1", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("4-Material-Animations-321c5-4", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("5-Material-Animations-ca00c-5", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("9-Material-Animations-918f8-9", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("10-Material-Animations-e6c86-10", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("11-Material-Animations-33c82-11", 5, level4ChildNode);
		VariantTaxonomyNode level6ChildNode = new VariantTaxonomyNode("12-Material-Animations-e62af-12", 6, level5ChildNode);

		level5ChildNode.addChildNode(level6ChildNode);
		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
 	
	
	/**
	 * GIT logger Time GroundTruth Data, N=8
	 */
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N8-Time-Set1 (logger)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITLoggerN8TimeSet1GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("0-logger-7806d-0", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("1-logger-c32b6-1", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2-logger-fa9dc-2", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("3-logger-42fe4-3", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("4-logger-ab3e4-4", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("6-logger-79f1e-6", 5, level4ChildNode);
		VariantTaxonomyNode level6ChildNode = new VariantTaxonomyNode("7-logger-c5271-7", 6, level5ChildNode);
		VariantTaxonomyNode level7ChildNode = new VariantTaxonomyNode("8-logger-9002b-8", 7, level6ChildNode);

		level6ChildNode.addChildNode(level7ChildNode);
		level5ChildNode.addChildNode(level6ChildNode);
		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N8-Time-Set2 (logger)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITLoggerN8TimeSet2GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("0-logger-7806d-0", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("1-logger-c32b6-1", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2-logger-fa9dc-2", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("3-logger-42fe4-3", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("4-logger-ab3e4-4", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("5-logger-a1f5f-5", 5, level4ChildNode);
		VariantTaxonomyNode level6ChildNode = new VariantTaxonomyNode("6-logger-79f1e-6", 6, level5ChildNode);
		VariantTaxonomyNode level7ChildNode = new VariantTaxonomyNode("7-logger-c5271-7", 7, level6ChildNode);

		level6ChildNode.addChildNode(level7ChildNode);
		level5ChildNode.addChildNode(level6ChildNode);
		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N8-Time-Set3 (logger)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITLoggerN8TimeSet3GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("0-logger-7806d-0", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("1-logger-c32b6-1", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2-logger-fa9dc-2", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("3-logger-42fe4-3", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("5-logger-a1f5f-5", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("6-logger-79f1e-6", 5, level4ChildNode);
		VariantTaxonomyNode level6ChildNode = new VariantTaxonomyNode("7-logger-c5271-7", 6, level5ChildNode);
		VariantTaxonomyNode level7ChildNode = new VariantTaxonomyNode("8-logger-9002b-8", 7, level6ChildNode);

		level6ChildNode.addChildNode(level7ChildNode);
		level5ChildNode.addChildNode(level6ChildNode);
		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N8-Time-Set4 (logger)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITLoggerN8TimeSet4GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("0-logger-7806d-0", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("1-logger-c32b6-1", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("3-logger-42fe4-3", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("4-logger-ab3e4-4", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("5-logger-a1f5f-5", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("6-logger-79f1e-6", 5, level4ChildNode);
		VariantTaxonomyNode level6ChildNode = new VariantTaxonomyNode("7-logger-c5271-7", 6, level5ChildNode);
		VariantTaxonomyNode level7ChildNode = new VariantTaxonomyNode("8-logger-9002b-8", 7, level6ChildNode);

		level6ChildNode.addChildNode(level7ChildNode);
		level5ChildNode.addChildNode(level6ChildNode);
		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
 	
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N8-Time-Set1 (MaterialAnimations)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITMaterialAnimationsN8TimeSet1GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("3-Material-Animations-400c1-3", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("4-Material-Animations-321c5-4", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("5-Material-Animations-ca00c-5", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("6-Material-Animations-b6dbd-6", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("7-Material-Animations-ed323-7", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("8-Material-Animations-af4db-8", 5, level4ChildNode);
		VariantTaxonomyNode level6ChildNode = new VariantTaxonomyNode("9-Material-Animations-918f8-9", 6, level5ChildNode);
		VariantTaxonomyNode level7ChildNode = new VariantTaxonomyNode("10-Material-Animations-e6c86-10", 7, level6ChildNode);

		level6ChildNode.addChildNode(level7ChildNode);
		level5ChildNode.addChildNode(level6ChildNode);
		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N8-Time-Set2 (MaterialAnimations)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITMaterialAnimationsN8TimeSet2GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2-Material-Animations-6297c-2", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("3-Material-Animations-400c1-3", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("4-Material-Animations-321c5-4", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("5-Material-Animations-ca00c-5", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("6-Material-Animations-b6dbd-6", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("7-Material-Animations-ed323-7", 5, level4ChildNode);
		VariantTaxonomyNode level6ChildNode = new VariantTaxonomyNode("10-Material-Animations-e6c86-10", 6, level5ChildNode);
		VariantTaxonomyNode level7ChildNode = new VariantTaxonomyNode("12-Material-Animations-e62af-12", 7, level6ChildNode);

		level6ChildNode.addChildNode(level7ChildNode);
		level5ChildNode.addChildNode(level6ChildNode);
		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N8-Time-Set3 (MaterialAnimations)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITMaterialAnimationsN8TimeSet3GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("3-Material-Animations-400c1-3", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("4-Material-Animations-321c5-4", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("6-Material-Animations-b6dbd-6", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("8-Material-Animations-af4db-8", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("9-Material-Animations-918f8-9", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("10-Material-Animations-e6c86-10", 5, level4ChildNode);
		VariantTaxonomyNode level6ChildNode = new VariantTaxonomyNode("11-Material-Animations-33c82-11", 6, level5ChildNode);
		VariantTaxonomyNode level7ChildNode = new VariantTaxonomyNode("12-Material-Animations-e62af-12", 7, level6ChildNode);

		level6ChildNode.addChildNode(level7ChildNode);
		level5ChildNode.addChildNode(level6ChildNode);
		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N8-Time-Set4 (MaterialAnimations)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITMaterialAnimationsN8TimeSet4GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("4-Material-Animations-321c5-4", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("5-Material-Animations-ca00c-5", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("6-Material-Animations-b6dbd-6", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("7-Material-Animations-ed323-7", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("8-Material-Animations-af4db-8", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("9-Material-Animations-918f8-9", 5, level4ChildNode);
		VariantTaxonomyNode level6ChildNode = new VariantTaxonomyNode("11-Material-Animations-33c82-11", 6, level5ChildNode);
		VariantTaxonomyNode level7ChildNode = new VariantTaxonomyNode("12-Material-Animations-e62af-12", 7, level6ChildNode);

		level6ChildNode.addChildNode(level7ChildNode);
		level5ChildNode.addChildNode(level6ChildNode);
		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
 	
	
	
	/**
	 * GIT logger Time GroundTruth Data, N=9
	 */
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N9-Time-Set1 (logger)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITLoggerN9TimeSet1GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("0-logger-7806d-0", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("1-logger-c32b6-1", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2-logger-fa9dc-2", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("3-logger-42fe4-3", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("4-logger-ab3e4-4", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("5-logger-a1f5f-5", 5, level4ChildNode);
		VariantTaxonomyNode level6ChildNode = new VariantTaxonomyNode("6-logger-79f1e-6", 6, level5ChildNode);
		VariantTaxonomyNode level7ChildNode = new VariantTaxonomyNode("7-logger-c5271-7", 7, level6ChildNode);
		VariantTaxonomyNode level8ChildNode = new VariantTaxonomyNode("8-logger-9002b-8", 8, level7ChildNode);

		level7ChildNode.addChildNode(level8ChildNode);
		level6ChildNode.addChildNode(level7ChildNode);
		level5ChildNode.addChildNode(level6ChildNode);
		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N9-Time-Set1 (MaterialAnimations)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITMaterialAnimationsN9TimeSet1GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("3-Material-Animations-400c1-3", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("4-Material-Animations-321c5-4", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("5-Material-Animations-ca00c-5", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("6-Material-Animations-b6dbd-6", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("7-Material-Animations-ed323-7", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("8-Material-Animations-af4db-8", 5, level4ChildNode);
		VariantTaxonomyNode level6ChildNode = new VariantTaxonomyNode("9-Material-Animations-918f8-9", 6, level5ChildNode);
		VariantTaxonomyNode level7ChildNode = new VariantTaxonomyNode("10-Material-Animations-e6c86-10", 7, level6ChildNode);
		VariantTaxonomyNode level8ChildNode = new VariantTaxonomyNode("11-Material-Animations-33c82-11", 8, level7ChildNode);

		level7ChildNode.addChildNode(level8ChildNode);
		level6ChildNode.addChildNode(level7ChildNode);
		level5ChildNode.addChildNode(level6ChildNode);
		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	
	
	/*** SPACE TIME DATA***/
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Time-Set1 (Photoview)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITPhotoviewN6TimeSet1GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("0-PhotoView-3f2b0-0", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("1-PhotoView-edd33-1", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2-PhotoView-34819-2", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("3-PhotoView-806a2-3", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("4-PhotoView-c1655-4", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("5-PhotoView-e8cb1-5", 5, level3ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Time-Set2 (Photoview)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITPhotoviewN6TimeSet2GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2-PhotoView-34819-2", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("3-PhotoView-806a2-3", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("4-PhotoView-c1655-4", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("5-PhotoView-e8cb1-5", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("6-PhotoView-f2b41-6", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("7-PhotoView-3b280-7", 5, level3ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Time-Set3 (Photoview)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITPhotoviewN6TimeSet3GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("0-PhotoView-3f2b0-0", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("1-PhotoView-edd33-1", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2-PhotoView-34819-2", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("6-PhotoView-f2b41-6", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("7-PhotoView-3b280-7", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("8-PhotoView-7d858-8", 5, level3ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Time-Set4 (Photoview)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITPhotoviewN6TimeSet4GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("1-PhotoView-edd33-1", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2-PhotoView-34819-2", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("5-PhotoView-e8cb1-5", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("6-PhotoView-f2b41-6", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("7-PhotoView-3b280-7", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("8-PhotoView-7d858-8", 5, level3ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}

	
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Time-Set1 (SocketioClient)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITSocketioClientN6TimeSet1GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("0-socket.io-client-java-9edb6-0", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("1-socket.io-client-java-4ca88-1", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2-socket.io-client-java-b0785-2", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("3-socket.io-client-java-65813-3", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("4-socket.io-client-java-026f3-4", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("5-socket.io-client-java-32909-5", 5, level3ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Time-Set2 (SocketioClient)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITSocketioClientN6TimeSet2GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2-socket.io-client-java-b0785-2", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("3-socket.io-client-java-65813-3", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("4-socket.io-client-java-026f3-4", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("6-socket.io-client-java-50868-6", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("7-socket.io-client-java-6ba58-7", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("8-socket.io-client-java-92c57-8", 5, level3ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Time-Set3 (SocketioClient)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITSocketioClientN6TimeSet3GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("0-socket.io-client-java-9edb6-0", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("1-socket.io-client-java-4ca88-1", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("5-socket.io-client-java-32909-5", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("6-socket.io-client-java-50868-6", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("7-socket.io-client-java-6ba58-7", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("8-socket.io-client-java-92c57-8", 5, level3ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph N6-Time-Set4 (SocketioClient)
	 * 
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGITSocketioClientN6TimeSet4GT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("3-socket.io-client-java-65813-3", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("4-socket.io-client-java-026f3-4", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("5-socket.io-client-java-32909-5", 2, level1ChildNode);
		VariantTaxonomyNode level3ChildNode = new VariantTaxonomyNode("6-socket.io-client-java-50868-6", 3, level2ChildNode);
		VariantTaxonomyNode level4ChildNode = new VariantTaxonomyNode("7-socket.io-client-java-6ba58-7", 4, level3ChildNode);
		VariantTaxonomyNode level5ChildNode = new VariantTaxonomyNode("8-socket.io-client-java-92c57-8", 5, level3ChildNode);

		level4ChildNode.addChildNode(level5ChildNode);
		level3ChildNode.addChildNode(level4ChildNode);
		level2ChildNode.addChildNode(level3ChildNode);
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);

		return rootNode;
	}

	
	
}




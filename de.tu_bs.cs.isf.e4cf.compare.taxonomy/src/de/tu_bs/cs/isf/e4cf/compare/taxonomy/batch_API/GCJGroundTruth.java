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
	 * End of 2008 Ground Truth Data
	 */
}

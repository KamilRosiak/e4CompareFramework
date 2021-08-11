/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures;

import java.util.ArrayList;
import java.util.List;

/**
 * @author developer-olan
 *
 */
public class VariantTaxonomyNode {

 private String dateCreated;
 private String varaintName;
 private int treeDepth;
 private VariantTaxonomyNode variantParent;
 private List<VariantTaxonomyNode> variantChildren  = new ArrayList<VariantTaxonomyNode>();

 
 public VariantTaxonomyNode(String _variantName, int _treeDepth, VariantTaxonomyNode _variantParent) {
	 this.varaintName = _variantName;
	 this.treeDepth = _treeDepth;
	 this.variantParent = _variantParent;
 }
 
 public VariantTaxonomyNode(String _variantName, int _treeDepth) {
	 this.varaintName = _variantName;
	 this.treeDepth = _treeDepth;
 }
 
 public VariantTaxonomyNode(String _variantName) {
	 this.varaintName = _variantName;
 }
 
 public boolean addChildNodeFromRoot(String childVariantName, String parentName) {
	 boolean childNodeAdded = false;
	 VariantTaxonomyNode newChildNode = new VariantTaxonomyNode(childVariantName);
	 
	 // Find Child Parent in Taxonomy Node Tree/Graph
	 VariantTaxonomyNode childParentNode = findChildParentRecursive(this.getVariantChildren(), parentName);
	 if (childParentNode == null) {
		 this.addChildNode(newChildNode);
		 newChildNode.setParent(this);
		 childNodeAdded = true;
	 } else {
		 // Child Parent Found, now add child to its parent
		 childParentNode.addChildNode(newChildNode); // Add ChildNode to Tree
		 newChildNode.setParent(childParentNode);
		 childNodeAdded = true;
	 }
	 
	 newChildNode.setTreeDepth();
	 
	 return childNodeAdded;
 }
 
 public VariantTaxonomyNode findChildParentRecursive(List <VariantTaxonomyNode> childrenVariants, String parentName) {
	 VariantTaxonomyNode foundParent = null;
	  
	 if (childrenVariants.size() > 0) {
		 for (VariantTaxonomyNode aChildVariant : this.variantChildren) {
			  if (aChildVariant.getVariantName().equals(parentName)) {
				  foundParent = aChildVariant;
				  break;
			  } else if (aChildVariant.getVariantChildren().size()> 0) {
				  findChildParentRecursive(aChildVariant.getVariantChildren(), parentName);
			  }
		 }
	  }
	 
	 return foundParent;
 }
 
 public void addChildNode(VariantTaxonomyNode childNode){
	this.variantChildren.add(childNode);
 }
 
 public String getVariantName() {
	 return this.varaintName;
 }
 
 public int getTreeDepth() {
	 return this.treeDepth;
 }
 
 public VariantTaxonomyNode getVariantParent() {
	 return this.variantParent;
 }
 
 public List<VariantTaxonomyNode> getVariantChildren() {
	 return this.variantChildren;
 }
 
 public String getCreationDate() {
	 return this.dateCreated;
 }
 
 public void setParent(VariantTaxonomyNode _parent) {
	 this.variantParent = _parent; 
 }
 
 public void setTreeDepth(int _treeDepth) {
	 this.treeDepth = _treeDepth; 
 }
 
 public void setTreeDepth() {
	 this.treeDepth = getVariantParent().treeDepth+1; 
 }
 
 
 
 
 
 
 
 
}

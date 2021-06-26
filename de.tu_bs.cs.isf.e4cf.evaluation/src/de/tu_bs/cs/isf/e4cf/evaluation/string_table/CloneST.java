package de.tu_bs.cs.isf.e4cf.evaluation.string_table;

public class CloneST {
	// Helper Functions
	public static final String COPY = "Copy";
	public static final String RCOPY = "RCopy";
	public static final String MOVE = "Move";
	public static final String MOVEPOS = "MovePOS";
	public static final String SWAP = "Swap";
	public static final String DELETE = "Delete";
	public static final String REFACTOR = "Refactor";
	public static final String SETATTR = "SetAttribute";
	
	// Attributes
	public static final String SOURCE = " source:";
	public static final String TARGET = " target:";
	public static final String CLONE = " clone:";
	public static final String INDEX = " index:";
	public static final String CONTAINER = " container:";
	public static final String TYPE = " type:";
	public static final String SCOPE = " scope:";
	public static final String FROM = " from:";
	public static final String TO = " to:";
	public static final String KEY = " key:";
	
	// Taxonomy Functions
	public static final String SYSTEMATIC_RENAMING = "Tax_SystematicRenaming";
	public static final String EXPRESSION = "Tax_ExpressionForParameter";
	public static final String ARBITRARY_RENAMING = "Tax_ArbitraryRenaming";
	public static final String INLINE_INSERTION_NODE = "Tax_SmallInlineInsertion_Node";
	public static final String INLINE_INSERTION_ATTR = "Tax_SmallInlineInsertion_Attribute";
	public static final String INLINE_DELETION_NODE = "Tax_SmallInlineDeletion_Node";
	public static final String INLINE_DELETION_ATTR = "Tax_SmallInlineDeletion_Attribute";
	public static final String DELETE_LINES = "Tax_DeleteLines";
	public static final String INSERT_LINES = "Tax_InsertLines";
}

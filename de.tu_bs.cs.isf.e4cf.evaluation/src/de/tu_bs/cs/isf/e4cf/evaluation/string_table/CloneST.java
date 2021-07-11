package de.tu_bs.cs.isf.e4cf.evaluation.string_table;

public class CloneST {
	// Non-atomic Functions
	public static final String NON_ATOMIC = "NonAtomic_";
	public static final String RCOPY = NON_ATOMIC + "RCopy";
	public static final String SWAP = NON_ATOMIC + "Swap";
	public static final String RDELETE = NON_ATOMIC + "RDelete";
	public static final String REFACTOR = NON_ATOMIC + "Refactor";
	
	// Atomic Functions
	public static final String ATOMIC = "Atomic_";
	public static final String COPY = ATOMIC + "Copy";
	public static final String MOVE = ATOMIC + "Move";
	public static final String MOVEPOS = ATOMIC + "MovePOS";
	public static final String DELETE = ATOMIC + "Delete";
	public static final String SETATTR = ATOMIC + "SetAttribute";
	
	// Attributes
	public static final String SOURCE = " source:";
	public static final String TARGET = " target:";
	public static final String CLONE = " clone:";
	public static final String CONTAINER = " container:";
	public static final String TYPE = " type:";
	public static final String SCOPE = " scope:";
	public static final String FROM = " from:";
	public static final String TO = " to:";
	public static final String KEY = " key:";
	
	// Taxonomy Functions
	public static final String TAX = "Tax_";
	public static final String SYSTEMATIC_RENAMING = TAX + "SystematicRenaming";
	public static final String EXPRESSION = TAX + "ExpressionForParameter";
	public static final String ARBITRARY_RENAMING = TAX + "ArbitraryRenaming";
	public static final String INLINE_INSERTION_NODE = TAX + "SmallInlineInsertion_Node";
	public static final String INLINE_INSERTION_ATTR = TAX + "SmallInlineInsertion_Attribute";
	public static final String INLINE_DELETION_NODE = TAX + "SmallInlineDeletion_Node";
	public static final String INLINE_DELETION_ATTR = TAX + "SmallInlineDeletion_Attribute";
	public static final String DELETE_LINES = TAX + "DeleteLines";
	public static final String INSERT_LINES = TAX + "InsertLines";
}

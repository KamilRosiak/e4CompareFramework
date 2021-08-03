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
	// Type II
	public static final String REFACTOR_IDENT = TAX + "RefactorIdentifier";
	public static final String REPLACE_IDENT = TAX + "ReplaceIdentifier";
	public static final String LITERAL_CHANGE = TAX + "LiteralChange";
	public static final String TYPE_CHANGE = TAX + "TypeChange";
	// TODO Type III
	public static final String TAX_ADD = TAX + "AddSubtree";
	public static final String TAX_MOVE = TAX + "MoveSubtree";
	public static final String TAX_DELETE = TAX + "DeleteSubtree";
	
	// Variant Functions
	public static final String LOG_SEPARATOR = "==========================================================";
	public static final String VARIANT = "NewVariant";
}

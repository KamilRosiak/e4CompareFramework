public enum NodeType {
	ANNOTATION(1),
	ARGUMENT(2),
	UNDEFINED(0)
	;
	
	public static NodeType fromString(String name) {
	    try {
	    	return valueOf(name);
	    } catch (IllegalArgumentException ex) {
	    	System.err.println("Tried to create unkown Standardized Node Type: " + name);
	    	return UNDEFINED;
	    }
	}
}
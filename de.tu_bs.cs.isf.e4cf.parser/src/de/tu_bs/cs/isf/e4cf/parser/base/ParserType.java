package de.tu_bs.cs.isf.e4cf.parser.base;

/**
 * Indicates a certain type of input file.
 * 
 * @author Oliver Urbaniak
 */
public enum ParserType {

	XML("XML", "xml", "XML files"),
	JSON("JSON", "json","JSON object files"),
	STRUCTURED_TEXT("Structured Text", "","IEC language: structured text"),
	SEQUENTIAL_FUNCTION_CHART("Sequential Function Chart", "","IEC language: sequential function chart"),
	FUNCTION_BLOCK("Function Block", "", "IEC language: function block"),
	LADDER_DIAGRAM("Ladder Diagram", "", "IEC language: ladder diagram"),
	PASCAL("Pascal", "pas", "pascal"),
	PIN_PASCAL("PIN Pascal", "pin", "pin pascal module"),
	ISP_PASCAL("ISP Pascal", "isp", "isp pascal module"),
	CUSTOM("Custom", "*", "customized parser type"),
	SLX_SIMULINK("SLX SIMULINK", "slx", "slx simulink model"),
	CPP("C++","cpp","c++ implementation"),
	JAVA("Java 8","java","Java implementation"),
	STEP("STEP ISO-Norm 10303","stp","STEP file");
	
	private String _desc;
	private String _extension;
	private String _name;
	
	ParserType(String name, String extension, String desc) {
		_name = name;
		_extension = extension;
		_desc = desc;
	}
	
	public String getName() {
		return _name;
	}

	public String getExtension() {
		return _extension;
	}
	
	public String getDescription() {
		return _desc;
	}
	
	@Override
	public String toString() {
		return _name;
	}
}

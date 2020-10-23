package de.tu_bs.cs.isf.e4cf.compare.stringtable;

public class CompareST {
    public static final String BUNDLE_NAME = "de.tu_bs.cs.isf.e4cf.compare";
    
    /*************** EXTENSION POINTS ****************************************/
    
    //Matcher Extension Point
    public static final String MATCHER_SYMBOLIC_NAME ="de.tu_bs.cs.isf.e4cf.compare.Matcher";
    public static final String MATCHER_EXTENSION = "matcher";
    
    //
    public static final String FILE_ENDING_METRIC = "metric";
    
    public static final String RAW_FOLDER =" 01 RAW";
    public static final String TREE_FOLDER =" 02 Trees";
    public static final String METRICS_FOLDER =" 03 Metrics";
    public static final String FAMILY_MODELS =" 04 Family Models";
    public static final String FEATURE_MODELS =" 05 Feature Models";

}

package de.tu_bs.cs.isf.e4cf.core.stringtable;

public abstract class E4CStringTable {

	public static final String DEFAULT_BUNDLE_NAME = "de.tu_bs.cs.isf.e4cf.core";
	
	
	/***
	 * VIEWS
	 */
	public static String CONSOL_VIEW_ID = "de.tu_bs.cs.isf.familymining.ppu_iec.rcp_e4.part.console_view";
	public static String PROJECT_EXPLORER_VIEW_ID = "de.tu_bs.cs.isf.e4cf.parts.project_explorer";
	public static String MAIN_WINDOW_ID = "de.tu_bs.cs.isf.e4cf.core.window.main";
	/***********
	 * FRAMEWORK 
	 ************/
	//Perspective Ids
	public static String PERSPECTIVE_DEFAULT = "de.tu_bs.cs.isf.familymining.ppu_iec.rcp_e4.perspective.default";
	
	//CSS Styles
	 public static final String DEFAULT_THEME = "de.tu_bs.cs.isf.e4cf.core.css.default";
	 public static final String DARK_THEME = "de.tu_bs.cs.isf.e4cf.core.css.dark";
	
	
	/***********
	 * Extension Points
	 ***********/
	public static String PREFERENCE_PAGE_SYMBOLIC_NAME      = "de.tu_bs.cs.isf.e4cf.core.PreferencesPage";
	public static String PREFERENCE_PAGE_ATTR_EXTENSION     = "pref_page_attr";
	public static String THEME_EXTENSION_POINT 			    = "org.eclipse.e4.ui.css.swt.theme";
	
	/***********
	 * PREFERENCES  
	 ****************/
	 //GENERAL
	public static final String LICENSE_AGREEMENT = "Show license dialog every time ?";
	
	
	public static final String LICENSE_AGREEMENT_KEY ="LICENSE_KEY";
	public static final String THEME_KEY ="THEME_KEY";
	public static final String WORKSPACE_LOCATION_KEY ="WORKSPACE_KEY";
	
	/***********
	 * LOGIN
	 ****************/
	public static final String LOGIN_MESSAGE = "please enter your key";
	public static final String LOGIN_PREF = "KEY_VALUE";
	
	/***********
	 * COMPARE ENGINE 
	 ****************/
	
	public static final String COMPARE_ENGINE_SHELL_NAME = "CompareEngine";
	public static final String PREFERENCE_MANAGER_SHELL_NAME ="Preferences";
	
	/***********
	 * IEC STUFF
	 ****************/
	
	//Project 
	public static final String CONFIG_DIRECTORY = "02 Metrics";
	public static final String MODEL_DIRECTORY ="01 Models";
	public static final String RESULT_DIRECTORY ="03 Results";
	public static final String FAMILY_MODEL_DIRECTORY ="04 FamilyModels";
	public static final String FEATURE_MODEL_DIRECTORY = "05 FeatureModels";
	public static final String UNMATCHED_DIRECTORY = "06 UnmatchedResults";
	
	public static final String FEATURE_MODEL_SUB_DIRECTORY = "Modification Sequences";

	
	//File Endings / File Naming Conventions
	public static final String FILE_ENDING_METRIC = "metric";
	public static final String FILE_ENDING_RESULT = "result";
	public static final String FILE_ENDING_RESULT_XML = "resultXML";
	public static final String FILE_ENDING_FAMILY_MODEL = "family";
	public static final String FILE_ENDING_FAMILY_MODEL_XML = "familyXML";
	public static final String FILE_ENDING_XML = "xml";
	public static final String FILE_ENDING_CONFIGURATION = "project";
	public static final String FILE_ENDING_UNIT = "unit";
	public static final String FILE_ENDING_CSV = "csv";
	
	/***********
	 * DIALOGS 
	 ****************/
	//Dialogs
	public static final String DIALOG_SELECT_METRIC = "Select Metric";
	public static final String DIALOG_OK = "ok";
	public static final String DIALOG_CANCEL = "cancel";
	public static final String DIALOG_RENAME = "rename";
	
}

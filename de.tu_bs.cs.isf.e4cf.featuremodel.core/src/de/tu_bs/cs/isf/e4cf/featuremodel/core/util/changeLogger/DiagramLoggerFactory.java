package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.changeLogger;

import java.io.File;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;

import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.changeLogger.logger.SimpleDiagramLogger;

public class DiagramLoggerFactory {
	
	static DiagramLogger currentlogger;
	static File exportLocation;
	
	@PostConstruct
	public static DiagramLogger getDiagramLogger(String diagramLogger){
	      if(diagramLogger == null){
	         return null;
	      }		
	      if(diagramLogger.equals(DiagramLoggerConsts.DIAGRAM_LOGGER_SIMPLE)) {
	    	  new SimpleDiagramLogger();
	    	  SimpleDiagramLogger logger = ContextInjectionFactory.make(SimpleDiagramLogger.class, EclipseContextFactory.create());
//	    			  new SimpleDiagramLogger();
//	    			  ContextInjectionFactory.make(SimpleDiagramLogger.class, EclipseContextFactory.create());
	    	  currentlogger = logger;
	    	  exportLocation = setExportLocation();
	    	  getApplicationContext();
	    	  return logger;
	      }
	      return null;
	   }
	
	public static DiagramLogger getCurrentLogger() {
		return currentlogger;
	}
	
	private static File setExportLocation() {
		File folder_fmChanges = new File(RCPContentProvider.getCurrentWorkspacePath()+E4CStringTable.FEATURE_MODEL_DIRECTORY+"/"+E4CStringTable.FEATURE_MODEL_SUB_DIRECTORY);
		folder_fmChanges.mkdirs();
		return folder_fmChanges;
	}
	
	public static File getExportLocation() {
		return exportLocation;
	}
	
	/**
	 * Gets the applications context and returns its service
	 */
	private static ServiceContainer getApplicationContext() {
		return ContextInjectionFactory.make(ServiceContainer.class, EclipseContextFactory.create());
	}
}
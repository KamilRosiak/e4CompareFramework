package de.tu_bs.cs.isf.e4cf.core.dnd.drop;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Control;
/**
 * Implement the class and overwrite the handleItem() method for using the control as drop target.
 * @author {Kamil Rosiak}
 *
 */
public abstract class AbstractFileDropTarget {

	public AbstractFileDropTarget(Control control ) {
		createControl(control);
	}
	
	public void createControl(Control control) {
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT;
		DropTarget target = new DropTarget(control, operations);
		// Receive data in Text or File format

		final FileTransfer fileTransfer = FileTransfer.getInstance();
		Transfer[] types = new Transfer[] {fileTransfer};
		target.setTransfer(types);
			 
		 	target.addDropListener(new DropTargetListener() {
		 	  public void dragEnter(DropTargetEvent event) {
		 	     if (event.detail == DND.DROP_DEFAULT) {
		 	         if ((event.operations & DND.DROP_COPY) != 0) {
		 	             event.detail = DND.DROP_COPY;
		 	         } else {
		 	             event.detail = DND.DROP_NONE;
		 	         }
		 	     }
		 	     // will accept text but prefer to have files dropped
		 	     for (int i = 0; i < event.dataTypes.length; i++) {
		 	         if (fileTransfer.isSupportedType(event.dataTypes[i])){
		 	             event.currentDataType = event.dataTypes[i];
		 	             // files should only be copied
		 	             if (event.detail != DND.DROP_COPY) {
		 	                 event.detail = DND.DROP_NONE;
		 	             }
		 	             break;
		 	         }
		 	     }
		 	   }
		 	  
		 	   public void dragOver(DropTargetEvent event) {
		 	   }
		 	   
		 	    public void dragOperationChanged(DropTargetEvent event) {
		 	        if (event.detail == DND.DROP_DEFAULT) {
		 	            if ((event.operations & DND.DROP_COPY) != 0) {
		 	                event.detail = DND.DROP_COPY;
			            } else {
		 	                event.detail = DND.DROP_NONE;
		 	            }
		 	        }
		        // files should only be copied
		 	        if (fileTransfer.isSupportedType(event.currentDataType)){
			            if (event.detail != DND.DROP_COPY) {
		 	                event.detail = DND.DROP_NONE;
		 	            }
		 	        }
		 	    }
		 	    
		 	    public void dragLeave(DropTargetEvent event) {
		 	    }
		 	    
		 	    public void dropAccept(DropTargetEvent event) {
		 	    }
		 	    
		 	    public void drop(DropTargetEvent event) {
		 	        if (fileTransfer.isSupportedType(event.currentDataType)){
		 	        	handleItem(event);
		 	        }
		 	    }
		 	});
	}
	
	public abstract void handleItem(DropTargetEvent event);


	
}
package de.tu_bs.cs.isf.e4cf.core.parts.console_view;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

/**
 * This view shows the java console.The implementation replace the java standard output to a outputstream and writs on a swt.Text. 
 * @param text is a member and presents the console output.
 * @author {Kamil Rosiak}
 */
public class ConsoleView {
	public static String VIEW_ID = "de.tu_bs.cs.isf.familymining.ppu_iec.rcp_e4.part.console_view";
	private Text text;
	
	@PostConstruct
	public void postConstruct(Composite parent, EMenuService menuService) {
		 text = new Text(parent,SWT.READ_ONLY | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		 menuService.registerContextMenu(text,"de.tu_bs.cs.isf.e4cf.parts.consol_view.popupmenu");
		 text.setBackground(null);

        final PrintStream oldOut = System.out;
        
        OutputStream out = new OutputStream() { 
        	StringBuffer buffer = new StringBuffer();
        	
        	@Override
        	public void write(final int b) throws IOException {
        		if (text.isDisposed())
        			return;
        		buffer.append((char) b);
        		oldOut.write(b);
        	}
        	
        	@Override
        	public void write(byte[] b, int off, int len) throws IOException {
        		super.write(b, off, len);
        		flush();
        	}
        	
        	@Override
        	public void flush() throws IOException {
        		final String newText = buffer.toString();
        		Display.getDefault().asyncExec(new Runnable() {
        			public void run() {
        				if (!text.isDisposed()) {
        					text.append(newText);	                    		
        				}
        			}
        		});
        		buffer = new StringBuffer();
        		oldOut.flush();
        	}
        };
        System.setOut(new PrintStream(out));
        
        text.addDisposeListener(new DisposeListener() {
            public void widgetDisposed(DisposeEvent e) {
                System.setOut(oldOut);
            }
        });
	}

		@Focus
	    public void setFocus(ESelectionService selectionSerivce) {
	        text.setFocus();
	        selectionSerivce.setSelection(text);
	    }
}
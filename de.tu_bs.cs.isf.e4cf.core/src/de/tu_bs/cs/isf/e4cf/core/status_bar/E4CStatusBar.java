package de.tu_bs.cs.isf.e4cf.core.status_bar;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ProgressBar;

import de.tu_bs.cs.isf.e4cf.core.status_bar.util.E4CStatus;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CEventTable;

public class E4CStatusBar {
	public final static int progressMaximum = 100;
	private ProgressBar progressBar;
	private String statusText ="";
	
	@PostConstruct
	public void createGui(Composite parent) {
		parent.setLayout(new FillLayout());
		progressBar = new ProgressBar(parent, SWT.None);
		progressBar.setMaximum(progressMaximum);
		progressBar.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {		      
			        Point point = progressBar.getSize();
			        Font font = new Font(progressBar.getShell().getDisplay(),"Segoe",9,SWT.None);
			        e.gc.setFont(font);
			        e.gc.setForeground(progressBar.getShell().getDisplay().getSystemColor(SWT.COLOR_BLACK));
			        
			        FontMetrics fontMetrics = e.gc.getFontMetrics();
			        int stringWidth = (int) (fontMetrics.getAverageCharacterWidth() * statusText.length());
			        int stringHeight = fontMetrics.getHeight();
			        
			        e.gc.drawString(statusText, (point.x-stringWidth)/2 , (point.y-stringHeight)/2, true);
			        font.dispose();
				
			}
		});
	}
	
	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	
	@Inject
	@Optional
	public void setStatus(@UIEventTopic(E4CEventTable.UPDATE_STATUS_BAR) E4CStatus status) {
		progressBar.setSelection(status.getCurrentStatus());
		progressBar.setMaximum(status.getMaximalStatus());
		setStatusText(status.getStatusText());
		progressBar.redraw();
	}
	
}
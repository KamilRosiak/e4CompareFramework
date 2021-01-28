package de.tu_bs.cs.isf.e4cf.text_editor.view;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;

import de.tu_bs.cs.isf.e4cf.text_editor.FileFormatContainer;
import de.tu_bs.cs.isf.e4cf.text_editor.interfaces.IFormatting;
import de.tu_bs.cs.isf.e4cf.text_editor.interfaces.IHighlighting;
import de.tu_bs.cs.isf.e4cf.text_editor.interfaces.IIndenting;
import javafx.application.Platform;
import javafx.scene.control.Tab;

/**
 * Extension of Tab class. This class adds a constructor containing the type of
 * a given file that is opened, as it is meant to hold different instances of a
 * text editor. It also has the function of highlighting its syntax, depending
 * on file extension (For now we have implemented highlighting for java and
 * xml).
 * 
 * @author Cedric Kapalla, Soeren Christmann, Lukas Cronauer, Erwin Wijaya
 * 
 */
public class EditorTab extends Tab {
	private enum Action {HIGHLIGHT, INDENT, FORMAT};
	private String fileEnding;
	private Timer timer;
	private CodeArea codeArea;

	/**
	 * Constructs a new Editor Tab.
	 * 
	 * @param text       The title of the Tab
	 * @param fileEnding The fileending of the file within the codearea
	 * @param content    The text withing the Codearea
	 */
	public EditorTab(String text, String fileEnding, String content) {
		this.fileEnding = fileEnding;
		codeArea = CodeAreaFactory.createCodeArea(content);
		setText(text);
		setContent(codeArea);
	}

	/**
	 * Returns the file extension
	 * 
	 * @return the current file ending
	 */
	public String getFileEnding() {
		return fileEnding;
	}
	
	@Override
	public void finalize() {
		this.canceHighlighting();
	}
	
	/**
	 * Schedules highlighting function every 500ms.
	 * 
	 * @param codeArea current area for text on active tab
	 */
	private void scheduleHighlighting(IHighlighting func) {
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						StyleSpans<Collection<String>> styleSpans;
						try {
							styleSpans = func.computeHighlighting(codeArea.getText());
							codeArea.setStyleSpans(0, styleSpans);
						} catch (NullPointerException e) {
							e.printStackTrace();
						}
					}
				});
			}
		};
		timer = new Timer();
		timer.schedule(timerTask, 0, 500);
	}

	/**
	 * Cancels the timer which computes the highlighting, if one is active.
	 *
	 * @author Lukas Cronauer
	 */
	public void canceHighlighting() {
		if (timer != null) {
			timer.cancel();
		}
	}

	public void initDisplayActions(FileFormatContainer container) {
		if (container.getHighlighter() != null) {
			runSafe(container.getHighlighter(), Action.HIGHLIGHT);
		}
		if (container.getIndenter() != null) {
			runSafe(container.getIndenter(), Action.INDENT);
		}
		if (container.getFormatter() != null) {
			runSafe(container.getFormatter(), Action.FORMAT);
		}
	}
	
	private void runSafe(Object extension, Action action) {
		ISafeRunnable runnable = new ISafeRunnable() {
	            @Override
	            public void handleException(Throwable e) {
	                System.out.println("Exception in " + fileEnding + " extension");
	                System.out.println(e.getMessage());
	            }

	            @Override
	            public void run() throws Exception {
	            	if (action == Action.HIGHLIGHT) {
						scheduleHighlighting(((IHighlighting) extension));
					} else if (action == Action.INDENT) {
						((IIndenting) extension).applyIndentation(codeArea);
					} else if (action == Action.FORMAT) {
						((IFormatting) extension).format(codeArea);
					}
	            }
		};
		SafeRunner.run(runnable);
	}
}
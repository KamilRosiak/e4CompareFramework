package de.tu_bs.cs.isf.e4cf.text_editor.highlighter;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.function.Function;

/**
 * SyntaxHighlighting class containing the method to check fileType of given
 * file, then compute the highlighting by calling the method from java/xml
 * highlighting, there's a timer for scheduler here, which will be called every
 * 500ms.
 *
 * @author Lukas Cronauer, Erwin Wijaya
 *
 */
public class SyntaxHighlighter {
	// reference to the codeArea the hightlighting is applied to
	private CodeArea codeArea;

	// reference to the corresponding highlighting function for the fileExtension
	private Function<String, StyleSpans<Collection<String>>> highlightingFunction;

	// executor / timer used to compute the highlighting at regular intervals
	private ExecutorService executor;
	private Timer timer;

	/**
	 * A Method to be called to initialize highlighting
	 * 
	 * @param fileType extension of the file
	 * @param codeArea current area for text on active tab
	 * @author Erwin Wijaya, Lukas Cronauer
	 */
	public SyntaxHighlighter(String fileType, CodeArea codeArea) {
		this.codeArea = codeArea;
		setHighlightingFunction(fileType);
		scheduleHighlighting(codeArea);
	}

	/**
	 * Initializes the highlighting function based on the file extension All file
	 * extensions that should be highlighted have to initialize their respective
	 * highlighting function here.
	 * 
	 * @param fileExtension extension of the file
	 */
	private void setHighlightingFunction(String fileExtension) {
		switch (fileExtension) {
		case "java":
			highlightingFunction = JavaHighlighting::computeHighlighting;
			break;

		case "xml":
			highlightingFunction = XMLHighlighting::computeHighlighting;
			break;

		default:
			highlightingFunction = NoHighlighting::computeHighlighting;
			break;
		}
	}

	/**
	 * Schedules highlighting function every 500ms
	 * 
	 * @param codeArea current area for text on active tab
	 */
	private void scheduleHighlighting(CodeArea codeArea) {
		/*
		 * Scheduling using a executor, currently not working as ReactFx classes are not
		 * included. Save to remove after WiSe 20/21!
		 */
//         executor = Executors.newSingleThreadExecutor();
//         Subscription cleanupWhenDone = codeArea.multiPlainChanges()
//                 .successionEnds(Duration.ofMillis(500))
//                 .supplyTask(this::computeHighlightingAsync)
//                 .awaitLatest(codeArea.multiPlainChanges())
//                 .filterMap(t -> {
//                     if(t.isSuccess()) {
//                         return Optional.of(t.get());
//                     } else {
//                         t.getFailure().printStackTrace();
//                         return Optional.empty();
//                     }
//                 })
//                 .subscribe(this::applyHighlighting);

		// call when no longer need it: `cleanupWhenFinished.unsubscribe();`

		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						String text = codeArea.getText();
						StyleSpans<Collection<String>> styleSpans;
						try {
							styleSpans = highlightingFunction.apply(text);
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
	 * Cancels the timer which computes the highlighting, if one is active
	 *
	 * @author Lukas Cronauer
	 */
	public void canceHighlighting() {
		if (timer != null) {
			timer.cancel();
		}
	}

	/**
	 * Calls the highlighting computation in the background using an executor
	 * 
	 * @return The computed highlighting
	 * @author Erwin Wijaya, Lukas Cronauer (from richtextfx)
	 */
	private Task<StyleSpans<Collection<String>>> computeHighlightingAsync() {
		String text = codeArea.getText();
		Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
			@Override
			protected StyleSpans<Collection<String>> call() throws Exception {
				return highlightingFunction.apply(text);
			}
		};
		executor.execute(task);
		return task;
	}

	/**
	 * Applies highlighting to the codeArea
	 * 
	 * @param highlighting
	 */
	private void applyHighlighting(StyleSpans<Collection<String>> highlighting) {
		codeArea.setStyleSpans(0, highlighting);
	}

}
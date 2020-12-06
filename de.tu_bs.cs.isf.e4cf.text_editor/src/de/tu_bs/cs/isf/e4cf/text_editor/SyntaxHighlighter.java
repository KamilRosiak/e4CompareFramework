package de.tu_bs.cs.isf.e4cf.text_editor;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;

import de.tu_bs.cs.isf.e4cf.text_editor.file_types.JavaFileType;
import de.tu_bs.cs.isf.e4cf.text_editor.file_types.XmlFileType;
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


public class SyntaxHighlighter {
    private CodeArea codeArea;
    private Pattern pattern;
    private Function<Matcher, String> styleClass;
    private ExecutorService executor;
    private Timer timer;
    
    public SyntaxHighlighter(String fileType, CodeArea codeArea) {
        this.codeArea = codeArea;
        initHighlighting(fileType, codeArea);
    }

    private void initHighlighting(String fileType, CodeArea codeArea) {
        initHighlightingData(fileType);
        scheduleHighlighting(codeArea);
    }

	private void initHighlightingData(String fileType) {
	    switch (fileType) {
	        case "java":
	            pattern = JavaFileType.PATTERN;
	            styleClass = JavaFileType::getStyleClass;
	            break;
	        
	        case "xml":
	            pattern = XmlFileType.PATTERN;
	            styleClass = XmlFileType::getStyleClass;
	            break;
	
	        default:
	            pattern = Pattern.compile("a^");
	    }
	}
    
	private StyleSpans<Collection<String>> computeHighlighting(String text) {
		Matcher matcher = pattern.matcher(text);
		int lastKwEnd = 0;
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		while(matcher.find()) {
			String styleClass = this.styleClass.apply(matcher);
		    spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
		    spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
		    lastKwEnd = matcher.end();
		}
		spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
		return spansBuilder.create();
	}

    private void scheduleHighlighting(CodeArea codeArea) {
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
    	
    	TimerTask timerTask = new TimerTask(){
            @Override
            public void run(){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run(){
                        String text = codeArea.getText();
                        StyleSpans<Collection<String>> styleSpans = computeHighlighting(text);
                        codeArea.setStyleSpans(0, styleSpans);
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 500);
    }
    
    public void canceHighlighting() {
    	if (timer != null) {
    		timer.cancel();
    	}
    }

    private Task<StyleSpans<Collection<String>>> computeHighlightingAsync() {
        String text = codeArea.getText();
        Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
            @Override
            protected StyleSpans<Collection<String>> call() throws Exception {
                return computeHighlighting(text);
            }
        };
        executor.execute(task);
        return task;
    }

    private void applyHighlighting(StyleSpans<Collection<String>> highlighting) {
        codeArea.setStyleSpans(0, highlighting);
    }

}
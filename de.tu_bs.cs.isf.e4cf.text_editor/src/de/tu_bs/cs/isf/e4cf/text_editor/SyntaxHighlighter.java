package de.tu_bs.cs.isf.e4cf.text_editor;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;

import de.tu_bs.cs.isf.e4cf.text_editor.stringtable.JavaST;
import de.tu_bs.cs.isf.e4cf.text_editor.stringtable.XmlST;
import javafx.concurrent.Task;
import javafx.stage.Stage;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.function.Function;


public class SyntaxHighlighter {
    private String fileType;
    private CodeArea codeArea;
    private String[] keywords;
    private Pattern pattern;
    private Function<String, String> styleClass;
    private ExecutorService executor;
    
    public SyntaxHighlighter(String fileType, CodeArea codeArea) {
        this.fileType = fileType;
        this.codeArea = codeArea;
        initHighlighting(fileType, codeArea);
    }

    private void initHighlighting(String fileType, CodeArea codeArea) {
        initHighlightingData(fileType);
        return;
    }

    private void initHighlightingData(String fileType) {
        switch (fileType) {
            case "java":
                keywords = JavaST.KEYWORDS;
                pattern = JavaST.PATTERN;
                styleClass = JavaST::getStyleClass;
                break;
            
            case "xml":
                keywords = XmlST.KEYWORDS;
                pattern = XmlST.PATTERN;
                styleClass = XmlST::getStyleClass;
                break;

            default:
                keywords = new String[0];
                pattern = Pattern.compile("a^");
        }
    }
    
    public StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = pattern.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while(matcher.find()) {
        	String styleClass = this.styleClass.apply(text);
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    public void start(CodeArea codeArea) {
        executor = Executors.newSingleThreadExecutor();
        Subscription cleanupWhenDone = codeArea.multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .supplyTask(this::computeHighlightingAsync)
                .awaitLatest(codeArea.multiPlainChanges())
                .filterMap(t -> {
                    if(t.isSuccess()) {
                        return Optional.of(t.get());
                    } else {
                        t.getFailure().printStackTrace();
                        return Optional.empty();
                    }
                })
                .subscribe(this::applyHighlighting);

        // call when no longer need it: `cleanupWhenFinished.unsubscribe();`
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
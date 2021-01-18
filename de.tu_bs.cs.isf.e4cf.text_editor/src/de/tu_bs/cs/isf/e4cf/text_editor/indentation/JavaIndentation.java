package de.tu_bs.cs.isf.e4cf.text_editor.indentation;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.CodeArea;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Indentation for Java-files
 * 
 * @author Lukas Cronauer, Erwin Wijaya
 * 
 */
public class JavaIndentation {
    private String whiteSpaceRegex = "^\\s+";
    private String braceRegex = "[{(]\\s*$";

    private CodeArea codeArea;

    public JavaIndentation (CodeArea codeArea) {
        this.codeArea = codeArea;
        apply(codeArea);
    }

    /**
     * Applying the auto Indentation
     * 
     * @param codeArea the codeArea to apply the indentation to
     * @author Lukas Cronauer, Erwin Wijaya
     */
    private void apply(CodeArea codeArea) {
        Pattern whiteSpace = Pattern.compile(whiteSpaceRegex);
        Pattern brace = Pattern.compile(braceRegex);
        codeArea.addEventFilter(KeyEvent.KEY_PRESSED, KE -> {
            if (KE.getCode() == KeyCode.ENTER) {
                List<String> segments = codeArea.getParagraph(codeArea.getCurrentParagraph()).getSegments();
                Matcher whiteSpaceInFront = whiteSpace.matcher(segments.get(0));
                Matcher newBlock = brace.matcher(segments.get(segments.size() - 1));
                
                if (newBlock.find()) {
                    // indent with one tab
                    Platform.runLater(() -> codeArea.insertText(codeArea.getCaretPosition(), "\t"));
                }

                if (whiteSpaceInFront.find()) {
                    // add indentation of the previous line to the new line
                    Platform.runLater(() -> codeArea.insertText(codeArea.getCaretPosition(), whiteSpaceInFront.group()));
                }
            }
        });
    }

}

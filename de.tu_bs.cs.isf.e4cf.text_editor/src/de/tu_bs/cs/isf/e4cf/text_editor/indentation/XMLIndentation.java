package de.tu_bs.cs.isf.e4cf.text_editor.indentation;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.CodeArea;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
/**
 *  Indentation for xml-files.
 *  
 * @author Lukas Cronauer, Erwin Wijaya
 *
 */
public class XMLIndentation {
    private String whiteSpaceRegex = "^\\s+";
    private String endtagRegex = ">\\s*$";
    private String closetagRegex = "(</.*\\s*>)$";

    private CodeArea codeArea;

    public XMLIndentation (CodeArea codeArea) {
        this.codeArea = codeArea;
        apply(codeArea);
    }

    /**
     * Applying the auto Indentation.
     * 
     * @param codeArea the codeArea to apply the indentation to
     * @author Lukas Cronauer, Erwin Wijaya
     */
    private void apply(CodeArea codeArea) {
        Pattern whiteSpace = Pattern.compile(whiteSpaceRegex);
        Pattern tag = Pattern.compile(endtagRegex);
        Pattern endtag = Pattern.compile(closetagRegex);
        codeArea.addEventFilter(KeyEvent.KEY_PRESSED, KE -> {
            if (KE.getCode() == KeyCode.ENTER) {
                List<String> segments = codeArea.getParagraph(codeArea.getCurrentParagraph()).getSegments();
                Matcher whiteSpaceInFront = whiteSpace.matcher(segments.get(0));
                Matcher newTag = tag.matcher(segments.get(segments.size() - 1));
                Matcher newEndtag = endtag.matcher(segments.get(segments.size() - 1));
               if (newTag.find() && !newEndtag.find()) {
                    // indent with one tab
                	
                	Platform.runLater(() -> codeArea.insertText(codeArea.getCaretPosition(), "  "));           	                 
                }

                if (whiteSpaceInFront.find()) {
                    // add indentation of the previous line to the new line
                	
                    Platform.runLater(() -> codeArea.insertText(codeArea.getCaretPosition(), whiteSpaceInFront.group()));
                }
            }
        });
    }

}

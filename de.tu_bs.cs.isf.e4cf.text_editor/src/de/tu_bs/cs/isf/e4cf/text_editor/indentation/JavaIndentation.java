package de.tu_bs.cs.isf.e4cf.text_editor.indentation;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.Paragraph;

import de.tu_bs.cs.isf.e4cf.text_editor.interfaces.IIndenting;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

/**
 * Indentation for Java-files
 * 
 * @author Lukas Cronauer, Erwin Wijaya
 * 
 */
public class JavaIndentation implements IIndenting {
    private String whiteSpaceRegex = "^\\s+";
    private String braceRegex = "[{(\\[]\\s*$";

    private CodeArea codeArea;

//    public JavaIndentation (CodeArea codeArea) {
//        this.codeArea = codeArea;
//        apply(codeArea);
//    }

    /**
     * Applying the auto Indentation.
     * 
     * @param codeArea the codeArea to apply the indentation to
     * @author Lukas Cronauer, Erwin Wijaya
     */
    public void applyIndentation(CodeArea codeArea) {
        Pattern whiteSpace = Pattern.compile(whiteSpaceRegex);
        Pattern brace = Pattern.compile(braceRegex);
        codeArea.addEventFilter(KeyEvent.KEY_PRESSED, KE -> {
            String line = codeArea.getParagraph(codeArea.getCurrentParagraph()).substring(0, codeArea.getCaretColumn());
            if (KE.getCode() == KeyCode.ENTER) {       
                Matcher whiteSpaceInFront = whiteSpace.matcher(line);
                Matcher newBlock = brace.matcher(line);
                
                if (newBlock.find()) {
                    // indent with one tab
                	Platform.runLater(() -> codeArea.insertText(codeArea.getCaretPosition(), "\t"));           	                 
                }

                if (whiteSpaceInFront.find()) {
                    // add indentation of the previous line to the new line
                	
                    Platform.runLater(() -> codeArea.insertText(codeArea.getCaretPosition(), whiteSpaceInFront.group()));
                }
            } else if (KE.getCode() == KeyCode.DIGIT7 && KE.isAltDown() && KE.isControlDown()) {
                insertClosingBrace("}");
            } else if (KE.getCode() == KeyCode.DIGIT8 && KE.isAltDown() && KE.isControlDown()) {
            	insertClosingBrace("]");
            } else if (KE.getCode() == KeyCode.DIGIT8 && KE.isShiftDown()) {
            	insertClosingBrace(")");
            } else if (KE.getCode() == KeyCode.DIGIT2 && KE.isShiftDown()) {
            	insertClosingBrace("\"");
            } else if (KE.getCode() == KeyCode.NUMBER_SIGN && KE.isShiftDown()) {
            	insertClosingBrace("'");
            } 
        });
    }
    /**
     * Inserts a character after another character
     * Sets the cursor back to the previous position
     * 
     * @param brace String to insert
     */
    private void insertClosingBrace(String brace) {
        Platform.runLater(() -> codeArea.insertText(codeArea.getCaretPosition(), brace));
        Platform.runLater(() -> codeArea.moveTo(codeArea.getCaretPosition() - 1));
    }

}

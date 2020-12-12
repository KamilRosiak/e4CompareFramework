package de.tu_bs.cs.isf.e4cf.text_editor.highlighter;

/**
 * Making a constructor that will be called upon highlighting
 * (currently been used on JavaHighlighting and served as a List) 
 *
 * @author Erwin Wijaya
 *
 */
public class Highlight {
	private final String css;
    private final int startIndex;
    private final int endIndex;

    public Highlight(String css, int startIndex, int endIndex){
        this.css = css;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public String getCss(){
        return css;
    }

    public int getLength(){
        return endIndex - startIndex;
    }

    public int getStartIndex(){
        return startIndex;
    }

    public int getEndIndex(){
        return endIndex;
    }   
}

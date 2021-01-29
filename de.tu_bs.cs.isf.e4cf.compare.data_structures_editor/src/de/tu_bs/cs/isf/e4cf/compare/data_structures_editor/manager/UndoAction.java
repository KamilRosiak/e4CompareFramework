package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager;

public interface UndoAction {

    void execute();

    void undo();
}

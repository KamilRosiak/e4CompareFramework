package de.tu_bs.cs.isf.e4cf.core.io.reader.java_reader.factory;

import com.github.javaparser.ast.stmt.Statement;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.core.io.reader.java_reader.JavaVisitor;

public interface IStatementNodeFactory {
    
    public Node createStatementNode(Statement stmt, Node parent, JavaVisitor visitor);
}

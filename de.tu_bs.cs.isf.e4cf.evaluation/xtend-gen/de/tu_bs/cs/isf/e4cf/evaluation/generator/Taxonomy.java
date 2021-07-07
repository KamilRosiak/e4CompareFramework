/**
 * This Class wraps the clone editing taxonomy of
 * 2009 - Roy, C. - A Mutation Injectionbased Automatic Framework for Evaluating Code Clone Detection Tools
 * Using the generic data structure and clone helper.
 */
package de.tu_bs.cs.isf.e4cf.evaluation.generator;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneHelper;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneLogger;
import de.tu_bs.cs.isf.e4cf.evaluation.string_table.CloneST;
import java.util.List;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.xtext.xbase.lib.Conversions;

@Creatable
@Singleton
@SuppressWarnings("all")
public class Taxonomy {
  @Inject
  private CloneHelper helper;
  
  @Inject
  private CloneLogger logger;
  
  public NodeImpl copyAndPaste(final Node source, final Node target) {
    NodeImpl _xblockexpression = null;
    {
      this.logger.logRaw("Tax_CopyPaste");
      _xblockexpression = this.helper.copyRecursively(source, target);
    }
    return _xblockexpression;
  }
  
  /**
   * @param container is the container of the declaration of the value to modify
   */
  public void systematicRenaming(final Node container, final String newValue) {
    this.logger.logRaw(CloneST.SYSTEMATIC_RENAMING);
    this.helper.refactor(container, newValue);
  }
  
  public void expressionsForParameters(final Node body, final String parameter, final String expression) {
    this.logger.logRaw(CloneST.EXPRESSION);
    final Consumer<Node> _function = (Node n) -> {
      final Consumer<Attribute> _function_1 = (Attribute a) -> {
        this.helper.setAttributeValue(n, 
          a.getAttributeKey(), 
          this.helper.getAttributeValue(n, a.getAttributeKey()).replace(parameter, expression));
      };
      n.getAttributes().forEach(_function_1);
    };
    this.helper.getAllChildren(body).forEach(_function);
  }
  
  /**
   * Swap two function argument nodes
   */
  public Node arbitraryRenaming(final Node n1, final Node n2) {
    Node _xblockexpression = null;
    {
      this.logger.logRaw(CloneST.ARBITRARY_RENAMING);
      _xblockexpression = this.helper.swap(n1, n2);
    }
    return _xblockexpression;
  }
  
  public Node smallInlineInsertion(final Node parent, final Node insertion) {
    Node _xblockexpression = null;
    {
      this.logger.logRaw(CloneST.INLINE_INSERTION_NODE);
      _xblockexpression = this.helper.move(insertion, parent);
    }
    return _xblockexpression;
  }
  
  public Node smallInlineInsertion(final Node parent, final Node insertion, final int index) {
    Node _xblockexpression = null;
    {
      this.logger.logRaw(CloneST.INLINE_INSERTION_NODE);
      _xblockexpression = this.helper.move(insertion, parent, index);
    }
    return _xblockexpression;
  }
  
  public void smallInlineInsertion(final Node parent, final String attributeKey, final String appendage) {
    this.logger.logRaw(CloneST.INLINE_INSERTION_ATTR);
    final String oldValue = this.helper.getAttributeValue(parent, attributeKey);
    this.helper.setAttributeValue(parent, attributeKey, (oldValue + appendage));
  }
  
  public void smallInlineInsertion(final Node parent, final String attributeKey, final String insertion, final int startIndex) {
    this.logger.logRaw(CloneST.INLINE_INSERTION_ATTR);
    final String oldValue = this.helper.getAttributeValue(parent, attributeKey);
    String _substring = oldValue.substring(0, startIndex);
    String _plus = (_substring + insertion);
    String _substring_1 = oldValue.substring(startIndex);
    String newValue = (_plus + _substring_1);
    this.helper.setAttributeValue(parent, attributeKey, newValue);
  }
  
  public boolean smallInlineDeletion(final Node removal) {
    boolean _xblockexpression = false;
    {
      this.logger.logRaw(CloneST.INLINE_DELETION_NODE);
      _xblockexpression = this.helper.delete(removal);
    }
    return _xblockexpression;
  }
  
  public void smallInlineDeletion(final Node parent, final String attributeKey, final String removePhrase) {
    this.logger.logRaw(CloneST.INLINE_DELETION_ATTR);
    String value = this.helper.getAttributeValue(parent, attributeKey);
    value.replaceAll(removePhrase, "");
    this.helper.setAttributeValue(parent, attributeKey, value);
  }
  
  public void deleteLines(final Node... nodes) {
    this.logger.logRaw(CloneST.DELETE_LINES);
    final Consumer<Node> _function = (Node n) -> {
      this.helper.delete(n);
    };
    ((List<Node>)Conversions.doWrapArray(nodes)).forEach(_function);
  }
  
  public void insertLines(final Node parent, final int targetIndex, final Node... insertions) {
    this.logger.logRaw(CloneST.INSERT_LINES);
    int index = targetIndex;
    for (final Node n : insertions) {
      int _plusPlus = index++;
      this.helper.move(n, parent, _plusPlus);
    }
  }
  
  public Object modifyLines() {
    return null;
  }
}

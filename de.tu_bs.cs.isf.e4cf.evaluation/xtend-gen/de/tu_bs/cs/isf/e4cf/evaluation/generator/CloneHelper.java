package de.tu_bs.cs.isf.e4cf.evaluation.generator;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

@Creatable
@Singleton
@SuppressWarnings("all")
public class CloneHelper {
  @Inject
  private CloneLogger logger;
  
  /**
   * Create a shallow copy of a node and assigns it to a target.
   * (Without the source children)
   * @param source The node to be cloned.
   * @param targetParent The parent for newly created source clone.
   * @return The clone of the source node
   */
  public NodeImpl copy(final Node source, final Node targetParent) {
    if ((!(source instanceof NodeImpl))) {
      return null;
    }
    final NodeImpl clone = new NodeImpl();
    clone.setNodeType(source.getNodeType());
    clone.setVariabilityClass(source.getVariabilityClass());
    clone.setParent(targetParent);
    final Consumer<Attribute> _function = (Attribute a) -> {
      String _attributeKey = a.getAttributeKey();
      List<Value> _attributeValues = a.getAttributeValues();
      AttributeImpl _attributeImpl = new AttributeImpl(_attributeKey, _attributeValues);
      clone.addAttribute(_attributeImpl);
    };
    source.getAttributes().forEach(_function);
    targetParent.addChild(clone);
    UUID _uUID = source.getUUID();
    String _plus = ("Copy source:" + _uUID);
    String _plus_1 = (_plus + " target:");
    UUID _uUID_1 = targetParent.getUUID();
    String _plus_2 = (_plus_1 + _uUID_1);
    String _plus_3 = (_plus_2 + " clone:");
    UUID _uUID_2 = clone.getUUID();
    String _plus_4 = (_plus_3 + _uUID_2);
    this.logger.logRaw(_plus_4);
    return clone;
  }
  
  /**
   * Creates a deep copy of a subtree.
   * @param source The subtrees root node to be moved
   * @param targetParent The parent for newly created source clone
   * @return The clone of the source node
   */
  public NodeImpl copyRecursively(final Node source, final Node targetParent) {
    if ((!(source instanceof NodeImpl))) {
      return null;
    }
    UUID _uUID = source.getUUID();
    String _plus = ("RCopy source:" + _uUID);
    String _plus_1 = (_plus + " target:");
    UUID _uUID_1 = targetParent.getUUID();
    String _plus_2 = (_plus_1 + _uUID_1);
    this.logger.logRaw(_plus_2);
    final NodeImpl clone = this._copyRecursively(source, targetParent);
    return clone;
  }
  
  private NodeImpl _copyRecursively(final Node source, final Node targetParent) {
    final NodeImpl clone = this.copy(source, targetParent);
    final Consumer<Node> _function = (Node c) -> {
      this._copyRecursively(c, clone);
    };
    source.getChildren().forEach(_function);
    return clone;
  }
  
  /**
   * Moves a node (subtree) by reassigning the references
   * @param source The subtrees root node to be moved
   * @param targetParent The new parent for the source
   * @return always the source
   */
  public Node move(final Node source, final Node targetParent) {
    UUID _uUID = source.getUUID();
    String _plus = ("Move source:" + _uUID);
    String _plus_1 = (_plus + " target:");
    UUID _uUID_1 = targetParent.getUUID();
    String _plus_2 = (_plus_1 + _uUID_1);
    this.logger.logRaw(_plus_2);
    final Node oldParent = source.getParent();
    oldParent.getChildren().remove(source);
    targetParent.addChild(source);
    source.setParent(targetParent);
    return source;
  }
  
  /**
   * Moves a node to the specified index in its current parent
   * @param source Node to move
   * @param targetIndex Position in parents children list
   * @return the moved Node
   */
  public Node move(final Node source, final int targetIndex) {
    final Node parent = source.getParent();
    int index = targetIndex;
    if (((targetIndex < 0) || (targetIndex > (parent.getNumberOfChildren() - 1)))) {
      System.err.println("Error while moving node position: Appending node to the end");
      int _numberOfChildren = parent.getNumberOfChildren();
      int _minus = (_numberOfChildren - 1);
      index = _minus;
    }
    UUID _uUID = source.getUUID();
    String _plus = ("MovePos source:" + _uUID);
    String _plus_1 = (_plus + " index:");
    String _plus_2 = (_plus_1 + Integer.valueOf(index));
    this.logger.logRaw(_plus_2);
    parent.getChildren().remove(source);
    parent.getChildren().add(index, source);
    return source;
  }
  
  /**
   * Moves a node to the specified index in the specified parent
   * @param source Node to move
   * @param targetParent The new parent for the source
   * @param targetIndex Position in targetParents children list
   * @return the moved Node
   */
  public Node move(final Node source, final Node targetParent, final int targetIndex) {
    this.move(source, targetParent);
    this.move(source, targetIndex);
    return source;
  }
  
  /**
   * Deletes a node from the tree by removing it from its parent
   * @param source Node to be removed
   */
  public boolean delete(final Node source) {
    boolean _xblockexpression = false;
    {
      UUID _uUID = source.getUUID();
      String _plus = ("Delete " + _uUID);
      this.logger.logRaw(_plus);
      _xblockexpression = source.getParent().getChildren().remove(source);
    }
    return _xblockexpression;
  }
  
  public ArrayList<Class<Node>> getAllChildren(final Node root) {
    ArrayList<Class<Node>> nodes = CollectionLiterals.<Class<Node>>newArrayList(Node.class);
    this._getAllChildren(root, nodes);
    return nodes;
  }
  
  private void _getAllChildren(final Node root, final List nodes) {
    final Consumer<Node> _function = (Node c) -> {
      nodes.add(c);
      this._getAllChildren(c, nodes);
    };
    root.getChildren().forEach(_function);
  }
}

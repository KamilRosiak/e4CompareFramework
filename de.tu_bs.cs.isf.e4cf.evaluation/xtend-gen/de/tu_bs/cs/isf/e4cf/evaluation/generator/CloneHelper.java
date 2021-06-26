package de.tu_bs.cs.isf.e4cf.evaluation.generator;

import com.google.common.base.Objects;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

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
   * Swaps two nodes
   */
  public Node swap(final Node n1, final Node n2) {
    Node _xblockexpression = null;
    {
      UUID _uUID = n1.getUUID();
      String _plus = ("Swap n1:" + _uUID);
      String _plus_1 = (_plus + " n2:");
      UUID _uUID_1 = n2.getUUID();
      String _plus_2 = (_plus_1 + _uUID_1);
      this.logger.logRaw(_plus_2);
      final Node parent1 = n1.getParent();
      final int index1 = parent1.getChildren().indexOf(n1);
      final Node parent2 = n2.getParent();
      final int index2 = parent2.getChildren().indexOf(n2);
      this.move(n1, parent2, index2);
      _xblockexpression = this.move(n2, parent1, index1);
    }
    return _xblockexpression;
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
  
  /**
   * Returns all children of the given node in depth first order
   * @param root start node
   */
  public ArrayList<Node> getAllChildren(final Node root) {
    ArrayList<Node> nodes = CollectionLiterals.<Node>newArrayList();
    this._getAllChildren(root, nodes);
    return nodes;
  }
  
  private void _getAllChildren(final Node root, final List<Node> nodes) {
    final Consumer<Node> _function = (Node c) -> {
      nodes.add(c);
      this._getAllChildren(c, nodes);
    };
    root.getChildren().forEach(_function);
  }
  
  /**
   * Finds the first element of type belowroot
   * @param root start node
   * @param type node type to find
   */
  public Node findFirst(final Node root, final String type) {
    final Function1<Node, Boolean> _function = (Node n) -> {
      String _nodeType = n.getNodeType();
      return Boolean.valueOf(Objects.equal(_nodeType, type));
    };
    return IterableExtensions.<Node>findFirst(this.getAllChildren(root), _function);
  }
  
  /**
   * Performs refactoring depending on what type of container is given and
   *  replaces the occurences of the old value
   */
  public void refactor(final Node container, final String newValue) {
    if ((container instanceof NodeImpl)) {
      Node body = null;
      final String oldValue = this.getAttributeValue(container, "Name");
      String _nodeType = ((NodeImpl)container).getNodeType();
      boolean _equals = Objects.equal(_nodeType, "VariableDeclarator");
      if (_equals) {
        body = ((NodeImpl)container).getParent().getParent();
      } else {
        boolean _startsWith = ((NodeImpl)container).getNodeType().startsWith("Argument");
        if (_startsWith) {
          this.setAttributeValue(container, "Name", newValue);
          body = ((NodeImpl)container).getParent().getParent().getChildren().get(1);
        } else {
          String _nodeType_1 = ((NodeImpl)container).getNodeType();
          boolean _equals_1 = Objects.equal(_nodeType_1, "MethodDeclaration");
          if (_equals_1) {
            body = ((NodeImpl)container).getParent();
          } else {
            return;
          }
        }
      }
      UUID _uUID = ((NodeImpl)container).getUUID();
      String _plus = ("Refactor container:" + _uUID);
      String _plus_1 = (_plus + " type:");
      String _nodeType_2 = ((NodeImpl)container).getNodeType();
      String _plus_2 = (_plus_1 + _nodeType_2);
      String _plus_3 = (_plus_2 + 
        " scope:");
      UUID _uUID_1 = body.getUUID();
      String _plus_4 = (_plus_3 + _uUID_1);
      String _plus_5 = (_plus_4 + " from:");
      String _plus_6 = (_plus_5 + oldValue);
      String _plus_7 = (_plus_6 + " to:");
      String _plus_8 = (_plus_7 + newValue);
      this.logger.logRaw(_plus_8);
      this._refactor(body, Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("Name", "Initilization", "Value", "Comparison", "Condition", "Update")), oldValue, newValue);
    }
  }
  
  private void _refactor(final Node body, final List<String> attrKeys, final String oldValue, final String newValue) {
    final Consumer<String> _function = (String k) -> {
      this._refactor(body, k, oldValue, newValue);
    };
    attrKeys.forEach(_function);
  }
  
  private void _refactor(final Node body, final String attrKey, final String oldValue, final String newValue) {
    final Function1<Node, Boolean> _function = (Node n) -> {
      final Function1<Attribute, Boolean> _function_1 = (Attribute a) -> {
        return Boolean.valueOf((Objects.equal(a.getAttributeKey(), attrKey) && ((String) IterableExtensions.<Value>head(a.getAttributeValues()).getValue()).contains(oldValue)));
      };
      return Boolean.valueOf(IterableExtensions.<Attribute>exists(n.getAttributes(), _function_1));
    };
    final Consumer<Node> _function_1 = (Node n) -> {
      final String newAttrValue = this.getAttributeValue(n, attrKey).replaceAll(oldValue, newValue);
      this.setAttributeValue(n, attrKey, newAttrValue);
      UUID _uUID = n.getUUID();
      String _plus = ((("SetAttr key: " + attrKey) + " ofNode: ") + _uUID);
      String _plus_1 = (_plus + " from:");
      String _plus_2 = (_plus_1 + oldValue);
      String _plus_3 = (_plus_2 + " to:");
      String _plus_4 = (_plus_3 + newAttrValue);
      this.logger.logRaw(_plus_4);
    };
    IterableExtensions.<Node>filter(this.getAllChildren(body), _function).forEach(_function_1);
  }
  
  public void setAttributeValue(final Node node, final String attributeKey, final String newValue) {
    UUID _uUID = node.getUUID();
    String _plus = ("SetAttribute node:" + _uUID);
    String _plus_1 = (_plus + " key:");
    String _plus_2 = (_plus_1 + attributeKey);
    String _plus_3 = (_plus_2 + " oldValue:");
    String _attributeValue = this.getAttributeValue(node, attributeKey);
    String _plus_4 = (_plus_3 + _attributeValue);
    String _plus_5 = (_plus_4 + " newValue:");
    String _plus_6 = (_plus_5 + newValue);
    this.logger.logRaw(_plus_6);
    Value _head = IterableExtensions.<Value>head(node.getAttributeForKey(attributeKey).getAttributeValues());
    _head.setValue(newValue);
  }
  
  public String getAttributeValue(final Node node, final String attributeKey) {
    Object _value = IterableExtensions.<Value>head(node.getAttributeForKey(attributeKey).getAttributeValues()).getValue();
    return ((String) _value);
  }
}

package de.tu_bs.cs.isf.e4cf.evaluation.generator;

import com.google.common.base.Objects;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
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
  
  public Node findFirst(final Node root, final String type) {
    final Function1<Node, Boolean> _function = (Node n) -> {
      String _nodeType = n.getNodeType();
      return Boolean.valueOf(Objects.equal(_nodeType, "VariableDeclarator"));
    };
    return IterableExtensions.<Node>findFirst(this.getAllChildren(root), _function);
  }
  
  public Iterable<Node> refactor(final Node container, final String newValue) {
    Iterable<Node> _xifexpression = null;
    if ((container instanceof NodeImpl)) {
      Iterable<Node> _xifexpression_1 = null;
      String _nodeType = ((NodeImpl)container).getNodeType();
      boolean _equals = Objects.equal(_nodeType, "VariableDeclarator");
      if (_equals) {
        final String oldValue = IterableExtensions.<Value>head(((NodeImpl)container).getAttributeForKey("Name").getAttributeValues()).getValue().toString();
        final Node body = ((NodeImpl)container).getParent().getParent();
        final Function1<Node, Boolean> _function = (Node n) -> {
          final Function1<Attribute, Boolean> _function_1 = (Attribute a) -> {
            return Boolean.valueOf((Objects.equal(a.getAttributeKey(), "Name") && Objects.equal(((String) IterableExtensions.<Value>head(a.getAttributeValues()).getValue()), oldValue)));
          };
          return Boolean.valueOf(IterableExtensions.<Attribute>exists(n.getAttributes(), _function_1));
        };
        final Consumer<Node> _function_1 = (Node n) -> {
          n.getAttributeForKey("Name").getAttributeValues().remove(0);
          Attribute _attributeForKey = n.getAttributeForKey("Name");
          StringValueImpl _stringValueImpl = new StringValueImpl(newValue);
          _attributeForKey.addAttributeValue(_stringValueImpl);
        };
        IterableExtensions.<Node>filter(this.getAllChildren(body), _function).forEach(_function_1);
        final Function1<Node, Boolean> _function_2 = (Node n) -> {
          final Function1<Attribute, Boolean> _function_3 = (Attribute a) -> {
            return Boolean.valueOf((Objects.equal(a.getAttributeKey(), "Value") && ((String) IterableExtensions.<Value>head(a.getAttributeValues()).getValue()).contains(oldValue)));
          };
          return Boolean.valueOf(IterableExtensions.<Attribute>exists(n.getAttributes(), _function_3));
        };
        final Consumer<Node> _function_3 = (Node n) -> {
          Object _value = IterableExtensions.<Value>head(n.getAttributeForKey("Value").getAttributeValues()).getValue();
          String v = ((String) _value);
          Value _head = IterableExtensions.<Value>head(n.getAttributeForKey("Value").getAttributeValues());
          _head.setValue(v.replaceAll(oldValue, newValue));
        };
        IterableExtensions.<Node>filter(this.getAllChildren(body), _function_2).forEach(_function_3);
      } else {
        Iterable<Node> _xifexpression_2 = null;
        boolean _startsWith = ((NodeImpl)container).getNodeType().startsWith("Argument");
        if (_startsWith) {
          Iterable<Node> _xblockexpression = null;
          {
            Value _head = IterableExtensions.<Value>head(((NodeImpl)container).getAttributeForKey("Name").getAttributeValues());
            _head.setValue(newValue);
            final String oldValue_1 = IterableExtensions.<Value>head(((NodeImpl)container).getAttributeForKey("Name").getAttributeValues()).getValue().toString();
            final Node body_1 = ((NodeImpl)container).getParent().getParent().getChildren().get(1);
            final Function1<Node, Boolean> _function_4 = (Node n) -> {
              final Function1<Attribute, Boolean> _function_5 = (Attribute a) -> {
                return Boolean.valueOf((((Objects.equal(a.getAttributeKey(), "Initilization") || Objects.equal(a.getAttributeKey(), "Name")) || Objects.equal(a.getAttributeKey(), "Value")) && ((String) IterableExtensions.<Value>head(a.getAttributeValues()).getValue()).contains(oldValue_1)));
              };
              return Boolean.valueOf(IterableExtensions.<Attribute>exists(n.getAttributes(), _function_5));
            };
            _xblockexpression = IterableExtensions.<Node>filter(this.getAllChildren(body_1), _function_4);
          }
          _xifexpression_2 = _xblockexpression;
        }
        _xifexpression_1 = _xifexpression_2;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
}

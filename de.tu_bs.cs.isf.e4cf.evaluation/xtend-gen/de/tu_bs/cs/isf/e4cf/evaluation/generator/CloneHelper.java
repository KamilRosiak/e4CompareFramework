package de.tu_bs.cs.isf.e4cf.evaluation.generator;

import com.google.common.base.Objects;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.gson.GsonExportService;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.gson.GsonImportService;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneLogger;
import de.tu_bs.cs.isf.e4cf.evaluation.string_table.CloneST;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@Creatable
@Singleton
@SuppressWarnings("all")
public class CloneHelper {
  @Inject
  private CloneLogger logger;
  
  @Inject
  private GsonExportService exporter;
  
  @Inject
  private GsonImportService importer;
  
  private Tree trackingTree;
  
  /**
   * Sets up the shadow tree, to track modifications on original tree and remove invalid ones
   */
  public Tree setTrackingTree(final Tree original) {
    return this.trackingTree = this.deepCopy(original);
  }
  
  public Tree getTrackingTree() {
    return this.trackingTree;
  }
  
  public TreeImpl deepCopy(final Tree t) {
    final String originalTree = this.exporter.exportTree(((TreeImpl) t));
    return this.importer.importTree(originalTree);
  }
  
  /**
   * Create a shallow copy of a node and assigns it to a target.
   * (Without the source children)
   * @param source The node to be cloned.
   * @param targetParent The parent for newly created source clone.
   * @param preserveUUID If true, the source uuid will be reused instead of a new one being created
   * @return The clone of the source node
   */
  public NodeImpl copy(final Node source, final Node targetParent, final boolean preserveUUID) {
    if ((!(source instanceof NodeImpl))) {
      return null;
    }
    final NodeImpl clone = new NodeImpl();
    if (preserveUUID) {
      clone.setUUID(UUID.fromString(source.getUUID().toString()));
    }
    clone.setNodeType(source.getNodeType());
    clone.setStandardizedNodeType(source.getStandardizedNodeType());
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
    final NodeImpl shadowClone = new NodeImpl();
    shadowClone.setNodeType(clone.getNodeType());
    shadowClone.setUUID(UUID.fromString(clone.getUUID().toString()));
    shadowClone.setStandardizedNodeType(clone.getStandardizedNodeType());
    shadowClone.setVariabilityClass(clone.getVariabilityClass());
    final Function1<Node, Boolean> _function_1 = (Node n) -> {
      UUID _uUID = n.getUUID();
      UUID _uUID_1 = targetParent.getUUID();
      return Boolean.valueOf(Objects.equal(_uUID, _uUID_1));
    };
    final Node shadowParent = IterableExtensions.<Node>findFirst(this.trackingTree.getRoot().depthFirstSearch(), _function_1);
    shadowParent.getChildren().add(shadowClone);
    shadowClone.setParent(shadowParent);
    final Consumer<Attribute> _function_2 = (Attribute a) -> {
      String _attributeKey = a.getAttributeKey();
      List<Value> _attributeValues = a.getAttributeValues();
      AttributeImpl _attributeImpl = new AttributeImpl(_attributeKey, _attributeValues);
      shadowClone.addAttribute(_attributeImpl);
    };
    source.getAttributes().forEach(_function_2);
    UUID _uUID = source.getUUID();
    String _plus = ((CloneST.COPY + CloneST.SOURCE) + _uUID);
    String _plus_1 = (_plus + CloneST.TARGET);
    UUID _uUID_1 = targetParent.getUUID();
    String _plus_2 = (_plus_1 + _uUID_1);
    String _plus_3 = (_plus_2 + CloneST.CLONE);
    UUID _uUID_2 = clone.getUUID();
    String _plus_4 = (_plus_3 + _uUID_2);
    this.logger.logRaw(_plus_4);
    return clone;
  }
  
  /**
   * Creates a deep copy of a subtree.
   * 
   * Make sure that the target parent is not contained in source!
   * 
   * @param source The subtrees root node to be moved
   * @param targetParent The parent for newly created source clone
   * @param preserveUUIDs If true, the sources uuids will be reused instead of new ones being created
   * @return The clone of the source node
   */
  public NodeImpl copyRecursively(final Node source, final Node targetParent, final boolean preserveUUIDs) {
    if ((!(source instanceof NodeImpl))) {
      return null;
    }
    UUID _uUID = source.getUUID();
    String _plus = ((CloneST.RCOPY + CloneST.SOURCE) + _uUID);
    String _plus_1 = (_plus + CloneST.TARGET);
    UUID _uUID_1 = targetParent.getUUID();
    String _plus_2 = (_plus_1 + _uUID_1);
    this.logger.logRaw(_plus_2);
    final NodeImpl clone = this._copyRecursively(source, targetParent, preserveUUIDs);
    return clone;
  }
  
  /**
   * Creates a deep copy of a subtree.
   * 
   * Make sure that the target parent is not contained in source!
   * 
   * @param source The subtrees root node to be moved
   * @param targetParent The parent for newly created source clone
   * @return The clone of the source node
   */
  public NodeImpl copyRecursively(final Node source, final Node targetParent) {
    return this.copyRecursively(source, targetParent, false);
  }
  
  private NodeImpl _copyRecursively(final Node source, final Node targetParent, final boolean preserveUUIDs) {
    final NodeImpl clone = this.copy(source, targetParent, preserveUUIDs);
    final Consumer<Node> _function = (Node c) -> {
      this._copyRecursively(c, clone, preserveUUIDs);
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
    final Function1<String, Boolean> _function = (String n) -> {
      UUID _uUID = source.getUUID();
      String _plus = ((CloneST.MOVE + CloneST.SOURCE) + _uUID);
      return Boolean.valueOf(n.startsWith(_plus));
    };
    final String previousMove = IterableExtensions.<String>findLast(this.logger.getLog(), _function);
    final Node oldParent = source.getParent();
    if ((previousMove != null)) {
      this.logger.getLog().remove(previousMove);
      final Function1<Node, Boolean> _function_1 = (Node n) -> {
        UUID _uUID = n.getUUID();
        UUID _uUID_1 = source.getUUID();
        return Boolean.valueOf(Objects.equal(_uUID, _uUID_1));
      };
      final UUID originalParentUuid = IterableExtensions.<Node>findFirst(this.trackingTree.getRoot().depthFirstSearch(), _function_1).getParent().getUUID();
      UUID _uUID = targetParent.getUUID();
      boolean _notEquals = (!Objects.equal(originalParentUuid, _uUID));
      if (_notEquals) {
        UUID _uUID_1 = source.getUUID();
        String _plus = ((CloneST.MOVE + CloneST.SOURCE) + _uUID_1);
        String _plus_1 = (_plus + CloneST.TARGET);
        UUID _uUID_2 = targetParent.getUUID();
        String _plus_2 = (_plus_1 + _uUID_2);
        this.logger.logRaw(_plus_2);
      }
    } else {
      UUID _uUID_3 = oldParent.getUUID();
      UUID _uUID_4 = targetParent.getUUID();
      boolean _notEquals_1 = (!Objects.equal(_uUID_3, _uUID_4));
      if (_notEquals_1) {
        UUID _uUID_5 = source.getUUID();
        String _plus_3 = ((CloneST.MOVE + CloneST.SOURCE) + _uUID_5);
        String _plus_4 = (_plus_3 + CloneST.TARGET);
        UUID _uUID_6 = targetParent.getUUID();
        String _plus_5 = (_plus_4 + _uUID_6);
        this.logger.logRaw(_plus_5);
      }
    }
    oldParent.getChildren().remove(source);
    targetParent.addChild(source);
    source.setParent(targetParent);
    final Function1<Node, Boolean> _function_2 = (Node n) -> {
      UUID _uUID_7 = n.getUUID();
      UUID _uUID_8 = source.getUUID();
      return Boolean.valueOf(Objects.equal(_uUID_7, _uUID_8));
    };
    final Node shadowSource = IterableExtensions.<Node>findFirst(this.trackingTree.getRoot().depthFirstSearch(), _function_2);
    final Function1<Node, Boolean> _function_3 = (Node n) -> {
      UUID _uUID_7 = n.getUUID();
      UUID _uUID_8 = oldParent.getUUID();
      return Boolean.valueOf(Objects.equal(_uUID_7, _uUID_8));
    };
    IterableExtensions.<Node>findFirst(this.trackingTree.getRoot().depthFirstSearch(), _function_3).getChildren().remove(shadowSource);
    final Function1<Node, Boolean> _function_4 = (Node n) -> {
      UUID _uUID_7 = n.getUUID();
      UUID _uUID_8 = targetParent.getUUID();
      return Boolean.valueOf(Objects.equal(_uUID_7, _uUID_8));
    };
    final Node shadowTargetParent = IterableExtensions.<Node>findFirst(this.trackingTree.getRoot().depthFirstSearch(), _function_4);
    shadowTargetParent.addChild(shadowSource);
    shadowSource.setParent(shadowTargetParent);
    return source;
  }
  
  /**
   * Moves a node to the specified index in its current parent
   * @param source Node to move
   * @param targetIndex Position in parents children list
   * @return the moved Node
   */
  public Node move(final Node source, final int targetIndex) {
    final Function1<String, Boolean> _function = (String n) -> {
      UUID _uUID = source.getUUID();
      String _plus = ((CloneST.MOVEPOS + CloneST.SOURCE) + _uUID);
      return Boolean.valueOf(n.startsWith(_plus));
    };
    final String previousMovePos = IterableExtensions.<String>findLast(this.logger.getLog(), _function);
    final Node parent = source.getParent();
    int index = targetIndex;
    int previousIndex = parent.getChildren().indexOf(source);
    if (((targetIndex < 0) || (targetIndex > (parent.getNumberOfChildren() - 1)))) {
      System.err.println("Error while moving node position: Appending node to the end");
      int _numberOfChildren = parent.getNumberOfChildren();
      int _minus = (_numberOfChildren - 1);
      index = _minus;
    }
    if ((previousMovePos != null)) {
      this.logger.getLog().remove(previousMovePos);
      final Function1<Node, Boolean> _function_1 = (Node n) -> {
        UUID _uUID = n.getUUID();
        UUID _uUID_1 = source.getUUID();
        return Boolean.valueOf(Objects.equal(_uUID, _uUID_1));
      };
      final Node originalSource = IterableExtensions.<Node>findFirst(this.trackingTree.getRoot().depthFirstSearch(), _function_1);
      final int originalIndex = originalSource.getParent().getChildren().indexOf(originalSource);
      if ((originalIndex != index)) {
        UUID _uUID = source.getUUID();
        String _plus = ((CloneST.MOVEPOS + CloneST.SOURCE) + _uUID);
        String _plus_1 = (_plus + CloneST.FROM);
        String _plus_2 = (_plus_1 + Integer.valueOf(originalIndex));
        String _plus_3 = (_plus_2 + CloneST.TO);
        String _plus_4 = (_plus_3 + Integer.valueOf(index));
        this.logger.logRaw(_plus_4);
      }
    } else {
      if ((previousIndex != index)) {
        UUID _uUID_1 = source.getUUID();
        String _plus_5 = ((CloneST.MOVEPOS + CloneST.SOURCE) + _uUID_1);
        String _plus_6 = (_plus_5 + CloneST.FROM);
        String _plus_7 = (_plus_6 + Integer.valueOf(previousIndex));
        String _plus_8 = (_plus_7 + CloneST.TO);
        String _plus_9 = (_plus_8 + Integer.valueOf(index));
        this.logger.logRaw(_plus_9);
      }
    }
    parent.getChildren().remove(source);
    parent.getChildren().add(index, source);
    final Function1<Node, Boolean> _function_2 = (Node n) -> {
      UUID _uUID_2 = n.getUUID();
      UUID _uUID_3 = parent.getUUID();
      return Boolean.valueOf(Objects.equal(_uUID_2, _uUID_3));
    };
    final Node shadowParent = IterableExtensions.<Node>findFirst(this.trackingTree.getRoot().depthFirstSearch(), _function_2);
    final Function1<Node, Boolean> _function_3 = (Node n) -> {
      UUID _uUID_2 = n.getUUID();
      UUID _uUID_3 = source.getUUID();
      return Boolean.valueOf(Objects.equal(_uUID_2, _uUID_3));
    };
    final Node shadowSource = IterableExtensions.<Node>findFirst(this.trackingTree.getRoot().depthFirstSearch(), _function_3);
    shadowParent.getChildren().remove(shadowSource);
    shadowParent.getChildren().add(index, shadowSource);
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
      String _plus = ((CloneST.SWAP + CloneST.SOURCE) + _uUID);
      String _plus_1 = (_plus + CloneST.TARGET);
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
      String _plus = ((CloneST.RDELETE + CloneST.TARGET) + _uUID);
      this.logger.logRaw(_plus);
      this._delete(source);
      source.getParent().getChildren().remove(source);
      final Function1<Node, Boolean> _function = (Node n) -> {
        String _string = n.getUUID().toString();
        String _string_1 = source.getUUID().toString();
        return Boolean.valueOf(Objects.equal(_string, _string_1));
      };
      final Node shadowSource = IterableExtensions.<Node>findFirst(this.trackingTree.getRoot().depthFirstSearch(), _function);
      _xblockexpression = shadowSource.getParent().getChildren().remove(shadowSource);
    }
    return _xblockexpression;
  }
  
  /**
   * Logs the uuids of all deleted nodes
   * @param source Node to be removed
   */
  private void _delete(final Node source) {
    this.logger.deleteLogsContainingString(source.getUUID().toString());
    UUID _uUID = source.getUUID();
    String _plus = ((CloneST.DELETE + CloneST.TARGET) + _uUID);
    this.logger.logRaw(_plus);
    final Consumer<Node> _function = (Node c) -> {
      this.logger.deleteLogsContainingString(c.getUUID().toString());
      UUID _uUID_1 = c.getUUID();
      String _plus_1 = ((CloneST.DELETE + CloneST.TARGET) + _uUID_1);
      this.logger.logRaw(_plus_1);
    };
    source.depthFirstSearch().forEach(_function);
  }
  
  /**
   * Performs refactoring depending on what type of container is given and
   *  replaces the occurences of the old value
   */
  public void refactor(final Node container, final String newValue) {
    if ((container instanceof NodeImpl)) {
      Node body = null;
      final String oldValue = this.getAttributeValue(container, "Name");
      NodeType _standardizedNodeType = ((NodeImpl)container).getStandardizedNodeType();
      if (_standardizedNodeType != null) {
        switch (_standardizedNodeType) {
          case VARIABLE_DECLARATOR:
            final Function1<Attribute, Boolean> _function = (Attribute a) -> {
              String _attributeKey = a.getAttributeKey();
              return Boolean.valueOf(Objects.equal(_attributeKey, "Name"));
            };
            boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(IterableExtensions.<Attribute>filter(((NodeImpl)container).getAttributes(), _function));
            if (_isNullOrEmpty) {
              String _string = ((NodeImpl)container).getUUID().toString();
              String _plus = ("A container for multiple declarations was input here: " + _string);
              InputOutput.<String>println(_plus);
              return;
            }
            body = ((NodeImpl)container).getParent().getParent();
            break;
          case ARGUMENT:
            this.setAttributeValue(container, "Name", newValue);
            NodeType _standardizedNodeType_1 = ((NodeImpl)container).getParent().getParent().getStandardizedNodeType();
            boolean _equals = Objects.equal(_standardizedNodeType_1, NodeType.METHOD_DECLARATION);
            if (_equals) {
              int _size = ((NodeImpl)container).getParent().getParent().getChildren().size();
              boolean _greaterThan = (_size > 1);
              if (_greaterThan) {
                body = ((NodeImpl)container).getParent().getParent().getChildren().get(1);
              }
            } else {
              final Function1<Node, Boolean> _function_1 = (Node n) -> {
                NodeType _standardizedNodeType_2 = n.getStandardizedNodeType();
                return Boolean.valueOf(Objects.equal(_standardizedNodeType_2, NodeType.BLOCK));
              };
              body = IterableExtensions.<Node>findFirst(((NodeImpl)container).getParent().getChildren(), _function_1);
            }
            break;
          case METHOD_DECLARATION:
            body = ((NodeImpl)container).getParent();
            break;
          case COMPILATION_UNIT:
          case CLASS:
            body = container;
            while (((!Objects.equal(body.getStandardizedNodeType(), NodeType.COMPILATION_UNIT)) && (body.getParent() != null))) {
              body = body.getParent();
            }
            break;
          default:
            {
              NodeType _standardizedNodeType_2 = ((NodeImpl)container).getStandardizedNodeType();
              String _plus_1 = ("Could not refactor node type: " + _standardizedNodeType_2);
              InputOutput.<String>println(_plus_1);
              return;
            }
        }
      } else {
        {
          NodeType _standardizedNodeType_2 = ((NodeImpl)container).getStandardizedNodeType();
          String _plus_1 = ("Could not refactor node type: " + _standardizedNodeType_2);
          InputOutput.<String>println(_plus_1);
          return;
        }
      }
      if ((body == null)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("Error with refactoring ");
        _builder.append(oldValue);
        _builder.append(": No body found");
        System.err.println(_builder);
      } else {
        UUID _uUID = ((NodeImpl)container).getUUID();
        String _plus_1 = ((CloneST.REFACTOR + CloneST.CONTAINER) + _uUID);
        String _plus_2 = (_plus_1 + CloneST.TYPE);
        String _nodeType = ((NodeImpl)container).getNodeType();
        String _plus_3 = (_plus_2 + _nodeType);
        String _plus_4 = (_plus_3 + CloneST.SCOPE);
        UUID _uUID_1 = body.getUUID();
        String _plus_5 = (_plus_4 + _uUID_1);
        String _plus_6 = (_plus_5 + CloneST.FROM);
        String _plus_7 = (_plus_6 + oldValue);
        String _plus_8 = (_plus_7 + CloneST.TO);
        String _plus_9 = (_plus_8 + newValue);
        this.logger.logRaw(_plus_9);
        this._refactor(body, Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("Name", "Value")), oldValue, newValue);
      }
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
        return Boolean.valueOf((Objects.equal(a.getAttributeKey(), attrKey) && Objects.equal(this.getAttributeValue(n, attrKey), oldValue)));
      };
      return Boolean.valueOf(IterableExtensions.<Attribute>exists(n.getAttributes(), _function_1));
    };
    final Consumer<Node> _function_1 = (Node n) -> {
      this.setAttributeValue(n, attrKey, newValue);
    };
    IterableExtensions.<Node>filter(body.depthFirstSearch(), _function).forEach(_function_1);
  }
  
  public void setAttributeValue(final Node node, final String attributeKey, final String newValue) {
    final Function1<String, Boolean> _function = (String n) -> {
      UUID _uUID = node.getUUID();
      String _plus = ((CloneST.SETATTR + CloneST.TARGET) + _uUID);
      String _plus_1 = (_plus + CloneST.KEY);
      String _plus_2 = (_plus_1 + attributeKey);
      return Boolean.valueOf(n.startsWith(_plus_2));
    };
    final String previousSetAttr = IterableExtensions.<String>findLast(this.logger.getLog(), _function);
    if ((previousSetAttr != null)) {
      this.logger.getLog().remove(previousSetAttr);
      final Function1<Node, Boolean> _function_1 = (Node n) -> {
        UUID _uUID = n.getUUID();
        UUID _uUID_1 = node.getUUID();
        return Boolean.valueOf(Objects.equal(_uUID, _uUID_1));
      };
      final Node originaNode = IterableExtensions.<Node>findFirst(this.trackingTree.getRoot().depthFirstSearch(), _function_1);
      final String originalValue = this.getAttributeValue(originaNode, attributeKey);
      boolean _notEquals = (!Objects.equal(originalValue, newValue));
      if (_notEquals) {
        UUID _uUID = node.getUUID();
        String _plus = ((CloneST.SETATTR + CloneST.TARGET) + _uUID);
        String _plus_1 = (_plus + CloneST.KEY);
        String _plus_2 = (_plus_1 + attributeKey);
        String _plus_3 = (_plus_2 + CloneST.FROM);
        String _plus_4 = (_plus_3 + originalValue);
        String _plus_5 = (_plus_4 + CloneST.TO);
        String _plus_6 = (_plus_5 + newValue);
        this.logger.logRaw(_plus_6);
      }
    } else {
      String _attributeValue = this.getAttributeValue(node, attributeKey);
      boolean _notEquals_1 = (!Objects.equal(_attributeValue, newValue));
      if (_notEquals_1) {
        UUID _uUID_1 = node.getUUID();
        String _plus_7 = ((CloneST.SETATTR + CloneST.TARGET) + _uUID_1);
        String _plus_8 = (_plus_7 + CloneST.KEY);
        String _plus_9 = (_plus_8 + attributeKey);
        String _plus_10 = (_plus_9 + CloneST.FROM);
        String _attributeValue_1 = this.getAttributeValue(node, attributeKey);
        String _plus_11 = (_plus_10 + _attributeValue_1);
        String _plus_12 = (_plus_11 + CloneST.TO);
        String _plus_13 = (_plus_12 + newValue);
        this.logger.logRaw(_plus_13);
      }
    }
    Value _head = IterableExtensions.<Value>head(node.getAttributeForKey(attributeKey).getAttributeValues());
    _head.setValue(newValue);
  }
  
  public String getAttributeValue(final Node node, final String attributeKey) {
    try {
      Object _value = IterableExtensions.<Value>head(node.getAttributeForKey(attributeKey).getAttributeValues()).getValue();
      return ((String) _value);
    } catch (final Throwable _t) {
      if (_t instanceof NoSuchElementException) {
        return null;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  /**
   * Returns a random entry of an iterable
   */
  public static <T extends Object> T random(final Iterable<T> l) {
    boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(l);
    if (_isNullOrEmpty) {
      return null;
    }
    return ((T[])Conversions.unwrapArray(l, Object.class))[new Random().nextInt(IterableExtensions.size(l))];
  }
  
  /**
   * Returns the root node of a node composite
   */
  public static Node getRoot(final Node n) {
    Node _parent = n.getParent();
    boolean _tripleNotEquals = (_parent != null);
    if (_tripleNotEquals) {
      return n.getParent();
    } else {
      return n;
    }
  }
}
